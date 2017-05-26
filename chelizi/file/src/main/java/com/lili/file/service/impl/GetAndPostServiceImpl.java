package com.lili.file.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.lili.file.service.GetAndPostService;

public class GetAndPostServiceImpl implements GetAndPostService {
	
	private static Logger log = LoggerFactory.getLogger(GetAndPostServiceImpl.class);

	@Override
	public JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		 JSONObject jsonObject = null;
	        
	        try {
	            // 创建 SSLContext 对象，并使用我们指定的信任管理器初始化
	            TrustManager[] tm = null;//{ new MyX509TrustManager() };
	            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	            sslContext.init(null, tm, new java.security.SecureRandom());
	            // 从上述 SSLContext 对象中得到 SSLSocketFactory 对象
	            SSLSocketFactory ssf = sslContext.getSocketFactory();
	            
	            URL url = new URL(requestUrl);
	            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	            conn.setSSLSocketFactory(ssf);
	            
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setUseCaches(false);
	            
	            // 设置请求方式（GET/POST）
	            conn.setRequestMethod(requestMethod);
	            
	            // 当 outputStr 不为 null 时，向输出流写数据
	            if (null != outputStr) {
	                OutputStream outputStream = conn.getOutputStream();
	                
	                // 注意编码格式
	                outputStream.write(outputStr.getBytes("UTF-8"));
	                outputStream.close();
	            }
	            
	            // 从输入流读取返回内容
	            InputStream inputStream = conn.getInputStream();
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String str = null;
	            StringBuffer buffer = new StringBuffer();
	            
	            while ((str = bufferedReader.readLine()) != null) {
	                buffer.append(str);
	            }
	            
	            // 释放资源
	            bufferedReader.close();
	            inputStreamReader.close();
	            inputStream.close();
	            inputStream = null;
	            conn.disconnect();
	           // jsonObject = JSONObject.fromObject(buffer.toString());
	        } catch (ConnectException ce) {
	            log.error(" 连接超时：{}", ce);
	        } catch (Exception e) {
	            log.error("https 请求异常：{}", e);
	        }
	        
	        return jsonObject;
	}

	@Override
	public String sendPost(String url, Object param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性 注意Authorization生成
			// conn.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public String getHttpURL(String path, String method) throws MalformedURLException, ProtocolException, IOException {
		if (method == null || method.length() == 0)
			method = "GET";
		// String path =
		// "http://127.0.0.1:8088/Rank/PlayerLevel?CorpID=123&LoginName=qqq&name="+URLEncoder.encode("汉字","utf-8");
		URL url = new URL(path);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
		// http正文内，因此需要设为true，默认情况下是false
		urlConn.setDoOutput(true);
		// 设置是否从httpUrlConnection读入，默认情况下是true
		urlConn.setDoInput(true);
		// Post请求不能使用缓存
		urlConn.setUseCaches(false);
		// 设定传送的内容类型是可序列化的java对象
		// （如果不设此项，在传送序列化对象时，当WEB服务默认的不是这种类型时可能抛java.io.EOFException）
		// urlConn.setRequestProperty("Content-type",
		// "application/x-java-serialize-object");
		urlConn.setRequestProperty("Content-type", "text/plain;charset=UTF-8");
		// 设定请求的方法为"POST"，默认是GET
		urlConn.setRequestMethod(method);
		// 连接，上面对urlConn的所有配置必须要在connect之前完成
		urlConn.setConnectTimeout(10000);// 连接主机的超时时间（单位：毫秒）
		urlConn.setReadTimeout(10000);// 从主机读取数据的超时时间（单位：毫秒）
		urlConn.connect();
		// 调用HttpURLConnection连接对象的getInputStream()函数，
		// 将内存缓冲区中封装好的完整的HTTP请求电文发送服务端
		InputStream inStrm = urlConn.getInputStream();// 注意，实际发送请求的代码段就在这里
		InputStreamReader isReader = new InputStreamReader(inStrm, "utf-8");
		BufferedReader bReader = new BufferedReader(isReader);
		StringBuffer sb = new StringBuffer();
		String str = bReader.readLine();
		while (str != null) {
			sb.append(str);
			str = bReader.readLine();
		}
		return sb.toString();
	}

}
