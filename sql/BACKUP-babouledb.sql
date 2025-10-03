-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 03 oct. 2025 à 16:35
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
) ENGINE=InnoDB AUTO_INCREMENT=9594 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `authors`
--

INSERT INTO `authors` (`auth_id`, `auth_fname`, `auth_lname`) VALUES
(9573, 'Olivier', 'ADAM'),
(9572, 'Jeffrey', 'ARCHER'),
(9586, 'Tahar', 'BEN JELLOUN'),
(9581, 'Yves', 'CANNAC'),
(9577, 'André', 'CASTELOT'),
(9569, 'Philippe', 'CLAUDEL'),
(9560, 'Harlan', 'COBEN'),
(9582, 'Jean-marie/jean-yves', 'COOLOMNANI/LHOMEAU'),
(9579, 'Christine', 'DE RIVOYRE'),
(9564, 'Lionel', 'DUROY'),
(9593, 'Jean-luois', 'ETIENNE'),
(9568, 'Luc', 'FERRY'),
(9571, 'Susan', 'FORWARD'),
(9561, 'Claudie', 'GALLAY'),
(9565, 'Laurent', 'GAUDE'),
(9583, 'Maurice', 'GENEVOIX'),
(9590, 'Marguerite', 'GENTZBITTEL'),
(9576, 'Jean', 'JAMET'),
(9570, 'Nathalie', 'KUPERMAN'),
(9558, 'Camilla', 'LÄCKBERG'),
(9574, 'Patrick', 'LAPEYRE'),
(9567, 'Carole', 'MARTINEZ'),
(9584, 'Robert', 'MERLE'),
(9588, 'Patrick', 'MODIANO'),
(9556, 'Yann', 'MOIX'),
(9580, 'Catherine', 'NAY'),
(9563, 'Sofi', 'OKSANEN'),
(9592, 'Katherine', 'PANCOL'),
(9589, 'Nean', 'REY'),
(9566, 'Michel', 'ROSTAIN'),
(9559, 'Jean-christophe', 'RUFIN'),
(9575, 'Luis', 'SEPULVADA'),
(9578, 'Christian', 'SIGNOL'),
(9562, 'Dai', 'SIJIE'),
(9557, 'Vikas', 'SWARUP'),
(9591, 'Vladimir', 'VOLKOFF'),
(9585, 'Eugen', 'WEBER');

-- --------------------------------------------------------

--
-- Structure de la table `books`
--

