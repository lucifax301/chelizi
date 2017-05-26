ALTER TABLE `db_lili`.`t_u_student` ADD COLUMN `packageId` int(11) COMMENT '微信报名端班别id 0表示未关联';
ALTER TABLE `db_lili`.`t_u_student` ADD COLUMN `relevanceTime` timestamp NULL DEFAULT NULL COMMENT '关联班别时间';


ALTER TABLE `db_lili`.`t_wechat_enroll_package` ADD COLUMN `hoursPrice` int(11) DEFAULT NULL COMMENT '课时价格';