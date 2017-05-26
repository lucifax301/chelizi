
--  �����������ֶ�
ALTER TABLE t_u_coach ADD column `schoolAge` int(11) DEFAULT NULL COMMENT '����' after extra;
ALTER TABLE t_u_coach ADD column `area` varchar(32) DEFAULT NULL COMMENT '����Ƭ��' after schoolAge;
ALTER TABLE t_u_coach ADD column `coachTag` varchar(128) DEFAULT NULL COMMENT '������ɫ' after area;
ALTER TABLE t_u_coach ADD column `profile` varchar(255) DEFAULT NULL COMMENT '���˼��' after coachTag;
ALTER TABLE t_u_coach ADD column `qrcode` varchar(255) DEFAULT NULL COMMENT '��ά��' after profile;

--  ��Ϣ����������Ϣ����
ALTER TABLE t_p_notice ADD column `msgType` int(11) DEFAULT '0' COMMENT '��Ϣ����ID����JpushConstant�ඨ��һ��' after orderId;


drop table if exists t_wechat_template;

/*==============================================================*/
/* Table: t_wechat_template                                 */
/*==============================================================*/
CREATE TABLE `t_wechat_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
 	`template_name` varchar(64) NOT NULL COMMENT 'ģ�����',
  `template_id` varchar(64) NOT NULL COMMENT 'ģ��ID', 
  `url` varchar(255) DEFAULT NULL COMMENT ' ģ����Ϣ��������',
  `template_param` varchar(255) DEFAULT NULL COMMENT 'ģ������',
  `ctime` datetime NOT NULL COMMENT '����ʱ��',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '�����޸�ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='΢��֪ͨģ���';


drop table if exists t_wechat_template_log;

/*==============================================================*/
/* Table: t_wechat_template_log                                 */
/*==============================================================*/
CREATE TABLE `t_wechat_template_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `student_id` bigint(20) NOT NULL COMMENT 'ѧԱID',
  `student_name` varchar(32) DEFAULT NULL COMMENT 'ѧԱ����',
  `student_phone` varchar(16) DEFAULT NULL COMMENT 'ѧԱ�ֻ���',
  `status` varchar(32) DEFAULT NULL COMMENT '����״̬Ϊ���ɹ�-success; �û�����-failed:user block;����ԭ��ʧ��-failed: system failed',
  `template_id` varchar(64) DEFAULT NULL COMMENT '΢��ģ��ID',
  `to_user` varchar(64) NOT NULL COMMENT '���ں�΢�ź�',
  `top_color` varchar(16) DEFAULT NULL COMMENT '��Ϣ��������ɫ',
  `url` varchar(255) DEFAULT NULL COMMENT ' ģ����Ϣ��������',
  `template_param` varchar(1000) DEFAULT NULL COMMENT '�����б�',
  `errcode` int(11) DEFAULT NULL COMMENT '�����룺0��ʾ�ɹ�',
  `errmsg` varchar(64) DEFAULT NULL COMMENT '������Ϣ',  
  `msg_type` varchar(32) DEFAULT NULL COMMENT '��Ϣ�������¼�',
  `msg_id` int(11) DEFAULT NULL COMMENT '��Ϣid',
  `ctime` datetime NOT NULL COMMENT '��������ʱ��',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '�����޸�ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='΢��֪ͨģ�巢����־��';



drop table if exists t_wechat_enroll_class;

