package com.bjpn.money.mapper;

import com.bjpn.money.model.LoanInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LoanInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoanInfo record);

    int insertSelective(LoanInfo record);

    LoanInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoanInfo record);

    int updateByPrimaryKey(LoanInfo record);

    //首页：根据产品类型 数量 查询 产品信息
    List<LoanInfo> selectLoanInfosByTypeAndNum(Map<String, Object> parasMap);

    Double selectLoanInfoHistoryRateAvg();

    //查询总记录数
    Long selectLoanInfoCountByType(Integer ptype);

    //减少剩余可投金额
    Integer updateLeftMoneyByLoanId(Map<String, Object> map);


    //通过是否满标查询已满标的产品
    List<LoanInfo> selectloanInfosByProductionStats(int i);
}