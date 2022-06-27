package com.bjpn.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpn.money.mapper.RechargeRecordMapper;
import com.bjpn.money.model.RechargeRecord;
import com.bjpn.money.utils.RegInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service(interfaceClass = RechargeRecordService.class,version = "1.0.0",timeout = 20000) //暴露服务给注册中心
@Component
public class RechargeRecordServiceImpl implements RechargeRecordService {
    @Autowired
    RechargeRecordMapper rechargeRecordMapper;


    @Override
    public int recharge(RechargeRecord rechargeRecord) {
        return rechargeRecordMapper.insertSelective(rechargeRecord);
    }

    @Override
    public int updateRechargeStatus(String out_trade_no, int i) {
        return rechargeRecordMapper.updateStatusByRechargeNo(out_trade_no, i);

    }


}
