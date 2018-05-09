--
-- Table structure
--
Create Database `cars`;
USE `cars`;
DROP TABLE IF EXISTS `CarDetails`;

CREATE TABLE `CarDetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `make` varchar(45) DEFAULT NULL,
  `model` varchar(45) DEFAULT NULL,
  `year` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `description` blob,
  `picture` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`));

INSERT INTO `CarDetails` VALUES (1,'Subaru','Impreza','2006','USA','Original rally car designed in the USA','subaru.jpg'),
						  (2,'BMW','5 series','2011','Germany','New style 5 series bemir now on the market','bmw.jpg');

						  