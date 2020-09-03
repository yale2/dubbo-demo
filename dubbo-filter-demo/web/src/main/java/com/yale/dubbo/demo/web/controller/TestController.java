package com.yale.dubbo.demo.web.controller;

import com.yale.dubbo.demo.api.service.DemoService;
import org.apache.dubbo.common.threadpool.support.fixed.FixedThreadPool;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author yale
 */
@RestController
public class TestController {

    @Reference
    private DemoService demoService;

    @RequestMapping("/test")
    public String test(){
        return demoService.test();
    }

    @RequestMapping("/testTP")
    public void testIP(){
        ExecutorService executor= Executors.newFixedThreadPool(3);
        while (true){
            executor.submit(new TestTPThread(demoService));
        }
    }
}
