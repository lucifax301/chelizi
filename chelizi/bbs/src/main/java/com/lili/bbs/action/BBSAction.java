package com.lili.bbs.action;

import java.util.List;

import com.lili.bbs.dto.BbsInform;
import com.lili.bbs.dto.BbsPraise;
import com.lili.bbs.dto.BbsReply;
import com.lili.bbs.dto.BbsTopic;
import com.lili.bbs.vo.BbsDetailMessage;
import com.lili.bbs.vo.BbsMessage;
import com.lili.common.vo.ReqResult;

public abstract class BBSAction {

    /**
     * 进行发布行为时,进行发布前的预处理
     * @param bbs
     * @return 
     * @throws Exception
     */
    public abstract ReqResult doReleaseAction(BbsMessage bbsMsg) throws Exception;
    

    /**
     * 进行评论行为时,进行评论前的预处理
     * @param bbsReply
     * @throws Exception
     */
    public abstract ReqResult doReplyAction(BbsReply bbsReply) throws Exception;
    
    /**
     * 进行点赞行为时,进行点赞前的预处理
     * @param bbsPraise
     * @throws Exception
     */
    public abstract ReqResult doPraiseAction(BbsPraise bbsPraise) throws Exception;

    /**
     * 获取社区帖子
     * @param pageSize
     * @param pageIndex
     * @return
     * @throws Exception
     */
	public abstract List<BbsMessage> getBBSList(String userId, Integer pageSize, Integer pageIndex) throws Exception;


	public abstract ReqResult getBbsDetail(BbsDetailMessage bbsDetailMessage, String userId) throws Exception;


	public abstract ReqResult getBBSPraiseList(BbsPraise praise, Integer pageSize, Integer pageIndex) throws Exception;


	public abstract ReqResult informTopic(BbsInform inform)  throws Exception;


	public abstract ReqResult getMyBBSList(BbsMessage bmsg, Integer pageSize, Integer pageIndex) throws Exception;


	public abstract ReqResult getMyBBSReplyList(Long userId, Integer userType, Integer pageSize, Integer pageIndex) throws Exception;
	
	public abstract ReqResult getMyBBSPraiseList(Long userId, Integer userType, Integer pageSize, Integer pageIndex) throws Exception;

	public abstract ReqResult getBBSReplyList(BbsReply reply, Integer pageSize, Integer pageIndex) throws Exception;

	public abstract List<BbsTopic> getBBSTopic()  throws Exception;

    
    
}
