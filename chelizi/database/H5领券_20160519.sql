
-- t_s_coupon表添加expireTime字段
ALTER TABLE `db_lili`.`t_s_coupon` ADD COLUMN `expireTime` timestamp NULL COMMENT '当validityPeriod=0时，expireTime有效，或者在限定时间的优惠券终止时间时有效' AFTER `extra`;

-- 修改t_s_coupon表中validityPeriod字段的注释
ALTER TABLE `db_lili`.`t_s_coupon` CHANGE COLUMN `validityPeriod` `validityPeriod` int(11) NOT NULL COMMENT '有效期(-1,无限久，0表示使用expireTime字段，其他大于0的数值表示单位小时)';
