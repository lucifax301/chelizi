ALTER TABLE t_u_coupon ADD INDEX index_couponTmpId (couponTmpId);

ALTER TABLE t_s_coupon ADD column groupType tinyint(4) DEFAULT '1' COMMENT '�Ż�ȯ����:1��ͨȯ��2����ȯ��3��Ƶȯ' after isUse;
ALTER TABLE t_s_coupon ADD column course2 tinyint(4) DEFAULT '0' COMMENT '��Ŀ����0-��1-��' after groupType;
ALTER TABLE t_s_coupon ADD column course3 tinyint(4) DEFAULT '0' COMMENT '��Ŀ����0-��1-��' after course2;
ALTER TABLE t_s_coupon ADD column courseDrive tinyint(4) DEFAULT '0' COMMENT '��ݣ�0-��1-��' after course3;
ALTER TABLE t_s_coupon ADD column courseEnroll tinyint(4) DEFAULT '0' COMMENT '������0-��1-��' after courseDrive;
ALTER TABLE t_s_coupon ADD column cityId varchar(255) DEFAULT  NULL COMMENT '���ó���' after courseEnroll;
ALTER TABLE t_s_coupon ADD column limitTime int(11) DEFAULT '1' COMMENT '�������' after cityId;


update `t_s_coupon` set groupType=1;

-- ��ʼ��ԭ���ݿ�Ŀ����
update `t_s_coupon` set course2=1,course3=1 where useRule like '%16%' or useRule like '%83%' or useRule like '%95%';
update `t_s_coupon` set course2=1 where useRule like '%10%' or useRule like '%17%' or useRule like '%19%' or useRule like '%78%' or useRule like '%91%';
update `t_s_coupon` set course3=1 where useRule like '%14%' or useRule like '%18%' or useRule like '%20%'  or useRule like '%92%';
update `t_s_coupon` set courseDrive=1 where useRule like '%21%' or useRule like '%84%';
update `t_s_coupon` set course2=1,course3=1,courseDrive=1 where useRule like '%74%';

-- ��ʼ��ԭ���ݳ�������
update `t_s_coupon` set cityId='100100|100101|100102|100103' where useRule like '%|2,%';
update `t_s_coupon` set cityId='100100|100101|100102|100103|102100|103100' where useRule like '%58%';
update `t_s_coupon` set cityId='100100|100101' where useRule like '%81%' or useRule like '%85%';
update `t_s_coupon` set cityId='103100|102100' where useRule like '%98%';
update `t_s_coupon` set cityId='100101' where useRule like '%64%';
update `t_s_coupon` set cityId='103100' where useRule like '%69%';
update `t_s_coupon` set cityId='100100' where useRule like '%75%';
update `t_s_coupon` set cityId='103100|102100|100100|100101|100102' where useRule like '%73%';

update `t_s_coupon` set courseEnroll=1 where useRule like '%60%';

-- ��ʼ��ԭ�����������
update `t_s_coupon` set limitTime=1 where genRule like '%57%' or genRule like '%62%' or genRule like '%63%' 
or genRule like '%66%' or genRule like '%67%' or genRule like '%68%' or genRule like '%71%' or genRule like '%72%'
or genRule like '%76%' or genRule like '%77%' or genRule like '%79%' or genRule like '%89%' or genRule like '%94%';
update `t_s_coupon` set limitTime=8 where genRule like '%11%' or genRule like '%13%';
update `t_s_coupon` set limitTime=20 where genRule like '%86%';
update `t_s_coupon` set limitTime=25 where genRule like '%87%' or genRule like '%99%';
update `t_s_coupon` set limitTime=4 where genRule like '%96%';
