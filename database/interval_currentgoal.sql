-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: interval
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `currentgoal`
--

DROP TABLE IF EXISTS `currentgoal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `currentgoal` (
  `goalID` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `deadline` datetime DEFAULT NULL,
  `priority` int unsigned DEFAULT '0',
  PRIMARY KEY (`goalID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currentgoal`
--

LOCK TABLES `currentgoal` WRITE;
/*!40000 ALTER TABLE `currentgoal` DISABLE KEYS */;
INSERT INTO `currentgoal` VALUES (1,'yoga','Do yoga for 30 minutes.','2021-10-11 17:00:00',3),(2,'ride bike','Go on a 15 mile bike ride around the city.','2021-10-11 13:00:00',5),(3,'groceries','Go buy groceries for this week\'s dinner. Make sure to get fruit and cheese for soccer practice.','2021-10-11 10:00:00',6),(4,'meditate','Do guided meditation for 20 minutes.','2021-10-11 22:00:00',3),(5,'walk','Go on a walk around the park.','2021-10-11 14:00:00',4),(6,'read','Go to the library and find a book. Read for at least 30 minutes. ','2021-10-11 13:00:00',3),(7,'swim','Go to the gym and swim laps in the pool for at least 45 minutes.','2021-10-11 18:00:00',5),(8,'dentist appointment','Make a dentist appointment.','2021-10-11 15:00:00',2),(9,'movie','Go see a movie with friends.','2021-10-11 23:00:00',4),(10,'rennovation','Schedule an appointment for a rennovation quote.','2021-10-11 12:00:00',8);
/*!40000 ALTER TABLE `currentgoal` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-11 14:22:51
