package com.lili.httpaccess.intercepter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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

import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
/**
 * 校验时间戳、签名
    * @ClassName: SignFilter
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author Administrator
    * @date 2015年11月9日
    *
 */
public class SignFilter implements Filter {
	private Logger log = Logger.getLogger(SignFilter.class);
	private static final boolean debug = true;
	private FilterConfig filterConfig = null;

	public SignFilter() {
		super();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (debug) {
			log("SignFilter:doFilter()");
		}

		if (request instanceof HttpServletRequest) {
			HttpServletRequest toCheckRequest = (HttpServletRequest) request;
			
			//20161107 驾培云过来的请求一定要打开签名校验；APP有时间也需要打开校验，临时关闭；
			String uri = toCheckRequest.getRequestURI();
			String m0 = "/httpaccess/v1/yun/";
			if(uri.contains(m0)){
				// 请求非法
				if (!isOkRequest(toCheckRequest)) {
					ReqResult r = new ReqResult();
					r.setCode(ResultCode.ERRORCODE.PARAMERROR);
					r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
					writeJsonObj(response, r.getResult());
					return;
				}
			}else {
				//其他请求，因为APP端一直没有调试好签名校验，暂时不强制
				// 请求非法
				if (!isOkRequest(toCheckRequest)) {
	/*				ReqResult r = new ReqResult();
					r.setCode(ResultCode.ERRORCODE.PARAMERROR);
					r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
					writeJsonObj(response, r.getResult());
					return;*/
				}
			}

		}

		Throwable problem = null;
		try {
			chain.doFilter(request, response);
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

	}

	private boolean isOkRequest(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paramName = (String) enu.nextElement();
			String paramValue = request.getParameter(paramName);
			params.put(paramName, paramValue);
		}
		//log(params.toString());
		String token = request.getHeader(ReqConstants.TOKEN);
		if(token != null && !"".equals(token)){
			params.put("token", token);
		}
		return requestVerify(params);
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

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		if (filterConfig != null) {
			if (debug) {
				log("SignFilter:Initializing filter");
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

	private final String SECRET = "6b1019d9561c548037b37023d7a0451b"; //无token时的默认值

	private boolean requestVerify(Map<String, String> params) {
		// 时间戳校验 防止重返攻击
		if (params.containsKey("timestamp")) {
			if (!timeVerify(params.get("timestamp"))) {
				return false;
			}
		}
		// 请求签名校验 防止请求被篡改 防止匿名攻击
		if (params.containsKey("sign")) {
			String signOld = params.get("sign");
			params.remove("sign");
			String sec;
			if(params.containsKey("token")){
				sec = params.get("token");
				params.remove("token");
			}else{
				sec = SECRET;
			}
			String signVerify = getSignature(params, sec);
			if (!signVerify.equals(signOld)) {
				//System.out.println("signOld---->" + signOld);
				//System.out.println("signVerify---->" + signVerify);
				log.error("SignFilter: secret---->"+sec);
				log.error("SignFilter: params---->"+params);
				log.error("SignFilter: signOld---->" + signOld);
				log.error("SignFilter: signVerify---->" + signVerify);
				return false;
			}
		}

		return true;
	}

	/**
	 * 获取签名的算法，应该与客户端统一
	 * 
	 * @param params
	 *            除sign之外的其他参数
	 * @param secret
	 *            密钥
	 * @return 签名字符串
	 */
	private String getSignature(Map<String, String> params, String secret) {
		// 先将参数以其参数名的字典序升序进行排序
		Map<String, String> sortedParams = new TreeMap<String, String>(params);
		Set<Entry<String, String>> entrys = sortedParams.entrySet();
		log.info("SignFilter: params---->" + params);
		log.info("SignFilter: sortedParams---->" + sortedParams);
		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder basestring = new StringBuilder();
		for (Entry<String, String> param : entrys) {
			basestring.append(param.getKey()).append("=")
					.append(param.getValue());
		}
		// 添加secret值到末尾
		basestring.append(secret);
		log.info("SignFilter: basestring---->" + basestring);
		// 使用MD5对待签名串求签
		byte[] bytes = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
		} catch (Exception ex) {
		}
		log.info("SignFilter: bytes---->" + bytes);
		// 将MD5输出的二进制结果转换为小写的十六进制
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex);
		}
		log.info("SignFilter: sign---->" + sign);
		return sign.toString();
	}

	private boolean timeVerify(String timestamp) {
		long t1 = Long.parseLong(timestamp);
		long t2 = System.currentTimeMillis() / 1000l;
		/*
		 * Date d1 = new Date(t1); Date d2 = new Date(t2); DateFormat dfm = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 * System.out.println(dfm.format(d1));
		 * System.out.println(dfm.format(d2)); System.out.println(t1);
		 * System.out.println(t2);
		 */

		if (t2 - t1 > 60 * 120) { // 2小时时间间隔内，认为请求是正常的
			log.error("SignFilter: timeOld---->" + t1);
			log.error("SignFilter: timeVerify---->" + t2);
			return false;
		} else {
			return true;
		}
	}

}
