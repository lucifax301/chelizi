package com.lili.access.util;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CrossFilter implements Filter {
	private static final boolean debug = true;
	private FilterConfig filterConfig = null;
	
	public CrossFilter(){
		super();
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		if(filterConfig != null){
			if(debug){
				log("CrosFilter:Initializing filter");
			}
		}
		
	}
	

	private void log(String string) {
		filterConfig.getServletContext().log(string);
		
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(debug){
			log("CrossFilter:doFilter()");
		}
		if(response instanceof HttpServletResponse){
			HttpServletResponse alteredResponse = (HttpServletResponse) response;
			//i need to find a way to make sure this only gets called on 200-300 http responses
			//TODO see above comment
			addHeadersFor200Response(alteredResponse);
		}

		Throwable problem = null;
		try {
			chain.doFilter(request, response);
		} catch (Throwable t) {
			// if an exception is thrown somewhere down the filter chain,
			// we still want to execute our after processing, and then
			// rethrow the problem after that.
			problem = t;
			t.printStackTrace();
		}
		
		// if there was a problem, we want to rethrow it if it is a known type
		if(problem != null){
			if(problem instanceof ServletException){
				throw (ServletException)problem;
			}
			if(problem instanceof IOException){
				throw (IOException)problem;
			}
			sendProcessingError(problem, response); 
		}
		
	}

	private void sendProcessingError(Throwable t, ServletResponse response) {
		String stackTrace = getStackTrace(t);
		if(stackTrace != null && !stackTrace.equals("")){
			PrintWriter pw =null;
			PrintStream ps = null;
			try {
				response.setContentType("text/html");
				ps = new PrintStream(response.getOutputStream());
				pw = new PrintWriter(ps);                  
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N  
  
                // PENDING! Localize this for next official release  
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                  
                pw.print(stackTrace);                  
                pw.print("</pre></body>\n</html>"); //NOI18N  
                pw.close();  
                ps.close();  
                response.getOutputStream().close();  
			} catch (IOException e) {
			}
			finally{
				try {
					if(pw != null) {
						pw.close();
					}
					if(ps != null){
						ps.close();
					}
				} catch (Exception e) {
					if(pw != null) {
						pw.close();
					}
					if(ps != null){
						ps.close();
					}
					e.printStackTrace();
				}
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

	private void addHeadersFor200Response(HttpServletResponse response) {
		// externalize the Allow-Origin
		//表明它允许"http://foo.org"发起跨域请求
		//response.setHeader("Access-Control-Allow-Origin", "http://xc.res.com:8080");
//		response.setHeader("Access-Control-Allow-Origin", "http://jx.lilixc.com");
		response.setHeader("Access-Control-Allow-Origin", filterConfig.getInitParameter("origin"));
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		response.setHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
		//表明在1728000秒内，不需要再发送预检验请求，可以缓存该结果
		response.setHeader("Access-Control-Max-Age", "1728000"); 
		response.setHeader("Access-Control-Allow-Credentials", "true"); 
		
		
//		response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//        response.setHeader("Access-Control-Max-Age", "1209600");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}












