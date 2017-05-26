
-- 定义券约束条件
-- --限数量：[1张]
insert into `db_lili`.`t_s_ccondition` ( `conditionId`, `param2`, `descri`, `type`, `param1`) values ( '57', '930b9ebb74d541439fcbbebc4f76f54f', '限领1张', '3', '1');
-- --限定范围：[深圳|东莞|广州|西安|长沙]
insert into `db_lili`.`t_s_ccondition` ( `conditionId`, `param2`, `descri`, `type`, `param1`) values ( '58', '0', '在指定区域内', '1', '深圳|东莞|广州|西安|长沙');
-- --限定时间：[2016-05-18 00:00:01-2016-09-30 23:59:59]
insert into `db_lili`.`t_s_ccondition` ( `conditionId`, `param2`, `descri`, `type`, `param1`) values ( '59', '2016-09-30 23:59:59', '在指定时间范围内', '0', '2016-05-18 00:00:01');
-- --限定订单类型：[报名]
insert into `db_lili`.`t_s_ccondition` ( `conditionId`, `param2`, `descri`, `type`, `param1`) values ( '60', '0', '指定报名费能用', '5', '11');

-- 定义券
insert into `db_lili`.`t_s_coupon` ( `QRCodeUrl`, `validityPeriod`, `moneyValue`, `verify`, `extra`, `couponTmpId`, `listBackImg`, `genRule`, `useNote`, `discount`, `indepentUse`, `smsMsgType`, `limitValue`, `icon`, `name`, `couponPic`, `type`, `couponUrl`, `jpushMsg`, `isExist`, `useRule`, `createUser`, `createTime`, `expireTime`) 
	values ( 'http://www.baidu.com', '0', '10000', '1', null, '930b9ebb74d541439fcbbebc4f76f54f', ' ', '(%s)|57', '1、该代金仅限深圳、东莞、广州、西安、长沙新报名用户使用\n2、该代金券仅抵扣报名费，每次限用1张，不与其他报名优惠同时使用\n3、该代金券不提现，使用有效期自即日起至2016年9月30日\n4、该代金优惠最终解释权归喱喱学车所有', '0', '1', '4', '10000', ' ', '100元报名学车代金券', 'coupon_dialog_enroll_cash_100.png', '0', null, '亲爱的用户，100元报名学车代金券已送达您个人账户，请查看！', '1', '(%s and %s and %s)|58,59,60', 'zhou long', '2016-05-18 10:30:00', '2016-09-30 23:59:59');

-- 定义库存
insert into `db_lili`.`t_s_cstock` ( `stockId`,`eventTopic`, `haveUsed`, `couponTempId`, `createUser`, `isExist`, `total`, `createTime`) 
	values ( '5', '', '0', '930b9ebb74d541439fcbbebc4f76f54f', 'zhou long', '1', '210000', '2016-05-18 10:30:00');