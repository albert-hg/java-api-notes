package com.albert.practice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {
    /* 
    Theard在創建時會佔據一些資源，若需要大量創建時則會造成資源的浪費。

    若我們可以在一開始的時候，就先建立一些Thread起來放著，
    等到要用的時候再拿來執行，並且用完過後再讓他持續等待下一個工作的來臨，
    那麼就可以避免大量建立Thread時的資源浪費。

    而先創建的「那些Thread」，就稱作為「Thread Pool」。

    建立ThreadPool的方法皆可以透過java.util.concurrent.Executors來創建，
    其靜態工廠總共有5總建立ThreadPool的類型:

        1. newCachedThreadPool() or newCachedThreadPool(ThreadFactory threadFactory)
        2. newFixedThreadPool(int nThreads) or newFixedThreadPool(int nThreads, ThreadFactory threadFactory)
        3. newSingleThreadExecutor() or newSingleThreadExecutor(ThreadFactory threadFactory)
        4. newScheduledThreadPool(int corePoolSize) or newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory)
        5. newSingleThreadScheduledExecutor() or newSingleThreadScheduledExecutor(ThreadFactory threadFactory)

    這些靜態工廠產生的ThreadPool都是ExecutorService.class，他繼承了Executor.class。
    在Executor.class內有一種執行任務的方式：

        1. void execute(Runnable command);

    使用execute()可以動態性的加入Runnable(任務)於任務列隊中。
    而ExecutorService.class內有兩種執行任務的方式：

        1. <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)  or
           <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
        2. <T> T invokeAny(Collection<? extends Callable<T>> tasks)  or
           <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
        3. <T> Future<T> submit(Callable<T> task) or
           <T> Future<T> submit(Runnable task, T result) or
           Future<?> submit(Runnable task)

    invokeAll vs invokeAny vs submit 之後再討論。

    因為ThrealPool內的Thread會因為等待新任務的來臨，所以如果想要一執行完所有的任務後就停止程序的話，
    則必須正確的關閉ExecutorService，有以下幾種方法：

        1. void shutdown(); 不再接受新任務，等待當前所有任務完成後關閉ExecutorService
        2. List<Runnable> shutdownNow(); 不再接受新任務，並且「直接關閉(=終止)」ExecutorService，並返回還沒執行的任務
        3. boolean isShutdown(); 判斷ExecutorService是否已經關閉不再接受新任務
        4. boolean isTerminated(); 判斷ExecutorService是否已經終止了
    */

    public static void newCachedThreadPool() {
        /* 
        創建一個有彈性的ThreadPool，如果所需的ThreadSize超過ThreadPool的大小，那麼就會自動新增Thread於Pool中。
        如果被創建的Thread經過60秒後沒有新的任務，那麼這個Thread就會被銷毀。
        Pros: 靈活，建立Thread的上限可為Interger.MAX_VALUE。
        Cons: 因為沒有大小的限制，所以當數量非常大的時候，系統可能無法負擔，導致崩潰。
        */
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i=0; i<20; i++) {
            final int id = i;
            es.execute(() -> {
                System.out.println(id + "_" + Thread.currentThread().getName());
            });
        }
        // es.shutdown();
        /* output: 
        0_pool-1-thread-1
        9_pool-1-thread-10
        8_pool-1-thread-9
        7_pool-1-thread-8
        6_pool-1-thread-7
        5_pool-1-thread-6
        4_pool-1-thread-5
        3_pool-1-thread-4
        1_pool-1-thread-2
        2_pool-1-thread-3
        15_pool-1-thread-16
        16_pool-1-thread-17
        17_pool-1-thread-18
        14_pool-1-thread-15
        12_pool-1-thread-13
        18_pool-1-thread-1
        19_pool-1-thread-10
        11_pool-1-thread-12
        13_pool-1-thread-14
        10_pool-1-thread-11
        ...等待60秒後程式才會停止，除非加入shoutdown();
        */
    }

    public static void newFixedThreadPool() {
        /* 
        Executors.newFixedThreadPool(n);
        固定ThreadPool的Size，可以避免newCachedThreadPool無線建立Thread的缺點。
        */
        ExecutorService es = Executors.newFixedThreadPool(4);
        for (int i=0; i<20; i++) {
            final int id = i;
            es.execute(() -> {System.out.println(id + "_" + Thread.currentThread().getName());});
        }
        /* output:
        1_pool-1-thread-2
        3_pool-1-thread-4
        0_pool-1-thread-1
        2_pool-1-thread-3
        6_pool-1-thread-1
        5_pool-1-thread-4
        4_pool-1-thread-2
        9_pool-1-thread-4
        8_pool-1-thread-1
        7_pool-1-thread-3
        12_pool-1-thread-1
        11_pool-1-thread-4
        10_pool-1-thread-2
        15_pool-1-thread-4
        14_pool-1-thread-1
        13_pool-1-thread-3
        18_pool-1-thread-1
        17_pool-1-thread-4
        16_pool-1-thread-2
        19_pool-1-thread-3
        ...永遠不會停止，除非加入shoutdown()
        */
    }

    public static void newSingleThreadExecutor() {
        /* 
        
        */
        ExecutorService es = Executors.newSingleThreadExecutor();
        for (int i=0; i<20; i++) {
            final int id = i;
            es.execute(() -> {System.out.println(id + "_" + Thread.currentThread().getName());});
        }
        es.shutdown();
    }

    public static void newScheduledThreadPool_schedule() {
        /* 
        
        */
        ScheduledExecutorService es = Executors.newScheduledThreadPool(4);
        for (int i=0; i<20; i++) {
            final int id = i;
            es.schedule(() -> {
                System.out.println(id + "_" + Thread.currentThread().getName());
            }, 1000, TimeUnit.MILLISECONDS);
        }
        es.shutdown();
    }

    public static void newScheduledThreadPool_scheduleAtFixedRate() {
        /* 
        https://www.huaweicloud.com/articles/0a999f61ad280491640e4bde68982744.html
        */
        ScheduledExecutorService es = Executors.newScheduledThreadPool(4);
        for (int i=0; i<20; i++) {
            final int id = i;
            es.scheduleAtFixedRate(() -> {
                System.out.println(id + "_" + Thread.currentThread().getName());
            }, 1, 5000, TimeUnit.MILLISECONDS);
        }
        // es.shutdown();
    }

    public static void newSingleThreadScheduledExecutor() {
        /* 
        
        */
        ExecutorService es = Executors.newSingleThreadScheduledExecutor();
    }


}
