package com.lili.student.service.impl;

import java.util.List;
import java.util.Date;

import com.lili.common.util.BeanCopy;
import com.lili.student.vo.VipCompanyQuery;
import com.lili.student.vo.VipCompanyVo;
import com.lili.student.dao.mapper.VipCompanyMapper;
import com.lili.student.dao.po.VipCompanyPo;
import com.lili.student.service.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class VipCompanyServiceImpl implements VipCompanyService {

	@Autowired
	VipCompanyMapper vipCompanyMapper;;
	public void addVipCompany(VipCompanyVo vipCompanyvo) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		vipCompanyMapper.addVipCompany(po);
	}
	public void addVipCompanyList(List<VipCompanyVo> vipCompanyvoList) throws Exception {
		List<VipCompanyPo> poList=BeanCopy.copyList(vipCompanyvoList,VipCompanyPo.class,BeanCopy.COPYALL);
		vipCompanyMapper.addVipCompanyList(poList);
	}
	public void delVipCompanyById(Integer coid) throws Exception {
		vipCompanyMapper.delVipCompanyById(coid);
	}
	public void delVipCompanyByIds(List<Integer> ids) throws Exception {
		vipCompanyMapper.delVipCompanyByIds(ids);
	}
	public void delVipCompanyByObj(VipCompanyVo vipCompanyvo) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		vipCompanyMapper.delVipCompanyByObj(po);
	}
	public void saveVipCompany(VipCompanyVo vipCompanyvo) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		vipCompanyMapper.saveVipCompany(po);
	}
	public void saveVipCompanyList(List<VipCompanyVo> vipCompanyvoList) throws Exception {
		List<VipCompanyPo> poList=BeanCopy.copyList(vipCompanyvoList,VipCompanyPo.class,BeanCopy.COPYALL);
		vipCompanyMapper.saveVipCompanyList(poList);
	}
	public int updateByCoid(VipCompanyVo vipCompanyvo,Integer coid) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByCoid(po,coid);
	}
	public int updateByCompany(VipCompanyVo vipCompanyvo,String company) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByCompany(po,company);
	}
	public int updateByVtype(VipCompanyVo vipCompanyvo,Integer vtype) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByVtype(po,vtype);
	}
	public int updateByShorter(VipCompanyVo vipCompanyvo,String shorter) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByShorter(po,shorter);
	}
	public int updateByProvinceId(VipCompanyVo vipCompanyvo,Integer provinceId) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByProvinceId(po,provinceId);
	}
	public int updateByCityId(VipCompanyVo vipCompanyvo,Integer cityId) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByCityId(po,cityId);
	}
	public int updateByProvince(VipCompanyVo vipCompanyvo,String province) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByProvince(po,province);
	}
	public int updateByCity(VipCompanyVo vipCompanyvo,String city) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByCity(po,city);
	}
	public int updateByManger(VipCompanyVo vipCompanyvo,String manger) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByManger(po,manger);
	}
	public int updateByMobile(VipCompanyVo vipCompanyvo,String mobile) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByMobile(po,mobile);
	}
	public int updateByPhone(VipCompanyVo vipCompanyvo,String phone) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByPhone(po,phone);
	}
	public int updateByEmail(VipCompanyVo vipCompanyvo,String email) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByEmail(po,email);
	}
	public int updateByRcid(VipCompanyVo vipCompanyvo,String rcid) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByRcid(po,rcid);
	}
	public int updateByRemark(VipCompanyVo vipCompanyvo,String remark) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByRemark(po,remark);
	}
	public int updateByActive(VipCompanyVo vipCompanyvo,Integer active) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByActive(po,active);
	}
	public int updateByAgreement(VipCompanyVo vipCompanyvo,String agreement) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByAgreement(po,agreement);
	}
	public int updateByVstate(VipCompanyVo vipCompanyvo,Integer vstate) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByVstate(po,vstate);
	}
	public int updateByReason(VipCompanyVo vipCompanyvo,String reason) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByReason(po,reason);
	}
	public int updateByIsdel(VipCompanyVo vipCompanyvo,Integer isdel) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByIsdel(po,isdel);
	}
	public int updateByCuid(VipCompanyVo vipCompanyvo,Long cuid) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByCuid(po,cuid);
	}
	public int updateByMuid(VipCompanyVo vipCompanyvo,Long muid) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByMuid(po,muid);
	}
	public int updateByCtime(VipCompanyVo vipCompanyvo,Date ctime) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByCtime(po,ctime);
	}
	public int updateByMtime(VipCompanyVo vipCompanyvo,String mtime) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		return vipCompanyMapper.updateByMtime(po,mtime);
	}
	public int updateNotNullByObject(VipCompanyVo vipCompanyvo,VipCompanyVo search) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		VipCompanyPo searchPo=BeanCopy.copyAll(search,VipCompanyPo.class);
		return vipCompanyMapper.updateNotNullByObject(po,searchPo);
	}
	public int updateAllByObject(VipCompanyVo vipCompanyvo,VipCompanyVo search) throws Exception {
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		VipCompanyPo searchPo=BeanCopy.copyAll(search,VipCompanyPo.class);
		return vipCompanyMapper.updateAllByObject(po,searchPo);
	}
	public VipCompanyVo queryVipCompanyById(Integer coid) throws Exception {
		String postSql="";
		String sqlFileld="*";
		VipCompanyPo po=vipCompanyMapper.queryVipCompanyById(coid,postSql,sqlFileld);
		VipCompanyVo vo=null;
		if(po!=null){
			vo=BeanCopy.copyAll(po,VipCompanyVo.class);
		}
		return vo;
	}
	public List<VipCompanyVo> queryVipCompanyByIds(List<Integer> ids,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryVipCompanyByIds(ids,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByObjectAnd(VipCompanyVo vipCompanyvo,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		List<VipCompanyPo> poList=vipCompanyMapper.queryByObjectAnd(po,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public Integer countByObject(VipCompanyVo vipCompanyvo,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		Integer total=vipCompanyMapper.countByObject(po,postSql,sqlFileld);
		return total;
	}
	public List<VipCompanyVo> queryByObjectOr(VipCompanyVo vipCompanyvo,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		List<VipCompanyPo> poList=vipCompanyMapper.queryByObjectOr(po,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByCoid(Integer coid,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByCoid(coid,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByCompany(String company,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByCompany(company,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByVtype(Integer vtype,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByVtype(vtype,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByShorter(String shorter,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByShorter(shorter,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByProvinceId(Integer provinceId,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByProvinceId(provinceId,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByCityId(Integer cityId,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByCityId(cityId,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByProvince(String province,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByProvince(province,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByCity(String city,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByCity(city,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByManger(String manger,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByManger(manger,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByMobile(String mobile,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByMobile(mobile,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByPhone(String phone,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByPhone(phone,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByEmail(String email,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByEmail(email,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByRcid(String rcid,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByRcid(rcid,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByRemark(String remark,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByRemark(remark,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByActive(Integer active,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByActive(active,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByAgreement(String agreement,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByAgreement(agreement,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByVstate(Integer vstate,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByVstate(vstate,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByReason(String reason,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByReason(reason,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByIsdel(Integer isdel,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByIsdel(isdel,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByCuid(Long cuid,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByCuid(cuid,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByMuid(Long muid,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByMuid(muid,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByCtime(Date ctime,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByCtime(ctime,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByMtime(String mtime,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		List<VipCompanyPo> poList=vipCompanyMapper.queryByMtime(mtime,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByNew0(VipCompanyVo vipCompanyvo,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		List<VipCompanyPo> poList=vipCompanyMapper.queryByNew0(po,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByNew1(VipCompanyVo vipCompanyvo,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		List<VipCompanyPo> poList=vipCompanyMapper.queryByNew1(po,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByNew2(VipCompanyVo vipCompanyvo,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		List<VipCompanyPo> poList=vipCompanyMapper.queryByNew2(po,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByNew3(VipCompanyVo vipCompanyvo,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		List<VipCompanyPo> poList=vipCompanyMapper.queryByNew3(po,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByNew4(VipCompanyVo vipCompanyvo,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		List<VipCompanyPo> poList=vipCompanyMapper.queryByNew4(po,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByNew5(VipCompanyVo vipCompanyvo,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		List<VipCompanyPo> poList=vipCompanyMapper.queryByNew5(po,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByNew6(VipCompanyVo vipCompanyvo,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		List<VipCompanyPo> poList=vipCompanyMapper.queryByNew6(po,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}
	public List<VipCompanyVo> queryByNew7(VipCompanyVo vipCompanyvo,VipCompanyQuery vipCompanyQuery) throws Exception {
		String postSql=vipCompanyQuery.getSqlPost();
		String sqlFileld=vipCompanyQuery.getSqlField();
		if(postSql==null) {
			postSql="";
		}
		VipCompanyPo po=BeanCopy.copyAll(vipCompanyvo,VipCompanyPo.class);
		List<VipCompanyPo> poList=vipCompanyMapper.queryByNew7(po,postSql,sqlFileld);
		List<VipCompanyVo> voList=new ArrayList<VipCompanyVo>();
		if(poList!=null && !poList.isEmpty()){
			voList=BeanCopy.copyList(poList,VipCompanyVo.class,BeanCopy.COPYALL);
		}
		return voList;
	}

}
