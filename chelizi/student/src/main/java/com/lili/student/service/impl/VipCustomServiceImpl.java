package com.lili.student.service.impl;

import java.util.List;
import java.util.Date;

import com.lili.common.util.BeanCopy;
import com.lili.student.vo.VipCustomVo;
import com.lili.student.vo.VipCustomQuery;
import com.lili.student.dao.mapper.VipCustomMapper;
import com.lili.student.dao.po.VipCustomPo;
import com.lili.student.service.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class VipCustomServiceImpl implements VipCustomService {

	@Autowired
	VipCustomMapper vipCustomMapper;;
	public void addVipCustom(VipCustomVo vipCustomvo) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		vipCustomMapper.addVipCustom(po);
	}
	public void addVipCustomList(List<VipCustomVo> vipCustomvoList) throws Exception {
		List<VipCustomPo> poList=BeanCopy.copyList(vipCustomvoList,VipCustomPo.class,BeanCopy.COPYALL);
		vipCustomMapper.addVipCustomList(poList);
	}
	public void delVipCustomById(Long studentId) throws Exception {
		vipCustomMapper.delVipCustomById(studentId);
	}
	public void delVipCustomByIds(List<Long> ids) throws Exception {
		vipCustomMapper.delVipCustomByIds(ids);
	}
	public void delVipCustomByObj(VipCustomVo vipCustomvo) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		vipCustomMapper.delVipCustomByObj(po);
	}
	public void saveVipCustom(VipCustomVo vipCustomvo) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		vipCustomMapper.saveVipCustom(po);
	}
	public void saveVipCustomList(List<VipCustomVo> vipCustomvoList) throws Exception {
		List<VipCustomPo> poList=BeanCopy.copyList(vipCustomvoList,VipCustomPo.class,BeanCopy.COPYALL);
		vipCustomMapper.saveVipCustomList(poList);
	}
	public int updateByStudentId(VipCustomVo vipCustomvo,Long studentId) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByStudentId(po,studentId);
	}
	public int updateByMobile(VipCustomVo vipCustomvo,String mobile) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByMobile(po,mobile);
	}
	public int updateByCname(VipCustomVo vipCustomvo,String cname) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByCname(po,cname);
	}
	public int updateByCoid(VipCustomVo vipCustomvo,Integer coid) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByCoid(po,coid);
	}
	public int updateByCid(VipCustomVo vipCustomvo,String cid) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByCid(po,cid);
	}
	public int updateByRcid(VipCustomVo vipCustomvo,Integer rcid) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByRcid(po,rcid);
	}
	public int updateByRcname(VipCustomVo vipCustomvo,String rcname) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByRcname(po,rcname);
	}
	public int updateByCoupon(VipCustomVo vipCustomvo,String coupon) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByCoupon(po,coupon);
	}
	public int updateByCouponLack(VipCustomVo vipCustomvo,String couponLack) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByCouponLack(po,couponLack);
	}
	public int updateByVstate(VipCustomVo vipCustomvo,Integer vstate) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByVstate(po,vstate);
	}
	public int updateByReason(VipCustomVo vipCustomvo,String reason) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByReason(po,reason);
	}
	public int updateByIsdel(VipCustomVo vipCustomvo,Integer isdel) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(VipCustomVo vipCustomvo,Long cuid) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(VipCustomVo vipCustomvo,Long muid) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(VipCustomVo vipCustomvo,Date ctime) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(VipCustomVo vipCustomvo,String mtime) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		return vipCustomMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(VipCustomVo vipCustomvo,VipCustomVo search) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		VipCustomPo searchPo=BeanCopy.copyAll(search,VipCustomPo.class);
		return vipCustomMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(VipCustomVo vipCustomvo,VipCustomVo search) throws Exception {
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		VipCustomPo searchPo=BeanCopy.copyAll(search,VipCustomPo.class);
		return vipCustomMapper.updateAllByObject(po,searchPo);
	}
	public VipCustomVo queryVipCustomById(Long studentId) throws Exception {
		String postSql="";
		String sqlFileld="*";
		VipCustomPo po=vipCustomMapper.queryVipCustomById(studentId,postSql,sqlFileld);
		VipCustomVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,VipCustomVo.class);
		}
		return vo;
	}
	public List<VipCustomVo> queryVipCustomByIds(List<Long> ids,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryVipCustomByIds(ids,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByObjectAnd(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		List<VipCustomPo> poList=vipCustomMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public Integer countByObject(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		Integer total=vipCustomMapper.countByObject(po,postSql,sqlFileld);
		return total;
	}
	public List<VipCustomVo> queryByObjectOr(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		List<VipCustomPo> poList=vipCustomMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByStudentId(Long studentId,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByStudentId(studentId,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByMobile(String mobile,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByMobile(mobile,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByCname(String cname,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByCname(cname,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByCoid(Integer coid,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByCoid(coid,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByCid(String cid,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByCid(cid,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByRcid(Integer rcid,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByRcid(rcid,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByRcname(String rcname,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByRcname(rcname,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByCoupon(String coupon,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByCoupon(coupon,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByCouponLack(String couponLack,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByCouponLack(couponLack,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByVstate(Integer vstate,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByVstate(vstate,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByReason(String reason,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByReason(reason,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByIsdel(Integer isdel,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByCuid(Long cuid,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByMuid(Long muid,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByMuid(muid,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByCtime(Date ctime,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByMtime(String mtime,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCustomPo> poList=vipCustomMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByNew0(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		List<VipCustomPo> poList=vipCustomMapper.queryByNew0(po,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByNew1(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		List<VipCustomPo> poList=vipCustomMapper.queryByNew1(po,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByNew2(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		List<VipCustomPo> poList=vipCustomMapper.queryByNew2(po,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByNew3(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		List<VipCustomPo> poList=vipCustomMapper.queryByNew3(po,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByNew4(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		List<VipCustomPo> poList=vipCustomMapper.queryByNew4(po,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByNew5(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		List<VipCustomPo> poList=vipCustomMapper.queryByNew5(po,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByNew6(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		List<VipCustomPo> poList=vipCustomMapper.queryByNew6(po,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCustomVo> queryByNew7(VipCustomVo vipCustomvo,VipCustomQuery vipCustomQuery) throws Exception {
		String postSql=vipCustomQuery.getSqlPost();
		String sqlFileld=vipCustomQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCustomPo po=BeanCopy.copyAll(vipCustomvo,VipCustomPo.class);
		List<VipCustomPo> poList=vipCustomMapper.queryByNew7(po,postSql,sqlFileld);
		List<VipCustomVo> voList=new ArrayList<VipCustomVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCustomVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
