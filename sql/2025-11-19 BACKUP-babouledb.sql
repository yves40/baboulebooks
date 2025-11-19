-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 19 nov. 2025 à 09:13
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
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_level` int(11) NOT NULL,
  `role_description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `roles`
--

INSERT INTO `roles` (`role_id`, `role_name`, `role_level`, `role_description`) VALUES
(1, 'ROLE_ADMINISTRATOR', 100, 'Agregates all admin roles'),
(2, 'ROLE_ADMIN', 90, 'Top level admin role, full privileges'),
(3, 'ROLE_ADMIN_SITE', 90, 'Top level admin role, full privileges'),
(4, 'ROLE_ADMIN_USERS', 80, 'Full privileges on user administration'),
(5, 'ROLE_ANONYMOUS', 10, 'Any user can access public features ');

-- --------------------------------------------------------

--
-- Structure de la table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
CREATE TABLE IF NOT EXISTS `sessions` (
  `ses_id` int(11) NOT NULL AUTO_INCREMENT,
  `ses_userid` int(11) NOT NULL,
  `ses_created` datetime NOT NULL,
  `ses_expired` datetime NOT NULL,
  PRIMARY KEY (`ses_id`),
  KEY `IDX_USERID` (`ses_userid`)
) ENGINE=InnoDB AUTO_INCREMENT=7365 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `sessions`
--

INSERT INTO `sessions` (`ses_id`, `ses_userid`, `ses_created`, `ses_expired`) VALUES
(7363, 3, '2025-11-19 09:33:16', '2025-11-19 10:33:16'),
(7364, 3463, '2025-11-19 10:10:55', '2025-11-19 11:10:55');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `usr_id` int(11) NOT NULL AUTO_INCREMENT,
  `usr_firstname` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `usr_lastname` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `usr_email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `usr_address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `usr_password` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `usr_created` datetime NOT NULL,
  `usr_lastlogin` datetime DEFAULT NULL,
  `usr_confirmed` datetime DEFAULT NULL,
  PRIMARY KEY (`usr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3464 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`usr_id`, `usr_firstname`, `usr_lastname`, `usr_email`, `usr_address`, `usr_password`, `usr_created`, `usr_lastlogin`, `usr_confirmed`) VALUES
(1, 'Yves', 'Toubhans', 'yves77340@gmail.com', 'La Lune 3ème étage', '$2y$13$RfzSlQAuYMnE.y8UQmDGd.npVrhWyfHU79.kroq3VPzE08y7tdcN6', '2023-12-27 11:31:14', NULL, '2023-12-27 11:31:14'),
(2, 'Benjamin', 'Toubhans', 'benjamin.toubhans@orange.fr', 'Mars with Elon', '$2y$13$facWuwrJqbWlSx5AuEwzPuOT9CHOZx2cNdELS.RV4q59RQD1mWTOC', '2023-12-27 11:31:16', NULL, '2023-12-27 11:31:16'),
(3, 'Isabelle', 'Toubhans', 'i.toubhans@free.fr', 'Pluton with Jeff', '$2y$13$2SHpYFVbciL2XwmSa5Ew6.C11JQhUMe.kPzAhlGeAvNQVQudep1Qe', '2023-12-27 11:31:17', NULL, '2023-12-27 11:31:17'),
(9, 'Bastien', 'Toubhans', 'bastien.toubhans@free.fr', '15 rue de Lyon 63250 Chabreloche', '$2y$13$bykthGOyYqdpEvNrRxqukeLyizTUt6Ud6.Vl4VB1VLORycmqD4/HC', '2023-12-27 15:08:23', NULL, '2023-12-27 15:08:23'),
(3463, 'Yves', 'Toubhans', 'yvesz@free.fr', NULL, '$2b$10$gFhR2UUXXuo7hkbQTLtdTO87j5YH53DaKX0cFH3M8SHRuPytP4lRO', '2025-11-19 10:03:46', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE IF NOT EXISTS `users_roles` (
  `ur_userid` int(11) NOT NULL,
  `ur_roleid` int(11) NOT NULL,
  PRIMARY KEY (`ur_userid`,`ur_roleid`),
  KEY `IDX_51498A8E67B3B43D` (`ur_userid`),
  KEY `FK_51498A8E38C751C4` (`ur_roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `users_roles`
--

INSERT INTO `users_roles` (`ur_userid`, `ur_roleid`) VALUES
(3463, 5);

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
-- Contraintes pour la table `sessions`
--
ALTER TABLE `sessions`
  ADD CONSTRAINT `FK_USER` FOREIGN KEY (`ses_userid`) REFERENCES `users` (`usr_id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK_51498A8E38C751C4` FOREIGN KEY (`ur_roleid`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_51498A8E67B3B43D` FOREIGN KEY (`ur_userid`) REFERENCES `users` (`usr_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
