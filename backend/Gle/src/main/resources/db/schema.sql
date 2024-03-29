CREATE DATABASE IF NOT EXISTS `products_directory`;
USE `products_directory`;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `product_category`;
DROP TABLE IF EXISTS `user_address`;
DROP TABLE IF EXISTS `cart_items`;
DROP TABLE IF EXISTS `payment_details`;
DROP TABLE IF EXISTS `order_items`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `brand`;
DROP TABLE IF EXISTS `order_details`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `category`;
-- AppUser Table
CREATE TABLE app_user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255),
    last_name VARCHAR(255),
    mobile BIGINT,
    email VARCHAR(255),
    password VARCHAR(255),
    appUserRole ENUM('USER', 'ADMIN') -- Assuming these are the roles
);

-- CartItem Table
CREATE TABLE cart_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    user_id INT,
    quantity INT,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES app_user(id)
);

-- OrderDetail Table
CREATE TABLE order_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    total INT,
    payment_id INT,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES app_user(id)
);

-- OrderItem Table
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    FOREIGN KEY (order_id) REFERENCES order_details(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- PaymentDetail Table
CREATE TABLE payment_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    status VARCHAR(255),
    FOREIGN KEY (order_id) REFERENCES order_details(id)
);

-- Product Table
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    suggested_use TEXT,
    other_ingredients TEXT,
    first_image VARCHAR(255),
    second_image VARCHAR(255),
    title VARCHAR(255),
    description TEXT,
    weight VARCHAR(255),
    quantity VARCHAR(255),
    price_uae INT,
    price_eg INT,
    expiry_date VARCHAR(255),
    rating INT
);

-- UserAddress Table
CREATE TABLE user_address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    address_line1 VARCHAR(255),
    city ENUM('Cairo'),
    area VARCHAR(255),
    mobile INT,
    FOREIGN KEY (user_id) REFERENCES app_user(id)
);
