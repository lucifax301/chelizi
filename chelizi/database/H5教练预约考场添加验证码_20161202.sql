CREATE INDEX `schoolId` ON `t_exam_place`(`schoolId`) USING BTREE ;
ALTER TABLE `t_exam_place_class` DROP INDEX `idx_pstart`;
ALTER TABLE `t_exam_place_class` MODIFY COLUMN `favorType`  int(4) NULL DEFAULT 1 COMMENT '优惠类型：0-不优惠；1-返课时；2-返金额' AFTER `innerExpire`;
ALTER TABLE `t_exam_place_order` DROP INDEX `idx_pstart`;
ALTER TABLE `t_exam_place_order` DROP INDEX `idx_coachId`;
ALTER TABLE `t_exam_place_order` MODIFY COLUMN `mtime`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' AFTER `ctime`;
ALTER TABLE `t_exam_place_order` MODIFY COLUMN `extra`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '冗余' AFTER `mtime`;
ALTER TABLE `t_exam_place_order` ADD COLUMN `code`  int(6) NULL DEFAULT NULL COMMENT '入场验证码' AFTER `extra`;
ALTER TABLE `t_exam_place_order` ADD COLUMN `codeValid`  tinyint(4) NULL DEFAULT 0 COMMENT '入场验证码是否有效 0-未验证 1-已验证' AFTER `code`;
ALTER TABLE `t_exam_place_order` ADD COLUMN `validTime`  datetime NULL DEFAULT NULL COMMENT '验证时间' AFTER `codeValid`;

ALTER TABLE `t_exam_place_order` ADD COLUMN `returnTotal`  int(11) NULL DEFAULT 0 COMMENT '返现金金额' AFTER `payTotal`;
ALTER TABLE `t_exam_place_order` ADD COLUMN `refundTotal`  int(11) NULL DEFAULT 0 COMMENT '退款金额' AFTER `returnTotal`;
CREATE INDEX `state_idx` ON `t_exam_place_order`(`state`) USING BTREE ;