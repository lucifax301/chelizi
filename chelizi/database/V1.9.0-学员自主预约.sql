drop TABLE if exists t_student_class;
CREATE TABLE `t_student_class` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '排班id',
  `orderId` varchar(34) NOT NULL COMMENT '预生成订单id',
  `studentId` bigint(11) NOT NULL COMMENT '学员id',
  `stuName` varchar(32) DEFAULT NULL COMMENT '冗余：学生名称',
  `stuImg` varchar(254) DEFAULT NULL COMMENT '冗余：学生头像',
  `stuMobile` varchar(20) DEFAULT NULL COMMENT '冗余：学生电话',
  `dltype` tinyint(4) NOT NULL DEFAULT '1' COMMENT '驾照类型：1代表C1,2代表C2',
  `courseId` int(10) DEFAULT NULL COMMENT '课程id',
  `courseName` varchar(30) DEFAULT NULL COMMENT '课程名称',
  `cstart` datetime DEFAULT NULL COMMENT '上课时间',
  `cend` datetime DEFAULT NULL COMMENT '下课时间',
  `clznum` tinyint(4) DEFAULT NULL COMMENT '课时',
  `price` int(11) DEFAULT NULL COMMENT '订单总价格，单位分',
  `lge` double DEFAULT NULL COMMENT '学员位置经度',
  `lae` double DEFAULT NULL COMMENT '学员位置纬度',
  `placeInfo` varchar(255) DEFAULT NULL COMMENT '地点描述',
  `regionId` int(11) NOT NULL COMMENT '城市id',
  `state` tinyint(4) DEFAULT '0' COMMENT '排班状态：0-正常；1-已成功；2-已取消；3-已超时；',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学员排班表';

