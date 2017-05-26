package com.lili.common.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReqResult implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,Object> result;

    public void setCode(String code){
        if(result==null) {
            result=new HashMap<String,Object>();
        }
        result.put(ResultCode.RESULTKEY.CODE,code);
        String msgInfo=ResultCode.getCodeInfo(code);
        if(msgInfo!=null){
        	result.put(ResultCode.RESULTKEY.MSGINFO, msgInfo);
        }
    }
    public void set(String code){
        if(result==null) {
            result=new HashMap<String,Object>();
        }
        result.put(ResultCode.RESULTKEY.CODE,code);
    }
    public void setData(Object data){
        if(result==null) {
            result=new HashMap<String,Object>();
        }
        result.put(ResultCode.RESULTKEY.DATA,data);
    }
    //多个数据可以一起返回，减少客户端请求次数
    public void setData(String key,Object data){
    	if(result==null) {
    		result=new HashMap<String,Object>();
    	}
    	result.put(key,data);
    }
    
    public String getMsgKey(){
    	if(null == result){
    		return "0";
    	}
    	return (String) result.get(ResultCode.RESULTKEY.MSGKEY);
    }
    public void setMsgKey(String msgKey){
        if(result==null) {
            result=new HashMap<String,Object>();
        }
        result.put(ResultCode.RESULTKEY.MSGKEY,msgKey);
    }
    
    public String getMsgInfo(){
    	if(null == result){
    		return "OK";
    	}
    	return (String) result.get(ResultCode.RESULTKEY.MSGINFO);
    }
    public void setMsgInfo(String msgInfo){
        if(result==null) {
            result=new HashMap<String,Object>();
        }
        result.put(ResultCode.RESULTKEY.MSGINFO,msgInfo);
    }
    public static ReqResult getSuccess(){
        ReqResult reqResult=new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
        return reqResult;
    }
    public static ReqResult getFailed(){
        ReqResult reqResult=new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.FAILED);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.FAILED);
        return reqResult;
    }
    public static ReqResult getParamError() {
    	ReqResult reqResult=new ReqResult();
        reqResult.setCode(ResultCode.ERRORCODE.PARAMERROR);
        reqResult.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
        return reqResult;
    }
    
    
    public Map<String,Object> getResult(){
    	//20160310所有返回的数据中，新增数据版本号，默认与发布的版本号一致。
    	result.put("v", "1.8.0");//20160713
    	return result;
    }
    
    public boolean isSuccess()
    {
        return result.get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS);
    }
}
