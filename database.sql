-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: market place
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `FName` varchar(255) DEFAULT NULL,
  `LName` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ID` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `balance` float NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin`
VALUES ('Joshua','Garth','admin@emarket.net','admin1234',1,7401.25);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `qty` int NOT NULL,
  `cart_id` int NOT NULL,
  `product_ID` mediumint unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`cart_id`,`product_ID`),
  KEY `product_ID` (`product_ID`),
  CONSTRAINT `cart_item_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `client` (`cart_id`),
  CONSTRAINT `cart_item_ibfk_2` FOREIGN KEY (`product_ID`) REFERENCES `products` (`product_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=968221 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item`
VALUES (4,5,4),
(3,5,6),
(3,6,50),
(2,8,1),
(20,10,5),
(3,14,50),
(4,16,2),
(3,17,50);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `FName` varchar(255) NOT NULL,
  `LName` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `Mobile` varchar(100) DEFAULT NULL,
  `amount_of_money` float DEFAULT NULL,
  `BDay` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cart_id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Email`),
  UNIQUE KEY `cart_id_UNIQUE` (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client`
VALUES ('Claire','Bassem','claire@gmail.com','koko1234','01277089618',6940,'23-10-1999','F','heliopolis',15),
('Teegan','Brenden','donec.non@google.couk','YYL02UVV8WG','(584) 218-2715',6029,'25-02-23','\'F\'','Ap #737-6657 Neque. Ave',1),
('Cole','Hope','dui@aol.ca','XWN68XHG3VV','1-345-681-2629',7077,'20-06-21','\'M\'','676-3452 Nibh. Street',2),
('Tyler','Renee','eu.dui@icloud.couk','JFF27TOF9NG','(430) 443-4798',6138,'02-12-22','\'M\'','833-2295 Et, Avenue',3),
('Cain','Garrett','facilisis.magna@protonmail.edu','PHP18NOY4CM','(642) 883-5136',6688,'17-11-22','\'F\'','Ap #512-8428 Et, Rd.',4),
('Farah','Ihab','foffa@gmail.com','foffa1234','01200762343',3000,'25-08-1999','F','masr el gedida el dor el awal',17),
('john','magdy','john@gmail.com','haha1234','01288485284',100000000000,'16/1/2000','M','el zatoun el dor el talet',5),
('karen','bassem','karen@gmail.com','123456458','01329865189',999919,'05-05-2022','F','masr',13),
('Kiro','Amgad','kiro@hotmail.com','kiro1234','01202749339',999725,'12-05-1999','M','rehab',8),
('Mathew','Ehab','M@gmail.com','mathew1234','01202548926',5600,'16/10/1999','M','masr el gedida el dor el tani',6),
('Mariz','Ghaly','mariz@gmail.com','12345678','01065845875',6127.38,'01-01-2000','F','obour',10),
('Mark','George','mark@gmail.com','mark1234','01258974869',1000370,'01-05-2022','M','masr el gedida el dor el 5ames',14),
('Mayar','El-Mallah','mayar@gmail.com','mayar1234','01141863698',9879.22,'30-03-2000','F','el abbaseya',16),
('Angelica','India','ut.lacus@icloud.com','PCJ24EKY7XB','1-448-362-6516',5822,'28-12-22','\'F\'','Ap #337-1961 Mauris Ave',7);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(45) NOT NULL,
  `price` float NOT NULL,
  `client_email` varchar(255) NOT NULL,
  `date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `client_email` (`client_email`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`client_email`) REFERENCES `client` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order`
VALUES (6,'el zatoun el dor el talet',90,'john@gmail.com','2022-05-25 17:16:54.87'),
(7,'el zatoun el dor el talet',401.89,'john@gmail.com','2022-05-27 17:16:54.87'),
(8,'el zatoun el dor el talet',125.43,'john@gmail.com','2022-05-26 17:16:54.87'),
(9,'masr el gedida el dor el 5ames',134.04,'mark@gmail.com','2022-05-28 17:16:54.87'),
(10,'obour',208.36,'mariz@gmail.com','2022-05-30 17:16:54.87'),
(11,'obour',339.19,'mariz@gmail.com','2022-05-29 17:16:54.87'),
(12,'el zatoun el dor el talet',80,'john@gmail.com','2022-06-19 21:44:45.956'),
(13,'rehab',181.22,'kiro@hotmail.com','2022-06-20 00:05:54.033'),
(14,'rehab',85,'kiro@hotmail.com','2022-06-20 03:16:47.842'),
(15,'rehab',40,'kiro@hotmail.com','2022-06-20 03:22:09.584'),
(16,'el abbaseya',30,'mayar@gmail.com','2022-06-21 00:46:18.782'),
(17,'el abbaseya',100.78,'mayar@gmail.com','2022-06-21 00:47:54.249'),
(18,'rehab',188.35,'kiro@hotmail.com','2022-06-21 06:57:21.275'),
(19,'obour',640.07,'mariz@gmail.com','2022-06-26 15:36:56.72'),
(20,'rehab',30,'kiro@hotmail.com','2022-06-26 15:39:03.574'),
(21,'rehab',30,'kiro@hotmail.com','2022-06-26 15:41:33.104'),
(22,'rehab',20.87,'kiro@hotmail.com','2022-06-26 15:42:06.426'),
(23,'obour',2835,'mariz@gmail.com','2022-06-26 15:44:44.685'),
(24,'rehab',810,'kiro@hotmail.com','2022-06-26 15:46:10.663'),
(25,'heliopolis',60,'claire@gmail.com','2022-06-26 16:22:48.275'),
(26,'el zatoun el dor el talet',500,'john@gmail.com','2022-06-27 16:15:32.34'),
(27,'el zatoun el dor el talet',116.55,'john@gmail.com','2022-06-27 16:19:01.859'),
(28,'el zatoun el dor el talet',647.5,'john@gmail.com','2022-06-27 19:21:32.063'),
(29,'el zatoun el dor el talet',314,'john@gmail.com','2022-06-28 23:05:57.014');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `price` float NOT NULL,
  `qty` int NOT NULL,
  `order_id` int NOT NULL,
  `product_ID` mediumint unsigned NOT NULL,
  PRIMARY KEY (`order_id`,`product_ID`),
  KEY `order_item_ibfk_2` (`product_ID`),
  CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`product_ID`) REFERENCES `products` (`product_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item`
VALUES (40,2,6,50),
(50,10,6,458),
(90.21,3,7,38031),
(311.68,4,7,563471),
(40,2,8,50),
(20,4,8,458),
(65.43,1,8,144115),
(20,1,9,50),
(20,4,9,458),
(35,2,9,233575),
(59.04,3,9,968220),
(60,3,10,50),
(130.86,2,10,144115),
(17.5,1,10,233575),
(40,2,11,50),
(65.43,1,11,144115),
(233.76,3,11,563471),
(80,4,12,50),
(40,2,13,50),
(10,2,13,458),
(52.5,3,13,233575),
(78.72,4,13,968220),
(15,3,14,458),
(70,4,14,233575),
(40,2,15,50),
(30,1,16,1),
(41.74,2,17,2),
(59.04,3,17,968220),
(60,2,18,1),
(104.35,5,18,2),
(24,2,18,4),
(40,2,19,50),
(90.21,3,19,38031),
(392.58,6,19,144115),
(77.92,1,19,563471),
(39.36,2,19,968220),
(30,1,20,1),
(30,1,21,1),
(20.87,1,22,2),
(2835,70,23,5),
(810,20,24,5),
(60,3,25,50),
(500,20,26,1),
(80.55,9,27,2),
(36,6,27,844741),
(150,6,28,1),
(53.7,6,28,2),
(125.65,7,28,3),
(29.4,6,28,4),
(288.75,7,28,6),
(44.75,5,29,2),
(241.75,5,29,5),
(27.5,1,29,38031);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `quantity` mediumint DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_ID` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `img_src` varchar(255) NOT NULL,
  `Theme_color` varchar(255) NOT NULL,
  PRIMARY KEY (`product_ID`),
  UNIQUE KEY `product_name` (`product_name`)
) ENGINE=InnoDB AUTO_INCREMENT=968221 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products`
VALUES (69,'Fish',25,'Tuna',1,'/img/tuna.png','87CEFA'),
(72,'Fruits',8.95,'Strawberry',2,'/img/strawberry.png','FFEFD5'),
(93,'Fruits',17.95,'Watermelon',3,'/img/watermelon.png','FFDAB9'),
(92,'Snacks',4.9,'Indomie',4,'/img/indomie.png','98FB98'),
(5,'Sauces',48.35,'Ketchup',5,'/img/ketchup.png','FAEBD7'),
(93,'Fruites',41.25,'Orange',6,'/img/orange.png','32CD32'),
(1,'Fruits',39.95,'Banana',50,'/img/banana.png','FFA500'),
(17,'Fruits',92.95,'Pineapple',458,'/img/Pineapple.png','FFFF00'),
(74,'Fruits',27.5,'Mango',38031,'/img/mango.png','FF8C00'),
(50,'dairy',28.75,'Chocolate',144115,'/img/chocolate.png','CD853F'),
(54,'Snacks',5,'Chipsey',233575,'/img/chipsey.png','D3D3D3'),
(50,'Fruits',29.95,'Grapes',557848,'/img/grapes.png','FFE4E1'),
(61,'Fruits',16.95,'Peach',563471,'/img/peach.png','FF4500'),
(94,'Beverages',6,'Pepsi',844741,'/img/pepsi.png','F5FFFA'),
(78,'Fruits',24.95,'Kiwi',968220,'/img/kiwi.png','98FB98');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-29  0:42:44
