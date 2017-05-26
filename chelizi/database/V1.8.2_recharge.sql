-- 增加报名生效城市,普惠方案开始
ALTER TABLE t_recharge_plan ADD column city_id varchar(256) COMMENT '该方案生效的城市，多个以逗号分割，为空表示不限制' after enroll;
ALTER TABLE t_recharge_plan ADD column city_name varchar(256) COMMENT '冗余：该方案生效的城市，多个以逗号分割' after city_id;
ALTER TABLE t_recharge_plan ADD column common tinyint not null default 0 COMMENT '普惠方案的当前优先版本：0一般，1优先，始终只有一个方案优先' after city_name;

ALTER TABLE t_recharge_plan ADD column need_verify tinyint not null default 1 COMMENT '成为大客户是否需要审核：1需要，0不需要' after common;
ALTER TABLE t_recharge_plan ADD column reg_use tinyint not null default 1 COMMENT '报名后是否还可以：0不可用,1可以用.' after need_verify;


delete from t_vip_company where coid=4;
insert  into `t_vip_company`(`coid`,`company`,`vtype`,`shorter`,`province_id`,`city_id`,`province`,`city`,`manger`,`mobile`,`phone`,`email`,`rcid`,`remark`,`active`,`agreement`,`vstate`,`reason`,`isdel`,`cuid`,`muid`,`ctime`,`mtime`) values (4,'喱喱学员普惠',2,NULL,NULL,100,'广东省','深圳市','喱喱经理','13900000000',NULL,NULL,NULL,NULL,1,'agreement',1,NULL,0,80,80,'2016-07-21 17:53:10','2016-07-21 17:53:24');


-- 修改东莞广仁方案为大客户自动获得资格，不需要人工审核
update t_recharge_plan set need_verify=0 where rcid=160715001;

--  添加深圳大客户、普惠
DELETE from t_recharge_plan where rcid in (160701003,160726001,160726002);
INSERT INTO `t_recharge_plan` (rcid,rcname,vtype,tstart,tend,active,enroll,city_id,city_name,common,need_verify,reg_use,auto,max_times,indepent,jpush,ems,rejpush,reems,coupon_template,coupon_number,agreement,vstate,reason,isdel,cuid,muid,ctime,mtime) VALUES 
(160701003, '普惠充值送方案', 2, '2016-7-28 00:00:00', '2016-8-31 00:00:00', 1, 1, '', '', 1, 0, 1, 0, NULL, 1, '喱喱学员们，又一波喱喱福利向大家砸来啦！', NULL, NULL, '97747', NULL, NULL, '学员报名后才能享受该充值优惠|不限充值次数，即每次充值都会赠送对应金额|充值上述固定额度，才能得到对应赠送金额|充值后不能提现', 1, NULL, 0, 1, 1, '2016-7-21 12:52:07', '2016-7-28 15:47:12'),
(160726001, '大客户充值送政策V001深圳电信', 1, '2016-7-26 00:00:00', '2016-8-31 00:00:00', 1, 1, '100100', '深圳市', 1, 1, 0, 0, NULL, 1, '亲爱的用户您已成为喱喱大客户，快来下载APP享受您的专属优惠吧！', NULL, NULL, '97747', NULL, NULL, '1.学员报名后才能享受该充值优惠\n2.充值上述固定金额，最高享100%的赠送金额\n3.充值次数不限，且充值后不能提现、退款、转移、转赠\n4.该优惠不与其他优惠同享', 1, NULL, 0, 1, 1, '2016-7-26 17:26:15', '2016-7-28 13:11:26'),
(160726002, '大客户充值送政策V001深圳腾讯', 1, '2016-7-26 00:00:00', '2016-8-31 00:00:00', 1, 1, '100100', '深圳市', 1, 1, 0, 0, NULL, 1, '亲爱的用户您已成为喱喱大客户，快来下载APP享受您的专属优惠吧！', NULL, NULL, '97747', NULL, NULL, '1.学员报名后才能享受该充值优惠\n2.充值上述固定金额，最高享100%的赠送金额\n3.充值次数不限，且充值后不能提现、退款、转移、转赠\n4.该优惠不与其他优惠同享', 1, NULL, 0, 1, 1, '2016-7-26 18:09:29', '2016-7-28 13:11:26');


--  添加深圳大客户、普惠套餐
DELETE from t_recharge_gear where rcid in (160701003,160726001,160726002);
INSERT INTO `t_recharge_gear`(`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`,`mtime`) VALUES 
(160701003, 300000, 300000, NULL, 90000, 1, 0, NULL, NULL, '2016-7-28 13:13:11', '2016-7-28 13:11:15'),
(160701003, 200000, 200000, NULL, 60000, 1, 0, NULL, NULL, '2016-7-28 13:13:11', '2016-7-28 13:11:15'),
(160701003, 100000, 100000, NULL, 30000, 1, 0, NULL, NULL, '2016-7-28 13:13:11', '2016-7-28 13:11:15'),
(160701003, 50000, 50000, NULL, 15000, 1, 0, NULL, NULL, '2016-7-28 13:13:11', '2016-7-28 13:11:15'),
(160726001, 300000, 300000, NULL, 300000, 1, 0, NULL, NULL, '2016-7-28 13:12:44', '2016-7-28 13:10:48'),
(160726001, 200000, 200000, NULL, 150000, 1, 0, NULL, NULL, '2016-7-28 13:12:44', '2016-7-28 13:10:48'),
(160726001, 100000, 100000, NULL, 50000, 1, 0, NULL, NULL, '2016-7-28 13:12:44', '2016-7-28 13:10:48'),
(160726002, 300000, 300000, NULL, 300000, 1, 0, NULL, NULL, '2016-7-28 13:12:19', '2016-7-28 13:10:23'),
(160726002, 200000, 200000, NULL, 150000, 1, 0, NULL, NULL, '2016-7-28 13:12:19', '2016-7-28 13:10:23'),
(160726002, 100000, 100000, NULL, 50000, 1, 0, NULL, NULL, '2016-7-28 13:12:19', '2016-7-28 13:10:23');