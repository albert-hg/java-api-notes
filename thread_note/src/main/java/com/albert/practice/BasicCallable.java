package com.albert.practice;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class BasicCallable {

    public static void byImplementsCallable() {
        Callable<Long> c1 = new MyBasicCallable(3000L);
        FutureTask<Long> f1 = new FutureTask<>(c1);
        Thread t1 = new Thread(f1);
        Callable<Long> c2 = new MyBasicCallable(1000L);
        FutureTask<Long> f2 = new FutureTask<>(c2);
        Thread t2 = new Thread(f2);

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }

    public static void byNewCallableInFutureTask() {
        Callable<Long> c1 = new Callable<Long>(){
            Long sleepTime = 2000L;
            @Override
            public Long call() {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return sleepTime;
            }
        };
        Callable<Long> c2 = new Callable<Long>(){
            Long sleepTime = 3000L;
            @Override
            public Long call() {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return sleepTime;
            }
        };
        FutureTask<Long> f1 = new FutureTask<>(c1);
        FutureTask<Long> f2 = new FutureTask<>(c2);
        Thread t1 = new Thread(f1);
        Thread t2 = new Thread(f2);
        t1.start();
        t2.start();
        try {
            f1.get();
            System.out.println(f1.get());
            f2.get();
            System.out.println(f2.get());
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }

    public static void byLambdaInThread() {
        FutureTask<Long> f1 = new FutureTask<>(() -> {
            Long sleepTime = 2000L;
            try {
                Thread.sleep(sleepTime);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            return sleepTime;
        });
        FutureTask<Long> f2 = new FutureTask<>(() -> {
            Long sleepTime = 3000L;
            try {
                Thread.sleep(sleepTime);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            return sleepTime;
        });
        Thread t1 = new Thread(f1);
        Thread t2 = new Thread(f2);
        t1.start();
        t2.start();
        try {
            System.out.println(f1.get());
            System.out.println(f2.get());
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}