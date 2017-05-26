package com.lili.common.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.lili.common.constant.JpushConstant;
import com.lili.common.util.StringUtil;

public class JpushMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4837914621958536008L;
	
	private String title;
	private String alter;
	private Map<String,String> extras;
	private String alias;
	private Long userId;
	private Integer userType;
	private String token;
	private String env;
	private String sound;
	private String badge;
	private String sendAll;
	//防止重复推送
	private Set<Long> userIds=new HashSet<Long>();
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAlter() {
		return alter;
	}
	public void setAlter(String alter) {
		this.alter = alter;
	}
	public Map<String, String> getExtras() {
		return extras;
	}
	public void setExtras(Map<String, String> extras) {
		this.extras = extras;
	}	
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	//1.使用者定义
	public String [] getAlias() {
		String [] result=new String[1];
		//增加环境后缀
		String post="";
		if(StringUtil.isNotNullAndNotEmpty(env)&&!env.equals("publish")){
			post="_" + env;
		}
		//变量初始化
		if(alias==null||alias.isEmpty()){
			//2.token
			if(userIds!=null && !userIds.isEmpty()){
				result=new String[userIds.size()];
				Iterator<Long> it=userIds.iterator();
				int i=0;
				while(it.hasNext()){
					result[i++]=it.next()+"_"+userType+post;
				}
			} else if(token!=null){
				result[0]=token+post;
			//3.userId
			} else  {
				result[0]=userId+"_"+userType+post;
			}
		} else {
			//直接别名不增加环境后缀
			result[0]=alias;
		}
		return result;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setOperate(String operate){
		if(extras==null){
			extras=new HashMap<String,String>();
		}
		extras.put(JpushConstant.EXTRAFIELD.OPERATE, operate);
	}
	public void setOrderId(String orderId){
		if(extras==null){
			extras=new HashMap<String,String>();
		}
		extras.put(JpushConstant.EXTRAFIELD.ORDERID, orderId);
	}
	public String getOperate(){
		if(extras!=null){
			return extras.get(JpushConstant.EXTRAFIELD.OPERATE);
		}
		return null;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public String getBadge() {
		return badge;
	}
	public void setBadge(String badge) {
		this.badge = badge;
	}
	public Set<Long> getUserIds() {
		return userIds;
	}
	public void setUserIds(Set<Long> userIds) {
		this.userIds = userIds;
	}
	public int addUser(Long userId){
		this.userIds.add(userId);
		return userIds.size();
	}
	
	public String getSendAll() {
		return sendAll;
	}
	public void setSendAll(String sendAll) {
		this.sendAll = sendAll;
	}
	public String toString(){
		return "{title="+title+",alter="+alter+",alias="+getAlias()[0]+",extras="+extras+",sound="+sound+",badge="+badge+",userIds="+userIds+"}";
	}
}
