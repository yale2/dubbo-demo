package com.yale.dubbo.transport.filter;

import java.util.concurrent.TimeUnit;

public class PrintThread implements Runnable {
    @Override
    public void run() {
        while (TimeRecorder.run) {
            try {
                for (String methodName : TimeRecorder.methodNameTimeListMap.keySet()) {
                    System.out.println(methodName + " TP90:" + TimeRecorder.calculateTP90(methodName));
                    System.out.println(methodName + " TP99:" + TimeRecorder.calculateTP99(methodName));
                }

                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
