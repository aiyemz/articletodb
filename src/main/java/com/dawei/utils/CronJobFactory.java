package com.dawei.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CronJobFactory implements ThreadFactory {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        atomicInteger.addAndGet(1);
        return new Thread(r,"CronJobThread-"+atomicInteger.get());
    }
}
