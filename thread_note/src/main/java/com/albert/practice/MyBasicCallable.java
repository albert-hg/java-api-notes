package com.albert.practice;

import java.util.concurrent.Callable;

public class MyBasicCallable implements Callable<Long>{
    private Long sleepTime;
    public MyBasicCallable(Long sleepTime) {
        this.sleepTime = sleepTime;
    }
    @Override
    public Long call() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sleepTime;
    }
}
