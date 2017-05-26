package com.lili.pay.manager;

public interface BankManager {


    /**
     * 获取银行卡张数
     *
     * @return
     */
    int queryBankSize(Long userId, Integer userType);

}
