package com.lili.school.service;

import com.lili.cms.entity.ResponseMessage;
import com.lili.order.vo.CommentTagVo;
import com.lili.school.model.CommentTag;

/**
 * 教练服务评价、学员技能标签接口
 * @author lzb
 *
 */
public interface ICmsCommentTagService {

    public String query(CommentTag commentTag);
    
    public ResponseMessage isUseOrDel(String checker, String id, Integer isdel);
    
    public ResponseMessage update(CommentTagVo commentTagVo);
    
    public ResponseMessage addCommentTag(CommentTagVo commentTagVo);
    

}
