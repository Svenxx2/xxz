package com.bjpn.money.mapper;

import com.bjpn.money.model.BidInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //让此接口交给springboot容器管理，同时提供注入@atupwired实现类的功能 都不需要new了
public interface BidInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BidInfo record);

    int insertSelective(BidInfo record);

    BidInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BidInfo record);

    int updateByPrimaryKey(BidInfo record);

    //累计成交额
    Double selectMoneySum();

    //根据产品ID查询产产品的投资记录BIDINFO
    List<BidInfo> selectBidInfosByLoanId(Integer loanId);
}