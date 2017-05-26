
ALTER TABLE `db_lili`.`t_s_coupon`
  ADD COLUMN `maxNum` int(11) DEFAULT '0' COMMENT '最大使用多少张',
  ADD COLUMN `isUse` tinyint(4) DEFAULT '0' COMMENT '是否可以跟普惠一起用：0-否，1-是';

update `t_s_coupon` set extra='100',isExist=1 where couponTmpId in ('464b7dee154c4c1ebc497d6a4ea45b3f','1706e9c40e99482fbada0f127b427a03','65dabc19350348ab98c5fa619f7ce221','901d206ea7254e5cbb149e7309454483','42f0187cd0874042b27e8e827c4a9c14','873621377fa1462abdc76055dae5ec89','834042c5c89843eaaa08bf55f1ac4104','20c87a9ea3d04dcb863cc7cb1e1b82b5','bd3a1acfc4964b6e85b790952f411ac0');


update `t_s_coupon` set expireTime='2016-12-31 23:59:59',validityPeriod=0,useNote='1、该抵用券仅限深圳新报名用户使用 |2、该抵用券仅抵扣报名费，每次限用1张，不与其他报名优惠同时使用 |3、该抵用券不提现，使用有效期自即日起至2016年12月31日 |4、该抵用优惠最终解释权归深圳市车厘子网络科技有限公司所有' where couponTmpId='464b7dee154c4c1ebc497d6a4ea45b3f';


update `t_s_coupon` set expireTime='2017-09-30 23:59:59',validityPeriod=0 where couponTmpId in ('1706e9c40e99482fbada0f127b427a03','65dabc19350348ab98c5fa619f7ce221','901d206ea7254e5cbb149e7309454483','42f0187cd0874042b27e8e827c4a9c14');
  

update `t_s_coupon` set expireTime='2017-09-30 23:59:59',validityPeriod=0,useNote='1、该抵用券仅限深圳校园用户使用 |2、该抵用券仅抵扣陪驾费，每次限用1张，不与其他优惠同时使用 |3、该抵用券不提现，不可转赠使用有效期自即日起至2017年9月30日 |4、该抵用优惠最终解释权归深圳市车厘子网络科技有限公司所有' where couponTmpId in('873621377fa1462abdc76055dae5ec89','834042c5c89843eaaa08bf55f1ac4104','20c87a9ea3d04dcb863cc7cb1e1b82b5','bd3a1acfc4964b6e85b790952f411ac0');

  
update `t_s_coupon` set maxNum=75,isUse=1 where couponTmpId='464b7dee154c4c1ebc497d6a4ea45b3f';

update `t_s_cstock` set isExist=1 where couponTempId in ('464b7dee154c4c1ebc497d6a4ea45b3f','1706e9c40e99482fbada0f127b427a03','65dabc19350348ab98c5fa619f7ce221','901d206ea7254e5cbb149e7309454483','42f0187cd0874042b27e8e827c4a9c14','873621377fa1462abdc76055dae5ec89','834042c5c89843eaaa08bf55f1ac4104','20c87a9ea3d04dcb863cc7cb1e1b82b5','bd3a1acfc4964b6e85b790952f411ac0');
