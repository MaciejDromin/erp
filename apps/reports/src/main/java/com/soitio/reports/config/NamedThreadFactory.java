package com.soitio.reports.config;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber;
    private final String threadPrefix;

    public NamedThreadFactory(String threadPrefix) {
        this.threadPrefix = threadPrefix;
        this.threadNumber = new AtomicInteger(1);
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, threadPrefix + "-" + threadNumber.getAndIncrement());
    }

}
