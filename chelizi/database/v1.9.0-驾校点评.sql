drop TABLE if exists t_wechat_enroll_school;
CREATE TABLE `t_wechat_enroll_school` (
  `schoolId` int(11) NOT NULL COMMENT '驾校ID，与t_s_school保持一致',
  `cityId` int(11) NOT NULL COMMENT '城市ID',
  `name` varchar(32) NOT NULL COMMENT '驾校名字',
  `score` double DEFAULT NULL COMMENT '评分，总分100',
  `logo` varchar(128) DEFAULT NULL COMMENT '驾校logo',
  `icon` varchar(128) DEFAULT NULL COMMENT '驾校封面图',
  `image` text COMMENT '驾校图片,"+"号分开',
  `coachCount` int(11) DEFAULT NULL COMMENT '教练数量',
  `carCount` int(11) DEFAULT NULL COMMENT '教练车数量',
  `trfieldCount` int(11) DEFAULT NULL COMMENT '训练场数量',
  `schoolInfo` text COMMENT '驾校简介',
  `regInfo` text COMMENT '报名须知',
  `lge` decimal(10,6) DEFAULT NULL COMMENT '驾校经度',
  `lae` decimal(10,6) DEFAULT NULL COMMENT '驾校纬度',
  `orderNum` int(11) DEFAULT NULL COMMENT '报名人数',
  `extra` varchar(128) DEFAULT NULL COMMENT '存放最短训练场最近距离',
  `tell` varchar(32) DEFAULT NULL COMMENT '客服电话',
  `address` varchar(200) DEFAULT NULL COMMENT '总部地址',
  `storeCount` int(11) DEFAULT NULL COMMENT '门店数量',
  `packageCount` int(11) DEFAULT NULL COMMENT '班别数量',
  PRIMARY KEY (`schoolId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信端驾校表';

drop TABLE if exists t_wechat_enroll_package;
CREATE TABLE `t_wechat_enroll_package` (
  `ttid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cityId` int(11) NOT NULL COMMENT '城市id',
  `schoolId` int(11) NOT NULL COMMENT '驾校id',
  `name` varchar(50) NOT NULL COMMENT '报名模版的名称',
  `marketPrice` int(11) DEFAULT NULL COMMENT '市场价，单位分',
  `price` int(11) NOT NULL COMMENT '报名总价，单位分',
  `title` varchar(256) DEFAULT NULL COMMENT '摘要',
  `icon` varchar(256) DEFAULT NULL COMMENT '图标',
  `protocol` text COMMENT '报名协议',
  `priceDetail` text COMMENT '价格详细说明，格式tile=price,多条之间用加号（+）分割，亦可以考虑富文本模式',
  `test_condition` text COMMENT '报名条件：多条之间采用加号（+）分割，亦可以考虑富文本模式',
  `describtion` text COMMENT '报考资料(图文混排)',
  `procPic` varchar(256) DEFAULT NULL COMMENT '报名流程(图文混排)',
  `firstStep` int(11) DEFAULT NULL COMMENT '该报名流程第一步',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  `cuid` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `muid` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  `feature` varchar(200) DEFAULT NULL COMMENT '班别特色',
  `transferStyle` varchar(20) DEFAULT NULL COMMENT '接送方式',
  `trainStyle` varchar(20) DEFAULT NULL COMMENT '练车方式',
  `carStyle` varchar(20) DEFAULT NULL COMMENT '教学车辆',
  `hours` int(11) DEFAULT NULL COMMENT '学车学时',
  `mailAddress` varchar(300) DEFAULT NULL COMMENT '资料邮寄地址',
  `remark` text COMMENT '备注',
  `cstate` int(2) DEFAULT '1' COMMENT '审核状态：1未审核 2审核通过 3审核不通过',
  `ostate` int(2) DEFAULT '1' COMMENT '是否招生：1停止招生 2开通招生',
  `orderNum` int(11) DEFAULT NULL COMMENT '报名人数',
  `graduateNum` int(11) DEFAULT NULL COMMENT '毕业人数',
  `passRate` float DEFAULT NULL COMMENT '通过率',
  `refuse` varchar(200) DEFAULT NULL COMMENT '拒绝理由',
  PRIMARY KEY (`ttid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='微信端班别表';

drop TABLE if exists t_wechat_enroll_order;
CREATE TABLE `t_wechat_enroll_order` (
  `orderId` varchar(32) NOT NULL COMMENT '订单号',
  `studentId` bigint(20) NOT NULL COMMENT '报名学生帐号',
  `ttid` int(11) NOT NULL COMMENT '报名模版',
  `driveLicense` char(1) NOT NULL DEFAULT 'C' COMMENT '报考驾照类别:A,B,C,D。当前只支持C',
  `dltype` tinyint(4) NOT NULL DEFAULT '1' COMMENT '驾照类型：1代表C1,2代表C2',
  `name` varchar(50) DEFAULT NULL COMMENT '报考姓名',
  `cardType` tinyint(4) DEFAULT '1' COMMENT '报考证件：1代表身份证',
  `cardId` varchar(50) DEFAULT NULL COMMENT '报考证件号码',
  `cardPic1` varchar(256) DEFAULT NULL COMMENT '证件正面照片',
  `cardPic2` varchar(256) DEFAULT NULL COMMENT '证件反面照片',
  `schoolId` int(11) NOT NULL COMMENT '驾校id',
  `officeId` varchar(50) DEFAULT NULL COMMENT '车管所报名流水号',
  `payState` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态：0未开始，1已提交，100支付成功，101支付失败',
  `payTime` datetime DEFAULT NULL COMMENT '支付时间',
  `postState` tinyint(4) NOT NULL DEFAULT '0' COMMENT '资料邮寄状态：0资料未邮寄，1资料已邮寄，2资料已收到',
  `testState` tinyint(4) NOT NULL DEFAULT '0' COMMENT '报考状态：0未报考，其他为进度步骤step_id',
  `cityId` int(11) NOT NULL COMMENT '冗余：报名城市id',
  `cityName` varchar(50) DEFAULT NULL COMMENT '报名模版的名称',
  `price` int(11) NOT NULL COMMENT '冗余：报名总价，单位分',
  `schoolName` varchar(50) DEFAULT NULL COMMENT '冗余：报名驾校名称',
  `priceDetail` text COMMENT '冗余：价格详细说明，格式tile=price,多条之间用加号（+）分割，亦可以考虑富文本模式',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0代表未删除，1代表已删除',
  `ctime` datetime DEFAULT NULL,
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `coupon` bigint(20) DEFAULT NULL COMMENT '使用的优惠券id',
  `couponTotal` int(11) DEFAULT '0' COMMENT '冗余：优惠券抵扣金额，单位分',
  `couponName` varchar(50) DEFAULT NULL COMMENT '冗余：优惠券名称',
  `payWay` varchar(16) DEFAULT NULL COMMENT '支付渠道',
  `payTotal` int(11) DEFAULT NULL COMMENT '实际支付金额',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  `checkouter` varchar(45) DEFAULT NULL COMMENT '结款人',
  `checkoutTime` timestamp NULL DEFAULT NULL COMMENT '结款时间',
  `orderState` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单状态：0已取消，1已下单，2未结款，3已结款，4退款中，5已退款，6退款失败，',
  `brokerage` int(11) DEFAULT '0' COMMENT '结算佣金金额，单位：分',
  PRIMARY KEY (`orderId`),
  UNIQUE KEY `order_id` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信端订单表';


drop TABLE if exists t_wechat_comment;
CREATE TABLE `t_wechat_comment` (
  `commentId` varchar(32) NOT NULL COMMENT '主键id',
  `studentId` bigint(11) DEFAULT NULL COMMENT '学员id',
  `name` varchar(200) DEFAULT NULL COMMENT '姓名',
  `score` int(3) DEFAULT NULL COMMENT '评分',
  `comment` varchar(500) DEFAULT NULL COMMENT '评论',
  `pic` varchar(500) DEFAULT NULL COMMENT '图片',
  `schoolId` bigint(11) NOT NULL COMMENT '学校id',
  `sumPraise` int(11) DEFAULT NULL COMMENT '点赞总数',
  `avgScore` int(3) DEFAULT NULL COMMENT '平均分 冗余',
  `extra` varchar(500) DEFAULT NULL COMMENT '冗余',
  `createTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`commentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='驾校点评评论表';

drop TABLE if exists t_wechat_comment_praise;
CREATE TABLE `t_wechat_comment_praise` (
  `praiseId` varchar(32) NOT NULL COMMENT '主键id',
  `commentId` varchar(32) NOT NULL COMMENT '评论id',
  `studentId` int(11) DEFAULT NULL COMMENT '学生Id',
  `extra` varchar(200) DEFAULT NULL COMMENT '冗余',
  `createTime` timestamp NULL DEFAULT NULL COMMENT '创建世界',
  PRIMARY KEY (`praiseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='驾校点评点赞表';





