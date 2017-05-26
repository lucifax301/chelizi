-- 驾校预约相关数据表
DROP TABLE IF EXISTS `t_exam_place`;
CREATE TABLE `t_exam_place` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '考场id',
  `name` varchar(50) DEFAULT NULL COMMENT '考场名称',
  `type` varchar(10) DEFAULT NULL COMMENT '科目类型：2-科目二；3-科目三；多个用逗号隔开',
  `area` int(11) DEFAULT NULL COMMENT '考场面积',
  `contact` varchar(20) DEFAULT NULL COMMENT '联系人',
  `mobile` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `lge` decimal(10,6) DEFAULT NULL COMMENT '经度',
  `lae` decimal(10,6) DEFAULT NULL COMMENT '纬度',
  `city` varchar(20) DEFAULT NULL COMMENT '所属城市',
  `cityId` int(10) DEFAULT NULL COMMENT '所属城市id',
  `school` varchar(20) DEFAULT NULL COMMENT '所属驾校',
  `schoolId` int(10) DEFAULT NULL COMMENT '所属驾校id',
  `days` int(10) DEFAULT '14' COMMENT '客户端可预约天数',
  `state` tinyint(4) DEFAULT '0' COMMENT '是否已删除：0-未删除，启用中；1-已删除',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(200) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '考场信息表';

-- 添加默认考场
INSERT INTO `db_lili`.`t_exam_place` (`id`, `name`, `type`, `area`, `contact`, `mobile`, `address`, `lge`, `lae`, `city`, `cityId`, `school`, `schoolId`, `days`, `state`, `ctime`, `mtime`, `extra`) 
VALUES ('1', '广仁考场', '2', '2000', NULL, '15888888888', '深南大道001号', NULL, NULL, '深圳市', '100100', '深圳广仁', '5', '14', '0', now(), now(), '默认考场');


