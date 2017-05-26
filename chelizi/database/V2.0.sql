-- ���������ļ�·����ַ
INSERT INTO `t_p_config` VALUES (7, 'config.file.', '/www/configfile/', NULL, 0, 0, NULL, '2016-8-26 6:06:26');

--  ���ӳ������ΰ�ť
ALTER TABLE t_s_region ADD column shield tinyint(4) DEFAULT '0' COMMENT 'APP�ͻ�������ʹ�ã�0-�����Σ�1-����' after mtime;

--  ���ݳ����б��¼�
update `t_s_region` set isdel=1 where rid=100102 or pid=100102;

--  ���ݡ���ɽ��������
update `t_s_region` set shield=1 where rid in (100103,101100) or pid in (100103,101100);

drop table if exists t_subject_video;
CREATE TABLE `t_subject_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `cityId` int(11) DEFAULT NULL COMMENT '����ID',
  `subject` tinyint(4) DEFAULT '1' COMMENT '��Ŀ����:1-��Ŀһ��2-��Ŀ����3-��Ŀ����4-��Ŀ��',
  `fileType` tinyint(4) DEFAULT '1' COMMENT '�ļ����ͣ�1-��Ƶ��2-H5�μ�',
  `examId` int(11) DEFAULT NULL COMMENT '����ID',
  `serialId` int(11) DEFAULT NULL COMMENT '��ţ��ļ�����ʹ��',
  `name` varchar(32) DEFAULT NULL COMMENT '��Ƶ����',
  `vtime` varchar(16) DEFAULT NULL COMMENT '��Ƶʱ��',
  `url` varchar(255) DEFAULT NULL COMMENT '��Ƶ��ţ·��',
  `video2Img` varchar(255) DEFAULT NULL COMMENT '��Ƶ2xͼƬ',
  `video3Img` varchar(255) DEFAULT NULL COMMENT '��Ƶ3xͼƬ',
  `isDel` tinyint(4) DEFAULT '0' COMMENT '�Ƿ�����:0-���ã�1-������',
  `creator` varchar(32) DEFAULT 'system' COMMENT '������',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `updater` varchar(32) DEFAULT NULL COMMENT '������',
  `utime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `actionRemark` varchar(1000) DEFAULT NULL COMMENT '����˵��',
  `standard` varchar(1000) DEFAULT NULL COMMENT '�۷ֱ�׼',
  `skill` varchar(1000) DEFAULT NULL COMMENT '����',
  `remark` varchar(255) DEFAULT NULL COMMENT '��ע',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='��Ƶ�б�';



drop table if exists t_u_type;
CREATE TABLE `t_titile_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `channel` tinyint(4) DEFAULT '2' COMMENT '������1-������2-ѧԱ',
  `name` varchar(64) NOT NULL COMMENT '������',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `creater` varchar(32) NOT NULL COMMENT '������',
  `utime` date DEFAULT NULL COMMENT '����ʱ��',
  `updater` varchar(32) DEFAULT NULL COMMENT '������',
  `remark` varchar(255) DEFAULT NULL COMMENT '��ע',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='�������ı�';





drop table if exists t_u_type_content;
CREATE TABLE `t_type_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `typeId` int(11) NOT NULL COMMENT '��������ID',
  `serialId` int(11) DEFAULT NULL COMMENT '��ţ�����ʹ��',
  `type` tinyint(4) DEFAULT NULL COMMENT '���ͣ�1-����2-��',
  `title` varchar(128) DEFAULT NULL COMMENT '����',
  `subject` varchar(128) DEFAULT NULL COMMENT '��Ŀ�������|�ŷָ�',
  `content` text COMMENT '���ݣ������|�ŷָ�',
  `releaseTime` date DEFAULT NULL COMMENT '����ʱ��',
  `status` tinyint(4) DEFAULT '1' COMMENT '����״̬��1-�ݸ壬2-�ѷ�����3-�ѳ���',
  `creater` varchar(32) NOT NULL COMMENT '������',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `updater` varchar(32) DEFAULT NULL COMMENT '������',
  `utime` date DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='������ϸ��';



