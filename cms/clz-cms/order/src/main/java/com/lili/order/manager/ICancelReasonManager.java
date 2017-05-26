package com.lili.order.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.order.vo.CancelReason;

public interface ICancelReasonManager {

	PagedResult<CancelReason> queryCancelReasonList(CancelReason cancelReason);

	List<CancelReason> queryCancelList(String id);


}
