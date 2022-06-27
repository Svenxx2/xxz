package com.bjpn.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpn.money.mapper.BidInfoMapper;
import com.bjpn.money.mapper.FinanceAccountMapper;
import com.bjpn.money.mapper.IncomeRecordMapper;
import com.bjpn.money.mapper.LoanInfoMapper;
import com.bjpn.money.model.BidInfo;
import com.bjpn.money.model.FinanceAccount;
import com.bjpn.money.model.IncomeRecord;
import com.bjpn.money.model.LoanInfo;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = IncomeService.class,version = "1.0.0",timeout = 20000) //暴露服务给注册中心
@Component
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    IncomeRecordMapper incomeRecordMapper;
    @Autowired
    LoanInfoMapper loanInfoMapper;
    @Autowired
    BidInfoMapper bidInfoMapper;
    @Autowired
    FinanceAccountMapper financeAccountMapper;

    @Transactional
    @Override
    public void incomeGeneratePlan() {
        /**
         * 1、查询满标产品==》List
         * 2、遍历满标产品，根据产品查询 投资记录==》List
         * 3、遍历投资记录，生产收益计划
         * 4、修改产品状态为2
         */
        //查询满标产品
        List<LoanInfo> loanInfos = loanInfoMapper.selectloanInfosByProductionStats(1);
        //遍历满标产品
        for (LoanInfo loanInfo : loanInfos) {
            //根据满标产品查询产品的投资记录
           List<BidInfo> bidInfos =  bidInfoMapper.selectBidInfosByLoanId(loanInfo.getId());
           //遍历投资记录
            for (BidInfo bidInfo : bidInfos) {
                //根据投资记录生成收益计划
                IncomeRecord incomeRecord = new IncomeRecord();
                incomeRecord.setBidId(bidInfo.getId());
                incomeRecord.setUid(bidInfo.getUid());
                incomeRecord.setLoanId(bidInfo.getLoanId());
                incomeRecord.setBidMoney(bidInfo.getBidMoney());
                incomeRecord.setIncomeStatus(0);
                Date date = null;
                Double shouyi = null;

                ////计算收益：利率/100/365*本金*周期*30
                if (loanInfo.getProductType() == 0 ) {
                    //新手宝按天
                    date = DateUtils.addDays(loanInfo.getProductFullTime(), loanInfo.getCycle());
                    shouyi = loanInfo.getRate() / 100 / 365 * bidInfo.getBidMoney() * loanInfo.getCycle();
                } else {
                    //其他宝 按月
                    date = DateUtils.addMonths(loanInfo.getProductFullTime(), loanInfo.getCycle());
                    shouyi = loanInfo.getRate() / 100 / 365 * bidInfo.getBidMoney() * loanInfo.getCycle() *30;


                }
                incomeRecord.setIncomeDate(date);
                BigDecimal bd= new BigDecimal(shouyi);
                Double b = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                incomeRecord.setIncomeMoney(b);
                //把收益计划插入到数据库
                incomeRecordMapper.insertSelective(incomeRecord);

            }
            //生成结束后把产品状态修改成2 已满标且完成收益计划
           loanInfo.setProductStatus(2);
            loanInfoMapper.updateByPrimaryKeySelective(loanInfo);

        }

    }


    @Transactional
    @Override
    public void checkShouyi() {
        //定时器扫描： 今天到期，且状态位0 的收益记录  ==》返现==》修改状态位1

        //获取一下当前时间
        Date date = new Date();
        //获取今天到期，且状态位0 的收益记录
        List<IncomeRecord> incomeRecords = incomeRecordMapper.selectincomeRecordsBystatusAndIncomeDate(date, 0);

        //遍历收益记录
        for (IncomeRecord incomeRecord : incomeRecords) {
            Map<String, Object> map = new HashMap<>();
            Integer uid = incomeRecord.getUid();
            double shouyi = incomeRecord.getBidMoney() + incomeRecord.getIncomeMoney();
            map.put("uid", uid);
            map.put("shouyi", shouyi);

            //获得收益 账户余额变更
            financeAccountMapper.updateByUidAndShouyi(map);
            incomeRecord.setIncomeStatus(1);
            incomeRecordMapper.updateByPrimaryKeySelective(incomeRecord);
        }
    }
}
