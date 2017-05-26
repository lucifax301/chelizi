-- 报名订单添加优惠劵信息
ALTER TABLE t_enroll_order
ADD  city_name varchar(50) NOT NULL COMMENT '报名模版的名称，默认为城市名称' AFTER city_id,
ADD  `coupon` bigint(20) DEFAULT NULL COMMENT '使用的优惠券id',
ADD  `coupon_total` int(11) NOT NULL DEFAULT '0' COMMENT '冗余：优惠券抵扣金额，单位分',
ADD  `coupon_name` varchar(50) DEFAULT NULL COMMENT '冗余：优惠券名称',
ADD  `payWay` varchar(16) DEFAULT NULL COMMENT '支付渠道',
ADD	 `pay_total` int(11) DEFAULT NULL COMMENT '实际支付金额',
ADD  `extra` varchar(255) DEFAULT NULL COMMENT '冗余';

-- 邮寄资料电话修改
UPDATE `t_enroll_material_address` set contacts='喱喱学车客服部', mobile='400-6666-666', phone='400-6666-666';

-- 修改优惠劵定义
ALTER TABLE t_s_coupon
ADD couponPic varchar(255) DEFAULT NULL COMMENT '推送优惠券弹窗图片的地址',
ADD couponUrl varchar(255) DEFAULT NULL COMMENT '推送优惠券弹窗中按钮跳转的地址：0-跳转到优惠券列表；1-跳转到指定优惠券详情；url-跳转到url链接',
ADD extra varchar(255) DEFAULT NULL COMMENT '冗余';

-- -- 增加报名抵扣券，现网环境只在运营要求的情况下才进行配置。
-- 定义券约束
-- INSERT INTO `t_s_ccondition` VALUES (23, 5, '11', '', '指定报名费能用'),(24, 0, '2016-04-01 00:00:01', '2016-05-31 23:59:59', '指定时间可以获得'),(25, 3, '1', '000967370fe74b8690d0615e8d7bdd8f', '限领一张');
-- 定义券
-- INSERT INTO `t_s_coupon` VALUES ('000967370fe74b8690d0615e8d7bdd8f', '报名折扣券', '', 1, 2, 720, 0, 90, 1, '', '仅限于深圳/东莞地区报名时使用|仅限用于报名费支付|每人仅限使用1次|退费时不返还折扣金额|有效期：自2016年5月1日00:00至至2016年5月31日23:59期间领取后30天内有效', 'www.baidu.com', 1, '(%s and %s)|24,25', '(%s)|23', 'yangpeng', '2016-4-20 11:46:33', '亲爱的用户，您的【报名学车折扣券】已送达个人账户，敬请登录喱喱学车APP查看！', 0, 50000, 'coupon_dialog_enroll_cash_200.png', '1', NULL);
-- 定义库存
-- INSERT INTO `t_s_cstock` VALUES (4, 'rmq_student_register_value', 10000, 3, '2016-4-20 11:46:33', 'yangpeng', 1, '000967370fe74b8690d0615e8d7bdd8f');

-- 学员认证表，调整状态位说明
ALTER TABLE `t_u_student_auth` MODIFY COLUMN  `state` int(2) DEFAULT NULL COMMENT '0：未认证，1：认证中，2：已认证，3：认证失败 4---已过期 5---已吊销';
ALTER TABLE `t_u_student_auth` 
ADD `drType` tinyint(4) DEFAULT NULL COMMENT '驾驶类型（1代表C1,2代表C2,3代表其它）' AFTER file_no,
ADD `photo` varchar(255) DEFAULT NULL COMMENT '图片地址' AFTER drType,
ADD `photo2` varchar(255) DEFAULT NULL COMMENT '图片地址' AFTER photo,
ADD `extra` varchar(255) DEFAULT NULL COMMENT '冗余';

-- 基本配置表，添加是否删除的状态位
ALTER TABLE `t_p_config`
ADD `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0代表未删除，1代表已删除' AFTER type;




