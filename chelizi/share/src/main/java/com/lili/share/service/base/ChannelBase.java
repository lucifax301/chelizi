package com.lili.share.service.base;

import java.util.List;
import java.util.Date;
import com.lili.common.util.BaseQuery;
import com.lili.share.vo.ChannelVo;
import com.lili.common.util.RedisRealTime;
import com.lili.share.dao.po.ChannelPo;
import com.lili.share.dao.po.ChannelQuery;
import com.lili.share.service.BaseService;
import com.lili.share.dao.mapper.ChannelMapper;
import com.lili.share.service.ChannelService;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import com.lili.common.util.BeanCopy;
public abstract class ChannelBase implements ChannelService, BaseService {

	@Autowired
	protected RedisRealTime redisRealTime;
	@Autowired
	protected ChannelMapper channelMapper;

	public void add(ChannelVo channelVo) throws Exception {
		if(channelVo==null) {
			return;
		}
		if(this.isNumPk()) {
			Integer pkid=redisRealTime.getPrimaryKey(this,1);
			channelVo.setCid(pkid);
		}
		ChannelPo po=BeanCopy.copyAll(channelVo,ChannelPo.class);
		channelMapper.add(po);
	}
	public void addList(List<ChannelVo> channelVoList) throws Exception {
		if(channelVoList==null||channelVoList.isEmpty()) {
			return;
		}
		if(this.isNumPk()) {
			Integer pkid=redisRealTime.getPrimaryKey(this,channelVoList.size());
			for(ChannelVo one:channelVoList){
				one.setCid(pkid++);
			}
		}
		List<ChannelPo> poList=BeanCopy.copyList(channelVoList,ChannelPo.class,BeanCopy.COPYALL);
		channelMapper.addList(poList);
	}
	public void updateObject(ChannelVo channelVo) throws Exception {
		if(channelVo==null) {
			return;
		}
		ChannelPo po=BeanCopy.copyAll(channelVo,ChannelPo.class);
		channelMapper.updateObject(po);
	}
	public void updateList(List<ChannelVo> channelVoList) throws Exception {
		if(channelVoList==null||channelVoList.isEmpty()) {
			return;
		}
		List<ChannelPo> poList=BeanCopy.copyList(channelVoList,ChannelPo.class,BeanCopy.COPYALL);
		channelMapper.updateList(poList);
	}
	public void delById(Integer cid) throws Exception {
		if(cid==null) {
			return;
		}
		channelMapper.delById(cid);
		ChannelVo vo=new ChannelVo();
		vo.setCid( cid);
	}
	public void delListByIds(List<Integer> ids) throws Exception {
		if(ids==null||ids.isEmpty()) {
			return;
		}
		channelMapper.delListByIds(ids);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		for(Integer one:ids) {
			ChannelVo vo=new ChannelVo();
			vo.setCid(one);
			voList.add(vo);
		}
	}
	public void save(ChannelVo channelVo) throws Exception {
		if(channelVo==null) {
			return;
		}
		Integer pkid=channelVo.getCid();
		if(pkid==null) {
			add(channelVo);
		} else {
			updateObject(channelVo);
		}
	}
	public void saveList(List<ChannelVo> channelVoList) throws Exception {
		if(channelVoList==null||channelVoList.isEmpty()) {
			return;
		}
		List<ChannelVo> addList=new ArrayList<ChannelVo>();
		List<ChannelVo> updateList=new ArrayList<ChannelVo>();
		for(ChannelVo one:channelVoList) {
			Integer pkid=one.getCid();
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
	public ChannelVo queryById(Integer cid) throws Exception {
		if(cid==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		ChannelPo po=channelMapper.queryById(cid);
		ChannelVo vo=new ChannelVo();
		if(po!=null) {
			vo=BeanCopy.copyAll(po,ChannelVo.class);
		}
		return vo;
	}
	public List<ChannelVo> queryListByIds(List<Integer> ids) throws Exception {
		if(ids==null||ids.isEmpty()) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		List<ChannelPo> poList=channelMapper.queryListByIds(ids,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && poList.size()>0) {
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByObjectAnd(ChannelVo channelVo,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(channelVo==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		query.setOrderBy(orderBy);
		ChannelPo po=BeanCopy.copyAll(channelVo,ChannelPo.class);
		List<ChannelPo> poList=channelMapper.queryByObjectAnd(po,query);
		if(poList!=null && poList.size()>0) {
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
			return voList;
		}
		return voList;
	}
	public List<ChannelVo> queryBySuid(String suid,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(suid==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryBySuid(suid,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryBySendPhone(String sendPhone,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendPhone==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryBySendPhone(sendPhone,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryBySendType(Integer sendType,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendType==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryBySendType(sendType,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryBySendUser(Long sendUser,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendUser==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryBySendUser(sendUser,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryBySendState(Integer sendState,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendState==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryBySendState(sendState,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryBySendTime(Date sendTime,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendTime==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryBySendTime(sendTime,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryBySendTotal(Integer sendTotal,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(sendTotal==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryBySendTotal(sendTotal,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByRecevieName(String recevieName,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(recevieName==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByRecevieName(recevieName,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByReceviePhone(String receviePhone,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(receviePhone==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByReceviePhone(receviePhone,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByRecevieUser(Long recevieUser,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(recevieUser==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByRecevieUser(recevieUser,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByRecevieState(Integer recevieState,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(recevieState==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByRecevieState(recevieState,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByRecevieCoupon(Integer recevieCoupon,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(recevieCoupon==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByRecevieCoupon(recevieCoupon,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByRegName(String regName,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(regName==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByRegName(regName,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByRegPic(String regPic,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(regPic==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByRegPic(regPic,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByRegType(Integer regType,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(regType==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByRegType(regType,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByCheckState(Integer checkState,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(checkState==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByCheckState(checkState,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByIsdel(Integer isdel,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(isdel==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByIsdel(isdel,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByCuid(Long cuid,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(cuid==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByCuid(cuid,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByMuid(Long muid,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(muid==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByMuid(muid,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByCtime(Date ctime,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(ctime==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByCtime(ctime,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByMtime(String mtime,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(mtime==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		List<ChannelPo> poList=channelMapper.queryByMtime(mtime,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<ChannelVo> queryByObject(ChannelVo channelVo,Integer pageSize,Integer pageIndex,String orderBy) throws Exception {
		if(channelVo==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		query.setPageSize(pageSize);
		query.setPageIndex(pageIndex);
		query.setOrderBy(orderBy);
		ChannelPo po=BeanCopy.copyAll(channelVo,ChannelPo.class);
		List<ChannelPo> poList=channelMapper.queryByObject(po,query);
		if(poList!=null && poList.size()>0) {
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
			return voList;
		}
		return voList;
	}
	public ChannelVo reloadById(Integer cid) throws Exception {
		if(cid==null) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		ChannelPo po=channelMapper.queryById(cid);
		ChannelVo vo=new ChannelVo();
		if(po!=null) {
			vo=BeanCopy.copyAll(po,ChannelVo.class);
		}
		return vo;
	}
	public List<ChannelVo> reloadListByIds(List<Integer> ids) throws Exception {
		if(ids==null||ids.isEmpty()) {
			return null;
		}
		BaseQuery query=new ChannelQuery();
		List<ChannelPo> poList=channelMapper.queryListByIds(ids,query);
		List<ChannelVo> voList=new ArrayList<ChannelVo>();
		if(poList!=null && poList.size()>0) {
			voList=BeanCopy.copyList(poList,ChannelVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

	public String getPkFieldName() {
		return "cid";
	}
	public String getProjectVoKey(){
		return "share.ChannelVo";
	}
	public <T> T getMaxPk(){
		return (T)channelMapper.queryMaxPk();
	}
	public boolean isNumPk(){
		return true;
	}

}
