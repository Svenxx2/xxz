package com.bjpn.money;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpn.money.service.IncomeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpringTask {
    @Reference(interfaceClass = IncomeService.class,version = "1.0.0",timeout = 20000)
    IncomeService incomeService;

    @Scheduled(cron = "0/5 * * * * ?")  //只需要一个注解既可以完成定时器任务
    public void generatePlan() {
        System.out.println("start");
        incomeService.incomeGeneratePlan();
        System.out.println("end");
    }


    //课后：完成收益返现
    @Scheduled(cron = "0/5 * * * * ?")  //只需要一个注解既可以完成定时器任务
    public  void planFan(){


        //定时器扫描： 今天到期，且状态位0 的收益记录  ==》返现==》修改状态位1
        System.out.println("start1");
        incomeService.checkShouyi();
        System.out.println("end2");
    }
}
