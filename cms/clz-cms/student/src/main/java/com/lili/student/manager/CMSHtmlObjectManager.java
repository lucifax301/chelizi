package com.lili.student.manager;

import com.lili.cms.entity.PagedResult;
import com.lili.file.dto.HtmlObject;
import com.lili.student.model.HtmlObjectBDTO;

public interface CMSHtmlObjectManager {

    HtmlObject findOne(Integer id);

    PagedResult<HtmlObject> findBatch(HtmlObjectBDTO dto);
	
}
