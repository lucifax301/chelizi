package com.lili.share.dao.mapper;

import java.util.Date;
import java.util.List;

import com.lili.common.util.BaseQuery;
import com.lili.share.dao.po.SharePo;

public interface ShareMapper{

  public void add(SharePo sharePo);
  public void addList(List<SharePo> sharePoList);
  public void updateObject(SharePo sharePo);
  public void updateList(List<SharePo> sharePoList);
  public void delById(Integer sid);
  public void delListByIds(List<Integer> ids);
  public void delByObject(SharePo sharePo);
  public int updateBySid(SharePo sharePo,Integer sid);
  public int updateBySuid(SharePo sharePo,String suid);
  public int updateBySendPhone(SharePo sharePo,String sendPhone);
  public int updateBySendType(SharePo sharePo,Integer sendType);
  public int updateBySendUser(SharePo sharePo,Long sendUser);
  public int updateBySendState(SharePo sharePo,Integer sendState);
  public int updateBySendTime(SharePo sharePo,Date sendTime);
  public int updateBySendTotal(SharePo sharePo,Integer sendTotal);
  public int updateByRecevieName(SharePo sharePo,String recevieName);
  public int updateByReceviePhone(SharePo sharePo,String receviePhone);
  public int updateByRecevieUser(SharePo sharePo,Long recevieUser);
  public int updateByRecevieState(SharePo sharePo,Integer recevieState);
  public int updateByRecevieCoupon(SharePo sharePo,Long recevieCoupon);
  public int updateByRegName(SharePo sharePo,String regName);
  public int updateByRegPic(SharePo sharePo,String regPic);
  public int updateByRegType(SharePo sharePo,Integer regType);
  public int updateByCheckState(SharePo sharePo,Integer checkState);
  public int updateByIsdel(SharePo sharePo,Integer isdel);
  public int updateByCuid(SharePo sharePo,Long cuid);
  public int updateByMuid(SharePo sharePo,Long muid);
  public int updateByCtime(SharePo sharePo,Date ctime);
  public int updateByMtime(SharePo sharePo,String mtime);
  public int updateNotNullByObject(SharePo sharePo,SharePo search);
  public int updateAllByObject(SharePo sharePo,SharePo search);
  public SharePo queryById(Integer sid);
  public SharePo queryReceviePhoneIsExit(String sendPhone);
  public Integer queryIsExitShare(String receviePhone, String recevieTemplate);
  public List<SharePo> queryListByIds(List<Integer> ids,BaseQuery ShareQuery);
  public List<SharePo> queryByObjectAnd(SharePo sharePo,BaseQuery ShareQuery);
  public List<SharePo> queryByObject(SharePo sharePo,BaseQuery ShareQuery);
  public List<Integer> queryIdByObject(SharePo sharePo,BaseQuery ShareQuery);
  public List<SharePo> queryBySuid(String suid,BaseQuery ShareQuery);
  public List<SharePo> queryBySendPhone(String sendPhone,BaseQuery ShareQuery);
  public List<SharePo> queryBySendType(Integer sendType,BaseQuery ShareQuery);
  public List<SharePo> queryBySendUser(Long sendUser,BaseQuery ShareQuery);
  public List<SharePo> queryBySendState(Integer sendState,BaseQuery ShareQuery);
  public List<SharePo> queryBySendTime(Date sendTime,BaseQuery ShareQuery);
  public List<SharePo> queryBySendTotal(Integer sendTotal,BaseQuery ShareQuery);
  public List<SharePo> queryByRecevieName(String recevieName,BaseQuery ShareQuery);
  public List<SharePo> queryByReceviePhone(String receviePhone,BaseQuery ShareQuery);
  public List<SharePo> queryByRecevieUser(Long recevieUser,BaseQuery ShareQuery);
  public List<SharePo> queryByRecevieState(Integer recevieState,BaseQuery ShareQuery);
  public List<SharePo> queryByRecevieCoupon(Integer recevieCoupon,BaseQuery ShareQuery);
  public List<SharePo> queryByRegName(String regName,BaseQuery ShareQuery);
  public List<SharePo> queryByRegPic(String regPic,BaseQuery ShareQuery);
  public List<SharePo> queryByRegType(Integer regType,BaseQuery ShareQuery);
  public List<SharePo> queryByCheckState(Integer checkState,BaseQuery ShareQuery);
  public List<SharePo> queryByIsdel(Integer isdel,BaseQuery ShareQuery);
  public List<SharePo> queryByCuid(Long cuid,BaseQuery ShareQuery);
  public List<SharePo> queryByMuid(Long muid,BaseQuery ShareQuery);
  public List<SharePo> queryByCtime(Date ctime,BaseQuery ShareQuery);
  public List<SharePo> queryByMtime(String mtime,BaseQuery ShareQuery);
  public Integer queryMaxPk();
  public Integer queryTotalMoney(SharePo sharePo);
  public Integer queryTotalPerson(SharePo sharePo);
  public Integer queryRealMoney(SharePo sharePo);
  public Integer queryRealRis(SharePo sharePo);
}
