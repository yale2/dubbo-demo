package com.yale.dubbo.transport.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.concurrent.CompletableFuture;

@Activate(group = CommonConstants.CONSUMER)
public class TPMonitorFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String methodName = invocation.getMethodName();
        long startTime=System.currentTimeMillis();
        Result result = invoker.invoke(invocation);
        long cost = System.currentTimeMillis() - startTime;
        System.out.println(methodName + " 调用耗时:" +cost);
        CompletableFuture.runAsync(()->{
            TimeRecorder.recordTime(methodName,cost);
        });
        return result;
    }
}
