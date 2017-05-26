package com.lili.share.service.base;

import java.util.List;
import java.util.Date;
import com.lili.share.vo.ShareVo;
import com.lili.common.util.BaseQuery;
import com.lili.share.dao.po.ShareQuery;
import com.lili.common.util.RedisRealTime;
import com.lili.share.dao.po.SharePo;
import com.lili.share.service.BaseService;
import com.lili.share.dao.mapper.ShareMapper;
import com.lili.share.service.ShareService;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import com.lili.common.util.BeanCopy;
public abstract class ShareBase implements ShareService, BaseService {

	@Autowired
	protected ShareMapper shareMapper;
	@Autowired
	protected RedisRealTime redisRealTime;

	public void add(ShareVo shareVo) throws Exception {
		if(shareVo==null) {
			return;
		}
		if(this.isNumPk()) {
			Integer pkid=redisRealTime.getPrimaryKey(this,1);
			shareVo.setSid(pkid);
		}
		SharePo po=BeanCopy.copyAll(shareVo,SharePo.class);
		shareMapper.add(po);
	}
	public void addList(List<ShareVo> shareVoList) throws Exception {
		if(shareVoList==null||shareVoList.isEmpty()) {
			return;
		}
		if(this.isNumPk()) {
			Integer pkid=redisRealTime.getPrimaryKey(this,shareVoList.size());
			for(ShareVo one:shareVoList){
				one.setSid(pkid++);
			}
		}
		List<SharePo> poList=BeanCopy.copyList(shareVoList,SharePo.class,BeanCopy.COPYALL);
		shareMapper.addList(poList);
	}
	public void updateObject(ShareVo shareVo) throws Exception {
		if(shareVo==null) {
			return;
		}
		SharePo po=BeanCopy.copyAll(shareVo,SharePo.class);
		shareMapper.updateObject(po);
	}
	public void updateList(List<ShareVo> shareVoList) throws Exception {
		if(shareVoList==null||shareVoList.isEmpty()) {
			return;
		}
		List<SharePo> poList=BeanCopy.copyList(shareVoList,SharePo.class,BeanCopy.COPYALL);
		shareMapper.updateList(poList);
	}
	public void delById(Integer sid) throws Exception {
		if(sid==null) {
			return;
		}
		shareMapper.delById(sid);
		ShareVo vo=new ShareVo();
		vo.setSid( sid);
	}
	public void delListByIds(List<Integer> ids) throws Exception {
		if(ids==null||ids.isEmpty()) {
			return;
		}
		shareMapper.delListByIds(ids);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		for(Integer one:ids) {
			ShareVo vo=new ShareVo();
			vo.setSid(one);
			voList.add(vo);
		}
	}
	public void save(ShareVo shareVo) throws Exception {
		if(shareVo==null) {
			return;
		}
		Integer pkid=shareVo.getSid();
		if(pkid==null) {
			add(shareVo);
		} else {
			updateObject(shareVo);
		}
	}
	public void saveList(List<ShareVo> shareVoList) throws Exception {
		if(shareVoList==null||shareVoList.isEmpty()) {
			return;
		}
		List<ShareVo> addList=new ArrayList<ShareVo>();
		List<ShareVo> updateList=new ArrayList<ShareVo>();
		for(ShareVo one:shareVoList) {
			Integer pkid=one.getSid();
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
	public ShareVo queryById(Integer sid) throws Exception {
		if(sid==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		SharePo po=shareMapper.queryById(sid);
		ShareVo vo=new ShareVo();
		if(po!=null) {
			vo=BeanCopy.copyAll(po,ShareVo.class);
		}
		return vo;
	}
	public List<ShareVo> queryListByIds(List<Integer> ids) throws Exception {
		if(ids==null||ids.isEmpty()) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		List<SharePo> poList=shareMapper.queryListByIds(ids,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && poList.size()>0) {
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByObjectAnd(ShareVo shareVo,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(shareVo==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		List<ShareVo> voList=new ArrayList<ShareVo>();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		query.setOrderBy(orderBy);
		SharePo po=BeanCopy.copyAll(shareVo,SharePo.class);
		List<SharePo> poList=shareMapper.queryByObjectAnd(po,query);
		if(poList!=null && poList.size()>0) {
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
			return voList;
		}
		return voList;
	}
	public List<ShareVo> queryBySuid(String suid,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(suid==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryBySuid(suid,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryBySendPhone(String sendPhone,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendPhone==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryBySendPhone(sendPhone,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryBySendType(Integer sendType,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendType==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryBySendType(sendType,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryBySendUser(Long sendUser,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendUser==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryBySendUser(sendUser,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryBySendState(Integer sendState,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendState==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryBySendState(sendState,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryBySendTime(Date sendTime,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendTime==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryBySendTime(sendTime,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryBySendTotal(Integer sendTotal,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendTotal==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryBySendTotal(sendTotal,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByRecevieName(String recevieName,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(recevieName==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByRecevieName(recevieName,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByReceviePhone(String receviePhone,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(receviePhone==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByReceviePhone(receviePhone,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByRecevieUser(Long recevieUser,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(recevieUser==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByRecevieUser(recevieUser,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByRecevieState(Integer recevieState,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(recevieState==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByRecevieState(recevieState,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByRecevieCoupon(Integer recevieCoupon,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(recevieCoupon==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByRecevieCoupon(recevieCoupon,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByRegName(String regName,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(regName==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByRegName(regName,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByRegPic(String regPic,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(regPic==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByRegPic(regPic,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByRegType(Integer regType,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(regType==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByRegType(regType,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByCheckState(Integer checkState,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(checkState==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByCheckState(checkState,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByIsdel(Integer isdel,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(isdel==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByIsdel(isdel,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByCuid(Long cuid,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(cuid==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByCuid(cuid,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByMuid(Long muid,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(muid==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByMuid(muid,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByCtime(Date ctime,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(ctime==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByCtime(ctime,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByMtime(String mtime,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(mtime==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<SharePo> poList=shareMapper.queryByMtime(mtime,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ShareVo> queryByObject(ShareVo shareVo,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(shareVo==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		List<ShareVo> voList=new ArrayList<ShareVo>();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		SharePo po=BeanCopy.copyAll(shareVo,SharePo.class);
		List<SharePo> poList=shareMapper.queryByObject(po,query);
		if(poList!=null && poList.size()>0) {
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
			return voList;
		}
		return voList;
	}
	public ShareVo reloadById(Integer sid) throws Exception {
		if(sid==null) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		SharePo po=shareMapper.queryById(sid);
		ShareVo vo=new ShareVo();
		if(po!=null) {
			vo=BeanCopy.copyAll(po,ShareVo.class);
		}
		return vo;
	}
	public List<ShareVo> reloadListByIds(List<Integer> ids) throws Exception {
		if(ids==null||ids.isEmpty()) {
			return null;
		}
		BaseQuery query=new ShareQuery();
		List<SharePo> poList=shareMapper.queryListByIds(ids,query);
		List<ShareVo> voList=new ArrayList<ShareVo>();
		if(poList!=null && poList.size()>0) {
			voList=BeanCopy.copyList(poList,ShareVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

	public String getPkFieldName() {
		return "sid";
	}
	public String getProjectVoKey(){
		return "share.ShareVo";
	}
	public <T> T getMaxPk(){
		return (T)shareMapper.queryMaxPk();
	}
	public boolean isNumPk(){
		return true;
	}

}
