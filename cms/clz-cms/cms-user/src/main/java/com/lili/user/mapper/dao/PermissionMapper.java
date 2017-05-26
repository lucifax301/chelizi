package com.lili.user.mapper.dao;

import java.util.List;

import com.lili.user.model.Permission;
import com.lili.user.model.PermissionBDTO;

public interface PermissionMapper {

    public List<Permission> findInterfaceByUserId(Long userId);
	
    public List<Permission> findMenuByUserId(PermissionBDTO dto);

    public List<Permission> findBtnList(PermissionBDTO dto);

    public List<Permission> findBatch(PermissionBDTO dto);
	
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
    
    
}
