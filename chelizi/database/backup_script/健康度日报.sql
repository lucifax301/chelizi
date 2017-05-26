
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
	@total AS '接口请求总次数';

	
	
SELECT
	sum(executeTime) /@total AS '接口平均耗时/ms'
FROM
	t_log_operate
WHERE
	requestTime > @time0;

SELECT
	responseCode AS '状态码',
	count(responseCode) AS '请求次数',
	@total AS '接口请求总次数',
	count(responseCode) /@total * 100 AS '所占比例/%'
FROM
	t_log_operate
WHERE
	requestTime > @time0
GROUP BY
	responseCode;

SELECT
	extra AS '请求发起地址',
	count(extra) AS '请求次数'
FROM
	t_log_operate
WHERE
	requestTime > @time0
GROUP BY
	extra;
