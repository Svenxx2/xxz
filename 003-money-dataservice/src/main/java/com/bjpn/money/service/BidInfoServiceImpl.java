package com.bjpn.money.service;

import ch.qos.logback.core.util.TimeUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.bjpn.money.mapper.BidInfoMapper;
import com.bjpn.money.mapper.FinanceAccountMapper;
import com.bjpn.money.mapper.LoanInfoMapper;
import com.bjpn.money.model.BidInfo;
import com.bjpn.money.model.LoanInfo;
import com.bjpn.money.utils.Contants;
import com.bjpn.money.utils.RegInfo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service(interfaceClass = BidInfoService.class,version = "1.0.0",timeout = 20000) //暴露服务给注册中心
@Component  //让此类交给springboot容器管理
public class BidInfoServiceImpl implements BidInfoService {

    @Autowired //注入一个该类型的实现类方便调用方法 不用new
    BidInfoMapper bidInfoMapper;
    @Autowired //springboot继承redis自动提供一个类模型
    RedisTemplate redisTemplate;
    @Autowired
    LoanInfoMapper loanInfoMapper;
    @Autowired
    UserService userService;
    @Autowired
    FinanceAccountMapper financeAccountMapper;
//    static Map<String,Object> onlymap ;

//    @Autowired
//    SqlSessionTemplate sqlSessionTemplate;


    @Override
    public Double queryMoneySum() {
        //序列化，用于美观KEY的代码，不会影响正常程序以及业务
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        Double bidMoneySum = (Double)redisTemplate.opsForValue().get(Contants.BID_MONEY_SUM);
//        if (bidMoneySum == null) {
//            bidMoneySum = bidInfoMapper.selectMoneySum();
//            redisTemplate.opsForValue().set(Contants.BID_MONEY_SUM, bidMoneySum, 20, TimeUnit.SECONDS);
//        }
//        return bidMoneySum;

        //尝试从缓存中找到该数据
        //缓存中都是key value 形式的数据，一般常用字符串
        Double bidMoneySum = (Double)redisTemplate.opsForValue().get(Contants.BID_MONEY_SUM);
        if (bidMoneySum == null) {
            //缓存没有找数据库，防止缓存穿透，用同步
            synchronized (this) {
                //进来后要再次冲缓存中找一下有没有这个数据
                bidMoneySum = (Double)redisTemplate.opsForValue().get(Contants.BID_MONEY_SUM);
                if (bidMoneySum == null) {
                    //缓存里没有这个数据，连接数据库去找
                    bidMoneySum = bidInfoMapper.selectMoneySum();
                    //再把这个数据放到缓存中方便以后使用
                    redisTemplate.opsForValue().set(Contants.BID_MONEY_SUM, bidMoneySum, 20,TimeUnit.SECONDS );//timeout:放在缓存中的时间

                }


            }

        }

        return bidMoneySum;
    }

    @Override
    public List<BidInfo> queryBidInfosByLoanId(Integer loanId) {
        return bidInfoMapper.selectBidInfosByLoanId(loanId);
    }


    //立即投资,添加投资记录,减少余额,剩余可投金额减少,可能修改产品状态码
    @Transactional //事务
    @Override
    public String bidInvest(Map<String, Object> map) {
        /**
         * 1、剩余可投金额减少
         * 2、账户减少
         * 3、可能修改产品状态
         * 4、添加投资记录
         */

        //查一下产品的数据
        LoanInfo loanInfo = loanInfoMapper.selectByPrimaryKey((Integer) map.get("loanId"));



//        synchronized (loan){
//            //1、剩余可投金额减少: 存在并发  数据不安全   需要同步处理
//            // 2、账户减少：不同账号  不存在并发，     //同一账号 存在并发   多终端同时登录
//            //3、可能修改产品状态：存在并发  数据安全   不需要同步
//            //4、添加投资记录：不存在并发
//        }
        //0、获取版本号
        Integer version = loanInfo.getVersion();
        map.put("version",version);

        //1、剩余可投金额减少
        int i =loanInfoMapper.updateLeftMoneyByLoanId(map);
        if (i == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Contants.LEFTMONEY_REDUCEFAIL;
        }

        //2、账户减少
         i = financeAccountMapper.updateAvaMoneyByUid(map);
        if (i == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Contants.ACCOUNT_REDUCEFAIL;
        }


        //3、可能修改产品状态
       loanInfo = loanInfoMapper.selectByPrimaryKey((Integer) map.get("loanId"));
        if (loanInfo.getLeftProductMoney() == 0 && loanInfo.getProductStatus() == 0) {
            loanInfo.setProductStatus(1);
            loanInfo.setProductFullTime(new Date());
            i = loanInfoMapper.updateByPrimaryKeySelective(loanInfo);
            if (i == 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Contants.PRODUCTSTATE_UPDATEFAIL;
            }
        }

//        4、添加投资记录
        BidInfo bidInfo = new BidInfo();
        bidInfo.setBidMoney((Double) map.get("bidMoney"));
        bidInfo.setBidStatus(1);
        bidInfo.setBidTime(new Date());
        bidInfo.setLoanId((Integer) map.get("loanId"));
        bidInfo.setUid((Integer) map.get("userId"));
        i = bidInfoMapper.insertSelective(bidInfo);
        if (i == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Contants.ADDBIDINFO_FAIL;
        }
        return Contants.BID_SUCCESS;
    }
}
