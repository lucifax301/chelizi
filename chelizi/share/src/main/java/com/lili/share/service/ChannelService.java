package com.lili.share.service;

import java.util.Date;
import java.util.List;

import com.lili.share.vo.ChannelVo;

public interface ChannelService {

  public void add(ChannelVo channelVo)  throws Exception;
  public void addList(List<ChannelVo> channelVoList)  throws Exception;
  public void updateObject(ChannelVo channelVo)  throws Exception;
  public void updateList(List<ChannelVo> channelVoList)  throws Exception;
  public void delById(Integer cid)  throws Exception;
  public void delListByIds(List<Integer> ids)  throws Exception;
  public void save(ChannelVo channelVo)  throws Exception;
  public void saveList(List<ChannelVo> channelVoList)  throws Exception;
  public ChannelVo queryById(Integer cid)  throws Exception;
  public List<ChannelVo> queryListByIds(List<Integer> ids)  throws Exception;
  public List<ChannelVo> queryByObjectAnd(ChannelVo channelVo,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryBySuid(String suid,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryBySendPhone(String sendPhone,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryBySendType(Integer sendType,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryBySendUser(Long sendUser,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryBySendState(Integer sendState,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryBySendTime(Date sendTime,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryBySendTotal(Integer sendTotal,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByRecevieName(String recevieName,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByReceviePhone(String receviePhone,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByRecevieUser(Long recevieUser,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByRecevieState(Integer recevieState,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByRecevieCoupon(Integer recevieCoupon,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByRegName(String regName,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByRegPic(String regPic,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByRegType(Integer regType,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByCheckState(Integer checkState,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByIsdel(Integer isdel,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByCuid(Long cuid,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByMuid(Long muid,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByCtime(Date ctime,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByMtime(String mtime,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public List<ChannelVo> queryByObject(ChannelVo channelVo,Integer pageSize,Integer pageIndex,String orderBy)  throws Exception;
  public ChannelVo reloadById(Integer cid)  throws Exception;
  public List<ChannelVo> reloadListByIds(List<Integer> ids)  throws Exception;
/**自动接口结束*************自动接口结束****************自动接口结束**/

}
