package com.minggow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadPoolExecutorTest {

    @Test
    public void coreThreadTimeOutTest() {
        /*
         * 设置核心线程和最大线程都是5个的线程池
         */
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(5, 5,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<>(),
                        Executors.defaultThreadFactory());
        /*
         * 设置核心线程超时时间是10秒，可以销毁
         */
        threadPoolExecutor.setKeepAliveTime(10, TimeUnit.SECONDS);
        threadPoolExecutor.allowCoreThreadTimeOut(true);

        System.out.println("main thread start");

        /*
         * 不断执行线程，线程内部sleep 2秒
         */
        for (int i = 0; i < 15; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    System.out.println("线程：" + Thread.currentThread().getName() + "开始sleep");
                    Thread.sleep(2000);
                    System.out.println("线程：" + Thread.currentThread().getName() + "结束sleep");
                } catch (Exception e) {
                    System.out.println("execute end");
                }
            });
        }

        /*
         * 主线程sleep 40秒
         */
        try {
            System.out.println("main thread sleep");
            Thread.sleep(1000 * 40);
        } catch (Exception e) {
            System.out.println("main thread end");
        }
    }
}

