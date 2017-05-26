--
-- Table structure for table `t_brokerage_course`
--

DROP TABLE IF EXISTS `t_brokerage_course`;
CREATE TABLE `t_brokerage_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `regionId` int(11) NOT NULL,
  `courseTmpId` int(11) NOT NULL COMMENT '课程id',
  `dltype` tinyint(4) NOT NULL COMMENT '驾照类型：1代表C1，2代表C2',
  `dateRule` varchar(50) NOT NULL,
  `brokerageType` tinyint(4) NOT NULL COMMENT '佣金类型，1=比例，2=具体金额',
  `brokerage` int(11) NOT NULL COMMENT '佣金的数值',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='课程的佣金模板';

--
-- Dumping data for table `t_brokerage_course`
--

LOCK TABLES `t_brokerage_course` WRITE;
INSERT INTO `t_brokerage_course` VALUES (1,100100,1,1,'1|2|3|4|5',1,15,'科目二基础训练C1，周一至周五'),(2,100100,1,1,'6|7',1,15,'科目二基础训练C1，周六周日'),(3,100100,11,2,'1|2|3|4|5',1,15,'科目二基础训练C2，周一至周五'),(4,100100,11,2,'6|7',1,15,'科目二基础训练C2，周六周日'),(5,100100,6,1,'1|2|3|4|5',1,15,'科目二应试训练C1，周一至周五'),(6,100100,6,1,'6|7',1,15,'科目二应试训练C1，周六周日'),(7,100100,16,2,'1|2|3|4|5',1,15,'科目二应试训练C2，周一至周五'),(8,100100,16,2,'6|7',1,15,'科目二应试训练C2，周六周日'),(9,100100,2,1,'1|2|3|4|5',1,15,'科目二考场模拟C1，周一至周五'),(10,100100,2,1,'6|7',1,15,'科目二考场模拟C1，周六周日'),(11,100100,12,2,'1|2|3|4|5',1,15,'科目二考场模拟C2，周一至周五'),(12,100100,12,2,'6|7',1,15,'科目二考场模拟C2，周六周日'),(13,100100,3,1,'1|2|3|4|5',1,15,'科目三基础训练C1，周一至周五'),(14,100100,3,1,'6|7',1,15,'科目三基础训练C1，周六周日'),(15,100100,13,2,'1|2|3|4|5',1,15,'科目三基础训练C2，周一至周五'),(16,100100,13,2,'6|7',1,15,'科目三基础训练C2，周六周日'),(17,100100,7,1,'1|2|3|4|5',1,15,'科目三应试训练C1，周一至周五'),(18,100100,7,1,'6|7',1,15,'科目三应试训练C1，周六周日'),(19,100100,17,2,'1|2|3|4|5',1,15,'科目三应试训练C2，周一至周五'),(20,100100,17,2,'6|7',1,15,'科目三应试训练C2，周六周日'),(21,100100,4,1,'1|2|3|4|5',1,15,'科目三考场模拟C1，周一至周五'),(22,100100,4,1,'6|7',1,15,'科目三考场模拟C1，周六周日'),(23,100100,14,2,'1|2|3|4|5',1,15,'科目三考场模拟C2，周一至周五'),(24,100100,14,2,'6|7',1,15,'科目三考场模拟C2，周六周日'),(25,100100,5,1,'1|2|3|4|5',1,15,'陪驾C1，周一至周五'),(26,100100,5,1,'6|7',1,15,'陪驾C1，周六周日'),(27,100100,15,2,'1|2|3|4|5',1,15,'陪驾C2，周一至周五'),(28,100100,15,2,'6|7',1,15,'陪驾C2，周六周日');
UNLOCK TABLES;

--
-- Table structure for table `t_brokerage_enroll`
--
DROP TABLE IF EXISTS `t_brokerage_enroll`;
CREATE TABLE `t_brokerage_enroll` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `regionId` int(11) NOT NULL COMMENT '地区id',
  `payee` tinyint(4) NOT NULL COMMENT '收款方，1=政府，2=驾校',
  `projectType` tinyint(4) NOT NULL COMMENT '项目类型，1=科目一报考费，2=科目二报考费，3=科目三报考费，4=工本费，5=长考费，6=档案费',
  `brokerageType` tinyint(4) NOT NULL COMMENT '佣金类型，1=比例，2=具体金额',
  `brokerage` int(11) NOT NULL DEFAULT '0' COMMENT '佣金的数值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='报名的佣金模板';