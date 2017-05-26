UPDATE t_enroll_package_template SET price_detail = '科目一报考费=50+科目二报考费=200+科目三报考费=260+工本费=10+档案费=337+刷卡费=98+照相费=25' WHERE ttid = 6;
/*深圳东莞 6 - 7*/
UPDATE t_coach_class_price SET courseId = 6 WHERE id IN (100, 340);
/*深圳*/
UPDATE t_common_price SET price = 20000 WHERE city_id = 100100 AND course_id IN (2, 12, 4, 14);

UPDATE t_coach_class_price SET price = 44000, mtime = NOW() WHERE cityId = 100100 AND tstart = '01-00' AND duration = 4 AND maxNum = 4 AND dftype = 1;
UPDATE t_coach_class_price SET price = 17000, mtime = NOW() WHERE cityId = 100100 AND tstart = '06-00' AND duration = 1 AND maxNum = 1 AND dftype = 1;
UPDATE t_coach_class_price SET price = 31600, mtime = NOW() WHERE cityId = 100100 AND tstart = '06-00' AND duration = 2 AND maxNum = 1 AND dftype = 1;
UPDATE t_coach_class_price SET price = 40800, mtime = NOW() WHERE cityId = 100100 AND tstart = '06-00' AND duration = 3 AND maxNum = 3 AND dftype = 1;
UPDATE t_coach_class_price SET price = 48000, mtime = NOW() WHERE cityId = 100100 AND tstart = '06-00' AND duration = 4 AND maxNum = 4 AND dftype = 1;

UPDATE t_coach_class_price SET price = 16800, mtime = NOW() WHERE cityId = 100100 AND tstart = '01-00' AND duration = 1 AND maxNum = 1 AND dftype = 2;
UPDATE t_coach_class_price SET price = 31600, mtime = NOW() WHERE cityId = 100100 AND tstart = '01-00' AND duration = 2 AND maxNum = 1 AND dftype = 2;
UPDATE t_coach_class_price SET price = 29600, mtime = NOW() WHERE cityId = 100100 AND tstart = '01-00' AND duration = 2 AND maxNum = 2 AND dftype = 2;
UPDATE t_coach_class_price SET price = 40800, mtime = NOW() WHERE cityId = 100100 AND tstart = '01-00' AND duration = 3 AND maxNum = 3 AND dftype = 2;
UPDATE t_coach_class_price SET price = 48000, mtime = NOW() WHERE cityId = 100100 AND tstart = '01-00' AND duration = 4 AND maxNum = 4 AND dftype = 2;
UPDATE t_coach_class_price SET price = 33600, mtime = NOW() WHERE cityId = 100100 AND tstart = '06-00' AND duration = 2 AND maxNum = 2 AND dftype = 2;
UPDATE t_coach_class_price SET price = 59200, mtime = NOW() WHERE cityId = 100100 AND tstart = '06-00' AND duration = 4 AND maxNum = 4 AND dftype = 2;
/*东莞*/
UPDATE t_coach_class_price SET price = 44000, mtime = NOW() WHERE cityId = 100101 AND tstart = '01-00' AND duration = 4 AND maxNum = 4 AND dftype = 1;
UPDATE t_coach_class_price SET price = 17000, mtime = NOW() WHERE cityId = 100101 AND tstart = '06-00' AND duration = 1 AND maxNum = 1 AND dftype = 1;
UPDATE t_coach_class_price SET price = 31600, mtime = NOW() WHERE cityId = 100101 AND tstart = '06-00' AND duration = 2 AND maxNum = 1 AND dftype = 1;
UPDATE t_coach_class_price SET price = 40800, mtime = NOW() WHERE cityId = 100101 AND tstart = '06-00' AND duration = 3 AND maxNum = 3 AND dftype = 1;
UPDATE t_coach_class_price SET price = 48000, mtime = NOW() WHERE cityId = 100101 AND tstart = '06-00' AND duration = 4 AND maxNum = 4 AND dftype = 1;

UPDATE t_coach_class_price SET price = 16800, mtime = NOW() WHERE cityId = 100101 AND tstart = '01-00' AND duration = 1 AND maxNum = 1 AND dftype = 2;
UPDATE t_coach_class_price SET price = 31600, mtime = NOW() WHERE cityId = 100101 AND tstart = '01-00' AND duration = 2 AND maxNum = 1 AND dftype = 2;
UPDATE t_coach_class_price SET price = 29600, mtime = NOW() WHERE cityId = 100101 AND tstart = '01-00' AND duration = 2 AND maxNum = 2 AND dftype = 2;
UPDATE t_coach_class_price SET price = 40800, mtime = NOW() WHERE cityId = 100101 AND tstart = '01-00' AND duration = 3 AND maxNum = 3 AND dftype = 2;
UPDATE t_coach_class_price SET price = 48000, mtime = NOW() WHERE cityId = 100101 AND tstart = '01-00' AND duration = 4 AND maxNum = 4 AND dftype = 2;
UPDATE t_coach_class_price SET price = 33600, mtime = NOW() WHERE cityId = 100101 AND tstart = '06-00' AND duration = 2 AND maxNum = 2 AND dftype = 2;
UPDATE t_coach_class_price SET price = 59200, mtime = NOW() WHERE cityId = 100101 AND tstart = '06-00' AND duration = 4 AND maxNum = 4 AND dftype = 2;
/*长沙*/
UPDATE t_coach_class_price SET price = 27000, mtime = NOW() WHERE cityId = 103100 AND tstart = '01-00' AND duration = 3 AND maxNum = 3 AND dftype = 1;
UPDATE t_coach_class_price SET price = 34000, mtime = NOW() WHERE cityId = 103100 AND tstart = '01-00' AND duration = 4 AND maxNum = 4 AND dftype = 1;
UPDATE t_coach_class_price SET price = 25600, mtime = NOW() WHERE cityId = 103100 AND tstart = '06-00' AND duration = 2 AND maxNum = 1 AND dftype = 1;
UPDATE t_coach_class_price SET price = 23600, mtime = NOW() WHERE cityId = 103100 AND tstart = '06-00' AND duration = 2 AND maxNum = 2 AND dftype = 1;
UPDATE t_coach_class_price SET price = 30000, mtime = NOW() WHERE cityId = 103100 AND tstart = '06-00' AND duration = 3 AND maxNum = 3 AND dftype = 1;
UPDATE t_coach_class_price SET price = 36000, mtime = NOW() WHERE cityId = 103100 AND tstart = '06-00' AND duration = 4 AND maxNum = 4 AND dftype = 1;

