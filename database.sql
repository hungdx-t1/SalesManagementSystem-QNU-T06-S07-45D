CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  full_name VARCHAR(100),
  role ENUM('admin', 'sales', 'customer') DEFAULT 'customer',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE customers (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  phone VARCHAR(20) UNIQUE,
  email VARCHAR(100),
  address TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  category_id INT,
  price DECIMAL(10,2) NOT NULL,
  stock INT DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE sales_orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  customer_id INT,
  order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  total_amount DECIMAL(10,2) DEFAULT 0,
  status ENUM('pending','paid','cancelled') DEFAULT 'pending',
  FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE sales_order_items (
  id INT AUTO_INCREMENT PRIMARY KEY,
  order_id INT,
  product_id INT,
  quantity INT NOT NULL,
  unit_price DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (order_id) REFERENCES sales_orders(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE payments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  order_id INT,
  amount DECIMAL(10,2) NOT NULL,
  payment_method ENUM('cash','bank_transfer','ewallet') DEFAULT 'cash',
  paid_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES sales_orders(id)
);

CREATE TABLE suppliers (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  phone VARCHAR(20),
  email VARCHAR(100),
  address TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE import_orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  supplier_id INT,
  product_id INT,
  quantity INT NOT NULL,
  import_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  total_cost DECIMAL(10,2),
  FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);
