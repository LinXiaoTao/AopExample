package com.example.monitor;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2017/3/15 下午3:24
 * leo linxiaotao1993@vip.qq.com
 */
public class Main {
    public static void main(String[] args) {
        test();
    }

    @Monitor
    private  static void test(){
        TimeMonitor.start(System.currentTimeMillis());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TimeMonitor.calculation(System.currentTimeMillis());
    }
}
