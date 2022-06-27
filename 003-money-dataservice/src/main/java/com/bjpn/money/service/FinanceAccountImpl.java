package com.bjpn.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpn.money.mapper.FinanceAccountMapper;
import com.bjpn.money.model.FinanceAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service(interfaceClass = FinanceAccountService.class,version = "1.0.0",timeout = 20000) //暴露服务给注册中心
@Component //交给springboot容器管理
public class FinanceAccountImpl implements FinanceAccountService {
    @Autowired//注入一个该类型的实现类方便调用方法 不用new
    FinanceAccountMapper financeAccountMapper;

    //使用userId 去账户数据表里查余额
    @Override
    public FinanceAccount queryFinanceAccountByUserId(Integer userId) {

        return financeAccountMapper.selectFinanceAccountByUserId(userId);
    }
}
