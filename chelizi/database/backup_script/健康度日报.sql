
SET @time0 = '2016-06-16';


-- --------------------------------------
SET @time0 = '$DATE_NOW';
USE bak_online_db_log;

SELECT
	count(1)
FROM
	`t_log_operate`
WHERE
	requestTime > @time0 INTO @total;

SELECT
	@total AS '�ӿ������ܴ���';

	
	
SELECT
	sum(executeTime) /@total AS '�ӿ�ƽ����ʱ/ms'
FROM
	t_log_operate
WHERE
	requestTime > @time0;

SELECT
	responseCode AS '״̬��',
	count(responseCode) AS '�������',
	@total AS '�ӿ������ܴ���',
	count(responseCode) /@total * 100 AS '��ռ����/%'
FROM
	t_log_operate
WHERE
	requestTime > @time0
GROUP BY
	responseCode;

SELECT
	extra AS '�������ַ',
	count(extra) AS '�������'
FROM
	t_log_operate
WHERE
	requestTime > @time0
GROUP BY
	extra;
