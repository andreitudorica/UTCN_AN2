-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Food
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Food
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Food` DEFAULT CHARACTER SET utf8 ;
USE `Food` ;

-- -----------------------------------------------------
-- Table `Food`.`CONTAINER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Food`.`CONTAINER` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` NVARCHAR(100) NULL,
  `isMicrowaveable` TINYINT(1) NULL,
  `material` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Food`.`TYPEofFOOD`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Food`.`TYPEofFOOD` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` NVARCHAR(100) NULL,
  `TASTE` VARCHAR(45) NULL,
  `CONTAINER_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_TYPEofFOOD_CONTAINER1_idx` (`CONTAINER_ID` ASC),
  CONSTRAINT `fk_TYPEofFOOD_CONTAINER1`
    FOREIGN KEY (`CONTAINER_ID`)
    REFERENCES `Food`.`CONTAINER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Food`.`MEAL`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Food`.`MEAL` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` NVARCHAR(100) NULL,
  `startTime` TIME(6) NULL,
  `endTime` TIME(6) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Food`.`DISH`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Food`.`DISH` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` NVARCHAR(100) NULL,
  `cookingTime` INT NULL,
  `isVegan` TINYINT(1) NULL,
  `TYPEofFOOD_ID` INT NOT NULL,
  `MEAL_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_DISH_TYPEofFOOD1_idx` (`TYPEofFOOD_ID` ASC),
  INDEX `fk_DISH_MEAL1_idx` (`MEAL_ID` ASC),
  CONSTRAINT `fk_DISH_TYPEofFOOD1`
    FOREIGN KEY (`TYPEofFOOD_ID`)
    REFERENCES `Food`.`TYPEofFOOD` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DISH_MEAL1`
    FOREIGN KEY (`MEAL_ID`)
    REFERENCES `Food`.`MEAL` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Food`.`ConsumersCategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Food`.`ConsumersCategory` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` NVARCHAR(100) NOT NULL,
  `age` INT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Food`.`ConsumersCategory_has_DISH`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Food`.`ConsumersCategory_has_DISH` (
  `ConsumersCategory_ID` INT NOT NULL,
  `DISH_ID` INT NOT NULL,
  PRIMARY KEY (`ConsumersCategory_ID`, `DISH_ID`),
  INDEX `fk_ConsumersCategory_has_DISH_DISH1_idx` (`DISH_ID` ASC),
  INDEX `fk_ConsumersCategory_has_DISH_ConsumersCategory_idx` (`ConsumersCategory_ID` ASC),
  CONSTRAINT `fk_ConsumersCategory_has_DISH_ConsumersCategory`
    FOREIGN KEY (`ConsumersCategory_ID`)
    REFERENCES `Food`.`ConsumersCategory` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ConsumersCategory_has_DISH_DISH1`
    FOREIGN KEY (`DISH_ID`)
    REFERENCES `Food`.`DISH` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Food`.`CONTINENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Food`.`CONTINENT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` NVARCHAR(100) NULL,
  `PopularDISH_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_CONTINENT_DISH1_idx` (`PopularDISH_ID` ASC),
  CONSTRAINT `fk_CONTINENT_DISH1`
    FOREIGN KEY (`PopularDISH_ID`)
    REFERENCES `Food`.`DISH` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Food`.`COUNTRY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Food`.`COUNTRY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` NVARCHAR(100) NULL,
  `DISH_ID` INT NOT NULL,
  `CONTINENT_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_COUNTRY_DISH1_idx` (`DISH_ID` ASC),
  INDEX `fk_COUNTRY_CONTINENT1_idx` (`CONTINENT_ID` ASC),
  CONSTRAINT `fk_COUNTRY_DISH1`
    FOREIGN KEY (`DISH_ID`)
    REFERENCES `Food`.`DISH` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_COUNTRY_CONTINENT1`
    FOREIGN KEY (`CONTINENT_ID`)
    REFERENCES `Food`.`CONTINENT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Food`.`INGREDIENTS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Food`.`INGREDIENTS` (
  `ID` INT NOT NULL,
  `name` NVARCHAR(100) NULL,
  `State` VARCHAR(45) NULL COMMENT 'liquid or solid',
  `COUNTRY_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_INGREDIENTS_COUNTRY1_idx` (`COUNTRY_ID` ASC),
  CONSTRAINT `fk_INGREDIENTS_COUNTRY1`
    FOREIGN KEY (`COUNTRY_ID`)
    REFERENCES `Food`.`COUNTRY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Food`.`DISH_has_INGREDIENTS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Food`.`DISH_has_INGREDIENTS` (
  `DISH_ID` INT NOT NULL,
  `INGREDIENTS_ID` INT NOT NULL,
  PRIMARY KEY (`DISH_ID`, `INGREDIENTS_ID`),
  INDEX `fk_DISH_has_INGREDIENTS_INGREDIENTS1_idx` (`INGREDIENTS_ID` ASC),
  INDEX `fk_DISH_has_INGREDIENTS_DISH1_idx` (`DISH_ID` ASC),
  CONSTRAINT `fk_DISH_has_INGREDIENTS_DISH1`
    FOREIGN KEY (`DISH_ID`)
    REFERENCES `Food`.`DISH` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DISH_has_INGREDIENTS_INGREDIENTS1`
    FOREIGN KEY (`INGREDIENTS_ID`)
    REFERENCES `Food`.`INGREDIENTS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Food`.`TYPEofFOOD_has_TYPEofFOOD`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Food`.`TYPEofFOOD_has_TYPEofFOOD` (
  `TYPEofFOOD_ID` INT NOT NULL,
  `TYPEofFOOD_ID1` INT NOT NULL,
  PRIMARY KEY (`TYPEofFOOD_ID`, `TYPEofFOOD_ID1`),
  INDEX `fk_TYPEofFOOD_has_TYPEofFOOD_TYPEofFOOD2_idx` (`TYPEofFOOD_ID1` ASC),
  INDEX `fk_TYPEofFOOD_has_TYPEofFOOD_TYPEofFOOD1_idx` (`TYPEofFOOD_ID` ASC),
  CONSTRAINT `fk_TYPEofFOOD_has_TYPEofFOOD_TYPEofFOOD1`
    FOREIGN KEY (`TYPEofFOOD_ID`)
    REFERENCES `Food`.`TYPEofFOOD` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TYPEofFOOD_has_TYPEofFOOD_TYPEofFOOD2`
    FOREIGN KEY (`TYPEofFOOD_ID1`)
    REFERENCES `Food`.`TYPEofFOOD` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Food`.`TYPEofFOOD_has_MEAL`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Food`.`TYPEofFOOD_has_MEAL` (
  `TYPEofFOOD_ID` INT NOT NULL,
  `MEAL_ID` INT NOT NULL,
  PRIMARY KEY (`TYPEofFOOD_ID`, `MEAL_ID`),
  INDEX `fk_TYPEofFOOD_has_MEAL_MEAL1_idx` (`MEAL_ID` ASC),
  INDEX `fk_TYPEofFOOD_has_MEAL_TYPEofFOOD1_idx` (`TYPEofFOOD_ID` ASC),
  CONSTRAINT `fk_TYPEofFOOD_has_MEAL_TYPEofFOOD1`
    FOREIGN KEY (`TYPEofFOOD_ID`)
    REFERENCES `Food`.`TYPEofFOOD` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TYPEofFOOD_has_MEAL_MEAL1`
    FOREIGN KEY (`MEAL_ID`)
    REFERENCES `Food`.`MEAL` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Food`.`RESTAURANT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Food`.`RESTAURANT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` NVARCHAR(100) NULL,
  `country_ID` INT NULL,
  `BestDISH_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_RESTAURANT_DISH1_idx` (`BestDISH_ID` ASC),
  CONSTRAINT `fk_RESTAURANT_DISH1`
    FOREIGN KEY (`BestDISH_ID`)
    REFERENCES `Food`.`DISH` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
