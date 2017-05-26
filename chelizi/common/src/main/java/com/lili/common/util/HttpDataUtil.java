/**
 * Date: 2014-2-18
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * HTTP请求
 * 
 * @author saly.bao
 */
public class HttpDataUtil
{

    public static boolean isHttpURLOpen(String path)
    {
        try
        {
            URL url = new URL(path);
            HttpURLConnection urlConn = (HttpURLConnection) url
                    .openConnection();
            urlConn.setRequestMethod("HEAD");
            String strMessage = urlConn.getResponseMessage();
            if (strMessage.compareTo("Not Found") == 0)
            {
                return false;
            }
            urlConn.disconnect();
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
    
    public static byte[] getHttpURLBytes(String path)
            throws MalformedURLException, ProtocolException, IOException
    {
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
        urlConn.setRequestMethod("POST");
        // 连接，上面对urlConn的所有配置必须要在connect之前完成
        urlConn.setConnectTimeout(300000);// 连接主机的超时时间（单位：毫秒）
        urlConn.setReadTimeout(300000);// 从主机读取数据的超时时间（单位：毫秒）
        urlConn.connect();
        // 调用HttpURLConnection连接对象的getInputStream()函数，
        // 将内存缓冲区中封装好的完整的HTTP请求电文发送服务端
        InputStream inStrm = urlConn.getInputStream();// 注意，实际发送请求的代码段就在这里
        return toByteArray(inStrm);
    }

    /**
     * 参数超长用
     * @param path
     * @param params
     * @return
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public static byte[] getHttpURLBytes(String path, String params)
            throws MalformedURLException, ProtocolException, IOException
    {
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
        urlConn.setRequestMethod("POST");
        // 连接，上面对urlConn的所有配置必须要在connect之前完成
        urlConn.setConnectTimeout(300000);// 连接主机的超时时间（单位：毫秒）
        urlConn.setReadTimeout(300000);// 从主机读取数据的超时时间（单位：毫秒）
     // post参数
        byte[] bytes = params.getBytes();
        urlConn.getOutputStream().write(bytes,0,bytes.length);
        urlConn.getOutputStream().flush();
        urlConn.getOutputStream().close();
//        urlConn.setRequestProperty("userID", params.split("=")[1]);
        urlConn.connect();
        
        
        // 调用HttpURLConnection连接对象的getInputStream()函数，
        // 将内存缓冲区中封装好的完整的HTTP请求电文发送服务端
        InputStream inStrm = urlConn.getInputStream();// 注意，实际发送请求的代码段就在这里
        return toByteArray(inStrm);
    }

    /**
     * InputStream转化为byte[]数组
     * 
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer)))
        {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public static String getHttpURL(String path) throws MalformedURLException,
            ProtocolException, IOException
    {
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
        urlConn.setRequestMethod("POST");
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
        while (str != null)
        {
            sb.append(str);
            str = bReader.readLine();
        }
        return sb.toString();
    }

    public static String getHttpURL(String path,String method) throws MalformedURLException,
    ProtocolException, IOException
	{
    	if(method==null||method.length()==0) method="GET";
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
		while (str != null)
		{
		    sb.append(str);
		    str = bReader.readLine();
		}
		return sb.toString();
	}
    
    public static void sendHttpURLByte(String path, byte[] b)
            throws MalformedURLException, ProtocolException, IOException
    {
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
        urlConn.setRequestMethod("POST");
        // 连接，上面对urlConn的所有配置必须要在connect之前完成
        urlConn.setConnectTimeout(10000);// 连接主机的超时时间（单位：毫秒）
        urlConn.setReadTimeout(10000);// 从主机读取数据的超时时间（单位：毫秒）
        OutputStream out = urlConn.getOutputStream();
        // ObjectOutputStream oos = new ObjectOutputStream(out);
        // oos.write(b);
        // oos.flush();
        // oos.close();
        // DataOutputStream dos = new DataOutputStream(os);
        out.write(b);
        out.flush();
        out.close();
        urlConn.connect();
        urlConn.getInputStream();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List getClassObjList(String xmlText, Class<?> xmlClass)
    {
        if (xmlText != null && !"".equals(xmlText))
        {
            List objList = new ArrayList();
            try
            {
                Document document = DocumentHelper.parseText(xmlText);
                Element rootElement = document.getRootElement();
                List<Element> elements = rootElement.elements("Item");
                for (Element element : elements)
                {
                    Object obj = xmlClass.newInstance();
                    int index = element.attributeCount();
                    for (int i = 0; i < index; i++)
                    {
                        Attribute attribute = element.attribute(i);
                        Field field = obj.getClass().getField(
                                attribute.getName());
                        if (null != field)
                        {
                            field.setAccessible(true);
                            String typeName = field.getType().getName();
                            Object data = attribute.getData();
                            if (typeName.equals("int")
                                    || typeName.equals(Integer.class.getName()))
                            {
                                field.setInt(
                                        obj,
                                        data == null ? 0 : Integer.valueOf(data
                                                .toString()));
                            }
                            else if (typeName.equals(String.class.getName()))
                            {
                                field.set(obj, data);
                            }
                            else if (typeName.equals("boolean")
                                    || typeName.equals(Boolean.class.getName()))
                            {
                                field.setBoolean(obj, data == null ? false
                                        : Boolean.valueOf(data.toString()));
                            }
                            else if (typeName.equals("float")
                                    || typeName.equals(Float.class.getName()))
                            {
                                field.setFloat(
                                        obj,
                                        data == null ? 0f : Float.valueOf(data
                                                .toString()));
                            }
                            else if (typeName.equals("short")
                                    || typeName.equals(Short.class.getName()))
                            {
                                field.setShort(
                                        obj,
                                        data == null ? 0 : Short.valueOf(data
                                                .toString()));
                            }
                            else if (typeName.equals("long")
                                    || typeName.equals(Long.class.getName()))
                            {
                                field.setLong(
                                        obj,
                                        data == null ? 0l : Long.valueOf(data
                                                .toString()));
                            }
                            else if (typeName.equals("double")
                                    || typeName.equals(Double.class.getName()))
                            {
                                field.setDouble(
                                        obj,
                                        data == null ? 0d : Double.valueOf(data
                                                .toString()));
                            }
                            else if (typeName.equals("char") && data != null)
                            {
                                field.setChar(obj, (Character) data);
                            }
                            else if (typeName.equals("byte")
                                    || typeName.equals(Byte.class.getName()))
                            {
                                field.setByte(
                                        obj,
                                        data == null ? 0 : Byte.valueOf(data
                                                .toString()));
                            }
                        }
                    }
                    objList.add(obj);
                }
                return objList;
            }
            catch (InstantiationException e1)
            {
                e1.printStackTrace();
            }
            catch (IllegalAccessException e1)
            {
                e1.printStackTrace();
            }
            catch (DocumentException e)
            {
                e.printStackTrace();
            }
            catch (NoSuchFieldException e)
            {
                e.printStackTrace();
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
