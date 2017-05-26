package com.lili.file.service;

import com.lili.common.vo.ReqResult;
import com.lili.file.dto.Device;

public interface FileService {

	public ReqResult getUptoken(String userId,String userType,String tokenId) throws Exception;

	public ReqResult getDownUrl(String userId,String userType, String picType, String style, String carId, boolean isCheckCar)throws Exception;
	
	public ReqResult getDownUrlByKey(String userId,String userType, String picName, String style)throws Exception;
	
	public ReqResult getRegion(String strLevel) throws Exception;

	public ReqResult addDevice(String userId, String userType, String osType, String osv, String deviceType,
			String imei, String mac, String imsi, String mobile, String ca, String ac, String lge, String lae,
			String appPackName, String appVersion, String appCode, String jpush);
	
	public Device getDevice(String userId, String userType);

	public ReqResult getTrfields(String keyword,String rid,String imei);

	public ReqResult postTrfields(String tid, String lge, String lae,String posDesc,
			String osType, String osv, String deviceType, String imei);

	public ReqResult post3Trfields(String name, String address, String school,
			String province, String city, String district, String baiduaddr,
			String lge, String lae, String mobile, String imei);

//	public ReqResult getPoster(String userType);

	/*public ReqResult postPoster(Poster pos);*/

	public ReqResult getCoursenew(String userId, String userType,String cityId,String v);

	public ReqResult getCoursenewTree(String userId, String userType);

	public ReqResult postFeedback(String fbtitle, String fbcontent,
			String fbpicture, String userId, String userType);

	public ReqResult getConfigs(String userId, String userType, String mtime);

	public ReqResult getCoursenew(String cityId, String v);

	public ReqResult getConfigFile(String cityId, String userId, String userType, String menu, String type, String channel);

	public ReqResult getCoursenewValid(String userId, String userType,
			String cityId, String v);

	public ReqResult getTitel(String typeId, String channel, String name);

	public ReqResult getContent(String id);

	public ReqResult getUpPublicToken(String userId, String userType, String tokenId);

}
