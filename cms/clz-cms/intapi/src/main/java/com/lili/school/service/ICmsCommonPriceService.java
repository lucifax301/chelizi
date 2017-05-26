package com.lili.school.service;

import com.lili.cms.entity.ResponseMessage;
import com.lili.order.vo.CommonPriceVo;
import com.lili.school.model.CommonPrice;

/**
 * 课程定价接口
 * @author lzb
 *
 */
public interface ICmsCommonPriceService {

    public String query(CommonPrice commonPrice);
    
    public ResponseMessage isUseOrDel(String checker, String id, Integer isdel);
    
    public ResponseMessage update(CommonPriceVo commonPriceVo);
    
    public ResponseMessage check(CommonPriceVo commonPriceVo);
    
    public ResponseMessage addCourse(CommonPriceVo commonPriceVo);
    

}
