package com.lili.common.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.lili.common.util.ThreadTruck;

public class MultiDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		String key = (String)ThreadTruck.get("route_db");
		return (key!=null)?key:"default";   
	}

}
