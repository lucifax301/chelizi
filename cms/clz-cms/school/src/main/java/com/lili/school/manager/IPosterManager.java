package com.lili.school.manager;

import com.lili.cms.entity.PagedResult;
import com.lili.school.model.PosterDTO;

public interface IPosterManager {

	PagedResult<PosterDTO> queryList(PosterDTO poster);

}
