package com.lili.share.dao.mapper;

import java.util.Date;
import java.util.List;

import com.lili.common.util.BaseQuery;
import com.lili.share.dao.po.ShareUserPo;

public interface ShareUserMapper{

  public void add(ShareUserPo shareUserPo);
  public void addList(List<ShareUserPo> shareUserPoList);
  public void updateObject(ShareUserPo shareUserPo);
  public void updateList(List<ShareUserPo> shareUserPoList);
  public void delById(String suid);
  public void delListByIds(List<String> ids);
  public void delByObject(ShareUserPo shareUserPo);
  public int updateBySuid(ShareUserPo shareUserPo,String suid);
  public int updateByUserName(ShareUserPo shareUserPo,String userName);
  public int updateBySendTotal(ShareUserPo shareUserPo,Integer sendTotal);
  public int updateByRecevieTemplate(ShareUserPo shareUserPo,String recevieTemplate);
  public int updateBySendPhone(ShareUserPo shareUserPo,String sendPhone);
  public int updateBySendType(ShareUserPo shareUserPo,Integer sendType);
  public int updateByRegType(ShareUserPo shareUserPo,Integer regType);
  public int updateBySendUser(ShareUserPo shareUserPo,Long sendUser);
  public int updateByShareUrl(ShareUserPo shareUserPo,String shareUrl);
  public int updateByDescription(ShareUserPo shareUserPo,String description);
  public int updateByRule(ShareUserPo shareUserPo,String rule);
  public int updateByBigpic(ShareUserPo shareUserPo,String bigpic);
  public int updateBySmallpic(ShareUserPo shareUserPo,String smallpic);
  public int updateByCheckState(ShareUserPo shareUserPo,Integer checkState);
  public int updateByIsdel(ShareUserPo shareUserPo,Integer isdel);
  public int updateByCuid(ShareUserPo shareUserPo,Long cuid);
  public int updateByMuid(ShareUserPo shareUserPo,Long muid);
  public int updateByCtime(ShareUserPo shareUserPo,Date ctime);
  public int updateByMtime(ShareUserPo shareUserPo,String mtime);
  public int updateNotNullByObject(ShareUserPo shareUserPo,ShareUserPo search);
  public int updateAllByObject(ShareUserPo shareUserPo,ShareUserPo search);
  public ShareUserPo queryById(String suid);
  public ShareUserPo queryByUserId(Integer sendType);
  public List<ShareUserPo> queryListByIds(List<String> ids,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByObjectAnd(ShareUserPo shareUserPo,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByObject(ShareUserPo shareUserPo,BaseQuery ShareUserQuery);
  public List<String> queryIdByObject(ShareUserPo shareUserPo,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByUserName(String userName,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryBySendTotal(Integer sendTotal,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByRecevieTemplate(String recevieTemplate,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryBySendPhone(String sendPhone,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryBySendType(Integer sendType,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByRegType(Integer regType,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryBySendUser(Long sendUser,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByShareUrl(String shareUrl,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByDescription(String description,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByRule(String rule,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByBigpic(String bigpic,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryBySmallpic(String smallpic,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByCheckState(Integer checkState,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByIsdel(Integer isdel,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByCuid(Long cuid,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByMuid(Long muid,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByCtime(Date ctime,BaseQuery ShareUserQuery);
  public List<ShareUserPo> queryByMtime(String mtime,BaseQuery ShareUserQuery);
  public String queryMaxPk();
  public ShareUserPo queryByExitPhone(String sendPhone);
}
