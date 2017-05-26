update t_c_permission set menuOrder=menuOrder+1,layoutOrder=layoutOrder+1 where level=1 and channel_type=0 and menuOrder>=3;
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder,must) values(160000,0,'考试管理','',3,1,1,3,3,0);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(160100,160000,'长考看板','long-exam.html','/school/enroll/longtrain',1,2,1,1,0);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(160200,160000,'理论课管理','theoretical-lessons.html','/school/enroll/theory',1,2,2,2,0);
insert into t_c_role_permission values(160000,1),(160100,1),(160200,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(160101,160100,'查看详情','','/school/enroll/longtrain/one,/school/enroll/longtrainStudent',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(160201,160200,'查看详情','','/school/enroll/theory/one,/school/enroll/theoryStudent',2,3,1,1,1);
insert into t_c_role_permission values(160101,1),(160201,1);

