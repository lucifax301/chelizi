DROP TABLE IF EXISTS `t_u_big_customer`;
CREATE TABLE `t_u_big_customer` (
  `company_name` varchar(255) DEFAULT NULL COMMENT '所在单位',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(50) NOT NULL COMMENT '手机号',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

