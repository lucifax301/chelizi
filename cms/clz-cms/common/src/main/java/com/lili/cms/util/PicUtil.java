package com.lili.cms.util;

import com.qiniu.util.Auth;

/**
 * 从七牛上获取图片
 * @author Hughes
 *
 */
public class PicUtil {

//	private final static String BUCKET = "lili";
	private final static String BUCKET_PUBLIC = "lili-public";
	private final static String AK = "wTZfpMov09_Pvgpzt01kVbTGoFKMcMf2CUmYs5n2";
	private final static String SK = "CzQMFHoGeNOVuF_0sG96oFzrQtVdRx25-aQrudp1";	
	private final static String DOMAIN = "http://7xnvu2.com1.z0.glb.clouddn.com/";
	
	static Auth auth = Auth.create(AK, SK);
	
	/**
	 * 获取七牛上存储的图片路径
	 * @param headIconName
	 * @return
	 */
	public static String getPicFromQN(String headIconName){

		String resoureName ="FnjqWFunVDKhLb3fDO0OeDCFzuWB";//默认图片
		if(StringUtil.isNullString(headIconName)){
			return auth.privateDownloadUrl(DOMAIN + resoureName);
		}else {
			long expires = System.currentTimeMillis()/1000L + 600; //十分钟
			return auth.privateDownloadUrl(DOMAIN + headIconName, expires);
		}
	}
	
	/**
	 * 获取七牛图片上传public token
	 * @return
	 */
	public static String getPublicToken(){
		return auth.uploadToken(BUCKET_PUBLIC);
	}
	
	
}
