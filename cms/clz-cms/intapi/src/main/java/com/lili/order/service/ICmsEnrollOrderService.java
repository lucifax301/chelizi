package com.lili.order.service;

import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.order.vo.EnrollOrderBalanceVo;
import com.lili.order.vo.EnrollOrderVo;

public interface ICmsEnrollOrderService {

    public String query(EnrollOrderVo enrollOrderVo, Long schoolNo);
    
    public EnrollOrderVo queryEnrollOrderByStudentId(EnrollOrderVo enrollOrderVo);
    
    public ResponseMessage detail(String orderId);
    
    public ResponseMessage update(String orderId);

	public String balanceQuery(EnrollOrderBalanceVo enrollOrderBalanceVo);

	public ResponseMessage payment(String orderId, String asker);

	public String payQuery(EnrollOrderBalanceVo enrollOrderBalanceVo);

	public List<EnrollOrderVo> downLoadExl(EnrollOrderVo enrollOrderVo);

	public List<EnrollOrderBalanceVo> payDownLoadExl(EnrollOrderBalanceVo enrollOrderVo);
    
}
