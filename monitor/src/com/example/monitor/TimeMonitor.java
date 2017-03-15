package com.example.monitor;

/**
 * 时间监视器
 * Created on 2017/3/14 下午4:03.
 * leo linxiaotao1993@vip.qq.com
 */

public class TimeMonitor {

    private static long sStartTime;

    public static void start(long time) {
        sStartTime = time;
    }

    public static void calculation(long time) {
        System.out.println(String.format("运行时间为:%s毫秒", String.valueOf(time - sStartTime)));
    }
}
