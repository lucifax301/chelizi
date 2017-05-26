package com.lili.file.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.device.manager.DeviceManager;
import com.lili.file.dto.Device;
import com.lili.file.mapper.DeviceMapper;

public class DeviceManagerImpl implements DeviceManager {

	@Autowired
	private DeviceMapper deviceMapper;
	
	@Override
	public List<Device> getDevice(Device device) {
		return deviceMapper.getAll(device);
	}

	@Override
	public Device getDeviceInfo(Integer id) {
		return deviceMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public Device getDeviceByDevice(Device device) {
		return deviceMapper.selectByDevice(device);
	}

	@Override
	public int getCount() {
		return deviceMapper.countAll();
	}

	@Override
	public int addDevice(Device device) {
		return deviceMapper.insertSelective(device);
	}

	@Override
	public int updateDevice(Device device) {
		return deviceMapper.updateByPrimaryKeySelective(device);
	}

	@Override
	public int deleteDevice(Integer id) {
		return deviceMapper.deleteByPrimaryKey(id);
	}

}
