
CREATE TABLE `carrier`(
	`id` INTEGER(20) NOT NULL AUTO_INCREMENT,
	`carrier_name` VARCHAR(255) NULL DEFAULT NULL,	
	`carrier_type` INTEGER(11)  NULL DEFAULT NULL,
	`action_value` VARCHAR(500) NOT NULL DEFAULT 'deliver to office',
    `carrier_service_id` VARCHAR(255) NULL DEFAULT NULL,
	`shipment_service_id` VARCHAR(255) NULL DEFAULT NULL,

	PRIMARY KEY (`id`)
) ENGINE=InnoDB 
  AUTO_INCREMENT = 1;
  

CREATE TABLE `shipment`(
	`id` INTEGER(20) NOT NULL AUTO_INCREMENT,
	`carrier_type` INTEGER(11) NULL DEFAULT NULL,
	`length` DECIMAL(13,2) NULL DEFAULT NULL,
	`width` DECIMAL(13,2) NULL DEFAULT NULL,
	`height` DECIMAL(13,2) NULL DEFAULT NULL,
	`weight` DECIMAL(13,2) NULL DEFAULT NULL,
	`action_type` INTEGER(11) NULL DEFAULT NULL,
    `action_value` VARCHAR(500) NULL DEFAULT NULL,
	`is_delivered` TINYINT NOT NULL DEFAULT 0,
	`is_confirmed` TINYINT NOT NULL DEFAULT 0,
	`is_deleted` TINYINT NOT NULL DEFAULT 0,
	`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`carrier_id` INTEGER(20) NOT NULL DEFAULT 1,
	PRIMARY KEY (`id`),
     KEY `fk_carrier` (`carrier_id`),
	 CONSTRAINT `fk_carrier` FOREIGN KEY (`carrier_id`) REFERENCES `carrier` (`id`)
) 
ENGINE=InnoDB
AUTO_INCREMENT = 1;

  INSERT INTO carrier(carrier_name,carrier_type,action_value,carrier_service_id) VALUES ('ups', 1, 'deliver to office', 'fedexAIR');


INSERT INTO shipment ( carrier_type, length, width, height,
weight, action_type, action_value, is_delivered, is_confirmed, is_deleted,carrier_id, created_at)
VALUES ( 3, 100.00, 100.00, 100.00, 100.00, 1, 'deliver to home',0,0,1,1, NOW());


INSERT INTO shipment ( carrier_type, length, width, height,
weight, action_type, action_value, is_delivered, is_confirmed, is_deleted, carrier_id, created_at)
VALUES ( 1, 100.00, 100.00, 100.00, 100.00, 0, 'deliver to office',0,0,1,2, NOW());