package com.dingdong.party.user;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class Test {

    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    @org.junit.Test
    public void testScheduler() throws InterruptedException {
        System.out.println(new Date().getTime());
        Long time = new Date().getTime() / 1000;
        scheduledThreadPool.schedule(() -> {
            System.out.println(new Date().getTime());
        }, 1, TimeUnit.SECONDS);
        Thread.sleep(10000);
    }
}
