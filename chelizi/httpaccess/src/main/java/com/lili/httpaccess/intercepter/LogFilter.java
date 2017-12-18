package com.lili.httpaccess.intercepter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.log.dto.LogOperate;

public class LogFilter implements Filter {
	private Logger logger = Logger.getLogger(LogFilter.class);
	private static final boolean debug = false;
	private FilterConfig filterConfig = null;
	
	private WebApplicationContext wac;
	private DefaultMQProducer logProducer;
	
	public LogFilter() {
		super();
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		if (filterConfig != null) {
			if (debug) {
				log("LogFilter:Initializing filter");
			}
		}
		ServletContext servletContext = filterConfig.getServletContext();
		wac = (WebApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		logProducer = (DefaultMQProducer) wac.getBean("logProducer");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (debug) {
			log("LogFilter:doFilter()");
		}
		request.setAttribute("startTime0", System.currentTimeMillis());

/*		if(response instanceof HttpServletResponse){
			HttpServletResponse res = (HttpServletResponse) response;
		}*/
		
		CoverResponse cr = new CoverResponse((HttpServletResponse)response);

		Throwable problem = null;
		try {
			//chain.doFilter(request, response);
			chain.doFilter(request, cr);
		} catch (Throwable t) {
			problem = t;
			t.printStackTrace();
		}

		if (problem != null) {
			if (problem instanceof ServletException) {
				throw (ServletException) problem;
			}
			if (problem instanceof IOException) {
				throw (IOException) problem;
			}
			sendProcessingError(problem, response);
		}
		
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			//long startTime0 = System.currentTimeMillis();
			//request.setAttribute("startTime0", startTime0);
			try {
				Map<String, String> params = new HashMap<String, String>();
				Enumeration enu = req.getParameterNames();
				while (enu.hasMoreElements()) {
					String paramName = (String) enu.nextElement();
					String paramValue = request.getParameter(paramName);
					params.put(paramName, paramValue);
				}
				String token = req.getHeader(ReqConstants.TOKEN);
				//处理替换 
				String content = cr.getContent(); 
				// 重置响应输出的内容长度  
				//response.setContentLength(-1);
				//response.getWriter().print(content);
				
				//耗时时间记录
				long endTime0 = System.currentTimeMillis();
				long startTime0 = (long) request.getAttribute("startTime0");
				long executeTime0 = endTime0 - startTime0;
				
				//nginx反向代理，尝试获取真实ip地址 //需要nginx中配置
				String ip = "";
				String remoteAddr = req.getRemoteAddr();
				String forwarded = req.getHeader("X-Forwarded-For"); 
				String realIp = req.getHeader("X-Real-IP");
				
				if (realIp == null) {
					 if (forwarded == null) {
						 ip = remoteAddr;
					 } else {
						int index = forwarded.indexOf(",");
						if(index != -1){
							ip = forwarded.substring(0,index);
						}
					 }
				 } else {
					 ip = realIp;
				}
				logger.info("*********************request method:"+req.getHeader("Content-type"));			 
				//long startTime1 = (long) request.getAttribute("startTime");
				//long executeTime1 = (long) request.getAttribute("executeTime");
				//记录日志到文件 
//		        if(logger.isDebugEnabled()){
		        	logger.debug("LogFilter: getRequestURL---->"+req.getRequestURL());
		        	logger.debug("LogFilter: getRequestURI---->"+req.getRequestURI());
		        	logger.debug("LogFilter: getQueryString--->"+req.getQueryString());
		        	logger.debug("LogFilter: getParams-------->"+params);
		        	logger.debug("LogFilter: getToken--------->"+token);
		        	logger.debug("LogFilter: getMethod-------->"+req.getMethod());
		        	logger.debug("LogFilter: getRemoteAddr---->"+req.getRemoteAddr());
		        	logger.debug("LogFilter: getRemoteHost---->"+req.getRemoteHost());
		        	logger.debug("LogFilter: getRemotePort---->"+req.getRemotePort());
		        	logger.debug("LogFilter: getResContent---->"+content);
					logger.debug("LogFilter: getResStatus----->"+cr.getStatus());
					
					logger.debug("LogFilter: nowTime---->"+System.currentTimeMillis());
					logger.debug("LogFilter: startTime0---->"+startTime0);
					logger.debug("LogFilter: executeTime0---->"+executeTime0);
		        	//logger.debug("LogFilter: startTime1---->"+startTime1);
		        	//logger.debug("LogFilter: executeTime1---->"+executeTime1);
					logger.debug("LogFilter: realIP---->"+ip);

//		        }
		        
		        
		        LogOperate operate = new LogOperate();
				if(params.containsKey("userId") && !"".equals(params.get("userId").trim())){
					operate.setUserId(Long.parseLong(params.get("userId")));
				}
				if(params.containsKey("userType") && !"".equals(params.get("userType").trim())){
					operate.setUserType(Byte.parseByte(params.get("userType")));
				}
				operate.setRequestTime(new Date(startTime0));
				operate.setRequestUrl(req.getRequestURL().toString());
				operate.setRequestParams(params.toString());
				operate.setRequestMethod(req.getMethod());
				operate.setRemoteHost(ip);
				operate.setRemotePort(req.getRemotePort());
				
				operate.setResponseCode(cr.getStatus());
				operate.setExecuteTime(executeTime0);
				//operate.setExtra(executeTime1+""); //临时记录control执行时间，用于比较判断是否日志服务耗时太多
				logger.info(operate.getUserId()+"|"+operate.getUserType()+"|"+operate.getRequestUrl()+"|"+operate.getRequestMethod()+"|"+operate.getRemoteHost()+"|"+operate.getRemotePort()+"|"+operate.getResponseCode()+"|"+operate.getExecuteTime()+"|"+params.toString());
				//直接通过mq发送消息
//				Message msg=new Message();
//				msg.setTopic(logProducer.getCreateTopicKey());
//				msg.setTags(OrderConstant.RMQTAG.LOGACCESS);
//				msg.setBody(SerializableUtil.serialize(operate));
//				logProducer.send(msg);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void log(String string) {
		filterConfig.getServletContext().log(string);

	}
	
	private void sendProcessingError(Throwable t, ServletResponse response) {
		String stackTrace = getStackTrace(t);
		if (stackTrace != null && !stackTrace.equals("")) {
			try {
				response.setContentType("text/html");
				PrintStream ps = new PrintStream(response.getOutputStream());
				PrintWriter pw = new PrintWriter(ps);
				pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); // NOI18N

				// PENDING! Localize this for next official release
				pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
				pw.print(stackTrace);
				pw.print("</pre></body>\n</html>"); // NOI18N
				pw.close();
				ps.close();
				response.getOutputStream().close();
			} catch (IOException e) {
			}
		}
	}

	private String getStackTrace(Throwable t) {
		String stackTrace = null;
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.close();
			sw.close();
			stackTrace = sw.getBuffer().toString();
		} catch (Exception e) {

		}
		return stackTrace;
	}
	
	private void writeJsonObj(ServletResponse response, Object result) {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonGenerator jsonGenerator = mapper.getJsonFactory()
					.createJsonGenerator(
							(OutputStream) httpResponse.getOutputStream(),
							JsonEncoding.UTF8);
			mapper.writeValue(jsonGenerator, result);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
