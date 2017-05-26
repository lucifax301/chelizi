package com.lili.share.service;

import java.util.Date;
import java.util.List;

import com.lili.share.vo.ShareVo;

public interface ShareService {

  public void add(ShareVo shareVo)  throws Exception;
  public void addList(List<ShareVo> shareVoList)  throws Exception;
  public void updateObject(ShareVo shareVo)  throws Exception;
  public void updateList(List<ShareVo> shareVoList)  throws Exception;
  public void delById(Integer sid)  throws Exception;
  public void delListByIds(List<Integer> ids)  throws Exception;
  public void save(ShareVo shareVo)  throws Exception;
  public void saveList(List<ShareVo> shareVoList)  throws Exception;
  public ShareVo queryById(Integer sid)  throws Exception;
  public List<ShareVo> queryListByIds(List<Integer> ids)  throws Exception;
  public List<ShareVo> queryByObjectAnd(ShareVo shareVo,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryBySuid(String suid,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryBySendPhone(String sendPhone,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryBySendType(Integer sendType,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryBySendUser(Long sendUser,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryBySendState(Integer sendState,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryBySendTime(Date sendTime,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryBySendTotal(Integer sendTotal,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByRecevieName(String recevieName,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByReceviePhone(String receviePhone,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByRecevieUser(Long recevieUser,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByRecevieState(Integer recevieState,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByRecevieCoupon(Integer recevieCoupon,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByRegName(String regName,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByRegPic(String regPic,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByRegType(Integer regType,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByCheckState(Integer checkState,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByIsdel(Integer isdel,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByCuid(Long cuid,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByMuid(Long muid,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByCtime(Date ctime,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByMtime(String mtime,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareVo> queryByObject(ShareVo shareVo,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public ShareVo reloadById(Integer sid)  throws Exception;
  public List<ShareVo> reloadListByIds(List<Integer> ids)  throws Exception;
/**自动接口结束*************自动接口结束****************自动接口结束**/

}
