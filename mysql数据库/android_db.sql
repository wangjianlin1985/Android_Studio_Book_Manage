/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : android_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2017-11-14 21:43:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `barcode` varchar(20) NOT NULL,
  `bookType` int(11) default NULL,
  `bookName` varchar(20) default NULL,
  `bookPhoto` varchar(50) default NULL,
  `price` float default NULL,
  `count` int(11) default NULL,
  `publishDate` datetime default NULL,
  `publish` varchar(20) default NULL,
  `introduction` varchar(500) default NULL,
  PRIMARY KEY  (`barcode`),
  KEY `FK1FAF091434BB68` (`bookType`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('TS001', '1', 'HTML5网站设计详解', 'upload/AC26C01FD6CB6B7F269C18ACF6E2BFEE.jpg', '32.5', '12', '2017-11-01 00:00:00', '电子科技大学出版社', '这个书教你开发最新的网站界面,这个书教你开发最新的网站界面,这个书教你开发最新的网站界面,这个书教你开发最新的网站界面,这个书教你开发最新的网站界面,这个书教你开发最新的网站界面,这个书教你开发最新的网站界面');
INSERT INTO `book` VALUES ('TS002', '2', '中国近代史', 'upload/1171022056191.jpg', '28.5', '19', '2017-11-12 00:00:00', '人民教育出版社', '讲解中国近代的历史书籍');
INSERT INTO `book` VALUES ('TS003', '1', '双鱼林安卓开发详解', 'upload/117102219911.jpg', '52.5', '18', '2017-11-08 00:00:00', '四川大学出版社', '此书由双鱼林设计大师编著，由浅入深讲解如何开发手机端app');

-- ----------------------------
-- Table structure for `booktype`
-- ----------------------------
DROP TABLE IF EXISTS `booktype`;
CREATE TABLE `booktype` (
  `bookTypeId` int(11) NOT NULL auto_increment,
  `bookTypeName` varchar(18) default NULL,
  `days` int(11) default NULL,
  PRIMARY KEY  (`bookTypeId`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of booktype
-- ----------------------------
INSERT INTO `booktype` VALUES ('1', '计算机类', '30');
INSERT INTO `booktype` VALUES ('2', '历史类', '25');

-- ----------------------------
-- Table structure for `loaninfo`
-- ----------------------------
DROP TABLE IF EXISTS `loaninfo`;
CREATE TABLE `loaninfo` (
  `loadId` int(11) NOT NULL auto_increment,
  `book` varchar(20) default NULL,
  `reader` varchar(20) default NULL,
  `borrowDate` datetime default NULL,
  `returnDate` datetime default NULL,
  `memo` varchar(500) default NULL,
  PRIMARY KEY  (`loadId`),
  KEY `FK726409DE4CA6B608` (`reader`),
  KEY `FK726409DE1D53B2D4` (`book`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of loaninfo
-- ----------------------------
INSERT INTO `loaninfo` VALUES ('1', 'TS001', 'DZ001', '2017-11-01 00:00:00', '2017-11-16 00:00:00', '这个同学及时还书，人品好');
INSERT INTO `loaninfo` VALUES ('2', 'TS002', 'DZ002', '2017-10-09 00:00:00', '2017-11-11 00:00:00', '此读者超期了几天，罚款了20元');
INSERT INTO `loaninfo` VALUES ('3', 'TS002', 'DZ001', '2017-10-08 00:00:00', '2017-11-14 00:00:00', '你也超期了2天，罚款5元哈！');

-- ----------------------------
-- Table structure for `reader`
-- ----------------------------
DROP TABLE IF EXISTS `reader`;
CREATE TABLE `reader` (
  `readerNo` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  `readerType` int(11) default NULL,
  `readerName` varchar(20) default NULL,
  `sex` varchar(2) default NULL,
  `photo` varchar(50) default NULL,
  `birthday` datetime default NULL,
  `telephone` varchar(20) default NULL,
  `email` varchar(50) default NULL,
  `qq` varchar(20) default NULL,
  `address` varchar(80) default NULL,
  PRIMARY KEY  (`readerNo`),
  KEY `FK91AA44E38F2B049C` (`readerType`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reader
-- ----------------------------
INSERT INTO `reader` VALUES ('DZ001', '123', '1', '双鱼林', '男', 'upload/C8725204FF77C47A03F6B1BFADD2E729.jpg', '2017-11-02 00:00:00', '13539843342', '2348195@qq.com', '235914124', '四川成都红星路13号');
INSERT INTO `reader` VALUES ('DZ002', '123', '2', '张小倩', '女', 'upload/1171022053312.jpg', '2012-10-14 00:00:00', '13539830843', 'xiaoqian@163.com', '512161541', '福建福州鼓楼区');

-- ----------------------------
-- Table structure for `readertype`
-- ----------------------------
DROP TABLE IF EXISTS `readertype`;
CREATE TABLE `readertype` (
  `readerTypeId` int(11) NOT NULL auto_increment,
  `readerTypeName` varchar(20) default NULL,
  `number` int(11) default NULL,
  PRIMARY KEY  (`readerTypeId`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of readertype
-- ----------------------------
INSERT INTO `readertype` VALUES ('1', '学生类', '3');
INSERT INTO `readertype` VALUES ('2', '教师类', '5');
