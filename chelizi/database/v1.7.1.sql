-- ������������Ż݄���Ϣ
ALTER TABLE t_enroll_order
ADD  city_name varchar(50) NOT NULL COMMENT '����ģ������ƣ�Ĭ��Ϊ��������' AFTER city_id,
ADD  `coupon` bigint(20) DEFAULT NULL COMMENT 'ʹ�õ��Ż�ȯid',
ADD  `coupon_total` int(11) NOT NULL DEFAULT '0' COMMENT '���ࣺ�Ż�ȯ�ֿ۽���λ��',
ADD  `coupon_name` varchar(50) DEFAULT NULL COMMENT '���ࣺ�Ż�ȯ����',
ADD  `payWay` varchar(16) DEFAULT NULL COMMENT '֧������',
ADD	 `pay_total` int(11) DEFAULT NULL COMMENT 'ʵ��֧�����',
ADD  `extra` varchar(255) DEFAULT NULL COMMENT '����';

-- �ʼ����ϵ绰�޸�
UPDATE `t_enroll_material_address` set contacts='��ѧ���ͷ���', mobile='400-6666-666', phone='400-6666-666';

-- �޸��Ż݄�����
ALTER TABLE t_s_coupon
ADD couponPic varchar(255) DEFAULT NULL COMMENT '�����Ż�ȯ����ͼƬ�ĵ�ַ',
ADD couponUrl varchar(255) DEFAULT NULL COMMENT '�����Ż�ȯ�����а�ť��ת�ĵ�ַ��0-��ת���Ż�ȯ�б�1-��ת��ָ���Ż�ȯ���飻url-��ת��url����',
ADD extra varchar(255) DEFAULT NULL COMMENT '����';

-- -- ���ӱ����ֿ�ȯ����������ֻ����ӪҪ�������²Ž������á�
-- ����ȯԼ��
-- INSERT INTO `t_s_ccondition` VALUES (23, 5, '11', '', 'ָ������������'),(24, 0, '2016-04-01 00:00:01', '2016-05-31 23:59:59', 'ָ��ʱ����Ի��'),(25, 3, '1', '000967370fe74b8690d0615e8d7bdd8f', '����һ��');
-- ����ȯ
-- INSERT INTO `t_s_coupon` VALUES ('000967370fe74b8690d0615e8d7bdd8f', '�����ۿ�ȯ', '', 1, 2, 720, 0, 90, 1, '', '����������/��ݸ��������ʱʹ��|�������ڱ�����֧��|ÿ�˽���ʹ��1��|�˷�ʱ�������ۿ۽��|��Ч�ڣ���2016��5��1��00:00����2016��5��31��23:59�ڼ���ȡ��30������Ч', 'www.baidu.com', 1, '(%s and %s)|24,25', '(%s)|23', 'yangpeng', '2016-4-20 11:46:33', '�װ����û������ġ�����ѧ���ۿ�ȯ�����ʹ�����˻��������¼��ѧ��APP�鿴��', 0, 50000, 'coupon_dialog_enroll_cash_200.png', '1', NULL);
-- ������
-- INSERT INTO `t_s_cstock` VALUES (4, 'rmq_student_register_value', 10000, 3, '2016-4-20 11:46:33', 'yangpeng', 1, '000967370fe74b8690d0615e8d7bdd8f');

-- ѧԱ��֤������״̬λ˵��
ALTER TABLE `t_u_student_auth` MODIFY COLUMN  `state` int(2) DEFAULT NULL COMMENT '0��δ��֤��1����֤�У�2������֤��3����֤ʧ�� 4---�ѹ��� 5---�ѵ���';
ALTER TABLE `t_u_student_auth` 
ADD `drType` tinyint(4) DEFAULT NULL COMMENT '��ʻ���ͣ�1����C1,2����C2,3����������' AFTER file_no,
ADD `photo` varchar(255) DEFAULT NULL COMMENT 'ͼƬ��ַ' AFTER drType,
ADD `photo2` varchar(255) DEFAULT NULL COMMENT 'ͼƬ��ַ' AFTER photo,
ADD `extra` varchar(255) DEFAULT NULL COMMENT '����';

-- �������ñ�����Ƿ�ɾ����״̬λ
ALTER TABLE `t_p_config`
ADD `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '״̬��0����δɾ����1������ɾ��' AFTER type;




