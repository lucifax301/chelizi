package com.lili.coach.manager;

import java.util.List;

import com.lili.coach.dto.ErrorAppeal;
import com.lili.coach.dto.ErrorAppealItem;

public interface CoachErrorAppealManager {

	/**
	 * 获取错误申述选项列表
	 * @return
	 */
	List <ErrorAppealItem> getErrorAppealItem();
	
	/**
	 * 提交错误申诉
	 * @param appeal
	 * @return
	 */
	public int addErrorAppeal(ErrorAppeal appeal);
	
}
