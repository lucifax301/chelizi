DROP TABLE
IF EXISTS t_enroll_package_template;

CREATE TABLE t_enroll_package_template (
	ttid INT auto_increment PRIMARY KEY COMMENT '主键',
	city_id INT NOT NULL COMMENT '城市id',
	name varchar(50) not NULL COMMENT '报名模版的名称，默认为城市名称',
	price INT NOT NULL COMMENT '报名总价，单位分',
	title VARCHAR (256) NOT NULL COMMENT '摘要',
	icon VARCHAR (256) NOT NULL COMMENT '图标',
	protocol text NOT NULL COMMENT '报名协议',
	price_detail text COMMENT '价格详细说明，格式tile=price,多条之间用加号（+）分割，亦可以考虑富文本模式',
	test_condition text COMMENT '报名条件：多条之间采用加号（+）分割，亦可以考虑富文本模式',
	describtion text COMMENT '描述详情',
	self_test TINYINT NOT NULL DEFAULT 0 COMMENT '是否支持自主报考：0不支持，1支持',
	proc_pic VARCHAR (256) COMMENT '报名流程图片',
	order_by INT COMMENT '报名报排序',
	first_step int COMMENT '该报名流程第一步',
	isdel TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态：0代表未删除，1代表已删除',
	cuid BIGINT COMMENT '创建人id',
	muid BIGINT COMMENT '更新人id',
	ctime datetime COMMENT '创建时间',
	mtime TIMESTAMP COMMENT '更新时间'
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '报考模版表';

DROP TABLE
IF EXISTS t_enroll_order;

CREATE TABLE t_enroll_order (
	order_id VARCHAR (32) NOT NULL UNIQUE PRIMARY KEY COMMENT '订单号',
	student_id BIGINT NOT NULL COMMENT '报名学生帐号',
	ttid INT NOT NULL COMMENT '报名模版',
	drive_License CHAR (1) NOT NULL DEFAULT 'C' COMMENT '报考驾照类别:A,B,C,D。当前只支持C',
	dltype TINYINT (4) NOT NULL DEFAULT 1 COMMENT '驾照类型：1代表C1,2代表C2',
	NAME VARCHAR (50) COMMENT '报考姓名',
	card_type TINYINT NOT NULL DEFAULT 1 COMMENT '报考证件：1代表身份证',
	card_id VARCHAR (50) COMMENT '报考证件号码',
	card_pic1 VARCHAR (256) COMMENT '证件正面照片',
	card_pic2 VARCHAR (256) COMMENT '证件反面照片',
	school_id INT COMMENT '驾校id',
	office_id VARCHAR (50) COMMENT '车管所报名流水号',
	pay_state TINYINT (4) NOT NULL DEFAULT '0' COMMENT '支付状态：0未开始，1已提交，100成功，101失败,',
	pay_time datetime DEFAULT NULL COMMENT '支付时间',
	post_state TINYINT NOT NULL DEFAULT 0 COMMENT '资料邮寄状态：0资料未邮寄，1资料已邮寄，2资料已收到',
	test_state TINYINT NOT NULL DEFAULT 0 COMMENT '报考状态：0未报考，其他为进度步骤step_id',
	city_id INT NOT NULL COMMENT '冗余：报名城市id',
	price INT NOT NULL COMMENT '冗余：报名总价，单位分',
	school_name VARCHAR (50) COMMENT '冗余：报名驾校名称',
	price_detail text COMMENT '冗余：价格详细说明，格式tile=price,多条之间用加号（+）分割，亦可以考虑富文本模式'
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '报考订单表';

DROP TABLE
IF EXISTS t_enroll_material_items;

CREATE TABLE t_enroll_material_items (
	pmid INT auto_increment PRIMARY KEY COMMENT '主键',
	NAME VARCHAR (256) NOT NULL COMMENT '资料名称',
	pic VARCHAR (256) COMMENT '实例图片，多个用逗号分割',
	icon VARCHAR (256) COMMENT '图标',
	description text COMMENT '详细描述',
	isdel TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态：0代表未删除，1代表已删除',
	cuid BIGINT COMMENT '创建人id',
	muid BIGINT COMMENT '更新人id',
	ctime datetime COMMENT '创建时间',
	mtime TIMESTAMP COMMENT '更新时间'
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '基础数据表：报名材料项目表，前后端都需要穷举的表';

DROP TABLE
IF EXISTS t_enroll_material;

CREATE TABLE t_enroll_material (
	tmid INT auto_increment PRIMARY KEY COMMENT '主键',
	city_id INT COMMENT '城市id',
	ttid INT COMMENT '冗余：报名模版',
	ptype TINYINT NOT NULL DEFAULT 1 COMMENT '居民类别：1本地居民，2外地居民，3港澳台，4现役军人',
	pdesc varchar(50) COMMENT '居民描述：1本地居民，2外地居民，3港澳台，4现役军人',
	pmid varchar(50) NOT NULL COMMENT '邮寄资料项id，多个用逗号分隔',
	isdel TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态：0代表未删除，1代表已删除',
	cuid BIGINT COMMENT '创建人id',
	muid BIGINT COMMENT '更新人id',
	ctime datetime COMMENT '创建时间',
	mtime TIMESTAMP COMMENT '更新时间'
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '关系表：各城市需要的报名材料表';

DROP TABLE
IF EXISTS t_enroll_material_address;

CREATE TABLE t_enroll_material_address (
	paid INT auto_increment PRIMARY KEY COMMENT '主键',
	city_id INT NOT NULL COMMENT '城市id',
	ttid INT NOT NULL COMMENT '冗余：报名模版',
	school_id INT COMMENT '驾校id',
	address VARCHAR (256) NOT NULL COMMENT '邮寄详细地址',
	post_id VARCHAR (20) COMMENT '邮编',
	contacts VARCHAR (50) NOT NULL COMMENT '联系人',
	mobile VARCHAR (11) COMMENT '联系手机',
	phone VARCHAR (20) NOT NULL COMMENT '联系电话',
	qq VARCHAR (50) COMMENT '联系qq',
	email VARCHAR (50) COMMENT '联系邮箱'
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '各城市报考资料邮寄地址表';

DROP TABLE
IF EXISTS t_enroll_progress_template;

CREATE TABLE t_enroll_progress_template (
	cpid INT auto_increment PRIMARY KEY COMMENT '主键',
	ttid INT not null COMMENT '城市报名id表的主键，本表的外键',
	city_id INT NOT NULL COMMENT '冗余：城市id',
	step_id INT NOT NULL COMMENT '步骤id，登录后显示的成为前置步骤，其步骤id<2000,其他步骤id则2000开始',
	step_type INT COMMENT '步骤类别，每一个构成大步骤的小步骤属于同一类别，例如报名，某科目报考+考试',
	next_step INT COMMENT '此步骤成功后的下一步',
	retry_step INT COMMENT '此步骤失败后的重新操作的下一步',
	icon VARCHAR (256) NOT NULL COMMENT '步骤图标',
	title VARCHAR (50) COMMENT '步骤名称,不需要显示时可以为空',
	dpage VARCHAR (256) COMMENT '该步骤对应的详情页，为空表示没有',
	bpage VARCHAR (256) COMMENT '该步骤对应的回退页面，为空表示没有，前端亦可不参考',
	pre_doc VARCHAR (256)  COMMENT '这一步骤将要开始（与进度状态关联）显示给用户的文案，其中可能包含变量',
	none_doc VARCHAR (256)  COMMENT '这一步骤未开始（与进度状态关联）显示给用户的文案，其中可能包含变量',
	submit_doc VARCHAR (256) COMMENT '这一步骤提交后显示给用户的文案，其中可能包含变量',
	succ_doc VARCHAR (256) COMMENT '这一步骤成功显示给用户的文案，其中可能包含变量',
	fail_doc VARCHAR (256) COMMENT '这一步骤失败显示给用户的文案，其中可能包含变量',
	none_rec TINYINT NOT NULL DEFAULT 0 COMMENT '这一步骤还未开始这条数据是否显示：0不显示，1显示',
	submit_rec TINYINT NOT NULL DEFAULT 0 COMMENT '这一步骤提交后这条数据是否显示：0不显示，1显示',
	succ_rec TINYINT NOT NULL DEFAULT 0 COMMENT '这一步骤成功这条数据是否显示：0不显示，1显示',
	fail_rec TINYINT NOT NULL DEFAULT 0 COMMENT '这一步骤失败这条数据是否显示：0不显示，1显示',
	submit_push VARCHAR (256) COMMENT '该步骤提交后推送消息文案,其中可能含有变量,为空表示不推送',
	succ_push VARCHAR (256) COMMENT '该步骤成功后推送消息文案,其中可能含有变量,为空表示不推送',
	fail_push VARCHAR (256) COMMENT '该步骤失败后推送消息文案,其中可能含有变量,为空表示不推送',
	pre_display TINYINT (4) NOT NULL DEFAULT 0 COMMENT '是否预先显示：0代表不显示，1代表显示',
	price INT NOT NULL DEFAULT 0 COMMENT '这一步支付的价钱：单位分,主要预留重新报考使用',
	red TINYINT NOT NULL DEFAULT 0 COMMENT '未读是否红点显示，0代表不显示，1代表显示',
	reco_course VARCHAR (256) COMMENT '当前步骤推荐课程，多个课程用逗号分割',
	other_course VARCHAR (256) COMMENT '推荐外的其他课程，多个课程用逗号分割',
	order_by INT NOT NULL DEFAULT 1 COMMENT '步骤排序，防止后续插入的步骤号大的步骤需要排到序列的前面',
	recovery TINYINT NOT NULL DEFAULT 0 COMMENT '恢复是否重入：0代表不重入，1代表重入，目前只有报名需要重入',
	UNIQUE KEY (ttid, step_id)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '各城市进度模版表，前后端都需要穷举的表';

DROP TABLE
IF EXISTS t_enroll_progress_user;

CREATE TABLE t_enroll_progress_user (
	cpid INT auto_increment PRIMARY KEY COMMENT '主键',
	title VARCHAR (50) COMMENT '冗余：步骤名称,不需要显示时可以为空',
	student_id BIGINT NOT NULL COMMENT '学员id',
	step_id INT NOT NULL COMMENT '外键：步骤id',
	step_times INT NOT NULL DEFAULT 1 COMMENT '该步骤执行的次数',
	pay_state TINYINT (4) NOT NULL DEFAULT '0' COMMENT '支付状态：0代表未支付，1代表已支付,2代表支付失败，9代表老学员自动支付',
	pay_time datetime DEFAULT NULL COMMENT '支付时间',
	process_state TINYINT NOT NULL DEFAULT 0 COMMENT '该步骤状态：0未开始，1已提交，100成功，101失败,999未来将需要完成的步骤',
	result VARCHAR (256) COMMENT '该步骤结论的文字显示，来自doc模版',
	isread TINYINT NOT NULL DEFAULT 0 COMMENT '该步骤用户是否已读，0未读，1已读',
	next_step INT COMMENT '根据模版和逻辑计算出来的下一步步骤id',
	need_show tinyint COMMENT '根据模版和逻辑计算该步骤当前是否需要在我的进度列表中显示',
	out_data VARCHAR (256) COMMENT '外部数据：例如车管所返回等',
	icon VARCHAR (256) NOT NULL COMMENT '冗余：步骤图标',
	price INT NOT NULL DEFAULT 0 COMMENT '冗余：该步骤需要支付的价钱',
	city_id INT NOT NULL COMMENT '冗余：城市id',
	ttid INT COMMENT '冗余：报名模版',
	school_id INT COMMENT '冗余：驾校id',
	red TINYINT NOT NULL DEFAULT 0 COMMENT '冗余：未读是否红点显示，0代表不显示，1代表显示',
	next_title VARCHAR (50) COMMENT '冗余：下一步骤文字标题，来自模版title',
	reco_course VARCHAR (256) COMMENT '冗余：当前步骤推荐课程，多个课程用逗号分割',
	other_course VARCHAR (256) COMMENT '冗余：推荐外的其他课程，多个课程用逗号分割',
	dpage VARCHAR (256) COMMENT '冗余：该步骤对应的详情页，为空表示没有',
	bpage VARCHAR (256) COMMENT '冗余：该步骤对应的回退页面，为空表示没有，前端亦可不参考',
	recovery TINYINT NOT NULL DEFAULT 0 COMMENT '冗余：恢复是否重入：0代表不重入，1代表重入，目前只有报名需要重入',
	order_by INT NOT NULL DEFAULT 1 COMMENT '冗余：步骤排序，用于拼接将要做的步骤',
	isdel TINYINT NOT NULL DEFAULT 0 COMMENT '客服可能介入，所以需要这5个字段：删除状态：0代表未删除，1代表已删除',
	cuid BIGINT COMMENT '创建人id',
	muid BIGINT COMMENT '更新人id',
	ctime datetime COMMENT '创建时间',
	mtime TIMESTAMP COMMENT '更新时间'
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '学员学习进度表';


DROP TABLE
IF EXISTS t_enroll_exam_reg;

CREATE TABLE `t_enroll_exam_reg` (
  `water_num` bigint(20) NOT NULL COMMENT '流水号',
  `city_id` int(11) NOT NULL COMMENT '城市id',
  `city_name` varchar(20) DEFAULT NULL COMMENT '城市名称',
  `subject_id` tinyint(4) NOT NULL COMMENT '科目id: 1-科目一；2-科目二；3-科目三',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `id_card` varchar(20) NOT NULL COMMENT '身份证号',
  `drtype` tinyint(4) DEFAULT NULL COMMENT '准驾车型：1-c1;2-c2',
  `exam_place` varchar(255) DEFAULT NULL COMMENT '考场信息',
  `user_id` bigint(11) DEFAULT NULL COMMENT '学员在系统中的id',
  `user_mobile` varchar(20) DEFAULT NULL COMMENT '学员在系统中的手机号',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`water_num`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8  COMMENT '约考报名表';

DROP TABLE
IF EXISTS t_enroll_exam_place;

CREATE TABLE `t_enroll_exam_place` (
  `placeId` int(11) NOT NULL COMMENT '考场id',
  `name` varchar(64) DEFAULT NULL COMMENT '考场名字',
  `cityId` int(10) DEFAULT NULL COMMENT '城市id',
  `lge` decimal(20,14) DEFAULT NULL COMMENT '考场经度',
  `lae` decimal(20,14) DEFAULT NULL COMMENT '考场纬度',
  `posDesc` varchar(32) DEFAULT NULL COMMENT '考场位置描述',
  `phoneNum` varchar(32) DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`placeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ##另外学员表中还需要增加一个进度状态字段，可用于简单描述当前步骤，另外需要加school_id标明报考驾校
-- ##前端需要对所有已明确的步骤进行单一建模，如果遇到无法处理的步骤，则提醒用户进行客户端更新


-- -----------初始化数据 进度模板-----------------
INSERT INTO `t_enroll_progress_template` VALUES 
(1, 1, 100100, 1, 1, 2, 1, 'progress_drivelicense', '报名', 'dpage_enrolllist', NULL, NULL, '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', 1, 0, 0, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 1, 1),
(2, 1, 100100, 2, 1, 3, 2, 'progress_drivelicense', '支付', 'step_2', NULL, NULL, '您有报名订单需要支付', '您已支付报考学费{1}元,驾照类别为{2}', '您成功已支付报考学费{1}元,驾照类别为{2}', '您的报名订单支付失败，请重新支付', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 2, 1),
(3, 1, 100100, 3, 1, 4, 3, 'progress_messages', NULL, 'step_3', NULL, NULL, '填写个人资料', '您已经填写基本信息', '您已经填写基本信息', NULL, 1, 1, 1, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 3, 1),
(4, 1, 100100, 4, 1, 5, 4, 'progress_messages', NULL, 'step_4', NULL, NULL, '{1}你好,需要您线下邮寄资料给我们，具体资料点击查看详情', '您的资料已邮寄，请等待车管所反馈', '您已邮寄相关资料到车管所', NULL, 1, 1, 1, 0, NULL, '您的资料已收到', NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 4, 1),
(5, 1, 100100, 5, 1, 2001, 4, 'progress_drivelicense', NULL, 'step_5', NULL, NULL, '请您耐心等待报考结果', '请您耐心等待报考结果', '您已经成功报考{1},流水号为{2}', '您报考失败，请重新邮寄资料', 1, 1, 1, 1, NULL, '您已经成功报考{1},流水号为{2}', '您报考失败，请重新邮寄资料', 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(6, 1, 100100, 2001, 2, 2002, 2001, 'progress_subjectone', '科目一', NULL, NULL, '科目一', '预约科目一考试', '你已提交科目一约考', '您科目一约考成功', '您科目一约考失败', 1, 1, 1, 1, NULL, '您科目一约考成功', '您科目一约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 6, 0),
(7, 1, 100100, 3001, 3, 3002, 3001, 'progress_subjecttwo', '科目二', NULL, NULL, '科目二', '预约科目二考试', '你已提交科目二约考', '您科目二约考成功', '您科目二约考失败', 1, 1, 1, 1, NULL, '您科目二约考成功', '您科目二约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 7, 0),
(8, 1, 100100, 4001, 4, 4002, 4001, 'progress_longtraining', '长训', NULL, NULL, '长训', '预约长训考试', '你已提交长训约考', '您长训约考成功', '您长训约考失败', 1, 1, 1, 1, NULL, '您长训约考成功', '您长训约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 8, 0),
(9, 1, 100100, 5001, 5, 5002, 5001, 'progress_subjectthree', '科目三', NULL, NULL, '科目三', '预约科目三考试', '你已提交科目三约考', '您科目三约考成功', '您科目三约考失败', 1, 1, 1, 1, NULL, '您科目三约考成功', '您科目三约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 9, 0),
(10, 1, 100100, 6001, 6, 6002, 6001, 'progress_subjectfour', '科目四', NULL, NULL, '科目四', '预约科目四考试', '你已提交科目四约考', '您科目四约考成功', '您科目四约考失败', 1, 1, 1, 1, NULL, '您科目四约考成功', '您科目四约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 10, 0),
(11, 1, 100100, 7001, 7, 7002, 7001, 'progress_drivelicense', '驾照', NULL, NULL, '通过考试,拿到驾照', '请耐心等待您的驾照', '通过考试,拿到驾照', '通过考试,拿到驾照', '通过考试,拿到驾照', 1, 1, 1, 1, NULL, '通过考试,拿到驾照', '通过考试,拿到驾照', 1, 0, 1, '1,2,3,4,5', '6,7,8', 11, 0),
(12, 2, 100101, 1, 1, 2, 1, 'progress_drivelicense', '报名', 'dpage_enrolllist', NULL, NULL, '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', 1, 0, 0, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 1, 1),
(13, 2, 100101, 2, 1, 3, 2, 'progress_drivelicense', '支付', 'step_2', NULL, NULL, '您有报名订单需要支付', '您已支付报考学费{1}元,驾照类别为{2}', '您成功已支付报考学费{1}元,驾照类别为{2}', '您的报名订单支付失败，请重新支付', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 2, 1),
(14, 2, 100101, 3, 1, 4, 3, 'progress_messages', NULL, 'step_3', NULL, NULL, '填写个人资料', '您已经填写基本信息', '您已经填写基本信息', NULL, 1, 1, 1, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 3, 1),
(15, 2, 100101, 4, 1, 5, 4, 'progress_messages', NULL, 'step_4', NULL, NULL, '{1}你好,需要您线下邮寄资料给我们，具体资料点击查看详情', '您的资料已邮寄，请等待车管所反馈', '您已邮寄相关资料到车管所', NULL, 1, 1, 1, 0, NULL, '您的资料已收到', NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 4, 1),
(16, 2, 100101, 5, 1, 2001, 4, 'progress_drivelicense', NULL, 'step_5', NULL, NULL, '请您耐心等待报考结果', '请您耐心等待报考结果', '您已经成功报考{1},流水号为{2}', '您报考失败，请重新邮寄资料', 1, 1, 1, 1, NULL, '您已经成功报考{1},流水号为{2}', '您报考失败，请重新邮寄资料', 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(17, 2, 100101, 2001, 2, 2002, 2001, 'progress_subjectone', '科目一', NULL, NULL, '科目一', '预约科目一考试', '你已提交科目一约考', '您科目一约考成功', '您科目一约考失败', 1, 1, 1, 1, NULL, '您科目一约考成功', '您科目一约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 6, 0),
(18, 2, 100101, 3001, 3, 3002, 3001, 'progress_subjecttwo', '科目二', NULL, NULL, '科目二', '预约科目二考试', '你已提交科目二约考', '您科目二约考成功', '您科目二约考失败', 1, 1, 1, 1, NULL, '您科目二约考成功', '您科目二约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 7, 0),
(19, 2, 100101, 4001, 4, 4002, 4001, 'progress_longtraining', '长训', NULL, NULL, '长训', '预约长训考试', '你已提交长训约考', '您长训约考成功', '您长训约考失败', 1, 1, 1, 1, NULL, '您长训约考成功', '您长训约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 8, 0),
(20, 2, 100101, 5001, 5, 5002, 5001, 'progress_subjectthree', '科目三', NULL, NULL, '科目三', '预约科目三考试', '你已提交科目三约考', '您科目三约考成功', '您科目三约考失败', 1, 1, 1, 1, NULL, '您科目三约考成功', '您科目三约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 9, 0),
(21, 2, 100101, 6001, 6, 6002, 6001, 'progress_subjectfour', '科目四', NULL, NULL, '科目四', '预约科目四考试', '你已提交科目四约考', '您科目四约考成功', '您科目四约考失败', 1, 1, 1, 1, NULL, '您科目四约考成功', '您科目四约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 10, 0),
(22, 2, 100101, 7001, 7, 7002, 7001, 'progress_drivelicense', '驾照', NULL, NULL, '通过考试,拿到驾照', '请耐心等待您的驾照', '通过考试,拿到驾照', '通过考试,拿到驾照', '通过考试,拿到驾照', 1, 1, 1, 1, NULL, '通过考试,拿到驾照', '通过考试,拿到驾照', 1, 0, 1, '1,2,3,4,5', '6,7,8', 11, 0),
(23, 3, 100102, 1, 1, 2, 1, 'progress_drivelicense', '报名', 'dpage_enrolllist', NULL, NULL, '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', 1, 0, 0, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 1, 1),
(24, 3, 100102, 2, 1, 3, 2, 'progress_drivelicense', '支付', 'step_2', NULL, NULL, '您有报名订单需要支付', '您已支付报考学费{1}元,驾照类别为{2}', '您成功已支付报考学费{1}元,驾照类别为{2}', '您的报名订单支付失败，请重新支付', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 2, 1),
(25, 3, 100102, 3, 1, 4, 3, 'progress_messages', NULL, 'step_3', NULL, NULL, '填写个人资料', '您已经填写基本信息', '您已经填写基本信息', NULL, 1, 1, 1, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 3, 1),
(26, 3, 100102, 4, 1, 5, 4, 'progress_messages', NULL, 'step_4', NULL, NULL, '{1}你好,需要您线下邮寄资料给我们，具体资料点击查看详情', '您的资料已邮寄，请等待车管所反馈', '您已邮寄相关资料到车管所', NULL, 1, 1, 1, 0, NULL, '您的资料已收到', NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 4, 1),
(27, 3, 100102, 5, 1, 2001, 4, 'progress_drivelicense', NULL, 'step_5', NULL, NULL, '请您耐心等待报考结果', '请您耐心等待报考结果', '您已经成功报考{1},流水号为{2}', '您报考失败，请重新邮寄资料', 1, 1, 1, 1, NULL, '您已经成功报考{1},流水号为{2}', '您报考失败，请重新邮寄资料', 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(28, 3, 100102, 2001, 2, 2002, 2001, 'progress_subjectone', '科目一', NULL, NULL, '科目一', '预约科目一考试', '你已提交科目一约考', '您科目一约考成功', '您科目一约考失败', 1, 1, 1, 1, NULL, '您科目一约考成功', '您科目一约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 6, 0),
(29, 3, 100102, 3001, 3, 3002, 3001, 'progress_subjecttwo', '科目二', NULL, NULL, '科目二', '预约科目二考试', '你已提交科目二约考', '您科目二约考成功', '您科目二约考失败', 1, 1, 1, 1, NULL, '您科目二约考成功', '您科目二约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 7, 0),
(30, 3, 100102, 4001, 4, 4002, 4001, 'progress_longtraining', '长训', NULL, NULL, '长训', '预约长训考试', '你已提交长训约考', '您长训约考成功', '您长训约考失败', 1, 1, 1, 1, NULL, '您长训约考成功', '您长训约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 8, 0),
(31, 3, 100102, 5001, 5, 5002, 5001, 'progress_subjectthree', '科目三', NULL, NULL, '科目三', '预约科目三考试', '你已提交科目三约考', '您科目三约考成功', '您科目三约考失败', 1, 1, 1, 1, NULL, '您科目三约考成功', '您科目三约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 9, 0),
(32, 3, 100102, 6001, 6, 6002, 6001, 'progress_subjectfour', '科目四', NULL, NULL, '科目四', '预约科目四考试', '你已提交科目四约考', '您科目四约考成功', '您科目四约考失败', 1, 1, 1, 1, NULL, '您科目四约考成功', '您科目四约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 10, 0),
(33, 3, 100102, 7001, 7, 7002, 7001, 'progress_drivelicense', '驾照', NULL, NULL, '通过考试,拿到驾照', '请耐心等待您的驾照', '通过考试,拿到驾照', '通过考试,拿到驾照', '通过考试,拿到驾照', 1, 1, 1, 1, NULL, '通过考试,拿到驾照', '通过考试,拿到驾照', 1, 0, 1, '1,2,3,4,5', '6,7,8', 11, 0),
(34, 4, 100103, 1, 1, 2, 1, 'progress_drivelicense', '报名', 'dpage_enrolllist', NULL, NULL, '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', 1, 0, 0, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 1, 1),
(35, 4, 100103, 2, 1, 3, 2, 'progress_drivelicense', '支付', 'step_2', NULL, NULL, '您有报名订单需要支付', '您已支付报考学费{1}元,驾照类别为{2}', '您成功已支付报考学费{1}元,驾照类别为{2}', '您的报名订单支付失败，请重新支付', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 2, 1),
(36, 4, 100103, 3, 1, 4, 3, 'progress_messages', NULL, 'step_3', NULL, NULL, '填写个人资料', '您已经填写基本信息', '您已经填写基本信息', NULL, 1, 1, 1, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 3, 1),
(37, 4, 100103, 4, 1, 5, 4, 'progress_messages', NULL, 'step_4', NULL, NULL, '{1}你好,需要您线下邮寄资料给我们，具体资料点击查看详情', '您的资料已邮寄，请等待车管所反馈', '您已邮寄相关资料到车管所', NULL, 1, 1, 1, 0, NULL, '您的资料已收到', NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 4, 1),
(38, 4, 100103, 5, 1, 2001, 4, 'progress_drivelicense', NULL, 'step_5', NULL, NULL, '请您耐心等待报考结果', '请您耐心等待报考结果', '您已经成功报考{1},流水号为{2}', '您报考失败，请重新邮寄资料', 1, 1, 1, 1, NULL, '您已经成功报考{1},流水号为{2}', '您报考失败，请重新邮寄资料', 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(39, 4, 100103, 2001, 2, 2002, 2001, 'progress_subjectone', '科目一', NULL, NULL, '科目一', '预约科目一考试', '你已提交科目一约考', '您科目一约考成功', '您科目一约考失败', 1, 1, 1, 1, NULL, '您科目一约考成功', '您科目一约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 6, 0),
(40, 4, 100103, 3001, 3, 3002, 3001, 'progress_subjecttwo', '科目二', NULL, NULL, '科目二', '预约科目二考试', '你已提交科目二约考', '您科目二约考成功', '您科目二约考失败', 1, 1, 1, 1, NULL, '您科目二约考成功', '您科目二约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 7, 0),
(41, 4, 100103, 4001, 4, 4002, 4001, 'progress_longtraining', '长训', NULL, NULL, '长训', '预约长训考试', '你已提交长训约考', '您长训约考成功', '您长训约考失败', 1, 1, 1, 1, NULL, '您长训约考成功', '您长训约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 8, 0),
(42, 4, 100103, 5001, 5, 5002, 5001, 'progress_subjectthree', '科目三', NULL, NULL, '科目三', '预约科目三考试', '你已提交科目三约考', '您科目三约考成功', '您科目三约考失败', 1, 1, 1, 1, NULL, '您科目三约考成功', '您科目三约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 9, 0),
(43, 4, 100103, 6001, 6, 6002, 6001, 'progress_subjectfour', '科目四', NULL, NULL, '科目四', '预约科目四考试', '你已提交科目四约考', '您科目四约考成功', '您科目四约考失败', 1, 1, 1, 1, NULL, '您科目四约考成功', '您科目四约考失败', 1, 0, 1, '1,2,3,4,5', '6,7,8', 10, 0),
(44, 4, 100103, 7001, 7, 7002, 7001, 'progress_drivelicense', '驾照', NULL, NULL, '通过考试,拿到驾照', '请耐心等待您的驾照', '通过考试,拿到驾照', '通过考试,拿到驾照', '通过考试,拿到驾照', 1, 1, 1, 1, NULL, '通过考试,拿到驾照', '通过考试,拿到驾照', 1, 0, 1, '1,2,3,4,5', '6,7,8', 11, 0);




-- -----------初始化数据 报名包类型-----------------
INSERT INTO t_enroll_package_template VALUES 
(null,100100,'深圳',199800,'身份证、居住证、体检表、照片','深圳.jpg','http://www.lilixc.com/examination-registration.html','科目一报考费=70+科目二报考费=130+科目三报考费=280+工本费=10+长训费=500+档案费=1008','年龄在18周岁以上+矫正视力大于等于4.9+听力无障碍',
'身份证复印件（正反面）+1寸白底照片6张+体检表',1,'深圳报名流程.jpg',null,1,0,null,null,null,null),
(null,100102,'广州',199800,'身份证、居住证、体检表、照片','广州.jpg','http://www.lilixc.com/examination-registration.html','科目一报考费=70+科目二报考费=130+科目三报考费=280+工本费=10+长训费=500+档案费=1008','年龄在18周岁以上+矫正视力大于等于4.9+听力无障碍',
'身份证复印件（正反面）+1寸白底照片6张+体检表',1,'广州报名流程.jpg',null,1,0,null,null,null,null),
(null,100101,'东莞',318800,'身份证、居住证、体检表、照片','东莞.jpg','http://www.lilixc.com/examination-registration.html','科目一报考费=70+科目二报考费=130+科目三报考费=280+工本费=10+长训费=500+档案费=1008+入场费=1190','年龄在18周岁以上+矫正视力大于等于4.9+听力无障碍',
'身份证复印件（正反面）+1寸白底照片6张+体检表',1,'东莞报名流程.jpg',null,1,0,null,null,null,null),
(null,100104,'佛山',199800,'身份证、居住证、体检表、照片','佛山.jpg','http://www.lilixc.com/examination-registration.html','科目一报考费=70+科目二报考费=130+科目三报考费=280+工本费=10+长训费=500+档案费=1008','年龄在18周岁以上+矫正视力大于等于4.9+听力无障碍',
'身份证复印件（正反面）+1寸白底照片6张+体检表',1,'佛山报名流程.jpg',null,1,0,null,null,null,null);

-- -----------初始化数据 邮寄资料条目-----------------
INSERT INTO `t_enroll_material_items` VALUES (1, '身份证复印件：2份（正反面）', 'signup_id', 'signup_id', '身份证复印件：2份（正反面）', 0, NULL, NULL, NULL, '2016-1-18 14:25:03');
INSERT INTO `t_enroll_material_items` VALUES (2, '小一寸白底照片12张', 'signup_pic', 'signup_pic', '小一寸白底照片12张', 0, NULL, NULL, NULL, '2016-1-18 14:30:22');
INSERT INTO `t_enroll_material_items` VALUES (3, '数码照相回执原件1份', 'signup_pic', 'signup_pic', '数码照相回执原件1份', 0, NULL, NULL, NULL, '2016-1-18 14:29:22');
INSERT INTO `t_enroll_material_items` VALUES (4, '体检表', 'signup_physicaltable', 'signup_physicaltable', '体检表', 0, NULL, NULL, NULL, '2016-1-18 14:29:22');
INSERT INTO `t_enroll_material_items` VALUES (5, '驾校合同', 'signup_contract', 'signup_contract', '驾校合同', 0, NULL, NULL, NULL, '2016-1-18 14:29:22');
INSERT INTO `t_enroll_material_items` VALUES (6, '有效居住证复印件2份', 'signup_residencepermit', 'signup_residencepermit', '有效居住证复印件2份', 0, NULL, NULL, NULL, '2016-1-18 14:29:22');
INSERT INTO `t_enroll_material_items` VALUES (7, '回乡证复印件2份', 'signup_back', 'signup_back', '回乡证复印件2份', 0, NULL, NULL, NULL, '2016-1-18 14:29:22');
INSERT INTO `t_enroll_material_items` VALUES (8, '临时住宿登记表原件1份', 'signup_temporaryaddress', 'signup_temporaryaddress', '临时住宿登记表原件1份', 0, NULL, NULL, NULL, '2016-1-18 14:29:22');
INSERT INTO `t_enroll_material_items` VALUES (9, '军官证复印件', 'signup_officer', 'signup_officer', '军官证复印件', 0, NULL, NULL, NULL, '2016-1-18 14:29:22');
INSERT INTO `t_enroll_material_items` VALUES (10, '团级以上单位出具的申请人现实际驻地的住址证明', 'signup_address', 'signup_address', '团级以上单位出具的申请人现实际驻地的住址证明', 0, NULL, NULL, NULL, '2016-1-18 14:30:53');
INSERT INTO `t_enroll_material_items` VALUES (11, '团级以上医疗机构的出具的身体检查结果', 'signup_physicaltable', 'signup_physicaltable', '团级以上医疗机构的出具的身体检查结果', 0, NULL, NULL, NULL, '2016-1-18 14:31:24');
INSERT INTO `t_enroll_material_items` VALUES (12, '身份证复件4份（正反面）', 'signup_id', 'signup_id', '身份证复件4份（正反面）', 0, NULL, NULL, NULL, '2016-1-18 14:25:03');
INSERT INTO `t_enroll_material_items` VALUES (13, '驾驶证规格相片16张（含驾驶证相片回执原件及光碟1张）', 'signup_pic', 'signup_pic', '驾驶证规格相片16张（含驾驶证相片回执原件及光碟1张）', 0, NULL, NULL, NULL, '2016-1-18 14:32:44');
INSERT INTO `t_enroll_material_items` VALUES (14, '身份证原件', 'signup_id', 'signup_id', '身份证原件', 0, NULL, NULL, NULL, '2016-1-18 14:25:03');
INSERT INTO `t_enroll_material_items` VALUES (15, '小一寸彩色白底免冠照14张', 'signup_pic', 'signup_pic', '小一寸彩色白底免冠照14张', 0, NULL, NULL, NULL, '2016-1-18 14:31:56');
INSERT INTO `t_enroll_material_items` VALUES (16, '小一寸彩色白底免冠16张', 'signup_pic', 'signup_pic', '小一寸彩色白底免冠16张', 0, NULL, NULL, NULL, '2016-1-18 14:31:56');

-- -----------初始化数据 邮寄地址-----------------
INSERT INTO `t_enroll_material_address` VALUES (1, 100100, 1, 1, '深圳市南山区高新中一道2号长园新材料港9栋10楼', '518000', '张敏琳', '4006666666', '', '', '');
INSERT INTO `t_enroll_material_address` VALUES (2, 100102, 2, 1, '广州市天河区岑村圣堂路2号C座306', '510000', '林先生', '13925207900', '', '', '');
INSERT INTO `t_enroll_material_address` VALUES (3, 100101, 3, 1, '东莞市东城区桑园银贵路2号广仁驾校一楼', '523122', '杨先生', '13922990445', '', '', '');
INSERT INTO `t_enroll_material_address` VALUES (4, 100104, 4, 1, '广州市天河区岑村圣堂路2号C座306', '510000', '林先生', '13925207900', '', '', '');

-- -----------初始化数据 邮寄条目分类-----------------
INSERT INTO `t_enroll_material` VALUES (1, 100100, 1, 1, '深圳居民', '1,2,3,4,5', 0, NULL, NULL, NULL, '2016-1-18 14:40:09');
INSERT INTO `t_enroll_material` VALUES (2, 100100, 1, 2, '非深圳居民', '1,2,3,4,6,5', 0, NULL, NULL, NULL, '2016-1-18 14:40:58');
INSERT INTO `t_enroll_material` VALUES (3, 100100, 1, 3, '港澳台同胞', '1,7,8,2,3,4,5', 0, NULL, NULL, NULL, '2016-1-18 14:41:25');
INSERT INTO `t_enroll_material` VALUES (4, 100100, 1, 4, '现役军人', '1,2,3,9,10,11,5', 0, NULL, NULL, NULL, '2016-1-18 14:42:07');
INSERT INTO `t_enroll_material` VALUES (5, 100102, 2, 1, '广州居民', '12,13,3,4,5', 0, NULL, NULL, NULL, '2016-1-18 14:56:34');
INSERT INTO `t_enroll_material` VALUES (6, 100102, 2, 2, '非广州居民', '12,13,3,4,6,5', 0, NULL, NULL, NULL, '2016-1-18 14:56:55');
INSERT INTO `t_enroll_material` VALUES (7, 100102, 2, 3, '港澳台同胞', '12,13,8,2,3,4,5', 0, NULL, NULL, NULL, '2016-1-18 14:57:02');
INSERT INTO `t_enroll_material` VALUES (8, 100102, 2, 4, '现役军人', '12,13,3,9,10,11,5', 0, NULL, NULL, NULL, '2016-1-18 14:57:12');
INSERT INTO `t_enroll_material` VALUES (9, 100101, 3, 1, '东莞居民', '14,1,15,3,4,5', 0, NULL, NULL, NULL, '2016-1-18 15:02:08');
INSERT INTO `t_enroll_material` VALUES (10, 100101, 3, 2, '非东莞居民', '14,1,15,3,4,6,5', 0, NULL, NULL, NULL, '2016-1-18 15:02:17');
INSERT INTO `t_enroll_material` VALUES (11, 100101, 3, 3, '港澳台同胞', '14,1,7,8,15,3,4,5', 0, NULL, NULL, NULL, '2016-1-18 15:02:22');
INSERT INTO `t_enroll_material` VALUES (12, 100101, 3, 4, '现役军人', '14,1,15,3,9,10,11,5', 0, NULL, NULL, NULL, '2016-1-18 15:02:28');
INSERT INTO `t_enroll_material` VALUES (13, 100104, 4, 1, '佛山居民', '1,16,3,4,5', 0, NULL, NULL, NULL, '2016-1-18 15:02:57');
INSERT INTO `t_enroll_material` VALUES (14, 100104, 4, 2, '非佛山居民', '1,16,3,4,6,5', 0, NULL, NULL, NULL, '2016-1-18 15:03:01');
INSERT INTO `t_enroll_material` VALUES (15, 100104, 4, 3, '港澳台同胞', '1,7,8,16,3,4,5', 0, NULL, NULL, NULL, '2016-1-18 15:03:14');
INSERT INTO `t_enroll_material` VALUES (16, 100104, 4, 4, '现役军人', '1,16,3,9,10,11,5', 0, NULL, NULL, NULL, '2016-1-18 15:03:22');

-- -----------初始化数据 考试科目地址-----------------
INSERT INTO `t_enroll_exam_place` VALUES (1, '车管所科目场地一', 100100, 113.86371508477795, 22.74801992629282, '芙蓉大道', '13888888888');
INSERT INTO `t_enroll_exam_place` VALUES (2, '车管所科目场地二', 100100, 113.86371508477795, 22.74801992629282, '芙蓉大道', '13888888888');



--  ------------- 相关字段更改 -----------------
ALTER TABLE t_u_student MODIFY COLUMN applyexam tinyint(4) DEFAULT '0' COMMENT '车管所报名阶段：0-未报名；1-报名；2-支付；3-填写个人信息；4-邮寄；5-等待报名结果；';
ALTER TABLE t_u_student ADD COLUMN applystate tinyint(4) DEFAULT '0' COMMENT '车管所报名阶段状态：0-未开始；1-已提交；100-已成功；101-已失败';
ALTER TABLE t_u_student ADD COLUMN applyorder varchar(32) COMMENT '报名订单号';
ALTER TABLE t_u_student ADD COLUMN applyttid int(11) COMMENT '报名套餐id';

-- -----------初始化数据 所有从驾校导入的学员，默认报名状态设置为报考已提交阶段“请您耐心等待报考结果”，下一个版本自主约考走通后设置状态为已成功即可-----------------
UPDATE t_u_student set applyexam =0, applystate =0,applyorder ='',applyttid = null WHERE isImport = 0;
UPDATE t_u_student set applyexam =5, applystate =1,applyorder ='',applyttid = 1 WHERE isImport = 1;














