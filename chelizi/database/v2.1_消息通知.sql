ALTER TABLE db_lili.`t_p_notice` CHANGE content content TEXT DEFAULT NULL COMMENT '内容';
ALTER TABLE db_lili.`t_p_notice` CHANGE pic pic VARCHAR(100) DEFAULT NULL COMMENT '消息的图片';
ALTER TABLE db_lili.`t_p_notice` CHANGE TYPE TYPE TINYINT(4) DEFAULT NULL COMMENT '公告类别（0-今日重点播报、1-活动消息、2-订单消息 3-喱喱头条、4-我的消息 5-系统消息）';
ALTER TABLE db_lili.`t_p_notice` CHANGE isdel isdel TINYINT(4) DEFAULT '0' COMMENT '状态：0-已发布；1-已删除 2-草稿';
ALTER TABLE db_lili.`t_p_notice` CHANGE userType userType TINYINT(4) DEFAULT '0' COMMENT '通知对象 (0 全体用户 1 全体教练 2 全体学员 1指定教练 2 指定学员 3驾校教练 4驾校学员 5 城市教练 6城市学员 7特约教练 8普通教练 9-cms消息中心配置教练消息 10-cms消息中心配置学员消息)';

ALTER TABLE db_lili.`t_p_notice` ADD COLUMN clickNum INT(11) DEFAULT '0' COMMENT '点击量';
ALTER TABLE db_lili.`t_p_notice` ADD COLUMN applyexam VARCHAR(1000) DEFAULT NULL COMMENT '学员进度，根据学员表的applyexam和applystate两字段组合 |applyexam,applystate|applyexam,applystate|';
ALTER TABLE db_lili.`t_p_notice` ADD COLUMN cityId  VARCHAR(200) DEFAULT NULL COMMENT '城市Id 中间逗号隔开';
ALTER TABLE db_lili.`t_p_notice` ADD COLUMN schoolId VARCHAR(500) DEFAULT NULL COMMENT '驾校Id 存储格式 ,schoolId,schoolId,';
ALTER TABLE db_lili.`t_p_notice` ADD COLUMN schoolName VARCHAR(500) DEFAULT NULL COMMENT '驾校名称 中间逗号隔开';
ALTER TABLE db_lili.`t_p_notice` ADD COLUMN userIdStrs VARCHAR(1000) DEFAULT NULL COMMENT '学员名称 存储格式 ,id,id,';
ALTER TABLE db_lili.`t_p_notice` ADD COLUMN userName VARCHAR(1000) DEFAULT NULL COMMENT '学员名称 中间逗号隔开';
ALTER TABLE db_lili.`t_p_notice` ADD COLUMN utype VARCHAR(200) DEFAULT NULL COMMENT '通知类型（1-驾校学员 2-喱喱学员 3-特约教练 4-普通教练）中间逗号隔开';
ALTER TABLE db_lili.`t_p_notice` ADD COLUMN digest VARCHAR(200) DEFAULT NULL COMMENT '摘要';
ALTER TABLE db_lili.`t_p_notice` ADD COLUMN orderId VARCHAR(32) DEFAULT NULL COMMENT '订单Id';


UPDATE db_lili_cms.`t_c_permission` SET name='App消息中心配置',ajax='/notice/getNotice,/notice/getNoticeById,/coach/batch,student/lili-batch,school/querySchool,/resource/token' WHERE id=140200;
UPDATE  db_lili_cms.`t_c_permission`  SET  NAME='编辑通知',ajax='/notice/update,/notice/updateState'  WHERE  id=140201;
UPDATE  db_lili_cms.`t_c_permission`  SET  ajax='/notice/add,/notice/update'  WHERE  id=140202;
DELETE FROM db_lili_cms.`t_c_permission` WHERE id=140203;
DELETE FROM db_lili_cms.`t_c_role_permission` WHERE permission_id=140203;
INSERT  INTO  db_lili_cms.`t_c_permission`(id,NAME,pid,TYPE,enabled,menuOrder,layoutOrder,LEVEL,ajax,url)  VALUES
            (140204,'删除通知',140200,2,1,4,4,3,'/notice/updateState','');
INSERT  INTO  db_lili_cms.`t_c_role_permission`  (permission_id,role_id)  VALUES  (140204,1);

