package com.lili.share.service;

import java.util.Date;
import java.util.List;

import com.lili.share.vo.ShareUserVo;

public interface ShareUserService {

  public void add(ShareUserVo shareUserVo)  throws Exception;
  public void addList(List<ShareUserVo> shareUserVoList)  throws Exception;
  public void updateObject(ShareUserVo shareUserVo)  throws Exception;
  public void updateList(List<ShareUserVo> shareUserVoList)  throws Exception;
  public void delById(String suid)  throws Exception;
  public void delListByIds(List<String> ids)  throws Exception;
  public void save(ShareUserVo shareUserVo)  throws Exception;
  public void saveList(List<ShareUserVo> shareUserVoList)  throws Exception;
  public ShareUserVo queryById(String suid)  throws Exception;
  public List<ShareUserVo> queryListByIds(List<String> ids)  throws Exception;
  public List<ShareUserVo> queryByObjectAnd(ShareUserVo shareUserVo,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByUserName(String userName,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryBySendTotal(Integer sendTotal,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByRecevieTemplate(String recevieTemplate,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryBySendPhone(String sendPhone,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryBySendType(Integer sendType,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByRegType(Integer regType,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryBySendUser(Long sendUser,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByShareUrl(String shareUrl,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByDescription(String description,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByRule(String rule,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByBigpic(String bigpic,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryBySmallpic(String smallpic,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByCheckState(Integer checkState,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByIsdel(Integer isdel,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByCuid(Long cuid,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByMuid(Long muid,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByCtime(Date ctime,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByMtime(String mtime,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ShareUserVo> queryByObject(ShareUserVo shareUserVo,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public ShareUserVo reloadById(String suid)  throws Exception;
  public List<ShareUserVo> reloadListByIds(List<String> ids)  throws Exception;
/**自动接口结束*************自动接口结束****************自动接口结束**/

}
