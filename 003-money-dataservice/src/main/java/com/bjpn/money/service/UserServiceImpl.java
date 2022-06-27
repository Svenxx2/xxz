package com.bjpn.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpn.money.mapper.FinanceAccountMapper;
import com.bjpn.money.mapper.UserMapper;
import com.bjpn.money.model.FinanceAccount;
import com.bjpn.money.model.User;
import com.bjpn.money.utils.Contants;
import com.bjpn.money.utils.ThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//用户业务实现类
@Service(interfaceClass = UserService.class, version = "1.0.0", timeout = 20000)
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    FinanceAccountMapper financeAccountMapper;

    @Override
    public long queryUserCount() {
        Long userCount = (Long)redisTemplate.opsForValue().get(Contants.USER_COUNT);
        if (userCount == null) {
            synchronized (this) {
                userCount = (Long)redisTemplate.opsForValue().get(Contants.USER_COUNT);
                if (userCount == null) {
                    userCount = userMapper.selectUserCount();
                    redisTemplate.opsForValue().set(Contants.USER_COUNT, userCount, 20, TimeUnit.SECONDS);
                }

            }
        }



        return userCount;
    }


    @Override
    public Integer checkCountByPhone(String phone) {
        //验证手机号码是否已经注册：不是根据手机号码 查询用户信息，而是根据手机号码查询用户数量
        return userMapper.checkCountByPhone(phone);
    }

    @Override
    public User register(String phone, String loginPassword) {
        User user = new User();
        user.setAddTime(new Date());
        user.setLoginPassword(loginPassword);
        user.setPhone(phone);
        int num = userMapper.insertSelective(user);
        if (num == 1) {
            //插入成功，注册成功

            ExecutorService executorService = Executors.newFixedThreadPool(8);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    FinanceAccount financeAccount = new FinanceAccount();
                    financeAccount.setUid(user.getId());
                    financeAccount.setAvailableMoney(888d);
                    int i = financeAccountMapper.insertSelective(financeAccount);
                }
            });

            //送大礼包888元  ,新建一个线程给这个礼包代码自己玩去



            return user;
        }


        return null;
    }

    @Override
    public User xiugaiByUserId(User user, String idCard, String realName) {

        user.setIdCard(idCard);
        user.setName(realName);
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i == 0) {
            //修改失败
            return null;

        }
        return user;
    }

    @Override
    public User queryUserByPhoneAndPwd(String phone, String loginPassword) {
        User user = userMapper.selectByPhoneAndPwd(phone, loginPassword);

//        //放到多线程中#########################################
        if (user != null) {
            ThreadPool.getPool().submit(new Runnable() {
                @Override
                public void run() {
                    //添加登陆时间
                    user.setLastLoginTime(new Date());
                    userMapper.updateByPrimaryKeySelective(user);
                }
            });

        }


        return user;
    }


}
