package com.lili.pay.service;

import java.util.List;

import com.lili.common.vo.ReqResult;
import com.lili.pay.vo.DepositVo;

/**
 * 提现接口
 *
 * @author lzb
 */
public interface IDepositService {

    /**
     * 提现
     *
     * @param pw
     * @param money
     * @param type
     * @param bankCard
     * @param userId
     * @param userType
     * @param timestamp
     * @param sign
     * @return
     */
    ReqResult deposit(String pw, String money, String type, String bankCard, String userId
            , String userType, String timestamp, String sign);


    /**
     * 提现记录
     *
     * @param pageNo
     * @param pageSize
     * @param userId
     * @param userType
     * @param timestamp
     * @param sign
     * @return
     */
    ReqResult queryDepositList(String pageNo, String pageSize, String userId, String userType,
                               String timestamp, String sign);

    /**
     * 剩余提现次数
     *
     * @param userId
     * @param userType
     * @param timestamp
     * @param sign
     * @return
     */
    ReqResult leftDepositCount(String userId, String userType, String timestamp, String sign);

    /**
     * 驾校提现
     *
     * @param passwd
     * @param money
     * @param schoolId
     * @param timestamp
     * @param sign
     * @return
     */
    ReqResult schDeposit(String passwd, String money, String schoolId, String timestamp, String sign);


    List<DepositVo> queryHasDeposit(DepositVo depositVo);


	void updateDepositList(List<DepositVo> depList, Integer status);

}
