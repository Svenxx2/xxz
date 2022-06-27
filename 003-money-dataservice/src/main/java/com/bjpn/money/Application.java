package com.bjpn.money;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
@MapperScan("com.bjpn.money.mapper")  //在运行的主类添加注解包扫描，有这个注解以后，会自动去扫描路径中的mapper接口
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
