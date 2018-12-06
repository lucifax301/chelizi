package com.lili.common.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.lili.common.util.ThreadTruck;

@Activate(group={Constants.CONSUMER},order=-9000)
public class DbRouteConsumerFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)
			throws RpcException {
		try{
			String key = (String)ThreadTruck.get("route_db");
			RpcContext.getContext().getAttachments().put("route_db", key);
			Result result = invoker.invoke(invocation);
			return result;
		}finally{
			
		}
	}

}
