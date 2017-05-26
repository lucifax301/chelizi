-- 增加配置文件路径地址
INSERT INTO `t_p_config` VALUES (7, 'config.file.', '/www/configfile/', NULL, 0, 0, NULL, '2016-8-26 6:06:26');

--  增加城市屏蔽按钮
ALTER TABLE t_s_region ADD column shield tinyint(4) DEFAULT '0' COMMENT 'APP客户端屏蔽使用：0-不屏蔽，1-屏蔽' after mtime;

--  广州城市列表下架
update `t_s_region` set isdel=1 where rid=100102 or pid=100102;

--  杭州、佛山城市屏蔽
update `t_s_region` set shield=1 where rid in (100103,101100) or pid in (100103,101100);

drop table if exists t_subject_video;
CREATE TABLE `t_subject_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cityId` int(11) DEFAULT NULL COMMENT '城市ID',
  `subject` tinyint(4) DEFAULT '1' COMMENT '科目类型:1-科目一，2-科目二，3-科目三，4-科目四',
  `fileType` tinyint(4) DEFAULT '1' COMMENT '文件类型：1-视频，2-H5课件',
  `examId` int(11) DEFAULT NULL COMMENT '考场ID',
  `serialId` int(11) DEFAULT NULL COMMENT '序号，文件排序使用',
  `name` varchar(32) DEFAULT NULL COMMENT '视频名称',
  `vtime` varchar(16) DEFAULT NULL COMMENT '视频时长',
  `url` varchar(255) DEFAULT NULL COMMENT '视频七牛路径',
  `video2Img` varchar(255) DEFAULT NULL COMMENT '视频2x图片',
  `video3Img` varchar(255) DEFAULT NULL COMMENT '视频3x图片',
  `isDel` tinyint(4) DEFAULT '0' COMMENT '是否启用:0-启用，1-不启用',
  `creator` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `utime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `actionRemark` varchar(1000) DEFAULT NULL COMMENT '操作说明',
  `standard` varchar(1000) DEFAULT NULL COMMENT '扣分标准',
  `skill` varchar(1000) DEFAULT NULL COMMENT '技巧',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='视频列表';



drop table if exists t_u_type;
CREATE TABLE `t_titile_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `channel` tinyint(4) DEFAULT '2' COMMENT '渠道：1-教练；2-学员',
  `name` varchar(64) NOT NULL COMMENT '分类名',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creater` varchar(32) NOT NULL COMMENT '创建人',
  `utime` date DEFAULT NULL COMMENT '更新时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='分类中心表';





drop table if exists t_u_type_content;
CREATE TABLE `t_type_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `typeId` int(11) NOT NULL COMMENT '关联主表ID',
  `serialId` int(11) DEFAULT NULL COMMENT '序号，排序使用',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型：1-单；2-多',
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `subject` varchar(128) DEFAULT NULL COMMENT '题目，多个以|号分割',
  `content` text COMMENT '内容，多个以|号分割',
  `releaseTime` date DEFAULT NULL COMMENT '发布时间',
  `status` tinyint(4) DEFAULT '1' COMMENT '发布状态：1-草稿，2-已发布；3-已撤回',
  `creater` varchar(32) NOT NULL COMMENT '创建人',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `utime` date DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内容明细表';



