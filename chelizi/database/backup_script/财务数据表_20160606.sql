/*��������*/
SELECT
	order_id ������,
	rstart �Ͽ�ʱ��,
	CASE pay_state
WHEN 0 THEN
	'δ֧��'
WHEN 1 THEN
	'��֧��'
END ֧��״̬,
 CASE order_state
WHEN 1 THEN
	'���µ�δ�ӵ�'
WHEN 2 THEN
	'�ѽӵ�δ�Ͽ�'
WHEN 3 THEN
	'�Ͽ���'
WHEN 4 THEN
	'���¿�'
WHEN 5 THEN
	'����������'
WHEN 6 THEN
	'ѧԱ������'
WHEN 7 THEN
	'˫��������'
WHEN 10 THEN
	'ȱ��'
END ����״̬,
 course_name �γ�����,
 price_total �����ܼ�,
 pay_time ֧��ʱ��,
 t.payWay ֧����ʽ,
 NULL,
 NULL,
 NULL,
 NULL,
 NULL,
 NULL,
 coupon_name �Ż�ȯ����,
 coupon �Ż�ȯ��,
 #waterNum ��������ˮ��, 
stu_name ѧԱ����,
 coach_name ��������,
 importSrc ������У
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



/*��������*/
SELECT
	t1.order_id ����ID,
	t1.price ������,
	pay_time ֧��ʱ��,
	CASE pay_state
WHEN 0 THEN
	'δ֧��'
WHEN 100 THEN
	'��֧��'
ELSE
	pay_state
END ֧��״̬,
 CASE t1.isdel
WHEN 0 THEN
	'δɾ��'
WHEN 1 THEN
	'��ɾ��'
ELSE
	t1.isdel
END ����״̬,
 t1.payWay ֧������,
 #t1.student_id ѧԱID,
NULL,
 NULL,
 NULL,
 NULL,
 NULL,
 NULL,
 t6. NAME �Ż�ȯ����,
 t5.couponId �Ż�ȯ��,
 t1. NAME ѧԱ����,
 t1.card_id ѧԱ���֤����,
 t2.phoneNum ѧԱ�ֻ�����,
 t3. NAME ��������,
 CASE t1.`dltype`
WHEN 1 THEN
	'C1'
WHEN 2 THEN
	'C2'
END ��ʻ���� #t1.price ������ 
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



/*��ֵ����*/
SELECT
	#id ID,
	orderId ��ֵ����ID,
	CASE userType
WHEN 1 THEN
	'����'
ELSE
	'ѧԱ'
END �û�����,
 IFNULL(t2. NAME, t3. NAME) ����,
 IFNULL(t2.phoneNum, t3.phoneNum) �ֻ�����,
 changeValue ��ֵ���,
 payWay ֧����ʽ,
 NULL,
 NULL,
 NULL,
 NULL,
 NULL #operateTime ����ʱ�� 
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









