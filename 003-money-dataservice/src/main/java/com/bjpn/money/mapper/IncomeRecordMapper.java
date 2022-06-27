package com.bjpn.money.mapper;

import com.bjpn.money.model.IncomeRecord;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository //让此接口交给springboot容器管理，同时提供注入@atupwired实现类的功能 都不需要new了
public interface IncomeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IncomeRecord record);

    int insertSelective(IncomeRecord record);

    IncomeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IncomeRecord record);

    int updateByPrimaryKey(IncomeRecord record);
    //获取今天到期，且状态位0 的收益记录
    List<IncomeRecord> selectincomeRecordsBystatusAndIncomeDate(Date date, int i);
}