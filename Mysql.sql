/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 10.0.38-MariaDB : Database - dingdong_party
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dingdong_party` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dingdong_party`;

/*Table structure for table `party_activity` */

DROP TABLE IF EXISTS `party_activity`;

CREATE TABLE `party_activity` (
  `id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动id',
  `name` varchar(30) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动名称',
  `summary` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '摘要',
  `group_id` char(19) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '党组id',
  `branch_id` char(19) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '党支部id',
  `address` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动地点',
  `director_id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '负责人id',
  `director_name` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '负责人姓名',
  `email` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '邮箱',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '截至时间',
  `registration_end_time` datetime NOT NULL COMMENT '报名截止时间',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0:草稿 1:未审批 2:被驳回 3:已审批 4：进行中 5：已结束',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '参与人数',
  `limit_num` int(11) NOT NULL DEFAULT '100' COMMENT '限制人数，默认100',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  `approver_id` char(19) DEFAULT NULL COMMENT '审批人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_activity` */

insert  into `party_activity`(`id`,`name`,`summary`,`group_id`,`branch_id`,`address`,`director_id`,`director_name`,`email`,`start_time`,`end_time`,`registration_end_time`,`status`,`num`,`limit_num`,`is_deleted`,`create_time`,`modify_time`,`approver_id`) values 
('1419572431459876245','未开始活动-请假不成功','摘要','1','1','asd','33','33','33','2021-08-20 10:56:32','2021-08-20 10:56:34','2021-09-30 21:55:12',1,-5,0,1,'2021-08-20 10:56:48','2021-08-20 10:56:51',NULL),
('1419572434015489632','未开始活动-请假','总结','1','1','松山湖003','1427505190059155458','负责人003','1658499@qq.com','2021-08-07 20:28:58','2021-08-13 20:29:01','2021-09-30 21:55:15',2,0,0,1,'2021-08-17 20:29:08','2021-08-17 20:29:11',NULL),
('1419572434024874963','已结束活动','总结','1','1','莞城005','1427505190059155458','负责人005','8457956@qq.com','2021-08-17 20:23:27','2021-08-17 20:23:31','2021-09-30 21:55:16',5,0,0,0,'2021-08-17 20:23:40','2021-08-17 20:23:43',NULL),
('1441387553432379394','中秋社区清理','中秋社区清理','2','1','莞雅社区','1427505190059155458','aa','863176846@qq.com','2021-09-22 00:00:00','2021-09-24 21:01:02','2021-09-30 21:55:21',0,0,25,0,'2021-09-24 21:02:27','2021-09-24 21:12:48',NULL);

/*Table structure for table `party_activity_comment` */

DROP TABLE IF EXISTS `party_activity_comment`;

CREATE TABLE `party_activity_comment` (
  `id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '评论id',
  `activity_id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动id',
  `user_id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户id',
  `content` text CHARACTER SET utf8mb4 NOT NULL COMMENT '评论内容',
  `image` varchar(30) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '图片',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_activity_comment` */

insert  into `party_activity_comment`(`id`,`activity_id`,`user_id`,`content`,`image`,`is_deleted`,`create_time`,`modify_time`) values 
('141957243401459863','1419572434024874963','1427546569351131137','sjkxnff','',0,'2021-08-20 11:29:01','2021-09-22 15:39:00'),
('141957243402412547','1419572434024874963','1427546569351131137','内容',NULL,0,'2021-08-20 11:28:09','2021-08-20 11:28:12'),
('1440584221474267137','1419572431459876245','1427546569351131137','asd','',0,'2021-09-22 15:50:17','2021-09-22 15:50:17');

/*Table structure for table `party_activity_details` */

DROP TABLE IF EXISTS `party_activity_details`;

CREATE TABLE `party_activity_details` (
  `id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动id',
  `content` tinytext CHARACTER SET utf8mb4 NOT NULL COMMENT '内容',
  `attention` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '注意事项',
  `announcement` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '公共链接',
  `enclosure` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '附件链接·',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_activity_details` */

insert  into `party_activity_details`(`id`,`content`,`attention`,`announcement`,`enclosure`,`is_deleted`,`create_time`,`modify_time`) values 
('1419572431459876245','内容','注意','aad','asd',1,'2021-08-20 11:19:33','2021-08-20 11:19:38'),
('1419572434015489632','333333','333333','333333','333',1,'2021-08-17 23:12:26','2021-08-17 23:12:29'),
('1419572434024874963','555','555','555','555',0,'2021-08-17 23:11:26','2021-08-17 23:11:29'),
('1441387553432379394','中秋社区清理','','','',0,'2021-09-24 21:02:27','2021-09-24 21:12:48');

/*Table structure for table `party_activity_experience` */

DROP TABLE IF EXISTS `party_activity_experience`;

CREATE TABLE `party_activity_experience` (
  `id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '心得id',
  `activity_id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动id',
  `user_id` char(19) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名称',
  `file_path` varchar(30) CHARACTER SET utf8mb4 NOT NULL COMMENT '心得地址',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_activity_experience` */

insert  into `party_activity_experience`(`id`,`activity_id`,`user_id`,`user_name`,`file_path`,`is_deleted`,`create_time`,`modify_time`) values 
('1419574496704118786','1419572434029355010','1','马浚杰','',0,'2021-07-26 16:25:09','2021-07-26 16:25:09');

/*Table structure for table `party_activity_image` */

DROP TABLE IF EXISTS `party_activity_image`;

CREATE TABLE `party_activity_image` (
  `id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '图片id',
  `activity_id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动id',
  `path` varchar(30) CHARACTER SET utf8mb4 NOT NULL COMMENT '图片地址',
  `sort` int(11) NOT NULL COMMENT '第几张',
  `is_deleted` tinyint(1) NOT NULL COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_activity_image` */

insert  into `party_activity_image`(`id`,`activity_id`,`path`,`sort`,`is_deleted`,`create_time`,`modify_time`) values 
('1419578331988307970','1419572434029355010','d1',0,0,'2021-07-26 16:40:23','2021-07-26 16:40:23');

/*Table structure for table `party_admin` */

DROP TABLE IF EXISTS `party_admin`;

CREATE TABLE `party_admin` (
  `id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '管理员id',
  `username` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '账号',
  `password` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '密码',
  `user_id` char(19) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户id',
  `group_id` char(19) DEFAULT NULL COMMENT '负责党组',
  `branch_id` char(19) DEFAULT NULL COMMENT '负责党支部',
  `authority` int(3) NOT NULL DEFAULT '1' COMMENT '权限（1，2，3）',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_admin` */

insert  into `party_admin`(`id`,`username`,`password`,`user_id`,`group_id`,`branch_id`,`authority`,`is_deleted`,`create_time`,`modify_time`) values 
('1','manman','e10adc3949ba59abbe56e057f20f883e','1419162725304541186','1','',1,0,'2021-07-23 16:37:04','2021-08-18 21:03:54'),
('1427505190059155458','admin','96e79218965eb72c92a549dd5a330112','1419162725304541186','','1',3,0,'2021-08-17 13:38:53','2021-09-16 12:04:39'),
('1427522773965647873','aa','e10adc3949ba59abbe56e057f20f883e','1419162725304541186','1','',1,0,'2021-08-17 14:48:46','2021-08-17 14:48:46'),
('1434066585122840577','111','c4ca4238a0b923820dcc509a6f75849b','1','1','1',1,0,'2021-09-04 16:11:32','2021-09-04 16:11:32'),
('1434069658813669378','123','698d51a19d8a121ce581499d7b701668','1419162725304541186','1','1',2,0,'2021-09-04 16:23:45','2021-09-04 16:23:45'),
('1434071205069004802','1','6512bd43d9caa6e02c990b0a82652dca','1427546569351131137','1','1',1,0,'2021-09-04 16:29:53','2021-09-04 16:29:53');

/*Table structure for table `party_branch` */

DROP TABLE IF EXISTS `party_branch`;

CREATE TABLE `party_branch` (
  `id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '总支部id',
  `name` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '总支部名称',
  `parent_id` char(40) CHARACTER SET utf8mb4 DEFAULT '0' COMMENT '父级党支部id',
  `parent_name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '父级党支部名称',
  `director_id` char(40) CHARACTER SET utf8mb4 NOT NULL COMMENT '负责人id',
  `director_name` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '负责人名字',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:删除,0:未删除)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_branch` */

insert  into `party_branch`(`id`,`name`,`parent_id`,`parent_name`,`director_id`,`director_name`,`is_deleted`,`create_time`,`modify_time`) values 
('1','网络空间安全党委','0',NULL,'1','aa',0,'2021-08-18 23:02:30','2021-08-18 23:02:34'),
('2','aa','0',NULL,'1','aa',0,'2021-07-24 10:21:12','2021-07-24 10:21:14');

/*Table structure for table `party_faculty` */

DROP TABLE IF EXISTS `party_faculty`;

CREATE TABLE `party_faculty` (
  `id` char(19) NOT NULL COMMENT '院系id',
  `name` varchar(20) NOT NULL COMMENT '院系名称',
  `pid` char(19) NOT NULL COMMENT '父级id',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_faculty` */

insert  into `party_faculty`(`id`,`name`,`pid`,`is_deleted`,`create_time`,`modify_time`) values 
('1','网安学院','0',0,'2021-08-26 10:33:13','2021-08-26 10:33:28'),
('2','计科学院','0',0,'2021-08-26 10:45:14','2021-08-26 10:45:16'),
('3','计科1','2',0,'2021-09-04 10:43:45','2021-09-04 10:43:47'),
('4','计科2','2',0,'2021-09-04 10:44:49','2021-09-04 10:44:51'),
('5','网安1','1',0,'2021-09-04 10:45:12','2021-09-04 10:45:13'),
('6','网安2','1',0,'2021-09-04 10:45:22','2021-09-04 10:45:24');

/*Table structure for table `party_group` */

DROP TABLE IF EXISTS `party_group`;

CREATE TABLE `party_group` (
  `id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '党小组id',
  `name` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '党小组名称',
  `branch_id` varchar(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '党支部id',
  `branch_name` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '党支部名称',
  `director_id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '负责人id',
  `director_name` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '负责人名称',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_group` */

insert  into `party_group`(`id`,`name`,`branch_id`,`branch_name`,`director_id`,`director_name`,`is_deleted`,`create_time`,`modify_time`) values 
('1','软件工程党支部','1','网络空间安全党委','1','aaa',0,'2021-08-18 23:03:28','2021-08-18 23:03:31'),
('1419230756353413122','aaa','2','网络空间安全党委','1','a',0,'2021-07-25 17:39:15','2021-07-25 17:39:55'),
('2','网络工程党支部','1','网络空间安全党委','2','aaa',0,'2021-08-18 23:04:01','2021-08-18 23:04:04'),
('3','网络空间安全党支部','1','网络空间安全党委','2','aa',0,'2021-08-18 23:04:32','2021-08-18 23:04:35');

/*Table structure for table `party_others` */

DROP TABLE IF EXISTS `party_others`;

CREATE TABLE `party_others` (
  `id` char(19) NOT NULL COMMENT 'id',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `num` int(11) NOT NULL COMMENT '值',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime(1) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_others` */

insert  into `party_others`(`id`,`name`,`num`,`is_deleted`,`create_time`,`modify_time`) values 
('12462534234','最高期数',10,0,'2021-09-02 19:26:33','2021-09-02 19:26:35.0'),
('14192307563','最高年级',2021,0,'2021-09-02 19:19:28','2021-09-02 19:19:30.0');

/*Table structure for table `party_stage` */

DROP TABLE IF EXISTS `party_stage`;

CREATE TABLE `party_stage` (
  `id` int(11) NOT NULL COMMENT '入党阶段id',
  `name` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '入党阶段名称',
  `description` text CHARACTER SET utf8mb4 COMMENT '入党阶段描述',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_stage` */

insert  into `party_stage`(`id`,`name`,`description`,`is_deleted`,`create_time`,`modify_time`) values 
(1,'入党申请人',NULL,0,'2021-08-26 10:42:45','2021-08-26 10:42:50'),
(2,'入党积极分子',NULL,0,'2021-08-30 21:13:06','2021-08-30 21:13:08'),
(3,'发展对象',NULL,0,'2021-08-30 21:13:18','2021-08-30 21:13:20'),
(4,'预备党员',NULL,0,'2021-08-30 21:13:28','2021-08-30 21:13:29'),
(5,'正式党员',NULL,0,'2021-08-30 21:13:37','2021-08-30 21:13:39'),
(6,'党员',NULL,0,'2021-08-30 21:13:47','2021-08-30 21:13:48');

/*Table structure for table `party_student` */

DROP TABLE IF EXISTS `party_student`;

CREATE TABLE `party_student` (
  `student_id` char(12) CHARACTER SET utf8mb4 NOT NULL COMMENT '学号',
  `institute` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '学院',
  `grade` int(11) DEFAULT NULL COMMENT '年级',
  `major` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '专业',
  `class_name` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '班级',
  `class_position` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '班级职务',
  `dormitory_area` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '社区',
  `dormitory_no` int(10) DEFAULT NULL COMMENT '宿舍号',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别(男:1,女:0)',
  `nation` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '民族',
  `origin` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '籍贯',
  `id_card` varchar(25) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '身份证号',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `phone` char(11) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '手机号',
  `join_league_time` date DEFAULT NULL COMMENT '入团时间',
  `family_address` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '家庭住址',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_student` */

insert  into `party_student`(`student_id`,`institute`,`grade`,`major`,`class_name`,`class_position`,`dormitory_area`,`dormitory_no`,`gender`,`nation`,`origin`,`id_card`,`birthday`,`phone`,`join_league_time`,`family_address`,`is_deleted`,`create_time`,`modify_time`) values 
('111111',NULL,2021,'计算机','3班',NULL,'莞博社区',226,1,'汉族','广东省广州市海珠区',NULL,NULL,'13025670',NULL,'和平乡',0,'2021-09-29 16:25:30','2021-09-29 16:25:30'),
('201941301327',NULL,2019,'计科','19计科',NULL,'莞博社区',200324,1,'汉族','上海市上海市黄浦区',NULL,NULL,'13785192615',NULL,'广东省东莞理工学院',0,'2021-09-16 01:28:45','2021-09-16 01:28:45'),
('201942122139',NULL,0,'','',NULL,'',NULL,1,'汉族','广东省广州市黄埔区',NULL,NULL,'',NULL,'',0,'2021-09-15 11:59:46','2021-09-15 12:00:41'),
('201943302118',NULL,NULL,'计科','19',NULL,'莞逸社区',2136,1,'满族','广东省广州市海珠区',NULL,NULL,'1812522',NULL,'广东省东莞市常平大道88号',0,'2021-09-09 09:23:35','2021-09-09 09:23:41'),
('201944007137',NULL,2019,'智能制造','19智能制造4班',NULL,'莞博社区',230807,1,'汉族','广东省韶关市曲江区',NULL,NULL,'13719936849',NULL,'广东省汕头市',0,'2021-09-22 15:40:26','2021-09-22 22:00:48'),
('201944101218','',0,'网络空间安全','19网安','1','莞博社区',20311,1,'汉族','广东省广州市海珠区','',NULL,'13417184023',NULL,'',0,'2021-07-25 13:41:28','2021-09-12 22:20:18'),
('202041302128',NULL,2020,'软件工程','20杨班',NULL,'莞博社区',20320,1,'汉族','广东省汕头市龙湖区',NULL,NULL,'13692049619',NULL,'广东省',0,'2021-09-22 22:03:27','2021-09-22 22:03:27'),
('asd',NULL,NULL,'','asd',NULL,'莞馨社区',2013,1,'汉族','广东省广州市海珠区',NULL,NULL,'asd',NULL,'广东省东莞市常平大道88号',0,'2021-09-09 09:57:54','2021-09-14 20:50:13'),
('qeokdxj',NULL,NULL,'','asd',NULL,'莞馨社区',2013,1,'汉族','广东省广州市海珠区',NULL,NULL,'asd',NULL,'广东省东莞市常平大道88号',0,'2021-09-14 20:51:16','2021-09-14 20:54:58');

/*Table structure for table `party_task` */

DROP TABLE IF EXISTS `party_task`;

CREATE TABLE `party_task` (
  `id` int(11) NOT NULL COMMENT '入党任务id',
  `name` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '入党任务名称',
  `descript` text CHARACTER SET utf8mb4 COMMENT '入党任务介绍',
  `stage_id` int(11) NOT NULL COMMENT '所属阶段',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_task` */

insert  into `party_task`(`id`,`name`,`descript`,`stage_id`,`is_deleted`,`create_time`,`modify_time`) values 
(1,'提交电子版《入党申请书》','',1,0,'2021-07-25 17:35:38','2021-07-25 17:35:38'),
(2,'递交纸质版《入党申请书》','',1,0,'2021-08-30 21:15:28','2021-08-30 21:15:30'),
(3,'填写电子版《入党申请人培养考察手册》',NULL,1,0,'2021-08-30 21:16:13','2021-08-30 21:16:15'),
(4,'填写并递交纸质版《入党申请人培养考察手册》',NULL,1,0,'2021-08-30 21:16:36','2021-08-30 21:16:38'),
(5,'党组织派人进行谈话',NULL,1,0,'2021-08-30 21:16:48','2021-08-30 21:16:50'),
(6,'党员推荐、群团组织推优',NULL,1,0,'2021-08-30 21:16:59','2021-08-30 21:17:01'),
(7,'确定为入党积极分子',NULL,1,0,'2021-08-30 21:17:12','2021-08-30 21:17:14'),
(8,'报上级党委备案',NULL,1,0,'2021-08-30 21:17:25','2021-08-30 21:17:27'),
(9,'指定培养联系人',NULL,2,0,'2021-08-30 21:17:39','2021-08-30 21:17:41'),
(10,'培养教育考察（分党校培训）',NULL,2,0,'2021-08-30 21:17:58','2021-08-30 21:18:00'),
(11,'填写电子版《入党积极分子培养考察手册》',NULL,2,0,'2021-08-30 21:18:18','2021-08-30 21:18:20'),
(12,'填写并递交纸质版《入党积极分子培养考察手册》',NULL,2,0,'2021-08-30 21:18:29','2021-08-30 21:18:31'),
(13,'分党校培训考试',NULL,2,0,'2021-08-30 21:18:38','2021-08-30 21:18:40'),
(14,'列为发展对象',NULL,2,0,'2021-08-30 21:18:51','2021-08-30 21:18:53'),
(15,'报上级党委备案',NULL,2,0,'2021-08-30 21:28:09','2021-08-30 21:28:11'),
(16,'指定入党介绍人',NULL,3,0,'2021-08-30 21:28:23','2021-08-30 21:28:25'),
(17,'提交电子版政治审查材料',NULL,3,0,'2021-08-30 21:28:38','2021-08-30 21:28:40'),
(18,'提交纸质版政治审查材料',NULL,3,0,'2021-08-30 21:28:53','2021-08-30 21:28:55'),
(19,'集中培训（党校培训）',NULL,3,0,'2021-08-30 21:29:16','2021-08-30 21:29:17'),
(20,'党校培训考试',NULL,3,0,'2021-08-30 21:29:27','2021-08-30 21:29:29'),
(21,'提交电子版《自传》',NULL,3,0,'2021-08-30 21:29:37','2021-08-30 21:29:39'),
(22,'递交纸质版《自传》',NULL,3,0,'2021-08-30 21:29:50','2021-08-30 21:29:51'),
(23,'征求社区意见',NULL,3,0,'2021-08-30 21:30:01','2021-08-30 21:30:03'),
(24,'补充《入党积极分子培养考察手册》的意见等内容',NULL,3,0,'2021-08-30 21:30:15','2021-08-30 21:30:17'),
(25,'支部委员会审查',NULL,3,0,'2021-08-30 21:30:28','2021-08-30 21:30:30'),
(26,'党委预审',NULL,3,0,'2021-08-30 21:30:41','2021-08-30 21:30:43'),
(27,'填写电子版《中国共产党入党志愿书》',NULL,3,0,'2021-08-30 21:31:13','2021-08-30 21:31:15'),
(28,'填写并递交纸质版《中国共产党入党志愿书》',NULL,3,0,'2021-08-30 21:31:28','2021-08-30 21:31:30'),
(29,'支部大会讨论',NULL,3,0,'2021-08-30 21:31:39','2021-08-30 21:31:42'),
(30,'上级党委派人谈话',NULL,3,0,'2021-08-30 21:31:52','2021-08-30 21:31:53'),
(31,'提交上级党委审批',NULL,3,0,'2021-08-30 21:32:05','2021-08-30 21:32:07'),
(32,'接收为预备党员',NULL,3,0,'2021-08-30 21:32:16','2021-08-30 21:32:17'),
(33,'编入党支部和党小组',NULL,4,0,'2021-08-30 21:32:25','2021-08-30 21:32:27'),
(34,'入党宣誓',NULL,4,0,'2021-08-30 21:32:43','2021-08-30 21:32:45'),
(35,'继续考察教育',NULL,4,0,'2021-08-30 21:32:56','2021-08-30 21:32:58'),
(36,'填写电子版《预备党员培养考察手册》',NULL,4,0,'2021-08-30 21:33:12','2021-08-30 21:33:14'),
(37,'填写并递交纸质版《预备党员培养考察手册》',NULL,4,0,'2021-08-30 21:33:25','2021-08-30 21:33:27'),
(38,'征求社区意见',NULL,4,0,'2021-08-30 21:33:40','2021-08-30 21:33:41'),
(39,'提交电子版《转正申请书》',NULL,4,0,'2021-08-30 21:33:54','2021-08-30 21:33:56'),
(40,'递交纸质版《转正申请书》',NULL,4,0,'2021-08-30 21:34:08','2021-08-30 21:34:10'),
(41,'补充《预备党员培养考察手册》中的意见等内容',NULL,4,0,'2021-08-30 21:34:19','2021-08-30 21:34:22'),
(42,'参加答辩会',NULL,4,0,'2021-08-30 21:34:34','2021-08-30 21:34:36'),
(43,'支部大会讨论',NULL,4,0,'2021-08-30 21:34:46','2021-08-30 21:34:47'),
(44,'上级党委审批',NULL,4,0,'2021-08-30 21:34:58','2021-08-30 21:35:00'),
(45,'转为正式党员',NULL,4,0,'2021-08-30 21:35:13','2021-08-30 21:35:15'),
(46,'《入党申请书》',NULL,5,0,'2021-08-30 21:35:26','2021-08-30 21:35:28'),
(47,'《入党申请人培养考察手册》',NULL,5,0,'2021-08-30 21:35:38','2021-08-30 21:35:42'),
(48,'《入党积极分子培养考察手册》',NULL,5,0,'2021-08-30 21:35:54','2021-08-30 21:35:58'),
(49,'考察期个人思想汇报',NULL,5,0,'2021-08-30 21:36:08','2021-08-30 21:36:10'),
(50,'个人自传',NULL,5,0,'2021-08-30 21:36:21','2021-08-30 21:36:22'),
(51,'政治审查材料',NULL,5,0,'2021-08-30 21:36:35','2021-08-30 21:36:37'),
(52,'校党校结业证书',NULL,5,0,'2021-08-30 21:36:46','2021-08-30 21:36:48'),
(53,'综合预审报告',NULL,5,0,'2021-08-30 21:36:58','2021-08-30 21:36:59'),
(54,'《中国共产党入党志愿书》',NULL,5,0,'2021-08-30 21:37:08','2021-08-30 21:37:09'),
(55,'《转正申请书》',NULL,5,0,'2021-08-30 21:37:20','2021-08-30 21:37:22'),
(56,'《预备党员培养考察手册》',NULL,5,0,'2021-08-30 21:37:33','2021-08-30 21:37:35'),
(57,'预备期个人思想汇报',NULL,5,0,'2021-08-30 21:37:44','2021-08-30 21:37:46'),
(58,'预备党员转正答辩情况报告',NULL,5,0,'2021-08-30 21:37:56','2021-08-30 21:37:58'),
(59,'转正综合审查报告',NULL,5,0,'2021-08-30 21:38:10','2021-08-30 21:38:12'),
(60,'党员名单',NULL,6,0,'2021-08-30 21:38:22','2021-08-30 21:38:24');

/*Table structure for table `party_teacher` */

DROP TABLE IF EXISTS `party_teacher`;

CREATE TABLE `party_teacher` (
  `teacher_id` varchar(10) CHARACTER SET utf8mb4 NOT NULL COMMENT '老师id',
  `party_age` int(11) DEFAULT NULL COMMENT '党龄',
  `phone` char(11) CHARACTER SET utf8mb4 NOT NULL COMMENT '电话',
  `email` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '邮箱',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_teacher` */

insert  into `party_teacher`(`teacher_id`,`party_age`,`phone`,`email`,`is_deleted`,`create_time`,`modify_time`) values 
('1',0,'','',0,'2021-07-25 16:24:53','2021-07-25 16:24:53');

/*Table structure for table `party_user` */

DROP TABLE IF EXISTS `party_user`;

CREATE TABLE `party_user` (
  `user_id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户id',
  `name` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '学生姓名',
  `open_id` char(28) CHARACTER SET utf8mb4 NOT NULL COMMENT '微信id',
  `student_id` char(12) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '学生id',
  `teacher_id` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '老师id',
  `branch_id` char(16) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '党支部id',
  `branch_name` char(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '党支部名称',
  `group_id` char(16) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '党组id',
  `group_name` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '党组名称',
  `stage_id` int(11) DEFAULT NULL COMMENT '所属阶段',
  `stage` int(11) DEFAULT NULL COMMENT '期数',
  `task_id` int(11) DEFAULT NULL COMMENT '阶段任务',
  `status` int(6) NOT NULL DEFAULT '0' COMMENT '信息审核状态，1:通过 2:不通过',
  `status_reason` varchar(50) DEFAULT NULL COMMENT '不通过原因',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_user` */

insert  into `party_user`(`user_id`,`name`,`open_id`,`student_id`,`teacher_id`,`branch_id`,`branch_name`,`group_id`,`group_name`,`stage_id`,`stage`,`task_id`,`status`,`status_reason`,`is_deleted`,`create_time`,`modify_time`) values 
('1419162725304541186','aa','1',NULL,'1','1','网络空间安全党委','1','网络空间安全党支部',1,0,0,1,NULL,0,'2021-07-25 13:08:55','2021-07-25 16:24:53'),
('1427546477068054530','1','asd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,'2021-08-17 16:22:57','2021-08-17 16:22:57'),
('1427546569351131137','DJ','oDGfk5EUjusEQhlEhDM9PPc3uGu4','qeokdxj',NULL,'1','网络空间安全党委','1','软件工程党支部',1,1,1,1,NULL,0,'2021-08-17 16:23:19','2021-09-14 20:54:58'),
('1434120940534484993','1','3',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,'2021-09-04 19:47:31','2021-09-04 19:47:31'),
('1435863042427535361','马浚杰','oDGfk5IdOEaQFWl8sORsUFnNXYnQ','201944101218',NULL,'1','网络空间安全党委','3','网络空间安全党支部',3,10,NULL,1,NULL,0,'2021-09-09 15:10:01','2021-09-12 22:20:18'),
('1435863473421631489','覃政东','undefined','201942122139',NULL,'1','网络空间安全党委','1','软件工程党支部',1,1,NULL,0,NULL,0,'2021-09-09 15:11:43','2021-09-15 12:00:41'),
('1435865422367899649','谢佳博','oDGfk5G7YQ7Z8ws28BiIcAG03Wjs','201944007137',NULL,'1','网络空间安全党委','1','软件工程党支部',3,6,NULL,0,NULL,0,'2021-09-09 15:19:28','2021-09-22 22:00:48'),
('1435937254366248961','1','oDGfk5P_hjogXKdonzpQ24mm1h5k',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,'2021-09-09 20:04:54','2021-09-09 20:04:54'),
('1437040225414135810','1','oDGfk5KKQn8JxKH_skcZnjS6MFlw',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,'2021-09-12 21:07:43','2021-09-12 21:07:43'),
('1437040342691069954','1','oDGfk5BXWMp1e5Fttv_XNlf1jEac',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,'2021-09-12 21:08:11','2021-09-12 21:08:11'),
('1437588372137820162','1','oY_Sr5cLzjIG0tParJmzZB6JVAXI',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,'2021-09-14 09:25:51','2021-09-14 09:25:51'),
('1438188852987674625','mhr','oDGfk5MBZhdk6JFZgbd_8OmKvQr4','201941301327',NULL,'1','网络空间安全党委','1','软件工程党支部',2,6,NULL,0,NULL,0,'2021-09-16 01:11:57','2021-09-16 01:28:45'),
('1440580929910312961',NULL,'oDGfk5OY1BNjWLzc4Sl70_wGH6bs',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,'2021-09-22 15:37:13','2021-09-22 15:37:13'),
('1440677696093917185','王春光','oDGfk5AVy0tUihri_yWXswhPG3Gk','202041302128',NULL,'1','网络空间安全党委','1','软件工程党支部',2,3,NULL,0,NULL,0,'2021-09-22 22:01:43','2021-09-22 22:03:27'),
('1440680991923474433',NULL,'oDGfk5ClMBL2IvWXVgRRkuARisWQ',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,'2021-09-22 22:14:49','2021-09-22 22:14:49'),
('1443129455081000961','啦啦啦','oDGfk5NtemHuFW6aB0rwECsRPFhM','111111',NULL,'1','网络空间安全党委','1','软件工程党支部',1,1,NULL,0,NULL,0,'2021-09-29 16:24:08','2021-09-29 16:25:30');

/*Table structure for table `party_user_activity` */

DROP TABLE IF EXISTS `party_user_activity`;

CREATE TABLE `party_user_activity` (
  `id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT 'id',
  `user_id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户id',
  `activity_id` char(19) CHARACTER SET utf8mb4 NOT NULL COMMENT '活动id',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态 0：正常 1：请假 2:审批通过 3：审批不通过 4: 申请加入 5：申请不通过',
  `reason` varchar(30) DEFAULT NULL COMMENT '请假原因',
  `branch_id` varchar(19) DEFAULT NULL COMMENT '党支部id',
  `group_id` varchar(19) DEFAULT NULL COMMENT '党组id',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_user_activity` */

insert  into `party_user_activity`(`id`,`user_id`,`activity_id`,`status`,`reason`,`branch_id`,`group_id`,`is_deleted`,`create_time`,`modify_time`) values 
('1419572431546548979','1427546569351131137','1419572431459876245',3,'asd',NULL,NULL,0,'2021-08-20 10:57:57','2021-08-20 10:58:02'),
('1419572434015145684','1427546569351131137','1419572434015489632',6,'请假',NULL,NULL,0,'2021-08-17 20:30:06','2021-08-17 20:30:09'),
('1419572434021478569','1427546569351131137','1419572434024874963',6,' ',NULL,NULL,0,'2021-08-17 20:32:17','2021-08-17 20:32:19'),
('1419572434025148965','1427546569351131137','1419572434025148965',5,' ',NULL,NULL,0,'2021-08-17 20:31:00','2021-08-17 20:31:02'),
('1419572434029156489','1427546569351131137','1419572434029515152',4,' ',NULL,NULL,0,'2021-08-17 20:27:57','2021-08-17 20:28:00');

/*Table structure for table `party_user_stage` */

DROP TABLE IF EXISTS `party_user_stage`;

CREATE TABLE `party_user_stage` (
  `id` char(19) NOT NULL COMMENT '用户-阶段id',
  `user_id` char(19) NOT NULL COMMENT '用户id',
  `stage_id` int(11) NOT NULL COMMENT '阶段id',
  `time` date NOT NULL COMMENT '时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_user_stage` */

insert  into `party_user_stage`(`id`,`user_id`,`stage_id`,`time`,`is_deleted`,`create_time`,`modify_time`) values 
('1427546569351126578','1427546569351131137',2,'2021-09-23',0,'2021-09-14 16:10:49','2021-09-14 16:10:51'),
('1427546569351145986','1427546569351131137',1,'2021-09-01',0,'2021-09-14 15:51:16','2021-09-14 15:51:18'),
('1437087217146761217','1419162725304541186',1,'2021-07-25',0,'2021-09-13 00:14:27','2021-09-13 00:14:27'),
('1437091907917840386','1435863042427535361',1,'2021-07-25',0,'2021-09-13 00:33:05','2021-09-13 22:22:41'),
('1437421658826113026','1435863042427535361',3,'2021-07-25',0,'2021-09-13 22:23:24','2021-09-13 22:23:24');

/*Table structure for table `party_user_task` */

DROP TABLE IF EXISTS `party_user_task`;

CREATE TABLE `party_user_task` (
  `id` char(19) NOT NULL COMMENT '用户-任务id',
  `user_id` char(19) NOT NULL COMMENT '用户id',
  `task_id` int(11) NOT NULL COMMENT '任务id',
  `time` date NOT NULL COMMENT '时间',
  `path` varchar(70) DEFAULT NULL COMMENT '文件地址',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `party_user_task` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
