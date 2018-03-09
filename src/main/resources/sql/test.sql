/*
Navicat MySQL Data Transfer

Source Server         : 192.168.2.14
Source Server Version : 50635
Source Host           : 192.168.2.14:3306
Source Database       : vcsaas_learn

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-03-09 17:17:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for acl_class
-- ----------------------------
DROP TABLE IF EXISTS `acl_class`;
CREATE TABLE `acl_class` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `class` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_acl_class` (`class`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of acl_class
-- ----------------------------
INSERT INTO `acl_class` VALUES ('2', 'ind.ck.entity.User');

-- ----------------------------
-- Table structure for acl_entry
-- ----------------------------
DROP TABLE IF EXISTS `acl_entry`;
CREATE TABLE `acl_entry` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `acl_object_identity` bigint(20) unsigned NOT NULL,
  `ace_order` int(11) NOT NULL,
  `sid` bigint(20) unsigned NOT NULL,
  `mask` int(10) unsigned NOT NULL,
  `granting` tinyint(1) NOT NULL,
  `audit_success` tinyint(1) NOT NULL,
  `audit_failure` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_acl_entry` (`acl_object_identity`,`ace_order`),
  KEY `fk_acl_entry_acl` (`sid`),
  CONSTRAINT `fk_acl_entry_acl` FOREIGN KEY (`sid`) REFERENCES `acl_sid` (`id`),
  CONSTRAINT `fk_acl_entry_object` FOREIGN KEY (`acl_object_identity`) REFERENCES `acl_object_identity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of acl_entry
-- ----------------------------
INSERT INTO `acl_entry` VALUES ('3', '3', '0', '17', '2', '1', '0', '0');
INSERT INTO `acl_entry` VALUES ('4', '6', '0', '17', '2', '1', '0', '0');
INSERT INTO `acl_entry` VALUES ('5', '7', '0', '20', '2', '1', '0', '0');
INSERT INTO `acl_entry` VALUES ('6', '10', '0', '17', '16', '1', '0', '0');

-- ----------------------------
-- Table structure for acl_object_identity
-- ----------------------------
DROP TABLE IF EXISTS `acl_object_identity`;
CREATE TABLE `acl_object_identity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `object_id_class` bigint(20) unsigned NOT NULL,
  `object_id_identity` varchar(36) NOT NULL,
  `parent_object` bigint(20) unsigned DEFAULT NULL,
  `owner_sid` bigint(20) unsigned DEFAULT NULL,
  `entries_inheriting` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_acl_object_identity` (`object_id_class`,`object_id_identity`),
  KEY `fk_acl_object_identity_parent` (`parent_object`),
  KEY `fk_acl_object_identity_owner` (`owner_sid`),
  CONSTRAINT `fk_acl_object_identity_class` FOREIGN KEY (`object_id_class`) REFERENCES `acl_class` (`id`),
  CONSTRAINT `fk_acl_object_identity_owner` FOREIGN KEY (`owner_sid`) REFERENCES `acl_sid` (`id`),
  CONSTRAINT `fk_acl_object_identity_parent` FOREIGN KEY (`parent_object`) REFERENCES `acl_object_identity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of acl_object_identity
-- ----------------------------
INSERT INTO `acl_object_identity` VALUES ('3', '2', '35', null, '17', '1');
INSERT INTO `acl_object_identity` VALUES ('6', '2', '34', null, '17', '1');
INSERT INTO `acl_object_identity` VALUES ('7', '2', '33', null, '20', '1');
INSERT INTO `acl_object_identity` VALUES ('10', '2', '38', null, '17', '1');

-- ----------------------------
-- Table structure for acl_sid
-- ----------------------------
DROP TABLE IF EXISTS `acl_sid`;
CREATE TABLE `acl_sid` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `principal` tinyint(1) NOT NULL,
  `sid` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_acl_sid` (`sid`,`principal`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of acl_sid
-- ----------------------------
INSERT INTO `acl_sid` VALUES ('17', '1', 'anonymousUser');
INSERT INTO `acl_sid` VALUES ('20', '1', 'user');

-- ----------------------------
-- Table structure for ROLES
-- ----------------------------
DROP TABLE IF EXISTS `ROLES`;
CREATE TABLE `ROLES` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  KEY `FKlrtlpmemw51mw5volc4rdkf64` (`user_id`),
  CONSTRAINT `FKlrtlpmemw51mw5volc4rdkf64` FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ROLES
-- ----------------------------
INSERT INTO `ROLES` VALUES ('1', 'ADMIN', '33');

-- ----------------------------
-- Table structure for USERS
-- ----------------------------
DROP TABLE IF EXISTS `USERS`;
CREATE TABLE `USERS` (
  `id` int(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sexual` tinyint(1) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `dob` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of USERS
-- ----------------------------
INSERT INTO `USERS` VALUES ('33', 'wwccss', null, '111', null, null, null);
INSERT INTO `USERS` VALUES ('34', 'ddff', null, '212', '2', null, null);
INSERT INTO `USERS` VALUES ('35', 'lucus', null, null, null, null, null);
INSERT INTO `USERS` VALUES ('38', 'gurl', 'dd@d.dd', '$2a$10$ofPkBDUezOJp6Sik63Q/0.QlU8a1itEyzldjSXqfn2nDPqXjN0Ljm', null, null, null);
