package com.lili.file.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lili.coach.dto.Car;
import com.lili.coach.dto.CarCheck;
import com.lili.coach.dto.Coach;
import com.lili.coach.dto.Region;
import com.lili.coach.dto.TrfieldRaw;
import com.lili.coach.dto.Trfields;
import com.lili.coach.manager.CarManager;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.manager.RegionManager;
import com.lili.coach.manager.TrfieldRawManager;
import com.lili.coach.manager.TrfieldsManager;
import com.lili.coach.vo.RegionVo;
import com.lili.coach.vo.TrfieldsVo;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.device.manager.DeviceManager;
import com.lili.file.dto.Device;
import com.lili.file.manager.ConfigManager;
import com.lili.file.manager.FileManager;
import com.lili.file.service.FileService;
import com.lili.file.vo.CoursenewVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;
import com.qiniu.util.Auth;

public class FileServiceImpl implements FileService {
	
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	CoachManager coachManger;
	@Autowired
	CarManager carManger;
	@Autowired
	StudentManager studentManger;
	@Autowired
	RegionManager regionManager;
	@Autowired
	DeviceManager deviceManager;
	@Autowired
	TrfieldsManager trfieldsManager;
	@Autowired
	TrfieldRawManager rawManager;
	@Autowired
	FileManager fileManager;
	@Autowired
	ConfigManager configManager;
	
	//公司七牛账号
	private final String BUCKET = "lili";
	private final String AK = "wTZfpMov09_Pvgpzt01kVbTGoFKMcMf2CUmYs5n2";
	private final String SK = "CzQMFHoGeNOVuF_0sG96oFzrQtVdRx25-aQrudp1";	
	private final String DOMAIN = "http://7xnvu2.com1.z0.glb.clouddn.com/";
	
	
	private final static String BUCKET_PUBLIC = "lili-public";
	
	Auth auth = Auth.create(AK, SK);

	@Override
	public ReqResult getUptoken(String userId,String userType,String tokenId) throws Exception {
		ReqResult r = new ReqResult();
		
		Map<String,String>data = new HashMap<String, String>();
		data.put("upToken", auth.uploadToken(BUCKET));		
		
		r.setCode(ResultCode.ERRORCODE.SUCCESS);
		r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		r.setData(data);
		return r;
	}
	

	@Override
	public ReqResult getUpPublicToken(String userId, String userType, String tokenId) {
		ReqResult r = new ReqResult();
		
		Map<String,String>data = new HashMap<String, String>();
		data.put("upToken", auth.uploadToken(BUCKET_PUBLIC));		
		
		r.setCode(ResultCode.ERRORCODE.SUCCESS);
		r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		r.setData(data);
		return r;
	}

	
	@Override
	public ReqResult getDownUrl(String userId, String userType,String picType, String style, String carId, boolean isCheckCar) {
		ReqResult r = new ReqResult();
		byte ut =0;
		byte pt =0;
		try {
			ut = Byte.parseByte(userType);
			pt = Byte.parseByte(picType);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r;
		}
		
		String resoureName ="FnjqWFunVDKhLb3fDO0OeDCFzuWB";//默认图片
		//根据图片类型在数据库中找到存储名称
		if(ut == ReqConstants.USER_TYPE_COACH){
			Coach c = coachManger.getCoachInfo(Long.parseLong(userId));
			Car car = null;
			CarCheck carCheck = null;
			if (carId != null && !"".equals(carId)) { //多辆车情况
				car = carManger.getCarInfo(Integer.parseInt(carId));
				if (isCheckCar) {
					carCheck = carManger.getCarCheckByCarId(Integer.parseInt(carId));
					if (pt == ReqConstants.PIC_TYPE_CAR_DRIVING_LICENCE) { //车辆行驶证
						resoureName = carCheck.getDrivePhoto();
					}
					else if (pt == ReqConstants.PIC_TYPE_CAR_DRIVING_LICENCE_COPY) { //车辆行驶证副本
						resoureName = carCheck.getDrivePhoto2();
					}
					else if (pt == ReqConstants.PIC_TYPE_CAR_PICTURE) { //车辆图片
						resoureName = carCheck.getCarImg();
					}
				}
			} 
			else { //一辆车
				car = carManger.getCarLevelByCoachId(Long.parseLong(userId));
				if (pt == ReqConstants.PIC_TYPE_CAR_DRIVING_LICENCE) { //车辆行驶证
					resoureName = car.getDrivePhoto();
				}
				else if (pt == ReqConstants.PIC_TYPE_CAR_PICTURE) { //车辆图片
					resoureName = car.getCarImg();
				}
			}
			
			if(pt == ReqConstants.PIC_TYPE_USER_HEADICON){
				resoureName = c.getHeadIcon();
			}
			else if(pt == ReqConstants.PIC_TYPE_IDCARD_FRONT){
				resoureName = c.getIdPhotoFront();
			}
			else if(pt == ReqConstants.PIC_TYPE_IDCARD_BACK){
				resoureName = c.getIdPhotoBack();
			}
			else if(pt == ReqConstants.PIC_TYPE_USER_DRIVING_LICENCE){
				resoureName = c.getDrPhoto();
			}
			else if (pt == ReqConstants.PIC_TYPE_USER_DRIVING_LICENCE_COPY) { //驾照副本
				resoureName = c.getDrPhoto2();
			}
			else if(pt == ReqConstants.PIC_TYPE_COACH_CARD){
				resoureName = c.getCoachCardPhoto();
			}
			
		}else if(ut == ReqConstants.USER_TYPE_STUDENT){
			Student s = studentManger.getStudentInfo(Long.parseLong(userId));
			if(pt == ReqConstants.PIC_TYPE_USER_HEADICON){
				resoureName = s.getHeadIcon();
			}
		}
		
		//构造下载地址
		String baseUrl = DOMAIN + resoureName;
		if(null!= style && !"".equals(style)){
			resoureName = resoureName + "?"+ style;
		}
		long expires = System.currentTimeMillis()/1000L + 600; //十分钟

		String downUrl = auth.privateDownloadUrl(baseUrl, expires);
		
		Map<String,String>data = new HashMap<String, String>();
		data.put("downUrl", downUrl);
		
		
		r.setCode(ResultCode.ERRORCODE.SUCCESS);
		r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		r.setData(data);
		return r;
	}


