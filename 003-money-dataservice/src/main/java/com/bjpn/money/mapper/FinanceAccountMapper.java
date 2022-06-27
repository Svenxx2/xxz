package com.bjpn.money.mapper;

import com.bjpn.money.model.FinanceAccount;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository //让此接口交给springboot容器管理，同时提供注入@atupwired实现类的功能 都不需要new了
public interface FinanceAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceAccount record);

    int insertSelective(FinanceAccount record);

    FinanceAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceAccount record);

    int updateByPrimaryKey(FinanceAccount record);
    ////使用userId 去账户数据表里查余额
    FinanceAccount selectFinanceAccountByUserId(Integer userId);

    //账户余额减少
    Integer updateAvaMoneyByUid(Map<String ,Object> map);

    ////获得收益 账户余额变更
    void updateByUidAndShouyi(Map<String, Object> map);
}