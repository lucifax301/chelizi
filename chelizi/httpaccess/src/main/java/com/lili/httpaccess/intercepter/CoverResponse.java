package com.lili.httpaccess.intercepter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CoverResponse extends HttpServletResponseWrapper {
	private PrintWriter tmpWriter;  
    private StringWriter output;  
  
    public CoverResponse(HttpServletResponse response) {  
        super(response);  
        // 这个是我们保存返回结果的地方
        output = new StringWriter();   
        // 这个是包装PrintWriter的，让所有结果通过这个PrintWriter写入到bufferedWriter中
        tmpWriter = new PrintWriter(output);  
    }  
    @Override  
    public void finalize() throws Throwable {      
         super.finalize();      
         output.close();      
         tmpWriter.close();  
    }  
      
    public String getContent() {  
    	//刷新该流的缓冲，详看java.io.Writer.flush()  
        tmpWriter.flush();       
        String s = output.toString();      
        //此处可根据需要进行对输出流以及Writer的重置操作      
        tmpWriter.print(s);  
        return s;      
    }      
  
    //覆盖getWriter()方法，使用我们自己定义的Writer   
    @Override
    public PrintWriter getWriter() throws IOException {      
        return tmpWriter;      
    }    
      
    public void close() throws IOException {      
        tmpWriter.close();      
    }

}
