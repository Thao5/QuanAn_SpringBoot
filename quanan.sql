-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: quanan
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `ban`
--

DROP TABLE IF EXISTS `ban`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ban` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mo_ta` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `id_chi_nhanh` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_chi_nhanh` (`id_chi_nhanh`),
  CONSTRAINT `ban_ibfk_1` FOREIGN KEY (`id_chi_nhanh`) REFERENCES `chi_nhanh` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ban`
--

LOCK TABLES `ban` WRITE;
/*!40000 ALTER TABLE `ban` DISABLE KEYS */;
INSERT INTO `ban` VALUES (2,'4 chỗ ngồi, gần cửa sổ','2023-10-02 07:00:00',4);
/*!40000 ALTER TABLE `ban` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (2,'Thức ăn');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chi_nhanh`
--

DROP TABLE IF EXISTS `chi_nhanh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chi_nhanh` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dia_chi` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `id_nguoi_dung` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_nguoi_dung` (`id_nguoi_dung`),
  CONSTRAINT `chi_nhanh_ibfk_1` FOREIGN KEY (`id_nguoi_dung`) REFERENCES `nguoi_dung` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_nhanh`
--

LOCK TABLES `chi_nhanh` WRITE;
/*!40000 ALTER TABLE `chi_nhanh` DISABLE KEYS */;
INSERT INTO `chi_nhanh` VALUES (4,'123/asdas','2023-09-27 07:00:00','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696363678/gly3bnsfhkepqe4ggzfr.jpg',1),(6,'123/asdasad','2023-09-29 22:57:41','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1691990852/uyaxwbdtxbrrefc3qt7j.png',2),(7,'sadasdasd','2023-09-29 23:05:24','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1691990852/uyaxwbdtxbrrefc3qt7j.png',1),(8,'asdasdasdas','2023-09-29 23:12:15','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1691990852/uyaxwbdtxbrrefc3qt7j.png',1),(9,'sadasd','2023-09-30 00:04:59','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1691990852/uyaxwbdtxbrrefc3qt7j.png',1),(10,'asdasdsa','2023-09-30 00:09:26','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1691990852/uyaxwbdtxbrrefc3qt7j.png',1),(11,'sadasdsad','2023-10-02 02:15:04','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696187706/wkp4cghymt3q9xclxstk.png',1);
/*!40000 ALTER TABLE `chi_nhanh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `danh_gia`
--

DROP TABLE IF EXISTS `danh_gia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `danh_gia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `noi_dung` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `danh_gia` int DEFAULT NULL,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `id_nguoi_dung` int DEFAULT NULL,
  `id_chi_nhanh` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_nguoi_dung` (`id_nguoi_dung`),
  KEY `id_chi_nhanh` (`id_chi_nhanh`),
  CONSTRAINT `danh_gia_ibfk_1` FOREIGN KEY (`id_nguoi_dung`) REFERENCES `nguoi_dung` (`id`),
  CONSTRAINT `danh_gia_ibfk_2` FOREIGN KEY (`id_chi_nhanh`) REFERENCES `chi_nhanh` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `danh_gia`
--

LOCK TABLES `danh_gia` WRITE;
/*!40000 ALTER TABLE `danh_gia` DISABLE KEYS */;
INSERT INTO `danh_gia` VALUES (1,'đẹp',5,'2023-10-03 07:00:00',3,4);
/*!40000 ALTER TABLE `danh_gia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoa_don`
--

DROP TABLE IF EXISTS `hoa_don`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `id_nguoi_dung` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_nguoi_dung` (`id_nguoi_dung`),
  CONSTRAINT `hoa_don_ibfk_1` FOREIGN KEY (`id_nguoi_dung`) REFERENCES `nguoi_dung` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don`
--

LOCK TABLES `hoa_don` WRITE;
/*!40000 ALTER TABLE `hoa_don` DISABLE KEYS */;
INSERT INTO `hoa_don` VALUES (2,'2023-10-03 07:00:00',3);
/*!40000 ALTER TABLE `hoa_don` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoa_don_chi_tiet`
--

DROP TABLE IF EXISTS `hoa_don_chi_tiet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don_chi_tiet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `so_luong_mua` int DEFAULT NULL,
  `gia_van_chuyen` decimal(10,0) DEFAULT NULL,
  `tong_tien` decimal(10,0) DEFAULT NULL,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `id_hoa_don` int DEFAULT NULL,
  `id_thuc_an` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_hoa_don` (`id_hoa_don`),
  KEY `id_thuc_an` (`id_thuc_an`),
  CONSTRAINT `hoa_don_chi_tiet_ibfk_1` FOREIGN KEY (`id_hoa_don`) REFERENCES `hoa_don` (`id`),
  CONSTRAINT `hoa_don_chi_tiet_ibfk_2` FOREIGN KEY (`id_thuc_an`) REFERENCES `thuc_an` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don_chi_tiet`
--

LOCK TABLES `hoa_don_chi_tiet` WRITE;
/*!40000 ALTER TABLE `hoa_don_chi_tiet` DISABLE KEYS */;
INSERT INTO `hoa_don_chi_tiet` VALUES (2,3,0,30000,'2023-10-03 15:55:02',2,1);
/*!40000 ALTER TABLE `hoa_don_chi_tiet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoa_don_chi_tiet_tai_cho`
--

DROP TABLE IF EXISTS `hoa_don_chi_tiet_tai_cho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don_chi_tiet_tai_cho` (
  `id` int NOT NULL AUTO_INCREMENT,
  `so_luong_mua` int DEFAULT NULL,
  `tong_tien` decimal(10,0) DEFAULT NULL,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `id_hoa_don` int DEFAULT NULL,
  `id_thuc_an` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_hoa_don` (`id_hoa_don`),
  KEY `id_thuc_an` (`id_thuc_an`),
  CONSTRAINT `hoa_don_chi_tiet_tai_cho_ibfk_1` FOREIGN KEY (`id_hoa_don`) REFERENCES `hoa_don_tai_cho` (`id`),
  CONSTRAINT `hoa_don_chi_tiet_tai_cho_ibfk_2` FOREIGN KEY (`id_thuc_an`) REFERENCES `thuc_an` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don_chi_tiet_tai_cho`
--

LOCK TABLES `hoa_don_chi_tiet_tai_cho` WRITE;
/*!40000 ALTER TABLE `hoa_don_chi_tiet_tai_cho` DISABLE KEYS */;
/*!40000 ALTER TABLE `hoa_don_chi_tiet_tai_cho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoa_don_tai_cho`
--

DROP TABLE IF EXISTS `hoa_don_tai_cho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don_tai_cho` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `id_ban` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_ban` (`id_ban`),
  CONSTRAINT `hoa_don_tai_cho_ibfk_1` FOREIGN KEY (`id_ban`) REFERENCES `ban` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don_tai_cho`
--

LOCK TABLES `hoa_don_tai_cho` WRITE;
/*!40000 ALTER TABLE `hoa_don_tai_cho` DISABLE KEYS */;
/*!40000 ALTER TABLE `hoa_don_tai_cho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nguoi_dung`
--

DROP TABLE IF EXISTS `nguoi_dung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nguoi_dung` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `last_name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `tai_khoan` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `mat_khau` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `email` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `phone` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `vai_tro` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nguoi_dung`
--

LOCK TABLES `nguoi_dung` WRITE;
/*!40000 ALTER TABLE `nguoi_dung` DISABLE KEYS */;
INSERT INTO `nguoi_dung` VALUES (1,'test','test','test','$2a$10$IXksfilrh.uq3hzi67v8ierswuBv5wwa4P.7xbsOBEK9973p.v22u','sadsad@gmail.com','21312321','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1691990852/uyaxwbdtxbrrefc3qt7j.png','OWNER',_binary ''),(2,'thảo','trịnh','thao','$2a$10$IXksfilrh.uq3hzi67v8ierswuBv5wwa4P.7xbsOBEK9973p.v22u','quocthao9899@gmail.com','123456789','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1691990852/uyaxwbdtxbrrefc3qt7j.png','ADMIN',_binary ''),(3,'Hiếu','Trịnh','hieu','123456','hieu@gmail.com','123456789','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696186631/zzugx2rx9dxqrnmkuhjp.png','CUSTOMER',_binary '');
/*!40000 ALTER TABLE `nguoi_dung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quy_dinh_van_chuyen`
--

DROP TABLE IF EXISTS `quy_dinh_van_chuyen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quy_dinh_van_chuyen` (
  `id` int NOT NULL AUTO_INCREMENT,
  `khoang_cach` double NOT NULL,
  `price` decimal(10,0) NOT NULL,
  `id_chi_nhanh` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_chi_nhanh` (`id_chi_nhanh`),
  CONSTRAINT `quy_dinh_van_chuyen_ibfk_1` FOREIGN KEY (`id_chi_nhanh`) REFERENCES `chi_nhanh` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quy_dinh_van_chuyen`
--

LOCK TABLES `quy_dinh_van_chuyen` WRITE;
/*!40000 ALTER TABLE `quy_dinh_van_chuyen` DISABLE KEYS */;
/*!40000 ALTER TABLE `quy_dinh_van_chuyen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thuc_an`
--

DROP TABLE IF EXISTS `thuc_an`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thuc_an` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `id_loai` int DEFAULT NULL,
  `id_chi_nhanh` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_loai` (`id_loai`),
  KEY `id_chi_nhanh` (`id_chi_nhanh`),
  CONSTRAINT `thuc_an_ibfk_1` FOREIGN KEY (`id_loai`) REFERENCES `category` (`id`),
  CONSTRAINT `thuc_an_ibfk_2` FOREIGN KEY (`id_chi_nhanh`) REFERENCES `chi_nhanh` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thuc_an`
--

LOCK TABLES `thuc_an` WRITE;
/*!40000 ALTER TABLE `thuc_an` DISABLE KEYS */;
INSERT INTO `thuc_an` VALUES (1,'Bún bò',3,10000,'2023-09-29 07:00:00','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1691990852/uyaxwbdtxbrrefc3qt7j.png',2,4);
/*!40000 ALTER TABLE `thuc_an` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-04  3:13:26
