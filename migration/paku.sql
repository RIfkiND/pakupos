-- ============================================================
--  POS SYSTEM — DATABASE SCHEMA
--  Updated from final class diagram (no customers table,
--  customer_name as plain field, order_code + queue_number
--  auto-generated, abstract/interface pattern reflected)
-- ============================================================

-- ─────────────────────────────────────────
--  ENUM TYPES
-- ─────────────────────────────────────────
CREATE TYPE role_enum AS ENUM ('OWNER', 'KARYAWAN');

CREATE TYPE order_status_enum AS ENUM ('PENDING', 'COMPLETED', 'CANCELLED');

CREATE TYPE payment_method_enum AS ENUM (
  'CASH', 'TRANSFER', 'EWALLET', 'CREDIT_CARD', 'QRIS'
);

CREATE TYPE payment_status_enum AS ENUM (
  'PENDING', 'PAID', 'FAILED', 'REFUNDED'
);


-- ─────────────────────────────────────────
--  USERS
--  Inherits: AuditableEntity → BaseEntity
-- ─────────────────────────────────────────
CREATE TABLE users (
  id              BIGSERIAL       PRIMARY KEY,
  name            VARCHAR(100)    NOT NULL,
  email           VARCHAR(100)    UNIQUE NOT NULL,
  password        VARCHAR(255)    NOT NULL,
  phone           VARCHAR(20)     UNIQUE,
  role            role_enum       NOT NULL DEFAULT 'KARYAWAN',
  is_active       BOOLEAN         NOT NULL DEFAULT TRUE,
  last_login_at   TIMESTAMP,
  created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),  -- BaseEntity
  updated_at      TIMESTAMP       NOT NULL DEFAULT NOW()   -- AuditableEntity
);

CREATE INDEX idx_users_email     ON users (email);
CREATE INDEX idx_users_phone     ON users (phone);
CREATE INDEX idx_users_role      ON users (role);
CREATE INDEX idx_users_is_active ON users (is_active);


-- ─────────────────────────────────────────
--  PRODUCTS
--  Inherits: AuditableEntity → BaseEntity
--  Implements: Priceable
-- ─────────────────────────────────────────
CREATE TABLE products (
  id                  BIGSERIAL       PRIMARY KEY,
  name                VARCHAR(255)    NOT NULL,
  category            VARCHAR(100),
  price               DECIMAL(15, 2)  NOT NULL,             -- Priceable.getUnitPrice()
  stock               INT             NOT NULL DEFAULT 0,
  created_at          TIMESTAMP       NOT NULL DEFAULT NOW(), -- BaseEntity
  updated_at          TIMESTAMP       NOT NULL DEFAULT NOW()  -- AuditableEntity
);

CREATE INDEX idx_products_category ON products (category);
CREATE INDEX idx_products_stock    ON products (stock);


-- ─────────────────────────────────────────
--  ORDERS
--  Inherits: OrderableEntity → AuditableEntity → BaseEntity
--  Implements: Payable, Statusable<OrderStatus>, Queueable
--
--  Flow:
--    1. Karyawan klik (+) → INSERT order → order_code & queue_number
--       di-generate otomatis via trigger (Queueable.initQueue)
--    2. Pilih menu → INSERT order_items
--    3. Input nama → UPDATE orders SET customer_name = ?
--       (Queueable.setCustomerName)
-- ─────────────────────────────────────────
CREATE TABLE orders (
  id              BIGSERIAL           PRIMARY KEY,
  user_id         BIGINT              NOT NULL REFERENCES users (id),  -- karyawan yang input

  -- Queueable / OrderableEntity
  order_code      VARCHAR(20)         UNIQUE NOT NULL,                 -- e.g. ORD-20250611-0042
  queue_number    INT                 NOT NULL,

  -- Queueable.setCustomerName — diisi setelah customer konfirmasi nama
  customer_name   VARCHAR(255),                                        -- nullable saat pertama dibuat

  -- Payable
  total_amount    DECIMAL(15, 2)      NOT NULL DEFAULT 0,

  -- Statusable<OrderStatus>
  status          order_status_enum   NOT NULL DEFAULT 'PENDING',

  ordered_at      TIMESTAMP           NOT NULL DEFAULT NOW(),          -- OrderableEntity
  created_at      TIMESTAMP           NOT NULL DEFAULT NOW(),          -- BaseEntity
  updated_at      TIMESTAMP           NOT NULL DEFAULT NOW()           -- AuditableEntity
);

CREATE INDEX idx_orders_user_id      ON orders (user_id);
CREATE INDEX idx_orders_order_code   ON orders (order_code);
CREATE INDEX idx_orders_queue_number ON orders (queue_number);
CREATE INDEX idx_orders_status       ON orders (status);
CREATE INDEX idx_orders_ordered_at   ON orders (ordered_at);


