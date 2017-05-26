package com.lili.cms.util;

public class LogUtil {

	public static String getStackMsg(Exception e) {

        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackArray = e.getStackTrace();
        sb.append("\t"+e.getLocalizedMessage() + "\n");
        for (int i = 0; i < stackArray.length; i++) {
            StackTraceElement element = stackArray[i];
            sb.append("\t"+element.toString() + "\n");
        }
        return sb.toString();
    }
}
