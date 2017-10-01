CREATE TABLE `users` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(100) NOT NULL,
	`email` VARCHAR(100) NOT NULL,
	`profile_picture` VARCHAR(1000) NULL DEFAULT NULL,
	`locale` VARCHAR(10) NULL DEFAULT NULL,
	`active` TINYINT(1) NOT NULL DEFAULT '0',
	`is_admin` TINYINT(1) NOT NULL DEFAULT '0',
	`created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`last_login_time` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `email` (`email`)
)
ENGINE=InnoDB
;

CREATE TABLE `users_access_tokens` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_id` INT(11) NOT NULL,
	`access_token` VARCHAR(500) NOT NULL,
	`created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `email` (`access_token`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;