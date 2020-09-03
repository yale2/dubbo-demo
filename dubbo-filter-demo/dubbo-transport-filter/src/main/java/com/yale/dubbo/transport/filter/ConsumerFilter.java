package com.yale.dubbo.transport.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @author yale
 */
@Activate(group = CommonConstants.CONSUMER)
public class ConsumerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext.getContext().setAttachment("ip",invoker.getUrl().getIp());
        Result result = invoker.invoke(invocation);
        return result;
    }
}
