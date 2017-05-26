package com.lili.share.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.common.util.MobileUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.share.dao.mapper.GiftMapper;
import com.lili.share.dto.Gift;
import com.lili.share.dto.GiftExample;
import com.lili.share.dto.GiftVo;
import com.lili.share.manager.GiftManager;

public class GiftManagerImpl implements GiftManager {
	private Logger logger = Logger.getLogger(GiftManagerImpl.class);
	
	@Autowired
	private GiftMapper giftMapper;

	@Override
	public ReqResult getGiftCount() {
		GiftExample example = new GiftExample();
		GiftExample.Criteria criteria = example.createCriteria();
		int total = giftMapper.countByExample(example);
		criteria.andStateNotEqualTo(0);//状态：0-未发放；1-已发放；2-已使用；3-已过期
		int used = giftMapper.countByExample(example);
		ReqResult r = ReqResult.getSuccess();
		r.setData("total", total);
		r.setData("used",used);
		r.setData("left",total - used);
		return r;
	}

	@Override
	public ReqResult getGiftRecent() {
		GiftExample example = new GiftExample();
		GiftExample.Criteria criteria = example.createCriteria();
		criteria.andStateNotEqualTo(0);
		example.setOrderByClause("mtime desc limit 5");
		List<Gift> used = giftMapper.selectByExample(example);
		List<GiftVo> gvs = new ArrayList<GiftVo>();
		if(null != used && used.size()>0){
			try {
				gvs = BeanCopy.copyList(used, GiftVo.class, BeanCopy.COPY2NULL);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//隐藏手机号的部分字段
			for(GiftVo gv:gvs){
				String mobile = gv.getMobile().trim();
				//String mo = mobile.substring(0,3)+ "****"+ mobile.substring(7,mobile.length());
				String mo = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
				gv.setMobile(mo);
			}
		}

		ReqResult r = ReqResult.getSuccess();
		r.setData(gvs);
		return r;
	}

	@Override
	public ReqResult saveGift(String name, String mobile, String couponId,
			String address) {
		ReqResult r = ReqResult.getSuccess();
		if(!MobileUtil.isMobile(mobile.trim())){
			r.setCode(ResultCode.ERRORCODE.MOBILE_NUMBER_ERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.MOBILE_NUMBER_ERROR);
			return r;
		}
		GiftExample example = new GiftExample();
		GiftExample.Criteria criteria = example.createCriteria();
		//已经领过的手机号不允许重复领取
		criteria.andMobileEqualTo(mobile.trim());
		criteria.andStateNotEqualTo(0);
		List<Gift> mobileUsed = giftMapper.selectByExample(example);
		if(null != mobileUsed && mobileUsed.size()>0){
			r.setCode(ResultCode.ERRORCODE.COUPON_MOBILE_HAD_USE);
			r.setMsgInfo(ResultCode.ERRORINFO.COUPON_MOBILE_HAD_USE);
			return r;
		}
		example = new GiftExample();
		criteria = example.createCriteria();
		criteria.andCouponIdEqualTo(couponId.trim());
		List<Gift> used = giftMapper.selectByExample(example);
		if(null == used || used.size() == 0 || used.size() >1){
			r.setCode(ResultCode.ERRORCODE.COUPON_UNFOUND);
			r.setMsgInfo(ResultCode.ERRORINFO.COUPON_UNFOUND);
			return r;
		}
		Gift gift = used.get(0);
		int state = gift.getState();
		if(state != 0){
			r.setCode(ResultCode.ERRORCODE.COUPON_TICKET_HAD_USE);
			r.setMsgInfo(ResultCode.ERRORINFO.COUPON_TICKET_HAD_USE);
			return r;
		}
		gift.setName(name.trim());
		gift.setMobile(mobile.trim());
		gift.setAddress(address.trim());
		gift.setState(2);
		giftMapper.updateByExampleSelective(gift, example);
		
		return r;
	}

}









