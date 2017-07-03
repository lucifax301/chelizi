 CREATE TABLE `t_exam_vip` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(50) DEFAULT NULL COMMENT '大客户姓名',
  `mobile` varchar(30) DEFAULT NULL COMMENT '大客户手机号',
  `c1count` int(10) DEFAULT 0 COMMENT '预留个大客户c1车数量',
  `c2count` int(10) DEFAULT 0 COMMENT '预留个大客户c2车数量',
  `school` varchar(20) DEFAULT NULL COMMENT '所属驾校(考场)',
  `schoolId` int(10) DEFAULT NULL COMMENT '所属驾校(考场)id',
  `state` tinyint(4) DEFAULT '0' COMMENT '是否已删除：0-未删除，启用中；1-已删除',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(200) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='考场大客户';

 CREATE TABLE `t_exam_vip_coach` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(50) DEFAULT NULL COMMENT '教练姓名',
  `mobile` varchar(30) DEFAULT NULL COMMENT '教练手机号',
  `vipId` int(10) DEFAULT NULL COMMENT '大客户id',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(200) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='考场大客户教练';


alter table t_s_school add column examkey varchar(30) default null COMMENT '考场项目id';
## 横荷考场项目有3个考场, 对应3个school, 横荷app获取考场列表的时候需传入这个考场项目id



 CREATE TABLE `t_exam_car` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `carno` varchar(20) DEFAULT NULL COMMENT '车号',
  `drtype` tinyint(4) DEFAULT '1' COMMENT '练考车型：1-C1；2-C2',
  `classid` int DEFAULT NULL COMMENT '排版id',
  `state` smallint DEFAULT 0 COMMENT '0 未被预定 1 已被预定',
  `vipId` int(10) DEFAULT NULL COMMENT '大客户id,如果被大客户的教练预约',
  `mobile` varchar(16) DEFAULT NULL COMMENT '教练手机',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='预约车记录';

## 新增一个排版的时候，就插入N（等于考场配置的车数量）条记录 or 保存到redis里，以排版id作为key, 保存list