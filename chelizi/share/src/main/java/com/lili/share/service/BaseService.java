package com.lili.share.service;

public interface BaseService {
	public String getPkFieldName();
	public String getProjectVoKey();
	public <T> T getMaxPk();
	public boolean isNumPk();
}
