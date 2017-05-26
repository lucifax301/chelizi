USE  db_lili;
ALTER TABLE db_lili.`t_s_school` CHANGE image image TEXT DEFAULT NULL COMMENT '驾校图片,"+"号分开';

ALTER TABLE db_lili.`t_s_school` ADD COLUMN phoneNum VARCHAR(32) DEFAULT NULL COMMENT '电话';
ALTER TABLE db_lili.`t_s_school` ADD COLUMN score DOUBLE DEFAULT NULL COMMENT '评分，总分5';
ALTER TABLE db_lili.`t_s_school` ADD COLUMN logo VARCHAR(128) DEFAULT NULL COMMENT '驾校logo';
ALTER TABLE db_lili.`t_s_school` ADD COLUMN icon VARCHAR(128) DEFAULT NULL COMMENT '驾校封面图';
ALTER TABLE db_lili.`t_s_school` ADD COLUMN trfieldCount INT(11) DEFAULT NULL COMMENT '训练场数量';
ALTER TABLE db_lili.`t_s_school` ADD COLUMN schoolInfo TEXT DEFAULT NULL COMMENT '驾校简介';
ALTER TABLE db_lili.`t_s_school` ADD COLUMN regInfo TEXT COMMENT '报名须知';
ALTER TABLE db_lili.`t_s_school` ADD COLUMN lge DECIMAL(10,6) DEFAULT NULL COMMENT '驾校经度';
ALTER TABLE db_lili.`t_s_school` ADD COLUMN lae DECIMAL(10,6) DEFAULT NULL COMMENT '驾校纬度';
ALTER TABLE db_lili.`t_s_school` ADD COLUMN orderNum INT(11) DEFAULT NULL COMMENT '报名人数';
ALTER TABLE db_lili.`t_s_school` ADD COLUMN extra VARCHAR(128) DEFAULT NULL COMMENT '存放最短训练场最近距离';
ALTER TABLE db_lili.`t_s_school` ADD COLUMN storeCount INT(11) DEFAULT NULL COMMENT '门店数量';
ALTER TABLE db_lili.`t_s_school` ADD COLUMN packageCount INT(11) DEFAULT NULL COMMENT '班别数量';
ALTER TABLE db_lili.`t_s_school` ADD COLUMN showState INT(2) DEFAULT '1'  COMMENT '是否在驾校点评中显示 1不显示 2显示';


UPDATE db_lili.`t_s_school` s,db_lili.`t_wechat_enroll_school` ws SET s.image=ws.image,s.coachCount=ws.coachCount,s.carCount=ws.carCount,s.address=ws.address,s.tell=ws.tell,s.score=ws.score,s.logo=ws.logo,s.icon=ws.icon,
s.trfieldCount=ws.trfieldCount,s.schoolInfo=ws.schoolInfo,s.regInfo=ws.regInfo,s.lge=ws.lge,s.lae=ws.lae,s.orderNum=ws.orderNum,s.extra=ws.extra,
s.storeCount=ws.storeCount,s.packageCount=ws.packageCount,s.showState=2 WHERE s.schoolId=ws.schoolId;

INSERT INTO `t_s_school` (`schoolId`, `cityId`, `name`, `score`, `logo`, `icon`, `image`, `coachCount`, `carCount`, `trfieldCount`, `schoolInfo`, `regInfo`, `lge`, `lae`, `orderNum`, `extra`, `tell`, `address`, `storeCount`, `packageCount`) VALUES('136','100100','西部驾校','8',NULL,'school_xibu_1.png','school_xibu_1.png+school_xibu_2.png','10','11','1','深圳市西部汽车驾驶员培训中心是经深圳市人民政府、深圳市交通局、深圳市交通警察支队联合批准后成立的专业从事机动车驾驶员培训机构。本公司师质雄厚、经验丰富，严谨的训练计划使受训者快识易懂，并坚持专车上门接送，就地训练，从而达到既节省时间又能学到高超的驾驶技术的目的，良好的信誉和优质的服务得到了社会各界及同行的肯定。\r\n特色服务专业化　 \r\n专业从事机动车驾驶员培训；所有教练车辆符合国家教学标准；\r\n全部教练员经广东省交警卫员总队认定资格（均具有教练证）专业机构研究驾驶培训行业发展趋势和发展策略。\r\n基地化 \r\n规范业务场点建设，为学员就近咨询、报名、学车提供方便。\r\n正规化 \r\n经政府部门批准成立，所有训练基地实行统一经营，统一管理，统一收费。','1、年龄：C1、C2牌18-70周岁\n2、身高：不限身高\n3、视力：C1、C2牌≧4.9（矫正）\n4、左下肢残缺允许报C2牌\n5、辨色力无红绿色弱/色盲',NULL,NULL,NULL,NULL,'0755-27512058','深圳市宝安区西乡街道学府路4号',NULL,NULL);
INSERT INTO `t_s_school` (`schoolId`, `cityId`, `name`, `score`, `logo`, `icon`, `image`, `coachCount`, `carCount`, `trfieldCount`, `schoolInfo`, `regInfo`, `lge`, `lae`, `orderNum`, `extra`, `tell`, `address`, `storeCount`, `packageCount`) VALUES('137','100100','深高技驾校','8',NULL,'school_shengaoji_1.png','school_shengaoji_1.png+school_shengaoji_2.png+school_shengaoji_3.png+school_shengaoji_4.png','14','15','3','深圳高级技工学校汽车驾驶员培训中心是经深圳市交通局、深圳市交警局批准于一九九五年设立，在深圳市工商局注册、直属深圳市劳动和社会保障局领导的专业驾驶培训机构。\r\n　　本中心拥有一批教学教学实践经验丰富，管理规范完善，并拥有一支由省公安厅交警总队考核出来的合格优秀教练员队伍担任教学，中心有固定的办公室教学、训练场地，配备有性能优良的全空调训练小汽车。中心服务标准，教练持《教练证》上岗，统一着装，言行举止文明礼貌，教学用车安全技术性能良好。严格履行《培训协议书》的内容及条款。教练员在教学过程中，严格按照规定的内容、时间教学，并通过认真、细致的讲解和示范，让学员理解并掌握教学内容。中心成立十年以来一直秉承认真负责、诚信经营、廉洁教学、安全第一的服务宗旨，为在校师生提供优质的驾驶技术培训服务，为社会培养了大批的合格驾驶员。\r\n自二○○四年九月新的考试方法实施以来本中心各科目考试合格率名列全市驾培行业前茅，教学质量、服务质量都得到了业务主管部门和广大学员的肯定。今后我们将一如既往，为广大师生提供更加优质、安全、便捷的驾驶技术培训服务。','1、年龄：C1、C2牌18-70周岁\n2、身高：不限身高\n3、视力：C1、C2牌≧4.9（矫正）\n4、左下肢残缺允许报C2牌\n5、辨色力无红绿色弱/色盲',NULL,NULL,NULL,NULL,'0755-82444162','深圳市福田区福强路1007号高级技校内',NULL,NULL);
UPDATE t_s_school SET showState=2 WHERE schoolId IN (136,137);