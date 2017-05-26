
USE  db_lili_cms;
INSERT INTO t_c_permission(id,pid,NAME,url,ajax,TYPE,LEVEL,enabled,layoutOrder,menuOrder) VALUES(131201,131200,'查看列表','','/order/insurance/list',2,3,1,1,1);
INSERT INTO t_c_permission(id,pid,NAME,url,ajax,TYPE,LEVEL,enabled,layoutOrder,menuOrder) VALUES(131202,131200,'编辑信息','','/order/insurance/update',2,3,1,2,2);
INSERT INTO t_c_permission(id,pid,NAME,url,ajax,TYPE,LEVEL,enabled,layoutOrder,menuOrder) VALUES(131203,131200,'查看详情','insure-details.html','/order/insurance/findInsuranceById',2,3,1,3,3);
INSERT INTO t_c_permission(id,pid,NAME,url,TYPE,LEVEL,enabled,layoutOrder,menuOrder) VALUES(131200,130000,'保险管理','insure.html',1,2,1,12,12);
INSERT INTO t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(131204,131200,'数据导出','','/order/insurance-export-excel',2,3,1,4,4);

UPDATE t_c_permission SET ajax='/student/account,/order/insurance/list' WHERE id=120510;

INSERT INTO `t_c_role_permission` (permission_id,role_id) VALUES (131201,1);
INSERT INTO `t_c_role_permission` (permission_id,role_id) VALUES (131202,1);
INSERT INTO `t_c_role_permission` (permission_id,role_id) VALUES (131203,1);
INSERT INTO `t_c_role_permission` (permission_id,role_id) VALUES (131200,1);

UPDATE t_enroll_progress_template SET succ_doc='恭喜您，科目一于{1}考试成绩合格',fail_doc='很遗憾，科目一于{1}考试没有通过，要多多努力练习哦，祝您早日通过' WHERE  step_id=302;
UPDATE t_enroll_progress_template SET succ_doc='恭喜您，科目二于{1}考试成绩合格',fail_doc='很遗憾，科目二于{1}考试没有通过，要多多努力练习哦，祝您早日通过'  WHERE  step_id=402;
UPDATE t_enroll_progress_template SET succ_doc='恭喜您，科目三于{1}考试成绩合格',fail_doc='很遗憾，科目三于{1}考试没有通过，要多多努力练习哦，祝您早日通过'  WHERE  step_id=602;
UPDATE t_enroll_progress_template SET succ_doc='恭喜您，科目四于{1}考试成绩合格',fail_doc='很遗憾，科目四于{1}考试没有通过，要多多努力练习哦，祝您早日通过'  WHERE  step_id=702;

--redis-cli -a chelizi keys enroll.progress.template.* |xargs redis-cli -a chelizi del
--redis-cli -a chelizi keys wechat.* |xargs redis-cli -a chelizi del
