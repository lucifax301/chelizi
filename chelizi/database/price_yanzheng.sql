--  显示异常

--广深东佛	工作日	手动档	科目2/3基础训练 应试训练 170
SELECT * FROM `t_common_price` WHERE tstart='01-00' AND course_id IN (1,6,3,7)  AND dftype=1 AND price!=17000;
--广深东佛	周末	手动档		科目2/3基础训练 应试训练 190
SELECT * FROM `t_common_price` WHERE tstart='06-00' AND course_id IN (1,6,3,7)  AND dftype=1  AND price!=19000;
--广深东佛	工作日	自动档	科目2/3基础训练 应试训练 185
SELECT * FROM `t_common_price` WHERE tstart='01-00' AND course_id IN (11,16,13,17,1,6,3,7) AND dftype=2  AND price!=18500;
--广深东佛	周末	自动档	科目2/3基础训练 应试训练 215
SELECT * FROM `t_common_price` WHERE tstart='06-00' AND course_id IN (11,16,13,17,1,6,3,7)  AND dftype=2  AND price!=20500;

--广深东佛	工作日	手动档	科目2/3考场模拟 200
SELECT * FROM `t_common_price` WHERE tstart='01-00' AND course_id IN (2,4)  AND dftype=1  AND price!=20000;
--广深东佛	周末	手动档  科目2/3考场模拟 220
SELECT * FROM `t_common_price` WHERE tstart='06-00' AND course_id IN (2,4)  AND dftype=1  AND price!=22000;
--广深东佛	工作日	自动档	科目2/3考场模拟 215
SELECT * FROM `t_common_price` WHERE tstart='01-00' AND course_id IN (12,14,2,4)  AND dftype=2  AND price!=21500;
--广深东佛	周末	自动档	科目2/3考场模拟 235
SELECT * FROM `t_common_price` WHERE tstart='06-00' AND course_id IN (12,14,2,4)  AND dftype=2  AND price!=23500;

--广深东佛：陪驾 120
SELECT * FROM `t_common_price` WHERE course_id IN (5,15)  AND price!=12000;
--广深东佛：体验课 C1 170
SELECT * FROM `t_common_price` WHERE course_id IN (101)  AND price!=17000;



--  显示所有

--广深东佛	工作日	手动档	科目2/3基础训练 应试训练 170
SELECT * FROM `t_common_price` WHERE tstart='01-00' AND course_id IN (1,6,3,7)  AND dftype=1;
--广深东佛	周末	手动档		科目2/3基础训练 应试训练 190
SELECT * FROM `t_common_price` WHERE tstart='06-00' AND course_id IN (1,6,3,7)  AND dftype=1;
--广深东佛	工作日	自动档	科目2/3基础训练 应试训练 185
SELECT * FROM `t_common_price` WHERE tstart='01-00' AND course_id IN (11,16,13,17,1,6,3,7) AND dftype=2;
--广深东佛	周末	自动档	科目2/3基础训练 应试训练 215
SELECT * FROM `t_common_price` WHERE tstart='06-00' AND course_id IN (11,16,13,17,1,6,3,7)  AND dftype=2;

--广深东佛	工作日	手动档	科目2/3考场模拟 200
SELECT * FROM `t_common_price` WHERE tstart='01-00' AND course_id IN (2,4)  AND dftype=1;
--广深东佛	周末	手动档		科目2/3考场模拟 220
SELECT * FROM `t_common_price` WHERE tstart='06-00' AND course_id IN (2,4)  AND dftype=1;
--广深东佛	周末	自动档	科目2/3基础训练 应试训练 205
SELECT * FROM `t_common_price` WHERE tstart='01-00' AND course_id IN (12,14,2,4)  AND dftype=2;
--广深东佛	周末	自动档	科目2/3考场模拟 235
SELECT * FROM `t_common_price` WHERE tstart='06-00' AND course_id IN (12,14,2,4)  AND dftype=2;

--广深东佛：陪驾 120
SELECT * FROM `t_common_price` WHERE course_id IN (5,15);
--广深东佛：体验课 C1 170
SELECT * FROM `t_common_price` WHERE course_id IN (101);