ALTER TABLE `db_lili`.`t_u_student`   
  ADD COLUMN `qqOpenId` VARCHAR(128) NULL   COMMENT 'QQ��openId' AFTER `relevanceTime`;
ALTER TABLE `db_lili`.`t_u_student`   
  ADD  UNIQUE INDEX `openId` (`openId`);
ALTER TABLE `db_lili`.`t_u_student`   
  ADD  UNIQUE INDEX `qqOpenId` (`qqOpenId`);
  
/*
ԭopenIdû��ΨһУ�飬��ͬһ��openId��Ӧ����ʺŵ�������¼��������졣
����������һ��openId��Ӧ����ʺŵ�������������²�ѯ�����ֶ������������ĸ��ʺš�
���ߵ�ʱ����Ҫע�⣡
*/
SELECT idNumber, NAME, phoneNum, isImport, importSrc, schoolId, cityId, applyexam, applystate, openId, registerTime, firstLogin FROM t_u_student WHERE openId IN 
(SELECT openId FROM t_u_student WHERE openId IS NOT NULL GROUP BY openId HAVING COUNT(*) > 1) ORDER BY openId;

/*20161107 ���£� ���¶���openid�ظ��ģ���openid��������Ψһ����*/
update t_u_student set openId = null 
where studentId in 
(36393047, 41123427, 10257428, 44756495, 19369569, 67771704, 30906980, 42569593, 65919585, 41364210,
27806050, 41845136, 63813066, 63287714, 47458854, 15292464, 60163242, 40765996, 40795365, 57580239, 34076884);


--  11��12���ջݻ
update `t_recharge_plan` set vtype=2,active=1,common=1,need_verify=0 where rcid in (161025001,161025002,161025003,161025004);

insert into db_lili_cms.t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(121303,121300,'�鿴�Ű�','arrange-exam-class.html','/examPlace/class,/examPlace/class/date',2,3,1,3,3);
insert into db_lili_cms.t_c_role_permission(permission_id,role_id) values(121303,1);