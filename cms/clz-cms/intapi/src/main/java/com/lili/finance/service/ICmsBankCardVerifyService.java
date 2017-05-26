package com.lili.finance.service;

import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.finance.vo.BankCardVerifyVo;

/**
 * 绑定银行卡接口
 * @author lzb
 *
 */
public interface ICmsBankCardVerifyService {

    public String query(BankCardVerifyVo bankCardVo);
    
    public ResponseMessage pass(String checker, String remark, String id);
    
    public ResponseMessage reject(String checker, String remark, String id);
    
    public List<BankCardVerifyVo> downLoad(BankCardVerifyVo bankCardVo);
    
    public List<BankCardVerifyVo> downLoadExcel(BankCardVerifyVo bankCardVo);
    
    public  List<BankCardVerifyVo> queryBankBoundList(BankCardVerifyVo bankCardVo);


}
