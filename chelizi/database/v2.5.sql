alter table t_enroll_order add column channel tinyint(4) NOT NULL DEFAULT '1' COMMENT '报名渠道：1-计时班别；2-驾校班别';


CREATE TABLE `t_enroll_purpose` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `name` varchar(32) DEFAULT NULL COMMENT '学员姓名',
  `phone_num` varchar(64) DEFAULT NULL COMMENT '手机号',
  `head_icon` varchar(256) DEFAULT NULL COMMENT '头像',
  `channel` tinyint(4) NOT NULL DEFAULT '1' COMMENT '意向来源：1-找驾校；2-找教练',
  `ttid` int(11) DEFAULT NULL COMMENT '报名包ID',
  `class_id` int(11) DEFAULT NULL COMMENT '班别ID',
  `city_id` int(11) DEFAULT NULL COMMENT '城市id',
  `school_id` int(11) DEFAULT NULL COMMENT '驾校id',
  `coach_id` bigint(20) DEFAULT NULL COMMENT '教练id',
  `package_name` varchar(50) DEFAULT NULL COMMENT '报名模版的名称',
  `market_price` int(11) DEFAULT NULL COMMENT '市场价，单位分',
  `price` int(11) DEFAULT NULL COMMENT '报名总价，单位分',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  `cuid` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `muid` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  `cType` int(2) DEFAULT '0' COMMENT '学车类型 1:C1  2 C2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='报名意向表';


update `t_wechat_enroll_school` set score=5 where score=8;

update `t_s_school` set score=5 where score=8;