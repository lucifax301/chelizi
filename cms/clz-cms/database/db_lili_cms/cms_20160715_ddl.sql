CREATE TABLE `t_s_trfield_file` (
  `creatorName` varchar(32) DEFAULT NULL COMMENT '创建人',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `schoolId` int(11) DEFAULT NULL COMMENT '所属驾校id',
  `lge` decimal(20,14) DEFAULT NULL COMMENT '训练场经度',
  `lae` decimal(20,14) DEFAULT NULL COMMENT '训练场纬度',
  `posDesc` varchar(32) DEFAULT NULL COMMENT '训纬场位置描述',
  `reverseLim` int(11) DEFAULT NULL COMMENT '倒车训练容纳量',
  `phoneNum` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `region` int(11) DEFAULT NULL COMMENT '所属区',
  `name` varchar(64) DEFAULT NULL COMMENT '训练场名字',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余：用于存放临时数据等',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `billNo` varchar(255) DEFAULT NULL COMMENT '表编号',
  `schoolName` varchar(255) DEFAULT NULL COMMENT '驾校名称',
  `cityName` varchar(255) DEFAULT NULL COMMENT '所属城市名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;



CREATE TABLE `t_p_car_file` (
  `creatorName` varchar(32) DEFAULT NULL COMMENT '创建人',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `carType` varchar(32) DEFAULT NULL COMMENT '车类型',
  `age` int(11) DEFAULT '0' COMMENT '车龄',
  `carPower` tinyint(4) DEFAULT NULL COMMENT '动力（1汽油/2柴油/3混合/4电动）',
  `driveType` tinyint(4) DEFAULT '1' COMMENT '驾驶类型（1代表C1,2代表C2,3代表其它）',
  `carNo` varchar(11) DEFAULT NULL COMMENT '车牌号',
  `coachPhoneNum` varchar(16) DEFAULT NULL COMMENT '关联教练电话',
  `buyTime` varchar(32) DEFAULT NULL COMMENT '购买时间',
  `coachName` varchar(32) DEFAULT NULL COMMENT '关联教练姓名',
  `schoolId` tinyint(4) DEFAULT '0' COMMENT '驾校ID（驾校ID为0表示为独立学员）',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `carEngineNo` varchar(128) DEFAULT NULL COMMENT '发动机号码',
  `carVin` varchar(128) DEFAULT NULL COMMENT '车辆识别码',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余：用于存放临时数据等',
  `billNo` varchar(255) DEFAULT NULL COMMENT '表编号',
  `schoolName` varchar(255) DEFAULT NULL COMMENT '驾校名称',
  `cityName` varchar(255) DEFAULT NULL COMMENT '所属城市名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;


CREATE TABLE `t_school_data_file` (
  `creatorName` varchar(32) DEFAULT NULL COMMENT '创建人',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `billNo` varchar(255) DEFAULT NULL COMMENT '表编号',
  `schoolName` varchar(255) DEFAULT NULL COMMENT '驾校名称',
  `schoolId` int(11) DEFAULT '0' COMMENT '驾校ID',
  `fileType` tinyint(4) NOT NULL COMMENT '文件类型：1-教练信息，2-学员信息，3-训练场信息，4-车辆信息，',
  `sum` int(11) DEFAULT '0' COMMENT '文件总记录数',
  `sucSum` int(11) DEFAULT '0' COMMENT '处理成功总数',
  `failSum` int(11) DEFAULT '0' COMMENT '处理失败总数',
  `status` tinyint(4) DEFAULT '1' COMMENT '处理状态：1-待处理，2-处理中，3-处理成功，4-处理失败',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文件创建时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '备注,冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=564 DEFAULT CHARSET=utf8 COMMENT='驾校数据导入记录表';

CREATE TABLE `t_u_coach_file` (
  `creatorName` varchar(32) DEFAULT NULL COMMENT '创建人',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idNumber` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `name` varchar(32) DEFAULT NULL COMMENT '学员姓名',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别(0,女；1，男；2，未知)',
  `age` smallint(6) DEFAULT NULL COMMENT '学员年龄',
  `hometown` varchar(128) DEFAULT NULL COMMENT '户籍地',
  `phoneNum` varchar(16) DEFAULT NULL COMMENT '学员电话',
  `coachCard` varchar(20) DEFAULT NULL COMMENT '教练证号',
  `schoolId` tinyint(4) DEFAULT '0' COMMENT '驾校ID（驾校ID为0表示为独立学员）',
  `cityId` int(11) DEFAULT NULL COMMENT '学员所属城市id',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `coachCardDate` varchar(32) DEFAULT NULL COMMENT '教练证到期日期',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余：用于存放临时数据等',
  `billNo` varchar(255) DEFAULT NULL COMMENT '表编号',
  `schoolName` varchar(255) DEFAULT NULL COMMENT '驾校名称',
  `cityName` varchar(255) DEFAULT NULL COMMENT '所属城市名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COMMENT='驾校学员导入表';

CREATE TABLE `t_u_student_file` (
  `creatorName` varchar(32) DEFAULT NULL COMMENT '创建人',
  `flowNo` varchar(32) DEFAULT NULL COMMENT '学员报名流水号',
  `StuCoachName` varchar(32) DEFAULT NULL COMMENT '关联教练姓名',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idNumber` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `name` varchar(32) DEFAULT NULL COMMENT '学员姓名',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别(0,女；1，男；2，未知)',
  `age` smallint(6) DEFAULT NULL COMMENT '学员年龄',
  `hometown` varchar(128) DEFAULT NULL COMMENT '户籍地',
  `phoneNum` varchar(16) DEFAULT NULL COMMENT '学员电话',
  `course1` smallint(6) DEFAULT NULL COMMENT '科目1成绩',
  `course2` smallint(6) DEFAULT NULL COMMENT '科目2成绩',
  `course3` smallint(6) DEFAULT NULL COMMENT '科目3成绩',
  `course4` smallint(6) DEFAULT NULL COMMENT '科目4成绩',
  `applyCarType` varchar(4) DEFAULT '1' COMMENT '申请的车牌类型(1代表C1,2代表C2)',
  `schoolId` tinyint(4) DEFAULT '0' COMMENT '驾校ID（驾校ID为0表示为独立学员）',
  `drType` tinyint(4) DEFAULT NULL COMMENT '驾驶类型（1代表C1,2代表C2,3代表其它）',
  `cityId` int(11) DEFAULT NULL COMMENT '学员所属城市id',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余：用于存放临时数据等',
  `billNo` varchar(255) DEFAULT NULL COMMENT '表编号',
  `schoolName` varchar(255) DEFAULT NULL COMMENT '驾校名称',
  `cityName` varchar(255) DEFAULT NULL COMMENT '所属城市名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8 COMMENT='驾校学员导入表';

