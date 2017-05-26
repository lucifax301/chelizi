-- 文科题:收藏夹
CREATE TABLE `t_u_exercise_collection` (
  `studentId` bigint(20) NOT NULL,
  `qid` varchar(45) NOT NULL COMMENT '题目Id',
  `subject` int(11) NOT NULL COMMENT '1表示科目一,4表示科目四',
  `chapter` int(11) NOT NULL COMMENT '1~7表示章节一到七',
  `ansSta` int(11) NOT NULL COMMENT '最近回答对错记录 数字1为对,2为错,0为未答过',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`studentId`,`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文科题收藏';


-- 文科题:错题集
CREATE TABLE `t_u_exercise_error` (
  `studentId` bigint(20) NOT NULL,
  `qid` varchar(45) NOT NULL COMMENT '题目Id',
  `subject` int(11) NOT NULL COMMENT '1表示科目一,4表示科目四',
  `chapter` int(11) NOT NULL COMMENT '1~7表示章节一到七',
  `ansSta` int(11) NOT NULL COMMENT '最近回答对错记录 数字1为对,2为错,0为未答过',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`studentId`,`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文科题收藏';


-- 文科题:模拟考
CREATE TABLE `t_u_exercise_exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `studentId` bigint(20) NOT NULL,
  `score` int(11) NOT NULL COMMENT '考试成绩',
  `examtime` int(11) NOT NULL COMMENT '考试开始时间 单位秒',
  `usetime` int(11) NOT NULL COMMENT '一次模拟考试所用的时间 考试结束时间减去开始考试时间 单位秒',
  `subject` int(11) NOT NULL COMMENT '1表示科目一,4表示科目四',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- 文科题:模拟考题集
CREATE TABLE `t_u_exercise_exam_detail` (
  `exerciseExamId` int(11) NOT NULL,
  `qid` varchar(45) NOT NULL COMMENT '''题目Id''',
  `ansRec` varchar(45) NOT NULL COMMENT '用户答题选择答案',
  `ansSta` int(11) NOT NULL COMMENT '用户回答状态 数字1为对,2为错,0为未答过',
  PRIMARY KEY (`exerciseExamId`,`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模拟考试记录答题详情';

