package com.lili.common.dubbo;

import java.util.Map;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.lili.common.util.ThreadTruck;

@Activate(group={Constants.PROVIDER},order=-7000)
public class DbRouteProviderFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)
			throws RpcException {
		try{
			Map<String,String> attachments = RpcContext.getContext().getAttachments();
			String dbroute = attachments.get("route_db");
			System.out.println("provider route_db:"+dbroute);
			ThreadTruck.put("route_db", dbroute);
			Result result = invoker.invoke(invocation);
			return result;
		}finally{
			ThreadTruck.remove("route_db");
		}
	}

}
