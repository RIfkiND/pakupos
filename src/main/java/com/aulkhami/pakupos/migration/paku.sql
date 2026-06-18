-- ============================================================
--  POS SYSTEM — DATABASE SCHEMA & FAKE DATA (CLEAN)
-- ============================================================

-- Hapus tabel jika sudah ada (sesuai urutan foreign key)
DROP TABLE IF EXISTS `payments`;
DROP TABLE IF EXISTS `order_items`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `users`;

-- ─────────────────────────────────────────
--  1. USERS
-- ─────────────────────────────────────────
CREATE TABLE `users` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `phone` VARCHAR(20),
  `role` ENUM('owner', 'karyawan') NOT NULL DEFAULT 'karyawan',
  `is_active` BOOLEAN DEFAULT TRUE,
  `last_login_at` TIMESTAMP NULL DEFAULT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ─────────────────────────────────────────
--  2. CATEGORY
-- ─────────────────────────────────────────
CREATE TABLE `category` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(30) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ─────────────────────────────────────────
--  3. PRODUCTS
-- ─────────────────────────────────────────
CREATE TABLE `products` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL,
  `category_id` INT NOT NULL,
  `price` DECIMAL(15, 2) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  CONSTRAINT `fk_products_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
);

-- ─────────────────────────────────────────
--  4. CUSTOMERS
-- ─────────────────────────────────────────
CREATE TABLE `customers` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL,
  `total_orders` INT DEFAULT 0,
  `total_spent` DECIMAL(15, 2) DEFAULT 0.00,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ─────────────────────────────────────────
--  5. ORDERS
-- ─────────────────────────────────────────
CREATE TABLE `orders` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `customer_id` INT NULL,
  `customer_name` VARCHAR(255),
  `total_amount` DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
  `payment_method` VARCHAR(50),
  `status` ENUM('pending', 'completed', 'cancelled') NOT NULL DEFAULT 'pending',
  `ordered_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT `fk_orders_customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON DELETE SET NULL
);

-- ─────────────────────────────────────────
--  6. ORDER ITEMS
-- ─────────────────────────────────────────
CREATE TABLE `order_items` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `price` DECIMAL(15, 2) NOT NULL,
  `subtotal` DECIMAL(15, 2) NOT NULL,
  
  CONSTRAINT `fk_order_items_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_order_items_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
);

-- ─────────────────────────────────────────
--  7. PAYMENTS
-- ─────────────────────────────────────────
CREATE TABLE `payments` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `order_id` INT NOT NULL UNIQUE,
  `payment_number` VARCHAR(50) NOT NULL UNIQUE,
  `payment_method` ENUM('cash', 'transfer', 'ewallet', 'credit_card', 'qris') NOT NULL,
  `amount` DECIMAL(12, 2) NOT NULL,
  `payment_status` ENUM('pending', 'paid', 'failed', 'refunded') NOT NULL DEFAULT 'pending',
  `paid_at` TIMESTAMP NULL DEFAULT NULL,
  `expired_at` TIMESTAMP NULL DEFAULT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  CONSTRAINT `fk_payments_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE
);

-- ============================================================
--  INSERT FAKE DATA
-- ============================================================

INSERT INTO `users` (`name`, `email`, `password`, `phone`, `role`) VALUES
('Budi Owner', 'owner@pakupos.com', '75K3eLr+dx6JJFuJ7LwIpEpOFmwGZZkRiB84PURz6U8=', '08111222333', 'owner'),
('Siti Karyawan', 'karyawan1@pakupos.com', '75K3eLr+dx6JJFuJ7LwIpEpOFmwGZZkRiB84PURz6U8=', '08222333444', 'karyawan'),
('Agus Karyawan', 'karyawan2@pakupos.com', '75K3eLr+dx6JJFuJ7LwIpEpOFmwGZZkRiB84PURz6U8=', '08333444555', 'karyawan');

INSERT INTO `category` (`name`) VALUES
('Martabak Manis'),
('Martabak Telur'),
('Minuman');

INSERT INTO `products` (`name`, `category_id`, `price`) VALUES
('Martabak Manis Coklat Kacang', 1, 25000.00),
('Martabak Manis Keju Susu', 1, 30000.00),
('Martabak Manis Spesial', 1, 40000.00),
('Martabak Telur Bebek 2', 2, 25000.00),
('Martabak Telur Ayam Spesial', 2, 45000.00),
('Es Teh Manis', 3, 5000.00);

INSERT INTO `customers` (`name`, `total_orders`, `total_spent`) VALUES
('Andi Pelanggan', 1, 65000.00),
('Rina Pelanggan', 2, 120000.00);

INSERT INTO `orders` (`customer_id`, `customer_name`, `total_amount`, `payment_method`, `status`) VALUES
(1, 'Andi Pelanggan', 65000.00, 'qris', 'completed'),
(NULL, 'Tamu Umum', 45000.00, 'cash', 'pending');

INSERT INTO `order_items` (`order_id`, `product_id`, `quantity`, `price`, `subtotal`) VALUES
(1, 1, 1, 25000.00, 25000.00),
(1, 3, 1, 40000.00, 40000.00),
(2, 5, 1, 45000.00, 45000.00);

INSERT INTO `payments` (`order_id`, `payment_number`, `payment_method`, `amount`, `payment_status`, `paid_at`) VALUES
(1, 'PAY-20260616-001', 'qris', 65000.00, 'paid', CURRENT_TIMESTAMP),
(2, 'PAY-20260616-002', 'cash', 45000.00, 'pending', NULL);