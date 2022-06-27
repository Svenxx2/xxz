package com.bjpn.money.service;

import com.bjpn.money.model.FinanceAccount;

public interface FinanceAccountService {
    //使用userId 去账户数据表里查余额
    FinanceAccount queryFinanceAccountByUserId(Integer id);
}
