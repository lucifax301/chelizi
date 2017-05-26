CREATE TABLE `t_user_push` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `userType` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户类型：1-教练，2-学员',
  `name` varchar(32) DEFAULT NULL COMMENT '教练姓名',
  `phoneNum` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `cityId` int(11) DEFAULT NULL COMMENT '教练注册所在的城市',
  `carNo` varchar(11) DEFAULT NULL COMMENT '车牌号',
  `driveType` tinyint(4) DEFAULT '1' COMMENT '车辆类型（1代表C1,2代表C2,3代表其它）',
  `isImport` tinyint(4) DEFAULT '0' COMMENT '是否是导入的教练',
  `coachClass` tinyint(4) DEFAULT '0' COMMENT '带教班别0-VP',
  `pushType` tinyint(4) DEFAULT '0' COMMENT '推送类型 0-收车出车，1-出车，2-收车',
  `pushContent` varchar(255) DEFAULT NULL COMMENT '推送内容',
  `isDel` tinyint(4) DEFAULT '0' COMMENT '是否启用:0-启用，1-不启用',
  `creator` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `utime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='推送用户存放表';


CREATE TABLE `t_micro_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `fileType` tinyint(4) DEFAULT '1' COMMENT '文件类型：1-视频，2-H5课件',
  `levelOne` tinyint(4) DEFAULT '1' COMMENT '一级目录:1-科目一，2-科目二，3-科目三，4-科目四,5-微课',
  `oneName` varchar(32) DEFAULT NULL COMMENT '一级目录名称',
  `levelTwo` tinyint(4) DEFAULT '1' COMMENT '二级目录,参见目录体系',
  `twoImg` varchar(255) DEFAULT NULL COMMENT '目录二图',
  `twoName` varchar(32) DEFAULT NULL COMMENT '二级目录名称',
  `levelThree` varchar(16) DEFAULT '1' COMMENT '三级目录，参见目录体系',
  `threeImg` varchar(255) DEFAULT NULL COMMENT '三级目录图',
  `threeName` varchar(32) DEFAULT NULL COMMENT '三级目录名称',
  `microId` varchar(64) DEFAULT NULL COMMENT '微课编号',
  `microName` varchar(64) DEFAULT NULL COMMENT '微课名称',
  `microTime` varchar(16) DEFAULT NULL COMMENT '微课时长',
  `version` varchar(32) DEFAULT NULL COMMENT '文件版本号',
  `versionVest` varchar(32) DEFAULT NULL COMMENT '版本归属',
  `filePath` varchar(128) DEFAULT NULL COMMENT '文件存放七牛路径',
  `microRemark` varchar(255) DEFAULT NULL COMMENT '微课简介',
  `url` varchar(255) DEFAULT NULL COMMENT '微课七牛路径',
  `isDel` tinyint(4) DEFAULT '0' COMMENT '是否启用:0-启用，1-不启用',
  `creator` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `utime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='微课信息表';





-- 车辆信息变更

ALTER TABLE t_coach_comment 
ADD  `anonymity` tinyint(4) DEFAULT '2' COMMENT '匿名评价：1-是，2-否';



--  增加投诉文案
delete from `t_cancel_reason` where utype=4;
INSERT INTO `t_cancel_reason` (`crid`,`reason`,`utype`,`isdel`,`cuid`,`muid`,`ctime`,`mtime` ) VALUES 
(30, '不能按时上课或者提前下课', 4, 0, 0, 0, '2016-7-4 15:22:03', '2016-7-4 15:22:04'),
(31, '教学态度不好', 4, 0, 0, 0, '2016-7-4 15:22:20', '2016-7-4 15:22:21'),
(32, '拒绝接单', 4, 0, 0, 0, '2016-7-4 15:22:37', '2016-7-4 15:22:40'),
(33, '接送时间太久或者不接送', 4, 0, 0, 0, '2016-7-4 15:22:54', '2016-7-4 15:22:56'),
(34, '要求支付额外费用', 4, 0, 0, 0, '2016-7-4 15:23:12', '2016-7-4 15:23:13');

--  删除原学员取消订单文案
delete from `t_cancel_reason` where utype=2;

-- 增加学员取消订单文案
INSERT INTO `t_cancel_reason` (`crid`,`reason`,`utype`,`isdel`,`cuid`,`muid`,`ctime`,`mtime` ) VALUES 
(1, '教练让我取消', 2, 0, 0, 0, '2016-7-4 17:43:41', '2016-7-4 17:43:42'),
(2, '订单选择错误', 2, 0, 0, 0, '2016-7-4 17:43:57', '2016-7-4 17:43:58'),
(3, '突然有急事', 2, 0, 0, 0, '2016-7-4 17:44:17', '2016-7-4 17:44:17'),
(4, '对方态度不好', 2, 0, 0, 0, '2016-7-4 17:44:32', '2016-7-4 17:44:33'),
(5, '选错教练', 2, 0, 0, 0, '2016-7-4 17:45:38', '2016-7-4 17:45:39'),
(6, '等待时间太久', 2, 0, 0, 0, '2016-7-4 17:45:53', '2016-7-4 17:45:54');


INSERT INTO `t_share_user` VALUES ('eed8490442a611e696afd89d672a2800', '学员推荐分享', 20000, NULL, NULL, 2, 2, NULL, 'http://www.lilixc.com/appPro/stu_share/voucher.html', '分享文字描述', '活动规则', NULL, NULL, 2, 0, NULL, NULL, NULL, '2016-7-6 11:13:37');


-- 微课视频、课件
INSERT INTO `t_micro_class` (id,fileType,levelOne,oneName,levelTwo,twoImg,twoName,levelThree,threeImg,threeName,microId,microName,microTime,version,versionVest,filePath,microRemark,url,isDel,creator,ctime,updater,utime,remark) VALUES 
(1, 1, 1, '科目一', 1, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B1%BD%E8%BD%A6%E5%B8%B8%E8%AF%86.png', '汽车常识', '1.1', 'http://o7d94lzvx.bkt.clouddn.com/%E8%BD%A6%E8%BE%86%E7%BB%93%E6%9E%84.png', '车辆结构', 'A1-01-01', '汽车基本结构', '05:19', 'V.03', '深港学院', 'video/micro/A1-01-01.mp4', '了解车辆的基本构成', 'http://o7d94lzvx.bkt.clouddn.com/video/micro/A1-01-01.mp4', 0, 'system', '2016-7-14 15:13:52', NULL, '2016-7-14 16:41:48', NULL),
(2, 1, 1, '科目一', 1, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B1%BD%E8%BD%A6%E5%B8%B8%E8%AF%86.png', '汽车常识', '1.2', 'http://o7d94lzvx.bkt.clouddn.com/%E5%AE%89%E5%85%A8%E8%A3%85%E7%BD%AE.jpg', '安全装置', 'A1-02-01', '汽车仪表微视频课程', '08:58', NULL, '深港学院', 'video/micro/A1-02-01.mp4', '熟悉各主要安全装置的配置\r\n掌握仪表、报警灯的作用\r\n掌握安全头枕、安全带、安全气囊、灯光、喇叭、防抱死制动系统的作用', 'http://o7d94lzvx.bkt.clouddn.com/video/micro/A1-02-01.mp4', 0, 'system', '2016-7-14 15:19:20', NULL, '2016-7-14 16:42:13', NULL),
(3, 1, 1, '科目一', 1, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B1%BD%E8%BD%A6%E5%B8%B8%E8%AF%86.png', '汽车常识', '1.2', 'http://o7d94lzvx.bkt.clouddn.com/%E5%AE%89%E5%85%A8%E8%A3%85%E7%BD%AE.jpg', '安全装置', 'A1-02-02', '被动安全装置', '6:00', 'v2.0_1', '深港学院', 'video/micro/A1-02-02.mp4', '掌握安全头枕、安全带、安全气囊、灯光、喇叭、防抱死制动系统的作', 'http://o7d94lzvx.bkt.clouddn.com/video/microA1-02-02.mp4', 0, 'system', '2016-7-7 20:14:26', NULL, '2016-7-14 15:38:07', NULL),
(4, 1, 1, '科目一', 1, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B1%BD%E8%BD%A6%E5%B8%B8%E8%AF%86.png', '汽车常识', '1.3', 'http://o7d94lzvx.bkt.clouddn.com/%E6%89%8B%E6%93%8D%E4%BD%9C%E6%9C%BA%E6%9E%84.jpg', '操纵机构', 'A1-03-01', '手操作机构', '6:00', 'v2.0_1', '深港学院', 'video/micro/A1-03-01.mp4', '掌握转向盘、加速操纵装置、制动操纵装置、变速器操纵杆、驻车制动操纵装置及其他操纵装置的作用', 'http://o7d94lzvx.bkt.clouddn.com/video/micro/A1-03-01.mp4', 0, 'system', '2016-7-7 20:14:31', NULL, '2016-7-14 15:37:33', NULL),
(5, 1, 1, '科目一', 1, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B1%BD%E8%BD%A6%E5%B8%B8%E8%AF%86.png', '汽车常识', '1.3', 'http://o7d94lzvx.bkt.clouddn.com/%E8%84%9A%E6%93%8D%E4%BD%9C%E6%9C%BA%E6%9E%84.jpg', '操纵机构', 'A1-03-02', '脚操作机构', '6:00', 'v2.0', '深港学院', 'video/micro/A1-03-02.mp4', '掌握离合器踏板的作用', 'http://o7d94lzvx.bkt.clouddn.com/video/micro/A1-03-02.mp4', 0, 'system', '2016-7-7 20:14:36', NULL, '2016-7-14 15:37:26', NULL),
(6, 1, 1, '科目一', 1, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B1%BD%E8%BD%A6%E5%B8%B8%E8%AF%86.png', '汽车常识', '1.4', 'http://o7d94lzvx.bkt.clouddn.com/%E5%AE%89%E5%85%A8%E8%A3%85%E7%BD%AE.jpg', '车辆性能', 'A1-04-01', '车辆性能与安全行车', '6:00', NULL, '深港学院', 'video/micro/A1-04-01.mp4', '了解车辆性能与安全行车的关系', 'http://o7d94lzvx.bkt.clouddn.com/video/micro/A1-04-01.mp4', 0, 'system', '2016-7-7 20:14:42', NULL, '2016-7-14 15:37:13', NULL),
(7, 1, 1, '科目一', 1, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B1%BD%E8%BD%A6%E5%B8%B8%E8%AF%86.png', '汽车常识', '1.4', 'http://o7d94lzvx.bkt.clouddn.com/%E5%AE%89%E5%85%A8%E8%A3%85%E7%BD%AE.jpg', '车辆性能', 'A1-04-02 ', '汽车性能与行车安全', '03:22', NULL, '深港学院', 'video/micro/A1-04-02.mp4', '了解制动性能对行车安全的影响', 'http://o7d94lzvx.bkt.clouddn.com/video/micro/A1-04-02.mp4', 0, 'system', '2016-7-14 15:28:40', NULL, '2016-7-14 16:42:03', NULL),
(8, 1, 1, '科目一', 1, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B1%BD%E8%BD%A6%E5%B8%B8%E8%AF%86.png', '汽车常识', '1.6', 'http://o7d94lzvx.bkt.clouddn.com/%E6%B1%BD%E8%BD%A6%E7%94%A8%E6%B2%B9%E4%B8%8E%E6%B6%B2.png', '运行材料', 'A1-06-01', '汽车用油与液', '06:02', NULL, '深港学院', 'video/micro/A1-06-01.mp4', '了解燃油、润滑油、冷却液、风窗玻璃清洗液等运行材料的使用常识', 'http://o7d94lzvx.bkt.clouddn.com/video/micro/A1-06-01.mp4', 0, 'system', '2016-7-14 15:35:12', NULL, '2016-7-15 09:58:18', NULL),
(9, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.1', 'http://o7d94lzvx.bkt.clouddn.com/%E5%87%86%E9%A9%BE%E8%BD%A6%E5%9E%8B.png', '驾照领用', 'A2-01-01', '准驾车型', '09:00', 'v1.0', '深港学院', NULL, '1.掌握机动车驾驶证准驾车型分类及代号 2.了解各类车型驾驶证的准考要求', 'http://www.lilixc.com/active/app/wk/zjcx/MSPlayer.html', 0, 'system', '2016-7-8 10:55:11', NULL, '2016-7-15 09:42:45', NULL),
(10, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.1', 'http://o7d94lzvx.bkt.clouddn.com/%E9%A9%BE%E9%A9%B6%E8%AF%81%E7%94%B3%E9%A2%86.png', '驾照领用', 'A2-01-02', '驾驶证申领', '08:20', 'v1.0', '深港学院', NULL, '1.掌握机动车驾驶证申领的条件，尤其是小型汽车 2.禁止申领驾驶证的规定', 'http://www.lilixc.com/active/app/wk/jszsl/MSPlayer.html', 0, 'system', '2016-7-8 10:55:11', NULL, '2016-7-15 09:42:50', NULL),
(11, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.1', 'http://o7d94lzvx.bkt.clouddn.com/%E8%80%83%E8%AF%95%E6%A0%87%E5%87%86.png', '驾照领用', 'A2-01-03', '考试标准', '08:20', 'v1.0', '深港学院', NULL, '1. 熟练掌握机动车驾驶证考试准备和考试要求 2. 重点学习理论科目一考试和实操科目二、三考试的实际操作和考试要求', 'http://www.lilixc.com/active/app/wk/ksbz/MSPlayer.html', 0, 'system', '2016-7-8 10:55:11', NULL, '2016-7-15 09:42:51', NULL),
(12, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.1', 'http://o7d94lzvx.bkt.clouddn.com/%E9%A9%BE%E9%A9%B6%E8%AF%81%E7%A7%AF%E5%88%86%E5%88%B6%E5%BA%A6.png', '驾照领用', 'A2-01-04', '驾驶证积分制度', '10:20', 'v1.0', '深港学院', NULL, '1.熟练掌握机动车驾驶证积分制度，包括积分周期和行为要求；2.了解驾驶证的记分周期、记分标准、严重记分行为和一般的记分行为；3. 重点掌握交通违法行为一次记12分、6分、3分、1分的主要情形。', 'http://www.lilixc.com/active/app/wk/jszjfzd/MSPlayer.html', 0, 'system', '2016-7-8 10:55:11', NULL, '2016-7-15 09:42:53', NULL),
(13, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.1', 'http://o7d94lzvx.bkt.clouddn.com/%E5%8F%91%E8%AF%81%E3%80%81%E8%A1%A5%E8%AF%81%E3%80%81%E6%8D%A2%E8%AF%81.png', '驾照领用', 'A2-01-05', '发证、补证、换证', '07:00', 'v1.0', '深港学院', NULL, '1. 了解机动车驾驶证，发证、补证、换证制度；2. 掌握发证要求、补证条件、换证规定以及逾期换证的后果。', 'http://www.lilixc.com/active/app/wk/fzbzhz/MSPlayer.html', 0, 'system', '2016-7-8 10:55:11', NULL, '2016-7-15 09:42:58', NULL),
(14, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.2', 'http://o7d94lzvx.bkt.clouddn.com/%E6%8C%87%E8%B7%AF%E6%A0%87%E5%BF%97.png', '道路通行规则', 'A2-02-07', '指路标志', '03:50', NULL, '深港学院', NULL, '1.了解指路标志的种类、识别； 2.掌握指路标志的作用。', 'http://www.lilixc.com/active/app/wk/zlbz/MSPlayer.html', 0, 'system', '2016-7-14 17:30:01', NULL, '2016-7-15 10:13:56', NULL),
(15, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.2', 'http://o7d94lzvx.bkt.clouddn.com/%E9%AB%98%E9%80%9F%E5%85%AC%E8%B7%AF%E6%A0%87%E5%BF%97.png', '道路通行规则', 'A2-02-08', '高速公路标志', '03:50', NULL, '深港学院', NULL, '1. 掌握常见的高速公路标志的含义；2. 轻松识别高速公路标志的作用。', 'http://www.lilixc.com/active/app/wk/gsglbz/MSPlayer.html', 0, 'system', '2016-7-14 17:32:28', NULL, '2016-7-15 10:13:56', NULL),
(16, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.2', 'http://o7d94lzvx.bkt.clouddn.com/%E9%81%93%E8%B7%AF%E6%96%BD%E5%B7%A5%E6%A0%87%E5%BF%97%E5%8F%8A%E5%85%B6%E5%AE%83.png', '道路通行规则', 'A2-02-09', '道路施工标志及其它', '03:50', NULL, '深港学院', NULL, '通过本门课程的学习，你将会熟练掌握道路施工安全标志和辅助标志。', 'http://www.lilixc.com/active/app/wk/dlsg/MSPlayer.html', 0, 'system', '2016-7-14 17:34:48', NULL, '2016-7-15 10:13:56', NULL),
(17, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.2', 'http://o7d94lzvx.bkt.clouddn.com/%E4%BA%A4%E9%80%9A%E6%A0%87%E7%BA%BF.png', '道路通行规则', 'A2-02-10', '交通标线', '03:50', NULL, '深港学院', NULL, '1.掌握交通标线的基本分类 2.识别各类交通标线', 'http://www.lilixc.com/active/app/wk/jtbx/MSPlayer.html', 0, 'system', '2016-7-14 17:47:56', NULL, '2016-7-15 10:13:56', NULL),
(18, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.2', 'http://o7d94lzvx.bkt.clouddn.com/%E4%BA%A4%E9%80%9A%E8%AD%A6%E5%AF%9F%E6%8C%87%E6%8C%A5%E6%89%8B%E5%8A%BF%E4%BF%A1%E5%8F%B7.png', '道路通行规则', 'A2-02-11', '交通警察指挥手势信号', '03:50', NULL, '深港学院', NULL, '1.掌握交警指挥手势信号的种类和作用。2.识别交警指挥手势信号', 'http://www.lilixc.com/active/app/wk/jtjczh/MSPlayer.html', 0, 'system', '2016-7-14 17:48:45', NULL, '2016-7-15 10:13:56', NULL),
(19, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.2', 'http://o7d94lzvx.bkt.clouddn.com/%E6%9C%BA%E5%8A%A8%E8%BD%A6%E9%80%9A%E8%A1%8C%E8%A7%84%E5%88%99.png', '道路通行规则', 'A2-02-12', '机动车通行规则', '03:50', NULL, '深港学院', NULL, '1. 掌握右侧通行、分道通行、路口通行及安全等通行原则。2.重点掌握车辆在特殊情况下行驶速度的规定。', 'http://www.lilixc.com/active/app/wk/jdctx/MSPlayer.html', 0, 'system', '2016-7-14 17:49:02', NULL, '2016-7-15 10:13:56', NULL),
(20, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.2', 'http://o7d94lzvx.bkt.clouddn.com/%E5%8F%98%E6%9B%B4%E8%BD%A6%E9%81%93%E7%9B%B8%E5%85%B3%E6%9D%A1%E4%BE%8B.png', '道路通行规则', 'A2-02-13', '变更车道相关条例', '03:50', NULL, '深港学院', NULL, '1.了解机动车行驶过程中变更车道的基本规则。2.掌握车辆变更车道使转向灯的使用。3.掌握车辆变更车道时应遵守的规定。', 'http://www.lilixc.com/active/app/wk/bgcdtl/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(21, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.2', 'http://o7d94lzvx.bkt.clouddn.com/%E8%A1%8C%E9%A9%B6%E9%99%90%E5%88%B6%E8%B6%85%E8%BD%A6%E4%B8%8E%E8%B7%9F%E8%BD%A6.png', '道路通行规则', 'A2-02-14', '行驶限制超车与跟车', '03:50', NULL, '深港学院', NULL, '1.重点掌握不同条件下限制超车的规定。2.掌握不同条件下车辆应保持的安全距离。', 'http://www.lilixc.com/active/app/wk/xsxzcc/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(22, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.2', 'http://o7d94lzvx.bkt.clouddn.com/%E4%BC%9A%E8%BD%A6%E8%A7%84%E5%AE%9A.png', '道路通行规则', 'A2-02-15', '会车规定', '03:50', NULL, '深港学院', NULL, '1.掌握会车时行车与避让规则 2.重点掌握在没有中心隔离设施或者没有中心线的道路上的会车规定3.重点掌握特殊车辆的会车规定', 'http://www.lilixc.com/active/app/wk/hcgd/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(23, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.2', 'http://o7d94lzvx.bkt.clouddn.com/%E9%81%BF%E8%AE%A9%E8%A1%8C%E4%BA%BA%E4%B8%8E%E9%9D%9E%E6%9C%BA%E5%8A%A8%E8%BD%A6.png', '道路通行规则', 'A2-02-16', '避让行人与非机动车', '03:50', NULL, '深港学院', NULL, '1.了解机动车通过交叉路口等的基本原则 2.掌握机动车与行人、非机动车发生事故后的赔偿比例划分原则。3.掌握行人和非机动车违反道路通行的处罚', 'http://www.lilixc.com/active/app/wk/brxr/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(24, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.2', 'http://o7d94lzvx.bkt.clouddn.com/%E6%8E%89%E5%A4%B4%E4%B8%8E%E5%80%92%E8%BD%A6.png', '道路通行规则', 'A2-02-17', '掉头与倒车', '03:50', NULL, '深港学院', NULL, '1.熟练掌握禁止掉头、允许掉头的规定和了解掉头时的注意事项。2.熟练掌握禁止倒车的规定，了解倒车时的注意事项。', 'http://www.lilixc.com/active/app/wk/dtydc/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(25, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.3', 'http://o7d94lzvx.bkt.clouddn.com/%E9%A9%BE%E9%A9%B6%E8%A1%8C%E4%B8%BA%E8%A6%81%E6%B1%82.png', '违法处罚', 'A2-03-01', '驾驶行为要求', '03:50', NULL, '深港学院', NULL, '1.认识驾驶行为要求与规范正确认知行驶前、中、后的规范操作要求。2.重点理解向外抛洒杂物的危害性', 'http://www.lilixc.com/active/app/wk/jsxwyq/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(26, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.3', 'http://o7d94lzvx.bkt.clouddn.com/%E9%A9%BE%E9%A9%B6%E4%BA%BA%E5%AE%89%E5%85%A8%E8%B4%A3%E4%BB%BB.png', '违法处罚', 'A2-03-02', '驾驶人安全责任', '03:50', NULL, '深港学院', NULL, '1.了解驾驶人安全责任 2.了解并杜绝驾驶“十不”行为，掌握车辆日常安全检查与维护', 'http://www.lilixc.com/active/app/wk/jsraqzr/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(27, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.3', 'http://o7d94lzvx.bkt.clouddn.com/%E9%81%BF%E8%AE%A9%E7%89%B9%E6%AE%8A%E8%BD%A6%E8%BE%86.png', '违法处罚', 'A2-03-03', '避让特殊车辆', '03:50', NULL, '深港学院', NULL, '1.了解避让特殊车辆通行规则 2.了解避让特殊车辆义务及违章处罚 3.了解因避让特殊车辆而违章的处理及避让的驾驶技巧', 'http://www.lilixc.com/active/app/wk/brtscl/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(28, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.3', 'http://o7d94lzvx.bkt.clouddn.com/%E5%81%9C%E8%BD%A6%E8%A7%84%E5%AE%9A.png', '违法处罚', 'A2-03-04', '停车规定', '03:50', NULL, '深港学院', NULL, '1.了解停车规定与要求 2.了解临时停车地点的选择及相关规定与注意事项 3.了解车辆停放的场地与类型', 'http://www.lilixc.com/active/app/wk/tcgd/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(29, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.3', 'http://o7d94lzvx.bkt.clouddn.com/%E9%AB%98%E9%80%9F%E5%85%AC%E8%B7%AF%E8%A1%8C%E8%BD%A6.png', '违法处罚', 'A2-03-05', '高速公路行车', '03:50', NULL, '深港学院', NULL, '1. 掌握高速公路行车规范与要求 2.掌握高速行车时速要求、转向灯规范、跟车安全距离、特殊气候操作规范以及认知不良的禁忌驾驶行为', 'http://www.lilixc.com/active/app/wk/gsglxc/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(30, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.3', 'http://o7d94lzvx.bkt.clouddn.com/%E6%95%85%E9%9A%9C%E8%BD%A6%E8%AD%A6%E7%A4%BA%E8%A6%81%E6%B1%82.png', '违法处罚', 'A2-03-06', '故障车警示要求', '03:50', NULL, '深港学院', NULL, '1.掌握故障车辆警示操作的基础要求 2.掌握车辆在普通路、高速公路以及弯道地段发生故障的警示要求', 'http://www.lilixc.com/active/app/wk/gzcjsyq/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(31, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.4', 'http://o7d94lzvx.bkt.clouddn.com/%E9%A9%BE%E9%A9%B6%E7%9A%84%E7%A6%81%E6%AD%A2%E8%A1%8C%E4%B8%BA.png', '车辆登记', 'A2-04-01', '驾驶的禁止行为', '03:50', NULL, '深港学院', NULL, '了解驾驶员驾驶的禁止行为。', 'http://www.lilixc.com/active/app/wk/jsdjzxw/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(32, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.4', 'http://o7d94lzvx.bkt.clouddn.com/%E8%BF%9D%E6%B3%95%E8%A1%8C%E4%B8%BA%E7%9A%84%E7%A7%8D%E7%B1%BB.png', '车辆登记', 'A2-04-02', '违法行为的种类', '03:50', NULL, '深港学院', NULL, '了解参与交通违法的处罚种类包括警告、罚款、暂扣或者吊销机动车驾驶证、拘留。', 'http://www.lilixc.com/active/app/wk/wfxwdzl/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(33, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.4', 'http://o7d94lzvx.bkt.clouddn.com/%E8%BF%9D%E5%8F%8D%E4%BA%A4%E9%80%9A%E4%BF%A1%E5%8F%B7%E7%9A%84%E5%A4%84%E7%BD%9A.png', '车辆登记', 'A2-04-03', '违反交通信号的处罚', '03:50', NULL, '深港学院', NULL, '1.了解违反交通信号的典型行为。2.掌握违反交通信号的处罚规定。', 'http://www.lilixc.com/active/app/wk/wfjtxh/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(34, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.4', 'http://o7d94lzvx.bkt.clouddn.com/%E9%85%92%E5%90%8E%E7%AD%89%E9%A9%BE%E9%A9%B6%E7%9A%84%E5%A4%84%E7%BD%9A.png', '车辆登记', 'A2-04-04', '酒后等驾驶的处罚', '03:50', NULL, '深港学院', NULL, '1.了解法规对酒驾、毒驾、药驾的处罚规定。2.了解主动酒驾与被动酒驾的区别。3.了解引起被动酒驾的原因。', 'http://www.lilixc.com/active/app/wk/jhjscf/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(35, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.4', 'http://o7d94lzvx.bkt.clouddn.com/%E9%A9%BE%E9%A9%B6%E8%AF%81%E8%BF%9D%E6%B3%95%E7%9A%84%E5%A4%84%E7%BD%9A.png', '车辆登记', 'A2-04-05', '驾驶证违法的处罚', '03:50', NULL, '深港学院', NULL, '1.了解无证驾驶的处罚规定及扣分规则。2.了解未随身携带驾驶证驾驶处罚规定。', 'http://www.lilixc.com/active/app/wk/jszwf/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(36, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.4', 'http://o7d94lzvx.bkt.clouddn.com/%E5%8F%B7%E7%89%8C%E8%BF%9D%E6%B3%95%E7%9A%84%E5%A4%84%E7%BD%9A.png', '车辆登记', 'A2-04-06', '号牌违法的处罚', '03:50', NULL, '深港学院', NULL, '1.掌握关于机动车号牌的安装规定。2.重点掌握机动车号码牌使用的违法行为和处罚规则。', 'http://www.lilixc.com/active/app/wk/hpwf/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(37, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.4', 'http://o7d94lzvx.bkt.clouddn.com/%E8%B6%85%E5%91%98%E8%B6%85%E8%BD%BD%E8%BF%9D%E6%B3%95%E7%9A%84%E5%A4%84%E7%BD%9A.png', '车辆登记', 'A2-04-07', '超员超载违法的处罚', '03:50', NULL, '深港学院', NULL, '了解机动车“超员、超载”违法行为处罚标准及扣分规则。', 'http://www.lilixc.com/active/app/wk/cyczwf/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(38, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.4', 'http://o7d94lzvx.bkt.clouddn.com/%E7%AE%80%E6%98%93%E7%A8%8B%E5%BA%8F%E5%A4%84%E7%BD%9A.png', '车辆登记', 'A2-04-08', '简易程序处罚', '03:50', NULL, '深港学院', NULL, '1.了解哪些交通事故可以自行协商处理；哪些交通事故必须立即处理。2.了解交通事故处理的标准流程', 'http://www.lilixc.com/active/app/wk/jycxcf/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(39, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.5', 'http://o7d94lzvx.bkt.clouddn.com/%E6%9C%BA%E5%8A%A8%E8%BD%A6%E7%99%BB%E8%AE%B0.png', '事故处理', 'A2-05-01', '机动车登记', '03:50', NULL, '深港学院', NULL, '1.了解机动车注册登记、变更、转移、抵押、注销登记的情形及规范要求\r\n2.掌握改变机动车车身颜色、更换发动机、更换车身或者车架等必须到车管所备案', 'http://www.lilixc.com/active/app/wk/jdcdj/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(40, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.5', 'http://o7d94lzvx.bkt.clouddn.com/%E6%9C%BA%E5%8A%A8%E8%BD%A6%E5%8F%B7%E7%89%8C%E4%BD%BF%E7%94%A8.png', '事故处理', 'A2-05-02', '机动车号牌使用', '03:50', NULL, '深港学院', NULL, '1.熟练掌握机动车号牌申领及使用规定 2.重点掌握机动车号牌使用规定，应随车携带检验合格标志、保险标志、驾驶证、行驶证。', 'http://www.lilixc.com/active/app/wk/jdchpsy/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(41, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.5', 'http://o7d94lzvx.bkt.clouddn.com/%E6%9C%BA%E5%8A%A8%E8%BD%A6%E5%AE%89%E5%85%A8%E6%8A%80%E6%9C%AF%E6%A3%80%E9%AA%8C.png', '事故处理', 'A2-05-03', '机动车安全技术检验', '03:50', NULL, '深港学院', NULL, '1.了解机动车技术检验规定。2.掌握小型、微型非营运载客汽车的安全技术检验规定。', 'http://www.lilixc.com/active/app/wk/jdcaqjs/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(42, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.6', 'http://o7d94lzvx.bkt.clouddn.com/%E4%BA%A4%E9%80%9A%E4%BA%8B%E6%95%85%E7%8E%B0%E5%9C%BA%E5%A4%84%E7%90%86.png', '地方法规', 'A2-06-01', '交通事故现场处理', '03:50', NULL, '深港学院', NULL, '1.掌握交通事故现场处理方法。2.重点掌握如何防止二次事故、人员伤害、证据保全、快处快赔，侧重于自我保护意识、人身救助和事故处理流程讲解。', 'http://www.lilixc.com/active/app/wk/jtsgxccl/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(43, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.6', 'http://o7d94lzvx.bkt.clouddn.com/%E4%BA%A4%E9%80%9A%E4%BA%8B%E6%95%85%E5%A4%84%E7%90%86%E7%A8%8B%E5%BA%8F.png', '地方法规', 'A2-06-02', '交通事故处理程序', '03:50', NULL, '深港学院', NULL, '1.了解交警处理交通事故的一般程序。2.掌握交警处理交通事故的五大步骤。', 'http://www.lilixc.com/active/app/wk/jtsgclcx/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(44, 2, 1, '科目一', 2, 'http://o7d94lzvx.bkt.clouddn.com/%E6%B3%95%E5%BE%8B%E6%B3%95%E8%A7%84.png', '法律法规', '2.6', 'http://o7d94lzvx.bkt.clouddn.com/%E4%BA%A4%E9%80%9A%E4%BA%8B%E6%95%85%E8%AE%A4%E5%AE%9A%E4%B8%8E%E8%B5%94%E5%81%BF.png', '地方法规', 'A2-06-03', '交通事故认定与赔偿', '03:50', NULL, '深港学院', NULL, '1.了解常见交通事故的判定原则. 2.掌握交通事枚快速处理的责任认定的原则.', 'http://www.lilixc.com/active/app/wk/jtsgrdypc/MSPlayer.html', 0, 'system', '2016-7-14 17:55:40', NULL, '2016-7-15 10:13:56', NULL),
(45, 2, 1, '科目一', 3, 'http://o7d94lzvx.bkt.clouddn.com/%E4%BF%A1%E5%8F%B7%E4%B8%8E%E8%A7%84%E5%88%99.png', '信号与规则', '3.1', 'http://o7d94lzvx.bkt.clouddn.com/%E4%BA%A4%E9%80%9A%E4%BF%A1%E5%8F%B7%E7%A7%8D%E7%B1%BB.png', '交通信号', 'A3-01-01', '交通信号种类', '04:40', 'v1.0', '深港学院', NULL, '1.识别不同交通信号的种类；2.掌握各种信号的作用。', 'http://www.lilixc.com/active/app/wk/jtxhzl/MSPlayer.html', 0, 'system', '2016-7-8 10:55:11', NULL, '2016-7-15 09:44:12', NULL),
(46, 2, 1, '科目一', 3, 'http://o7d94lzvx.bkt.clouddn.com/%E4%BF%A1%E5%8F%B7%E4%B8%8E%E8%A7%84%E5%88%99.png', '信号与规则', '3.1', 'http://o7d94lzvx.bkt.clouddn.com/%E4%BA%A4%E9%80%9A%E4%BF%A1%E5%8F%B7%E7%81%AF.png', '交通信号', 'A3-01-02', '交通信号灯', '06:00', 'v1.0', '深港学院', NULL, '通过本门课程的学习,您将了解交通信号灯的种类和作用.', 'http://www.lilixc.com/active/app/wk/jtxhd/MSPlayer.html', 0, 'system', '2016-7-8 10:55:11', NULL, '2016-7-15 09:44:14', NULL),
(47, 2, 1, '科目一', 3, 'http://o7d94lzvx.bkt.clouddn.com/%E4%BF%A1%E5%8F%B7%E4%B8%8E%E8%A7%84%E5%88%99.png', '信号与规则', '3.1', 'http://o7d94lzvx.bkt.clouddn.com/%E4%BA%A4%E9%80%9A%E6%A0%87%E5%BF%97.png', '交通信号', 'A3-01-03', '交通标志', '04:40', 'v1.0', '深港学院', NULL, '1.识别不同交通信号的种类；2.掌握各种信号的作用。', 'http://www.lilixc.com/active/app/wk/jtbz/MSPlayer.html', 0, 'system', '2016-7-8 10:55:11', NULL, '2016-7-15 09:44:16', NULL),
(48, 2, 1, '科目一', 3, 'http://o7d94lzvx.bkt.clouddn.com/%E4%BF%A1%E5%8F%B7%E4%B8%8E%E8%A7%84%E5%88%99.png', '信号与规则', '3.1', 'http://o7d94lzvx.bkt.clouddn.com/%E8%AD%A6%E5%91%8A%E6%A0%87%E5%BF%97.png', '交通信号', 'A3-01-04', '警告标志', '07:20', 'v1.0', '深港学院', NULL, '1.识别不同交通信号的种类；2.掌握各种信号的作用。', 'http://www.lilixc.com/active/app/wk/jgbz/MSPlayer.html', 0, 'system', '2016-7-8 10:55:11', NULL, '2016-7-15 09:44:18', NULL),
(49, 2, 1, '科目一', 3, 'http://o7d94lzvx.bkt.clouddn.com/%E4%BF%A1%E5%8F%B7%E4%B8%8E%E8%A7%84%E5%88%99.png', '信号与规则', '3.1', 'http://o7d94lzvx.bkt.clouddn.com/%E7%A6%81%E4%BB%A4%E6%A0%87%E5%BF%97.png', '交通信号', 'A3-01-05', '禁令标志', '04:40', 'v1.0', '深港学院', NULL, '1. 掌握常见的调整公路标志的含义 2. 轻松识别调整公路标志的作用。', 'http://www.lilixc.com/active/app/wk/jlbz/MSPlayer.html', 0, 'system', '2016-7-8 10:55:11', NULL, '2016-7-15 09:44:20', NULL),
(50, 2, 1, '科目一', 3, 'http://o7d94lzvx.bkt.clouddn.com/%E4%BF%A1%E5%8F%B7%E4%B8%8E%E8%A7%84%E5%88%99.png', '信号与规则', '3.1', 'http://o7d94lzvx.bkt.clouddn.com/%E6%8C%87%E7%A4%BA%E6%A0%87%E5%BF%97.png', '交通信号', 'A3-01-06', '指示标志', '08:00', 'v1.0', '深港学院', NULL, '1.了解指示标志的识别；2.掌握指示标志的作用。', 'http://www.lilixc.com/active/app/wk/zsbz/MSPlayer.html', 0, 'system', '2016-7-8 10:55:11', NULL, '2016-7-15 09:44:22', NULL);


--  新增教练评价学员标签
INSERT INTO t_comment_tag (tag,course_id,type,good_bad, isdel, cuid,muid,ctime, mtime, star1,star2, star3, star4,star5, extra)VALUES 
('虚心请教', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('主动好学', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('积极互动', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('心态好', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('很快掌握', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('学得特快', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('淡定', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('比较放松', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('独立上路', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('比较紧张', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('自尊心强', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('理解偏差', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('非常紧张', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('不太认真', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('不熟练', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('手脚发抖', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('主动放弃', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('易激动', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL),
('情绪失控', 1, 3, NULL, 0, NULL, NULL, NULL, '2016-6-6 14:59:01', NULL, NULL, NULL, NULL, NULL, NULL);


--  教练收车、出车推送名单
INSERT INTO `t_user_push` (userId,userType,name,phoneNum,cityId,carNo,driveType,isImport,coachClass,pushType,pushContent,isDel,creator,ctime,updater,utime,remark)VALUES
(271,1,'刘加喜','13423918939',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(871,1,'何少云','13424447938',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(123,1,'凌向群','13510377648',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(134,1,'林文辉','13510846577',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(896,1,'欧毅','13543261478',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(651,1,'聂汉平','13603079553',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(429,1,'肖志坚','13684943582',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(300,1,'吴祥禄','13715173363',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(1011,1,'黄辅国','13760290010',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(339,1,'罗瑞科','13798228212',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(135,1,'刘军亮','13823340912',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(268,1,'彭茂新','13823611798',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(1068,1,'刘伟标','13823691949',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(149,1,'谢庆青','13823717349',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(614,1,'向志伟','13923803153',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(3633,1,'廖顺中','13923887318',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(221,1,'钟国华','15013835790',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(890,1,'马立榜','15915334608',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(564,1,'周建华','15919740227',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL),
(338,1,'余润平','18126287095',100100,NULL,1,0,0,0,NULL,0,'system','2016-6-30 17:55:43',NULL,NULL,NULL);


--  特约教练
update t_u_coach set extra='1' where phoneNum in ('13798228212','18126287095','13424447938','13603079553','15915334608','13923803153','13823340912','13510846577','13823611798','13684943582','13823717349','13543261478','13823691949','13423918939','13510377648','13760290010','15919740227','15013835790','13715173363','13923887318');

--  特约教练说明
INSERT INTO `t_p_config`(name,value,description,type,isdel,extra,mtime) VALUES ('student.ty.xy.coach', '提供随约随学，教练接送，专程1对1教学等尊享服务', NULL, 2, 0, NULL, '2016-7-13 11:23:03');