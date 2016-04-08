CREATE DATABASE  IF NOT EXISTS `citiquantsdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `citiquantsdb`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: citiquantsdb
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cities`
--

DROP TABLE IF EXISTS `cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cities` (
  `Country - City` varchar(45) DEFAULT NULL,
  `Year` int(11) NOT NULL,
  `Labor Availability 1` int(11) DEFAULT NULL,
  `Labor Availability (bilingual) 1` int(11) DEFAULT NULL,
  `Employed Bilingual Agents 1` int(11) DEFAULT NULL,
  `Annual Attrition Rate` double DEFAULT NULL,
  `Union Presence & Activity` int(11) DEFAULT NULL,
  `Cultural Domain Acuity Model (CDAM)` int(11) DEFAULT NULL,
  `State or province of city` varchar(45) DEFAULT NULL,
  `Tier Classification` int(11) DEFAULT NULL,
  `Name of city` varchar(45) NOT NULL,
  `Country Population` int(11) DEFAULT NULL,
  `Population Metro Area` int(11) DEFAULT NULL,
  `Population City` int(11) DEFAULT NULL,
  `Growth rate of city` double DEFAULT NULL,
  `Wage Inflation Rate` double DEFAULT NULL,
  `Labor cost per agent/daily` double DEFAULT NULL,
  `Supervisor` double DEFAULT NULL,
  `Attrition rates (avg reported attrition)` double DEFAULT NULL,
  `Real Estate Cost (per sq.ft/annum)` double DEFAULT NULL,
  `Telecom Cost (Monthly)` double DEFAULT NULL,
  `Teledensity Landline/dial-up` double DEFAULT NULL,
  `Teledensity Mobility` double DEFAULT NULL,
  `Internet Connectivity` varchar(45) DEFAULT NULL,
  `Electricity Cost p KWh` double DEFAULT NULL,
  `Growth rate of Call Center & BPO Sector` double DEFAULT NULL,
  `service suppliers # in city` int(11) DEFAULT NULL,
  `WTO Free Zone Extension 2015` varchar(45) DEFAULT NULL,
  `CARICOM/CAFTA-DR MEMBER` varchar(45) DEFAULT NULL,
  `GDP (bb)` double DEFAULT NULL,
  `GDP per capita` double DEFAULT NULL,
  `GDP real growth rate` double DEFAULT NULL,
  `unemployment` double DEFAULT NULL,
  `Employment in IT/BPO Sector` int(11) DEFAULT NULL,
  `Inflation` double DEFAULT NULL,
  `Corporate Tax Rate %` double DEFAULT NULL,
  `Fringe Benefit %` double DEFAULT NULL,
  `Employment Rate` double DEFAULT NULL,
  `Labor Availability 2` int(11) DEFAULT NULL,
  `Labor Availability (bilingual) 2` int(11) DEFAULT NULL,
  `Employed Bilingual Agents 2` double DEFAULT NULL,
  `4 year university grads` int(11) DEFAULT NULL,
  `2-yr college/technical (per year)` int(11) DEFAULT NULL,
  `stock of graduates` int(11) DEFAULT NULL,
  `annual business graduates` int(11) DEFAULT NULL,
  `literacy rates` double DEFAULT NULL,
  PRIMARY KEY (`Year`,`Name of city`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cities`
--

LOCK TABLES `cities` WRITE;
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rules`
--

DROP TABLE IF EXISTS `rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rules` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Rule Name` varchar(45) NOT NULL,
  `Data Field` varchar(45) NOT NULL,
  `Specification` varchar(45) NOT NULL,
  `Boundary Type` varchar(45) NOT NULL,
  `Boundary Condition` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Rule Name_UNIQUE` (`Rule Name`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rules`
--

LOCK TABLES `rules` WRITE;
/*!40000 ALTER TABLE `rules` DISABLE KEYS */;
/*!40000 ALTER TABLE `rules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-08 22:44:08
