package com.lili.file.manager;

import java.util.List;

import com.lili.common.util.Page;
import com.lili.common.vo.ReqResult;
import com.lili.file.dto.Poster;
import com.lili.file.dto.PosterExample;

public interface PosterManager {
	/**
	 * 获取开机广告，客户端获取最新的一张。
	 * @param userType
	 * @return
	 */
	public ReqResult getPoster(String userType);
	
	/**
	 * 获取开机广告，cms使用，分页查询
	 * @param example
	 * @return
	 */
	public Page<Poster> getPoster(PosterExample example,int pageNo, int pageSize);
	
	/**
	 * 获取开机广告，cms使用
	 * @param example
	 * @return
	 */
	public List<Poster> getPoster(PosterExample example);
	/**
	 * 新增开机广告，cms使用
	 * @param pos，posterId为null
	 * @return
	 */
	public int postPoster(Poster pos);
	
	/**
	 * 更新开机广告，cms使用，激活、停用、修改图片
	 * @param pos，posterId不为空；激活、停用设置isdel；
	 * @return
	 */
	public int putPoster(Poster pos);
}
