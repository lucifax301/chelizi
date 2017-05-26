#!/bin/bash
# yangpeng 20160613

BACKUPDIR=/root/tmp
DATE_NOW=`date +%F`
DATE_LASTWEEK=`date -d 'last week' +%F`

FILENAME=caiwu-`date '+%Y%m%d%H%M%S'`.xls
FILETEMP=temp-`date '+%Y%m%d%H%M%S'`.xls

MAILTO=wencong@lilixc.com,shixuekun@lilixc.com,zhoulu@lilixc.com
MAILCC=lihaiyun@lilixc.com,luyonghong@lilixc.com,shiqizhen@lilixc.com,zhangyitian@lilixc.com,zhuxicheng@lilixc.com


mkdir -p "$BACKUPDIR"

# 以下为课时费对账单
DBSQL=" 
SET @time0 = '$DATE_LASTWEEK';
SET @time1 = '$DATE_NOW';

USE bak_online_db_lili;
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
WHEN 0 THEN
	'订单已取消'
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
 CASE t.payWay
WHEN 'zhifubao' THEN
	pay_total
END '支付宝',
 CASE t.payWay
WHEN 'weixin' THEN
	pay_total
END '微信',
 CASE t.payWay
WHEN 'yinlian' THEN
	pay_total
END '银联',
 CASE t.payWay
WHEN 'qqwallet' THEN
	pay_total
END 'QQ钱包',
 CASE t.payWay
WHEN 'balance' THEN
	pay_total
END '余额',
 t.coupon_total / 100 AS '优惠券',
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
							rstart > @time0
							AND rstart < @time1
						)
						OR (rend > @time0 AND rend < @time1)
						OR (
							pay_time > @time0
							AND pay_time < @time1
						)
					) # AND order_state BETWEEN 4   AND 7
				AND otype = 1
			) AS t1
		LEFT JOIN t_u_money AS t2 ON t1.order_id = t2.orderid
		AND t2.userType IN (1, 2)
		LEFT JOIN t_u_coach AS t3 ON t1.coach_id = t3.coachId
	) t
LEFT JOIN bak_online_db_log.t_log_pay t3 ON t.order_id = t3.orderId;
"

# 以下为报名费对账单
DBSQL2="
SET @time0 = '$DATE_LASTWEEK';
SET @time1 = '$DATE_NOW';
USE bak_online_db_lili;
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
CASE t1.payWay
WHEN 'zhifubao' THEN
	t1.pay_total / 100
END '支付宝',
 CASE t1.payWay
WHEN 'weixin' THEN
	t1.pay_total / 100
END '微信',
 CASE t1.payWay
WHEN 'yinlian' THEN
	t1.pay_total / 100
END '银联',
 CASE t1.payWay
WHEN 'qqwallet' THEN
	t1.pay_total / 100
END 'QQ钱包',
 CASE t1.payWay
WHEN 'balance' THEN
	t1.pay_total / 100
END '余额',
 t1.coupon_total / 100 AS '优惠券',
 t6. NAME AS '优惠券名字',
 t1.coupon AS '优惠券号',
 t1. NAME 学员姓名,
 concat('\'', t1.card_id) 学员身份证号码,
 t2.phoneNum 学员手机号码,
 t3. NAME 报考城市,
 CASE t1.dltype
WHEN 1 THEN
	'C1'
WHEN 2 THEN
	'C2'
END 驾驶类型 #t1.price 报名费 
FROM
	t_enroll_order AS t1
JOIN t_u_student AS t2 ON t1.student_id = t2.studentId
LEFT JOIN t_enroll_package_template AS t3 ON t1.city_id = t3.city_id -- LEFT JOIN t_u_money t4 ON t1.order_id = t4.orderid AND t4.userType IN (1, 2)
LEFT JOIN t_u_coupon t5 ON t1.coupon = t5.couponId
LEFT JOIN t_s_coupon t6 ON t5.couponTmpId = t6.couponTmpId
WHERE
	pay_state = 100
AND pay_time > @time0
AND pay_time < @time1;
"

# 以下为充值费对账单
DBSQL3="
SET @time0 = '$DATE_LASTWEEK';
SET @time1 = '$DATE_NOW';
USE bak_online_db_lili;
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
 CASE payWay
WHEN 'zhifubao' THEN
	changeValue
END '支付宝',
 CASE payWay
WHEN 'weixin' THEN
	changeValue
END '微信',
 CASE payWay
WHEN 'yinlian' THEN
	changeValue
END '银联',
 CASE payWay
WHEN 'qqwallet' THEN
	changeValue
END 'QQ钱包',
 CASE payWay
WHEN 'system' THEN
	changeValue
END '平台系统'
FROM
	t_u_money AS t1
LEFT JOIN t_u_student AS t2 ON t1.userId = t2.studentId
AND t1.userType = 2
LEFT JOIN t_u_coach AS t3 ON t1.userId = t3.coachId
AND t1.userType = 1
WHERE
	operateType IN (0, 18)
AND t1.userType IN (1, 2)
AND operateTime > @time0
AND operateTime < @time1;

"
#echo "$DBSQL"

#echo "$DBSQL" |/usr/bin/mysql -uchelizi -pClz_Zd16FV-b9 -h cheliziol.mysql.rds.aliyuncs.com > "$BACKUPDIR"/"$TAG"
echo "-------------以下为课时费对账单--------------------------------" >> "$BACKUPDIR"/"$FILETEMP"
echo " "  >> "$BACKUPDIR"/"$FILETEMP"
echo " "  >> "$BACKUPDIR"/"$FILETEMP"
echo "$DBSQL" |/usr/bin/mysql -uroot -pchelizi -hlocalhost >> "$BACKUPDIR"/"$FILETEMP"
echo " "  >> "$BACKUPDIR"/"$FILETEMP"
echo " "  >> "$BACKUPDIR"/"$FILETEMP"
echo "-------------以下为报名费对账单--------------------------------" >> "$BACKUPDIR"/"$FILETEMP"
echo " "  >> "$BACKUPDIR"/"$FILETEMP"
echo " "  >> "$BACKUPDIR"/"$FILETEMP"
echo "$DBSQL2" |/usr/bin/mysql -uroot -pchelizi -hlocalhost >> "$BACKUPDIR"/"$FILETEMP"
echo " "  >> "$BACKUPDIR"/"$FILETEMP"
echo " "  >> "$BACKUPDIR"/"$FILETEMP"
echo "-------------以下为充值费对账单--------------------------------" >> "$BACKUPDIR"/"$FILETEMP"
echo " "  >> "$BACKUPDIR"/"$FILETEMP"
echo " "  >> "$BACKUPDIR"/"$FILETEMP"
echo "$DBSQL3" |/usr/bin/mysql -uroot -pchelizi -hlocalhost >> "$BACKUPDIR"/"$FILETEMP"
sed -i "s/NULL//g" "$BACKUPDIR"/"$FILETEMP"
iconv -futf8 -tgb2312 -c -s -o "$BACKUPDIR"/"$FILENAME" "$BACKUPDIR"/"$FILETEMP"

echo "附件为上周财务统计表，每周一凌晨6:33自动导出邮寄。如有疑问请联系管理员QQ286880852 " |mail -s "【系统】上周财务统计表" -a "$BACKUPDIR"/"$FILENAME" -c "$MAILCC" "$MAILTO"

rm -rf "$BACKUPDIR"
