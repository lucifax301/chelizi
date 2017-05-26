package com.lili.finance.service;

import com.lili.finance.vo.TaskFileVo;

/**
 * 奖金接口
 * @author lzb
 *
 */
public interface ICmsFileTaskService {

    public String insert(TaskFileVo taskFile);

	public String getUptoken(String userId, String userType, String tokenId);

	public String getDowntoken(String userId, String userType, String picType, String style, String carId, String isCheckCar);

	public String getDownUrlByKey(String picName);

	public String getUpPublicToken(String userId, String userType, String tokenId);
    


}
