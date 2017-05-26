package com.lili.file.manager;

import java.util.Map;

import com.lili.common.vo.ReqResult;
import com.lili.file.vo.CoursenewVo;

public interface FileManager {

//	public ReqResult getPoster(String userType);

	/*public ReqResult postPoster(Poster pos);*/

	public ReqResult getCoursenew(String userId, String userType,String cityId,String v);

	public ReqResult getCoursenewTree(String userId, String userType);

	public ReqResult postFeedback(String fbtitle, String fbcontent,
			String fbpicture, String userId, String userType);
	
	public CoursenewVo getCoursenewBycourseid(int courseTmpId);
	
	/**
	 * 取得所有的课程
	 * @return
	 */
	public Map<Integer, CoursenewVo> getCoursenewMap();

	public ReqResult getCoursenew(String cityId, String v);

	public ReqResult getConfigFile(String cityId, String userId, String userType, String menu, String type, String channel);

	public ReqResult getTitel(String typeId, String channel, String name);

	public ReqResult getContent(String id);
}