DROP TABLE IF EXISTS `t_exam_place_class`;
CREATE TABLE `t_exam_place_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '排班id',
  `placeId` int(11) DEFAULT NULL COMMENT '考场id',
  `pstart` datetime DEFAULT NULL COMMENT '计划练车开始时间',
  `pend` datetime DEFAULT NULL COMMENT '计划练车结束时间',
  `rstart` datetime DEFAULT NULL COMMENT '实际练车开始时间（如延班则与计划不同）',
  `rend` datetime DEFAULT NULL COMMENT '实际练车结束时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `duration` int(10) DEFAULT '0' COMMENT '排班时长（小时）',
  `outerPrice` int(11) DEFAULT '20000' COMMENT '外部价格（/分）',
  `innerPrice` int(11) DEFAULT '12000' COMMENT '内部价格（/分）',
  `minHours` int(4) DEFAULT '0' COMMENT '最小预约时段（/小时）',
  `c1` int(4) DEFAULT '0' COMMENT '车辆总数量C1',
  `c1inner` int(4) DEFAULT '0' COMMENT '车辆数量C1-内部',
  `c1outer` int(4) DEFAULT '0' COMMENT '车辆数量C1-外部',
  `c1book` int(4) DEFAULT '0' COMMENT '已预约车辆总数量C1',
  `c1bookInner` int(4) DEFAULT '0' COMMENT '已预约车辆数量C1-内部',
  `c1bookOuter` int(4) DEFAULT '0' COMMENT '已预约车辆数量C1-外部',
  `c2` int(4) DEFAULT '0' COMMENT '车辆总数量C2',
  `c2inner` int(4) DEFAULT '0' COMMENT '车辆数量C2-内部',
  `c2outer` int(4) DEFAULT '0' COMMENT '车辆数量C2-外部',
  `c2book` int(4) DEFAULT '0' COMMENT '已预约车辆总数量C2',
  `c2bookInner` int(4) DEFAULT '0' COMMENT '已预约车辆数量C2-内部',
  `c2bookOuter` int(4) DEFAULT '0' COMMENT '已预约车辆数量C2-外部',
  `innerExpire` int(4) DEFAULT '0' COMMENT '内部失效时间（/天）',
  `favorType` int(4) DEFAULT '1' COMMENT '优惠类型：1-返课时；2-返金额',
  `favorIn` int(4) DEFAULT '0' COMMENT '累计几个小时',
  `favorOut` int(4) DEFAULT '0' COMMENT '送的学时或金额',  
  `state` tinyint(4) DEFAULT '0' COMMENT '排班状态：0-正常；1-已关闭；2-已延迟',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(200) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT '考场排班表';



DROP TABLE IF EXISTS `t_exam_place_order`;
CREATE TABLE `t_exam_place_order` (
  `orderId` varchar(32) NOT NULL COMMENT '订单id',
  `classId` int(11) DEFAULT NULL COMMENT '排班id',
  `placeId` int(11) DEFAULT NULL COMMENT '考场id',
  `placeName` varchar(50) DEFAULT NULL COMMENT '考场名称',
  `school` varchar(20) DEFAULT NULL COMMENT '所属驾校',
  `type` tinyint(4) DEFAULT '2' COMMENT '练考类型：2-科目二；3-科目三',
  `drtype` tinyint(4) DEFAULT '1' COMMENT '练考车型：1-C1；2-C2',
  `coachId` bigint(20) DEFAULT NULL COMMENT '教练id',
  `coachName` varchar(20) DEFAULT NULL COMMENT '教练姓名',
  `coachMobile` varchar(20) DEFAULT NULL COMMENT '教练手机号',
  `coachImg` varchar(200) DEFAULT NULL COMMENT '教练头像',
  `coachType` tinyint(4) DEFAULT '1' COMMENT '教练类型：1-内部教练；2-外部教练',
  `carNo` varchar(30) DEFAULT NULL COMMENT '车牌号',
  `device` varchar(50) DEFAULT NULL COMMENT '设备开发：（预留）',
  `duration` int(10) DEFAULT '0' COMMENT '预约时长（小时）',
  `favorUse` int(10) DEFAULT '0' COMMENT '本次使用的优惠课时',
  `favorGen` int(10) DEFAULT '0' COMMENT '本次生成的优惠课时',
  `favorLeft` int(10) DEFAULT '0' COMMENT '获取的优惠课时剩余总计',
  `favorInfo` varchar(20) DEFAULT NULL COMMENT '可获优惠描述',
  `delay` int(10) DEFAULT '0' COMMENT '是否顺延（分钟）',
  `delayInfo` varchar(200) DEFAULT NULL COMMENT '顺延原因',
  `priceTotal` int(11) DEFAULT '0' COMMENT '订单总金额',
  `couponTotal` int(11) DEFAULT '0' COMMENT '优惠抵扣金额',
  `payTotal` int(11) DEFAULT '0' COMMENT '实际支付金额',
  `payTime` datetime DEFAULT NULL COMMENT '支付时间',
  `payWay` varchar(10) DEFAULT NULL COMMENT '支付方式',
  `state` tinyint(4) DEFAULT '0' COMMENT '订单状态：0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',
  `pstart` datetime DEFAULT NULL COMMENT '计划练车开始时间',
  `pend` datetime DEFAULT NULL COMMENT '计划练车结束时间',
  `rstart` datetime DEFAULT NULL COMMENT '实际练车开始时间',
  `rend` datetime DEFAULT NULL COMMENT '实际练车结束时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(200) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '考场预约订单表';


DROP TABLE IF EXISTS `t_exam_place_favor`;
CREATE TABLE `t_exam_place_favor` (
  `placeId` int(11) NOT NULL COMMENT '考场id',
  `userId` bigint(11) NOT NULL COMMENT '用户id',
  `duration` int(11) DEFAULT '0' COMMENT '预约课时时长累计',
  `favorIn` int(11) DEFAULT NULL COMMENT '条件：累计几个小时',
  `favorOut` int(11) DEFAULT '0' COMMENT '送的学时。和金额单位一致，乘了100',
  `favorOut2` int(11) DEFAULT '0' COMMENT '送的金额，单位分。元乘了100',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(200) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`userId`,`placeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '考场订单时长累计、优惠记录';


DROP TABLE IF EXISTS `t_exam_place_city`;
CREATE TABLE `t_exam_place_city` (
  `id` int(11) NOT NULL COMMENT '城市表主键',
  `name` varchar(20) NOT NULL COMMENT '城市名称',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '约考场城市';
INSERT INTO t_exam_place_city VALUES (100100, '深圳市', 0,now() );
INSERT INTO t_exam_place_city VALUES (100101, '东莞市', 1,now() );
INSERT INTO t_exam_place_city VALUES (100102, '广州市', 1,now() );
INSERT INTO t_exam_place_city VALUES (100103, '佛山市', 1,now() );
INSERT INTO t_exam_place_city VALUES (101100, '杭州市', 1,now() );
INSERT INTO t_exam_place_city VALUES (102100, '西安市', 1,now() );
INSERT INTO t_exam_place_city VALUES (103100, '长沙市', 1,now() );

ALTER TABLE t_u_money MODIFY `orderId` varchar(350) DEFAULT NULL COMMENT '关联的订单id|优惠券id';
use db_log;
ALTER TABLE t_log_pay MODIFY `orderId` varchar(350) DEFAULT NULL COMMENT '订单id';
ALTER TABLE t_log_operate MODIFY `requestParams` text COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求地址';


