delete  from t_subject_video;
INSERT INTO `t_subject_video` (id, cityId, subject, fileType, examId, serialId, name, vtime, url, video2Img, video3Img, isDel, creator, ctime, updater, utime, actionRemark, standard, skill,remark) VALUES 
(1, 100100, 2, 1, NULL, 1, '候考', '01:20', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilihoukao.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/jinglukaoshitongdao@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/jinglukaoshitongdao@3x.png', 0, 'system', '2016-9-1 14:58:57', NULL, '2016-9-10 11:16:16', NULL, NULL, '车管所一般会在7点开门，八点考试。需要先排完队取号，取的号是按照时间的先后顺序排的，排的位置让你你也能大概知道自己的考试时间。考试之前的取号，那个号是已经给你安排了固定的车，每个人用车的时间不同，所以不用惊讶，排在你后面的人有可能比你先开始考试。仔细听广播，等叫到你的号和名字时，工作人员提醒你可以上车，车子也由另外的考员开过来了，这时你别问他车子好不好开，因为车管所的每一辆车都比你在驾校练车时开的车好开很多。他们的车方向盘好打，离合器易踩容易控制。', NULL),
(2, 100100, 2, 1, NULL, 2, '考前准备与起步', '01:22', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilikaoqianzhunyuqibu.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/shangchezhunbei@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/shangchezhunbei@3x.png', 0, 'system', '2016-9-1 15:01:16', NULL, '2016-9-10 11:18:50', NULL, NULL, '围绕车子转一圈，检查四轮气压是否正常、螺丝是否松动等；|检查车头下面是否有漏水、漏油、漏电现象；|调整座椅；|调整后视镜、左反光镜、右反光镜；|系好安全带；|检查档位是否空挡，手刹拉起。', NULL),
(3, 100100, 2, 1, NULL, 3, '倒车入库', '01:45', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilidaocheruku.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/daocheluku@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/daocheluku@3x.png', 0, 'system', '2016-9-1 15:01:32', NULL, '2016-9-10 11:03:13', '考试过程中，车辆进退途中不得停车。从道路一端控制线（车身压控制线）倒入车库停车，再前进出库向另一端驶过控制线后倒入车库停车，最后前进驶出车库。', '不按规定路线顺序行驶，扣100分|没有完全倒入库内，扣100分|车身出线，扣100分|中途停车，扣100分', '一定要保证车速又稳又慢，需要注意放松离合时切忌抬得过快、压得过猛；|打方向盘的手法要正确，记清楚方向盘要回多少或者是否已经回正。而且打方向要快；|看点的距离一定要准确，注意打方向盘的时机和停车的位置。进库后调整方向盘时不要猛打方向，即不能超过一圈。', NULL),
(5, 100100, 2, 1, NULL, 4, '侧方停车', '01:03', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilicefangtingche.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/cefangweitingche@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/cefangweitingche@3x.png', 0, 'system', '2016-9-1 15:01:53', NULL, '2016-9-10 11:03:55', '车辆在库前方靠右停稳后，一次倒车入库。|中途不得停车。|车轮不轧碰车道边线、库位边线。', '车辆入库停止后，车身出线，扣100分|行驶中车轮触压车道边线，扣10分|未停车于库内，扣100分|起步未开左转向灯，扣10分|中途停车，扣100分', '注意控制速度，很多人打方向时，脚会情不自禁地放松或踩离合器，控制好手和脚的协调性很重要，多练习；|看点要准确，记住平时练习找的点；|掌握回方向盘的时机，如果回晚了，右侧车身出线，回早了，左侧车身又会出线；|倒车时注意判断车辆是否平行，超出库位也会扣分的。', NULL),
(6, 100100, 2, 1, NULL, 5, '曲线行驶', '00:46', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xiliquxianxingshi.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/quxianxingshi@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/quxianxingshi@3x.png', 0, 'system', '2016-9-1 15:02:11', NULL, '2016-9-10 11:13:48', '驾驶车辆从弯道的一端前进驶入，从另一端驶出。车时压到左边缘线。|行驶中转向、速度平稳。|中途不得停车，车轮不得碰轧车道边线。|将车身移正时，车轮不必调正，只要车轮不压线就算合格。|注意离合器与加速踏板的协调配合，防止汽车熄火。', '行驶中车轮压线，扣100分|中途停车，扣100分', '尽量保持车辆与车道线平行，匀速行驶，及时修正方向。', NULL),
(7, 100100, 2, 1, NULL, 6, '直角转弯', '00:34', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilizhijiaozhuanwan.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/zhijiaozhuanwan@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/zhijiaozhuanwan@3x.png', 0, 'system', '2016-9-1 15:14:54', NULL, '2016-9-10 11:14:07', '驾驶车辆按规定的线路行驶，由左向右或由右向左直角转弯。|一次通过，中途不得停车。|车轮不得碰轧车道边线。', '行驶中车轮压线，扣100分|中途停车，扣100分', '低速通过；|进入直角时尽量贴近外侧控制线|到点就打死方向盘，不能过早或过晚。', NULL),
(8, 100100, 2, 1, NULL, 7, '坡道定点停车和起步', '00:46', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilipodaodingtingcheheqibu.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/dingdiantingcheheqibu@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/dingdiantingcheheqibu@3x.png', 0, 'system', '2016-9-1 15:15:00', NULL, '2016-9-10 11:03:39', '驾驶人应通过视觉和感觉及时判断坡道的陡坦、长短及路宽等道路情况，采取恰当操作方法，控制车辆平稳停车和起步。|坐到转向正确，换挡迅速，方向、制动、离合器三者配合准确协调。', '前保险杠位于虚线以内、实线以外，扣10分|前保险杠位于虚线以外，扣100分|再次起步时后溜小于30CM，扣10分，大于30CM，扣100分', '控制车速，上坡时应保持足够的动力；|正确判断车身位置，及时修正方向|坡道起步时应学会控制好离合、刹车之间的关系。', NULL),
(9, 100100, 2, 1, NULL, 8, '考试完成', '00:57', 'http://o7d94lzvx.bkt.clouddn.com/video/twosubject/xilikaoshiwancheng.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/kaoshiwancheng@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/kaoshiwancheng@3x.png', 0, 'system', '2016-9-1 15:15:07', NULL, '2016-9-10 11:14:16', NULL, NULL, '考试结束后把车开到起始位置，即你在哪里开的还开到哪里去，不要熄火。考试结束后你不要离开考场，要等待签字确认你的成绩。等确认好成绩以后科目二的工作就全部结束了。', NULL),
(10, 100100, 3, 1, NULL, 1, '路考讲解1', '08:43', 'http://o7d94lzvx.bkt.clouddn.com/bszkaochangdong.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/bszdongkaochang@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/bszdongkaochang@3x.png', 0, 'system', '2016-9-1 15:15:11', NULL, '2016-9-10 11:21:16', NULL, NULL, '1.考生指纹验证无法通过后，请下车对指纹进行重新采集后方可重新排队进行考试。|2.考试过程中如果考生分数低于合格分数，系统会自动提示考生进行靠边停车，靠边停车动作完成后系统会提示进行下一次考试。|3.考生按要求完成所有必考项目且行驶距离大于3公里后，系统将自动提示靠边停车。学员听到靠边停车语音后，请在考试路线上自行选择清晰路沿石(或道路边缘实线)处，确认安全后完成靠边停车动作。靠边停车完成后，系统将语音播报考试结果。|4.绕车一周观察车辆外观和周边环境时，为保证雷达信号检测的准确性，请保持人身与车身距离在1米范围内。|5.根据公安部对驾驶人考试规定的要求，每个学员1次预约有两次考试机会，第一次考试不及格可当场补考一次.学员第一次考试时需要参加绕车一周和夜间灯光模拟，第一次考试不及格第二次补考时，学员可不进行绕车一周和灯光模拟，准备完成后直接开始第二次考试.第一次考试不及格停放车辆区域的考试科目将在绕路线一周后开始考试考试起点可以在考试路段的任意位置，最终学员需要考满16项考试项目后方可结束考试上传考试成绩.(注意:参加白天考试的学员需要进行夜间灯光模拟，参加夜间考试的学员将不再进行本项目的评判)。|6.如有多人同时跟车准备考试的情况，一个学员考试结束后，下一个学员需要依照标准流程下车从上车准备，绕车一周以及夜间灯光模拟等开始考试。', NULL),
(11, 100100, 3, 1, NULL, 2, '路考讲解2', '09:05', 'http://o7d94lzvx.bkt.clouddn.com/bszkaochangxi.mp4', 'http://o7d94lzvx.bkt.clouddn.com/image/png/bszxikaochang@2x.png', 'http://o7d94lzvx.bkt.clouddn.com/image/png/bszxikaochang@3x.png', 0, 'system', '2016-9-9 11:03:55', NULL, '2016-9-10 11:21:28', NULL, NULL, '1.考生指纹验证无法通过后，请下车对指纹进行重新采集后方可重新排队进行考试。|2.考试过程中如果考生分数低于合格分数，系统会自动提示考生进行靠边停车，靠边停车动作完成后系统会提示进行下一次考试。|3.考生按要求完成所有必考项目且行驶距离大于3公里后，系统将自动提示靠边停车。学员听到靠边停车语音后，请在考试路线上自行选择清晰路沿石(或道路边缘实线)处，确认安全后完成靠边停车动作。靠边停车完成后，系统将语音播报考试结果。|4.绕车一周观察车辆外观和周边环境时，为保证雷达信号检测的准确性，请保持人身与车身距离在1米范围内。|5.根据公安部对驾驶人考试规定的要求，每个学员1次预约有两次考试机会，第一次考试不及格可当场补考一次.学员第一次考试时需要参加绕车一周和夜间灯光模拟，第一次考试不及格第二次补考时，学员可不进行绕车一周和灯光模拟，准备完成后直接开始第二次考试.第一次考试不及格停放车辆区域的考试科目将在绕路线一周后开始考试考试起点可以在考试路段的任意位置，最终学员需要考满16项考试项目后方可结束考试上传考试成绩.(注意:参加白天考试的学员需要进行夜间灯光模拟，参加夜间考试的学员将不再进行本项目的评判)。|6.如有多人同时跟车准备考试的情况，一个学员考试结束后，下一个学员需要依照标准流程下车从上车准备，绕车一周以及夜间灯光模拟等开始考试。', NULL);

ALTER TABLE `db_lili`.`t_p_notice`   
  ADD COLUMN `pic` VARCHAR(32) NULL   COMMENT '消息的图片' AFTER `etime`;
ALTER TABLE `db_lili`.`t_u_student`   
  ADD COLUMN `basicHour2` INT DEFAULT 0  NULL   COMMENT '科目二基础训练已学课时(分钟)' AFTER `lastLogin`,
  ADD COLUMN `testHour2` INT DEFAULT 0  NULL   COMMENT '科目二应试训练已学课时(分钟)' AFTER `basicHour2`,
  ADD COLUMN `simTestHour2` INT DEFAULT 0  NULL   COMMENT '科目二考场模拟已学课时(分钟)' AFTER `testHour2`,
  ADD COLUMN `basicHour3` INT DEFAULT 0  NULL   COMMENT '科目二应试训练已学课时(分钟)' AFTER `simTestHour2`,
  ADD COLUMN `testHour3` INT DEFAULT 0  NULL   COMMENT '科目二考场模拟已学课时(分钟)' AFTER `basicHour3`,
  ADD COLUMN `simTestHour3` INT DEFAULT 0  NULL   COMMENT '科目二应试训练已学课时(分钟)' AFTER `testHour3`,
  ADD COLUMN `driveHour` INT DEFAULT 0  NULL   COMMENT '陪驾已学课时(分钟)' AFTER `simTestHour3`;
  
  update `t_s_coupon` set validityPeriod=0,expireTime='2016-12-31 23:59:59',useNote='1.该优惠券仅限深圳、东莞、西安、长沙用户使用; |2.该优惠券可用于报名费和约课，每次限用1张，不与其他优惠券同时使用; |3.该优惠券不可提现，使用有效期自即日起至2016年12月31日; |4.该优惠券最终解释权归深圳市车厘子网络科技有限公司所有。' where couponTmpId='68f6c9f2ca83492faa6a591cf24a3403';