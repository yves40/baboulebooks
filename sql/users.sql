-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 21 sep. 2025 à 08:53
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
-- Base de données : `bomerledb`
--

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `firstname`, `lastname`, `email`, `address`, `password`, `created`, `lastlogin`, `confirmpassword`, `confirmed`) VALUES
(1, 'Yves', 'Toubhans', 'yves77340@gmail.com', 'La Lune 3ème étage', '$2y$13$RfzSlQAuYMnE.y8UQmDGd.npVrhWyfHU79.kroq3VPzE08y7tdcN6', '2023-12-27 11:31:14', NULL, '$2y$13$mOiY9hiGGAXZQyD5rEReZutZXmPUZ1f/zojrPiLzvXN3TCVUBih0i', '2023-12-27 11:31:14'),
(2, 'Benjamin', 'Toubhans', 'benjamin.toubhans@orange.fr', 'Mars with Elon', '$2y$13$facWuwrJqbWlSx5AuEwzPuOT9CHOZx2cNdELS.RV4q59RQD1mWTOC', '2023-12-27 11:31:16', NULL, '$2y$13$GpxSSHwqYh7FqGr/wwdK5uYz5PA.25t41ebVphZq1EBxb5YszOL5q', '2023-12-27 11:31:16'),
(3, 'Isabelle', 'Toubhans', 'i.toubhans@free.fr', 'Pluton with Jeff', '$2y$13$2SHpYFVbciL2XwmSa5Ew6.C11JQhUMe.kPzAhlGeAvNQVQudep1Qe', '2023-12-27 11:31:17', NULL, '$2y$13$2SHpYFVbciL2XwmSa5Ew6.C11JQhUMe.kPzAhlGeAvNQVQudep1Qe', '2023-12-27 11:31:17'),
(9, 'Bastien', 'Toubhans', 'bastien.toubhans@free.fr', '15 rue de Lyon 63250 Chabreloche', '$2y$13$bykthGOyYqdpEvNrRxqukeLyizTUt6Ud6.Vl4VB1VLORycmqD4/HC', '2023-12-27 15:08:23', NULL, '$2y$13$bykthGOyYqdpEvNrRxqukeLyizTUt6Ud6.Vl4VB1VLORycmqD4/HC', '2023-12-27 15:08:23');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
