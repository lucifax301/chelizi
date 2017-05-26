-- 20160308
--  ------------- 相关字段更改 -----------------
ALTER TABLE t_u_student ADD COLUMN drExpires datetime DEFAULT NULL COMMENT '驾照有效期' after drType;
ALTER TABLE t_u_student ADD COLUMN drPhoto2  varchar(256) DEFAULT NULL COMMENT '驾照副本图片地址' after drPhoto;

-- 配合新的报名状态修改
alter table t_u_student modify column applystate int(4) DEFAULT '0' COMMENT '车管所报名阶段状态：0-未开始；1-已提交；100-已成功；101-已失败';
alter table t_u_student modify column applyexam int(4) DEFAULT '0' COMMENT '车管所报名阶段：0-未报名；1-报名；2-支付；3-填写个人信息；4-邮寄；5-等待报名结果；';


-- 更新学员进度模板，配合cms的修改
TRUNCATE table t_enroll_progress_template;
INSERT INTO `t_enroll_progress_template` VALUES 
(NULL, 1, 100100, 1, 1, 2, 1, 'progress_drivelicense', '报名', 'dpage_enrolllist', NULL, NULL, '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', 1, 0, 0, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 1, 1),
(NULL, 1, 100100, 2, 1, 3, 2, 'progress_drivelicense', '支付', 'step_2', NULL, NULL, '您有报名订单需要支付', '您已支付报考学费{1}元,驾照类别为{2}', '您成功已支付报考学费{1}元,驾照类别为{2}', '您的报名订单支付失败，请重新支付', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 2, 1),
(NULL, 1, 100100, 3, 1, 4, 3, 'progress_messages', NULL, 'step_3', NULL, NULL, '填写个人资料', '您已经填写基本信息', '您已经填写基本信息', NULL, 1, 1, 1, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 3, 1),
(NULL, 1, 100100, 4, 1, 5, 4, 'progress_messages', NULL, 'step_4', NULL, NULL, '{1}你好,需要您线下邮寄资料给我们，具体资料点击查看详情', '您的资料已邮寄，请等待车管所反馈', '您已邮寄相关资料到车管所', NULL, 1, 1, 1, 0, NULL, '您的资料已收到', NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 4, 1),
(NULL, 1, 100100, 5, 1, 6, 5, 'progress_drivelicense', NULL, 'step_5', NULL, NULL, '资料审核', '未收资料', '资料齐全', '资料不全', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 1, 100100, 6, 1, 7, 6, 'progress_drivelicense', NULL, NULL, NULL, NULL, '表准备邮寄', '表已寄出至驾校', '驾校已收表', '', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 1, 100100, 7, 1, 101, 1, 'progress_drivelicense', NULL, NULL, NULL, NULL, '已交表至车管所', '车管所受理中', '报名成功', '报名失败', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 1, 100100, 101, 1, 201, 101, 'progress_drivelicense', NULL, NULL, NULL, NULL, '等待预约理论课', '约课中', '已约理论课', '缺理论课', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 1, 100100, 201, 1, 301, 201, 'progress_drivelicense', NULL, NULL, NULL, NULL, '未模拟考试', '', '模拟考试达标', '模拟考试未达标', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 1, 100100, 301, 1, 302, 301, 'progress_subjectone', '科目一', NULL, NULL, NULL, '科一约考排队中', '', '科一排队成功', '科一约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 1, 100100, 302, 1, 401, 301, 'progress_subjectone', NULL, NULL, NULL, NULL, '已约考科一', '', '科一合格', '科一不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 1, 100100, 401, 1, 402, 401, 'progress_subjecttwo', '科目二', NULL, NULL, NULL, '科二约考排队中', '', '科二排队成功', '科二约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 1, 100100, 402, 1, 501, 401, 'progress_subjecttwo', NULL, NULL, NULL, NULL, '已约考科二', '', '科二合格', '科二不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 1, 100100, 501, 1, 502, 501, 'progress_longtraining', '长训', NULL, NULL, NULL, '长训约考排队中', '', '长训排队成功', '长训约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 1, 100100, 502, 1, 601, 501, 'progress_longtraining', NULL, NULL, NULL, NULL, '已约考长训', '', '长训合格', '长训不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 1, 100100, 601, 1, 602, 601, 'progress_subjectthree', '科目三', NULL, NULL, NULL, '科三约考排队中', '', '科三排队成功', '科三约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 1, 100100, 602, 1, 701, 601, 'progress_subjectthree', NULL, NULL, NULL, NULL, '已约考科三', '', '科三合格', '科三不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 1, 100100, 701, 1, NULL, 701, 'progress_subjectfour', '科目四', NULL, NULL, NULL, '已约考科四', '', '已拿证', '科四不合格', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),

(NULL, 2, 100101, 1, 1, 2, 1, 'progress_drivelicense', '报名', 'dpage_enrolllist', NULL, NULL, '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', 1, 0, 0, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 1, 1),
(NULL, 2, 100101, 2, 1, 3, 2, 'progress_drivelicense', '支付', 'step_2', NULL, NULL, '您有报名订单需要支付', '您已支付报考学费{1}元,驾照类别为{2}', '您成功已支付报考学费{1}元,驾照类别为{2}', '您的报名订单支付失败，请重新支付', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 2, 1),
(NULL, 2, 100101, 3, 1, 4, 3, 'progress_messages', NULL, 'step_3', NULL, NULL, '填写个人资料', '您已经填写基本信息', '您已经填写基本信息', NULL, 1, 1, 1, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 3, 1),
(NULL, 2, 100101, 4, 1, 5, 4, 'progress_messages', NULL, 'step_4', NULL, NULL, '{1}你好,需要您线下邮寄资料给我们，具体资料点击查看详情', '您的资料已邮寄，请等待车管所反馈', '您已邮寄相关资料到车管所', NULL, 1, 1, 1, 0, NULL, '您的资料已收到', NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 4, 1),
(NULL, 2, 100101, 5, 1, 6, 5, 'progress_drivelicense', NULL, 'step_5', NULL, NULL, '资料审核', '未收资料', '资料齐全', '资料不全', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 2, 100101, 6, 1, 7, 6, 'progress_drivelicense', NULL, NULL, NULL, NULL, '表准备邮寄', '表已寄出至驾校', '驾校已收表', '', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 2, 100101, 7, 1, 101, 1, 'progress_drivelicense', NULL, NULL, NULL, NULL, '已交表至车管所', '车管所受理中', '报名成功', '报名失败', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 2, 100101, 101, 1, 201, 101, 'progress_drivelicense', NULL, NULL, NULL, NULL, '等待预约理论课', '约课中', '已约理论课', '缺理论课', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 2, 100101, 201, 1, 301, 201, 'progress_drivelicense', NULL, NULL, NULL, NULL, '未模拟考试', '', '模拟考试达标', '模拟考试未达标', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 2, 100101, 301, 1, 302, 301, 'progress_subjectone', '科目一', NULL, NULL, NULL, '科一约考排队中', '', '科一排队成功', '科一约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 2, 100101, 302, 1, 401, 301, 'progress_subjectone', NULL, NULL, NULL, NULL, '已约考科一', '', '科一合格', '科一不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 2, 100101, 401, 1, 402, 401, 'progress_subjecttwo', '科目二', NULL, NULL, NULL, '科二约考排队中', '', '科二排队成功', '科二约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 2, 100101, 402, 1, 501, 401, 'progress_subjecttwo', NULL, NULL, NULL, NULL, '已约考科二', '', '科二合格', '科二不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 2, 100101, 501, 1, 502, 501, 'progress_longtraining', '长训', NULL, NULL, NULL, '长训约考排队中', '', '长训排队成功', '长训约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 2, 100101, 502, 1, 601, 501, 'progress_longtraining', NULL, NULL, NULL, NULL, '已约考长训', '', '长训合格', '长训不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 2, 100101, 601, 1, 602, 601, 'progress_subjectthree', '科目三', NULL, NULL, NULL, '科三约考排队中', '', '科三排队成功', '科三约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 2, 100101, 602, 1, 701, 601, 'progress_subjectthree', NULL, NULL, NULL, NULL, '已约考科三', '', '科三合格', '科三不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 2, 100101, 701, 1, NULL, 701, 'progress_subjectfour', '科目四', NULL, NULL, NULL, '已约考科四', '', '已拿证', '科四不合格', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),

(NULL, 3, 100102, 1, 1, 2, 1, 'progress_drivelicense', '报名', 'dpage_enrolllist', NULL, NULL, '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', 1, 0, 0, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 1, 1),
(NULL, 3, 100102, 2, 1, 3, 2, 'progress_drivelicense', '支付', 'step_2', NULL, NULL, '您有报名订单需要支付', '您已支付报考学费{1}元,驾照类别为{2}', '您成功已支付报考学费{1}元,驾照类别为{2}', '您的报名订单支付失败，请重新支付', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 2, 1),
(NULL, 3, 100102, 3, 1, 4, 3, 'progress_messages', NULL, 'step_3', NULL, NULL, '填写个人资料', '您已经填写基本信息', '您已经填写基本信息', NULL, 1, 1, 1, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 3, 1),
(NULL, 3, 100102, 4, 1, 5, 4, 'progress_messages', NULL, 'step_4', NULL, NULL, '{1}你好,需要您线下邮寄资料给我们，具体资料点击查看详情', '您的资料已邮寄，请等待车管所反馈', '您已邮寄相关资料到车管所', NULL, 1, 1, 1, 0, NULL, '您的资料已收到', NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 4, 1),
(NULL, 3, 100102, 5, 1, 6, 5, 'progress_drivelicense', NULL, 'step_5', NULL, NULL, '资料审核', '未收资料', '资料齐全', '资料不全', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 3, 100102, 6, 1, 7, 6, 'progress_drivelicense', NULL, NULL, NULL, NULL, '表准备邮寄', '表已寄出至驾校', '驾校已收表', '', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 3, 100102, 7, 1, 101, 1, 'progress_drivelicense', NULL, NULL, NULL, NULL, '已交表至车管所', '车管所受理中', '报名成功', '报名失败', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 3, 100102, 101, 1, 201, 101, 'progress_drivelicense', NULL, NULL, NULL, NULL, '等待预约理论课', '约课中', '已约理论课', '缺理论课', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 3, 100102, 201, 1, 301, 201, 'progress_drivelicense', NULL, NULL, NULL, NULL, '未模拟考试', '', '模拟考试达标', '模拟考试未达标', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 3, 100102, 301, 1, 302, 301, 'progress_subjectone', '科目一', NULL, NULL, NULL, '科一约考排队中', '', '科一排队成功', '科一约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 3, 100102, 302, 1, 401, 301, 'progress_subjectone', NULL, NULL, NULL, NULL, '已约考科一', '', '科一合格', '科一不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 3, 100102, 401, 1, 402, 401, 'progress_subjecttwo', '科目二', NULL, NULL, NULL, '科二约考排队中', '', '科二排队成功', '科二约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 3, 100102, 402, 1, 501, 401, 'progress_subjecttwo', NULL, NULL, NULL, NULL, '已约考科二', '', '科二合格', '科二不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 3, 100102, 501, 1, 502, 501, 'progress_longtraining', '长训', NULL, NULL, NULL, '长训约考排队中', '', '长训排队成功', '长训约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 3, 100102, 502, 1, 601, 501, 'progress_longtraining', NULL, NULL, NULL, NULL, '已约考长训', '', '长训合格', '长训不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 3, 100102, 601, 1, 602, 601, 'progress_subjectthree', '科目三', NULL, NULL, NULL, '科三约考排队中', '', '科三排队成功', '科三约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 3, 100102, 602, 1, 701, 601, 'progress_subjectthree', NULL, NULL, NULL, NULL, '已约考科三', '', '科三合格', '科三不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 3, 100102, 701, 1, NULL, 701, 'progress_subjectfour', '科目四', NULL, NULL, NULL, '已约考科四', '', '已拿证', '科四不合格', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),