drop TABLE if exists t_student_class_pool;
CREATE TABLE `t_student_class_pool` (
  `orderId` varchar(34) NOT NULL COMMENT '预生成订单id',
  `coachId` bigint(11) NOT NULL COMMENT '教练id',
  `studentId` bigint(11) NOT NULL COMMENT '学员id',
  `coachName` varchar(32) DEFAULT NULL COMMENT '教练姓名',
  `coachImg` varchar(254) DEFAULT NULL COMMENT '教练头像',
  `coachMobile` varchar(20) DEFAULT NULL COMMENT '教练手机号',
  `schoolName` varchar(20) DEFAULT NULL COMMENT '驾校名称',
  `starLevel` int(11) DEFAULT '80' COMMENT '星级评分',
  `distance` double DEFAULT NULL COMMENT '教练与学员的距离',
  `placeId` int(11) DEFAULT NULL COMMENT '如果是科目二则为训练场ID，如果是科目三则可以为空',
  `placeName` varchar(50) DEFAULT NULL COMMENT '训练场名称，或科目三定位中的名称，可以为空',
  `placeLge` double DEFAULT NULL COMMENT '上课地点的经度',
  `placeLae` double DEFAULT NULL COMMENT '上课地点的纬度',
  `state` tinyint(4) DEFAULT '0' COMMENT '状态：0-已推送教练；1-教练已接单；2-教练已取消；',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`orderId`,`coachId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学员排班教练接单记录表';

drop TABLE if exists t_student_book_price;
CREATE TABLE `t_student_book_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `regionId` int(11) NOT NULL COMMENT '城市id',
  `courseTmpId` int(11) NOT NULL COMMENT '课程id',
  `dltype` tinyint(4) NOT NULL COMMENT '驾照类型：1代表C1，2代表C2',
  `dateRule` varchar(50) NOT NULL COMMENT '日期规则',
  `price` int(11) NOT NULL COMMENT '冗余：报名总价，单位分',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学员自主预约价格表';

INSERT INTO `t_student_book_price` (`id`, `regionId`, `courseTmpId`, `dltype`, `dateRule`, `price`, `description`) VALUES 
(1, 100100, 1, 1, '1|2|3|4|5', 15000, '科目二基础训练，周一到周五，手动挡'),
(2, 100100, 1, 1, '6|7', 17000, '科目二基础训练，周六到周日，手动挡'),
(3, 100100, 11, 2, '1|2|3|4|5', 16800, '科目二基础训练，周一到周五，自动挡'),
(4, 100100, 11, 2, '6|7', 19000, '科目二基础训练，周六到周日，自动挡'),
(5, 100100, 3, 1, '1|2|3|4|5', 15000, '科目三基础训练，周一到周五，手动挡'),
(6, 100100, 3, 1, '6|7', 17000, '科目三基础训练，周六到周日，手动挡'),
(7, 100100, 13, 2, '1|2|3|4|5', 16800, '科目三基础训练，周一到周五，自动挡'),
(8, 100100, 13, 2, '6|7', 19000, '科目三基础训练，周六到周日，自动挡'),
(9, 100100, 6, 1, '1|2|3|4|5', 15000, '科目二应试训练，周一到周五，手动挡'),
(10, 100100, 6, 1, '6|7', 17000, '科目二应试训练，周六到周日，手动挡'),
(11, 100100, 16, 2, '1|2|3|4|5', 16800, '科目二应试训练，周一到周五，自动挡'),
(12, 100100, 16, 2, '6|7', 19000, '科目二应试训练，周六到周日，自动挡'),
(13, 100100, 7, 1, '1|2|3|4|5', 15000, '科目三应试训练，周一到周五，手动挡'),
(14, 100100, 7, 1, '6|7', 17000, '科目三应试训练，周六到周日，手动挡'),
(15, 100100, 17, 2, '1|2|3|4|5', 16800, '科目三应试训练，周一到周五，自动挡'),
(16, 100100, 17, 2, '6|7', 19000, '科目三应试训练，周六到周日，自动挡'),
(17, 100100, 2, 1, '1|2|3|4|5', 20000, '科目二考场模拟，周一到周五，手动挡'),
(18, 100100, 2, 1, '6|7', 20000, '科目二考场模拟，周六到周日，手动挡'),
(19, 100100, 12, 2, '1|2|3|4|5', 20000, '科目二考场模拟，周一到周五，自动挡'),
(20, 100100, 12, 2, '6|7', 20000, '科目二考场模拟，周六到周日，自动挡'),
(21, 100100, 4, 1, '1|2|3|4|5', 20000, '科目三考场模拟，周一到周五，手动挡'),
(22, 100100, 4, 1, '6|7', 20000, '科目三考场模拟，周六到周日，手动挡'),
(23, 100100, 14, 2, '1|2|3|4|5', 20000, '科目三考场模拟，周一到周五，自动挡'),
(24, 100100, 14, 2, '6|7', 20000, '科目三考场模拟，周六到周日，自动挡'),
(25, 100100, 5, 1, '1|2|3|4|5', 12000, '陪驾，周一到周五，手动挡'),
(26, 100100, 5, 1, '6|7', 12000, '陪驾，周六到周日，手动挡'),
(27, 100100, 15, 2, '1|2|3|4|5', 12000, '陪驾，周一到周五，自动挡'),
(28, 100100, 15, 2, '6|7', 12000, '陪驾，周六到周日，自动挡');

ALTER TABLE `db_lili`.`t_u_coach`
  ADD COLUMN `firstLogin` TIMESTAMP NULL   COMMENT '第一次登录时间' AFTER `extra`,
  ADD COLUMN `lastLogin` TIMESTAMP NULL   COMMENT '最后一次登录时间' AFTER `firstLogin`;
  
ALTER TABLE `db_lili`.`t_u_student`
  ADD COLUMN `firstLogin` TIMESTAMP NULL   COMMENT '第一次登录时间' AFTER `vipPackageId`,
  ADD COLUMN `lastLogin` TIMESTAMP NULL   COMMENT '最后一次登录时间' AFTER `firstLogin`;

