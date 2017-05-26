package com.lili.finance.service;

import java.io.File;

import com.lili.cms.entity.ResponseMessage;
import com.lili.finance.vo.BonusDetailVo;
import com.lili.finance.vo.BonusVo;

/**
 * 奖金接口
 * @author lzb
 *
 */
public interface ICmsBonusService {
	public ResponseMessage upload(File outfile, String tagFileName, String creator);
	
	public ResponseMessage uploadNew(byte[] b, String tagFileName, String creator);

    public String query(BonusVo bonusVo);
    
    public String queryDetail(BonusDetailVo bonusDetailVo);
    
    public ResponseMessage submit(BonusVo bonusVo);
    
    public ResponseMessage grant(BonusVo bonusVo);
    
    public ResponseMessage delete(BonusVo bonusVo);
    
    public ResponseMessage finReject(BonusVo bonusVo);


}
