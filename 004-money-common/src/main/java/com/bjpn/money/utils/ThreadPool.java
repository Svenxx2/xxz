package com.bjpn.money.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
     static ExecutorService pool = Executors.newFixedThreadPool(16);

    public static ExecutorService getPool() {
        return pool;
    }
}