UPDATE t_coach_class_price SET price = 25600, mtime = NOW() WHERE cityId = 103100 AND tstart = '01-00' AND duration = 2 AND maxNum = 1 AND dftype = 2;
UPDATE t_coach_class_price SET price = 31800, mtime = NOW() WHERE cityId = 103100 AND tstart = '01-00' AND duration = 3 AND maxNum = 3 AND dftype = 2;
UPDATE t_coach_class_price SET price = 40000, mtime = NOW() WHERE cityId = 103100 AND tstart = '01-00' AND duration = 4 AND maxNum = 4 AND dftype = 2;
UPDATE t_coach_class_price SET price = 28000, mtime = NOW() WHERE cityId = 103100 AND tstart = '06-00' AND duration = 2 AND maxNum = 1 AND dftype = 2;
UPDATE t_coach_class_price SET price = 25600, mtime = NOW() WHERE cityId = 103100 AND tstart = '06-00' AND duration = 2 AND maxNum = 2 AND dftype = 2;
UPDATE t_coach_class_price SET price = 36000, mtime = NOW() WHERE cityId = 103100 AND tstart = '06-00' AND duration = 3 AND maxNum = 3 AND dftype = 2;
UPDATE t_coach_class_price SET price = 44000, mtime = NOW() WHERE cityId = 103100 AND tstart = '06-00' AND duration = 4 AND maxNum = 4 AND dftype = 2;
/*西安*/
UPDATE t_coach_class_price SET price = 27000, mtime = NOW() WHERE cityId = 102100 AND tstart = '01-00' AND duration = 3 AND maxNum = 3 AND dftype = 1;
UPDATE t_coach_class_price SET price = 34000, mtime = NOW() WHERE cityId = 102100 AND tstart = '01-00' AND duration = 4 AND maxNum = 4 AND dftype = 1;
UPDATE t_coach_class_price SET price = 25600, mtime = NOW() WHERE cityId = 102100 AND tstart = '06-00' AND duration = 2 AND maxNum = 1 AND dftype = 1;
UPDATE t_coach_class_price SET price = 23600, mtime = NOW() WHERE cityId = 102100 AND tstart = '06-00' AND duration = 2 AND maxNum = 2 AND dftype = 1;
UPDATE t_coach_class_price SET price = 30000, mtime = NOW() WHERE cityId = 102100 AND tstart = '06-00' AND duration = 3 AND maxNum = 3 AND dftype = 1;
UPDATE t_coach_class_price SET price = 36000, mtime = NOW() WHERE cityId = 102100 AND tstart = '06-00' AND duration = 4 AND maxNum = 4 AND dftype = 1;

UPDATE t_coach_class_price SET price = 25600, mtime = NOW() WHERE cityId = 102100 AND tstart = '01-00' AND duration = 2 AND maxNum = 1 AND dftype = 2;
UPDATE t_coach_class_price SET price = 31800, mtime = NOW() WHERE cityId = 102100 AND tstart = '01-00' AND duration = 3 AND maxNum = 3 AND dftype = 2;
UPDATE t_coach_class_price SET price = 40000, mtime = NOW() WHERE cityId = 102100 AND tstart = '01-00' AND duration = 4 AND maxNum = 4 AND dftype = 2;
UPDATE t_coach_class_price SET price = 28000, mtime = NOW() WHERE cityId = 102100 AND tstart = '06-00' AND duration = 2 AND maxNum = 1 AND dftype = 2;
UPDATE t_coach_class_price SET price = 25600, mtime = NOW() WHERE cityId = 102100 AND tstart = '06-00' AND duration = 2 AND maxNum = 2 AND dftype = 2;
UPDATE t_coach_class_price SET price = 36000, mtime = NOW() WHERE cityId = 102100 AND tstart = '06-00' AND duration = 3 AND maxNum = 3 AND dftype = 2;
UPDATE t_coach_class_price SET price = 44000, mtime = NOW() WHERE cityId = 102100 AND tstart = '06-00' AND duration = 4 AND maxNum = 4 AND dftype = 2;
/*所有区域的现约价格减少20元（接送费）*/
UPDATE t_common_price SET price = price - 2000;
