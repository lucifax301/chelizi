package com.lili.device.manager;

import java.util.List;

import com.lili.file.dto.Device;

public interface DeviceManager {

	/**
	 * 获取驾校列表
	 * @param device
	 * @return
	 */
	public List<Device> getDevice(Device device);

	/**
	 * 根据id获取终端信息
	 * @param id
	 * @return
	 */
	public Device getDeviceInfo(Integer id);
	
    /**
     * 根据设备信息查找最新的设备数据
     * @param device
     * @return
     */
    public Device getDeviceByDevice(Device device);

	/**
	 * 获取终端信息数量
	 * @return
	 */
	public int getCount();

	/**
	 * 新增终端信息
	 * @param device
	 * @return
	 */
	public int addDevice(Device device);

	/**
	 * 更新终端信息
	 * @param device
	 * @return
	 */
	public int updateDevice(Device device);

	/**
	 * 根据id删除终端信息
	 * @param id
	 * @return
	 */
	public int deleteDevice(Integer id);
}
