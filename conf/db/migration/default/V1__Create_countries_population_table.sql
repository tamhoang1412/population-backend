CREATE TABLE `countries` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  `code` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`)
  );

CREATE TABLE `population` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `country_id` INT NOT NULL,
  `year` INT NOT NULL,
  `population` BIGINT,
  PRIMARY KEY (`id`)
  );
