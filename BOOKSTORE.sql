/*
MySQL Data Transfer
Source Host: localhost
Source Database: library
Target Host: localhost
Target Database: library
Date: 2014/10/23 10:07:49
*/
USE mysql;
CREATE DATABASE Library;
USE Library;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for booktable
-- ----------------------------
DROP TABLE IF EXISTS `booktable`;
CREATE TABLE `booktable` (
  `BID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BookName` varchar(255) DEFAULT NULL,
  `Author` varchar(20) DEFAULT NULL,
  `Press` varchar(255) DEFAULT NULL,
  `PressDate` varchar(255) DEFAULT NULL,
  `Price` double DEFAULT NULL,
  `Count` int(11) DEFAULT NULL,
  `TotalPrice` double DEFAULT NULL,
  `ISBN` bigint(20) DEFAULT NULL,
  `BookCategory` varchar(255) DEFAULT NULL,
  `Language` varchar(255) DEFAULT NULL,
  `Size` varchar(255) DEFAULT NULL,
  `Binding` varchar(255) DEFAULT NULL,
  `Feature` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`BID`)
) ENGINE=InnoDB AUTO_INCREMENT=1182 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for usertable
-- ----------------------------
DROP TABLE IF EXISTS `usertable`;
CREATE TABLE `usertable` (
  `UID` bigint(20) NOT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `Sex` enum('男','女') DEFAULT NULL,
  `UserGrade` varchar(10) DEFAULT NULL,
  `HistoryCount` int(11) DEFAULT NULL,
  `LimitCount` int(4) DEFAULT NULL,
  PRIMARY KEY (`UID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for bookbrowse
-- ----------------------------
DROP TABLE IF EXISTS `bookbrowse`;
CREATE TABLE `bookbrowse` (
  `BID` bigint(20) NOT NULL,
  `UID` bigint(20) NOT NULL,
  `BorrowDate` date NOT NULL,
  `ReturnDate` date DEFAULT NULL,
  `Comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`BID`,`UID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;