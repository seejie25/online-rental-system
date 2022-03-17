-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: group19db
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property` (
  `property_id` int NOT NULL AUTO_INCREMENT,
  `property_name` varchar(200) NOT NULL,
  `num_room` int NOT NULL,
  `num_bathroom` int NOT NULL,
  `property_type` varchar(45) NOT NULL,
  `property_address` varchar(200) NOT NULL,
  `rental` int NOT NULL,
  `facilities` varchar(256) DEFAULT NULL,
  `property_status` varchar(45) NOT NULL,
  `owner_username` varchar(45) NOT NULL,
  `comment` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`property_id`),
  KEY `username` (`owner_username`),
  CONSTRAINT `username` FOREIGN KEY (`owner_username`) REFERENCES `user` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property`
--

LOCK TABLES `property` WRITE;
/*!40000 ALTER TABLE `property` DISABLE KEYS */;
INSERT INTO `property` VALUES (1,'Cyberjaya',5,7,'Single Storey','JALAN GR 1/6 , CASSIA, GARDEN RESIDENCE, 63000, Selangor',4200,'Air Conditioner, Gym, Playground','Active','fa',NULL),(2,'Isle of Kamares @ Setia Eco Glades, Cyberjaya',3,3,'Townhouse','Isle of Kamares, Setia Eco Glades, Isle of Kamares @ Setia Eco Glades, 63000, Selangor',3500,NULL,'Active','carmen','Good house'),(3,'Cassia @ Garden Residence',6,7,'Double Storey','Cyberjaya, Sepang, Selangor',3150,'Air Conditioner, Playground','Inactive','fa',NULL),(4,'Mirage By The Lake',3,5,'Double Storey','Persiaran Tasik, Cyberjaya, Cyberjaya, Sepang, Selango',2800,'Badminton Court, Playground, Swimming Pool, Gym','Active','fa',NULL),(5,'Laurel @ Laman main.View',4,3,'Double Storey','Jalan LV 4, Cyberjaya, Sepang, Selangor',2400,'Badminton Court, Playground, Gym','Active','lim',NULL),(6,'HYVE SOHO Suites Cyberjaya',1,1,'Others','HYVE SOHO Suites Jalan Impact, Cyber 6, Cyberjaya, Sepang, Selangor',1100,'Air Conditioner, Basketball Court, Playground, Swimming Pool, Gym','Active','carmen',NULL),(7,'LakeFront Residence @ Cyberjaya',6,7,'Bungalow','LakeFront Residence Persiaran Semarak Api, Cyberjaya, Sepang, Selangor',6500,'Playground, Swimming Pool','Inactive','loony',NULL),(8,'Cassia @ Garden Residence',7,7,'Double Storey','Cyberjaya, Sepang, Selangor',3000,'Playground','Inactive','carmen',NULL),(9,'Taman Pinggiran Cyberjaya, Selangor, Cyberjaya',3,2,'Single Storey','Taman Pinggiran Cyberjaya, Selangor, Selangor',1350,'Playground','Active','sia','Clean environment'),(10,'Cyberia Crescent 1',1,5,'Townhouse','Jalan Fauna 1 OFF Persiaran Multimedia, Cyberjaya, Cyberjaya, Sepang, Selangor',380,'Air Conditioner, Gym, Swimming Pool, Playground','Active','tee',NULL),(11,'Cyberia SmartHomes',1,3,'Townhouse','Persiaran Multimedia, Cyberjaya, Sepang, Selangor',400,'Air Conditioner, Playground, Swimming Pool, Gym','Inactive','sia',NULL),(12,'Cyberia SmartHomes',4,3,'Townhouse','Persiaran Multimedia, Cyberjaya, Sepang, Selangor',2200,'Air Conditioner, Playground, Swimming Pool, Gym','Active','loony',NULL),(13,'Ceria Residences, Cyberjaya',5,5,'Double Storey','Persiaran Sepang Persiaran Sepang, Cyberjaya, Sepang, Selangor',3000,'Air Conditioner, Badminton Court, Basketball Court, Gym, Swimming Pool','Active','carmen',NULL),(14,'Aspen @ Garden Residence',7,7,'Bungalow','Cyberjaya, Sepang, Selangor',15000,'Air Conditioner, Badminton Court, Gym, Swimming Pool','Active','sia',NULL),(15,'Maple Residence @ Laman main.View',4,4,'Double Storey','Jalan LV4, Cyberjaya, Sepang, Selangor',2450,'Basketball Court, Gym, Swimming Pool','Inactive','fa',NULL),(16,'Solstice @ Pagaea',1,1,'Condominium','Persiaran Bestari, Cyber 11, Cyberjaya, Sepang, Selangor',900,'Air Conditioner, Gym, Swimming Pool','Active','loony',NULL),(17,'Mozart in Symphony hills , Cyberjaya',5,4,'Single Storey','Jalan Mozart, Mozart, Symphony Hills, Mozart in Symphony hills , 63000, Selangor',4000,'Air Conditioner, Gym, Playground, Swimming Pool, Basketball Court','Active','fa',NULL),(18,'The Place @ Cyberjaya',1,1,'Condominium','1-1 Jalan Teknokrat 1/1, Cyberjaya, Cyberjaya, Sepang, Selangor',800,'Air Conditioner, Swimming Pool','Active','loony',NULL),(19,'Ceria Residences, Cyberjaya',5,5,'Double Storey','Persiaran Sepang Persiaran Sepang, Cyberjaya, Sepang, Selangor',2400,'Badminton Court, Basketball Court, Gym, Swimming Pool','Inactive','fa',NULL),(20,'Tamarind Suites @ Cyberjaya',1,1,'Others','Tamarind Square, Cyberjaya, Sepang, Selangor',1000,'Gym','Active','sia',NULL),(21,'Mozart, Symphony Hills, Cyberjaya',5,5,'Single Storey','Mozart, Symphony Hills, Mozart, Symphony Hills, 63000, Selangor',3500,'Air Conditioner, Playground','Inactive','fa',NULL),(22,'LakeFront Residence @ Cyberjaya',4,3,'Condominium','LakeFront Residence Persiaran Semarak Api, Cyberjaya, Sepang, Selangor',1700,'Air Conditioner, Playground, Swimming Pool','Active','loo',NULL),(23,'Third Avenue',1,1,'Others','Third Avenue Jalan Teknokrat 3, Cyber 4, Cyberjaya, Sepang, Selangor',950,'Air Conditioner, Gym, Swimming Pool','Inactive','tee',NULL),(24,'Samaya, Setia Eco Glades',4,4,'Double Storey','Cyberjaya, Sepang, Selangor',3200,'Playground, Gym, Swimming Pool','Inactive','carmen',NULL),(25,'Sejati Residences @ Cyberjaya',7,7,'Bungalow','No. 3 & 5 Jalan SL 2, Sejati Lakeside, Cyber 10, Cyberjaya, Sepang, Selangor',10000,'Air Conditioner, Gym, Swimming Pool, Playground','Active','carmen',NULL),(26,'Centrus SoHo 1 @ Cyberjaya',1,1,'Others','Lingkaran Cyber Point Timur Lingkaran Cyber Point Timur, Cyberjaya, Sepang, Selangor',900,'Air Conditioner, Playground, Gym, Swimming Pool','Active','tee',NULL),(27,'Cyberia SmartHomes',4,3,'Condominium','Persiaran Multimedia, Cyberjaya, Sepang, Selangor',1500,'Air Conditioner, Gym, Playground, Swimming Pool','Active','carmen',NULL),(28,'Liu Li Garden , Setia Eco Glades , Cyberjaya, Cyberjaya',4,4,'Single Storey','Liu Li Garden , Setia Eco Glades , Cyberjaya, 63000, Selangor',3000,NULL,'Active','tee',NULL),(29,'Eclipse Residence @ Pangaea',3,2,'Condominium','Persiaran Bestari Off Persiaran Multimedia, Pangaea, Cyber 11, Cyberjaya, Sepang, Selangor',1600,'Air Conditioner, Playground, Swimming Pool','Inactive','fa',NULL),(30,'Schumann, Symphony Hills, Cyberjaya',5,4,'Townhouse','Cyberjaya , Selangor, Schumann, Schubert, Beethoven, Mozart, Beethoven Symphony Hills, Schumann, Symphony Hills, 63000, Selangor',3800,NULL,'Active','carmen',NULL);
/*!40000 ALTER TABLE `property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `phone_number` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `user_type` varchar(45) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','admin','admin','admin','0123456789','admin@admin.com','Admin'),('carmen','1111','Tan','Carmen','0133355666','carmen@gmail.com','Agent'),('chang','1111','Zi Xuan','Chang','0145655666','chang@gmail.com','Tenant'),('fa','1111','Fa Fa','Lim','0135668866','fafa@hotlook.com','Agent'),('lim','1111','Jia Wei','Lim','987654321','jiawei@gmail.com','Owner'),('loo','1111','Xuan Kai','Loo','013454566','loo@yahoo.com','Owner'),('loony','1111','Loony','Tan','012545844','loony@hotmail.com','Agent'),('sia','1111','Annie','Sia','01546546','annie@yahoo.com','Agent'),('tee','1111','Wei Jie','Tee','0133355666','tee@gmail.com','Agent'),('viz','1111','Viz','Tan','0134445666','viz@gmail.com','Tenant');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-30 21:27:57
