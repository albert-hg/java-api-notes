package com.albert.practice;

public class BasicRunnable {

    public static void byImplementsRunnable() {
        // implements a Runnable
        Runnable mr1 = new MyBasicRunnable(3000L);
        Thread t1 = new Thread(mr1);
        t1.start();
        Runnable mr2 = new MyBasicRunnable(3500L);
        Thread t2 = new Thread(mr2);
        t2.start();

        // must join these 2 thread to wait all threads finish
        try {
            t1.join();
            t2.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }

    public static void byNewRunnableInThread() {
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Long sleepTime = 5100L;
                    Thread.sleep(sleepTime);
                    System.out.println(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Long sleepTime = 4100L;
                    Thread.sleep(sleepTime);
                    System.out.println(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println("done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void byLambdaInThread() {
        Thread t1 = new Thread(() -> {
            try {
                Long sleepTime = 3100L;
                Thread.sleep(sleepTime);
                System.out.println(sleepTime);
            } catch(InterruptedException e) {

            }
        });
        Thread t2 = new Thread(() -> {
            try {
                Long sleepTime = 4100L;
                Thread.sleep(sleepTime);
                System.out.println(sleepTime);
            } catch(InterruptedException e) {

            }
        });
        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println("done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}