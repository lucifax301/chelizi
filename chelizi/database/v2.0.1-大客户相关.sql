

ALTER TABLE t_recharge_plan ADD column isExit_rercid tinyint(4) DEFAULT '0' COMMENT '是否存在关联方案：0-不存在；1-存在' after isdel;
ALTER TABLE t_recharge_plan ADD column rercid int(11) DEFAULT NULL COMMENT '关联rcid' after isExit_rercid;
ALTER TABLE t_recharge_plan ADD column reg_num int(11) DEFAULT '1' COMMENT '初始方案送多少次后执行关联方案' after rercid;


ALTER TABLE t_recharge_gear ADD column coupon_name varchar(256) DEFAULT NULL COMMENT '优惠券名称，多张以|线分割' after money;
ALTER TABLE t_recharge_gear ADD column coupon_id varchar(256) DEFAULT NULL COMMENT '优惠券模板ID，多个以|分割' after coupon_name;
ALTER TABLE t_recharge_gear ADD column coupon_num varchar(256) DEFAULT NULL COMMENT '优惠券数量，多种以|线分割，必须与couponTmpId大小一致' after coupon_id;


ALTER TABLE t_recharge_record ADD column coupon_name varchar(256) DEFAULT NULL COMMENT '优惠券名称，多张以|线分割' after recharge;
ALTER TABLE t_recharge_record ADD column coupon_id varchar(256) DEFAULT NULL COMMENT '优惠券模板ID，多个以|分割' after coupon_name;
ALTER TABLE t_recharge_record ADD column coupon_num varchar(256) DEFAULT NULL COMMENT '优惠券数量，多种以|线分割，必须与couponTmpId大小一致' after coupon_id;

