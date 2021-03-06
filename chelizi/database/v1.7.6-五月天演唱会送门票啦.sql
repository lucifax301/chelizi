﻿-- 用于保存优惠券信息
DROP TABLE IF EXISTS t_u_gift;
CREATE TABLE `t_u_gift` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `couponId` varchar(50) DEFAULT NULL COMMENT '礼券id、兑换码',
  `studentId` bigint(20) DEFAULT NULL COMMENT '学员id',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `state` int(4) DEFAULT 0 COMMENT '状态：0-未发放；1-已发放；2-已使用；3-已过期',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 150张门票兑换码
INSERT INTO `t_u_gift` VALUES 
(1, 'ea166e82', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(2, 'ea2065a2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(3, 'ea24f752', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(4, 'ea2de502', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(5, 'ea36e4a2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(6, 'ea3ef7e2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(7, 'ea497b02', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(8, 'ea5468b2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(9, 'ea5fb352', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(10, 'ea6ae992', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(11, 'ea761512', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(12, 'ea809322', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(13, 'ea8b88c2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(14, 'ea966d42', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(15, 'eaa14b92', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(16, 'eaabe0d2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(17, 'eab70652', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(18, 'eac19eb2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(19, 'eacc8172', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(20, 'ead42352', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(21, 'ead8a1b2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(22, 'eadd1b82', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(23, 'eae1c082', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(24, 'eae683a2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(25, 'eaeae702', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(26, 'eaef6da2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(27, 'eaf3c422', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(28, 'eaf81c82', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(29, 'eafccc92', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(30, 'eb014212', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(31, 'eb05f4f2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(32, 'eb0abc02', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(33, 'eb0f9e82', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(34, 'eb1448d2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(35, 'eb195852', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(36, 'eb243402', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(37, 'eb2ee8a2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(38, 'eb396b52', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(39, 'eb4464e2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(40, 'eb4f7522', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(41, 'eb59f7f2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(42, 'eb64c962', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(43, 'eb6fee32', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(44, 'eb7a8432', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(45, 'eb857252', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(46, 'eb901ba2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(47, 'eb9ad442', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(48, 'eba56ab2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(49, 'ebb06602', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(50, 'ebbb3e02', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(51, 'ebc009b2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(52, 'ebc47292', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(53, 'ebc903f2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(54, 'ebcdc3f2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(55, 'ebd27a22', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(56, 'ebd73dd2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(57, 'ebdbe832', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(58, 'ebe06822', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(59, 'ebe4eae2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(60, 'ebe9fe42', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(61, 'ebeea9a2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(62, 'ebf3eb62', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(63, 'ebf868d2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(64, 'ebfcf812', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(65, 'ec01bb12', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(66, 'ec099162', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(67, 'ec145c52', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(68, 'ec1f3122', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(69, 'ec2a0f62', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(70, 'ec34d022', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(71, 'ec3fb732', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(72, 'ec4ae9b2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(73, 'ec562972', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(74, 'ec60fc52', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(75, 'ec6c7692', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(76, 'ec7785e2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(77, 'ec830272', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(78, 'ec8e0ad2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(79, 'ec98f442', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(80, 'eca39b12', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(81, 'ecae4622', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(82, 'ecb2cff2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(83, 'ecb77872', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(84, 'ecbbe5f2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(85, 'ecc08d02', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(86, 'ecc59712', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(87, 'ecca3ac2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(88, 'eccee362', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(89, 'ecd34ec2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(90, 'ecd82dc2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(91, 'ecdcd5c2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(92, 'ece158d2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(93, 'ece5eef2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(94, 'ecea86e2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(95, 'eceedcd2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(96, 'ecf3b722', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(97, 'ecfb47c2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(98, 'ed059e32', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(99, 'ed0ffcb2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(100, 'ed1af802', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(101, 'ed264d92', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(102, 'ed3100f2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(103, 'ed3c7172', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(104, 'ed478fe2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(105, 'ed52c7c2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(106, 'ed5d6572', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(107, 'ed681232', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(108, 'ed734bc2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(109, 'ed7e14d2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(110, 'ed894772', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(111, 'ed946472', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(112, 'ed9f7952', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(113, 'eda41192', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(114, 'eda908a2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(115, 'edadadd2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(116, 'edb2b5c2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(117, 'edb76b92', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(118, 'edbc4e82', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(119, 'edc10bb2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(120, 'edc5ff52', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(121, 'edcaa3d2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(122, 'edcf7d32', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(123, 'edd41d62', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(124, 'edd8e7a2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(125, 'edddc972', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(126, 'ede28822', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(127, 'ede7eea2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(128, 'edf276f2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(129, 'edfce182', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(130, 'ee07dba2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(131, 'ee12a212', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(132, 'ee1d9ac2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(133, 'ee28e8d2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(134, 'ee3441c2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(135, 'ee3efe92', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(136, 'ee4a6182', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(137, 'ee552312', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(138, 'ee604592', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(139, 'ee6b6fb2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(140, 'ee763af2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(141, 'ee80dfb2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(142, 'ee8b9742', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(143, 'ee938a92', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(144, 'ee985f62', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(145, 'ee9cff82', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(146, 'eea18de2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(147, 'eea65df2', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(148, 'eeaad962', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(149, 'eeaf5a22', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL),
(150, 'eeb44242', NULL, NULL, NULL, NULL, 0, '2016-6-8 13:16:40', NULL, NULL);











