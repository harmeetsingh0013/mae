CREATE SCHEMA mae_app;

USE mae_app;

DROP TABLE IF EXISTS `companies`;

DROP TABLE IF EXISTS `orders`;

DROP TABLE IF EXISTS `orders_items`;

DROP TABLE IF EXISTS `products`;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `companies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `add_date` date NOT NULL,
  `update_date` date DEFAULT NULL,
  `code` varchar(45) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` enum('owner','customer') NOT NULL,
  `gst_no` varchar(45) NOT NULL,
  `address` varchar(255) NOT NULL,
  `state` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `pincode` varchar(15) NOT NULL,
  `others` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pincode` (`pincode`)
);

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `add_date` date NOT NULL,
  `update_date` date DEFAULT NULL,
  `type` enum('owner','customer') NOT NULL DEFAULT 'owner',
  `mobile1` varchar(15) NOT NULL,
  `mobile2` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_users_1_idx` (`company_id`),
  CONSTRAINT `fk_users_1` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `code` varchar(45) NOT NULL,
  `add_date` date NOT NULL,
  `update_date` date DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  KEY `fk_products_1_idx` (`company_id`),
  KEY `code` (`code`),
  CONSTRAINT `fk_products_1` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);


CREATE TABLE `orders` (
  `id` int(40) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `invoice_no` varchar(45) NOT NULL,
  `mode` enum('cash','bill') DEFAULT NULL,
  `dispatch_through` varchar(45) DEFAULT NULL,
  `destination` varchar(45) DEFAULT NULL,
  `add_date` date NOT NULL,
  `update_date` date DEFAULT NULL,
  `status` enum('cancel','approve') NOT NULL DEFAULT 'approve',
  `discount` decimal(10,2) DEFAULT NULL,
  `s_gst` decimal(10,2) NOT NULL,
  `c_gst` decimal(10,2) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `invoice_no` (`invoice_no`),
  KEY `fk_orders_1_idx` (`company_id`),
  CONSTRAINT `fk_orders_1` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `orders_items` (
  `id` int(40) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orders_items_1_idx` (`product_id`),
  KEY `fk_orders_items_2_idx` (`order_id`),
  CONSTRAINT `fk_orders_items_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_items_2` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);