
ALTER TABLE `db_lili`.`t_s_coupon`
  ADD COLUMN `maxNum` int(11) DEFAULT '0' COMMENT '���ʹ�ö�����',
  ADD COLUMN `isUse` tinyint(4) DEFAULT '0' COMMENT '�Ƿ���Ը��ջ�һ���ã�0-��1-��';

update `t_s_coupon` set extra='100',isExist=1 where couponTmpId in ('464b7dee154c4c1ebc497d6a4ea45b3f','1706e9c40e99482fbada0f127b427a03','65dabc19350348ab98c5fa619f7ce221','901d206ea7254e5cbb149e7309454483','42f0187cd0874042b27e8e827c4a9c14','873621377fa1462abdc76055dae5ec89','834042c5c89843eaaa08bf55f1ac4104','20c87a9ea3d04dcb863cc7cb1e1b82b5','bd3a1acfc4964b6e85b790952f411ac0');


update `t_s_coupon` set expireTime='2016-12-31 23:59:59',validityPeriod=0,useNote='1���õ���ȯ���������±����û�ʹ�� |2���õ���ȯ���ֿ۱����ѣ�ÿ������1�ţ��������������Ż�ͬʱʹ�� |3���õ���ȯ�����֣�ʹ����Ч���Լ�������2016��12��31�� |4���õ����Ż����ս���Ȩ�������г���������Ƽ����޹�˾����' where couponTmpId='464b7dee154c4c1ebc497d6a4ea45b3f';


update `t_s_coupon` set expireTime='2017-09-30 23:59:59',validityPeriod=0 where couponTmpId in ('1706e9c40e99482fbada0f127b427a03','65dabc19350348ab98c5fa619f7ce221','901d206ea7254e5cbb149e7309454483','42f0187cd0874042b27e8e827c4a9c14');
  

update `t_s_coupon` set expireTime='2017-09-30 23:59:59',validityPeriod=0,useNote='1���õ���ȯ��������У԰�û�ʹ�� |2���õ���ȯ���ֿ���ݷѣ�ÿ������1�ţ����������Ż�ͬʱʹ�� |3���õ���ȯ�����֣�����ת��ʹ����Ч���Լ�������2017��9��30�� |4���õ����Ż����ս���Ȩ�������г���������Ƽ����޹�˾����' where couponTmpId in('873621377fa1462abdc76055dae5ec89','834042c5c89843eaaa08bf55f1ac4104','20c87a9ea3d04dcb863cc7cb1e1b82b5','bd3a1acfc4964b6e85b790952f411ac0');

  
update `t_s_coupon` set maxNum=75,isUse=1 where couponTmpId='464b7dee154c4c1ebc497d6a4ea45b3f';

update `t_s_cstock` set isExist=1 where couponTempId in ('464b7dee154c4c1ebc497d6a4ea45b3f','1706e9c40e99482fbada0f127b427a03','65dabc19350348ab98c5fa619f7ce221','901d206ea7254e5cbb149e7309454483','42f0187cd0874042b27e8e827c4a9c14','873621377fa1462abdc76055dae5ec89','834042c5c89843eaaa08bf55f1ac4104','20c87a9ea3d04dcb863cc7cb1e1b82b5','bd3a1acfc4964b6e85b790952f411ac0');
