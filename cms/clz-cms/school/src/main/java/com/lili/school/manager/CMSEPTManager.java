package com.lili.school.manager;

import com.lili.cms.entity.PagedResult;
import com.lili.school.dto.EnrollPackageTemplateWithBLOBs;
import com.lili.school.model.EnrollPackageTemplateVo;
import com.lili.school.model.EptwbBDTO;

public interface CMSEPTManager {

	public EnrollPackageTemplateVo findOne(Integer ttid)  throws Exception;

	public PagedResult<EnrollPackageTemplateWithBLOBs> findBatch(EptwbBDTO dto)  throws Exception;
	
}
