package com.lili.user.service;

import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.user.model.Permission;
import com.lili.user.model.PermissionBDTO;

public interface CMSPermissionService {
	

    public  List<Permission> getInterfaceByUserId(Long userId);

    public  List<Permission> getMenuByUserId(PermissionBDTO dto);

    public  List<Permission> getBtnList(PermissionBDTO dto);
    
    public  ResponseMessage findBatch(PermissionBDTO dto);
    

    public  ResponseMessage insertOne(Permission record);
    public  ResponseMessage updateOne(Permission record);
    public  ResponseMessage findOne(Integer id);

}
