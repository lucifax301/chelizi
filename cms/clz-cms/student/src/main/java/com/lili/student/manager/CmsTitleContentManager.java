package com.lili.student.manager;

import com.lili.cms.entity.PagedResult;
import com.lili.student.model.TitleTypeVo;
import com.lili.student.model.TypeContentVo;

public interface CmsTitleContentManager {

	PagedResult<TitleTypeVo> queryTitleList(TitleTypeVo tt);

	int addTitle(TitleTypeVo tt);

	int deleteTitle(TitleTypeVo tt);

	int updateTitle(TitleTypeVo tt);

	TypeContentVo queryContent(TypeContentVo tc);

	PagedResult<TypeContentVo> queryContentList(TypeContentVo tc);

	int addContent(TypeContentVo tc);

	int updateContent(TypeContentVo tc);

	int releaseContent(TypeContentVo tc);
	
}
