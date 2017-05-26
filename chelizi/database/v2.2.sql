
--  教练表增加字段
ALTER TABLE t_u_coach ADD column `schoolAge` int(11) DEFAULT NULL COMMENT '教龄' after extra;
ALTER TABLE t_u_coach ADD column `area` varchar(32) DEFAULT NULL COMMENT '招生片区' after schoolAge;
ALTER TABLE t_u_coach ADD column `coachTag` varchar(128) DEFAULT NULL COMMENT '教练特色' after area;
ALTER TABLE t_u_coach ADD column `profile` varchar(255) DEFAULT NULL COMMENT '个人简介' after coachTag;
ALTER TABLE t_u_coach ADD column `qrcode` varchar(255) DEFAULT NULL COMMENT '二维码' after profile;

--  消息中心增加消息类型
ALTER TABLE t_p_notice ADD column `msgType` int(11) DEFAULT '0' COMMENT '消息推送ID，与JpushConstant类定义一致' after orderId;


drop table if exists t_wechat_template;

/*==============================================================*/
/* Table: t_wechat_template                                 */
/*==============================================================*/
CREATE TABLE `t_wechat_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
 	`template_name` varchar(64) NOT NULL COMMENT '模板标题',
  `template_id` varchar(64) NOT NULL COMMENT '模板ID', 
  `url` varchar(255) DEFAULT NULL COMMENT ' 模板消息详情链接',
  `template_param` varchar(255) DEFAULT NULL COMMENT '模板内容',
  `ctime` datetime NOT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='微信通知模板表';


drop table if exists t_wechat_template_log;

/*==============================================================*/
/* Table: t_wechat_template_log                                 */
/*==============================================================*/
CREATE TABLE `t_wechat_template_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `student_name` varchar(32) DEFAULT NULL COMMENT '学员姓名',
  `student_phone` varchar(16) DEFAULT NULL COMMENT '学员手机号',
  `status` varchar(32) DEFAULT NULL COMMENT '发送状态为：成功-success; 用户拒收-failed:user block;其他原因失败-failed: system failed',
  `template_id` varchar(64) DEFAULT NULL COMMENT '微信模板ID',
  `to_user` varchar(64) NOT NULL COMMENT '公众号微信号',
  `top_color` varchar(16) DEFAULT NULL COMMENT '消息顶部的颜色',
  `url` varchar(255) DEFAULT NULL COMMENT ' 模板消息详情链接',
  `template_param` varchar(1000) DEFAULT NULL COMMENT '参数列表',
  `errcode` int(11) DEFAULT NULL COMMENT '错误码：0表示成功',
  `errmsg` varchar(64) DEFAULT NULL COMMENT '错误信息',  
  `msg_type` varchar(32) DEFAULT NULL COMMENT '消息类型是事件',
  `msg_id` int(11) DEFAULT NULL COMMENT '消息id',
  `ctime` datetime NOT NULL COMMENT '订单创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='微信通知模板发送日志表';



drop table if exists t_wechat_enroll_class;