	@Override
	public ReqResult getDownUrlByKey(String userId, String userType,
			String picName, String style) throws Exception {
		ReqResult r = new ReqResult();
		
		if(picName.indexOf("http://")>=0 || picName.indexOf("https://")>=0){
			Map<String,String>data = new HashMap<String, String>();
			data.put("downUrl", picName);
			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			r.setData(data);
			return r;
		}
		
		String resoureName ="FnjqWFunVDKhLb3fDO0OeDCFzuWB";//默认图片
		
		//判断传递的picName是否为空，不为空则覆盖resoureName的值
		if(StringUtils.isNotEmpty(picName)){
			resoureName = picName;
		}
		
		if(null!= style && !"".equals(style)){
			resoureName = resoureName + "?"+ style;
		}
		
		//构造下载地址
		String baseUrl = DOMAIN + resoureName;
		long expires = System.currentTimeMillis()/1000L + 600; //十分钟

		String downUrl = auth.privateDownloadUrl(baseUrl, expires);
		
		
		Map<String,String>data = new HashMap<String, String>();
		data.put("downUrl", downUrl);
		
		
		r.setCode(ResultCode.ERRORCODE.SUCCESS);
		r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
		r.setData(data);
		return r;
	}
	
	public static void main(String[] args) {
		FileServiceImpl fs = new FileServiceImpl();
		ReqResult r = ReqResult.getSuccess();
		try {
			r = fs.getDownUrlByKey(null, null, null, "imageView2/1/w/100/h/200");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(r.getResult().get("data"));
		
	}


	@Override
	public ReqResult getRegion(String strLevel) throws Exception {
		ReqResult r = ReqResult.getSuccess();
		
		List<RegionVo> voList = new ArrayList<RegionVo>();
		
		Region region = new Region();
		if(null != strLevel && !"".equals(strLevel.trim())){
			region.setRlevel(Integer.parseInt(strLevel.trim()));  //设置城市等级
		}
		region.setIsdel(0);   //设置状态为未删除
		
		List<Region> regionList = regionManager.getRegion(region);
		
		voList = BeanCopy.copyList(regionList, RegionVo.class, BeanCopy.COPYNOTNULL);
		r.setData(voList);
		
		return r;
	}


	@Override
	public ReqResult addDevice(String userId, String userType, String osType, String osv, String deviceType,
			String imei, String mac, String imsi, String mobile, String ca, String ac, String lge, String lae,
			String appPackName, String appVersion, String appCode, String jpush) {
		ReqResult r = ReqResult.getSuccess();
		try {
			Device device = new Device();
			device.setUserId(Long.parseLong(userId));
			device.setUserType(Integer.parseInt(userType));
			if(null != osType && !"".equals(osType)){
				device.setOsType(Integer.parseInt(osType));
			}
			if(null != osv && !"".equals(osv)){
				device.setOsv(osv);
			}
			if(null != deviceType && !"".equals(deviceType)){
				device.setDeviceType(deviceType);
			}
			if(null != imei && !"".equals(imei)){
				device.setImei(imei);
			}
			if(null != mac && !"".equals(mac)){
				device.setMac(mac);
			}
			if(null != imsi && !"".equals(imsi)){
				device.setImsi(imsi);
			}
			if(null != mobile && !"".equals(mobile)){
				device.setMobile(mobile);
			}
			if(null != ca && !"".equals(ca)){
				device.setCa(ca);
			}
			if(null != ac && !"".equals(ac)){
				device.setAc(ac);
			}
			if(null != lge && !"".equals(lge)){
				device.setLge(Double.parseDouble(lge));
			}
			if(null != lae && !"".equals(lae)){
				device.setLae(Double.parseDouble(lae));
			}
			if(null != appPackName && !"".equals(appPackName)){
				device.setAppPackName(appPackName);
			}
			if(null != appPackName && !"".equals(appPackName)){
				device.setAppPackName(appPackName);
			}
			if(null != appVersion && !"".equals(appVersion)){
				device.setAppVersion(appVersion);
			}
			if(null != appCode && !"".equals(appCode)){
				device.setAppCode(appCode);
			}
			if(null != jpush && !"".equals(jpush)){
				device.setJpush(Integer.parseInt(jpush));
			}
			
			deviceManager.addDevice(device);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ReqResult.getFailed();
		}
		return r;
	}


	@Override
	public Device getDevice(String userId, String userType) {
		Device device = new Device();
		device.setUserId(Long.parseLong(userId));
		device.setUserType(Integer.parseInt(userType));
		Device data = deviceManager.getDeviceByDevice(device);
		return data;
	}


	@Override
	public ReqResult getTrfields(String keyword,String rid,String imei) {
		ReqResult r = ReqResult.getSuccess();
		List<TrfieldsVo> data = trfieldsManager.getTrfieldsSpecial(keyword,rid,imei);
		r.setData(data);
		return r;
	}


	@Override
	public ReqResult postTrfields(String tid, String lge, String lae,String posDesc,
			String osType, String osv, String deviceType, String imei) {
		ReqResult r = ReqResult.getSuccess();
		Trfields tf = new Trfields();
		tf.setTrainFieldId(Integer.parseInt(tid.trim()));
		tf.setLge(Double.parseDouble(lge.trim()));
		tf.setLae(Double.parseDouble(lae.trim()));
		tf.setPosDesc(posDesc);
		tf.setOsType(Integer.parseInt(osType.trim()));
		tf.setOsv(osv);
		tf.setDeviceType(deviceType);
		tf.setImei(imei);
		trfieldsManager.addTrfields(tf);
		return r;
	}


	@Override
	public ReqResult post3Trfields(String name, String address, String school,
			String province, String city, String district, String baiduaddr,
			String lge, String lae, String mobile, String imei) {
		ReqResult r = ReqResult.getSuccess();
		TrfieldRaw raw = new TrfieldRaw();
		raw.setName(name);
		raw.setAddress(address);
		raw.setSchool(school);
		raw.setProvince(province);
		raw.setCity(city);
		raw.setDistrict(district);
		raw.setBaiduaddr(baiduaddr);
		raw.setLae(BigDecimal.valueOf(Double.parseDouble(lae)));
		raw.setLge(BigDecimal.valueOf(Double.parseDouble(lge)));
		raw.setMobile(mobile);
		raw.setImei(imei);
		rawManager.add(raw);		
		return r;
	}


/*	@Override
	public ReqResult getPoster(String userType) {
		return fileManager.getPoster(userType);
	}*/


/*	@Override
	public ReqResult postPoster(Poster pos) {
		return fileManager.postPoster(pos);
	}
*/

	@Override
	public ReqResult getCoursenew(String userId, String userType,String cityId,String v) {
		return fileManager.getCoursenew(userId,userType,cityId,v);
	}
	
	@Override
	public ReqResult getCoursenew(String cityId,String v) {
		return fileManager.getCoursenew(cityId,v);
	}


	@Override
	public ReqResult getCoursenewTree(String userId, String userType) {
		return fileManager.getCoursenewTree(userId,userType);
	}


	@Override
	public ReqResult postFeedback(String fbtitle, String fbcontent,
			String fbpicture, String userId, String userType) {
		return fileManager.postFeedback(fbtitle,fbcontent,fbpicture,userId,userType);
	}


	@Override
	public ReqResult getConfigs(String userId, String userType, String mtime) {
		return configManager.getConfigs(userId, userType, mtime);
	}

	@Override
	public ReqResult getCoursenewValid(String userId, String userType,
			String cityId, String v) {
		ReqResult r = ReqResult.getSuccess();
		ReqResult raw =fileManager.getCoursenew(userId,userType,cityId,v);
		@SuppressWarnings("unchecked")
		List<CoursenewVo> cvs = (List<CoursenewVo>) raw.getResult().get(ResultCode.RESULTKEY.DATA);
		for(int i=0;i<cvs.size();i++){
			CoursenewVo cv = cvs.get(i);
			if(null == cv.getExtra() || cv.getExtra().equals("0")){ //null或0-开放；1-提示不能预约；2-提示上传驾照；3-报名引导弹窗
				continue;
			}else{
				cvs.remove(i);
				i--;
			}
			
		}
		for(int i=0;i<cvs.size();i++){
			CoursenewVo cv = cvs.get(i);
			if(cv.getCoursetemid() == 8 ||cv.getCoursetemid() == 18){ //去掉文科题
				cvs.remove(i);
				i--;
			}else{
				continue;
			}
			
		}
		r.setData(cvs);
		return r;
	}

	@Override
	public ReqResult getConfigFile(String cityId, String userId, String userType, String menu, String type, String channel) {
		return fileManager.getConfigFile(cityId, userId, userType, menu, type, channel);
	}


	@Override
	public ReqResult getTitel(String typeId, String channel, String name) {
		return fileManager.getTitel(typeId, channel, name);
	}


	@Override
	public ReqResult getContent(String id) {
		return fileManager.getContent(id);
	}



	
}
