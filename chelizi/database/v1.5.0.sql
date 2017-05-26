-- 修改陪驾配置
DELETE FROM t_comment_tag WHERE ctid = 39;
DELETE FROM t_skill_relation WHERE ctid = 39;
UPDATE t_comment_tag SET tag = '驾驶习惯' WHERE ctid = 36;
UPDATE t_comment_tag SET tag = '技能状况' WHERE ctid = 37;
UPDATE t_comment_tag SET tag = '上路感觉' WHERE ctid = 38;


-- 20160225
--  ------------- 相关字段更改 -----------------
ALTER TABLE t_p_notice ADD COLUMN extra varchar(254) DEFAULT NULL COMMENT '附加字段：采用json格式保存url等信息';
ALTER TABLE t_u_student ADD COLUMN flowNo varchar(254) DEFAULT NULL COMMENT '学员报名流水号';
ALTER TABLE t_enroll_order ADD COLUMN ctime datetime DEFAULT NULL COMMENT '创建时间';
ALTER TABLE t_enroll_order ADD COLUMN mtime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';
-- 已有的订单初始化一个时间
UPDATE t_enroll_order SET ctime = NOW();

-- 开机广告
DROP TABLE IF EXISTS t_u_poster;
CREATE TABLE `t_u_poster` (
  `posterId` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `posterName` varchar(254) DEFAULT NULL COMMENT '广告名称',
  `posterPic` varchar(254) DEFAULT NULL COMMENT '广告图片名称 分辨率高',
  `posterPic2` varchar(254) DEFAULT NULL COMMENT '广告图片名称2 分辨率中',
  `posterPic3` varchar(254) DEFAULT NULL COMMENT '广告图片名称3 分辨率低',
  `posterUrl` varchar(255) DEFAULT NULL COMMENT '广告点击跳转url',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余字段',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`posterId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 新课程
DROP TABLE IF EXISTS t_s_coursenew;
CREATE TABLE `t_s_coursenew` (
  `coursenewNo` varchar(30) NOT NULL COMMENT '课程编号',
  `coursenewName` varchar(30) DEFAULT NULL COMMENT '课程名称',
  `courseTemId` int(10) DEFAULT NULL COMMENT '课程id 与原id对应',
  `level1` varchar(30) DEFAULT NULL COMMENT '一级导航',
  `level2` varchar(30) DEFAULT NULL COMMENT '二级导航',
  `subject` varchar(30) DEFAULT NULL COMMENT '考试科目',
  `subjectId` int(10) DEFAULT NULL COMMENT '科目id',
  `citys` varchar(30) DEFAULT NULL COMMENT '适用城市',
  `descripton` varchar(255) DEFAULT NULL COMMENT '描述',
  `content` varchar(255) DEFAULT NULL COMMENT '教学内容',
  `period` varchar(30) DEFAULT NULL COMMENT '建议学时',
  `drType` tinyint(4) DEFAULT NULL COMMENT '驾驶类型（1代表C1,2代表C2,3代表其它）',
  `tags` varchar(30) DEFAULT NULL COMMENT '技能点，多个用逗号隔开',
  `price1` int(11) DEFAULT NULL COMMENT '工作日价格',
  `price2` int(11) DEFAULT NULL COMMENT '周末价格',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`coursenewNo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- 新课程配置数据
INSERT INTO `t_s_coursenew` VALUES 
('E10001', '科目二基础训练', 1, '科目二', '基础训练', '科目二', 2, NULL, '刚完成科目一考试无科目二练习基础的学员', NULL, '6-8', 1, NULL, 17000, 19000, NULL, NULL),
('E10002', '科目二应试训练', 6, '科目二', '应试训练', '科目二', 2, NULL, '1、已进行不低于4小时的科目二基础训练；\r\n2、准备预约科目二考试的在学学员', NULL, '4-6', 1, NULL, 17000, 19000, NULL, NULL),
('E10003', '科目二考场模拟', 2, '科目二', '考场模拟', '科目二', 2, NULL, '1、已经预约成功科目二考试的学员；\r\n2、在2天内考试科目二的学员', NULL, '1-2', 1, NULL, 20000, 22000, NULL, NULL),
('E20001', '科目二基础训练', 11, '科目二', '基础训练', '科目二', 2, NULL, '刚完成科目一考试无科目二练习基础的学员', NULL, '6-8', 2, NULL, 18500, 20500, NULL, NULL),
('E20002', '科目二应试训练', 16, '科目二', '应试训练', '科目二', 2, NULL, '1、已进行不低于4小时的科目二基础训练；\r\n2、准备预约科目二考试的在学学员', NULL, '4-6', 2, NULL, 18500, 20500, NULL, NULL),
('E20003', '科目二考场模拟', 12, '科目二', '考场模拟', '科目二', 2, NULL, '1、已经预约成功科目二考试的学员；\r\n2、在2天内考试科目二的学员', NULL, '1-2', 2, NULL, 20500, 23500, NULL, NULL),
('S10001', '科目三基础训练', 3, '科目三', '基础训练', '科目三', 3, NULL, '刚完成科目二考试无科目三练习基础的学员', NULL, '2', 1, NULL, 17000, 19000, NULL, NULL),
('S10002', '科目三应试训练', 7, '科目三', '应试训练', '科目三', 3, NULL, '1、已进行不低于2小时的科目三基础训练；\r\n2、准备预约科目三考试的在学学员', NULL, '4-6', 1, NULL, 17000, 19000, NULL, NULL),
('S10003', '科目三考场模拟', 4, '科目三', '考场模拟', '科目三', 3, NULL, '1、已经预约成功科目三考试的学员；\r\n2、在2天内考试科目三的学员', NULL, '1-2', 1, NULL, 20000, 22000, NULL, NULL),
('S20001', '科目三基础训练', 13, '科目三', '基础训练', '科目三', 3, NULL, '刚完成科目二考试无科目三练习基础的学员', NULL, '2', 2, NULL, 18500, 20500, NULL, NULL),
('S20002', '科目三应试训练', 17, '科目三', '应试训练', '科目三', 3, NULL, '1、已进行不低于2小时的科目三基础训练；\r\n2、准备预约科目三考试的在学学员', NULL, '4-6', 2, NULL, 18500, 20500, NULL, NULL),
('S20003', '科目三考场模拟', 14, '科目三', '考场模拟', '科目三', 3, NULL, '1、已经预约成功科目三考试的学员；\r\n2、在2天内考试科目三的学员', NULL, '1-2', 2, NULL, 20500, 23500, NULL, NULL),
('P10001', '陪驾', 5, '陪驾', '陪驾', '陪驾', 5, NULL, '1、刚拿到驾照，跃跃欲试的新手党；\r\n2、反应能力、辨别方向能力、操控能力有点逊；\r\n3、回到家或出去玩停车停半天，关键还停不进去；', '1、哪里不会学哪里，哪里老错练哪里，强化训练，练会为止；\r\n2、跟车、超车、会车、变道、停车、倒车、弯道想学啥就学啥；', '4-6', 1, NULL, 17000, 17000, NULL, NULL),
('P20001', '陪驾', 15, '陪驾', '陪驾', '陪驾', 5, NULL, '1、刚拿到驾照，跃跃欲试的新手党；\r\n2、反应能力、辨别方向能力、操控能力有点逊；\r\n3、回到家或出去玩停车停半天，关键还停不进去；', '1、哪里不会学哪里，哪里老错练哪里，强化训练，练会为止；\r\n2、跟车、超车、会车、变道、停车、倒车、弯道想学啥就学啥；', '4-6', 2, NULL, 20000, 20000, NULL, NULL);


-- 用户反馈记录
DROP TABLE IF EXISTS t_u_feedback;
CREATE TABLE `t_u_feedback` (
  `fbid` int(20) NOT NULL AUTO_INCREMENT COMMENT '反馈id',
  `fbtitle` varchar(100) DEFAULT NULL COMMENT '标题',
  `fbcontent` text COMMENT '内容',
  `fbpicture` varchar(255) DEFAULT NULL COMMENT '图片URL',
  `userid` bigint(32) DEFAULT NULL COMMENT '用户id',
  `usertype` tinyint(16) DEFAULT NULL COMMENT '用户类型',
  `username` varchar(30) DEFAULT NULL COMMENT '用户姓名',
  `userphone` varchar(32) DEFAULT NULL COMMENT '用户手机号',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `state` tinyint(4) DEFAULT '0' COMMENT '状态：0-新反馈；1-已处理',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`fbid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- 更新评价标签
DROP TABLE IF EXISTS t_comment_tag;
CREATE TABLE `t_comment_tag` (
  `ctid` int(11) NOT NULL AUTO_INCREMENT COMMENT '价格表主键',
  `tag` varchar(50) DEFAULT NULL COMMENT '标签',
  `course_id` int(11) DEFAULT NULL COMMENT '类别id，针对有学生的标签，为驾考类型，1为C1,2为C2；针对于教练的标签，为评分数',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '标签类型：1代表教练的标签，2代表学员的标签',
  `good_bad` tinyint(4) DEFAULT NULL COMMENT '好评差评分类：1代表好评，0代表差评，只有教练标签有值',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  `cuid` int(11) DEFAULT NULL COMMENT '创建人id',
  `muid` int(11) DEFAULT NULL COMMENT '更新人id',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ctid`),
  UNIQUE KEY `ctid` (`ctid`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='评价标签表';

INSERT INTO `t_comment_tag` VALUES
(1, '教学时段不认真', 1, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(2, '服务态度很差', 1, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(3, '行为举止不检点', 1, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(4, '练车卫生环境差', 1, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(5, '不守时', 1, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(6, '教学有点故意推延', 2, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(7, '教学时开小差', 2, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(8, '言语恶劣', 2, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(9, '教练不注意个人卫生', 2, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(10, '时间观念不够', 2, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(11, '教学质量还可以', 3, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(12, '教练员比较注重个人卫生', 3, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(13, '有照预约教学课程进行教学', 3, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(14, '教学态度认真', 3, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(15, '耐心指导，有问必答', 3, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(16, '教练耐心教学', 4, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(17, '教练服务态度也很好', 4, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(18, '很注重个人卫生', 4, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(19, '时间观念强', 4, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(20, '教学认真不开小差', 4, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(21, '主动耐心传授考试规则细节、结束后验收掌握情况', 5, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(22, '教练很认真负责', 5, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(23, '主动联系学员', 5, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(24, '着装专业、整洁、工作时戴手套', 5, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(25, '整体服务很好', 5, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(26, '倒车入库', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(27, '侧方停车', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(28, '坡道定点', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(29, '直角转弯', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(30, '曲线行驶', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(31, '上路行驶', 1, 2, 1, 1, 0, 0, '2015-10-23 00:00:00', '2016-2-25 17:50:59'),
(32, '通过路口', 1, 2, 1, 1, 0, 0, '2015-10-23 00:00:00', '2016-2-25 17:51:00'),
(33, '通过特定区域', 1, 2, 1, 1, 0, 0, '2015-10-23 00:00:00', '2016-2-25 17:51:01'),
(34, '夜间行驶', 1, 2, 1, 1, 0, 0, '2015-10-23 00:00:00', '2016-2-25 17:51:02'),
(35, '综合评判', 1, 2, 1, 1, 0, 0, '2015-10-23 00:00:00', '2016-2-25 17:51:04'),
(36, '驾驶习惯', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2016-2-1 17:20:40'),
(37, '技能状况', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2016-2-1 17:20:40'),
(38, '上路感觉', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2016-2-1 17:20:40'),
(50, '科目二应试训练', 1, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:17'),
(51, '科目二考场模拟', 1, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:21'),
(52, '科目三基础训练', 1, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:13'),
(53, '科目三应试训练', 1, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:01'),
(54, '科目三考场模拟', 1, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:06'),
(57, '驾驶习惯', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(58, '技能状况', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(59, '上路感觉', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(60, '倒车入库', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(61, '侧方停车', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(62, '坡道定点', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(63, '直角转弯', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(64, '曲线行驶', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(65, '科目二应试训练', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:32'),
(66, '科目二考场模拟', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:37'),
(67, '科目三基础训练', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:28'),
(68, '科目三应试训练', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:46:07'),
(69, '科目三考场模拟', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:46:04');


-- 更新技能点
DROP TABLE IF EXISTS t_skill_relation;
CREATE TABLE `t_skill_relation` (
  `sid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctid` int(11) NOT NULL COMMENT '技能标签id',
  `city_id` int(11) DEFAULT NULL COMMENT '该技能需求的城市',
  `course_id` int(11) DEFAULT NULL COMMENT '该技能的课程',
  `subject_id` int(11) DEFAULT NULL COMMENT '该技能的科目',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  `cuid` int(11) DEFAULT NULL COMMENT '创建人id',
  `muid` int(11) DEFAULT NULL COMMENT '更新人id',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`sid`),
  UNIQUE KEY `sid` (`sid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='各课程技能点关系表';

INSERT INTO `t_skill_relation` VALUES 
(30, 36, 0, 5, 5, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(31, 57, 0, 15, 5, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:56:57'),
(32, 37, 0, 5, 5, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(33, 58, 0, 15, 5, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:00'),
(34, 38, 0, 5, 5, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(35, 59, 0, 15, 5, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:02'),
(36, 26, 0, 1, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(37, 60, 0, 11, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:03'),
(38, 27, 0, 1, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(39, 61, 0, 11, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:04'),
(40, 28, 0, 1, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(41, 62, 0, 11, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:05'),
(42, 29, 0, 1, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(43, 63, 0, 11, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:05'),
(44, 30, 0, 1, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(45, 64, 0, 11, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:06'),
(46, 50, 0, 6, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(47, 65, 0, 16, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:08'),
(48, 51, 0, 2, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(49, 66, 0, 12, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:09'),
(50, 52, 0, 3, 3, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(51, 67, 0, 13, 3, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:10'),
(52, 53, 0, 7, 3, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(53, 68, 0, 17, 3, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:11'),
(54, 54, 0, 4, 3, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(55, 69, 0, 14, 3, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:13');

-- 更新学员驾考类型
update t_u_student set applyCarType = '1' WHERE applyCarType = '0' or applyCarType = '3' or applyCarType ='';
update t_u_student set drtype = CAST(applyCarType AS signed) WHERE drType is null;
ALTER TABLE t_u_student MODIFY COLUMN applyCarType varchar(4) DEFAULT '1' COMMENT '申请的车牌类型(1代表C1,2代表C2)';











