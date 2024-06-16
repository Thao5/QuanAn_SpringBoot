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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ban`
--

LOCK TABLES `ban` WRITE;
/*!40000 ALTER TABLE `ban` DISABLE KEYS */;
INSERT INTO `ban` VALUES (2,'4 chỗ ngồi, gần cửa sổ','2023-10-02 07:00:00',4),(4,'bàn 8 chỗ','2023-10-09 01:12:28',4);
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (2,'Thức ăn'),(4,'Thức uống'),(5,'Thức uống lạnh'),(6,'Thức uống nóng'),(7,'Thức ăn lạnh');
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_nhanh`
--

LOCK TABLES `chi_nhanh` WRITE;
/*!40000 ALTER TABLE `chi_nhanh` DISABLE KEYS */;
INSERT INTO `chi_nhanh` VALUES (4,'123/12 Võ Văn Tần','2023-09-27 07:00:00','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696612220/hxsmyvodse4i7brjjkx6.jpg',1),(6,'123/asdasad','2023-09-29 22:57:41','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696612220/hxsmyvodse4i7brjjkx6.jpg',2),(7,'sadasdasd','2023-09-29 23:05:24','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696612220/hxsmyvodse4i7brjjkx6.jpg',1),(8,'asdasdasdas','2023-09-29 23:12:15','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696612220/hxsmyvodse4i7brjjkx6.jpg',1),(9,'sadasd','2023-09-30 00:04:59','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696612220/hxsmyvodse4i7brjjkx6.jpg',1),(10,'asdasdsa','2023-09-30 00:09:26','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696612220/hxsmyvodse4i7brjjkx6.jpg',1);
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
  `id_thuc_an` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_nguoi_dung` (`id_nguoi_dung`),
  KEY `id_chi_nhanh` (`id_chi_nhanh`),
  KEY `danh_gia_ibfk_3_idx` (`id_thuc_an`),
  CONSTRAINT `danh_gia_ibfk_1` FOREIGN KEY (`id_nguoi_dung`) REFERENCES `nguoi_dung` (`id`),
  CONSTRAINT `danh_gia_ibfk_2` FOREIGN KEY (`id_chi_nhanh`) REFERENCES `chi_nhanh` (`id`),
  CONSTRAINT `danh_gia_ibfk_3` FOREIGN KEY (`id_thuc_an`) REFERENCES `thuc_an` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `danh_gia`
--

LOCK TABLES `danh_gia` WRITE;
/*!40000 ALTER TABLE `danh_gia` DISABLE KEYS */;
INSERT INTO `danh_gia` VALUES (1,'đẹp',5,'2023-10-03 07:00:00',3,4,1),(2,'hay',5,'2023-10-03 07:00:00',2,4,1),(3,'hay',5,'2023-10-15 12:08:56',1,4,1),(5,'sạch',4,'2023-10-16 02:32:20',7,4,1),(6,'test',4,'2023-10-16 07:30:24',8,4,1),(9,'đẹp',4,'2023-10-22 07:00:00',9,4,1),(11,'rất ngon',5,'2024-03-29 07:00:00',10,4,1),(12,'quán thật sự nice',5,'2024-04-05 12:51:10',2,4,6),(13,'quán này cute thật sự',5,'2024-04-05 12:55:57',2,4,8),(23,'đồ ăn trình bày bắt mắt',5,'2024-04-05 13:20:33',2,4,7),(28,'worst one',2,'2024-04-05 13:27:43',10,4,6),(30,'tệ',2,'2024-04-05 13:31:17',9,4,7),(31,'quán tệ',2,'2024-04-05 13:31:41',7,4,8),(32,'worst',2,'2024-04-05 13:32:43',26,4,7),(33,'quá tệ',1,'2024-04-05 13:33:06',9,4,6),(34,'quán sạch',4,'2024-04-05 13:33:37',8,4,7);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don`
--

