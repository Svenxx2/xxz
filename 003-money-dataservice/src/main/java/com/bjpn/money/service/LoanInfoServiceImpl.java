package com.bjpn.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpn.money.mapper.LoanInfoMapper;
import com.bjpn.money.model.LoanInfo;
import com.bjpn.money.utils.Contants;
import com.bjpn.money.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service(interfaceClass = LoanInfoService.class, version = "1.0.0", timeout = 20000)
@Component
public class LoanInfoServiceImpl implements LoanInfoService {
    @Autowired
    LoanInfoMapper loanInfoMapper;
    //产品业务实现类
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Double queryLoanInfoHistoryRateAvg() {
        Double loanInfoHistoryRateAvg = (Double) redisTemplate.opsForValue().get(Contants.LOAN_INFO_HISTORY_RATE_AVG);
        if (loanInfoHistoryRateAvg == null) {
            synchronized (this) {
                loanInfoHistoryRateAvg = (Double) redisTemplate.opsForValue().get(Contants.LOAN_INFO_HISTORY_RATE_AVG);
                if (loanInfoHistoryRateAvg == null) {
                    loanInfoHistoryRateAvg = loanInfoMapper.selectLoanInfoHistoryRateAvg();
                    redisTemplate.opsForValue().set(Contants.LOAN_INFO_HISTORY_RATE_AVG, loanInfoHistoryRateAvg, 20, TimeUnit.SECONDS);

                }
            }
        }
        return loanInfoHistoryRateAvg;
    }

    @Override
    public List<LoanInfo> queryLoanInfosByTypeAndNum(Map<String, Object> parasMap) {
        return loanInfoMapper.selectLoanInfosByTypeAndNum(parasMap);
    }

    @Override
    public List<LoanInfo> queryLoanInfoByTypeAndpage(Integer ptype, PageModel pageModel) {

        Map<String,Object> parasMap = new HashMap();
        parasMap.put("ptype", ptype);
        parasMap.put("start", (pageModel.getCunPage()-1)*pageModel.getPageSize());
        parasMap.put("content", pageModel.getPageSize());

        return  loanInfoMapper.selectLoanInfosByTypeAndNum(parasMap);
    }

    @Override
    public Long queryLoanInfoCountByType(Integer ptype) {
        return loanInfoMapper.selectLoanInfoCountByType(ptype);
    }

    @Override
    public LoanInfo queryLoanInfosByLoanId(Integer loanId) {
        return loanInfoMapper.selectByPrimaryKey(loanId);
    }
}
