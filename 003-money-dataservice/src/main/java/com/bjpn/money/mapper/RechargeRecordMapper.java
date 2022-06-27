package com.bjpn.money.mapper;

import com.bjpn.money.model.RechargeRecord;
import org.springframework.stereotype.Repository;


@Repository
public interface RechargeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);

    int updateStatusByRechargeNo(String out_trade_no, int i);
}