-- ============================================================
--  POS SYSTEM — DATABASE SCHEMA (MySQL VERSION)
-- ============================================================

-- ─────────────────────────────────────────
--  USERS
-- ─────────────────────────────────────────
CREATE TABLE users (
  id              BIGINT          PRIMARY KEY AUTO_INCREMENT,
  name            VARCHAR(100)    NOT NULL,
  email           VARCHAR(100)    UNIQUE NOT NULL,
  password        VARCHAR(255)    NOT NULL,
  phone           VARCHAR(20)     UNIQUE,
  role            ENUM('OWNER', 'KARYAWAN') NOT NULL DEFAULT 'KARYAWAN',
  is_active       BOOLEAN         NOT NULL DEFAULT TRUE,
  last_login_at   TIMESTAMP       NULL,
  created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email     ON users (email);
CREATE INDEX idx_users_phone     ON users (phone);
CREATE INDEX idx_users_role      ON users (role);
CREATE INDEX idx_users_is_active ON users (is_active);


-- ─────────────────────────────────────────
--  PRODUCTS
-- ─────────────────────────────────────────
CREATE TABLE products (
  id                  BIGINT          PRIMARY KEY AUTO_INCREMENT,
  name                VARCHAR(255)    NOT NULL,
  category            VARCHAR(100),
  price               DECIMAL(15, 2)  NOT NULL,
  stock               INT             NOT NULL DEFAULT 0,
  created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_products_category ON products (category);
CREATE INDEX idx_products_stock    ON products (stock);


-- ─────────────────────────────────────────
--  ORDERS
-- ─────────────────────────────────────────
CREATE TABLE orders (
  id              BIGINT              PRIMARY KEY AUTO_INCREMENT,
  user_id         BIGINT              NOT NULL,
  order_code      VARCHAR(20)         UNIQUE NOT NULL,
  queue_number    INT                 NOT NULL,
  customer_name   VARCHAR(255)        NULL,
  total_amount    DECIMAL(15, 2)      NOT NULL DEFAULT 0,
  status          ENUM('PENDING', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
  ordered_at      TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_at      TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX idx_orders_user_id      ON orders (user_id);
CREATE INDEX idx_orders_order_code   ON orders (order_code);
CREATE INDEX idx_orders_queue_number ON orders (queue_number);
CREATE INDEX idx_orders_status       ON orders (status);
CREATE INDEX idx_orders_ordered_at   ON orders (ordered_at);


-- ─────────────────────────────────────────
--  ORDER ITEMS
-- ─────────────────────────────────────────
CREATE TABLE order_items (
  id          BIGINT          PRIMARY KEY AUTO_INCREMENT,
  order_id    BIGINT          NOT NULL,
  product_id  BIGINT          NOT NULL,
  quantity    INT             NOT NULL,
  unit_price  DECIMAL(15, 2)  NOT NULL,
  subtotal    DECIMAL(15, 2)  NOT NULL,
  created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE INDEX idx_order_items_order_id   ON order_items (order_id);
CREATE INDEX idx_order_items_product_id ON order_items (product_id);


-- ─────────────────────────────────────────
--  PAYMENTS
-- ─────────────────────────────────────────
CREATE TABLE payments (
  id                        BIGINT                  PRIMARY KEY AUTO_INCREMENT,
  order_id                  BIGINT                  NOT NULL UNIQUE,
  payment_number            VARCHAR(50)             UNIQUE NOT NULL,
  payment_method            ENUM('CASH', 'TRANSFER', 'EWALLET', 'CREDIT_CARD', 'QRIS') NOT NULL,
  payment_gateway           VARCHAR(50),
  amount                    DECIMAL(12, 2)          NOT NULL,
  status                    ENUM('PENDING', 'PAID', 'FAILED', 'REFUNDED') NOT NULL DEFAULT 'PENDING',
  payment_proof_url         VARCHAR(255),
  payment_gateway_response  JSON,
  paid_at                   TIMESTAMP               NULL,
  expired_at                TIMESTAMP               NULL,
  created_at                TIMESTAMP               NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at                TIMESTAMP               NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE INDEX idx_payments_order_id  ON payments (order_id);
CREATE INDEX idx_payments_number    ON payments (payment_number);
CREATE INDEX idx_payments_status    ON payments (status);


-- ─────────────────────────────────────────
--  TRIGGER: Auto-generate order_code + queue_number
-- ─────────────────────────────────────────
DELIMITER //
CREATE TRIGGER trg_orders_init_queue
BEFORE INSERT ON orders
FOR EACH ROW
BEGIN
    DECLARE today TEXT;
    DECLARE next_queue INT;

    SET today = DATE_FORMAT(NOW(), '%Y%m%d');

    SELECT COALESCE(MAX(queue_number), 0) + 1
    INTO next_queue
    FROM orders
    WHERE DATE(ordered_at) = CURRENT_DATE();

    SET NEW.order_code = CONCAT('ORD-', today, '-', LPAD(next_queue, 4, '0'));
    SET NEW.queue_number = next_queue;
END;
//
DELIMITER ;


-- ─────────────────────────────────────────
--  TRIGGER: Auto-recalculate total_amount
-- ─────────────────────────────────────────
DELIMITER //
CREATE TRIGGER trg_order_items_after_insert
AFTER INSERT ON order_items
FOR EACH ROW
BEGIN
    UPDATE orders
    SET total_amount = (SELECT SUM(subtotal) FROM order_items WHERE order_id = NEW.order_id)
    WHERE id = NEW.order_id;
END;
//

CREATE TRIGGER trg_order_items_after_update
AFTER UPDATE ON order_items
FOR EACH ROW
BEGIN
    UPDATE orders
    SET total_amount = (SELECT SUM(subtotal) FROM order_items WHERE order_id = NEW.order_id)
    WHERE id = NEW.order_id;
END;
//

CREATE TRIGGER trg_order_items_after_delete
AFTER DELETE ON order_items
FOR EACH ROW
BEGIN
    UPDATE orders
    SET total_amount = (SELECT SUM(subtotal) FROM order_items WHERE order_id = OLD.order_id)
    WHERE id = OLD.order_id;
END;
//
DELIMITER ;