(NULL, 4, 100103, 1, 1, 2, 1, 'progress_drivelicense', '报名', 'dpage_enrolllist', NULL, NULL, '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', '开启您的驾考之旅', 1, 0, 0, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 1, 1),
(NULL, 4, 100103, 2, 1, 3, 2, 'progress_drivelicense', '支付', 'step_2', NULL, NULL, '您有报名订单需要支付', '您已支付报考学费{1}元,驾照类别为{2}', '您成功已支付报考学费{1}元,驾照类别为{2}', '您的报名订单支付失败，请重新支付', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 2, 1),
(NULL, 4, 100103, 3, 1, 4, 3, 'progress_messages', NULL, 'step_3', NULL, NULL, '填写个人资料', '您已经填写基本信息', '您已经填写基本信息', NULL, 1, 1, 1, 0, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 3, 1),
(NULL, 4, 100103, 4, 1, 5, 4, 'progress_messages', NULL, 'step_4', NULL, NULL, '{1}你好,需要您线下邮寄资料给我们，具体资料点击查看详情', '您的资料已邮寄，请等待车管所反馈', '您已邮寄相关资料到车管所', NULL, 1, 1, 1, 0, NULL, '您的资料已收到', NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 4, 1),
(NULL, 4, 100103, 5, 1, 6, 5, 'progress_drivelicense', NULL, 'step_5', NULL, NULL, '资料审核', '未收资料', '资料齐全', '资料不全', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 4, 100103, 6, 1, 7, 6, 'progress_drivelicense', NULL, NULL, NULL, NULL, '表准备邮寄', '表已寄出至驾校', '驾校已收表', '', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 4, 100103, 7, 1, 101, 1, 'progress_drivelicense', NULL, NULL, NULL, NULL, '已交表至车管所', '车管所受理中', '报名成功', '报名失败', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 4, 100103, 101, 1, 201, 101, 'progress_drivelicense', NULL, NULL, NULL, NULL, '等待预约理论课', '约课中', '已约理论课', '缺理论课', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 4, 100103, 201, 1, 301, 201, 'progress_drivelicense', NULL, NULL, NULL, NULL, '未模拟考试', '', '模拟考试达标', '模拟考试未达标', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 4, 100103, 301, 1, 302, 301, 'progress_subjectone', '科目一', NULL, NULL, NULL, '科一约考排队中', '', '科一排队成功', '科一约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 4, 100103, 302, 1, 401, 301, 'progress_subjectone', NULL, NULL, NULL, NULL, '已约考科一', '', '科一合格', '科一不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 4, 100103, 401, 1, 402, 401, 'progress_subjecttwo', '科目二', NULL, NULL, NULL, '科二约考排队中', '', '科二排队成功', '科二约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 4, 100103, 402, 1, 501, 401, 'progress_subjecttwo', NULL, NULL, NULL, NULL, '已约考科二', '', '科二合格', '科二不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 4, 100103, 501, 1, 502, 501, 'progress_longtraining', '长训', NULL, NULL, NULL, '长训约考排队中', '', '长训排队成功', '长训约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 4, 100103, 502, 1, 601, 501, 'progress_longtraining', NULL, NULL, NULL, NULL, '已约考长训', '', '长训合格', '长训不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 4, 100103, 601, 1, 602, 601, 'progress_subjectthree', '科目三', NULL, NULL, NULL, '科三约考排队中', '', '科三排队成功', '科三约考取消中', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 4, 100103, 602, 1, 701, 601, 'progress_subjectthree', NULL, NULL, NULL, NULL, '已约考科三', '', '科三合格', '科三不合格', 1, 1, 1, 1, NULL, NULL, NULL, 0, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1),
(NULL, 4, 100103, 701, 1, NULL, 701, 'progress_subjectfour', '科目四', NULL, NULL, NULL, '已约考科四', '', '已拿证', '科四不合格', 1, 1, 1, 1, NULL, NULL, NULL, 1, 0, 1, '1,2,3,4,5', '6,7,8', 5, 1);



-- 订单中冗余优惠劵
ALTER TABLE t_order ADD COLUMN coupon bigint COMMENT '使用的优惠券id';
ALTER TABLE t_order ADD COLUMN coupon_total int not null default 0 COMMENT '冗余：优惠券抵扣金额，单位分';
ALTER TABLE t_order ADD COLUMN coupon_name varchar(50) COMMENT '冗余：优惠券名称';

-- 订单中冗余课程类型，用于客户端判断是否需要弹出相关提示
ALTER TABLE t_order ADD COLUMN course_type VARCHAR(50) DEFAULT NULL COMMENT '冗余：课程类型，1代表需要验证驾照的课程';
-- 新课程配置数据，在原冗余字段中配置课程类型
ALTER TABLE t_s_coursenew MODIFY COLUMN `remark` varchar(255) DEFAULT NULL COMMENT '备注：1-该课程需要核准驾照；';
truncate t_s_coursenew;
INSERT INTO `t_s_coursenew` VALUES 
('E10001', '科目二基础训练', 1, '科目二', '基础训练', '科目二', 2, NULL, '刚完成科目一考试无科目二练习基础的学员', NULL, '6-8', 1, NULL, 17000, 19000, NULL, NULL),
('E10002', '科目二应试训练', 6, '科目二', '应试训练', '科目二', 2, NULL, '1、已进行不低于4小时的科目二基础训练；\r\n2、准备预约科目二考试的在学学员', NULL, '4-6', 1, NULL, 17000, 19000, NULL, NULL),
('E10003', '科目二考场模拟', 2, '科目二', '考场模拟', '科目二', 2, NULL, '1、已经预约成功科目二考试的学员；\r\n2、在2天内考试科目二的学员', NULL, '1-2', 1, NULL, 20000, 22000, NULL, NULL),
('E20001', '科目二基础训练', 11, '科目二', '基础训练', '科目二', 2, NULL, '刚完成科目一考试无科目二练习基础的学员', NULL, '6-8', 2, NULL, 18500, 20500, NULL, NULL),
('E20002', '科目二应试训练', 16, '科目二', '应试训练', '科目二', 2, NULL, '1、已进行不低于4小时的科目二基础训练；\r\n2、准备预约科目二考试的在学学员', NULL, '4-6', 2, NULL, 18500, 20500, NULL, NULL),
('E20003', '科目二考场模拟', 12, '科目二', '考场模拟', '科目二', 2, NULL, '1、已经预约成功科目二考试的学员；\r\n2、在2天内考试科目二的学员', NULL, '1-2', 2, NULL, 20500, 23500, NULL, NULL),
('S10001', '科目三基础训练', 3, '科目三', '基础训练', '科目三', 3, NULL, '刚完成科目二考试无科目三练习基础的学员', NULL, '2', 1, NULL, 17000, 19000, NULL, NULL),
('S10002', '科目三应试训练', 7, '科目三', '应试训练', '科目三', 3, NULL, '1、已进行不低于2小时的科目三基础训练；\r\n2、准备预约科目三考试的在学学员', NULL, '4-6', 1, NULL, 17000, 19000, NULL, NULL),
('S10003', '科目三考场模拟', 4, '科目三', '考场模拟', '科目三', 3, NULL, '1、已经预约成功科目三考试的学员；\r\n2、在2天内考试科目三的学员', NULL, '1-2', 1, NULL, 20000, 22000, NULL, NULL),
('S20001', '科目三基础训练', 13, '科目三', '基础训练', '科目三', 3, NULL, '刚完成科目二考试无科目三练习基础的学员', NULL, '2', 2, NULL, 18500, 20500, NULL, NULL),
('S20002', '科目三应试训练', 17, '科目三', '应试训练', '科目三', 3, NULL, '1、已进行不低于2小时的科目三基础训练；\r\n2、准备预约科目三考试的在学学员', NULL, '4-6', 2, NULL, 18500, 20500, NULL, NULL),
('S20003', '科目三考场模拟', 14, '科目三', '考场模拟', '科目三', 3, NULL, '1、已经预约成功科目三考试的学员；\r\n2、在2天内考试科目三的学员', NULL, '1-2', 2, NULL, 20500, 23500, NULL, NULL),
('P10001', '陪驾', 5, '陪驾', '陪驾', '陪驾', 5, NULL, '1、刚拿到驾照，跃跃欲试的新手党；\r\n2、反应能力、辨别方向能力、操控能力有点逊；\r\n3、回到家或出去玩停车停半天，关键还停不进去；', '1、哪里不会学哪里，哪里老错练哪里，强化训练，练会为止；\r\n2、跟车、超车、会车、变道、停车、倒车、弯道想学啥就学啥；', '4-6', 1, NULL, 17000, 17000, '1', NULL),
('P20001', '陪驾', 15, '陪驾', '陪驾', '陪驾', 5, NULL, '1、刚拿到驾照，跃跃欲试的新手党；\r\n2、反应能力、辨别方向能力、操控能力有点逊；\r\n3、回到家或出去玩停车停半天，关键还停不进去；', '1、哪里不会学哪里，哪里老错练哪里，强化训练，练会为止；\r\n2、跟车、超车、会车、变道、停车、倒车、弯道想学啥就学啥；', '4-6', 2, NULL, 20000, 20000, '1', NULL),
('T10001', '体验课程', 101, '体验课程', '体验课程', '体验课程', 9, NULL, '有意向学车未报名的潜在学员', NULL, '1', 1, NULL, 17000, 17000, NULL, NULL);



DROP TABLE IF EXISTS `t_channel`;
CREATE TABLE `t_channel` (
  `cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `suid` varchar(32) NOT NULL COMMENT '分享用户或用户类',
  `send_phone` varchar(20) DEFAULT NULL COMMENT '发送人的手机',
  `send_type` tinyint(4) NOT NULL COMMENT '发送人的用户类型：1代表教练，2代表学员，3管理后台用户,4校园代理，5推广渠道',
  `send_user` bigint(20) DEFAULT NULL COMMENT '发送分享人的id',
  `send_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '发送人状态，1未获得奖金，2已获得奖金，3已入账奖金',
  `send_time` datetime DEFAULT NULL COMMENT '支付给分享人时间',
  `send_total` int(11) DEFAULT NULL COMMENT '支付分享人金额，单位分',
  `recevie_name` varchar(20) DEFAULT NULL COMMENT '接收人姓名',
  `recevie_phone` varchar(20) NOT NULL COMMENT '接收人手机,不必须唯一，以最近一个作为有效',
  `recevie_user` bigint(20) DEFAULT NULL COMMENT '接人注册后的注册id',
  `recevie_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '接受状态：1已关联，2已注册，3已报名',
  `recevie_coupon` bigint(20) DEFAULT NULL COMMENT '接人人获得的优惠券',
  `reg_name` varchar(20) DEFAULT NULL COMMENT '注册人的名称',
  `reg_pic` varchar(256) DEFAULT NULL COMMENT '注册人的头像',
  `reg_type` tinyint(4) NOT NULL DEFAULT '2' COMMENT '注册用户类型：1代表教练，2代表学员',
  `check_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '审核状态：1待审核，2审核通过',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  `cuid` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `muid` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`cid`),
  UNIQUE KEY `cid` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='用户分享表';


