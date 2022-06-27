package com.bjpn.money.service;

public interface RedisService {
    void push(String phone, String code);

    String get(String phone);
}