LOCK TABLES `hoa_don` WRITE;
/*!40000 ALTER TABLE `hoa_don` DISABLE KEYS */;
INSERT INTO `hoa_don` VALUES (2,'2023-10-03 07:00:00',3),(4,'2023-10-11 16:30:47',2),(5,'2023-10-15 16:11:27',3),(6,'2023-10-15 16:15:24',3),(8,'2023-10-16 00:24:30',7),(9,'2023-10-16 00:33:25',8),(10,'2023-10-16 07:31:25',3),(14,'2024-04-04 02:10:23',2);
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don_chi_tiet`
--

LOCK TABLES `hoa_don_chi_tiet` WRITE;
/*!40000 ALTER TABLE `hoa_don_chi_tiet` DISABLE KEYS */;
INSERT INTO `hoa_don_chi_tiet` VALUES (2,3,0,30000,'2023-10-03 15:55:02',2,1),(4,2,0,20000,'2023-10-11 16:30:47',4,1),(5,1,0,10000,'2023-10-15 16:11:27',5,1),(6,1,0,50000,'2023-10-15 16:11:27',5,6),(7,1,0,50000,'2023-10-15 16:11:27',5,7),(8,1,0,50000,'2023-10-15 16:11:27',5,8),(9,1,10000,50000,'2023-10-15 16:15:24',6,6),(10,1,10000,50000,'2023-10-15 16:15:25',6,7),(12,2,10000,50000,'2023-10-16 00:33:25',9,7),(13,1,10000,50000,'2023-10-16 07:31:25',10,7),(16,1,10000,20000,'2024-04-04 02:10:24',14,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don_chi_tiet_tai_cho`
--

