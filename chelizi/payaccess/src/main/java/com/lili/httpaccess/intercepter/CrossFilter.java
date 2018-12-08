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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lili.common.util.ThreadTruck;

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
		
		Throwable problem = null;
		String kc = ((HttpServletRequest)request).getHeader("kc");
		
		System.out.println("payaccess route_db:"+kc);
		if(kc!=null){
			ThreadTruck.put("route_db", kc);
		}
		try {
			chain.doFilter(request, response);
		} catch (Throwable t) {
			// if an exception is thrown somewhere down the filter chain,
			// we still want to execute our after processing, and then
			// rethrow the problem after that.
			problem = t;
			t.printStackTrace();
		} finally{
			ThreadTruck.remove("route_db");
		}
		
		// if there was a problem, we want to rethrow it if it is a known type
		if(problem != null){
			if(problem instanceof ServletException){
				throw (ServletException)problem;
			}
			if(problem instanceof IOException){
				throw (IOException)problem;
			}
		}
		
	}

	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
