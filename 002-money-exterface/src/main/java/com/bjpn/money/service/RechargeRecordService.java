package com.bjpn.money.service;


import com.bjpn.money.model.RechargeRecord;

import java.util.Map;

//充值记录服务
public interface RechargeRecordService {


    ////生成订单插入到数据库中
    int recharge(RechargeRecord rechargeRecord);


    //通过订单号修改订单交易状态
    int updateRechargeStatus(String out_trade_no, int i);


}
