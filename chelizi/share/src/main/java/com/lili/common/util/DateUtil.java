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
    	return getCalendar(date).get(Calendar.DAY_OF_WEEK)-1;
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
    
    public static void main(String args[]) {
    	Date date=new Date();
    	Calendar cal = Calendar.getInstance();

        // print current time
        System.out.println(" Current time is : " + cal.getTime());


        // create a new calendar
        Calendar cal2 = Calendar.getInstance();
  	  
        // print the next time
        Date d = cal2.getTime();
        System.out.println(" Next time is : " + d);

        
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
    }
}
