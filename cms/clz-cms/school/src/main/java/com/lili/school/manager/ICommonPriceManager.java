package com.lili.school.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.school.model.CommonPrice;

public interface ICommonPriceManager {

	PagedResult<CommonPrice> queryCommonPriceList(CommonPrice commonPrice);

	List<CommonPrice> queryComPriceList(String id);


}
