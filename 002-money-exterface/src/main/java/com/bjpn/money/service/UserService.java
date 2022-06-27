package com.bjpn.money.service;

import com.bjpn.money.model.User;
import org.springframework.beans.factory.annotation.Autowired;
//用户业务接口
public interface UserService {

    long queryUserCount();

    Integer checkCountByPhone(String phone);

    User register(String phone, String loginPassword);


    User xiugaiByUserId(User user, String idCard, String realName);

    User queryUserByPhoneAndPwd(String phone, String loginPassword);
}
