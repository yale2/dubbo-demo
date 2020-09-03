package com.yale.dubbo.demo.provider.service;

import com.yale.dubbo.demo.api.service.DemoService;
import org.apache.dubbo.config.annotation.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author yale
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String test() {
        return "hello";
    }

    @Override
    public String testA() {
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));
            System.out.println("返回响应");
            return "testA success";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String testB() {
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));
            return "testB success";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String testC() {
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));
            return "testC success";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