DROP TABLE IF EXISTS `t_share`;
CREATE TABLE `t_share` (
  `sid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `suid` varchar(32) NOT NULL COMMENT '分享用户或用户类',
  `send_phone` varchar(20) DEFAULT NULL COMMENT '发送人的手机',
  `send_type` tinyint(4) NOT NULL COMMENT '发送人的用户类型：1代表教练，2代表学员，3管理后台用户,4校园代理，5推广渠道（数据写入专门的渠道表）',
  `send_user` bigint(20) DEFAULT NULL COMMENT '发送分享人的id',
  `send_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '发送人状态，1奖金未入账，2奖金已入账',
  `send_time` datetime DEFAULT NULL COMMENT '支付给分享人时间',
  `send_total` int(11) DEFAULT NULL COMMENT '支付分享人金额，单位分',
  `recevie_name` varchar(20) DEFAULT NULL COMMENT '接收人姓名',
  `recevie_phone` varchar(20) NOT NULL COMMENT '接收人手机,必须唯一',
  `recevie_user` bigint(20) DEFAULT NULL COMMENT '接人注册后的注册id',
  `recevie_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '接受状态：1已关联，2已注册，3已报名',
  `recevie_coupon` bigint(20) DEFAULT NULL COMMENT '接人人获得的优惠券',
  `reg_name` varchar(20) DEFAULT NULL COMMENT '注册人的名称',
  `reg_pic` varchar(256) DEFAULT NULL COMMENT '注册人头像',
  `reg_type` tinyint(4) DEFAULT NULL COMMENT '注册的用户类型：1代表教练，2代表学员',
  `check_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '审核状态：1待审核，2审核通过',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  `cuid` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `muid` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `recevie_template` varchar(32) DEFAULT NULL COMMENT '优惠券模板ID',
  PRIMARY KEY (`sid`),
  UNIQUE KEY `sid` (`sid`),
  UNIQUE KEY `recevie_phone` (`recevie_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='用户分享表';


DROP TABLE IF EXISTS `t_share_user`;
CREATE TABLE `t_share_user` (
  `suid` varchar(32) NOT NULL COMMENT '分享用户或用户类主键',
  `userName` varchar(20) NOT NULL COMMENT '用户名称',
  `send_total` int(11) NOT NULL COMMENT '分享用户获得金额,单位分',
  `recevie_template` varchar(32) DEFAULT NULL COMMENT '接人人获得的优惠券',
  `send_phone` varchar(20) DEFAULT NULL COMMENT '发送人的手机',
  `send_type` tinyint(4) NOT NULL COMMENT '发送人的用户类型：1代表教练，2代表学员，3管理后台用户,4校园代理，101推广渠道（大于100都插入渠道表）',
  `reg_type` tinyint(4) NOT NULL DEFAULT '2' COMMENT '被邀请人的用户类型：1代表教练，2代表学员',
  `send_user` bigint(20) DEFAULT NULL COMMENT '发送分享人的原始用户id,如果为空，则为这类人的规则',
  `shareUrl` varchar(256) DEFAULT NULL COMMENT '分享页面基础路径',
  `description` text COMMENT '分享简单文字描述',
  `rule` text COMMENT '活动规则',
  `bigpic` varchar(256) DEFAULT NULL COMMENT '分享大图路径',
  `smallpic` varchar(256) DEFAULT NULL COMMENT '分享小图路径',
  `check_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '审核状态：1待审核，2审核通过',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  `cuid` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `muid` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`suid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户分享表';


-- 优惠券相关
DROP TABLE IF EXISTS `t_s_ccondition`;

CREATE TABLE `t_s_ccondition` (
  `conditionId` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '优惠券条件Id(生成和使用规则)',
  `type` tinyint(4) NOT NULL COMMENT '条件类型',
  `param1` varchar(32) NOT NULL DEFAULT '0' COMMENT '附加参数1',
  `param2` varchar(32) NOT NULL DEFAULT '0' COMMENT '附加参数2',
  `descri` varchar(64) DEFAULT NULL COMMENT '条件的描述',
  PRIMARY KEY (`conditionId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `t_s_ccondition` */

insert  into `t_s_ccondition`(`conditionId`,`type`,`param1`,`param2`,`descri`) values (1,0,'2016-03-17 00:00:01','2016-04-19 23:59:01','在指定时间范围内'),(2,1,'深圳|东莞|广州|佛山','','在指定区域内'),(3,2,'1|2|3','','指定科目'),(4,2,'2|3|5','','指定科目'),(5,2,'4','','指定科目'),(6,2,'3','','指定科目'),(7,3,'1','10235e74e65c4a1696f69ebc5aa1cb20','限领1张'),(8,3,'1','017a4362c571404094a86de73b907986','限领1张'),(9,4,'1','','分享才能领取'),(10,2,'101','0','免费体验课'),(11,3,'8','1b78579cf90c42deb36ec944e0b89980','限领8张'),(12,0,'2016-03-01 01:01:00','2016-09-30 23:59:59','在指定时间范围内'),(13,3,'8','28f6f59bb9a94d39aad198fa9800f87f','限领8张'),(14,2,'3','0','科目三基础训练'),(15,0,'2016-03-01 01:01:00','2016-12-31 23:59:59','在指定时间范围内');

/*Table structure for table `t_s_coupon` */

DROP TABLE IF EXISTS `t_s_coupon`;

CREATE TABLE `t_s_coupon` (
  `couponTmpId` varchar(32) NOT NULL COMMENT '优惠券ID',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `listBackImg` varchar(32) DEFAULT NULL COMMENT '列表背景',
  `indepentUse` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否独立使用（1，是，0，不是）',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型（0，代金券，1课时券，2折扣券）',
  `validityPeriod` int(11) NOT NULL COMMENT '有效期(-1,无限久，单位小时)',
  `moneyValue` int(11) DEFAULT NULL COMMENT '抵扣的面额（单位：分）',
  `discount` int(11) NOT NULL COMMENT '折扣百分比（0,100）',
  `isExist` tinyint(4) DEFAULT NULL COMMENT '模板是否还生效',
  `icon` varchar(32) DEFAULT NULL COMMENT '优惠券图标',
  `useNote` varchar(256) DEFAULT NULL COMMENT '使用须知',
  `QRCodeUrl` varchar(64) DEFAULT NULL COMMENT '二维码图片地址',
  `verify` tinyint(4) DEFAULT NULL COMMENT '审核状态:0未审核，1已审核',
  `genRule` varchar(128) DEFAULT NULL COMMENT '获得规则（关系表达式）',
  `useRule` varchar(128) DEFAULT NULL COMMENT '使用规则（关系表达式）',
  `createUser` varchar(32) DEFAULT NULL COMMENT '模板创建者',
  `createTime` datetime DEFAULT NULL COMMENT '模板创建时间',
  `jpushMsg` varchar(128) DEFAULT NULL COMMENT '极光推送信息内容',
  `smsMsgType` int(11) DEFAULT NULL COMMENT '短信推送模板id',
  `limitValue` int(11) DEFAULT '0' COMMENT '最高抵扣多少',
  PRIMARY KEY (`couponTmpId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_s_coupon` */

insert  into `t_s_coupon`(`couponTmpId`,`name`,`listBackImg`,`indepentUse`,`type`,`validityPeriod`,`moneyValue`,`discount`,`isExist`,`icon`,`useNote`,`QRCodeUrl`,`verify`,`genRule`,`useRule`,`createUser`,`createTime`,`jpushMsg`,`smsMsgType`,`limitValue`) values ('017a4362c571404094a86de73b907986','免费学车券','',1,1,168,0,0,1,'','体验课程免费体验券|每人限领1张|每次仅限使用一张|仅限深圳、东莞地区使用|体验券使用时间：自领券起，7日内有效|体验券使用时长为40分钟','http://www.baidu.com',1,'(%s and %s and %s)|1,8,9','(%s and %s)|1,10','chubin.hu','2016-03-12 11:04:37','亲爱的用户，您的免费体验券已送达个人账户，请查看！',0,20500),('1b78579cf90c42deb36ec944e0b89980','科目二基础训练课程抵用券','',1,0,168,2500,0,0,'','每人限领8张|每次仅限使用一张|不可叠加使用|仅限深圳、东莞地区报名使用|抵用券使用时间：2016年3月1日00:00至2016年9月30日23:59','http://www.baidu.com',1,'(%s)|11','(%s and %s)|10,12','chubin.hu','2016-03-12 15:21:31','亲爱的用户，您的抵用券已送达个人账户，请查看！',0,100),('28f6f59bb9a94d39aad198fa9800f87f','科目二基础训练课程抵用券','',1,0,168,2500,0,0,'','每人限领8张|每次仅限使用一张|不可叠加使用|仅限深圳、东莞地区报名使用|抵用券使用时间：2016年3月1日00:00至2016年12月31日23:59','http://www.baidu.com',1,'(%s)|13','(%s and %s)|14,15','chubin.hu','2016-03-12 15:25:19','亲爱的用户，您的抵用券已送达个人账户，请查看！',0,100);

/*Table structure for table `t_s_cstock` */

DROP TABLE IF EXISTS `t_s_cstock`;

CREATE TABLE `t_s_cstock` (
  `stockId` int(11) NOT NULL AUTO_INCREMENT COMMENT '优惠券库存主键',
  `eventTopic` varchar(32) DEFAULT NULL COMMENT '监听的事件',
  `total` int(11) NOT NULL DEFAULT '0' COMMENT '总共有多少张',
  `haveUsed` int(11) DEFAULT '0' COMMENT '已使用多少张',
  `createTime` datetime DEFAULT NULL COMMENT '库存创建时间',
  `createUser` varchar(32) DEFAULT NULL COMMENT '库存创建者',
  `isExist` tinyint(4) DEFAULT NULL COMMENT '是否生效',
  `couponTempId` varchar(32) NOT NULL DEFAULT '0' COMMENT '优惠券模板主键',
  PRIMARY KEY (`stockId`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_s_cstock` */

insert  into `t_s_cstock`(`stockId`,`eventTopic`,`total`,`haveUsed`,`createTime`,`createUser`,`isExist`,`couponTempId`) values (1,'rmq_student_register_value',5000,27,'2016-03-12 11:06:58','chubin.hu',1,'017a4362c571404094a86de73b907986'),(2,'rmq_school_value',35000,1,'2016-03-12 15:21:52','chubin.hu',0,'1b78579cf90c42deb36ec944e0b89980'),(3,'rmq_school_value',35000,2,'2016-03-12 15:25:37','chubin.hu',0,'28f6f59bb9a94d39aad198fa9800f87f');

/*Table structure for table `t_u_coupon` */

DROP TABLE IF EXISTS `t_u_coupon`;

CREATE TABLE `t_u_coupon` (
  `couponId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '优惠券id',
  `studentId` bigint(20) NOT NULL COMMENT '学员id',
  `getTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '获得时间',
  `useTime` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '使用时间',
  `couponTmpId` varchar(32) NOT NULL COMMENT '优惠券的模板id',
  `stockId` int(11) DEFAULT NULL COMMENT '获取优惠券的库存id',
  `isExist` tinyint(4) NOT NULL COMMENT '是否有效',
  `expireTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '过期时间',
  `orderId` varchar(32) DEFAULT NULL COMMENT '订单id',
  PRIMARY KEY (`couponId`),
  KEY `studentId` (`studentId`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000075 DEFAULT CHARSET=utf8;






update t_u_bankcard_verify set bank_name='东莞银行' where bank_name='东莞市商业银行';



INSERT INTO `t_share_user` VALUES ('8ed74780e5c811e58b9b0862662c4296', '教练分享', 15000, NULL, NULL, 1, 2, NULL, 'http://www.lilixc.com/appPro/share/recommend.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-14 16:51:21');
INSERT INTO `t_share_user` VALUES ('be7700c8e5ca11e58b9b0862662c4296', 'XX渠道推广', 100, NULL, NULL, 101, 2, NULL, 'http://www.lilixc.com/weixin/extension/extensio-03.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-14 18:05:56');
INSERT INTO `t_share_user` VALUES ('986709e1eb5811e5bc985254006ae3ee', '兼职达人统筹', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('f2469dcfeb5911e5bc985254006ae3ee', '兼职达人兼职01号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('f9fc5815eb5911e5bc985254006ae3ee', '兼职达人兼职02号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('00bc960aeb5a11e5bc985254006ae3ee', '兼职达人兼职03号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('05bdc254eb5a11e5bc985254006ae3ee', '兼职达人兼职04号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('1c09ffc4eb5a11e5bc985254006ae3ee', '兼职达人兼职05号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('1c107231eb5a11e5bc985254006ae3ee', '兼职达人兼职06号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('1c17891deb5a11e5bc985254006ae3ee', '兼职达人兼职07号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('1c1e712feb5a11e5bc985254006ae3ee', '兼职达人兼职08号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('1c25aea3eb5a11e5bc985254006ae3ee', '兼职达人兼职09号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('1c2cdcf2eb5a11e5bc985254006ae3ee', '兼职达人兼职10号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('1c35aa95eb5a11e5bc985254006ae3ee', '兼职达人兼职11号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('1c3d76ddeb5a11e5bc985254006ae3ee', '兼职达人兼职12号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('1c44f554eb5a11e5bc985254006ae3ee', '兼职达人兼职13号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('1c4dcf55eb5a11e5bc985254006ae3ee', '兼职达人兼职14号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('73b93d9eeb5a11e5bc985254006ae3ee', '兼职达人兼职15号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('73bfb296eb5a11e5bc985254006ae3ee', '兼职达人兼职16号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('73c6b7f7eb5a11e5bc985254006ae3ee', '兼职达人兼职17号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('73cdcc45eb5a11e5bc985254006ae3ee', '兼职达人兼职18号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('73d52292eb5a11e5bc985254006ae3ee', '兼职达人兼职19号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('73dc4a43eb5a11e5bc985254006ae3ee', '兼职达人兼职20号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('73e35065eb5a11e5bc985254006ae3ee', '兼职达人兼职21号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('73ed14e6eb5a11e5bc985254006ae3ee', '兼职达人兼职22号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('73f5258eeb5a11e5bc985254006ae3ee', '兼职达人兼职23号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('73ff19cdeb5a11e5bc985254006ae3ee', '兼职达人兼职24号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('c5872636eb5a11e5bc985254006ae3ee', '兼职达人兼职25号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('c58dfb73eb5a11e5bc985254006ae3ee', '兼职达人兼职26号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('c5951415eb5a11e5bc985254006ae3ee', '兼职达人兼职27号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('c59c3988eb5a11e5bc985254006ae3ee', '兼职达人兼职28号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('c5a41377eb5a11e5bc985254006ae3ee', '兼职达人兼职29号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('c5ab6e7eeb5a11e5bc985254006ae3ee', '兼职达人兼职30号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('c5b2a497eb5a11e5bc985254006ae3ee', '兼职达人兼职31号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('c5b9af46eb5a11e5bc985254006ae3ee', '兼职达人兼职32号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('c5c0c528eb5a11e5bc985254006ae3ee', '兼职达人兼职33号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('c5c8366feb5a11e5bc985254006ae3ee', '兼职达人兼职34号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('e70471bfeb5a11e5bc985254006ae3ee', '兼职达人兼职35号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('e70b6141eb5a11e5bc985254006ae3ee', '兼职达人兼职36号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('e712aebfeb5a11e5bc985254006ae3ee', '兼职达人兼职37号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('e719d6f4eb5a11e5bc985254006ae3ee', '兼职达人兼职38号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('e72119aeeb5a11e5bc985254006ae3ee', '兼职达人兼职39号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('e7286286eb5a11e5bc985254006ae3ee', '兼职达人兼职40号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('e72fb167eb5a11e5bc985254006ae3ee', '兼职达人兼职41号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('e736e38deb5a11e5bc985254006ae3ee', '兼职达人兼职42号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('e73e137beb5a11e5bc985254006ae3ee', '兼职达人兼职43号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('e7456f06eb5a11e5bc985254006ae3ee', '兼职达人兼职44号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('073ff8daeb5b11e5bc985254006ae3ee', '兼职达人兼职45号', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('9ceffeceec0911e58b9b0862662c4296', '运营人员推广专用', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-16 18:05:56');
INSERT INTO `t_share_user` VALUES ('8dfabf16ecd711e5b2a80862662c4296', '东莞渠道推广', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/weixin/extension/extensio-03.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 2, 0, NULL, NULL, NULL, '2016-3-14 18:05:56');


INSERT INTO `t_share_user` VALUES ('LIECEIZE', '深信校园代理1', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('AXKTGFBI', '深信校园代理2', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('KGSVZVNK', '深信校园代理3', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('JSQDYSSM', '深信校园代理4', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('UOASSIQM', '深信校园代理5', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('UFTCAXTE', '深信校园代理6', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('QYXJOOPP', '深信校园代理7', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('FWOQZTLA', '深信校园代理8', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('IMSBKAQC', '深信校园代理9', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('XDJBESSU', '深信校园代理10', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('NRVSFTKK', '深信校园代理11', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('RFNPVRIV', '深信校园代理12', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('LCKHNHJE', '深信校园代理13', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('NWONUBTN', '深信校园代理14', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('FEGQRCWT', '深信校园代理15', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('DHYVDAMG', '深信校园代理16', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('QXBABSJX', '深信校园代理17', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('WTLBXEWG', '深信校园代理18', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('OOXEVCLJ', '深信校园代理19', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('DDUDCDTW', '深信校园代理20', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('VIXBPXVT', '深信校园代理21', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('GBPMBYPN', '深信校园代理22', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('SCTHZEEY', '深信校园代理23', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('PMPDLHZM', '深信校园代理24', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('DENDRGWO', '深信校园代理25', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('JIFLRHIU', '深信校园代理26', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('HUZPXZEY', '深信校园代理27', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('JDDRHOVT', '深信校园代理28', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('GQEIMUOW', '深信校园代理29', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('FTFSOSOZ', '深信校园代理30', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('ILBDUVHK', '深信校园代理31', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('BBJUHZAE', '深信校园代理32', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('IQCJTTPL', '深信校园代理33', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('SLHRBMUO', '深信校园代理34', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('FKPGWOLA', '深信校园代理35', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('DGEPGUYJ', '深信校园代理36', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('QLIBVDQS', '深信校园代理37', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('JAMZSQIM', '深信校园代理38', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('NRMSROHA', '深信校园代理39', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('HLYDURPS', '深信校园代理40', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('EFKDZGTK', '深信校园代理41', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('RZTRJXYD', '深信校园代理42', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('RNPAEBYC', '深信校园代理43', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('RTKHSXUP', '深信校园代理44', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('LMWIKISV', '深信校园代理45', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('CWQUHDZL', '深信校园代理46', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('PCKJRBSM', '深信校园代理47', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('WEPFQAPY', '深信校园代理48', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('EOXIHPWP', '深信校园代理49', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('HAHHRZWX', '深信校园代理50', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('YUNHJSPC', '深信校园代理51', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('ORXDVBRS', '深信校园代理52', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('BZOFDFCO', '深信校园代理53', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('UNAINFPD', '深信校园代理54', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('RYRLOCDU', '深信校园代理55', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('AOAQZXSA', '深信校园代理56', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('MUGEPNYM', '深信校园代理57', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('KNLWXPOO', '深信校园代理58', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('MPZKHETR', '深信校园代理59', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('MYPEHKWN', '深信校园代理60', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('LZHXAVYO', '深信校园代理61', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('FFKOAFCC', '深信校园代理62', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('TFRQKUOU', '深信校园代理63', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('PZXNAKWZ', '深信校园代理64', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('JTXGBRZG', '深信校园代理65', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('GZQYFTDR', '深信校园代理66', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('YCKGQOGA', '深信校园代理67', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('JLQVFWSC', '深信校园代理68', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('TTGPAGLY', '深信校园代理69', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('PYALTGPX', '深信校园代理70', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('HQHRYJZZ', '深信校园代理71', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('GUJRXMHI', '深信校园代理72', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('GFOVITOV', '深信校园代理73', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('MMEHIOPK', '深信校园代理74', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('SYKPPNOD', '深信校园代理75', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('OXBGTOCK', '深信校园代理76', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('JEZLTEZZ', '深信校园代理77', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('ZFOZFNZV', '深信校园代理78', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('VNLJQSQG', '深信校园代理79', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('XYMHSYMJ', '深信校园代理80', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('KXSYOQBJ', '深信校园代理81', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('CKQWSTAQ', '深信校园代理82', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('WTZUEOXS', '深信校园代理83', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('BIQCVYGU', '深信校园代理84', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('NNTCXTAX', '深信校园代理85', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('JDAOUSFI', '深信校园代理86', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('XIRLAFTO', '深信校园代理87', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('YOWWRGPV', '深信校园代理88', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('IHJSFYAF', '深信校园代理89', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('CZOTRFAX', '深信校园代理90', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('HMXOBRIF', '深信校园代理91', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('QFFWXNTQ', '深信校园代理92', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('GWVRGZYC', '深信校园代理93', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('PENNWAPX', '深信校园代理94', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('KGYEKKTE', '深信校园代理95', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('HPJSUXWX', '深信校园代理96', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('FTXRIJHD', '深信校园代理97', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('WRUUOVEL', '深信校园代理98', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('VHZQLLRZ', '深信校园代理99', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('MVXXDUQP', '深信校园代理100', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('QQUXYXXH', '深信校园代理101', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('JPTEKQGH', '深信校园代理102', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('QZWCJCWI', '深信校园代理103', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('CSCMRYXK', '深信校园代理104', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('RQSITZED', '深信校园代理105', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('NEUEHPSJ', '深信校园代理106', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('UGXROTOF', '深信校园代理107', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('PRWAWTBC', '深信校园代理108', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('ZGZIUNUI', '深信校园代理109', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('GFMUKQAZ', '深信校园代理110', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('JZBQZOOJ', '深信校园代理111', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('QJTFRWKG', '深信校园代理112', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('WXPSCBKT', '深信校园代理113', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('LXCKMAGE', '深信校园代理114', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('FWJFYTEF', '深信校园代理115', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('YDIPNNTM', '深信校园代理116', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('ISMZQRHA', '深信校园代理117', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('WZXFKTJS', '深信校园代理118', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('QSTTMZSK', '深信校园代理119', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('BJGWRKWC', '深信校园代理120', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('LXEXPMBF', '深信校园代理121', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('GIJCJRJR', '深信校园代理122', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('CQCVLWZU', '深信校园代理123', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('WULCTXPG', '深信校园代理124', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('VTAFXCIF', '深信校园代理125', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('TFABWQYO', '深信校园代理126', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('XIQTZGUT', '深信校园代理127', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('PGFWGETL', '深信校园代理128', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('MNLCGROT', '深信校园代理129', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('YLDFGAIV', '深信校园代理130', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('RHOYVDRP', '深信校园代理131', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('APZZSOZY', '深信校园代理132', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('IJLWHEQD', '深信校园代理133', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('SWRZTMRL', '深信校园代理134', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('OOBHVEIY', '深信校园代理135', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('XNIFGVVU', '深信校园代理136', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('TWUUHLBA', '深信校园代理137', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('PBSYAORE', '深信校园代理138', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('CMRFPBCL', '深信校园代理139', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('QZXVPSFL', '深信校园代理140', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('XYGLDLPX', '深信校园代理141', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('XHCQFSGM', '深信校园代理142', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('JSLIALNX', '深信校园代理143', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('ZOQJUEXX', '深信校园代理144', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('GONDFPTV', '深信校园代理145', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('GWAUBGOX', '深信校园代理146', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('ZNNBDJOE', '深信校园代理147', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('GNBMSEMS', '深信校园代理148', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('QIFXKHRS', '深信校园代理149', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('OTPDIQHX', '深信校园代理150', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('WITBSHCK', '深信校园代理151', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('RBPBIDNR', '深信校园代理152', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('XDPYXJBY', '深信校园代理153', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('VYMFJXXP', '深信校园代理154', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('WZIMLBVT', '深信校园代理155', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('WZXTFLML', '深信校园代理156', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('MYUHNQMP', '深信校园代理157', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('XVLNSFQJ', '深信校园代理158', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('CHNPXCBP', '深信校园代理159', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('JSNRTRDF', '深信校园代理160', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('LTANWCWW', '深信校园代理161', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('JLYKVEZA', '深信校园代理162', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('APAXKYGT', '深信校园代理163', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('KGHBPBFH', '深信校园代理164', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('BXXAYESZ', '深信校园代理165', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('IPEWXEEO', '深信校园代理166', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('HHYKCRLD', '深信校园代理167', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('DGFPPBQT', '深信校园代理168', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('YZJSWNSS', '深信校园代理169', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('AJAGZXHH', '深信校园代理170', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('NESVUFMH', '深信校园代理171', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('VRJWOESZ', '深信校园代理172', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('LOKERYQG', '深信校园代理173', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('KHGBUSRM', '深信校园代理174', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('NOEDIINI', '深信校园代理175', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('EEHSOYRF', '深信校园代理176', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('YHCRAEPF', '深信校园代理177', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('LFYNRWBZ', '深信校园代理178', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('PQNUVGKU', '深信校园代理179', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('HSSJGWNG', '深信校园代理180', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('LSYAYLWN', '深信校园代理181', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('XWKRZVLY', '深信校园代理182', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('LGDDTILG', '深信校园代理183', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('CTXPKVSH', '深信校园代理184', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('SCZETMJQ', '深信校园代理185', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('VDCMHYXD', '深信校园代理186', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('ZWOXKUAC', '深信校园代理187', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('IEDTTWAF', '深信校园代理188', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('SALXLPYX', '深信校园代理189', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('ROUBVCVO', '深信校园代理190', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('TFGMRWSW', '深信校园代理191', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('FCEQZCFO', '深信校园代理192', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('MRUHTECJ', '深信校园代理193', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('XPLMFRQA', '深信校园代理194', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('FDRBAOXE', '深信校园代理195', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('MGGLRHLA', '深信校园代理196', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('WWPLUPVT', '深信校园代理197', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('ZWKQYKLE', '深信校园代理198', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('PUPZVWJO', '深信校园代理199', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('ILPKGWSP', '深信校园代理200', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('SELPRWSP', '深信校园代理201', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('PMJCBQOQ', '深信校园代理202', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('BJJANLWT', '深信校园代理203', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('NFGQBZMM', '深信校园代理204', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('RODPMIDJ', '深信校园代理205', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('DIGYXTPM', '深信校园代理206', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('ZXIVLJBS', '深信校园代理207', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('DBGMJMUU', '深信校园代理208', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('MOTTUVSD', '深信校园代理209', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('HIZNEHZW', '深信校园代理210', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('JCIBCUND', '深信校园代理211', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('IEQELXYI', '深信校园代理212', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('TJTQNDSQ', '深信校园代理213', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('BEGOJXHS', '深信校园代理214', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('XODRBYOQ', '深信校园代理215', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('WZEFWSOK', '深信校园代理216', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('DEGFSCJH', '深信校园代理217', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('FRFTVERV', '深信校园代理218', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('GIFVGJPT', '深信校园代理219', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('VDKORORO', '深信校园代理220', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('NJHPBNOP', '深信校园代理221', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('LNDPHAXM', '深信校园代理222', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('UGMIJOKQ', '深信校园代理223', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('ZAGZFVCG', '深信校园代理224', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('TJWAXUEY', '深信校园代理225', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('ZYRHNIZI', '深信校园代理226', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('AWQHBPJE', '深信校园代理227', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('XDQDCJZT', '深信校园代理228', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('GARTREHG', '深信校园代理229', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('FUTPXWIT', '深信校园代理230', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('GVMMMMZX', '深信校园代理231', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('KQERVQSH', '深信校园代理232', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('SOLLAQFR', '深信校园代理233', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('TADSAPZL', '深信校园代理234', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('NNJWWNRC', '深信校园代理235', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('SVVBQOCG', '深信校园代理236', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('MIDSDRYN', '深信校园代理237', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('OEINJNAS', '深信校园代理238', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('NBFYEQNL', '深信校园代理239', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('AWYMOQPP', '深信校园代理240', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('SFPRLUFL', '深信校园代理241', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('GBHGHBRV', '深信校园代理242', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('YNEIOMPO', '深信校园代理243', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('WHBPHGTC', '深信校园代理244', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('RFIZBXDC', '深信校园代理245', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('IINOZQXV', '深信校园代理246', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('WGNRLASP', '深信校园代理247', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('RDCKWOJJ', '深信校园代理248', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('JLRUQZUU', '深信校园代理249', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('WIEQTGCX', '深信校园代理250', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('TPXEIEWO', '深信校园代理251', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('RCIOCYGE', '深信校园代理252', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('UWWBFQQH', '深信校园代理253', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('JACYTFWD', '深信校园代理254', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('BQVRQLPD', '深信校园代理255', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('XNUCKIQY', '深信校园代理256', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('ENOBSUAT', '深信校园代理257', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('BKMZKKWF', '深信校园代理258', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('EEHZVGKW', '深信校园代理259', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('LJJLWPTC', '深信校园代理260', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('ZTJFSHWS', '深信校园代理261', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('OPJWTPBD', '深信校园代理262', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('OSFPNTAE', '深信校园代理263', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('DOPPOVGT', '深信校园代理264', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('RDLXPGGX', '深信校园代理265', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('IYHIVPJF', '深信校园代理266', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('TVMKLXBY', '深信校园代理267', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('HRUFLJVX', '深信校园代理268', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('KNKFIPVU', '深信校园代理269', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('OOKMEVAQ', '深信校园代理270', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('NOXAKVJO', '深信校园代理271', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('PGZSWQRA', '深信校园代理272', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('CCQXFOAL', '深信校园代理273', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('FSVIRMNT', '深信校园代理274', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('EGCCPLDI', '深信校园代理275', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('FNBQMPWA', '深信校园代理276', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('ELPMZPDL', '深信校园代理277', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('NMHOPTHO', '深信校园代理278', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('KEVHDFPN', '深信校园代理279', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('RBOICYEE', '深信校园代理280', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('CFISOGKQ', '深信校园代理281', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('AJHPVYCN', '深信校园代理282', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('DYPZSXXI', '深信校园代理283', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('ZHEXECSX', '深信校园代理284', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('ZZPHXSSK', '深信校园代理285', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('BUHSZECU', '深信校园代理286', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('MZPCWXIJ', '深信校园代理287', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('XXIBWXOE', '深信校园代理288', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('TQLYCSNU', '深信校园代理289', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('XCXIUVYD', '深信校园代理290', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('VQUXCAJP', '深信校园代理291', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('BUPPNAXB', '深信校园代理292', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('NITQYIAO', '深信校园代理293', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('GQSOSNUP', '深信校园代理294', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('XDKYBGKT', '深信校园代理295', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('AFFKKGYK', '深信校园代理296', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('NAXKYBFW', '深信校园代理297', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('THNOMUUA', '深信校园代理298', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('KVDFGJLG', '深信校园代理299', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('YTPMUJLP', '深信校园代理300', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('SRWRJJKJ', '深信校园代理301', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('BVISPIRI', '深信校园代理302', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('TYQIBHLS', '深信校园代理303', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('YPWQPGEG', '深信校园代理304', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('HLNNUXGF', '深信校园代理305', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('CHTFUGCY', '深信校园代理306', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('AJMVNNKD', '深信校园代理307', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('EIEXICFQ', '深信校园代理308', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('GUYMEFWA', '深信校园代理309', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('XKUIMKTC', '深信校园代理310', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('RYKPQHFZ', '深信校园代理311', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('ZOROGBPU', '深信校园代理312', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('QLHHSUKN', '深信校园代理313', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('MBHQBMIM', '深信校园代理314', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('MLSUQVWP', '深信校园代理315', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('VKKNWVZH', '深信校园代理316', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('MUPPTDQK', '深信校园代理317', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('VVSKBWPR', '深信校园代理318', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('EVKSYJDP', '深信校园代理319', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('HDUXQWHT', '深信校园代理320', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('CSRVGPHB', '深信校园代理321', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('RYSKVWHW', '深信校园代理322', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('YESOSNTV', '深信校园代理323', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('UUVWOAFO', '深信校园代理324', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('SWTCPNTF', '深信校园代理325', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('SLEDNXJE', '深信校园代理326', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('FADPKWNG', '深信校园代理327', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('CUKNJYOU', '深信校园代理328', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('EYWAHJNE', '深信校园代理329', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('XNWGFJXB', '深信校园代理330', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('EQQRXJZM', '深信校园代理331', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('HSUNRSMQ', '深信校园代理332', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('MPUMQODC', '深信校园代理333', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('YRQNEIBI', '深信校园代理334', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('RHGZMWNX', '深信校园代理335', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('VINGMMFV', '深信校园代理336', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('HRLTHULB', '深信校园代理337', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('JYUPASYL', '深信校园代理338', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('QRMCGMEM', '深信校园代理339', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('EJTQUIKW', '深信校园代理340', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('YWDNIHKA', '深信校园代理341', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('AMKBSLZF', '深信校园代理342', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('JGCCCRYP', '深信校园代理343', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('YUZRJTQT', '深信校园代理344', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('TISSMPXP', '深信校园代理345', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('LCUNNEKW', '深信校园代理346', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('HNSGGSBU', '深信校园代理347', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('BNIZEZPA', '深信校园代理348', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('FXEPEYOO', '深信校园代理349', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('QWVKCLTU', '深信校园代理350', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('DYOFMRKA', '深信校园代理351', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('RMASBLDL', '深信校园代理352', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('VQFCBPBU', '深信校园代理353', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('SUQXHIAW', '深信校园代理354', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('DXHSZTCH', '深信校园代理355', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('OKMKQYRV', '深信校园代理356', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('ZFZTVWNN', '深信校园代理357', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('BMDAGXIO', '深信校园代理358', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('OXMINZXL', '深信校园代理359', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('JJDPYTTY', '深信校园代理360', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('UBUOOLYB', '深信校园代理361', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('IGSKAEOW', '深信校园代理362', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('OUGZIVFO', '深信校园代理363', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('LYPHHZGC', '深信校园代理364', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('KBJTXWEB', '深信校园代理365', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('LDOKQJCO', '深信校园代理366', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('HFXFVJRP', '深信校园代理367', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('CASRAAFY', '深信校园代理368', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('LCUQNLSF', '深信校园代理369', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('MNUSLFYZ', '深信校园代理370', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('JANHOUHK', '深信校园代理371', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('DFZJNPFI', '深信校园代理372', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('FDCBMTXX', '深信校园代理373', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('WVHRMISH', '深信校园代理374', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('GOIYSWPT', '深信校园代理375', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('EANPBSBO', '深信校园代理376', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('ESSKQKNE', '深信校园代理377', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('ZGXSKSVE', '深信校园代理378', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('LEODSZLL', '深信校园代理379', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('EWEUYFSM', '深信校园代理380', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('AMUIOOCG', '深信校园代理381', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('EKCSSSKH', '深信校园代理382', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('OCHJTNBU', '深信校园代理383', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('JOCHDUDZ', '深信校园代理384', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('LAMVRHPH', '深信校园代理385', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('OOBTARFC', '深信校园代理386', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('SFKWJUNP', '深信校园代理387', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('SZMQREVG', '深信校园代理388', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('DHHVWFQJ', '深信校园代理389', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('HMZRSBZN', '深信校园代理390', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('KRTNIOBI', '深信校园代理391', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('HSJIRHHY', '深信校园代理392', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('OEOJFOPZ', '深信校园代理393', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('VEGINXYP', '深信校园代理394', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('CMVZDEFM', '深信校园代理395', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('AWSYEZRF', '深信校园代理396', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('AVBKFZPZ', '深信校园代理397', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('WMGLVLWO', '深信校园代理398', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('DAFUNAXY', '深信校园代理399', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('NTLTXUGJ', '深信校园代理400', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('PTJEECPH', '深信校园代理401', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('QKGGDJWQ', '深信校园代理402', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('PRDYTSFP', '深信校园代理403', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('UMMIVUBK', '深信校园代理404', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('DZLQJPVG', '深信校园代理405', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('VVVNNFWU', '深信校园代理406', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('SGQYEIJN', '深信校园代理407', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('LOKDXEZL', '深信校园代理408', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('YULBUDTA', '深信校园代理409', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('FRKNWUTA', '深信校园代理410', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('GXLSGKOQ', '深信校园代理411', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('WBEEYTDD', '深信校园代理412', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('IJDYYHCS', '深信校园代理413', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('QXJNIOAG', '深信校园代理414', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('PNWKPBQD', '深信校园代理415', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('XOAKNZAF', '深信校园代理416', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('GYCFSZMU', '深信校园代理417', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('EISUIRFL', '深信校园代理418', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('SXFSOJFS', '深信校园代理419', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('JRWSPFPL', '深信校园代理420', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('OOBHFKQA', '深信校园代理421', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('MHGTXTGZ', '深信校园代理422', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('JILEQOAL', '深信校园代理423', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('GBYPQUDL', '深信校园代理424', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('TAUPPWJM', '深信校园代理425', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('ZLAGYWRK', '深信校园代理426', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('FIAKMOMB', '深信校园代理427', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('TOCARWFT', '深信校园代理428', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('SZKJAXIA', '深信校园代理429', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('RMQVATJJ', '深信校园代理430', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('FUFQETPM', '深信校园代理431', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('YUQNSIHD', '深信校园代理432', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('XKUKBKAN', '深信校园代理433', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('NJAJWQUK', '深信校园代理434', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('FYPMFHHK', '深信校园代理435', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('BWXESFSA', '深信校园代理436', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('NHYXFNME', '深信校园代理437', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('TXISDRYN', '深信校园代理438', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('ZSUQUGWV', '深信校园代理439', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('XEGMVDCL', '深信校园代理440', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('TFWNAPRB', '深信校园代理441', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('CNSWKSLU', '深信校园代理442', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('NWVGALJQ', '深信校园代理443', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('KXQDUSNH', '深信校园代理444', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('NWTAIVQD', '深信校园代理445', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('AOULGLJT', '深信校园代理446', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('KDKWLILB', '深信校园代理447', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('JBFREEQA', '深信校园代理448', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('LMCCEVIR', '深信校园代理449', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('QWVQZQKV', '深信校园代理450', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('RVQPOHUQ', '深信校园代理451', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('XCVEQPJA', '深信校园代理452', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('ZJRUDVZQ', '深信校园代理453', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('KSGYSTBD', '深信校园代理454', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('NRGVBTEE', '深信校园代理455', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('GNKNVCZD', '深信校园代理456', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('XLKBUOSW', '深信校园代理457', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('UJPAZDQD', '深信校园代理458', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('FPOMVKIZ', '深信校园代理459', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('QYPHCFDW', '深信校园代理460', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('GNYAZIBR', '深信校园代理461', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('MIGGPIPF', '深信校园代理462', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('DBOHHGCF', '深信校园代理463', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('ZILLIMRG', '深信校园代理464', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('VHKAVHMR', '深信校园代理465', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('YOHRMKNN', '深信校园代理466', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('VONNUHMQ', '深信校园代理467', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('XFLFUVLY', '深信校园代理468', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('MKVRYFIL', '深信校园代理469', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('SVGWBGGF', '深信校园代理470', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('DAOUTVYD', '深信校园代理471', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('QMYSBDRI', '深信校园代理472', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('BFFWMSAT', '深信校园代理473', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('YMBHIUWS', '深信校园代理474', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('MKRLGYGL', '深信校园代理475', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('YQTLAIWG', '深信校园代理476', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('MYQKMOIR', '深信校园代理477', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('UQVHEHVX', '深信校园代理478', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('JMBVMSEA', '深信校园代理479', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('LZRSQFHK', '深信校园代理480', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('CEJTQOMK', '深信校园代理481', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('KCTDXLHN', '深信校园代理482', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('WCFLPMXM', '深信校园代理483', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('PMVGCZUV', '深信校园代理484', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('XDPKGAJW', '深信校园代理485', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('UVVBWSSM', '深信校园代理486', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('EQXSECJP', '深信校园代理487', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('XYJGDBXD', '深信校园代理488', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('LDZGFSFH', '深信校园代理489', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('TMXKAQGS', '深信校园代理490', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('LVPKGWGT', '深信校园代理491', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('HQASJQIC', '深信校园代理492', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('NLPXEKIW', '深信校园代理493', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('BFOPCYKB', '深信校园代理494', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('FDAJWSWX', '深信校园代理495', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('IHBLLOGX', '深信校园代理496', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('FULAEUTD', '深信校园代理497', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('HVRGJEPE', '深信校园代理498', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('UNPIKMMD', '深信校园代理499', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('OHAEJEQK', '深信校园代理500', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('WSQMWBXO', '深信校园代理501', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('FBXCMTOK', '深信校园代理502', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('BIEIFDDV', '深信校园代理503', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('EJTMHQOR', '深信校园代理504', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('WFOFPZMQ', '深信校园代理505', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('WGACIPUW', '深信校园代理506', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('OTZACPLW', '深信校园代理507', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('MPPPGGGA', '深信校园代理508', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('BDOIWMYL', '深信校园代理509', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('EBELVAEQ', '深信校园代理510', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('SUXNKQJC', '深信校园代理511', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('CRZYGCFG', '深信校园代理512', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('BMNSFWQN', '深信校园代理513', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('EWUXMJMO', '深信校园代理514', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('LITPCORP', '深信校园代理515', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('DHQKPZEY', '深信校园代理516', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('AWTRBFKN', '深信校园代理517', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('JUDVHUPS', '深信校园代理518', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('ZPQRNYPS', '深信校园代理519', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('TRYDYRTT', '深信校园代理520', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('EWHIMRMP', '深信校园代理521', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('XOXMBBDQ', '深信校园代理522', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('BHMAJKKD', '深信校园代理523', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('LGZQYGKL', '深信校园代理524', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('ZABEZHKD', '深信校园代理525', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('IFUGMIWJ', '深信校园代理526', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('BUBEUDSK', '深信校园代理527', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('PSWVSIBW', '深信校园代理528', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('HNUYOBGV', '深信校园代理529', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('UKFOBJZY', '深信校园代理530', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('ACJLICYY', '深信校园代理531', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('VYIJJJNM', '深信校园代理532', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('PDGGYLTT', '深信校园代理533', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('LSTGCYWD', '深信校园代理534', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('IMQIFFCD', '深信校园代理535', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('DDJDEOBA', '深信校园代理536', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('TIYXXJCV', '深信校园代理537', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('ZZFLYBYE', '深信校园代理538', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('XIIHUAEV', '深信校园代理539', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('TSHWWBZT', '深信校园代理540', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('MOVCAZQJ', '深信校园代理541', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('AUTKNHUZ', '深信校园代理542', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('XVUUOSFM', '深信校园代理543', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('SALTPQNM', '深信校园代理544', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('GNBDBWAS', '深信校园代理545', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('XAXMKIRI', '深信校园代理546', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('BGAVWXIJ', '深信校园代理547', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('YBAYQHAP', '深信校园代理548', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('RLFXDKWH', '深信校园代理549', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('PUCISSOQ', '深信校园代理550', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('XIPEFHTX', '深信校园代理551', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('USKRTGQU', '深信校园代理552', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('UFZALHOV', '深信校园代理553', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('WPROSSGN', '深信校园代理554', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('CSEZGIKB', '深信校园代理555', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('EYMPJPNH', '深信校园代理556', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('TOLTCFRF', '深信校园代理557', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('FLWTTDSX', '深信校园代理558', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('ZSLCZAKL', '深信校园代理559', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('KQDFCYFQ', '深信校园代理560', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('RGWQLQPK', '深信校园代理561', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('JBHRUNHB', '深信校园代理562', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('ARMHXJFB', '深信校园代理563', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('JZTMLYGU', '深信校园代理564', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('RXDVTJOB', '深信校园代理565', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('QIIPAXIH', '深信校园代理566', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('KMUWNWYX', '深信校园代理567', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('HZOLYTEK', '深信校园代理568', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('XCPOHIVN', '深信校园代理569', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('PQNCYFAL', '深信校园代理570', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('ANKLODQY', '深信校园代理571', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('PXBMAJNL', '深信校园代理572', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('FXISBJYP', '深信校园代理573', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('WKUEFBJW', '深信校园代理574', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('IHQOYUKN', '深信校园代理575', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('GEVKVRWH', '深信校园代理576', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('DVWMGOFN', '深信校园代理577', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('VMCPTQPG', '深信校园代理578', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('YUZLVROH', '深信校园代理579', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('SJCMAWKC', '深信校园代理580', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('TPXTGTQN', '深信校园代理581', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('YFAPWYHL', '深信校园代理582', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('ADEHOEIW', '深信校园代理583', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('YVRMQGOA', '深信校园代理584', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('FTXZYRPE', '深信校园代理585', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('TTKCDSBQ', '深信校园代理586', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('UIHNNEMG', '深信校园代理587', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('BYVNYGRK', '深信校园代理588', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('XIYOJICQ', '深信校园代理589', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('AHQBETZB', '深信校园代理590', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('VBMDURWF', '深信校园代理591', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('FPKQLEKD', '深信校园代理592', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('KYJRIGFS', '深信校园代理593', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('VWWXPAUK', '深信校园代理594', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('IDWIDHFV', '深信校园代理595', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('ELGZWEBQ', '深信校园代理596', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('ZLAALBDU', '深信校园代理597', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('OGAFYOKO', '深信校园代理598', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('PAWGAAFT', '深信校园代理599', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('UJUBYNIV', '深信校园代理600', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('ELKVNVJO', '深信校园代理601', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('XCSLENLJ', '深信校园代理602', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('TPGEQQJT', '深信校园代理603', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('LSTFCHIW', '深信校园代理604', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('YIUABGOW', '深信校园代理605', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('UPVWTOEF', '深信校园代理606', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('LALLHUJJ', '深信校园代理607', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('GWNBTYCP', '深信校园代理608', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('KZCRWSHS', '深信校园代理609', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('NAMVVDIX', '深信校园代理610', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('FIMGHABE', '深信校园代理611', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('GOHKXIUZ', '深信校园代理612', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('IWNQDWRL', '深信校园代理613', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('SMTSPOHN', '深信校园代理614', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('AKZSRKCK', '深信校园代理615', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('RLQHNFOU', '深信校园代理616', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('LAPJJAWM', '深信校园代理617', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('ZRQWAXFH', '深信校园代理618', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('YUQVGEHI', '深信校园代理619', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('SACMDHJE', '深信校园代理620', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('BUSAHELD', '深信校园代理621', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('BUQOFPNR', '深信校园代理622', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('XNULOYEG', '深信校园代理623', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('MOFSOAPW', '深信校园代理624', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('QTENSVQM', '深信校园代理625', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('SRCJNMKK', '深信校园代理626', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('VMIBUNVM', '深信校园代理627', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('XKWQMVYY', '深信校园代理628', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('KQTAPDHL', '深信校园代理629', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('HIJRXHQG', '深信校园代理630', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('VQAUEKTB', '深信校园代理631', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('WXQKWALC', '深信校园代理632', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('ZRRTZGZO', '深信校园代理633', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('ATHUQPJL', '深信校园代理634', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('LAJFELMV', '深信校园代理635', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('PPNTEXJY', '深信校园代理636', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('OVDOTASG', '深信校园代理637', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('OUSIPLAC', '深信校园代理638', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('WAUBTXAI', '深信校园代理639', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('KDNUQDKR', '深信校园代理640', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('JFCFSPRD', '深信校园代理641', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('RKRONEVT', '深信校园代理642', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('OHNOSLYS', '深信校园代理643', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('HSZEHUAY', '深信校园代理644', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('KSTIPJTQ', '深信校园代理645', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('FDTYMCOI', '深信校园代理646', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('RDUWKXLT', '深信校园代理647', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('GSYDABUQ', '深信校园代理648', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('PPCCWCZP', '深信校园代理649', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('WYWQVYWI', '深信校园代理650', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('PMOSACZU', '深信校园代理651', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('LWPWWBYZ', '深信校园代理652', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('PMFTIFKU', '深信校园代理653', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('UECVUYCZ', '深信校园代理654', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('HSMSNJQQ', '深信校园代理655', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('WEGGOAVV', '深信校园代理656', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('CEEPTMZA', '深信校园代理657', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('FOQIGGMD', '深信校园代理658', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('FZSSIQUR', '深信校园代理659', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('TQXXBGTM', '深信校园代理660', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('WSYPKLWB', '深信校园代理661', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('NCXYNJCL', '深信校园代理662', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('EAAASPYB', '深信校园代理663', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('AKXCSAAT', '深信校园代理664', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('JTIITKPB', '深信校园代理665', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('IVCXIHZB', '深信校园代理666', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('UBHUMJJG', '深信校园代理667', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('FSJLJCPJ', '深信校园代理668', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('JSLYXRUK', '深信校园代理669', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('QJNECTBH', '深信校园代理670', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('DNKUFFFO', '深信校园代理671', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('ANLLIUDP', '深信校园代理672', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('FHLSBLPT', '深信校园代理673', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('JMKKWQEQ', '深信校园代理674', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('KPBGYRQA', '深信校园代理675', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('RBCIQGIV', '深信校园代理676', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('BHAJQWIS', '深信校园代理677', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('LYYNHYBU', '深信校园代理678', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('BURLPGTI', '深信校园代理679', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('QHBCSQML', '深信校园代理680', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('YVSQFYNX', '深信校园代理681', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('ZOWSMFJX', '深信校园代理682', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('NFFSZEHC', '深信校园代理683', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('NCBLRGED', '深信校园代理684', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('TYVULDNL', '深信校园代理685', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('UYGWQSQW', '深信校园代理686', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('LQDWMHMB', '深信校园代理687', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('PZFMDEQL', '深信校园代理688', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('YTBHVPWC', '深信校园代理689', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('KCMQQTCD', '深信校园代理690', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('TEYWZYIX', '深信校园代理691', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('ONSORHIM', '深信校园代理692', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('GEJZUHYC', '深信校园代理693', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('CTIWTGUW', '深信校园代理694', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('CMEOUBJP', '深信校园代理695', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('XWDUQZHC', '深信校园代理696', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('AYOXPRAN', '深信校园代理697', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('CEWMKVIH', '深信校园代理698', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('BGBCSIIL', '深信校园代理699', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('IDDONQYT', '深信校园代理700', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('SZAFXQQG', '深信校园代理701', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('EHQZAYZU', '深信校园代理702', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('WRZYHHEL', '深信校园代理703', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('YXMAENFM', '深信校园代理704', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('EXDNLDEK', '深信校园代理705', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('WCLTITQK', '深信校园代理706', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('THMMXYRZ', '深信校园代理707', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('PTOLERKS', '深信校园代理708', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('CQUPBPCQ', '深信校园代理709', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('LQUWRXKW', '深信校园代理710', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('QBQCXCJT', '深信校园代理711', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('NUJJXIWF', '深信校园代理712', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('WGKIXOQY', '深信校园代理713', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('NTLINUQO', '深信校园代理714', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('NFSEQEJK', '深信校园代理715', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('WUYCEVVW', '深信校园代理716', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('KUGJANAC', '深信校园代理717', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('XFWZDCZK', '深信校园代理718', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('HDTJZMKR', '深信校园代理719', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('CKAGMSVY', '深信校园代理720', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('MGEBCUHA', '深信校园代理721', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('FRFPZPYU', '深信校园代理722', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('PABXVNOG', '深信校园代理723', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('TZGDLKPY', '深信校园代理724', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('LVGECJLD', '深信校园代理725', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('JVASERAC', '深信校园代理726', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('DISEXJFK', '深信校园代理727', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('JKVTHBSK', '深信校园代理728', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('GAHXDSSA', '深信校园代理729', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('PQZVAJEH', '深信校园代理730', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('SCCREJRK', '深信校园代理731', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('SOSANAYS', '深信校园代理732', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('FFYRDYGI', '深信校园代理733', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('SUPDCTCJ', '深信校园代理734', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('VCBRCPTD', '深信校园代理735', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('ISGEDULS', '深信校园代理736', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('BFFGXDOB', '深信校园代理737', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('WYFZTLPW', '深信校园代理738', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('ITPDVKAZ', '深信校园代理739', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('YHKZRDKR', '深信校园代理740', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('WSGPXUPN', '深信校园代理741', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('EDHKETXV', '深信校园代理742', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('GYDZCZEA', '深信校园代理743', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('FDCLOJCJ', '深信校园代理744', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('RDSZGXJW', '深信校园代理745', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('CXTCERUC', '深信校园代理746', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('FFWCFMBA', '深信校园代理747', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('PEZASBXI', '深信校园代理748', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('GWGCNCBI', '深信校园代理749', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('CHQXMVXK', '深信校园代理750', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('LNQVBNWC', '深信校园代理751', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('PCQMQSJZ', '深信校园代理752', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('MTQBRUVU', '深信校园代理753', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('FUNZTEJX', '深信校园代理754', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('JNFEOFJH', '深信校园代理755', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('IIBOSSDO', '深信校园代理756', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('FTZLDORF', '深信校园代理757', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('BVVKUGKL', '深信校园代理758', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('NWMQBUWW', '深信校园代理759', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('OKYRNCPJ', '深信校园代理760', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('ZEUYHASO', '深信校园代理761', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('FNKXSTIX', '深信校园代理762', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('WVYMAIWF', '深信校园代理763', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('ETENDKXO', '深信校园代理764', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('UQXXRLEL', '深信校园代理765', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('TRKRSLYX', '深信校园代理766', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('QIGDKXSB', '深信校园代理767', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('YQEPNBIM', '深信校园代理768', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('UIXZAUSY', '深信校园代理769', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('ESCODEOF', '深信校园代理770', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('PKYJKVSH', '深信校园代理771', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('WQHPYXWE', '深信校园代理772', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('ZBMMKLXR', '深信校园代理773', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('MKXTPLQF', '深信校园代理774', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('TFQYNKMH', '深信校园代理775', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('UEFQPLRP', '深信校园代理776', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('DVOJBRZT', '深信校园代理777', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('BQXUFKFV', '深信校园代理778', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('GRFLGXLV', '深信校园代理779', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('CDMTTONP', '深信校园代理780', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('QWPTWYQD', '深信校园代理781', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('HTYMLREC', '深信校园代理782', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('FUUKMMSN', '深信校园代理783', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('OFXRPGSP', '深信校园代理784', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('CKHMTTEU', '深信校园代理785', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('QPVAAADX', '深信校园代理786', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('OCNDWRLF', '深信校园代理787', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('WVTIYRQO', '深信校园代理788', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('LQSDPEQA', '深信校园代理789', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('INFHCXUT', '深信校园代理790', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('ICVYJQCI', '深信校园代理791', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('UIYDHQOW', '深信校园代理792', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('QBBXEDWZ', '深信校园代理793', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('FZVOUVGZ', '深信校园代理794', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('ZAMMQFCP', '深信校园代理795', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('AWERDBMX', '深信校园代理796', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('PVDTMJBD', '深信校园代理797', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('HRHVBMTE', '深信校园代理798', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('FZUAODDM', '深信校园代理799', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('WWIWJQKE', '深信校园代理800', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('ZUAIQEUF', '深信校园代理801', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('WGNSEPZL', '深信校园代理802', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('RIPDGCFK', '深信校园代理803', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('SOPXWFSR', '深信校园代理804', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('SLOWOIQN', '深信校园代理805', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('ZPUOHVJR', '深信校园代理806', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('ROVJOMZC', '深信校园代理807', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('AKPVQEVY', '深信校园代理808', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('XQKPYVCH', '深信校园代理809', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('ZWHIAJYE', '深信校园代理810', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('EKKRXXLZ', '深信校园代理811', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('FFZBKHEL', '深信校园代理812', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('STJSFVPC', '深信校园代理813', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('WUCDWFOC', '深信校园代理814', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('DSJMLHXR', '深信校园代理815', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('DCCVMCLO', '深信校园代理816', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('YSSKIUXI', '深信校园代理817', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('TSPIYMVY', '深信校园代理818', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('YHBEVKFE', '深信校园代理819', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('LNEJXMPY', '深信校园代理820', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('CENILWWD', '深信校园代理821', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('UPCRXOGW', '深信校园代理822', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('KABCRAHO', '深信校园代理823', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('NUHGDZWH', '深信校园代理824', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('KVCREIPW', '深信校园代理825', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('YWJHSQNC', '深信校园代理826', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('YFZXCCXI', '深信校园代理827', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('QLQSDXMV', '深信校园代理828', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('YIQPGSBZ', '深信校园代理829', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('XMQJXTBZ', '深信校园代理830', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('IBHGTIYO', '深信校园代理831', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('QDAJEXSB', '深信校园代理832', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('KCGCFWUC', '深信校园代理833', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('KFSVMDOV', '深信校园代理834', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('GQZNLKEA', '深信校园代理835', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('UDJNPIHW', '深信校园代理836', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('SCGZTFYY', '深信校园代理837', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('HYKNYJSE', '深信校园代理838', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('MLQEKTRZ', '深信校园代理839', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('NZYVZQXQ', '深信校园代理840', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('QQDSFENJ', '深信校园代理841', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('GVGYLSED', '深信校园代理842', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('KUQZUJCB', '深信校园代理843', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('ZZSCBIEA', '深信校园代理844', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('IPPFHZMZ', '深信校园代理845', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('ZUAMWYTQ', '深信校园代理846', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('LNFBEMLO', '深信校园代理847', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('MAJDOKNF', '深信校园代理848', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('SFHWKLSW', '深信校园代理849', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('MDCFJDLN', '深信校园代理850', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('OBDQRMUP', '深信校园代理851', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('UAHGHVMK', '深信校园代理852', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('WOGOISLT', '深信校园代理853', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('FGYZTIYB', '深信校园代理854', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('NOZMXZZI', '深信校园代理855', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('PRFUTXJB', '深信校园代理856', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('ZDBOQJHQ', '深信校园代理857', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('WYECIQFU', '深信校园代理858', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('ZVBKSDSD', '深信校园代理859', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('FCGSNTXS', '深信校园代理860', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('LVXVMUPL', '深信校园代理861', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('ZLFAVRAC', '深信校园代理862', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('XOXJLQRT', '深信校园代理863', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('KJOOYCHP', '深信校园代理864', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('FIWVNKNV', '深信校园代理865', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('MLUBNSCS', '深信校园代理866', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('VSMJHDOW', '深信校园代理867', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('JDMFJQLR', '深信校园代理868', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('HDKPEQRQ', '深信校园代理869', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('AWXNZDVY', '深信校园代理870', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('EKDDFCBA', '深信校园代理871', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('JQXOZXNE', '深信校园代理872', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('MFHKSKCE', '深信校园代理873', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('NRQACHLA', '深信校园代理874', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('MQVODTKX', '深信校园代理875', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('YRJBQILY', '深信校园代理876', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('AHQISMRE', '深信校园代理877', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('GMMOPGPI', '深信校园代理878', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('BHJDCENC', '深信校园代理879', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('VBYFVUEG', '深信校园代理880', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('VSBYXIYS', '深信校园代理881', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('JOSDBNTB', '深信校园代理882', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('OOAXABCR', '深信校园代理883', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('IMRBCBRG', '深信校园代理884', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('QHVBMAXO', '深信校园代理885', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('ZCRONLKR', '深信校园代理886', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('ZMBIOPEE', '深信校园代理887', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('UBZGBEIE', '深信校园代理888', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('VUBXKHRR', '深信校园代理889', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('KJTBEKRG', '深信校园代理890', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('VKLRWGTK', '深信校园代理891', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('VYWMTAVO', '深信校园代理892', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('XWDHSVHB', '深信校园代理893', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('LZCIBUIH', '深信校园代理894', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('WMYCDMJJ', '深信校园代理895', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('QBOPIHVD', '深信校园代理896', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('YGZEXAVY', '深信校园代理897', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('ZLVSPOLO', '深信校园代理898', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('KDPWKHLT', '深信校园代理899', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('BWGLQOJY', '深信校园代理900', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('ATMZGBZS', '深信校园代理901', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('IPZKCRUH', '深信校园代理902', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('BTQKWUWL', '深信校园代理903', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('OVTDQHZO', '深信校园代理904', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('HKRYHKYK', '深信校园代理905', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('DBCEEVQL', '深信校园代理906', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('NKCCKBOZ', '深信校园代理907', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('VJANHFNB', '深信校园代理908', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('EBSZTQQK', '深信校园代理909', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('JACPSJSG', '深信校园代理910', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('EKMNPDLH', '深信校园代理911', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('GEJHODZY', '深信校园代理912', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('THYKQKJQ', '深信校园代理913', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('AZGGOYCK', '深信校园代理914', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('JJEIOTYK', '深信校园代理915', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('XNMGMYKI', '深信校园代理916', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('FBSMPKUH', '深信校园代理917', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('UOZGNKMM', '深信校园代理918', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('EKRAIRHG', '深信校园代理919', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('IZERGYRP', '深信校园代理920', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('UOYDIQMZ', '深信校园代理921', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('YIIQEUSS', '深信校园代理922', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('SNKVSRQR', '深信校园代理923', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('DJCRHGON', '深信校园代理924', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('OPCFYNHD', '深信校园代理925', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('XGPQTFXI', '深信校园代理926', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('HJNKRIWQ', '深信校园代理927', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('ICIRLSZH', '深信校园代理928', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('PKJLALRT', '深信校园代理929', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('FBQWSPQZ', '深信校园代理930', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('AXUWKQKT', '深信校园代理931', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('JCHDBQRZ', '深信校园代理932', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('CSAAJPAP', '深信校园代理933', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('QYQFMIHX', '深信校园代理934', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('RUOXLNKS', '深信校园代理935', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('PSWZRIZV', '深信校园代理936', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('JEVJCWLT', '深信校园代理937', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('YYSGJQFG', '深信校园代理938', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('XBZCVVIY', '深信校园代理939', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('WZEMSATT', '深信校园代理940', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('VDJRWRHB', '深信校园代理941', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('AVAKXJWA', '深信校园代理942', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('WIOUIWRB', '深信校园代理943', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('XNYRCDRJ', '深信校园代理944', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('LELEMTAY', '深信校园代理945', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('QAACJAIX', '深信校园代理946', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('JWAJLIRB', '深信校园代理947', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('YLRBWXZK', '深信校园代理948', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('JERGDYSC', '深信校园代理949', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('TQSKONAD', '深信校园代理950', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('EYPCOYBQ', '深信校园代理951', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('OZARYHUW', '深信校园代理952', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('NDZCMRAM', '深信校园代理953', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('VUWFIAIG', '深信校园代理954', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('FRIAMQPA', '深信校园代理955', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('XWSPFQPL', '深信校园代理956', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('PMGXKAZY', '深信校园代理957', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('NHGEOLLV', '深信校园代理958', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('EJVBOWNY', '深信校园代理959', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('YUXWTJAM', '深信校园代理960', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('HAVIALTW', '深信校园代理961', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('LVFTCETL', '深信校园代理962', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('XJVMNSCB', '深信校园代理963', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('AGBZFIND', '深信校园代理964', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('HRKCCKCR', '深信校园代理965', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('FJSXVCWR', '深信校园代理966', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('ULJCLODP', '深信校园代理967', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('XFCKQSAL', '深信校园代理968', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('XVRHJNEP', '深信校园代理969', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('FILFEVRZ', '深信校园代理970', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('VXDWCBCW', '深信校园代理971', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('JBXYZFAW', '深信校园代理972', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('ZTDAVFOS', '深信校园代理973', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('DZOZCHRR', '深信校园代理974', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('MVZOZPXI', '深信校园代理975', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('SXJIRPMS', '深信校园代理976', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('HVZYDUNS', '深信校园代理977', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('DPVPTEYT', '深信校园代理978', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('FLYFVYNT', '深信校园代理979', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('QWBALYOS', '深信校园代理980', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('WAYZXCFA', '深信校园代理981', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('JAQAAKGX', '深信校园代理982', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('TMNKQIUA', '深信校园代理983', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('ZLUVATTM', '深信校园代理984', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('FDERAXIY', '深信校园代理985', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('RBVSOMCS', '深信校园代理986', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('HKCVODFW', '深信校园代理987', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('PNEMTCRI', '深信校园代理988', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('QTNFUIBY', '深信校园代理989', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('FCZBWCWM', '深信校园代理990', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
INSERT INTO `t_share_user` VALUES ('PGWPCYKS', '深信校园代理991', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:13');
INSERT INTO `t_share_user` VALUES ('GUPDRGCI', '深信校园代理992', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:18');
INSERT INTO `t_share_user` VALUES ('UZKZPHUV', '深信校园代理993', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:28:58');
INSERT INTO `t_share_user` VALUES ('RLJNGCYV', '深信校园代理994', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:23:32');
INSERT INTO `t_share_user` VALUES ('GVAMZBNW', '深信校园代理995', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:25:32');
INSERT INTO `t_share_user` VALUES ('QFRPELFU', '深信校园代理996', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:28');
INSERT INTO `t_share_user` VALUES ('QFWJYLJP', '深信校园代理997', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:09');
INSERT INTO `t_share_user` VALUES ('ZUJWKOPO', '深信校园代理998', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:05');
INSERT INTO `t_share_user` VALUES ('OACDQEHA', '深信校园代理999', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:04');
INSERT INTO `t_share_user` VALUES ('MRPZXHSD', '深信校园代理1000', 100, NULL, NULL, 4, 2, NULL, 'http://www.lilixc.com/appPro/ticket/ticket.html', '分享文字描述', '活动规则', 'big.jpg', 'small.jpg', 1, 0, NULL, NULL, NULL, '2016-3-18 15:29:24');
