
-- 修改注释
ALTER TABLE t_order MODIFY COLUMN `pay_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态：0代表未支付，1代表已支付，2代表支付失败，9代表老学员自动支付，10已挂起，11退款中，12已退款';

-- 消息中心
ALTER TABLE t_p_notice ADD COLUMN etime timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '截止时间，过了截止时间的消息不再展示';

