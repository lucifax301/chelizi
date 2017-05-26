
-- 教练余额统计
SELECT
	CASE
WHEN t1.coachId = 0 THEN
	'车厘子公司'
ELSE
	t2. NAME
END 姓名,
 t2.phoneNum 手机号,
 t1.money / 100 余额（元）
FROM
	t_u_coachaccount t1
LEFT JOIN t_u_coach t2 ON t1.coachId = t2.coachId
WHERE
	t1.money != 0
ORDER BY
	money DESC;
	
	
-- 学员余额统计	
SELECT
	t2.name AS 姓名,
	t2.phoneNum AS 手机号,
	t1.money / 100 AS 余额（元）
FROM
	t_u_stuaccount t1
LEFT JOIN t_u_student t2 ON t1.studentId = t2.studentId
WHERE
	t1.money != 0
ORDER BY
	money DESC;
	

-- 奖金发放记录
SELECT
	t2.name AS '姓名',
	t2.phoneNum AS '手机号',
	t2.importSrc AS '驾校',
	t1.changeValue / 100 AS '发放奖金（元）',
	t1.operateTime AS '发放时间',
	t1.remark AS '备注'
FROM
	t_u_money t1
LEFT JOIN t_u_coach t2 ON t1.userId = t2.coachId
WHERE
	t1.userType = 1
AND t1.userId != 0
AND t1.operateType = 2
ORDER BY
	t1.operateTime DESC;


-- 教练和学员的提现记录
SELECT
	t1.apply_time AS '申请时间',
	t2. NAME AS '申请人',
	CASE t1.user_type
WHEN 1 THEN
	'教练'
WHEN 2 THEN
	'学员'
END AS '申请人类型',
 t1.money /- 100 AS '金额',
 t1.type AS '提现到',
 t1.bank_card AS '卡号',
 t1.check_remark AS '财务反馈'
FROM
	t_u_deposit t1
LEFT JOIN t_u_coach t2 ON t1.user_id = t2.coachId
WHERE
	-- user_type = 1 AND 
	check_status = 1
ORDER BY
	user_type,
	t1.apply_time DESC;











	
	