/*==============================================================*/
/* Table: t_wechat_enroll_class                                 */
/*==============================================================*/
create table t_wechat_enroll_class
(
   class_id             int(11) not null AUTO_INCREMENT comment '����ID',
   coach_id             bigint(20) not null comment '����ID',
   class_name           varchar(64) not null comment '��������',
   drtype               tinyint(4) not null default 1 comment '�������ͣ�1����C1,2����C2��3����C1��C2',
   price                int(11) not null comment '�۸񣺵�λ��',
   pre_price            int(11) default NULL comment '�Żݼ�',
   drtype2              tinyint(4) default NULL comment '�������ͣ�1����C1,2����C2��3����C1��C2',
   price2               int(11) default NULL comment 'ԭ��',
   pre_price2           int(11) default NULL comment '�Żݼ�',
   address              varchar(64) default NULL comment '�ŵ��ַ',
   class_tag            varchar(128) default NULL comment '������ɫ����ǩ',
   class_remark         varchar(128) default NULL comment '��ע',
   order_tag            varchar(255) default NULL comment '������ɫ',
   school_id            int(11) default NULL comment '��УID',
   city_id              int(11) default NULL comment '����ID',
   city_name            varchar(50) default NULL comment '��������',
   is_del               tinyint(4) default 0 comment '״̬��0����δɾ����1������ɾ��',
   ctime                datetime default NULL comment '����ʱ��',
   mtime                timestamp default CURRENT_TIMESTAMP,
   primary key (class_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='�������ͱ�';



drop table if exists t_wechat_enroll_student;

/*==============================================================*/
/* Table: t_wechat_enroll_student                               */
/*==============================================================*/
create table t_wechat_enroll_student
(
  `order_id` varchar(32) NOT NULL COMMENT '������',
  `class_id` int(11) NOT NULL COMMENT '������������ID',
  `class_name` varchar(64) DEFAULT NULL COMMENT '��������',
  `class_remark` varchar(255) DEFAULT NULL COMMENT '������ɫ��ע',
  `drtype` tinyint(4) NOT NULL DEFAULT '1' COMMENT '�������ͣ�1����C1,2����C2',
  `coach_id` bigint(20) NOT NULL COMMENT '����ID',
  `coach_name` varchar(32) DEFAULT NULL COMMENT '��������',
  `student_id` bigint(20) NOT NULL COMMENT 'ѧԱID',
  `student_name` varchar(32) DEFAULT NULL COMMENT 'ѧԱ����',
  `student_phone` varchar(16) DEFAULT NULL COMMENT 'ѧԱ�ֻ���',
  `price` int(11) NOT NULL DEFAULT '0' COMMENT 'ԭ���ͼ۸񣬵�λ��',
  `pay_price` int(11) DEFAULT NULL COMMENT 'ʵ��֧�����',
  `pay_time` datetime DEFAULT NULL COMMENT '֧��ʱ��',
  `pay_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '֧��״̬��0δ��ʼ��1���ύ��100֧���ɹ���101֧��ʧ��',
  `pay_way` varchar(16) DEFAULT 'weixin' COMMENT '֧������',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '�ʽ�״̬:0-ƽ̨��ʱ���ܣ�1-ƽ̨��ת�˸�����',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '״̬��0����δɾ����1������ɾ��',
  `is_new` tinyint(4) NOT NULL DEFAULT '0' COMMENT '�Ƿ���ѧԱ��0-�ǣ�1-��',
  `city_id` int(11) DEFAULT NULL COMMENT '��������id',
  `city_name` varchar(50) DEFAULT NULL COMMENT '��������',
  `remark` varchar(255) DEFAULT NULL COMMENT '��ע',
  `ctime` datetime DEFAULT NULL COMMENT '��������ʱ��',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '�����޸�ʱ��',
   primary key (order_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='����ѧԱ��';


drop table if exists t_wechat_mycoaches;

/*==============================================================*/
/* Table: t_wechat_mycoaches                                      */
/*==============================================================*/

CREATE TABLE `t_wechat_mycoaches` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `studentId` bigint(20) NOT NULL COMMENT 'ѧԱid',
  `name` varchar(32) DEFAULT NULL COMMENT '����ͨѶ¼ѧԱ����',
  `phone` varchar(16) DEFAULT NULL COMMENT '����ͨѶ¼�ֻ���',
  `head_icon` varchar(256) DEFAULT NULL COMMENT 'ͷ��',
  `state` tinyint(4) DEFAULT '0' COMMENT 'ѧԱ���ȣ�0-��ѯ��1-��һ��2-�ƶ���3-������4-���ģ�5-��ҵ',
  `coachId` bigint(20) NOT NULL COMMENT '����id', 
  `wxstatus` tinyint(4) NOT NULL DEFAULT '0' COMMENT '����״̬��0-΢��δ��1-΢���Ѱ󶨣�2-΢�Ű��ֻ���3-΢���ѽ��',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '�����Ƿ���ܣ�0-������1-�ѽ���',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ��״̬��0����δɾ����1������ɾ��',
  `channel` tinyint(4) DEFAULT '1' COMMENT '��Դ��1�����ֶ����롢2ѧԱ��ע΢�š�3ѧԱ��д��Ϣ',
  `istop` tinyint(4) DEFAULT '0' COMMENT '�Ƿ��ö���0-��1-��',
  `is_new` tinyint(4) NOT NULL DEFAULT '1' COMMENT '�Ƿ���ѧԱ��0-��1-��',
  `school_id` int(11) default NULL comment '��УID',
  `drtype` tinyint(4) not null default 0 comment '�������ͣ�1����C1,2����C2',
  `coach_remark` varchar(255) default NULL comment '������עѧԱ',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `mtime` timestamp default CURRENT_TIMESTAMP comment '�����޸�ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='΢�Ű󶨹�ϵ��';




drop table if exists t_wechat_student;

/*==============================================================*/
/* Table: t_wechat_student                                      */
/*==============================================================*/
create table t_wechat_student
(  
  `student_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ѧԱID',
  `name` varchar(32) DEFAULT NULL COMMENT '΢���ǳ�',
  `sex` tinyint(4) DEFAULT '1' COMMENT '�Ա�(0-δ֪��1-�У�2-Ů)',
  `phone` varchar(16) DEFAULT NULL COMMENT '�ֻ���',
  `head_icon` varchar(256) DEFAULT NULL COMMENT 'ͷ��',
  `wechat_state` tinyint(4) DEFAULT '0' COMMENT '��ע���ں�״̬��0-δ��ע��1-�ѹ�ע��2-��ȡ��',
  `open_id` varchar(128) DEFAULT NULL COMMENT '΢��openID',
  `union_id` varchar(128) DEFAULT NULL COMMENT '΢��unionID',
  `subscribe` varchar(32) DEFAULT NULL COMMENT '�û��Ƿ��ĸù��ںű�ʶ��ֵΪ0ʱ��������û�û�й�ע�ù��ںţ���ȡ����������Ϣ',
  `groupid` varchar(16) DEFAULT NULL COMMENT '�û����ڵķ���ID',
  `tagid_list` varchar(16) DEFAULT NULL COMMENT '�û������ϵı�ǩID�б�',
  `id_number` varchar(20) DEFAULT NULL COMMENT '���֤����',
  `school_id` int(11) DEFAULT NULL COMMENT '��УID',
  `drtype` tinyint(4) NOT NULL DEFAULT '1' COMMENT '�������ͣ�1����C1,2����C2',
  `isdel` tinyint(4) DEFAULT '0' COMMENT '״̬��0����δɾ����1������ɾ��',
  `city_id` int(11) DEFAULT NULL COMMENT '��������id',
  `city_name` varchar(50) DEFAULT NULL COMMENT '��������',
  `ctime` datetime DEFAULT NULL COMMENT '����ʱ��',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '�޸�ʱ��',
   primary key (student_id),
   UNIQUE KEY `openId` (`open_id`),
   UNIQUE KEY `unionId` (`union_id`),
   UNIQUE KEY `phoneNum` (`phone`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='��������ѧԱ��';


drop table if exists t_wechat_coach_class;

/*==============================================================*/
/* Table: t_wechat_coach_class                                      */
/*==============================================================*/
CREATE TABLE `t_wechat_coach_class` (
  `ccid` int(11) NOT NULL AUTO_INCREMENT COMMENT '�Ű�����',
  `ctype` tinyint(4) NOT NULL DEFAULT '3' COMMENT '������ͣ�1��Լ��3ԤԼ',
  `coach_id` bigint(20) NOT NULL COMMENT '����id',
  `cstart` datetime NOT NULL COMMENT '�γ̽�����ʼʱ��,ע���ʱ���������ʱ��',
  `cend` datetime NOT NULL COMMENT '�γ̽���ʱ��,ע���ʱ���������ʱ��',
  `rstart` datetime DEFAULT NULL COMMENT 'ԤԼ�γ�ʱ�俪ʼʱ�� ����������',
  `rend` datetime DEFAULT NULL COMMENT '�γ�ʵ�ʽ���ʱ��,����������ʱ��',
  `time_num` tinyint(4) DEFAULT NULL COMMENT 'ʱ�������Ϊ5Сʱ',
  `order_id` varchar(34) DEFAULT NULL COMMENT '�ÿγ̵Ķ�����,ԤԼ�γ̿ɲ����붩����',
  `max_num` tinyint(4) DEFAULT NULL COMMENT 'ԤԼ���͵�����ԤԼ����',
  `book_num` tinyint(4) NOT NULL DEFAULT '0' COMMENT '��ǰ��ԤԼ����',
  `car_id` int(11) DEFAULT NULL COMMENT 'ԤԼ����id',
  `car_name` varchar(50) DEFAULT NULL COMMENT '���ࣺԤԼ��������',  
  `car_no` varchar(32) DEFAULT NULL COMMENT '���ࣺ���ƺ�',
  `course_id` varchar(50) DEFAULT NULL COMMENT 'ԤԼ��Ŀid',
  `course_name` varchar(254) DEFAULT NULL COMMENT 'ԤԼ��Ŀid:0-�ۺ�ѵ����1-ѧ���γ̣�2-��Ŀ��ѵ����3-��Ŀ��ѵ��',
  `place_id` int(11) DEFAULT NULL COMMENT 'ԤԼѵ����id',
  `place_name` varchar(254) DEFAULT NULL COMMENT '���ࣺѵ��������',
  `dltype` tinyint(4) DEFAULT NULL COMMENT '��������',
  `tid` int(11) DEFAULT NULL COMMENT '�Ű�ʱ������',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ��״̬��0����δɾ����1������ɾ��',
  `lge` double DEFAULT NULL COMMENT 'ѵ��������',
  `lae` double DEFAULT NULL COMMENT 'ѵ����γ��',
  `rid` int(11) DEFAULT NULL COMMENT '�Ű�ȡ��ԭ��id,û�пɲ���д',
  `reason` varchar(254) DEFAULT NULL COMMENT '�Ű�ȡ��ԭ��',
  PRIMARY KEY (`ccid`)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8 COMMENT='����΢���Ű��';


drop table if exists t_wechat_plant_class;

/*==============================================================*/
/* Table: t_wechat_plant_class                                      */
/*==============================================================*/
CREATE TABLE `t_wechat_plant_class` (
  `order_id` varchar(34) NOT NULL COMMENT '����id(ȫ��Ψһ����UID)',
  `ccid` int(11) NOT NULL COMMENT '�Ű�����',
  `coach_id` bigint(20) DEFAULT NULL COMMENT '���ࣺ����id',
  `student_id` bigint(20) NOT NULL COMMENT '���ࣺѧԱid',
  `gtime` datetime DEFAULT NULL COMMENT '���ࣺ�µ�ʱ��',
  `stu_name` varchar(32) DEFAULT NULL COMMENT '���ࣺѧ������',
  `stu_img` varchar(254) DEFAULT NULL COMMENT '���ࣺѧ��ͷ��',
  `stu_mobile` varchar(20) DEFAULT NULL COMMENT '���ࣺѧ���ֻ�����',
  `state` tinyint(4) default 0 comment '״̬��0����������1-�ѽ��ܣ�2-�ܾ���3-ȡ��,4-����ɣ�5-ȱ��',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ��״̬��0����δɾ����1������ɾ��',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='����΢���Ű�ѧԱԤԼ��';



drop table if exists t_wechat_order;

/*==============================================================*/
/* Table: t_wechat_order                                      */
/*==============================================================*/

CREATE TABLE `t_wechat_order` (
  `order_id` varchar(34) NOT NULL COMMENT '����id(ȫ��Ψһ����UID)',
  `coach_id` bigint(20) DEFAULT NULL COMMENT '����id',
  `coach_name` varchar(32) DEFAULT NULL COMMENT '���ࣺ��������',
  `coach_img` varchar(254) DEFAULT NULL COMMENT '���ࣺ����ͷ��',
  `coach_mobile` varchar(20) DEFAULT NULL COMMENT '�����绰',
  `city_id` int(11) DEFAULT NULL COMMENT '�����ĳ���id',    
  `school_id` int(11) DEFAULT NULL COMMENT '����������У������ʱ�����Խ���������У��ȷ���������չ�����', 
  `course_id` varchar(50) NOT NULL COMMENT '�γ�id',
  `course_name` varchar(254) DEFAULT NULL COMMENT '���ࣺ��Ŀ����',
  `learn_addr` varchar(254) DEFAULT NULL COMMENT '�Ͽεص�',
  `student_id` bigint(20) NOT NULL COMMENT 'ѧԱid',
  `stu_name` varchar(32) DEFAULT NULL COMMENT '���ࣺѧ������',
  `stu_img` varchar(254) DEFAULT NULL COMMENT '���ࣺѧ��ͷ��',
  `stu_mobile` varchar(20) DEFAULT NULL COMMENT 'ѧ���绰',  
  `dltype` tinyint(4) NOT NULL DEFAULT '1' COMMENT '�������ͣ�1����C1,2����C2',
  `lge` double DEFAULT NULL COMMENT 'ѧԱ���͵㾭��',
  `lae` double DEFAULT NULL COMMENT 'ѧԱ���͵�γ��',
  `stu_addr` varchar(254) DEFAULT NULL COMMENT 'ѧԱ���͵ص�',
  `pstart` datetime DEFAULT NULL COMMENT '�ƻ��Ͽ�ʱ��',
  `pend` datetime DEFAULT NULL COMMENT '�ƻ�����ʱ��',
  `clz_num` tinyint(4) NOT NULL DEFAULT '1' COMMENT '�ö���Լ�ν���',
  `order_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '����״̬��0��ȷ��,1-�ѽ��ܣ�2-ѧԱ�ܾ���3-����ȡ��,4-����ɣ�5-ȱ��',
  `rstart` datetime DEFAULT NULL COMMENT 'ʵ���Ͽο�ʼʱ��',
  `rend` datetime DEFAULT NULL COMMENT 'ʵ���Ͽν���ʱ��',
  `cstart` datetime DEFAULT NULL COMMENT '��������ʱ��',
  `cend` datetime DEFAULT NULL COMMENT '�����س�ʱ��',
  `need_trans` tinyint(4) NOT NULL DEFAULT '1' COMMENT '�Ƿ���Ҫ���ͣ�0������Ҫ����,1������Ҫ����',
  `gtime` datetime DEFAULT NULL COMMENT '�µ�ʱ��',
  `atime` datetime DEFAULT NULL COMMENT '�ӵ�ʱ��',
  `otype` tinyint(4) NOT NULL DEFAULT '3' COMMENT '�������ͣ�1��Լ����,2���ζ�����3ԤԼ����',
  `coorder` varchar(34) DEFAULT NULL COMMENT '���ε�ԭ��',
  `ccid` int(11) DEFAULT NULL COMMENT '���ࣺԤԼ�Ű�id',  
  `car_id` int(11) DEFAULT NULL COMMENT '������',
  `car_name` varchar(50) DEFAULT NULL COMMENT '���ࣺ��������',
  `car_img` varchar(254) DEFAULT NULL COMMENT '����:����ͼ��',
  `car_no` varchar(32) DEFAULT NULL COMMENT '���ࣺ���ƺ�',   
  `place_id` int(11) DEFAULT NULL COMMENT 'ѵ����ID', 
  `place_name` varchar(254) DEFAULT NULL COMMENT 'ѵ��������', 
  `place_lge` double DEFAULT NULL COMMENT '�Ͽεص�ľ���',
  `place_lae` double DEFAULT NULL COMMENT '�Ͽεص��γ��',  
  `operate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='����ԤԼ��';




alter table t_comment_tag MODIFY COLUMN `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '��ǩ���ͣ�1-������ǩ��2-ѧԱ��ǩ,3-MANGER,4-������ɫ,5-������ɫ';



INSERT INTO `t_comment_tag` (tag, course_id, type, good_bad, isdel, cuid, muid, ctime, mtime, star1, star2, star3, star4, star5, extra) VALUES 
('������', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('������', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('Ƣ����', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('������', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('һ��һ��', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('�������', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('ҹ����ѵ', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('���ڸ���', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('�Է�AA', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('����ĩ��ѵ', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('һ��һ��ѧ', 1, 4, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('����ȫ��', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('��챨��', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('����ѧʱ', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('������ѧ��', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('��ĩѧ��', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('����ѧ��', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('һ��һ��', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('����һ��', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL),
('����һ��', 1, 5, NULL, 0, NULL, NULL, NULL, '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL);


INSERT INTO `t_wechat_template` VALUES (2, '��ѧԱ����֪ͨ', 'EikvzkXZC6MtlDw5w7lOVg8RDEO-LE2eOeFImaiosBE', 'http://uatweixinjl.lilixc.com/course/show?orderId=', '{{first.DATA}} �������ͣ�{{keyword1.DATA}} ֧����{{keyword2.DATA}} ѧԱ������{{keyword3.DATA}} ѧԱ�绰��{{keyword4.DATA}} ������ţ�{{keyword5.DATA}} {{remark.DATA}}', '2016-11-21 09:34:06', '2016-11-21 09:34:07');
INSERT INTO `t_wechat_template` VALUES (3, '�γ�����֪ͨ', 'EuCEwMzzmO6z_vVNSFiKvwwVngkmukk6YYcQtAuCAbM', 'http://uatweixinjl.lilixc.com/course/show?orderId=', '{{first.DATA}} ����������{{keyword1.DATA}} ѧԱ������{{keyword2.DATA}} ��ʼʱ�䣺{{keyword3.DATA}} �γ�ʱ����{{keyword4.DATA}} {{remark.DATA}}', '2016-11-21 09:35:58', '2016-11-21 09:35:59');