DROP TABLE IF EXISTS `books`;
CREATE TABLE IF NOT EXISTS `books` (
  `bk_id` int(11) NOT NULL AUTO_INCREMENT,
  `bk_title` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bk_author` int(11) DEFAULT NULL,
  `bk_location` int(11) DEFAULT NULL,
  `bk_editor` int(11) DEFAULT NULL,
  PRIMARY KEY (`bk_id`),
  UNIQUE KEY `IDX_BOOKS` (`bk_title`) USING BTREE,
  KEY `FK_AUTHOR` (`bk_author`),
  KEY `FK_EDITOR` (`bk_editor`),
  KEY `FK_LOCATION` (`bk_location`)
) ENGINE=InnoDB AUTO_INCREMENT=492 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `books`
--

INSERT INTO `books` (`bk_id`, `bk_title`, `bk_author`, `bk_location`, `bk_editor`) VALUES
(454, 'Naissance', 9556, 9435, 808),
(455, 'Meutre dans un jardin indien', 9557, 9435, 809),
(456, 'Le prédicateur', 9558, 9435, 810),
(457, 'Katiba', 9559, 9435, 811),
(458, 'Sans laisser d\'adresse', 9560, 9435, 809),
(459, 'L\'amour est une île', 9561, 9435, 810),
(460, 'Trois vies chinoises', 9562, 9435, 811),
(461, 'Purge', 9563, 9435, 815),
(462, 'Le chagrin', 9564, 9435, 816),
(463, 'Ouragan', 9565, 9435, 810),
(464, 'Le fils', 9566, 9435, 818),
(465, 'Le cœur cousu', 9567, 9435, 819),
(466, 'La révolution de l\'amour', 9568, 9435, 820),
(467, 'L\'enquête', 9569, 9435, 815),
(468, 'Nous étions des êtres vivants', 9570, 9435, 822),
(469, 'Parents toxiques', 9571, 9435, 823),
(470, 'Only Time Will Tell', 9572, 9435, 824),
(471, 'Les lisières', 9573, 9435, 811),
(472, 'La vie est brève et le désir sans fin', 9574, 9435, 826),
(473, 'L\'ombre de ce que nous avons été', 9575, 9435, 827),
(474, 'Une parole étouffée', 9576, 9436, 828),
(475, 'Au fil de l\'histoire', 9577, 9436, 829),
(476, 'La rivière espérance', 9578, 9436, 830),
(477, 'Belle alliance', 9579, 9436, 808),
(478, 'Le Noir et le Rouge', 9580, 9436, 808),
(479, 'Le juste pouvoir', 9581, 9436, 833),
(480, 'Le mariage blanc', 9582, 9436, 808),
(481, 'Lorelei', 9583, 9436, 835),
(482, 'Malevol', 9584, 9436, 822),
(483, 'La fin des terroirs', 9585, 9436, 837),
(484, 'L\'enfant de sable', 9586, 9436, 835),
(485, 'La nuit sacrée', 9586, 9436, 835),
(486, 'Remise de peine', 9588, 9436, 835),
(487, 'Africa blues', 9589, 9436, 841),
(488, 'Madame le proviseur', 9590, 9436, 835),
(489, 'Le montage', 9591, 9436, 816),
(490, 'J\'étais là avant', 9592, 9436, 844),
(491, 'Le marcheur du pôle', 9593, 9436, 830);

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
) ENGINE=InnoDB AUTO_INCREMENT=846 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `editors`
--

INSERT INTO `editors` (`ed_id`, `ed_name`) VALUES
(810, 'ACTES SUD'),
(844, 'ALBIN MICHEL'),
(809, 'BELFOND'),
(837, 'FAYARD'),
(811, 'FLAMMARION'),
(819, 'FOLIO'),
(829, 'FRANCE LOISIRS'),
(822, 'GALLIMARD'),
(808, 'GRASSET'),
(833, 'JC LATTES'),
(816, 'JULLIARD'),
(841, 'MANYA'),
(823, 'MARABOUT'),
(827, 'MÉTAILLIÉ'),
(818, 'OH EDITIONS'),
(826, 'P.O.L'),
(824, 'PAN'),
(820, 'PLON'),
(828, 'RAMSAY'),
(830, 'ROBERT LAFFONT'),
(835, 'SEUIL'),
(815, 'STOCK');

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
) ENGINE=InnoDB AUTO_INCREMENT=9437 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `locations`
--

INSERT INTO `locations` (`loc_id`, `loc_city`) VALUES
(9436, 'Hossegor'),
(9435, 'Pontault');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `roles`
--

INSERT INTO `roles` (`id`, `name`, `level`, `description`) VALUES
(1, 'ROLE_ADMINISTRATOR', 100, 'Agregates all admin roles, look at security.yaml'),
(2, 'ROLE_ADMIN', 90, 'Top level admin role, full privileges'),
(3, 'ROLE_ADMIN_SITE', 90, 'Top level admin role, full privileges'),
(4, 'ROLE_ADMIN_USERS', 80, 'Full privileges on user administration'),
(5, 'ROLE_ANONYMOUS', 10, 'Any user can access public features ');

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `firstname`, `lastname`, `email`, `address`, `password`, `created`, `lastlogin`, `confirmpassword`, `confirmed`) VALUES
(1, 'Yves', 'Toubhans', 'yves77340@gmail.com', 'La Lune 3ème étage', '$2y$13$RfzSlQAuYMnE.y8UQmDGd.npVrhWyfHU79.kroq3VPzE08y7tdcN6', '2023-12-27 11:31:14', NULL, '$2y$13$mOiY9hiGGAXZQyD5rEReZutZXmPUZ1f/zojrPiLzvXN3TCVUBih0i', '2023-12-27 11:31:14'),
(2, 'Benjamin', 'Toubhans', 'benjamin.toubhans@orange.fr', 'Mars with Elon', '$2y$13$facWuwrJqbWlSx5AuEwzPuOT9CHOZx2cNdELS.RV4q59RQD1mWTOC', '2023-12-27 11:31:16', NULL, '$2y$13$GpxSSHwqYh7FqGr/wwdK5uYz5PA.25t41ebVphZq1EBxb5YszOL5q', '2023-12-27 11:31:16'),
(3, 'Isabelle', 'Toubhans', 'i.toubhans@free.fr', 'Pluton with Jeff', '$2y$13$2SHpYFVbciL2XwmSa5Ew6.C11JQhUMe.kPzAhlGeAvNQVQudep1Qe', '2023-12-27 11:31:17', NULL, '$2y$13$2SHpYFVbciL2XwmSa5Ew6.C11JQhUMe.kPzAhlGeAvNQVQudep1Qe', '2023-12-27 11:31:17'),
(9, 'Bastien', 'Toubhans', 'bastien.toubhans@free.fr', '15 rue de Lyon 63250 Chabreloche', '$2y$13$bykthGOyYqdpEvNrRxqukeLyizTUt6Ud6.Vl4VB1VLORycmqD4/HC', '2023-12-27 15:08:23', NULL, '$2y$13$bykthGOyYqdpEvNrRxqukeLyizTUt6Ud6.Vl4VB1VLORycmqD4/HC', '2023-12-27 15:08:23');

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
-- Déchargement des données de la table `users_roles`
--

INSERT INTO `users_roles` (`users_id`, `roles_id`) VALUES
(1, 1),
(1, 5),
(2, 4),
(2, 5),
(3, 3),
(3, 5),
(9, 1),
(9, 5);

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