delete  from t_subject_video;
INSERT INTO `t_subject_video` (id, cityId, subject, fileType, examId, serialId, name, vtime, url, video2Img, video3Img, isDel, creator, ctime, updater, utime, actionRemark, standard, skill,remark) VALUES 
(1, 100100, 2, 1, NULL, 1, '��', '01:20', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilihoukao.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/jinglukaoshitongdao@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/jinglukaoshitongdao@3x.png', 0, 'system', '2016-9-1 14:58:57', NULL, '2016-9-10 11:16:16', NULL, NULL, '������һ�����7�㿪�ţ��˵㿼�ԡ���Ҫ�������ȡ�ţ�ȡ�ĺ��ǰ���ʱ����Ⱥ�˳���ŵģ��ŵ�λ��������Ҳ�ܴ��֪���Լ��Ŀ���ʱ�䡣����֮ǰ��ȡ�ţ��Ǹ������Ѿ����㰲���˹̶��ĳ���ÿ�����ó���ʱ�䲻ͬ�����Բ��þ��ȣ��������������п��ܱ����ȿ�ʼ���ԡ���ϸ���㲥���Ƚе���ĺź�����ʱ��������Ա����������ϳ�������Ҳ������Ŀ�Ա�������ˣ���ʱ����������Ӻò��ÿ�����Ϊ��������ÿһ�����������ڼ�У����ʱ���ĳ��ÿ��ܶࡣ���ǵĳ������̺ô�������ײ����׿��ơ�', NULL),
(2, 100100, 2, 1, NULL, 2, '��ǰ׼������', '01:22', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilikaoqianzhunyuqibu.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/shangchezhunbei@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/shangchezhunbei@3x.png', 0, 'system', '2016-9-1 15:01:16', NULL, '2016-9-10 11:18:50', NULL, NULL, 'Χ�Ƴ���תһȦ�����������ѹ�Ƿ���������˿�Ƿ��ɶ��ȣ�|��鳵ͷ�����Ƿ���©ˮ��©�͡�©������|�������Σ�|�������Ӿ����󷴹⾵���ҷ��⾵��|ϵ�ð�ȫ����|��鵵λ�Ƿ�յ�����ɲ����', NULL),
(3, 100100, 2, 1, NULL, 3, '�������', '01:45', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilidaocheruku.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/daocheluku@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/daocheluku@3x.png', 0, 'system', '2016-9-1 15:01:32', NULL, '2016-9-10 11:03:13', '���Թ����У���������;�в���ͣ�����ӵ�·һ�˿����ߣ�����ѹ�����ߣ����복��ͣ������ǰ����������һ��ʻ�������ߺ��복��ͣ�������ǰ��ʻ�����⡣', '�����涨·��˳����ʻ����100��|û����ȫ������ڣ���100��|������ߣ���100��|��;ͣ������100��', 'һ��Ҫ��֤����������������Ҫע��������ʱ�м�̧�ù��졢ѹ�ù��ͣ�|�����̵��ַ�Ҫ��ȷ�������������Ҫ�ض��ٻ����Ƿ��Ѿ����������Ҵ���Ҫ�죻|����ľ���һ��Ҫ׼ȷ��ע������̵�ʱ����ͣ����λ�á���������������ʱ��Ҫ�ʹ��򣬼����ܳ���һȦ��', NULL),
(5, 100100, 2, 1, NULL, 4, '�෽ͣ��', '01:03', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilicefangtingche.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/cefangweitingche@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/cefangweitingche@3x.png', 0, 'system', '2016-9-1 15:01:53', NULL, '2016-9-10 11:03:55', '�����ڿ�ǰ������ͣ�Ⱥ�һ�ε�����⡣|��;����ͣ����|���ֲ������������ߡ���λ���ߡ�', '�������ֹͣ�󣬳�����ߣ���100��|��ʻ�г��ִ�ѹ�������ߣ���10��|δͣ���ڿ��ڣ���100��|��δ����ת��ƣ���10��|��;ͣ������100��', 'ע������ٶȣ��ܶ��˴���ʱ���Ż��鲻�Խ��ط��ɻ������������ƺ��ֺͽŵ�Э���Ժ���Ҫ������ϰ��|����Ҫ׼ȷ����סƽʱ��ϰ�ҵĵ㣻|���ջط����̵�ʱ������������ˣ��Ҳ೵����ߣ������ˣ���೵���ֻ���ߣ�|����ʱע���жϳ����Ƿ�ƽ�У�������λҲ��۷ֵġ�', NULL),
(6, 100100, 2, 1, NULL, 5, '������ʻ', '00:46', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xiliquxianxingshi.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/quxianxingshi@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/quxianxingshi@3x.png', 0, 'system', '2016-9-1 15:02:11', NULL, '2016-9-10 11:13:48', '��ʻ�����������һ��ǰ��ʻ�룬����һ��ʻ������ʱѹ�����Ե�ߡ�|��ʻ��ת���ٶ�ƽ�ȡ�|��;����ͣ�������ֲ��������������ߡ�|����������ʱ�����ֲ��ص�����ֻҪ���ֲ�ѹ�߾���ϸ�|ע������������̤���Э����ϣ���ֹ����Ϩ��', '��ʻ�г���ѹ�ߣ���100��|��;ͣ������100��', '�������ֳ����복����ƽ�У�������ʻ����ʱ��������', NULL),
(7, 100100, 2, 1, NULL, 6, 'ֱ��ת��', '00:34', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilizhijiaozhuanwan.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/zhijiaozhuanwan@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/zhijiaozhuanwan@3x.png', 0, 'system', '2016-9-1 15:14:54', NULL, '2016-9-10 11:14:07', '��ʻ�������涨����·��ʻ���������һ���������ֱ��ת�䡣|һ��ͨ������;����ͣ����|���ֲ��������������ߡ�', '��ʻ�г���ѹ�ߣ���100��|��;ͣ������100��', '����ͨ����|����ֱ��ʱ����������������|����ʹ��������̣����ܹ�������', NULL),
(8, 100100, 2, 1, NULL, 7, '�µ�����ͣ������', '00:46', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilipodaodingtingcheheqibu.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/dingdiantingcheheqibu@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/dingdiantingcheheqibu@3x.png', 0, 'system', '2016-9-1 15:15:00', NULL, '2016-9-10 11:03:39', '��ʻ��Ӧͨ���Ӿ��͸о���ʱ�ж��µ��Ķ�̹�����̼�·��ȵ�·�������ȡǡ���������������Ƴ���ƽ��ͣ�����𲽡�|����ת����ȷ������Ѹ�٣������ƶ���������������׼ȷЭ����', 'ǰ���ո�λ���������ڡ�ʵ�����⣬��10��|ǰ���ո�λ���������⣬��100��|�ٴ���ʱ����С��30CM����10�֣�����30CM����100��', '���Ƴ��٣�����ʱӦ�����㹻�Ķ�����|��ȷ�жϳ���λ�ã���ʱ��������|�µ���ʱӦѧ����ƺ���ϡ�ɲ��֮��Ĺ�ϵ��', NULL),
(9, 100100, 2, 1, NULL, 8, '�������', '00:57', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilikaoshiwancheng.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/kaoshiwancheng@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/kaoshiwancheng@3x.png', 0, 'system', '2016-9-1 15:15:07', NULL, '2016-9-10 11:14:16', NULL, NULL, '���Խ�����ѳ�������ʼλ�ã����������￪�Ļ���������ȥ����ҪϨ�𡣿��Խ������㲻Ҫ�뿪������Ҫ�ȴ�ǩ��ȷ����ĳɼ�����ȷ�Ϻóɼ��Ժ��Ŀ���Ĺ�����ȫ�������ˡ�', NULL),
(10, 100100, 3, 1, NULL, 1, '·������1', '08:43', 'http://o7d94lzvx.bkt.clouddn.com/bszkaochangdong.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/bszdongkaochang@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/bszdongkaochang@3x.png', 0, 'system', '2016-9-1 15:15:11', NULL, '2016-9-10 11:21:16', NULL, NULL, '1.����ָ����֤�޷�ͨ�������³���ָ�ƽ������²ɼ��󷽿������Ŷӽ��п��ԡ�|2.���Թ�������������������ںϸ������ϵͳ���Զ���ʾ�������п���ͣ��������ͣ��������ɺ�ϵͳ����ʾ������һ�ο��ԡ�|3.������Ҫ��������бؿ���Ŀ����ʻ�������3�����ϵͳ���Զ���ʾ����ͣ����ѧԱ��������ͣ�����������ڿ���·��������ѡ������·��ʯ(���·��Եʵ��)����ȷ�ϰ�ȫ����ɿ���ͣ������������ͣ����ɺ�ϵͳ�������������Խ����|4.�Ƴ�һ�ܹ۲쳵����ۺ��ܱ߻���ʱ��Ϊ��֤�״��źż���׼ȷ�ԣ��뱣�������복�������1�׷�Χ�ڡ�|5.���ݹ������Լ�ʻ�˿��Թ涨��Ҫ��ÿ��ѧԱ1��ԤԼ�����ο��Ի��ᣬ��һ�ο��Բ�����ɵ�������һ��.ѧԱ��һ�ο���ʱ��Ҫ�μ��Ƴ�һ�ܺ�ҹ��ƹ�ģ�⣬��һ�ο��Բ�����ڶ��β���ʱ��ѧԱ�ɲ������Ƴ�һ�ܺ͵ƹ�ģ�⣬׼����ɺ�ֱ�ӿ�ʼ�ڶ��ο���.��һ�ο��Բ�����ͣ�ų�������Ŀ��Կ�Ŀ������·��һ�ܺ�ʼ���Կ����������ڿ���·�ε�����λ�ã�����ѧԱ��Ҫ����16�����Ŀ�󷽿ɽ��������ϴ����Գɼ�.(ע��:�μӰ��쿼�Ե�ѧԱ��Ҫ����ҹ��ƹ�ģ�⣬�μ�ҹ�俼�Ե�ѧԱ�����ٽ��б���Ŀ������)��|6.���ж���ͬʱ����׼�����Ե������һ��ѧԱ���Խ�������һ��ѧԱ��Ҫ���ձ�׼�����³����ϳ�׼�����Ƴ�һ���Լ�ҹ��ƹ�ģ��ȿ�ʼ���ԡ�', NULL),
(11, 100100, 3, 1, NULL, 2, '·������2', '09:05', 'http://o7d94lzvx.bkt.clouddn.com/bszkaochangxi.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/bszxikaochang@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/bszxikaochang@3x.png', 0, 'system', '2016-9-9 11:03:55', NULL, '2016-9-10 11:21:28', NULL, NULL, '1.����ָ����֤�޷�ͨ�������³���ָ�ƽ������²ɼ��󷽿������Ŷӽ��п��ԡ�|2.���Թ�������������������ںϸ������ϵͳ���Զ���ʾ�������п���ͣ��������ͣ��������ɺ�ϵͳ����ʾ������һ�ο��ԡ�|3.������Ҫ��������бؿ���Ŀ����ʻ�������3�����ϵͳ���Զ���ʾ����ͣ����ѧԱ��������ͣ�����������ڿ���·��������ѡ������·��ʯ(���·��Եʵ��)����ȷ�ϰ�ȫ����ɿ���ͣ������������ͣ����ɺ�ϵͳ�������������Խ����|4.�Ƴ�һ�ܹ۲쳵����ۺ��ܱ߻���ʱ��Ϊ��֤�״��źż���׼ȷ�ԣ��뱣�������복�������1�׷�Χ�ڡ�|5.���ݹ������Լ�ʻ�˿��Թ涨��Ҫ��ÿ��ѧԱ1��ԤԼ�����ο��Ի��ᣬ��һ�ο��Բ�����ɵ�������һ��.ѧԱ��һ�ο���ʱ��Ҫ�μ��Ƴ�һ�ܺ�ҹ��ƹ�ģ�⣬��һ�ο��Բ�����ڶ��β���ʱ��ѧԱ�ɲ������Ƴ�һ�ܺ͵ƹ�ģ�⣬׼����ɺ�ֱ�ӿ�ʼ�ڶ��ο���.��һ�ο��Բ�����ͣ�ų�������Ŀ��Կ�Ŀ������·��һ�ܺ�ʼ���Կ����������ڿ���·�ε�����λ�ã�����ѧԱ��Ҫ����16�����Ŀ�󷽿ɽ��������ϴ����Գɼ�.(ע��:�μӰ��쿼�Ե�ѧԱ��Ҫ����ҹ��ƹ�ģ�⣬�μ�ҹ�俼�Ե�ѧԱ�����ٽ��б���Ŀ������)��|6.���ж���ͬʱ����׼�����Ե������һ��ѧԱ���Խ�������һ��ѧԱ��Ҫ���ձ�׼�����³����ϳ�׼�����Ƴ�һ���Լ�ҹ��ƹ�ģ��ȿ�ʼ���ԡ�', NULL);

ALTER TABLE `db_lili`.`t_p_notice`   
  ADD COLUMN `pic` VARCHAR(32) NULL   COMMENT '��Ϣ��ͼƬ' AFTER `etime`;
ALTER TABLE `db_lili`.`t_u_student`   
  ADD COLUMN `basicHour2` INT DEFAULT 0  NULL   COMMENT '��Ŀ������ѵ����ѧ��ʱ(����)' AFTER `lastLogin`,
  ADD COLUMN `testHour2` INT DEFAULT 0  NULL   COMMENT '��Ŀ��Ӧ��ѵ����ѧ��ʱ(����)' AFTER `basicHour2`,
  ADD COLUMN `simTestHour2` INT DEFAULT 0  NULL   COMMENT '��Ŀ������ģ����ѧ��ʱ(����)' AFTER `testHour2`,
  ADD COLUMN `basicHour3` INT DEFAULT 0  NULL   COMMENT '��Ŀ��Ӧ��ѵ����ѧ��ʱ(����)' AFTER `simTestHour2`,
  ADD COLUMN `testHour3` INT DEFAULT 0  NULL   COMMENT '��Ŀ������ģ����ѧ��ʱ(����)' AFTER `basicHour3`,
  ADD COLUMN `simTestHour3` INT DEFAULT 0  NULL   COMMENT '��Ŀ��Ӧ��ѵ����ѧ��ʱ(����)' AFTER `testHour3`,
  ADD COLUMN `driveHour` INT DEFAULT 0  NULL   COMMENT '�����ѧ��ʱ(����)' AFTER `simTestHour3`;
  
  update `t_s_coupon` set validityPeriod=0,expireTime='2016-12-31 23:59:59',useNote='1.���Ż�ȯ�������ڡ���ݸ����������ɳ�û�ʹ��; |2.���Ż�ȯ�����ڱ����Ѻ�Լ�Σ�ÿ������1�ţ����������Ż�ȯͬʱʹ��; |3.���Ż�ȯ�������֣�ʹ����Ч���Լ�������2016��12��31��; |4.���Ż�ȯ���ս���Ȩ�������г���������Ƽ����޹�˾���С�' where couponTmpId='68f6c9f2ca83492faa6a591cf24a3403';