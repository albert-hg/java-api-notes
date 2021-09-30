package com.albert.practice;

public class MyBasicRunnable implements Runnable {
    private Long sleepTime;
        public MyBasicRunnable(Long sleepTime) {
            this.sleepTime = sleepTime;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(this.sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.sleepTime);
        }
}
