DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `card` int(11) DEFAULT NULL,
                            `subscription_id` int(11) DEFAULT NULL,
                            `name` varchar(50) NOT NULL,
                            `surname` varchar(50) NOT NULL,
                            `phone` varchar(20) NOT NULL,
                            `phone2` varchar(20) DEFAULT NULL,
                            `address` varchar(100) NOT NULL,
                            `archived` tinyint(1) NOT NULL DEFAULT 0,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `name` varchar(50) NOT NULL,
                            `surname` varchar(50) NOT NULL,
                            `phone` varchar(20) NOT NULL,
                            `phone2` varchar(20) DEFAULT NULL,
                            `address` varchar(100) NOT NULL,
                            `archived` tinyint(1) NOT NULL DEFAULT 0,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `employment`;
CREATE TABLE `employment` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `name` varchar(30) NOT NULL,
                              `price` int(11) DEFAULT 0,
                              `archived` tinyint(1) NOT NULL DEFAULT 0,
                              `position_id` int(11) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `unique_employment_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `name` varchar(20) DEFAULT NULL,
                            `archived` tinyint(1) NOT NULL DEFAULT 0,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `unique_position_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `subscription`;
CREATE TABLE `subscription` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `name` varchar(30) NOT NULL,
                                `archived` tinyint(1) NOT NULL DEFAULT 0,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `unique_subscription_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `archive`;
CREATE TABLE `archive` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `date` timestamp NOT NULL DEFAULT current_timestamp(),
                                `customer_id` int(11) NOT NULL,
                                `employee_id` int(11) DEFAULT NULL,
                                `employment_id` int(11) DEFAULT NULL,
                                `bonus` tinyint(1) NOT NULL DEFAULT 0,
                                PRIMARY KEY (`id`),
                                KEY `fk_customer_archive` (`customer_id`),
                                KEY `fk_employee_archive` (`employee_id`),
                                KEY `employment_id` (`employment_id`),
                                CONSTRAINT `archive_ibfk_1` FOREIGN KEY (`employment_id`) REFERENCES `employment` (`id`),
                                CONSTRAINT `fk_customer_archive` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT `fk_employee_archive` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `employee_position`;
CREATE TABLE `employee_position` (
                                `employee_id` int(11) NOT NULL,
                                `position_id` int(11) NOT NULL,
                                UNIQUE KEY `employee_id` (`employee_id`,`position_id`),
                                CONSTRAINT `employee_position_fk` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `subscription_employment`;
CREATE TABLE `subscription_employment` (
                                `subscription_id` int(11) NOT NULL,
                                `employment_id` int(11) NOT NULL,
                                `quantity` int(11) NOT NULL DEFAULT 0,
                                `price` int(11) NOT NULL DEFAULT 0,
                                PRIMARY KEY (`subscription_id`,`employment_id`),
                                KEY `fk_employment_id` (`employment_id`) USING BTREE,
                                CONSTRAINT `fk_service_id` FOREIGN KEY (`employment_id`) REFERENCES `employment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT `fk_subscription_id` FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `working_days`;
CREATE TABLE `working_days` (
                                `employee_id` int(11) NOT NULL,
                                `monday` tinyint(1) NOT NULL DEFAULT 0,
                                `tuesday` tinyint(1) NOT NULL DEFAULT 0,
                                `wednesday` tinyint(1) NOT NULL DEFAULT 0,
                                `thursday` tinyint(1) NOT NULL DEFAULT 0,
                                `friday` tinyint(1) NOT NULL DEFAULT 0,
                                `saturday` tinyint(1) NOT NULL DEFAULT 0,
                                `sunday` tinyint(1) NOT NULL DEFAULT 0,
                                UNIQUE KEY `working_days_employee_id_unique` (`employee_id`),
                                CONSTRAINT `working_days_fk` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
