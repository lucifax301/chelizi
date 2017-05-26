package com.lili.file.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.vo.ReqResult;
import com.lili.file.dto.Poster;
import com.lili.file.manager.PosterManager;
import com.lili.file.service.PosterService;

public class PosterServiceImpl implements PosterService {
	@Autowired
	PosterManager posterManager;
	@Override
	public ReqResult getPoster(String userType) {
		return posterManager.getPoster(userType);
	}

	

}
