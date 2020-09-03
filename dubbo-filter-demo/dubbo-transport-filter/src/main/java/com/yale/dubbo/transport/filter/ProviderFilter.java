package com.yale.dubbo.transport.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @author yale
 */
@Activate(group = CommonConstants.PROVIDER)
public class ProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("consumer ip: "+ RpcContext.getContext().getAttachment("ip"));
        return invoker.invoke(invocation);
    }
}