-- ─────────────────────────────────────────
--  ORDER ITEMS
--  Inherits: BaseEntity
--  Implements: Priceable
-- ─────────────────────────────────────────
CREATE TABLE order_items (
  id          BIGSERIAL       PRIMARY KEY,
  order_id    BIGINT          NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
  product_id  BIGINT          NOT NULL REFERENCES products (id),
  quantity    INT             NOT NULL CHECK (quantity > 0),
  unit_price  DECIMAL(15, 2)  NOT NULL,                    -- Priceable.getUnitPrice()
  subtotal    DECIMAL(15, 2)  NOT NULL,                    -- Priceable.getSubtotal()
  created_at  TIMESTAMP       NOT NULL DEFAULT NOW()       -- BaseEntity
);

CREATE INDEX idx_order_items_order_id   ON order_items (order_id);
CREATE INDEX idx_order_items_product_id ON order_items (product_id);


-- ─────────────────────────────────────────
--  PAYMENTS
--  Inherits: AuditableEntity → BaseEntity
--  Implements: Statusable<PaymentStatus>
-- ─────────────────────────────────────────
CREATE TABLE payments (
  id                        BIGSERIAL               PRIMARY KEY,
  order_id                  BIGINT                  NOT NULL UNIQUE REFERENCES orders (id),
  payment_number            VARCHAR(50)             UNIQUE NOT NULL,

  -- PaymentMethod enum
  payment_method            payment_method_enum     NOT NULL,
  payment_gateway           VARCHAR(50),

  amount                    DECIMAL(12, 2)          NOT NULL,

  -- Statusable<PaymentStatus>
  status                    payment_status_enum     NOT NULL DEFAULT 'PENDING',

  payment_proof_url         VARCHAR(255),
  payment_gateway_response  JSONB,
  paid_at                   TIMESTAMP,
  expired_at                TIMESTAMP,
  created_at                TIMESTAMP               NOT NULL DEFAULT NOW(),  -- BaseEntity
  updated_at                TIMESTAMP               NOT NULL DEFAULT NOW()   -- AuditableEntity
);

CREATE INDEX idx_payments_order_id  ON payments (order_id);
CREATE INDEX idx_payments_number    ON payments (payment_number);
CREATE INDEX idx_payments_status    ON payments (status);


-- ─────────────────────────────────────────
--  TRIGGER: preUpdate → AuditableEntity
-- ─────────────────────────────────────────
CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_users_updated_at
  BEFORE UPDATE ON users
  FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_products_updated_at
  BEFORE UPDATE ON products
  FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_orders_updated_at
  BEFORE UPDATE ON orders
  FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_payments_updated_at
  BEFORE UPDATE ON payments
  FOR EACH ROW EXECUTE FUNCTION set_updated_at();


-- ─────────────────────────────────────────
--  TRIGGER: Queueable.initQueue()
--  Auto-generate order_code + queue_number
--  saat INSERT order baru (karyawan klik "+")
-- ─────────────────────────────────────────
CREATE OR REPLACE FUNCTION generate_order_queue()
RETURNS TRIGGER AS $$
DECLARE
  today       TEXT;
  next_queue  INT;
BEGIN
  today := TO_CHAR(NOW(), 'YYYYMMDD');

  -- queue_number: reset tiap hari, increment per hari
  SELECT COALESCE(MAX(queue_number), 0) + 1
    INTO next_queue
    FROM orders
   WHERE DATE(ordered_at) = CURRENT_DATE;

  -- order_code format: ORD-YYYYMMDD-XXXX (e.g. ORD-20250611-0001)
  NEW.order_code   := 'ORD-' || today || '-' || LPAD(next_queue::TEXT, 4, '0');
  NEW.queue_number := next_queue;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_orders_init_queue
  BEFORE INSERT ON orders
  FOR EACH ROW EXECUTE FUNCTION generate_order_queue();


-- ─────────────────────────────────────────
--  TRIGGER: Payable.getTotalAmount()
--  Auto-recalculate total_amount di orders
--  setiap kali order_items berubah
-- ─────────────────────────────────────────
CREATE OR REPLACE FUNCTION recalculate_order_total()
RETURNS TRIGGER AS $$
BEGIN
  UPDATE orders
     SET total_amount = (
           SELECT COALESCE(SUM(subtotal), 0)
             FROM order_items
            WHERE order_id = COALESCE(NEW.order_id, OLD.order_id)
         )
   WHERE id = COALESCE(NEW.order_id, OLD.order_id);
  RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_order_items_recalc
  AFTER INSERT OR UPDATE OR DELETE ON order_items
  FOR EACH ROW EXECUTE FUNCTION recalculate_order_total();