/*==============================================================*/
/* Table: t_wechat_enroll_class                                 */
/*==============================================================*/
create table t_wechat_enroll_class
(
   class_id             int(11) not null AUTO_INCREMENT comment '主键ID',
   coach_id             bigint(20) not null comment '教练ID',
   class_name           varchar(64) not null comment '招生班名',
   drtype               tinyint(4) not null default 1 comment '驾照类型：1代表C1,2代表C2，3代表C1，C2',
   price                int(11) not null comment '价格：单位分',
   pre_price            int(11) default NULL comment '优惠价',
   drtype2              tinyint(4) default NULL comment '驾照类型：1代表C1,2代表C2，3代表C1，C2',
   price2               int(11) default NULL comment '原价',
   pre_price2           int(11) default NULL comment '优惠价',
   address              varchar(64) default NULL comment '门店地址',
   class_tag            varchar(128) default NULL comment '班型特色，标签',
   class_remark         varchar(128) default NULL comment '备注',
   order_tag            varchar(255) default NULL comment '其他特色',
   school_id            int(11) default NULL comment '驾校ID',
   city_id              int(11) default NULL comment '城市ID',
   city_name            varchar(50) default NULL comment '城市名称',
   is_del               tinyint(4) default 0 comment '状态：0代表未删除，1代表已删除',
   ctime                datetime default NULL comment '创建时间',
   mtime                timestamp default CURRENT_TIMESTAMP,
   primary key (class_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='招生班型表';



drop table if exists t_wechat_enroll_student;

/*==============================================================*/
/* Table: t_wechat_enroll_student                               */
/*==============================================================*/
create table t_wechat_enroll_student
(
  `order_id` varchar(32) NOT NULL COMMENT '订单号',
  `class_id` int(11) NOT NULL COMMENT '招生班型主键ID',
  `class_name` varchar(64) DEFAULT NULL COMMENT '招生班名',
  `class_remark` varchar(255) DEFAULT NULL COMMENT '班型特色备注',
  `drtype` tinyint(4) NOT NULL DEFAULT '1' COMMENT '驾照类型：1代表C1,2代表C2',
  `coach_id` bigint(20) NOT NULL COMMENT '教练ID',
  `coach_name` varchar(32) DEFAULT NULL COMMENT '教练姓名',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `student_name` varchar(32) DEFAULT NULL COMMENT '学员姓名',
  `student_phone` varchar(16) DEFAULT NULL COMMENT '学员手机号',
  `price` int(11) NOT NULL DEFAULT '0' COMMENT '原班型价格，单位分',
  `pay_price` int(11) DEFAULT NULL COMMENT '实际支付金额',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `pay_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态：0未开始，1已提交，100支付成功，101支付失败',
  `pay_way` varchar(16) DEFAULT 'weixin' COMMENT '支付渠道',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '资金状态:0-平台暂时保管；1-平台已转账给教练',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0代表未删除，1代表已删除',
  `is_new` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否新学员：0-是，1-否',
  `city_id` int(11) DEFAULT NULL COMMENT '报名城市id',
  `city_name` varchar(50) DEFAULT NULL COMMENT '城市名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ctime` datetime DEFAULT NULL COMMENT '订单创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单修改时间',
   primary key (order_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='招生学员表';


drop table if exists t_wechat_mycoaches;

/*==============================================================*/
/* Table: t_wechat_mycoaches                                      */
/*==============================================================*/

CREATE TABLE `t_wechat_mycoaches` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `studentId` bigint(20) NOT NULL COMMENT '学员id',
  `name` varchar(32) DEFAULT NULL COMMENT '教练通讯录学员姓名',
  `phone` varchar(16) DEFAULT NULL COMMENT '教练通讯录手机号',
  `head_icon` varchar(256) DEFAULT NULL COMMENT '头像',
  `state` tinyint(4) DEFAULT '0' COMMENT '学员进度：0-咨询；1-科一；2-科二；3-科三；4-科四；5-毕业',
  `coachId` bigint(20) NOT NULL COMMENT '教练id', 
  `wxstatus` tinyint(4) NOT NULL DEFAULT '0' COMMENT '关联状态：0-微信未绑；1-微信已绑定；2-微信绑定手机，3-微信已解绑',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '教练是否接受：0-待处理，1-已接受',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  `channel` tinyint(4) DEFAULT '1' COMMENT '来源：1教练手动导入、2学员关注微信、3学员填写信息',
  `istop` tinyint(4) DEFAULT '0' COMMENT '是否置顶：0-否，1-是',
  `is_new` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否新学员：0-否，1-是',
  `school_id` int(11) default NULL comment '驾校ID',
  `drtype` tinyint(4) not null default 0 comment '驾照类型：1代表C1,2代表C2',
  `coach_remark` varchar(255) default NULL comment '教练备注学员',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` timestamp default CURRENT_TIMESTAMP comment '订单修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='微信绑定关系表';




drop table if exists t_wechat_student;

/*==============================================================*/
/* Table: t_wechat_student                                      */
/*==============================================================*/
create table t_wechat_student
(  
  `student_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '学员ID',
  `name` varchar(32) DEFAULT NULL COMMENT '微信昵称',
  `sex` tinyint(4) DEFAULT '1' COMMENT '性别(0-未知；1-男；2-女)',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `head_icon` varchar(256) DEFAULT NULL COMMENT '头像',
  `wechat_state` tinyint(4) DEFAULT '0' COMMENT '关注公众号状态：0-未关注；1-已关注；2-已取消',
  `open_id` varchar(128) DEFAULT NULL COMMENT '微信openID',
  `union_id` varchar(128) DEFAULT NULL COMMENT '微信unionID',
  `subscribe` varchar(32) DEFAULT NULL COMMENT '用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息',
  `groupid` varchar(16) DEFAULT NULL COMMENT '用户所在的分组ID',
  `tagid_list` varchar(16) DEFAULT NULL COMMENT '用户被打上的标签ID列表',
  `id_number` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `school_id` int(11) DEFAULT NULL COMMENT '驾校ID',
  `drtype` tinyint(4) NOT NULL DEFAULT '1' COMMENT '驾照类型：1代表C1,2代表C2',
  `isdel` tinyint(4) DEFAULT '0' COMMENT '状态：0代表未删除，1代表已删除',
  `city_id` int(11) DEFAULT NULL COMMENT '报名城市id',
  `city_name` varchar(50) DEFAULT NULL COMMENT '城市名称',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
   primary key (student_id),
   UNIQUE KEY `openId` (`open_id`),
   UNIQUE KEY `unionId` (`union_id`),
   UNIQUE KEY `phoneNum` (`phone`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='教练导入学员表';


drop table if exists t_wechat_coach_class;

/*==============================================================*/
/* Table: t_wechat_coach_class                                      */
/*==============================================================*/
CREATE TABLE `t_wechat_coach_class` (
  `ccid` int(11) NOT NULL AUTO_INCREMENT COMMENT '排班主键',
  `ctype` tinyint(4) NOT NULL DEFAULT '3' COMMENT '班次类型：1现约，3预约',
  `coach_id` bigint(20) NOT NULL COMMENT '教练id',
  `cstart` datetime NOT NULL COMMENT '课程教练开始时间,注意该时间包括余量时间',
  `cend` datetime NOT NULL COMMENT '课程结束时间,注意该时间包括余量时间',
  `rstart` datetime DEFAULT NULL COMMENT '预约课程时间开始时间 不包含余量',
  `rend` datetime DEFAULT NULL COMMENT '课程实际结束时间,不包括余量时间',
  `time_num` tinyint(4) DEFAULT NULL COMMENT '时长，最大为5小时',
  `order_id` varchar(34) DEFAULT NULL COMMENT '该课程的订单号,预约课程可不输入订单号',
  `max_num` tinyint(4) DEFAULT NULL COMMENT '预约类型的最大可预约人数',
  `book_num` tinyint(4) NOT NULL DEFAULT '0' COMMENT '当前已预约人数',
  `car_id` int(11) DEFAULT NULL COMMENT '预约汽车id',
  `car_name` varchar(50) DEFAULT NULL COMMENT '冗余：预约汽车名称',  
  `car_no` varchar(32) DEFAULT NULL COMMENT '冗余：车牌号',
  `course_id` varchar(50) DEFAULT NULL COMMENT '预约科目id',
  `course_name` varchar(254) DEFAULT NULL COMMENT '预约科目id:0-综合训练；1-学车课程；2-科目二训练；3-科目三训练',
  `place_id` int(11) DEFAULT NULL COMMENT '预约训练场id',
  `place_name` varchar(254) DEFAULT NULL COMMENT '冗余：训练场名称',
  `dltype` tinyint(4) DEFAULT NULL COMMENT '驾照类型',
  `tid` int(11) DEFAULT NULL COMMENT '排班时段主键',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  `lge` double DEFAULT NULL COMMENT '训练场经度',
  `lae` double DEFAULT NULL COMMENT '训练场纬度',
  `rid` int(11) DEFAULT NULL COMMENT '排班取消原因id,没有可不填写',
  `reason` varchar(254) DEFAULT NULL COMMENT '排班取消原因',
  PRIMARY KEY (`ccid`)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8 COMMENT='教练微信排班表';


drop table if exists t_wechat_plant_class;

/*==============================================================*/
/* Table: t_wechat_plant_class                                      */
/*==============================================================*/
CREATE TABLE `t_wechat_plant_class` (
  `order_id` varchar(34) NOT NULL COMMENT '订单id(全网唯一，用UID)',
  `ccid` int(11) NOT NULL COMMENT '排班主键',
  `coach_id` bigint(20) DEFAULT NULL COMMENT '冗余：教练id',
  `student_id` bigint(20) NOT NULL COMMENT '冗余：学员id',
  `gtime` datetime DEFAULT NULL COMMENT '冗余：下单时间',
  `stu_name` varchar(32) DEFAULT NULL COMMENT '冗余：学生姓名',
  `stu_img` varchar(254) DEFAULT NULL COMMENT '冗余：学生头像',
  `stu_mobile` varchar(20) DEFAULT NULL COMMENT '冗余：学生手机号码',
  `state` tinyint(4) default 0 comment '状态：0代表正常，1-已接受，2-拒绝；3-取消,4-已完成；5-缺课',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教练微信排班学员预约表';



drop table if exists t_wechat_order;

/*==============================================================*/
/* Table: t_wechat_order                                      */
/*==============================================================*/

CREATE TABLE `t_wechat_order` (
  `order_id` varchar(34) NOT NULL COMMENT '订单id(全网唯一，用UID)',
  `coach_id` bigint(20) DEFAULT NULL COMMENT '教练id',
  `coach_name` varchar(32) DEFAULT NULL COMMENT '冗余：教练名称',
  `coach_img` varchar(254) DEFAULT NULL COMMENT '冗余：教练头像',
  `coach_mobile` varchar(20) DEFAULT NULL COMMENT '教练电话',
  `city_id` int(11) DEFAULT NULL COMMENT '订单的城市id',    
  `school_id` int(11) DEFAULT NULL COMMENT '订单所属驾校：可暂时考虑以教练所属驾校来确定，但最终规格待定', 
  `course_id` varchar(50) NOT NULL COMMENT '课程id',
  `course_name` varchar(254) DEFAULT NULL COMMENT '冗余：科目名称',
  `learn_addr` varchar(254) DEFAULT NULL COMMENT '上课地点',
  `student_id` bigint(20) NOT NULL COMMENT '学员id',
  `stu_name` varchar(32) DEFAULT NULL COMMENT '冗余：学生名称',
  `stu_img` varchar(254) DEFAULT NULL COMMENT '冗余：学生头像',
  `stu_mobile` varchar(20) DEFAULT NULL COMMENT '学生电话',  
  `dltype` tinyint(4) NOT NULL DEFAULT '1' COMMENT '驾照类型：1代表C1,2代表C2',
  `lge` double DEFAULT NULL COMMENT '学员接送点经度',
  `lae` double DEFAULT NULL COMMENT '学员接送点纬度',
  `stu_addr` varchar(254) DEFAULT NULL COMMENT '学员接送地点',
  `pstart` datetime DEFAULT NULL COMMENT '计划上课时间',
  `pend` datetime DEFAULT NULL COMMENT '计划结束时间',
  `clz_num` tinyint(4) NOT NULL DEFAULT '1' COMMENT '该订单约课节数',
  `order_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态：0待确认,1-已接受，2-学员拒绝；3-教练取消,4-已完成；5-缺课',
  `rstart` datetime DEFAULT NULL COMMENT '实际上课开始时间',
  `rend` datetime DEFAULT NULL COMMENT '实际上课结束时间',
  `cstart` datetime DEFAULT NULL COMMENT '教练出发时间',
  `cend` datetime DEFAULT NULL COMMENT '教练回场时间',
  `need_trans` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否需要接送：0代表不需要接送,1代表需要接送',
  `gtime` datetime DEFAULT NULL COMMENT '下单时间',
  `atime` datetime DEFAULT NULL COMMENT '接单时间',
  `otype` tinyint(4) NOT NULL DEFAULT '3' COMMENT '订单类型：1现约订单,2续课订单，3预约订单',
  `coorder` varchar(34) DEFAULT NULL COMMENT '续课单原单',
  `ccid` int(11) DEFAULT NULL COMMENT '冗余：预约排班id',  
  `car_id` int(11) DEFAULT NULL COMMENT '教练车',
  `car_name` varchar(50) DEFAULT NULL COMMENT '冗余：教练车型',
  `car_img` varchar(254) DEFAULT NULL COMMENT '冗余:汽车图标',
  `car_no` varchar(32) DEFAULT NULL COMMENT '冗余：车牌号',   
  `place_id` int(11) DEFAULT NULL COMMENT '训练场ID', 
  `place_name` varchar(254) DEFAULT NULL COMMENT '训练场名称', 
  `place_lge` double DEFAULT NULL COMMENT '上课地点的经度',
  `place_lae` double DEFAULT NULL COMMENT '上课地点的纬度',  
  `operate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单预约表';




alter table t_comment_tag MODIFY COLUMN `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '标签类型：1-教练标签，2-学员标签,3-MANGER,4-教练特色,5-班型特色';



INSERT INTO `t_comment_tag` (tag, course_id, type, good_bad, isdel, cuid, muid, ctime, mtime, star1, star2, star3, star4, star5, extra) VALUES 
('不抽烟', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('包接送', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('脾气好', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('不收礼', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('一人一车', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('资深教练', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('夜间培训', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('分期付款', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('吃饭AA', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('仅周末培训', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('一对一教学', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('费用全包', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('体检报销', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('不限学时', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('工作日学车', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('周末学车', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('晚上学车', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('一人一车', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('两人一车', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('四人一车', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL);


INSERT INTO `t_wechat_template` VALUES (2, '新学员订单通知', 'EikvzkXZC6MtlDw5w7lOVg8RDEO-LE2eOeFImaiosBE', 'http://uatweixinjl.lilixc.com/course/show?orderId=', '{{first.DATA}} 报名班型：{{keyword1.DATA}} 支付金额：{{keyword2.DATA}} 学员姓名：{{keyword3.DATA}} 学员电话：{{keyword4.DATA}} 订单编号：{{keyword5.DATA}} {{remark.DATA}}', '2016-11-21 09:34:06', '2016-11-21 09:34:07');
INSERT INTO `t_wechat_template` VALUES (3, '课程提醒通知', 'EuCEwMzzmO6z_vVNSFiKvwwVngkmukk6YYcQtAuCAbM', 'http://uatweixinjl.lilixc.com/course/show?orderId=', '{{first.DATA}} 教练姓名：{{keyword1.DATA}} 学员姓名：{{keyword2.DATA}} 开始时间：{{keyword3.DATA}} 课程时长：{{keyword4.DATA}} {{remark.DATA}}', '2016-11-21 09:35:58', '2016-11-21 09:35:59');
