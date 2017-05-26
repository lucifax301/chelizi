

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130214,130200,'优惠券延期','','/coupon/prolong',2,3,1,14,14);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130215,130200,'优惠券分组','','/coupon/groupCoupon',2,3,1,15,15);

insert into t_c_role_permission(permission_id,role_id) values(130214,1);
insert into t_c_role_permission(permission_id,role_id) values(130215,1);