package com.lili.order.vo;

import java.io.Serializable;
import java.util.List;

public class WaitOrderVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4253962018654478742L;
	//当前第一个待支付的订单
	private OrderVo waitPay;
	//其他待支付的订单id
	private List<String> waitPayId;
	//第一个待评价的订单
	private OrderVo waitComment;
	//其他待评价的订单id
	private List<String> waitCommentId;
	public OrderVo getWaitPay() {
		return waitPay;
	}
	public void setWaitPay(OrderVo waitPay) {
		this.waitPay = waitPay;
	}
	public List<String> getWaitPayId() {
		return waitPayId;
	}
	public void setWaitPayId(List<String> waitPayId) {
		this.waitPayId = waitPayId;
	}
	public OrderVo getWaitComment() {
		return waitComment;
	}
	public void setWaitComment(OrderVo waitComment) {
		this.waitComment = waitComment;
	}
	public List<String> getWaitCommentId() {
		return waitCommentId;
	}
	public void setWaitCommentId(List<String> waitCommentId) {
		this.waitCommentId = waitCommentId;
	}
	
}
