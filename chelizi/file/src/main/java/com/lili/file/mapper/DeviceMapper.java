/**
 * 
 */
package com.lili.file.mapper;

import java.util.List;

import com.lili.file.dto.Device;

public interface DeviceMapper {

	/**
	 * 根据主键删除终端信息
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增终端信息
     * @param device
     * @return
     */
    int insert(Device device);

    /**
     * 新增终端信息
     * @param device
     * @return
     */
    int insertSelective(Device device);

    /**
     * 根据id获取终端信息
     * @param id
     * @return
     */
    Device selectByPrimaryKey(Integer id);
    
    /**
     * 根据设备信息查找最新的设备数据
     * @param device
     * @return
     */
    Device selectByDevice(Device device);

    /**
     * 修改终端信息
     * @param device
     * @return
     */
    int updateByPrimaryKeySelective(Device device);

    /**
     * 修改终端信息
     * @param device
     * @return
     */
    int updateByPrimaryKey(Device device);
    
    /**
     * 获取终端列表
     * @param device
     * @return
     */
    List<Device> getAll(Device device);
    
    /**
     * 获取公告数量
     * @return
     */
    int countAll();
    
}
