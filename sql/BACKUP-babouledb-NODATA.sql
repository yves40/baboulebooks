-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 03 oct. 2025 à 16:36
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

-- --------------------------------------------------------

--
-- Structure de la table `authors`
--

DROP TABLE IF EXISTS `authors`;
CREATE TABLE IF NOT EXISTS `authors` (
  `auth_id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_fname` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `auth_lname` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`auth_id`),
  UNIQUE KEY `IDX_AUTHORS` (`auth_lname`,`auth_fname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `books`
--

DROP TABLE IF EXISTS `books`;
CREATE TABLE IF NOT EXISTS `books` (
  `bk_id` int(11) NOT NULL AUTO_INCREMENT,
  `bk_title` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bk_author` int(11) DEFAULT NULL,
  `bk_location` int(11) DEFAULT NULL,
  `bk_editor` int(11) DEFAULT NULL,
  PRIMARY KEY (`bk_id`),
  UNIQUE KEY `IDX_BOOKS` (`bk_title`) USING BTREE,
  KEY `FK_AUTHOR` (`bk_author`),
  KEY `FK_EDITOR` (`bk_editor`),
  KEY `FK_LOCATION` (`bk_location`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `editors`
--

DROP TABLE IF EXISTS `editors`;
CREATE TABLE IF NOT EXISTS `editors` (
  `ed_id` int(11) NOT NULL AUTO_INCREMENT,
  `ed_name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ed_id`),
  UNIQUE KEY `IDX_EDITORS` (`ed_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `locations`
--

DROP TABLE IF EXISTS `locations`;
CREATE TABLE IF NOT EXISTS `locations` (
  `loc_id` int(11) NOT NULL AUTO_INCREMENT,
  `loc_city` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`loc_id`),
  UNIQUE KEY `IDX_LOCATIONS` (`loc_city`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `level` int(11) NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastname` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created` datetime NOT NULL,
  `lastlogin` datetime DEFAULT NULL,
  `confirmpassword` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `confirmed` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE IF NOT EXISTS `users_roles` (
  `users_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  PRIMARY KEY (`users_id`,`roles_id`),
  KEY `IDX_51498A8E67B3B43D` (`users_id`),
  KEY `IDX_51498A8E38C751C4` (`roles_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `FK_AUTHOR` FOREIGN KEY (`bk_author`) REFERENCES `authors` (`auth_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_EDITOR` FOREIGN KEY (`bk_editor`) REFERENCES `editors` (`ed_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_LOCATION` FOREIGN KEY (`bk_location`) REFERENCES `locations` (`loc_id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK_51498A8E38C751C4` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_51498A8E67B3B43D` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
