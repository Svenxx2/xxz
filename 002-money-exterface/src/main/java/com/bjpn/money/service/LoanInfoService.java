package com.bjpn.money.service;

import com.bjpn.money.model.LoanInfo;
import com.bjpn.money.utils.PageModel;

import java.util.List;
import java.util.Map;

public interface LoanInfoService  {

    //年化收益率
    Double queryLoanInfoHistoryRateAvg();

    List<LoanInfo> queryLoanInfosByTypeAndNum(Map<String, Object> parasMap);

    List<LoanInfo> queryLoanInfoByTypeAndpage(Integer ptype, PageModel pageModel);

    Long queryLoanInfoCountByType(Integer ptype);

    LoanInfo queryLoanInfosByLoanId(Integer loanId);
}
