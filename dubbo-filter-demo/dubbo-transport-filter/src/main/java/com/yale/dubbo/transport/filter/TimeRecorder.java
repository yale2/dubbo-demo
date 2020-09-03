package com.yale.dubbo.transport.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author yale
 */
public class TimeRecorder{


    public static Map<String, LinkedBlockingDeque<Long>> methodNameTimeListMap = new ConcurrentHashMap<>();

    public static boolean run = false;

    private static Map<String,Integer> countMap=new HashMap<>();



    public static void recordTime(String methodName, long time) {
        if (!methodNameTimeListMap.containsKey(methodName)) {
            LinkedBlockingDeque<Long> timeList = new LinkedBlockingDeque<>(60);
            methodNameTimeListMap.put(methodName, timeList);
            countMap.put(methodName,1);
            timeList.add(time);
        } else {
            if (countMap.get(methodName) > 12) {
                methodNameTimeListMap.get(methodName).removeFirst();
            }
            countMap.put(methodName,countMap.get(methodName)+1);
            methodNameTimeListMap.get(methodName).addLast(time);
        }
        if (!run) {
            run = true;
            CompletableFuture.runAsync(new PrintThread());
        }
    }

    public static Long calculateTP90(String methodName) {
        LinkedBlockingDeque<Long> timeList = methodNameTimeListMap.get(methodName);
        timeList.stream().sorted(Long::compareTo);
        Double v = timeList.size() * 0.9;
        return (Long)timeList.toArray()[v.intValue()];
    }

    public static Long calculateTP99(String methodName) {
        LinkedBlockingDeque<Long> timeList = methodNameTimeListMap.get(methodName);
        timeList.stream().sorted(Long::compareTo);
        Double v = timeList.size() * 0.99;
        return (Long)timeList.toArray()[v.intValue()];
    }

}
