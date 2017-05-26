CREATE TABLE `t_v_custom_export` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `file_name` varchar(255) NOT NULL COMMENT '导入表格名称',
  `sum` int(11) NOT NULL DEFAULT '0' COMMENT '总人数',
  `valid_sum` int(11) NOT NULL DEFAULT '0' COMMENT '有效数',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1 初始化状态，未确认，2 确认导入-导入成功，3 放弃导入，4导入失败',
  `creator` varchar(32) NOT NULL COMMENT '提交人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '导表时间',
  `verifier` varchar(32) DEFAULT NULL COMMENT '确认人',
  `verifie_time` timestamp NULL DEFAULT NULL COMMENT '确认时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=252 DEFAULT CHARSET=utf8 COMMENT='大客户导入表';


CREATE TABLE `t_v_custom_export_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `export_id` int(11) NOT NULL COMMENT '关联导入主表ID',
  `cname` varchar(20) DEFAULT NULL COMMENT '学员姓名',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别(0,女；1，男；2，未知)',
  `mobile` varchar(20) NOT NULL COMMENT '手机',
  `idNumber` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `city` int(11) DEFAULT NULL COMMENT '城市',
  `cid` varchar(50) DEFAULT NULL COMMENT '工号',
  `rcname` varchar(50) DEFAULT NULL COMMENT '方案中文名称',
  `rcid` int(11) DEFAULT NULL COMMENT '充值送套餐id',
  `coid` int(11) DEFAULT NULL COMMENT '大客户公司主键',
  `company` varchar(100) NOT NULL COMMENT '公司名称',
  `status` tinyint(4) DEFAULT '0' COMMENT '导入状态：0-未确认，1-导入成功，2-导入失败，3-放弃导入',
  `city_name` varchar(50) DEFAULT NULL COMMENT '城市名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 COMMENT='大客户导入用户明细表';

