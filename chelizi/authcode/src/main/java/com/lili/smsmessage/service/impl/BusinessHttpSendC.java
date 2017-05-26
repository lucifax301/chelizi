package com.lili.smsmessage.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class BusinessHttpSendC
{

	public static String ApiUrl = "http://client.sms10000.com/api/webservice"; //202.104.149.58 client.sms10000.com

	public static void main(String[] arg)
	{

		/*
		 * 1 成功(只表示调接口成功，不代表发送成功) 0 失败 -1 缺少参数 -2 请求已经过期 -3 Key验证失败 -4 Ip验证失败 -5
		 * eprId不存在 -6 userId不存在 -7 定时日期格式不对 -8 签名不对
		 */
		  SecureRandom r = new SecureRandom();
		  int msgid = Math.abs(r.nextInt());
		  String content = "你好，这是商务短信不用审！";
		  content = content.trim(); 
		  String mobiles = "17093437801";//手机号码需要判断是否符合规则 
		  if(getMobileType("17093437801")==0)
		  {
			  System.out.println("手机号码不对" );
			  return ;
		  }
		  String eprId = "587";//企业id 
		  String userId = "szcczwl";//帐号
		  String pwd = "Cczwl587";//密码 
		  long t1 = System.currentTimeMillis(); 
		  String re =sendSms(eprId, userId, pwd, content, mobiles, msgid);
		  System.out.println("提交结果result=" + re); 
		  long t2 =System.currentTimeMillis(); System.out.println("提交耗时：" + (t2 - t1));
		 
	 

	}
 

	/**
	 * 判断号码类型，随时会更新
	 * 
	 * @param mobile
	 * @return 0未知，1移动，2联通，3电信
	 */
	public static int getMobileType(String mobile)
	{

		mobile = mobile.trim();
		String cm = "^((13[4-9])|(147)|(15[0-4,7-9])|(18[2-4,7-8])|(178))\\d{8}$";// 移动
		String cu = "^((13[0-2])|(145)|(15[5-6])|(18[5-6])|(176))\\d{8}$";// 联通
		String ct = "^((133)|(153)|(18[0,1,9])|(177))\\d{8}$";// 电信
		String cmext = "^(1705)\\d{7}$";
		String cuext = "^(170[8-9])\\d{7}$";
		String ctext = "^(170[0-2])\\d{7}$";
		int flag = 0;

		if (mobile.matches(cm) || mobile.matches(cmext))
		{
			flag = 1;
		}
		else if (mobile.matches(cu) || mobile.matches(cuext))
		{
			flag = 2;
		}
		else if (mobile.matches(ct) || mobile.matches(ctext))
		{
			flag = 3;
		}

		return flag;

	}
	public static String sendSms(String eprId, String userId, String pwd, String content, String mobiles){
		  SecureRandom r = new SecureRandom();
		  int msgid = Math.abs(r.nextInt());
		  return sendSms(eprId, userId, pwd, content, mobiles, msgid);
	}

	public static String sendSms(String eprId, String userId, String pwd, String content, String mobiles, int msgid)
	{

		try
		{

			int format = 1;
			content = java.net.URLEncoder.encode(content, "utf-8");
			long timestamp = System.currentTimeMillis();
		 
			String key = eprId + userId + pwd + timestamp;
			key = encodeByMD5(key);
			String data = "cmd=send&eprId=" + eprId + "&userId=" + userId + "&key=" + key + "&timestamp=" + timestamp + "&format=" + format
					+ "&mobile=" + mobiles + "&msgId=" + msgid + "&content=" + content;
			System.out.println(data);
			String result = httpPost(ApiUrl, data);
			// result=httpGet(url);
			String url = ApiUrl + "?" + data;
			System.out.println(url);
		  
			return result;

		}
		catch (Exception e)
		{

			e.printStackTrace();
		}

		return "";
	}

	// 对字符串进行MD5加密
	private static String encodeByMD5(String originString)
	{
		if (originString != null)
		{
			try
			{
				// 创建具有指定算法名称的信息摘要
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
				byte[] results = md.digest(originString.getBytes());
				// 将得到的字节数组变成字符串返回
				String resultString = byteArrayToHexString(results);
				return resultString.toUpperCase();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return null;
	}


	// 转换字节数组为十六进制字符串
	private static String byteArrayToHexString(byte[] b)
	{
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
		{
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}
	
	
	// 十六进制下数字到字符的映射数组
	private final static String[] hexDigits =
	{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	

	// 将一个字节转化成十六进制形式的字符串
	private static String byteToHexString(byte b)
	{
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * get
	 * 
	 * @param strURL
	 * @return
	 */
	public static String httpGet(String strURL)
	{
		String re = "";
		HttpURLConnection urlConn = null;
		InputStream in = null;
		List<String> list = new ArrayList<String>();
		try
		{
			URL url = new URL(strURL);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setDoOutput(true);
			urlConn.setConnectTimeout(1000);
			urlConn.setRequestMethod("GET");

			in = urlConn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, "GBK"));
			String line = rd.readLine();
			while (line != null)
			{
				line = line.trim();
				if (!line.equals(""))
					list.add(line);
				line = rd.readLine();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (in != null)
				try
				{
					in.close();
				}
				catch (IOException e)
				{
				}

			if (urlConn != null)
			{
				urlConn.disconnect();
			}
		}

		if (list != null && list.size() > 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				re += list.get(i) + "\n";
			}
		}

		return re.trim();
	}

	public static String httpPost(String host, String data)
	{
		String result = "";
		try
		{
			URL url = new URL(host);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setReadTimeout(5000000);
			connection.setRequestMethod("POST");
			// connection.setRequestProperty("Content-type", "text");
			// connection.setRequestProperty("Content-Type","application/xml;charset=UTF-8");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			connection.setRequestProperty("user-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; MS Web Services Client Protocol 2.0.50727.4223)");
			// SEND DNS INFO
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			out.write(data);// SEND DATA
			out.flush();
			out.close();
			// GET RESPONSE
			InputStream in = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			String line = rd.readLine();
			List<String> list = new ArrayList<String>();
			while (line != null)
			{
				line = line.trim();
				if (!line.equals(""))
					list.add(line);
				line = rd.readLine();
			}
			if (list != null)
			{
				if (list.size() == 1)
				{
					result += list.get(0);
				}
				else if (list.size() > 1)
				{
					for (int i = 0; i < list.size(); i++)
					{
						result += list.get(i) + "\n";
					}
				}
			}
		}
		catch (MalformedURLException e)
		{

			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{

			e.printStackTrace();
		}
		catch (IOException e)
		{

			e.printStackTrace();
		}

		return result;
	}

	public static String httpPost2(String host, String data)
	{
		String result = "";
		try
		{
			URL url = new URL(host);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setReadTimeout(500);
			connection.setRequestMethod("POST");
			// connection.setRequestProperty("Content-type", "text");
			connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
			// connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			 connection.setRequestProperty("user-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; MS Web Services Client Protocol 2.0.50727.4223)");
			// SEND DNS INFO
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			out.write(data);// SEND DATA
			out.flush();
			out.close();
			// GET RESPONSE
			InputStream in = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			String line = rd.readLine();
			List<String> list = new ArrayList<String>();
			while (line != null)
			{
				line = line.trim();
				if (!line.equals(""))
					list.add(line);
				line = rd.readLine();
			}
			if (list != null)
			{
				if (list.size() == 1)
				{
					result += list.get(0);
				}
				else if (list.size() > 1)
				{
					for (int i = 0; i < list.size(); i++)
					{
						result += list.get(i) + "\n";
					}
				}
			}
		}
		catch (MalformedURLException e)
		{

			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{

			e.printStackTrace();
		}
		catch (IOException e)
		{

			e.printStackTrace();
		}

		return result;
	}

	
	
	public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
           
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type","text/xml;charset=UTF-8");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    

	
	
	public static String post3(String strURL, String strPostData, String encoding, int time)
	{
		HttpURLConnection urlConn = null;
		InputStream in = null;
		StringBuffer sb = new StringBuffer();
		
	
		System.out.println("HTTP POST:"+strURL);
		System.out.println("HTTP POST Data:"+strPostData);
		try
		{
			 
            URL url = new  URL( strURL );
            
            urlConn= (HttpURLConnection)url.openConnection();
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("accept", "*/*");
            urlConn.setRequestProperty("connection", "Keep-Alive");
            urlConn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            urlConn.setRequestProperty("Content-Type","text/xml;charset=UTF-8");
            urlConn.setRequestProperty("Content-Length",strPostData.getBytes().length+"" );
          //  urlConn.setRequestProperty("user-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; MS Web Services Client Protocol 2.0.50727.4223)");
            urlConn.setConnectTimeout(time);
            urlConn.setReadTimeout(time);
            OutputStreamWriter out = new OutputStreamWriter(urlConn.getOutputStream(), encoding); 
            out.write(strPostData); 
            out.flush(); 
            out.close();
            in = urlConn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in,encoding));
            String line = rd.readLine();
            while( line != null )
            {
            	line = line.trim();
            	if( ! line.equals("") )
            	   sb.append(line);
            	line = rd.readLine();
            }
          
		}       
		catch (Exception e)
		{
            e.printStackTrace();
        }  
		finally
		{
			if( in != null )
				try
				{
					in.close();
				} catch (IOException e)
				{
				}
			
            if(urlConn!=null)
            {
            	urlConn.disconnect();
            }
        }
		
		return sb.toString();
	}

}
