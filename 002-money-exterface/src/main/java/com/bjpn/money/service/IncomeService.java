package com.bjpn.money.service;

public interface IncomeService {

    void incomeGeneratePlan();

    //定时器扫描： 今天到期，且状态位0 的收益记录  ==》返现==》修改状态位1
    void checkShouyi();
}
