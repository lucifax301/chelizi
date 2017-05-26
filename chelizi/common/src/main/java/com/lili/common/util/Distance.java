package com.lili.common.util;

public class Distance {

	/*
	 * lat1 位置1纬度
	 * lon1 位置1经度
	 * lat2 位置2纬度
	 * lon2 位置2经度
	 */
	public static int getDistatce(double lat1, double lon1, double lat2,    double lon2) {
	  double R = 6371;
	  double distance = 0.0;
	  double dLat = (lat2 - lat1) * Math.PI / 180;
	  double dLon = (lon2 - lon1) * Math.PI / 180;
	  double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
	          + Math.cos(lat1 * Math.PI / 180)
	          * Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon / 2)
	          * Math.sin(dLon / 2);
	  distance = (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))) * R;
	  int num=(int) Math.round(distance);
      return num;
}
	
}
