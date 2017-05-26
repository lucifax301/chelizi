package com.lili.share.service.base;

import java.util.List;
import java.util.Date;
import com.lili.share.vo.ShareUserVo;
import com.lili.common.util.BaseQuery;
import com.lili.share.dao.po.ShareUserPo;
import com.lili.common.util.RedisRealTime;
import com.lili.share.dao.po.ShareUserQuery;
import com.lili.share.service.BaseService;
import com.lili.share.dao.mapper.ShareUserMapper;
import com.lili.share.service.ShareUserService;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import com.lili.common.util.BeanCopy;
public abstract class ShareUserBase implements ShareUserService, BaseService {

	@Autowired
	protected ShareUserMapper shareUserMapper;
	@Autowired
	protected RedisRealTime redisRealTime;

	public void add(ShareUserVo shareUserVo) throws Exception {
		if(shareUserVo==null) {
			return;
		}
		ShareUserPo po=BeanCopy.copyAll(shareUserVo,ShareUserPo.class);
		shareUserMapper.add(po);
	}
	public void addList(List<ShareUserVo> shareUserVoList) throws Exception {
		if(shareUserVoList==null||shareUserVoList.isEmpty()) {
			return;
		}
		List<ShareUserPo> poList=BeanCopy.copyList(shareUserVoList,ShareUserPo.class,BeanCopy.COPYALL);
		shareUserMapper.addList(poList);
	}
	public void updateObject(ShareUserVo shareUserVo) throws Exception {
		if(shareUserVo==null) {
			return;
		}
		ShareUserPo po=BeanCopy.copyAll(shareUserVo,ShareUserPo.class);
		shareUserMapper.updateObject(po);
	}
	public void updateList(List<ShareUserVo> shareUserVoList) throws Exception {
		if(shareUserVoList==null||shareUserVoList.isEmpty()) {
			return;
		}
		List<ShareUserPo> poList=BeanCopy.copyList(shareUserVoList,ShareUserPo.class,BeanCopy.COPYALL);
		shareUserMapper.updateList(poList);
	}
	public void delById(String suid) throws Exception {
		if(suid==null) {
			return;
		}
		shareUserMapper.delById(suid);
		ShareUserVo vo=new ShareUserVo();
		vo.setSuid( suid);
	}
	public void delListByIds(List<String> ids) throws Exception {
		if(ids==null||ids.isEmpty()) {
			return;
		}
		shareUserMapper.delListByIds(ids);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		for(String one:ids) {
			ShareUserVo vo=new ShareUserVo();
			vo.setSuid(one);
			voList.add(vo);
		}
	}
	public void save(ShareUserVo shareUserVo) throws Exception {
		if(shareUserVo==null) {
			return;
		}
		String pkid=shareUserVo.getSuid();
		if(pkid==null) {
			add(shareUserVo);
		} else {
			updateObject(shareUserVo);
		}
	}
	public void saveList(List<ShareUserVo> shareUserVoList) throws Exception {
		if(shareUserVoList==null||shareUserVoList.isEmpty()) {
			return;
		}
		List<ShareUserVo> addList=new ArrayList<ShareUserVo>();
		List<ShareUserVo> updateList=new ArrayList<ShareUserVo>();
		for(ShareUserVo one:shareUserVoList) {
			String pkid=one.getSuid();
			if(pkid==null) {
				addList.add(one);
			} else {
				updateList.add(one);
			}
		}
		if(!addList.isEmpty()) {
			addList(addList);
		}
		if(!addList.isEmpty()) {
			updateList(updateList);
		}
	}
	public ShareUserVo queryById(String suid) throws Exception {
		if(suid==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		ShareUserPo po=shareUserMapper.queryById(suid);
		ShareUserVo vo=new ShareUserVo();
		if(po!=null) {
			vo=BeanCopy.copyAll(po,ShareUserVo.class);
		}
		return vo;
	}
	public List<ShareUserVo> queryListByIds(List<String> ids) throws Exception {
		if(ids==null||ids.isEmpty()) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		List<ShareUserPo> poList=shareUserMapper.queryListByIds(ids,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && poList.size()>0) {
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByObjectAnd(ShareUserVo shareUserVo,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(shareUserVo==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		query.setOrderBy(orderBy);
		ShareUserPo po=BeanCopy.copyAll(shareUserVo,ShareUserPo.class);
		List<ShareUserPo> poList=shareUserMapper.queryByObjectAnd(po,query);
		if(poList!=null && poList.size()>0) {
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
			return voList;
		}
		return voList;
	}
	public List<ShareUserVo> queryByUserName(String userName,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(userName==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryByUserName(userName,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryBySendTotal(Integer sendTotal,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendTotal==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryBySendTotal(sendTotal,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByRecevieTemplate(String recevieTemplate,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(recevieTemplate==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryByRecevieTemplate(recevieTemplate,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryBySendPhone(String sendPhone,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendPhone==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryBySendPhone(sendPhone,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryBySendType(Integer sendType,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendType==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryBySendType(sendType,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByRegType(Integer regType,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(regType==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryByRegType(regType,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryBySendUser(Long sendUser,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendUser==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryBySendUser(sendUser,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByShareUrl(String shareUrl,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(shareUrl==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryByShareUrl(shareUrl,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByDescription(String description,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(description==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryByDescription(description,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByRule(String rule,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(rule==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryByRule(rule,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByBigpic(String bigpic,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(bigpic==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryByBigpic(bigpic,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryBySmallpic(String smallpic,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(smallpic==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryBySmallpic(smallpic,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByCheckState(Integer checkState,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(checkState==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryByCheckState(checkState,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByIsdel(Integer isdel,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(isdel==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryByIsdel(isdel,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByCuid(Long cuid,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(cuid==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryByCuid(cuid,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByMuid(Long muid,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(muid==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryByMuid(muid,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByCtime(Date ctime,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(ctime==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryByCtime(ctime,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByMtime(String mtime,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(mtime==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ShareUserPo> poList=shareUserMapper.queryByMtime(mtime,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareUserVo> queryByObject(ShareUserVo shareUserVo,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(shareUserVo==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		ShareUserPo po=BeanCopy.copyAll(shareUserVo,ShareUserPo.class);
		List<ShareUserPo> poList=shareUserMapper.queryByObject(po,query);
		if(poList!=null && poList.size()>0) {
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
			return voList;
		}
		return voList;
	}
	public ShareUserVo reloadById(String suid) throws Exception {
		if(suid==null) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		ShareUserPo po=shareUserMapper.queryById(suid);
		ShareUserVo vo=new ShareUserVo();
		if(po!=null) {
			vo=BeanCopy.copyAll(po,ShareUserVo.class);
		}
		return vo;
	}
	public List<ShareUserVo> reloadListByIds(List<String> ids) throws Exception {
		if(ids==null||ids.isEmpty()) {
			return null;
		}
		BaseQuery query=new ShareUserQuery();
		List<ShareUserPo> poList=shareUserMapper.queryListByIds(ids,query);
		List<ShareUserVo> voList=new ArrayList<ShareUserVo>();
		if(poList!=null && poList.size()>0) {
			voList=BeanCopy.copyList(poList,ShareUserVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

	public String getPkFieldName() {
		return "suid";
	}
	public String getProjectVoKey(){
		return "share.ShareUserVo";
	}
	public <T> T getMaxPk(){
		return (T)shareUserMapper.queryMaxPk();
	}
	public boolean isNumPk(){
		return false;
	}

}
