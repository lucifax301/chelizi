
-- 更新启动广告
ALTER TABLE t_u_poster 
ADD COLUMN `isdel` tinyint(4) DEFAULT 0 COMMENT '是否已删除：0-未删除；1-已删除' AFTER type;

-- 更新系统消息，上线需清除相关缓存数据
ALTER TABLE t_p_notice 
ADD COLUMN `isdel` tinyint(4) DEFAULT 0 COMMENT '是否已删除：0-未删除；1-已删除';

-- 教练封号，上线需清除相关缓存数据
ALTER TABLE t_u_coach 
ADD COLUMN state tinyint(4) DEFAULT '0' COMMENT '用户状态(0-正常；1-临时封号；2-永久封号)',
ADD COLUMN reviveTime datetime DEFAULT NULL COMMENT '封号截止时间',
ADD COLUMN `extra` varchar(255) DEFAULT NULL COMMENT '冗余：用于存放临时数据等';

-- 学员封号，上线需清除相关缓存数据
ALTER TABLE t_u_student 
ADD COLUMN state tinyint(4) DEFAULT '0' COMMENT '用户状态(0-正常；1-临时封号；2-永久封号)',
ADD COLUMN reviveTime datetime DEFAULT NULL COMMENT '封号截止时间';


-- 优惠券模板条件表中,增加创建时间的记录
ALTER TABLE `db_lili`.`t_s_ccondition` ADD COLUMN `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `descri`;

-- 修改coupon表的字段,添加是否使用,是否有效的状态表示
ALTER TABLE `db_lili`.`t_u_coupon`
  CHANGE COLUMN `expireTime` `expireTime` timestamp NULL COMMENT '过期时间' AFTER `useTime`,
  CHANGE COLUMN `couponTmpId` `couponTmpId` varchar(32) NOT NULL COMMENT '优惠券的模板id' AFTER `expireTime`,
  CHANGE COLUMN `stockId` `stockId` int(11) DEFAULT NULL COMMENT '获取优惠券的库存id' AFTER `couponTmpId`,
  CHANGE COLUMN `orderId` `orderId` varchar(32) DEFAULT NULL COMMENT '订单id' AFTER `stockId`,
  CHANGE COLUMN `isExist` `isUsed` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否已使用，0=未使用，1=已使用' AFTER `orderId`,
  ADD COLUMN `isValid` tinyint NOT NULL DEFAULT 1 COMMENT '是否有效，0=无效，1=有效' AFTER `isUsed`;

-- 修正coupon字段默认值存在的问题
ALTER TABLE `db_lili`.`t_u_coupon`
CHANGE COLUMN `useTime` `useTime` TIMESTAMP NULL DEFAULT NULL COMMENT '使用时间' ;
  
-- 训练场
ALTER TABLE t_s_trfield 
ADD COLUMN `isdel` tinyint(4) DEFAULT 0 COMMENT '是否已删除：0-未删除；1-已删除';
  
-- 协议、帮助文档等  
CREATE TABLE `t_u_html` (
  `hid` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `hname` varchar(255) DEFAULT NULL COMMENT '名称',
  `hdescription` varchar(255) DEFAULT NULL COMMENT '描述',
  `html` longtext COMMENT 'html文档内容',
  `url` varchar(255) DEFAULT NULL COMMENT '用于外部调用的url',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`hid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 接口优化，添加记录接口执行时间
use db_log;
ALTER TABLE t_log_operate 
ADD COLUMN `executeTime` bigint(20) DEFAULT NULL COMMENT '接口执行时间：毫秒数',
ADD COLUMN `extra` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '冗余';




  
  