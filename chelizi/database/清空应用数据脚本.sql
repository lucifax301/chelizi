-- ************************清空应用数据**************************************
use db_lili;
TRUNCATE t_order;
TRUNCATE t_order_cancel;
TRUNCATE t_order_complain;
TRUNCATE t_coach_class;
TRUNCATE t_coach_statistic;
TRUNCATE t_coach_score;
TRUNCATE t_plant_class;
TRUNCATE t_student_statistic;
TRUNCATE t_stu_comment;
TRUNCATE t_coach_comment;
TRUNCATE t_u_device;

-- TRUNCATE t_u_coachaccount;
-- TRUNCATE t_u_stuaccount;

update t_u_coach set wstate=0 and eventDesc='';
update t_u_student set eventState=0 and eventDesc='';

use db_log;
TRUNCATE t_log_complain;
TRUNCATE t_log_coupon;
TRUNCATE t_log_money;
TRUNCATE t_log_operate;
TRUNCATE t_log_pay;
TRUNCATE t_log_stuskill;
TRUNCATE t_log_techquality;

-- redis清除
-- redis-cli flushdb;

-- --------------清空报名应用数据-------------
use db_lili;
-- -----------初始化数据 所有从驾校导入的学员，默认报名状态设置为报考已提交阶段“请您耐心等待报考结果”，下一个版本自主约考走通后设置状态为已成功即可-----------------
UPDATE t_u_student set applyexam =0, applystate =0,applyorder ='',applyttid = null WHERE isImport = 0;
UPDATE t_u_student set applyexam =5, applystate =1,applyorder ='',applyttid = 1 WHERE isImport = 1;
TRUNCATE table t_enroll_exam_reg;
TRUNCATE TABLE t_enroll_order;
TRUNCATE TABLE t_enroll_progress_user;
-- redis-cli flushdb;