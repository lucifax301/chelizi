package com.lili.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil
{
	public static int getYear(Date date) {
		return getCalendar(date).get(Calendar.YEAR);
    }
	public static int getMonth(Date date)
    {
    	return getCalendar(date).get(Calendar.MONTH)+1;
    }
	public static int getWeek(Date date)
    {
		Calendar c=getCalendar(date);
    	int weekDay=c.get(Calendar.DAY_OF_WEEK);
    	boolean isFirstSunday = (c.getFirstDayOfWeek() == Calendar.SUNDAY); 
    	if(isFirstSunday){  
    	    weekDay = weekDay - 1;  
    	    if(weekDay == 0){  
    	        weekDay = 7;  
    	    }  
    	}
    	return weekDay;
    }
    public static int getDay(Date date)
    {
        return getCalendar(date).get(Calendar.DATE);
    }
    public static int getHour(Date date)
    {
    	return getCalendar(date).get(Calendar.HOUR_OF_DAY);
    }
    public static java.util.Calendar getCalendar(Date date)
    {
    	if (date==null) {
    		date=new Date();
    	}
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }
    public static Date dateAfterMinute(Date date,int minute){
    	if (date==null) {
    		date=new Date();
    	}
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
    	return calendar.getTime();
    }
    public static Date dateAfterMilliSecond(Date date,int millisecond){
    	if (date==null) {
    		date=new Date();
    	}
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, millisecond);
    	return calendar.getTime();
    }
    public static Date getExactHour(Date date){
    	if (date==null) {
    		date=new Date();
    	}
    	SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ctime = formatter.format(date).substring(0,13)+":00:00";
    	try {
			return formatter.parse(ctime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    //取当前日期一周的开始日期
    public static String getStartDate(){
        int mondayPlus;
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
        	mondayPlus =  -6;
        } else {
        	mondayPlus =  2 - dayOfWeek;
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        return  formatter.format(monday);
    }
    
    //取当前日期一周的结束日期
    public static String getEndDate(){
    	int mondayPlus;
    	Calendar cd = Calendar.getInstance();
    	int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
    	if (dayOfWeek == 1) {
    		mondayPlus =  -6;
    	} else {
    		mondayPlus =  2 - dayOfWeek;
    	}
    	GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus +6);
        Date monday = currentDate.getTime();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
    	return  formatter.format(monday);
    }
    
    public  static String getDateRandom(){
    	Date date = new Date(); 
    	int number=(int)(Math.random()*1000000);
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
    	return dateFormat.format(date) + number;
    }
    public static Date getDateStart(Date date){
    	Calendar gdate = Calendar.getInstance(); 
		gdate.setTime(date);
        gdate.set(Calendar.HOUR_OF_DAY, 0);  
        gdate.set(Calendar.MINUTE, 0);  
        gdate.set(Calendar.SECOND, 0);  
        gdate.set(Calendar.MILLISECOND, 0);  
        return gdate.getTime();
    }
    
    public static Date getDateEnd(Date date){
    	Calendar gdate = Calendar.getInstance(); 
		gdate.setTime(date);
        gdate.set(Calendar.HOUR_OF_DAY, 23);  
        gdate.set(Calendar.MINUTE, 59);  
        gdate.set(Calendar.SECOND, 59);  
        gdate.set(Calendar.MILLISECOND, 999);  
        return gdate.getTime();
    }
    public static long getTimeInMillis(Date date){
    	Calendar gdate = Calendar.getInstance(); 
		gdate.setTime(date);
        return gdate.getTime().getTime();
    }
    
    public static boolean isWeek(String date){
    	  SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");//日期格式化辅助类
		  Date d;
		  try {
		   d = df.parse(date);//格式化日期
		   if (d.getDay() == 0 || d.getDay() == 6){//判断是不是双休日
			   return true;
		   }
		   else{
			   return false;
		   }
		  } catch (ParseException e) {
			  e.printStackTrace();
			  return false;
		  }
    }
    
    public static Date getNextDay(Date date){
    	Calendar gdate = Calendar.getInstance(); 
    	gdate.setTime(date);
    	gdate.add(gdate.DATE, 1);
    	return gdate.getTime();
    }
    
    /**
     * 判断两个日期是否是同一天
     * 
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear  && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth  && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }
    
    /**
     * 当前时间+10年后的时间
     * @param date
     * @return
     */
    public static Date getNextTenYear(Date date) {
    	 Calendar c = Calendar.getInstance();
    	 c.setTime(date);
	     c.add(Calendar.YEAR, 10);
		return c.getTime();
    }
    
    public static void main(String args[]) {
    	Date test=TimeUtil.parseDate("2016-07-23 21:21:21", "yyyy-MM-dd hh:mm:ss");
    	System.out.println(DateUtil.getWeek(test));
    	test=TimeUtil.parseDate("2016-07-24 21:21:21", "yyyy-MM-dd hh:mm:ss");
    	System.out.println(DateUtil.getWeek(test));
    	test=TimeUtil.parseDate("2016-07-25 21:21:21", "yyyy-MM-dd hh:mm:ss");
    	System.out.println(DateUtil.getWeek(test));
    	test=TimeUtil.parseDate("2016-07-26 21:21:21", "yyyy-MM-dd hh:mm:ss");
    	System.out.println(DateUtil.getWeek(test));
    	test=TimeUtil.parseDate("2016-07-27 21:21:21", "yyyy-MM-dd hh:mm:ss");
    	System.out.println(DateUtil.getWeek(test));
    	test=TimeUtil.parseDate("2016-07-28 21:21:21", "yyyy-MM-dd hh:mm:ss");
    	System.out.println(DateUtil.getWeek(test));
    	test=TimeUtil.parseDate("2016-07-29 21:21:21", "yyyy-MM-dd hh:mm:ss");
    	System.out.println(DateUtil.getWeek(test));
    	
    	Date date=new Date();
    	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ctime = formatter.format(date);
        System.out.println(ctime);
    	System.out.println(formatter.format(getExactHour(date)));
    	System.out.println(getYear(date));
    	System.out.println(getMonth(date));
    	System.out.println(getDay(date));
    	System.out.println(getHour(date));
    	System.out.println(getWeek(date));
    	System.out.println(getStartDate()+" 00:00:00");
    	System.out.println(getEndDate()+" 23:59:59");
    	System.out.println(getDateRandom());
    	
    	System.out.println("-----------------------");
    	Date today = new Date();
    	Date tomorrow = getNextDay(today);
    	System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(today));
    	System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tomorrow));
    	System.out.println(isSameDate(today, tomorrow));
    	
    	System.out.println(DateUtil.getNextTenYear(test));
    	
    }
}
