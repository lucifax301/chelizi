/*订单对账*/
SELECT
	order_id 订单号,
	rstart 上课时间,
	CASE pay_state
WHEN 0 THEN
	'未支付'
WHEN 1 THEN
	'已支付'
END 支付状态,
 CASE order_state
WHEN 1 THEN
	'已下单未接单'
WHEN 2 THEN
	'已接单未上课'
WHEN 3 THEN
	'上课中'
WHEN 4 THEN
	'已下课'
WHEN 5 THEN
	'教练已评价'
WHEN 6 THEN
	'学员已评价'
WHEN 7 THEN
	'双方已评价'
WHEN 10 THEN
	'缺课'
END 订单状态,
 course_name 课程名称,
 price_total 订单总价,
 pay_time 支付时间,
 t.payWay 支付方式,
 NULL,
 NULL,
 NULL,
 NULL,
 NULL,
 NULL,
 coupon_name 优惠券名字,
 coupon 优惠券号,
 #waterNum 第三方流水号, 
stu_name 学员名称,
 coach_name 教练名称,
 importSrc 所属驾校
FROM
	(
		SELECT
			*
		FROM
			(
				SELECT
					*
				FROM
					t_order
				WHERE
					(
						(
							rstart > '2016-05-30'
							AND rstart < '2016-06-06'
						)
						OR (
							rend > '2016-05-30'
							AND rend < '2016-06-06'
						)
						OR (
							pay_time > '2016-05-30'
							AND pay_time < '2016-06-06'
						)
					) # AND order_state BETWEEN 4	AND 7
				AND otype = 1
			) AS t1
		LEFT JOIN `t_u_money` AS t2 ON t1.order_id = t2.orderid and t2.userType in (1,2)
		LEFT JOIN `t_u_coach` AS t3 ON t1.coach_id = t3.coachId
	) t
LEFT JOIN `bak_online_db_log`.`t_log_pay` t3 ON t.order_id = t3.`orderId`;



/*报名对账*/
SELECT
	t1.order_id 订单ID,
	t1.price 报名费,
	pay_time 支付时间,
	CASE pay_state
WHEN 0 THEN
	'未支付'
WHEN 100 THEN
	'已支付'
ELSE
	pay_state
END 支付状态,
 CASE t1.isdel
WHEN 0 THEN
	'未删除'
WHEN 1 THEN
	'已删除'
ELSE
	t1.isdel
END 订单状态,
 t1.payWay 支付类型,
 #t1.student_id 学员ID,
NULL,
 NULL,
 NULL,
 NULL,
 NULL,
 NULL,
 t6. NAME 优惠券名字,
 t5.couponId 优惠券号,
 t1. NAME 学员姓名,
 t1.card_id 学员身份证号码,
 t2.phoneNum 学员手机号码,
 t3. NAME 报考城市,
 CASE t1.`dltype`
WHEN 1 THEN
	'C1'
WHEN 2 THEN
	'C2'
END 驾驶类型 #t1.price 报名费 
FROM
	t_enroll_order AS t1
JOIN t_u_student AS t2 ON t1.student_id = t2.studentId
LEFT JOIN t_enroll_package_template AS t3 ON t1.city_id = t3.city_id
LEFT JOIN t_u_money t4 ON t1.order_id = t4.orderid and t4.userType in (1,2)
LEFT JOIN t_u_coupon t5 ON t1.order_id = t5.orderid
LEFT JOIN t_s_coupon t6 ON t5.couponTmpId = t6.couponTmpId
WHERE
	pay_state = 100
AND pay_time > '2016-05-30'
AND pay_time < '2016-06-06';



/*充值对账*/
SELECT
	#id ID,
	orderId 充值订单ID,
	CASE userType
WHEN 1 THEN
	'教练'
ELSE
	'学员'
END 用户类型,
 IFNULL(t2. NAME, t3. NAME) 姓名,
 IFNULL(t2.phoneNum, t3.phoneNum) 手机号码,
 changeValue 充值金额,
 payWay 支付方式,
 NULL,
 NULL,
 NULL,
 NULL,
 NULL #operateTime 操作时间 
FROM
	t_u_money AS t1
LEFT JOIN t_u_student AS t2 ON t1.userId = t2.studentId
AND t1.userType = 2
LEFT JOIN t_u_coach AS t3 ON t1.userId = t3.coachId
AND t1.userType = 1
WHERE
	operateType = 0
AND t1.userType in (1,2)
AND operateTime > '2016-05-30'
AND operateTime < '2016-06-06';









