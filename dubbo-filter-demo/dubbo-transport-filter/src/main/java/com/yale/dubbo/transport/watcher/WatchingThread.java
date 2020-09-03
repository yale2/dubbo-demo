package com.yale.dubbo.transport.watcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.threadpool.support.fixed.FixedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class WatchingThread extends FixedThreadPool implements Runnable {

    private Logger logger = LoggerFactory.getLogger(WatchingThread.class);

    private static final double ALARM_PERCENT=0.90;

    private final Map<URL,ThreadPoolExecutor> THREAD_POOLS=new ConcurrentHashMap<>();

    public WatchingThread(){
        Executors.newSingleThreadScheduledExecutor().
                scheduleWithFixedDelay(this,1,3, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        for (Map.Entry<URL, ThreadPoolExecutor> entry : THREAD_POOLS.entrySet()) {
            URL url=entry.getKey();
            ThreadPoolExecutor executor=entry.getValue();
            int activeCount = executor.getActiveCount();
            int corePoolSize = executor.getCorePoolSize();
            double used=(double) activeCount/corePoolSize;
            int usedNum=((int)used*100);
            logger.info("线程池运行状态:[{}/{}]:{}%",activeCount,corePoolSize,used);
            if(used > ALARM_PERCENT){
                logger.warn("超出警戒值，host:{},当前已使用量：{}%，url:{}",url.getIp(),usedNum,url);
            }
        }
    }

    @Override
    public Executor getExecutor(URL url) {
        Executor executor = super.getExecutor(url);
        if(executor instanceof ThreadPoolExecutor){
            THREAD_POOLS.put(url,(ThreadPoolExecutor)executor);
        }
        return executor;
    }
}
