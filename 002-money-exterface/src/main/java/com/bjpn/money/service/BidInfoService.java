package com.bjpn.money.service;

//投资业务接口

import com.bjpn.money.model.BidInfo;

import java.util.List;
import java.util.Map;

public interface BidInfoService {


    //查询累计成交额

    Double queryMoneySum();

    //根据产品ID查询产产品的投资记录BIDINFO
    List<BidInfo> queryBidInfosByLoanId(Integer loanId);


    //立即投资,添加投资记录,减少余额,剩余可投金额减少,可能修改产品状态码
    String bidInvest(Map<String, Object> map);
}
