package com.lili.user.manager;


import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.user.model.Permission;
import com.lili.user.model.PermissionBDTO;

public interface CMSPermissionManager {

    public  List<Permission> getInterfaceByUserId(Long userId);

    public  List<Permission> getMenuByUserId(PermissionBDTO dto);

    public  List<Permission> getBtnList(PermissionBDTO dto);

    public 	PagedResult<Permission> findBatch(PermissionBDTO dto);
    
    boolean deleteByPrimaryKey(Integer id);

    boolean insertOne(Permission record);

    Permission selectByPrimaryKey(Integer id);

    boolean updateOne(Permission record);

}
