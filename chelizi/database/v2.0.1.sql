ALTER TABLE `db_lili`.`t_student_class_pool`   
  ADD COLUMN `carId` BIGINT(11) NOT NULL   COMMENT 'ΩÃ¡∑≥µid' AFTER `coachId`,
  ADD COLUMN `carNo` VARCHAR(32) NULL   COMMENT '≥µ≈∆∫≈' AFTER `coachMobile`;
UPDATE `db_lili`.`t_student_class_pool` SET carId = extra;
UPDATE `db_lili`.`t_student_class_pool` AS t1, t_p_car AS t2 SET t1.`carNo` = t2.`carNo` WHERE t1.`extra` = t2.`carId`;