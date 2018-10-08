-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Movies
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Movies
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Movies` DEFAULT CHARACTER SET utf8 ;
USE `Movies` ;

-- -----------------------------------------------------
-- Table `Movies`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`category` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(25) NULL,
  `last_update` TIMESTAMP NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`film_text`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`film_text` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NULL,
  `description` MEDIUMTEXT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`language`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`language` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` CHAR(20) NULL,
  `last_update` TIMESTAMP NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`film`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`film` (
  `title` VARCHAR(255) NOT NULL,
  `description` MEDIUMTEXT NULL,
  `release_year` DATE NULL,
  `rental_duration` TINYINT NULL,
  `rental_rate` DECIMAL(4,2) NULL,
  `length` SMALLINT NULL,
  `replacement_cost` DECIMAL(5,2) NULL,
  `rating` CHAR(5) NULL,
  `special_features` CHAR(54) NULL,
  `last_update` TIMESTAMP NULL,
  `ID` INT NOT NULL AUTO_INCREMENT,
  `film_text_ID` INT NOT NULL,
  `language_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_film_film_text1_idx` (`film_text_ID` ASC),
  INDEX `fk_film_language1_idx` (`language_ID` ASC),
  CONSTRAINT `fk_film_film_text1`
    FOREIGN KEY (`film_text_ID`)
    REFERENCES `Movies`.`film_text` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_film_language1`
    FOREIGN KEY (`language_ID`)
    REFERENCES `Movies`.`language` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`country` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(25) NULL,
  `last_update` TIMESTAMP NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`actor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`actor` (
  `ID` INT NOT NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `last_update` TIMESTAMP NULL,
  `country_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_actor_country1_idx` (`country_ID` ASC),
  CONSTRAINT `fk_actor_country1`
    FOREIGN KEY (`country_ID`)
    REFERENCES `Movies`.`country` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`city`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`city` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(50) NULL,
  `last_update` TIMESTAMP NULL,
  `country_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_city_country1_idx` (`country_ID` ASC),
  CONSTRAINT `fk_city_country1`
    FOREIGN KEY (`country_ID`)
    REFERENCES `Movies`.`country` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`address` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(50) NULL,
  `district` VARCHAR(20) NULL,
  `postal_code` VARCHAR(10) NULL,
  `phone` VARCHAR(20) NULL,
  `last_update` TIMESTAMP NULL,
  `city_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_address_city1_idx` (`city_ID` ASC),
  CONSTRAINT `fk_address_city1`
    FOREIGN KEY (`city_ID`)
    REFERENCES `Movies`.`city` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`inventory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`inventory` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `film_id` SMALLINT NULL,
  `last_update` TIMESTAMP NULL,
  `store_payment_payment_id` SMALLINT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`store`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`store` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `last_update` TIMESTAMP NULL,
  `address_ID` INT NOT NULL,
  `inventory_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_store_address1_idx` (`address_ID` ASC),
  INDEX `fk_store_inventory1_idx` (`inventory_ID` ASC),
  CONSTRAINT `fk_store_address1`
    FOREIGN KEY (`address_ID`)
    REFERENCES `Movies`.`address` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_store_inventory1`
    FOREIGN KEY (`inventory_ID`)
    REFERENCES `Movies`.`inventory` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`customer` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_nume` VARCHAR(45) NULL,
  `email` VARCHAR(50) NULL,
  `address_id` SMALLINT NULL,
  `active` BIT NULL,
  `create_date` TIMESTAMP NULL,
  `last_update` TIMESTAMP NULL,
  `store_store_id` TINYINT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_customer_store1_idx` (`store_store_id` ASC),
  CONSTRAINT `fk_customer_store1`
    FOREIGN KEY (`store_store_id`)
    REFERENCES `Movies`.`store` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`rental`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`rental` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `return_date` TIMESTAMP NULL,
  `last_update` TIMESTAMP NULL,
  `customer_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_rental_customer1_idx` (`customer_ID` ASC),
  CONSTRAINT `fk_rental_customer1`
    FOREIGN KEY (`customer_ID`)
    REFERENCES `Movies`.`customer` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`payment` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `amount` DECIMAL(5,2) NULL,
  `payment_date` TIMESTAMP NULL,
  `last_update` TIMESTAMP NULL,
  `rental_ID` INT NOT NULL,
  `customer_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_payment_rental1_idx` (`rental_ID` ASC),
  INDEX `fk_payment_customer1_idx` (`customer_ID` ASC),
  CONSTRAINT `fk_payment_rental1`
    FOREIGN KEY (`rental_ID`)
    REFERENCES `Movies`.`rental` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_payment_customer1`
    FOREIGN KEY (`customer_ID`)
    REFERENCES `Movies`.`customer` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`staff` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `address_id` SMALLINT NULL,
  `picture` BLOB NULL,
  `email` VARCHAR(50) NULL,
  `active` BIT NULL,
  `username` VARCHAR(16) NULL,
  `password` VARCHAR(40) NULL,
  `last_update` TIMESTAMP NULL,
  `staffcol` VARCHAR(45) NULL,
  `store_store_id` TINYINT NOT NULL,
  `store_ID` INT NOT NULL,
  PRIMARY KEY (`ID`, `store_store_id`),
  INDEX `fk_staff_store1_idx` (`store_ID` ASC),
  CONSTRAINT `fk_staff_store1`
    FOREIGN KEY (`store_ID`)
    REFERENCES `Movies`.`store` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`film_has_actor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`film_has_actor` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `film_ID` INT NOT NULL,
  `actor_ID` INT NOT NULL,
  PRIMARY KEY (`ID`, `film_ID`, `actor_ID`),
  INDEX `fk_film_has_actor_film1_idx` (`film_ID` ASC),
  INDEX `fk_film_has_actor_actor1_idx` (`actor_ID` ASC),
  CONSTRAINT `fk_film_has_actor_film1`
    FOREIGN KEY (`film_ID`)
    REFERENCES `Movies`.`film` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_film_has_actor_actor1`
    FOREIGN KEY (`actor_ID`)
    REFERENCES `Movies`.`actor` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`film_has_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`film_has_category` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `category_ID` INT NOT NULL,
  `film_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_film_has_category_category1_idx` (`category_ID` ASC),
  INDEX `fk_film_has_category_film1_idx` (`film_ID` ASC),
  CONSTRAINT `fk_film_has_category_category1`
    FOREIGN KEY (`category_ID`)
    REFERENCES `Movies`.`category` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_film_has_category_film1`
    FOREIGN KEY (`film_ID`)
    REFERENCES `Movies`.`film` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Movies`.`inventory_has_film`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies`.`inventory_has_film` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `inventory_ID` INT NOT NULL,
  `film_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_inventory_has_film_inventory1_idx` (`inventory_ID` ASC),
  INDEX `fk_inventory_has_film_film1_idx` (`film_ID` ASC),
  CONSTRAINT `fk_inventory_has_film_inventory1`
    FOREIGN KEY (`inventory_ID`)
    REFERENCES `Movies`.`inventory` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_inventory_has_film_film1`
    FOREIGN KEY (`film_ID`)
    REFERENCES `Movies`.`film` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
