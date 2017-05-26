package com.lili.student.mapper.dao.common;

import java.util.List;

import com.lili.file.dto.HtmlObject;
import com.lili.student.model.HtmlObjectBDTO;

public interface HtmlObjectMapper {

    HtmlObject selectByPrimaryKey(Integer id);

    List<HtmlObject> findBatch(HtmlObjectBDTO dto);
    
}