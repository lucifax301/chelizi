update t_c_permission set menuOrder=menuOrder+1 where menuOrder>2 and pid=120000
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(121200,120000,'����ԤԼ����','exam-ord.html','/examPlace/order',1,2,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(121300,120000,'����','exam-field.html','/examPlace',1,2,1,4,4);
insert into t_c_role_permission values(121200,1),(121300,1);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(121201,121200,'�鿴��������','exam-ord-details.html','/examPlace/order/info',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(121202,121200,'�رն���','exam-ord.html','/examPlace/order/update',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(121301,121300,'��������','exam-field.html','/examPlace',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(121302,121300,'�༭����','exam-field.html','/examPlace/update',2,3,1,2,2);
insert into t_c_role_permission values(121201,1),(121202,1),(121301,1),(121302,1);