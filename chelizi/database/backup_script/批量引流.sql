/*��У���ݵ���*/
#������Ϣ
INSERT INTO t_s_region (rid, region, rlevel, pid, isdel, cuid, muid, ctime, mtime)
SELECT rid, region, rlevel, pid, isdel, cuid, muid, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP() FROM Sheet4$;
#��У��Ϣ
INSERT INTO t_s_school (schoolId, NAME, image, coachCount, carCount, cityId)
VALUES (124, '������ʻѧУ', 0, 0, 0, 102100);
#ѵ������Ϣ
INSERT INTO t_s_trfield (schoolId, lge, lae, posDesc, reverseLim, phoneNum, region, NAME)
VALUES (124, 0, 0, '������У��Ŀ������ѵ����', 1000, '029-84365502', '102100104', '����-������Уѵ����');
#������Ϣ
INSERT INTO t_u_coach (schoolId, idNumber, cityId, NAME, sex, hometown, phoneNum, PASSWORD, coachCard, coachCardDate, isImport, importSrc)
SELECT 124, IDNumber, '102100', NAME, sex, hometown, phoneNum, UPPER(MD5('123456')), coachCard, coachCardDate, 1, importSrc FROM Sheet1$;
#ѧԱ��Ϣ
INSERT INTO t_u_student (schoolId, idNumber, cityId, NAME, sex, phoneNum, PASSWORD, drType, applyCarType, isImport, importSrc, StuCoachEmpID)
SELECT 124, IDNumber, '102100', NAME, sex, phoneNum, UPPER(MD5('123456')), drType, drType, 1, importSrc, StuCoachEmpID FROM Sheet2$;
#������Ϣ
INSERT INTO t_p_car (carType, carNo, driveType, UsePerson, schoolId, cityId)
SELECT carType, carNo, driveType, UsePerson, 124, 102100 FROM Sheet3$;
#��Ӱ󶨹�ϵ
INSERT INTO t_u_coachcar (coachId, carId)
SELECT coachId, carId FROM t_u_coach AS t1 JOIN t_p_car AS t2 ON t1.name = t2.UsePerson WHERE t1.schoolId = 124;
INSERT INTO t_u_mycoaches (coachId, studentId)
SELECT coachId, studentId FROM t_u_coach AS t1 JOIN t_u_student AS t2 ON t1.stuCoachID = t2.stuCoachEmpID WHERE t1.schoolId = 124;
#����ѧԱ������󶨵�Ψһ��ʶ
UPDATE Sheet2$ AS t1, t_u_coach AS t2 SET StuCoachEmpID = t2.coachId WHERE t1.`������` = t2.name
UPDATE t_u_coach SET StuCoachID = coachId WHERE schoolId = 124;
#����
SELECT coachId, schoolId, idNumber, cityId, NAME, sex, hometown, phoneNum, PASSWORD, coachCard, coachCardDate, isImport, importSrc, StuCoachID FROM t_u_coach WHERE schoolId = 124
#ѧԱ
SELECT schoolId, idNumber, cityId, NAME, sex, phoneNum, PASSWORD, drType, applyCarType, isImport, importSrc, StuCoachEmpID FROM t_u_student WHERE schoolId = 124
#����
SELECT carType, carNo, driveType, UsePerson, schoolId, cityId FROM t_p_car WHERE schoolId = 124