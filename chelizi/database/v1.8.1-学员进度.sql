-- 驾校开理论课
DROP TABLE IF EXISTS t_enroll_theory;
CREATE TABLE `t_enroll_theory` (
  `theoryId` int(11) NOT NULL AUTO_INCREMENT COMMENT '理论课id',
  `schoolId` int(11) NOT NULL COMMENT '学校id',
  `classDate` datetime DEFAULT NULL COMMENT '上课日期',
  `classTime` varchar(50) DEFAULT NULL COMMENT '课程时间（09:00-18:00）',
  `className` varchar(50) DEFAULT NULL COMMENT '课程名称（学前理论）',
  `classPlace` varchar(255) DEFAULT NULL COMMENT '上课地点',
  `contactName` varchar(50) DEFAULT NULL COMMENT '联系人',
  `contactMobile` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `cardType` tinyint(4) DEFAULT '0' COMMENT '携带证件：0-身份证；1-军官证',
  `cardDesc` varchar(20) DEFAULT NULL COMMENT '携带证件描述（身份证）',
  `total` int(11) DEFAULT '0' COMMENT '已安排人数',
  `absence` int(11) DEFAULT '0' COMMENT '缺勤人数',
  `state` int(11) DEFAULT '0' COMMENT '状态：0-未确认；1-确认开课，待上课；2-已上课；3-已取消',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`theoryId`),
  key (`classDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1000 COMMENT='驾校开理论课';

-- 理论课上课学员
DROP TABLE IF EXISTS t_enroll_theory_student;
CREATE TABLE `t_enroll_theory_student` (
  `theoryId` int(11) NOT NULL COMMENT '理论课id',
  `studentId` bigint(20) NOT NULL COMMENT '学员id',
  `schoolId` int(11) NOT NULL COMMENT '学校id',
  `name` varchar(32) DEFAULT NULL COMMENT '学员姓名',
  `phoneNum` varchar(20) DEFAULT NULL COMMENT '学员电话',
  `sex` tinyint(4) DEFAULT '0' COMMENT '性别：0-女；1-男',
  `drType` tinyint(4) DEFAULT NULL COMMENT '准驾车型：1-C1;2-C2',
  `idNumber` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `flowNo` varchar(50) DEFAULT NULL COMMENT '流水号',
  `isImport` tinyint(4) DEFAULT '0' COMMENT '0-喱喱学员，1-驾校学员',
  `state` tinyint(4) DEFAULT '0' COMMENT '考勤状态：0-未记考勤；1-出勤；2-缺勤',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `isdel` tinyint(4) DEFAULT '0' COMMENT '是否已删除：0-未删除；1-已删除',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`theoryId`,`studentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='理论课上课学员';


-- 驾校开长考课
DROP TABLE IF EXISTS t_enroll_longtrain;
CREATE TABLE `t_enroll_longtrain` (
  `ltId` int(11) NOT NULL AUTO_INCREMENT COMMENT '长考id',
  `schoolId` int(11) NOT NULL COMMENT '学校id',
  `classDate` datetime DEFAULT NULL COMMENT '上课日期',
  `classTime` varchar(50) DEFAULT NULL COMMENT '课程时间（09:00-18:00）',
  `classPlace` varchar(255) DEFAULT NULL COMMENT '上课地点',
  `coachId` int(11) NOT NULL COMMENT '教练id',
  `coachSex` tinyint(4) DEFAULT '1' COMMENT '教练性别',
  `coachIdCard` VARCHAR(20) DEFAULT NULL COMMENT '教练身份证',  
  `contactName` varchar(50) DEFAULT NULL COMMENT '联系人，教练',
  `contactMobile` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `carNo` varchar(11) DEFAULT NULL COMMENT '车牌号',
  `carType` varchar(11) DEFAULT NULL COMMENT '车品牌-大众捷达等',
  `drType` tinyint(4) DEFAULT '1'COMMENT '准驾类型：1-C1,2-C2',
  `carrys` varchar(20) DEFAULT NULL COMMENT '携带物品描述（身份证）',
  `total` int(11) DEFAULT '0' COMMENT '已安排人数',
  `failed` int(11) DEFAULT '0' COMMENT '不及格人数',
  `state` int(11) DEFAULT '0' COMMENT '状态：0-未确认；1-确认开课，待上课；2-已上课；3-已取消',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`ltId`),
  key (`classDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1000 COMMENT='驾校开长考课';

-- 长考课上课学员
DROP TABLE IF EXISTS t_enroll_longtrain_student;
CREATE TABLE `t_enroll_longtrain_student` (
  `ltId` int(11) NOT NULL COMMENT '长考id',
  `studentId` bigint(20) NOT NULL COMMENT '学员id',
  `schoolId` int(11) NOT NULL COMMENT '学校id',
  `name` varchar(32) DEFAULT NULL COMMENT '学员姓名',
  `phoneNum` varchar(20) DEFAULT NULL COMMENT '学员电话',
  `sex` tinyint(4) DEFAULT '1' COMMENT '性别：0-女；1-男',
  `drType` tinyint(4) DEFAULT '1' COMMENT '准驾车型：1-C1;2-C2',
  `idNumber` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `flowNo` varchar(50) DEFAULT NULL COMMENT '流水号',
  `isImport` tinyint(4) DEFAULT '0' COMMENT '0-喱喱学员，1-驾校学员',
  `state` tinyint(4) DEFAULT '0' COMMENT '成绩状态：0-未记成绩；1-合格；2-不合格',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `isdel` tinyint(4) DEFAULT '0' COMMENT '是否已删除：0-未删除；1-已删除',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`ltId`,`studentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='长考课上课学员';

-- 导入约考登记表
DROP TABLE IF EXISTS t_enroll_exam_reg;
CREATE TABLE `t_enroll_exam_reg` (
  `tableNo` VARCHAR(50) NOT NULL COMMENT '表文件编号',
  `flowNo` bigint(20) NOT NULL COMMENT '流水号',
  `schoolId` int(11) NOT NULL COMMENT '学校id',
  `studentId` bigint(20) DEFAULT NULL COMMENT '学员id',
  `idNumber` varchar(20) DEFAULT NULL COMMENT '身份证',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `schoolName` varchar(20) DEFAULT NULL COMMENT '所属驾校',
  `drType` tinyint(4) DEFAULT NULL COMMENT '准驾车型',
  `registState` int(4) DEFAULT '0' COMMENT '登记状态: 0-正常排队；1-滚动登记；2-已取消',
  `registTime` timestamp NULL DEFAULT NULL COMMENT '登记时间',
  `registPeriod` datetime DEFAULT NULL COMMENT '登记周期',
  `waitDays` int(11) DEFAULT NULL COMMENT '等候天数',
  `bookResult` int(4) DEFAULT '0' COMMENT '预约结果: 0-等待排队；1-已排到队；2-没排到队',
  `examPlace` varchar(50) DEFAULT NULL COMMENT '考场',
  `examDate` datetime DEFAULT NULL COMMENT '考试日期',
  `examTime` VARCHAR(50) DEFAULT NULL COMMENT '考试时间（09:00-10:00）',
  `examOrder` varchar(50) DEFAULT NULL COMMENT '场次',
  `queueTime` datetime DEFAULT NULL COMMENT '排队时间',
  `isdel` tinyint(4) DEFAULT 0 COMMENT '有效状态：0代表未删除，有效；1代表已删除，无效；',
  `applystate` int(11) DEFAULT '0' COMMENT '解析学员状态：0-未约考；1-排队中；100：排队成功；101：排队失败',
  `importState` int(11) DEFAULT '0' COMMENT '导入状态：0-未开始；1-已导入',
  `subjectId` int(11) DEFAULT NULL COMMENT '科目id',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`tableNo`,`flowNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='导入约考登记表';

-- 导入考试成绩表
DROP TABLE IF EXISTS t_enroll_exam_result;
CREATE TABLE `t_enroll_exam_result` (
  `tableNo` VARCHAR(50) NOT NULL COMMENT '表文件编号',
  `flowNo` bigint(20) NOT NULL COMMENT '流水号',
  `schoolId` int(11) NOT NULL COMMENT '学校id',
  `studentId` bigint(20) DEFAULT NULL COMMENT '学员id',
  `idNumber` varchar(20) DEFAULT NULL COMMENT '身份证',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `schoolName` varchar(20) DEFAULT NULL COMMENT '所属驾校',
  `examDate` datetime DEFAULT NULL COMMENT '考试日期',
  `score` int(11) DEFAULT 0 COMMENT '考试分数',
  `applystate` int(11) DEFAULT '0' COMMENT '考试结果--解析学员状态：100：合格；101：不合格',
  `importState` int(11) DEFAULT '0' COMMENT '导入状态：0-未开始；1-已导入',
  `subjectId` int(11) DEFAULT NULL COMMENT '科目id',
  `isdel` tinyint(4) DEFAULT 0 COMMENT '有效状态：0代表未删除，有效；1代表已删除，无效；',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`tableNo`,`flowNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='导入考试成绩表';

-- 考试成绩表导入记录
DROP TABLE IF EXISTS t_enroll_import_record;
CREATE TABLE `t_enroll_import_record` (
  `tableNo` VARCHAR(50) NOT NULL COMMENT '表文件编号',
  `schoolId` int(11) NOT NULL COMMENT '学校id',
  `type` tinyint(4) DEFAULT '1' COMMENT '表文件类型：1-预约登记表；2-考试成绩表',
  `subjectId` int(11) DEFAULT NULL COMMENT '科目id',
  `remark` varchar(20) DEFAULT NULL COMMENT '描述（科目一预约登记、科目二考试成绩）',
  `total` int(11) DEFAULT '0' COMMENT '总数据',
  `valid` int(11) DEFAULT '0' COMMENT '有效数据',
  `state` int(11) DEFAULT '0' COMMENT '导入状态：0-未开始；1-已导入',
  `operator` varchar(20) DEFAULT NULL COMMENT '操作人',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`tableNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表导入记录';


-- 考试须知配置表
DROP TABLE IF EXISTS t_enroll_exam_notice;
CREATE TABLE `t_enroll_exam_notice` (
  `subjectId` int(11) DEFAULT NULL COMMENT '科目id',
  `notice` text DEFAULT NULL COMMENT '考试须知：多条用+号连接',
  `isdel` tinyint(4) DEFAULT '0' COMMENT '是否已删除：0-未删除；1-已删除',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`subjectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试须知配置表';


-- 插入测试数据


-- 菜单权限
INSERT INTO `db_lili_cms`.`t_c_permission` (`channel_type`, `id`, `name`, `pid`, `url`, `type`, `enabled`, `remark`) VALUES ('1', '101900', '理论课管理', '0', '/theoretical-lessons', '1', '0', NULL);
INSERT INTO `db_lili_cms`.`t_c_permission` (`channel_type`, `id`, `name`, `pid`, `url`, `type`, `enabled`, `remark`) VALUES ('1', '102000', '长考看板', '0', '/long-exam', '1', '0', NULL);
INSERT INTO `db_lili_cms`.`t_c_permission` (`channel_type`, `id`, `name`, `pid`, `url`, `type`, `enabled`, `remark`) VALUES ('1', '102100', '约考管理', '0', '/order-exam', '1', '0', NULL);




