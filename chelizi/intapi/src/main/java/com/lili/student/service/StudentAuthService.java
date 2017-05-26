package com.lili.student.service;

import java.util.List;

public interface StudentAuthService {
	/**
	 * 更改学员认证状态，由cms后台调用
	 * @param type		认证类型：0-身份证；1-驾驶证；
	 * @param state		认证状态：0：未认证，1：认证中，2：已认证，3：认证失败 4---已过期 5---已吊销'
	 * @param ids	主键id列表
	 * @param remark		备注
	 * @return	0-成功；1-失败
	 */
	public int changeState(int type, int state, List<Long> ids, String remark);
}
