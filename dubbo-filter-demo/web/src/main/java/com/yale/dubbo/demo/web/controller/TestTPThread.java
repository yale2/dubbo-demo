package com.yale.dubbo.demo.web.controller;

import com.yale.dubbo.demo.api.service.DemoService;

public class TestTPThread implements Runnable {

    private DemoService demoService;

    public TestTPThread(DemoService demoService){
        this.demoService=demoService;
    }
    @Override
    public void run() {
        demoService.testA();
        demoService.testB();
        demoService.testC();
    }
}
