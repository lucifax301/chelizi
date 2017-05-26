CREATE TABLE t_insurance_order(
  insuranceId VARCHAR(34) NOT NULL PRIMARY KEY COMMENT '保险id',
  studentId BIGINT(11) NOT NULL COMMENT '学员ID',
  NAME  VARCHAR(32) DEFAULT NULL COMMENT '学员姓名',
  phoneNum VARCHAR(64) DEFAULT NULL COMMENT '学员电话',
  cityId INT(11) DEFAULT NULL COMMENT '学员所属城市id',
  cityName VARCHAR(100) DEFAULT NULL  COMMENT '城市名称',
  price INT(11) DEFAULT NULL COMMENT '保险价格，单位分',
  YEAR  INT(2) DEFAULT NULL COMMENT '保险年限',
  insuranceNumber VARCHAR(34) DEFAULT NULL COMMENT '保单号',
  payTime DATETIME DEFAULT NULL COMMENT '支付时间',
  effectTime DATETIME DEFAULT NULL  COMMENT '生效时间',
  invalidTime DATETIME DEFAULT NULL  COMMENT '失效时间',
  operationTime timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  compensate INT(2) DEFAULT 0 COMMENT '理赔状态 0:未理赔  1:已理赔',
  compensateRemark VARCHAR(200) DEFAULT NULL  COMMENT '理赔备注',
  payState INT(2) DEFAULT 0 COMMENT '支付状态 0:未支付 100:支付成功  101:支付失败',
  payWay VARCHAR(100) DEFAULT NULL  COMMENT '支付方式',
  refundState INT(2) DEFAULT 0 COMMENT '退款状态 0:未退款 1:已退款',
  refundPrice INT(11) DEFAULT NULL COMMENT '退款金额，单位分',
  refundRemark  VARCHAR(200) DEFAULT NULL  COMMENT '退款备注',
  visitState INT(2) DEFAULT 0 COMMENT '回访状态 0:未回访  1:已回访',
  visitRemark VARCHAR(200) DEFAULT NULL  COMMENT '回访备注'
);

ALTER TABLE db_lili.`t_wechat_comment` ADD COLUMN openId VARCHAR(128) DEFAULT NULL COMMENT '微信openId';
