package com.lili.cms.util;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;

public class StringUtil {

	  public static final String BLANK_STR = "";
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * null、“ ”、“null”都返回true
	 * @return
	 */
	public static boolean isNullString(String str) {
		return (null == str || StringUtils.isBlank(str.trim()) || "null".equals(str.trim().toLowerCase())) ? true : false;
	}

	public static boolean isNull(Object str) {
		return (null == str || StringUtils.isBlank(str.toString().trim()) || "null".equals(str.toString().trim().toLowerCase())) ? true : false;
	}
	
	/**
	 * 格式化字符串
	 * 如果为空，返回“”
	 * @param str
	 * @return
	 */
	public static String formatString(String str) {
		if(isNullString(str)) {
			return "";
		} else {
			return str;
		}
	}
	
	/**
	 * 截取字符串，字母、汉字都可以，汉字不会截取半
	 * @param str 字符串
	 * @param n 截取的长度，字母数，如果为汉字，一个汉字等于两个字母数
	 * @return
	 */
	public static String subStringByByte(String str, int n){
		int num = 0;
		try {
			byte[] buf = str.getBytes("GBK");
			if(n>=buf.length){
				return str;
			}
			boolean bChineseFirstHalf = false;
			for(int i=0;i<n;i++)
			{
				if(buf[i]<0 && !bChineseFirstHalf){
					bChineseFirstHalf = true;
				}else{
					num++;
					bChineseFirstHalf = false;				
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str.substring(0,num);
	}
	
	public static Integer stringToInteger(String str){
		return Integer.valueOf(isNullString(str)?"0":str);
	}
	
	public static Long stringToLong(String str){
		return Long.valueOf(isNullString(str)?"0":str);
	}
	
    public static String getUUID(){
    	return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    public static String getNumber4Random(){ 
    	Long xx = Math.round(Math.random()*9999);
    	while(xx<1000){
    		xx = Math.round(Math.random()*9999);
    	}
    	
    	return String.valueOf(xx);
    }
    
    public static String getChinaPayWaterId(){
    	Date date = new Date(); 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss"); 
    	return dateFormat.format(date) + StringUtil.getNumber4Random();
    }
    
    /**
     * 根据  symbol 符号来重组字符串,输出(xx,xx,xx)
     * @param str
     * @param symbol
     * @return
     */
    public static String recombinateStr(String str,String symbol){
    	if(isNull(str))
    		return "";
    	if(isNull(symbol))
    		return str.toString();

		String[] strArray = str.toString().split(symbol);

		StringBuffer ids = new StringBuffer();
		if(strArray != null && strArray.length > 0){
			for(String item : strArray){
				ids.append("'" +item + "',");
			}
		}
		return ids.substring(0, ids.length() - 1);
    }

    /**
     * 通过逗号重组字符串,输出(xx,xx,xx)
     * @param str
     * @return 返回的字符串直接用做SQL中 in 的条件
     */
    public static String recombinateStrByComma(String str){
    	return recombinateStr(str, ",");
    	
    }
    
    /**
     * 通过逗号重组字符串,输出List<String>
     * @param str
     * @return 返回的序列直接用做SQL中 in 的条件
     */
    public static List<Long> getIdListByStr(String str){
    	if(isNull(str))
    		return null;
		String[] strArray = str.toString().split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : strArray){
			idList.add(Long.parseLong(id));
		}
    	return idList;
    }
    
    /**
     * 格式化金额，不足12位前补零
     * @param money
     * @return
     */
    public static String getTransAmt(Integer money){
    	 String str = String.format("%012d", money);  
    	 return str;
    }
    
    public static String tuofeng(String org, boolean first)
    {
        String tuofeng = org;
        List<String> all = new ArrayList<String>();
        char[] name = org.toCharArray();
        boolean need = true;
        for (int i = 0; i < name.length; i++)
        {
            if (need)
            {
                all.add(String.valueOf(name[i]));
            }
            if (name[i] == '_')
            {
                need = true;
            }
            else
            {
                need = false;
            }
        }
        if (all.size() > 0)
        {
            if (first)
            {
                tuofeng = tuofeng.replaceFirst(all.get(0), all.get(0).toUpperCase());
            }
            for (int i = 1; i < all.size(); i++)
            {
                tuofeng = tuofeng.replace("_" + all.get(i), all.get(i).toUpperCase());
            }
        }
        return tuofeng;
    }
    
}