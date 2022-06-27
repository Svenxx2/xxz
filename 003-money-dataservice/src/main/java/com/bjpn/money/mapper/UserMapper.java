package com.bjpn.money.mapper;

import com.bjpn.money.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    long selectUserCount();
    ////验证手机号码是否已经注册：不是根据手机号码 查询用户信息，而是根据手机号码查询用户数量
    Integer checkCountByPhone(String phone);


    //依照账号与密码查找数据
    User selectByPhoneAndPwd(String phone, String loginPassword);
}