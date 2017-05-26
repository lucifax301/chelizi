ALTER TABLE t_c_user
add `enabled` tinyint(4) DEFAULT NULL COMMENT '是否启用,0--启用,1--停用';


DROP TABLE IF EXISTS `t_c_role`;

CREATE TABLE `t_c_role` (
  `level` tinyint(4) NOT NULL COMMENT '部门下的层级,车厘子总部为顶级部门',
  `isGroup` tinyint(4) NOT NULL COMMENT '是否是部门节点 0---不是 1---是',
  `isAdmin` tinyint(4) NOT NULL COMMENT '是否是管理员 0---不是 1---是',
  `pid` int(11) NOT NULL COMMENT '父角色id',
  `schoolId` int(11) NOT NULL COMMENT '角色所属驾校id',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(30) NOT NULL COMMENT '角色名称',
  `enabled` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否启用,0--启用，1--停用',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';