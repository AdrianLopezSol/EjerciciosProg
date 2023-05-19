/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para cine
DROP DATABASE IF EXISTS `imdb`;
CREATE DATABASE `imdb` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci */;
USE `imdb`;

-- directors definition

CREATE TABLE `directors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imdb_id` varchar(10) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `birthYear` int(4) DEFAULT NULL,
  `deathYear` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `directors_imdb_id_IDX` (`imdb_id`) USING BTREE,
  UNIQUE KEY `directors_UN` (`imdb_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- imdb.actors definition

CREATE TABLE `actors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imdb_id` varchar(10) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `birthYear` int(4) DEFAULT NULL,
  `deathYear` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `actors_UN` (`imdb_id`),
  UNIQUE KEY `actors_imdb_id_IDX` (`imdb_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- movies definition

CREATE TABLE `movies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imdb_id` varchar(10) NOT NULL,
  `title` varchar(57) DEFAULT NULL,
  `year` int(4) DEFAULT NULL,
  `image` varchar(161) DEFAULT NULL,
  `runtime` int(4) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `director_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `movies_UN` (`imdb_id`),
  UNIQUE KEY `movies_imdb_id_IDX` (`imdb_id`) USING BTREE,
  KEY `movies_FK` (`director_id`),
  CONSTRAINT `movies_FK` FOREIGN KEY (`director_id`) REFERENCES `directors` (`imdb_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- imdb.actors_movies definition

CREATE TABLE `actors_movies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `movie_id` varchar(10) NOT NULL,
  `actor_id` varchar(10) NOT NULL,
  `characters` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `actors_movies_FK` (`actor_id`),
  KEY `actors_movies_FK2` (`movie_id`),
  CONSTRAINT `actors_movies_FK2` FOREIGN KEY (`actor_id`) REFERENCES `actors` (`imdb_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `actors_movies_FK` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`imdb_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
