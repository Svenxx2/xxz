package com.bjpn.money.utils;

import java.util.HashMap;
import java.util.Map;

public class RegInfo {
    static Map<String, Object> map = new HashMap<>();
    public static Map<String, Object> success() {
        map.put("code", 1);


        return map;

    }
    public static Map<String, Object> success(String string) {
        map.put("code", 1);
        map.put("message", string);


        return map;

    }
    public static Map<String, Object> fail() {
        map.put("code", 0);

        return map;

    }
    public static Map<String, Object> fail(String string) {
        map.put("code", 0);
        map.put("message", string);
        return map;

    }

    //生成随机数
    public static String gerenateCode(Integer len){
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<len;i++){
            stringBuilder.append(Math.round(Math.random()*9));
        }
        return stringBuilder.toString();
    }

}
