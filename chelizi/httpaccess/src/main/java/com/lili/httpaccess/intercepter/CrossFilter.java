package com.lili.httpaccess.intercepter;

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
			try {
				response.setContentType("text/html");
				PrintStream ps = new PrintStream(response.getOutputStream());
				PrintWriter pw = new PrintWriter(ps);                  
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
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept,token");
		//表明在1728000秒内，不需要再发送预检验请求，可以缓存该结果
		response.addHeader("Access-Control-Max-Age", "1728000"); 
		//response.addHeader("Access-Control-Allow-Credentials", "true"); 
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}












