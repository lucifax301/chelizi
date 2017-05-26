UPDATE t_u_student AS t1, t_s_school AS t2 SET t1.cityId = t2.cityId WHERE t1.schoolId = t2.schoolId AND t1.schoolId != 0;
