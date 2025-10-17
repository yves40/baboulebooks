-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 17 oct. 2025 à 11:17
-- Version du serveur : 5.7.36
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `babouledb`
--

START TRANSACTION;
DELETE FROM sessions;
DELETE FROM users_roles;
DELETE FROM users;
DELETE FROM roles;
DROP TABLE IF EXISTS `books`;
DROP TABLE IF EXISTS `authors`;
DROP TABLE IF EXISTS `editors`;
DROP TABLE IF EXISTS `locations`;
DROP TABLE IF EXISTS `users_roles`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `sessions`;
DROP TABLE IF EXISTS `users`;
COMMIT;

START TRANSACTION;
CREATE TABLE IF NOT EXISTS `authors` (
  `auth_id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_fname` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `auth_lname` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`auth_id`),
  UNIQUE KEY `IDX_AUTHORS` (`auth_lname`,`auth_fname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `books` (
  `bk_id` int(11) NOT NULL AUTO_INCREMENT,
  `bk_title` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bk_author` int(11) DEFAULT NULL,
  `bk_location` int(11) DEFAULT NULL,
  `bk_editor` int(11) DEFAULT NULL,
  PRIMARY KEY (`bk_id`),
  UNIQUE KEY `IDX_BOOKS` (`bk_title`) USING BTREE,
  KEY `FK_AUTHOR` (`bk_author`),
  KEY `FK_EDITOR` (`bk_editor`),
  KEY `FK_LOCATION` (`bk_location`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `editors` (
  `ed_id` int(11) NOT NULL AUTO_INCREMENT,
  `ed_name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ed_id`),
  UNIQUE KEY `IDX_EDITORS` (`ed_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `locations` (
  `loc_id` int(11) NOT NULL AUTO_INCREMENT,
  `loc_city` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`loc_id`),
  UNIQUE KEY `IDX_LOCATIONS` (`loc_city`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_level` int(11) NOT NULL,
  `role_description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `sessions` (
  `ses_id` int(11) NOT NULL AUTO_INCREMENT,
  `ses_userid` int(11) NOT NULL,
  `ses_created` datetime NOT NULL,
  `ses_expired` datetime NOT NULL,
  PRIMARY KEY (`ses_id`),
  KEY `IDX_USERID` (`ses_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `users` (
  `usr_id` int(11) NOT NULL AUTO_INCREMENT,
  `usr_firstname` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `usr_lastname` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `usr_email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `usr_address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `usr_password` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `usr_confirmpassword` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `usr_created` datetime NOT NULL,
  `usr_lastlogin` datetime DEFAULT NULL,
  `usr_confirmed` datetime DEFAULT NULL,
  PRIMARY KEY (`usr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `users_roles` (
  `ur_userid` int(11) NOT NULL,
  `ur_roleid` int(11) NOT NULL,
  PRIMARY KEY (`ur_userid`, `ur_roleid`),
  KEY `IDX_51498A8E67B3B43D` (`ur_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contraintes 
--
ALTER TABLE `books`
  ADD CONSTRAINT `FK_AUTHOR` FOREIGN KEY (`bk_author`) REFERENCES `authors` (`auth_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_EDITOR` FOREIGN KEY (`bk_editor`) REFERENCES `editors` (`ed_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_LOCATION` FOREIGN KEY (`bk_location`) REFERENCES `locations` (`loc_id`) ON DELETE CASCADE;

ALTER TABLE `sessions`
  ADD CONSTRAINT `FK_USER` FOREIGN KEY (`ses_userid`) REFERENCES `users` (`usr_id`) ON DELETE CASCADE;

ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK_51498A8E38C751C4` FOREIGN KEY (`ur_roleid`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_51498A8E67B3B43D` FOREIGN KEY (`ur_userid`) REFERENCES `users` (`usr_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
