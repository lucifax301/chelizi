package com.lili.order.service;

import com.lili.cms.entity.ResponseMessage;
import com.lili.order.vo.CancelReason;
import com.lili.order.vo.CancelReasonVo;

/**
 * 课程接口
 * @author lzb
 *
 */
public interface ICmsCancelReasonService {

    public String query(CancelReason cancelReason);
    
    public ResponseMessage isUseOrDel(String checker, String remark, String id, Integer isdel);
    
    public ResponseMessage update(CancelReasonVo cancelReason);
    
    public ResponseMessage addCourse(CancelReasonVo cancelReason);
    

}
