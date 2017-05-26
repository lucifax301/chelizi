package com.lili.user.mapper.dao;

import java.util.HashMap;
import java.util.List;

import com.lili.user.model.Role;
import com.lili.user.model.RoleBDTO;

public interface RoleMapper {
	
	List<Role> findBatch(RoleBDTO dto);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    /**
     * 给用户分配角色
     * @param roleIds 角色id集合
     * @param userId
     * @return
     */
    Integer allotRole(HashMap<String, Object> params);
//    Integer allotRole(List<Long> roleIds,Integer userId);
    
    /**
     * 给角色分配权限
     * @param permissionIds 权限id集合
     * @param roleId
     * @return
     */
    Integer allotPermission(HashMap<String, Object> params);
//    Integer allotPermission(List<Long> permissionIds,Integer roleId);
}
