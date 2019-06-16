package com.rkylin.crawler.engine.flood.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ThreadPoolManager4Tool {
    private static final transient Logger logger = Logger.getLogger(ThreadPoolManager4Tool.class);

    private static ExecutorService executor = null;
    private static ScheduledExecutorService sexecutor = null;

    public static void init(int threadSize) {
        executor = Executors.newFixedThreadPool(threadSize);
        sexecutor = Executors.newScheduledThreadPool(1);
    }

    /**
     * 执行(异步)
     * 
     * @param r
     *            Runnable
     */
    public static void exec(Runnable r) {
        executor.execute(r);
    }

    /**
     * 定时执行
     * 
     * @param r
     *            Runnable
     */
    public static void sexec(Runnable r, long initialDelay, long period, TimeUnit unit) {
        sexecutor.scheduleAtFixedRate(r, initialDelay, period, unit);
    }

    public static void shutdown() {
        try {
            if (executor != null) {
                executor.shutdown();
                executor.awaitTermination(1, TimeUnit.MINUTES);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }

        try {
            if (sexecutor != null) {
                sexecutor.shutdown();
                sexecutor.awaitTermination(1, TimeUnit.MINUTES);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }
}