LOCK TABLES `hoa_don_chi_tiet_tai_cho` WRITE;
/*!40000 ALTER TABLE `hoa_don_chi_tiet_tai_cho` DISABLE KEYS */;
INSERT INTO `hoa_don_chi_tiet_tai_cho` VALUES (1,3,30000,'2023-10-04 04:18:41',2,7),(6,3,30000,'2023-10-12 11:13:53',7,7),(7,3,30000,'2023-10-12 11:45:30',8,8),(8,3,30000,'2023-10-12 11:50:48',9,1),(9,3,30000,'2023-10-12 11:51:00',10,1),(10,3,30000,'2023-10-12 12:04:21',11,1),(11,3,30000,'2023-10-12 12:38:49',12,1),(12,3,30000,'2023-10-12 12:40:00',13,1),(13,3,30000,'2023-10-12 12:43:25',14,1),(14,3,30000,'2023-10-12 12:44:06',15,1),(15,3,30000,'2023-10-12 12:48:34',16,1),(16,3,30000,'2023-10-12 12:50:23',17,1),(17,3,30000,'2023-10-12 12:59:05',18,1),(18,3,30000,'2023-10-12 13:13:55',19,1),(26,1,10000,'2023-10-16 07:32:48',25,1),(31,2,40,'2024-06-08 17:48:32',29,1),(32,2,40,'2024-06-08 17:49:03',30,1),(33,2,40,'2024-06-08 17:49:03',31,1),(34,2,40,'2024-06-08 17:49:03',32,1),(35,2,40,'2024-06-08 17:49:48',33,1),(36,2,40,'2024-06-08 17:50:07',34,1),(37,2,40,'2024-06-08 17:50:12',35,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don_tai_cho`
--

LOCK TABLES `hoa_don_tai_cho` WRITE;
/*!40000 ALTER TABLE `hoa_don_tai_cho` DISABLE KEYS */;
INSERT INTO `hoa_don_tai_cho` VALUES (2,'2023-10-04 04:18:28',2),(7,'2023-10-12 11:13:53',2),(8,'2023-10-12 11:45:30',2),(9,'2023-10-12 11:50:48',2),(10,'2023-10-12 11:51:00',2),(11,'2023-10-12 12:04:21',2),(12,'2023-10-12 12:38:49',2),(13,'2023-10-12 12:40:00',2),(14,'2023-10-12 12:43:25',2),(15,'2023-10-12 12:44:06',2),(16,'2023-10-12 12:48:33',2),(17,'2023-10-12 12:50:23',2),(18,'2023-10-12 12:59:05',2),(19,'2023-10-12 13:13:55',2),(21,'2023-10-16 00:25:37',4),(25,'2023-10-16 07:32:48',2),(29,'2024-06-08 17:48:32',2),(30,'2024-06-08 17:49:03',2),(31,'2024-06-08 17:49:03',2),(32,'2024-06-08 17:49:03',2),(33,'2024-06-08 17:49:48',2),(34,'2024-06-08 17:50:07',2),(35,'2024-06-08 17:50:12',2);
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
  `loai_nguoi_dung` varchar(45) DEFAULT 'NORMAL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nguoi_dung`
--

LOCK TABLES `nguoi_dung` WRITE;
/*!40000 ALTER TABLE `nguoi_dung` DISABLE KEYS */;
INSERT INTO `nguoi_dung` VALUES (1,'test','test','test','$2a$10$IXksfilrh.uq3hzi67v8ierswuBv5wwa4P.7xbsOBEK9973p.v22u','sadsad@gmail.com','21312321','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1691990852/uyaxwbdtxbrrefc3qt7j.png','OWNER',_binary '','NORMAL'),(2,'thảo','trịnh','thao','$2a$10$IXksfilrh.uq3hzi67v8ierswuBv5wwa4P.7xbsOBEK9973p.v22u','quocthao9899@gmail.com','123456789','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1691990852/uyaxwbdtxbrrefc3qt7j.png','ADMIN',_binary '','NORMAL'),(3,'Hiếu','Trịnh','hieu','$2a$10$pLrRMXpkU/DMKbcTXYiUgebphKq0Uwz9woIx4E.tSlM2Q56CGSvme','2051050459thao@ou.edu.vn','012345678','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696186631/zzugx2rx9dxqrnmkuhjp.png','CUSTOMER',_binary '','NORMAL'),(7,'nam','nam','nam','$2a$10$bq9BQ1QOE3O4511JO3FEtuAtb5RuQFGOm0ly1LsumdUcumrSCavAG','nam@gmail.com','321456789','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696714107/i1jtpgmaailri9q5e76v.jpg','CUSTOMER',_binary '','NORMAL'),(8,'kolo','kolo','kolo','$2a$10$UPTUn.QWH7gaDe3T7HQvQ.84HYqBrK.oitK0osPLg9kccd72y.rU2','kolo@gmail.com','326541789','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696714311/fuzftqdt1a0otqbriwlg.jpg','CUSTOMER',_binary '','NORMAL'),(9,'Hùng','Hùng','hung','$2a$10$lDcWbb9LwOrU/dDGhcdVkefkqh9zGPB.88./ytgIfBXmLGIpvPtFa','hung@gmail.com','456321789','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696752298/tbd39z6zdgzooifc6d8y.jpg','CUSTOMER',_binary '\0','NORMAL'),(10,'thao98','thao98','thao98','$2a$10$U9Q4Yzo/OFV5nA9ucaCHHOTD08N/84KqDJKdL4ZLHzw24lO5dQ5S6','quocthao9898@gmail.com','326541897','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696752684/d41fqp7hadh6vyvi54o8.jpg','CUSTOMER',_binary '\0','NORMAL'),(12,'thảo','Trịnh','thao2','$2a$10$1kSZxImV9o1/.6v2W7bDIe7A0VL1iJq/eKYsIGw6vLgiSLNSb.SVi','2051050459hieu@ou.edu.vn','0123546789','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1697390560/y3ckcksojsz8gq8vd6nk.jpg','ADMIN',_binary '','NORMAL'),(13,'ban','ban','ban','$2a$10$IXksfilrh.uq3hzi67v8ierswuBv5wwa4P.7xbsOBEK9973p.v22u','ban@gmail.com','0235648956','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1691990852/uyaxwbdtxbrrefc3qt7j.png','ban',_binary '','NORMAL'),(14,'sadasdas','sadasdsa','moko','$2a$10$WvHHXSvhgpXLDYBPbJszAOHZ86DiRx00m3SG4ssj1ycI13sa8sCHe','moko@gmail.com','0231548967','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1697393136/t8xq1ebauhbul2fqe0x9.jpg','CUSTOMER',_binary '','NORMAL'),(15,'komos','komo','komo','$2a$10$eiaQvoRncLOVqaKcxpdn7uFrvaxv8oz16SBFw6sdpTFs.uSrHj8ke','komo@gmail.com','0235641879','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1697416517/js1qnuupveqe6odyiba3.jpg','CUSTOMER',_binary '\0','NORMAL'),(16,'thao','thao','thao3','$2a$10$Ol6Tjjy9t9FW.KIDc/NziuxRft79bqjrRI1SOmpwvI5ZWrPODNFr.','2051050459hieu4@ou.edu.vn','0321564879','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1697416886/agvmdmut2bbn4nyjfjyt.jpg','ADMIN',_binary '\0','NORMAL'),(17,'test96','test961','test96','$2a$10$E.Cuc3P8MWV.ide0.g1Coe2lnbtBV7QIo4ailgTxl14UP7ju4SWPy','test96@gmail.com','0635241897','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1697910247/tzrpjwouwpt6tfduhdwc.jpg','CUSTOMER',_binary '\0','NORMAL'),(26,'Hieu','Trinh','trinhminhhieu2712@gmail.com','$2a$10$/fyjlpfUcDPBMtk5HWhp9OyWhVlrbE6uL8XF2oHxxkUBJ.A00o8GO','trinhminhhieu2712@gmail.com','','https://lh3.googleusercontent.com/a/ACg8ocJM2K974Hm5Z1qDfTLUXVexY2XaYPeDhgCLlR97FUE0=s96-c','CUSTOMER',_binary '','GOOGLE');
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
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_loai` (`id_loai`),
  KEY `id_chi_nhanh` (`id_chi_nhanh`),
  CONSTRAINT `thuc_an_ibfk_1` FOREIGN KEY (`id_loai`) REFERENCES `category` (`id`),
  CONSTRAINT `thuc_an_ibfk_2` FOREIGN KEY (`id_chi_nhanh`) REFERENCES `chi_nhanh` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thuc_an`
--

LOCK TABLES `thuc_an` WRITE;
/*!40000 ALTER TABLE `thuc_an` DISABLE KEYS */;
INSERT INTO `thuc_an` VALUES (1,'Bún bò píasadas',46,20,'2023-09-29 07:00:00','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1714196330/yiqj7edkj7du6v9fxbio.webp',2,4,_binary ''),(6,'Bún bò huế',8,45,'2023-10-06 22:48:00','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696612470/anxvrwil12cv6xwowzly.jpg',2,4,_binary ''),(7,'Bún bò hu',10,40,'2023-10-06 07:00:00','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696612470/anxvrwil12cv6xwowzly.jpg',7,4,_binary '\0'),(8,'Táo ngâm muối',9,2131231,'2023-10-07 07:00:00','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1696613158/wq9d5hoava8ag4tbaun5.jpg',2,4,_binary '\0'),(9,'Bún bò huế muối',10,10000,'2023-10-16 07:00:00','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1697389924/uyedwlrrggu7zgvbxtqf.jpg',2,4,_binary '\0'),(17,'Phở tái',80,40,'2024-04-11 07:00:00','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1714196369/oshaxkcjxubiagoaiiar.jpg',2,4,_binary '\0'),(18,'Bò kho',30,35,'2024-04-11 07:00:00','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1714196397/tldmqqrqqckdujbq0fkb.jpg',2,4,_binary ''),(19,'Gỏi cuốn',30,5000,'2024-04-27 07:00:00','https://res.cloudinary.com/dtlqyvkvu/image/upload/v1714197313/kp7srflzde6ovee8o6nz.jpg',2,4,_binary '\0');
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

-- Dump completed on 2024-06-16 15:59:51
