UPDATE t_enroll_package_template SET price_detail = '��Ŀһ������=50+��Ŀ��������=200+��Ŀ��������=260+������=10+������=337+ˢ����=98+�����=25' WHERE ttid = 6;
/*���ڶ�ݸ 6 - 7*/
UPDATE t_coach_class_price SET courseId = 6 WHERE id IN (100, 340);
/*����*/
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
/*��ݸ*/
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
/*��ɳ*/
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
/*����*/
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
/*�����������Լ�۸����20Ԫ�����ͷѣ�*/
UPDATE t_common_price SET price = price - 2000;
