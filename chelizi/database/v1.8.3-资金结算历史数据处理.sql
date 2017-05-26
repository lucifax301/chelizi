UPDATE t_u_money set isBalance =0,isEarning = 0 WHERE userType =2 and operateType =7 and payWay !='balance' and operateTime < '2016-06-30 21:00:00'; -- 非余额支付报名费
UPDATE t_u_money set isBalance =1,isEarning = 0 WHERE userType =2 and operateType =7 and payWay = 'balance' and operateTime < '2016-06-30 21:00:00'; -- 余额支付报名费

UPDATE t_u_money set isBalance =1,isEarning = 2 WHERE userType =4 and operateType =7 ; -- 喱喱收到报名费不算收入，代收款
-- '2016-06-30 21:00:00'这个时间之前的报名费未计入喱喱代收款，是否还需要添加

UPDATE t_u_money set isBalance =0,isEarning = 0 WHERE userType =2 and operateType =5 and payWay !='balance' and operateTime < '2016-06-30 21:00:00'; -- 非余额支付课时费
UPDATE t_u_money set isBalance =1,isEarning = 0 WHERE userType =2 and operateType =5 and payWay = 'balance' and operateTime < '2016-06-30 21:00:00'; -- 余额支付课时费
UPDATE t_u_money set isBalance =1,isEarning = 1 WHERE userType =3 and operateType =5 and operateTime < '2016-06-30 21:00:00'; -- 驾校收课时费

UPDATE t_u_money set isBalance =1,isEarning = 0 WHERE userType =3 and operateType =8 and operateTime < '2016-06-30 21:00:00'; -- 驾校余额支付佣金
UPDATE t_u_money set isBalance =1,isEarning = 1 WHERE userType =4 and operateType =8 and operateTime < '2016-06-30 21:00:00'; -- 喱喱余额收入佣金

UPDATE t_u_money set isBalance =1,isEarning = 2 WHERE operateType =0 and operateTime < '2016-06-30 21:00:00'; -- 充值都计入余额，不算支出和收入
UPDATE t_u_money set isBalance =1,isEarning = 2 WHERE operateType =1 and operateTime < '2016-06-30 21:00:00'; -- 提现都计入余额，不算支出和收入

UPDATE t_u_money set isBalance =1,isEarning = 1 WHERE userType =1 and operateType =2 and operateTime < '2016-06-30 21:00:00'; -- 奖金都计入余额，教练作为收入
UPDATE t_u_money set isBalance =1,isEarning = 0 WHERE userType =4 and operateType =2 and operateTime < '2016-06-30 21:00:00'; -- 奖金都计入余额，喱喱作为支出

UPDATE t_u_money set isBalance =1,isEarning = 1 WHERE userType =2 and operateType =18 and operateTime < '2016-06-30 21:00:00'; -- 充值送现金，学员作为余额收入
UPDATE t_u_money set isBalance =1,isEarning = 0 WHERE userType =4 and operateType =18 and operateTime < '2016-06-30 21:00:00'; -- 充值送现金，喱喱作为余额支出