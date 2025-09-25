SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

USE babouledb;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

DROP TABLE IF EXISTS `books`;
CREATE TABLE IF NOT EXISTS `books` (
    `bk_id` int(11) NOT NULL AUTO_INCREMENT,
    `bk_title` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
    `bk_author` int(11),
    `bk_location` int(11),
    `bk_editor` int(11),
  PRIMARY KEY (`bk_id`),
  UNIQUE KEY `IDX_BOOKS` (`bk_title`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `authors`;
CREATE TABLE IF NOT EXISTS `authors` (
  `auth_id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_fname` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `auth_lname` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`auth_id`),
  UNIQUE KEY `IDX_AUTHORS` (`auth_lname`, `auth_fname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `editors`;
CREATE TABLE IF NOT EXISTS `editors` (
  `ed_id` int(11) NOT NULL AUTO_INCREMENT,
  `ed_name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ed_id`),
  UNIQUE KEY `IDX_EDITORS` (`ed_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `locations`;
CREATE TABLE IF NOT EXISTS `locations` (
  `loc_id` int(11) NOT NULL AUTO_INCREMENT,
  `loc_city` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`loc_id`),
  UNIQUE KEY `IDX_LOCATIONS` (`loc_city`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE `books`
  ADD CONSTRAINT `FK_AUTHOR` FOREIGN KEY (`bk_author`) REFERENCES `authors` (`auth_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_EDITOR` FOREIGN KEY (`bk_editor`) REFERENCES `editors` (`ed_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_LOCATION` FOREIGN KEY (`bk_location`) REFERENCES `locations` (`loc_id`) ON DELETE CASCADE;

