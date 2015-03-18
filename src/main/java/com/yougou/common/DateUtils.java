package com.yougou.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 结构说明：时间操作相关类
 * 创建时间：2014-08-25 yinyunfei
 */
public class DateUtils {

	/**
	 * 根据当前日期及增加天数得到相应日期
	 * 
	 * @param nowDay
	 *            指定的初始日期,格式为: 2010-07-10
	 * @param addDays
	 *            需要增加的天数,如果为负数则是减少的天数
	 * @return
	 * @throws Exception
	 */
	public static String addDay(String nowDay, int addDays) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cd = Calendar.getInstance();
		try {
			cd.setTime(sdf.parse(nowDay));
			cd.add(Calendar.DATE, addDays);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdf.format(cd.getTime());
	}
	
	/**
	 * 根据当前日期及增加天数得到相应日期
	 * 
	 * @param nowDay
	 *            指定的初始日期,格式为: 100710
	 * @param addDays
	 *            需要增加的天数,如果为负数则是减少的天数
	 * @return
	 * @throws Exception
	 */
	public static String addDay2(String nowDay, int addDays) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		Calendar cd = Calendar.getInstance();
		try {
			cd.setTime(sdf.parse(nowDay));
			cd.add(Calendar.DATE, addDays);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdf.format(cd.getTime());
	}
	
	
	public static String addSmallDay(String nowDay, int addDays) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-m-d");
		Calendar cd = Calendar.getInstance();
		try {
			cd.setTime(sdf.parse(nowDay));
			cd.add(Calendar.DATE, addDays);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdf.format(cd.getTime());
	}
	
	/**
	 * 方法说明： 取得相隔多少天的时间  例如 当输入2010-09-01和30时，输出的时间为2010-10-01
	 * 创建时间： 2010-09-15 YYF
	 * @param date  输入的时间
	 * @param dayMark 时间间隔 如果是正数往后查，如果是负数是往前查
	 * @return
	 * @throws Exception
	 */
	public static String getOtherDay(String date,int dayMark){
		String strStart = "";
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dt = df.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			c.add(Calendar.DAY_OF_MONTH, dayMark);
			String mDateTime = df.format(c.getTime());
			strStart = mDateTime.substring(0, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strStart;
   }

	/**
	 * 得到当前日期yyyy-MM-dd；
	 * 
	 * @return String
	 */
	public static String getSystemDate() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mDateTime1 = formatter.format(date);
		return mDateTime1;
	}
	
	/**
	 * 得到当前日期yyMMdd；
	 * 
	 * @return String
	 */
	public static String getSystemDate2() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
		String mDateTime1 = formatter.format(date);
		return mDateTime1;
	}

	/**
	 * 得到当前日期和时间yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getSystemDateAndTime() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mDateTime1 = formatter.format(date);
		return mDateTime1;
	}
	
	/**
	 * 得到当前日期和时间yyyyMMddHHmmss
	 * 
	 * @return String
	 */
	public static String getSystemDateAndTime2() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String mDateTime1 = formatter.format(date);
		return mDateTime1;
	}

	/**
	 * 方法说明：获取当前系统的时间 年-月-日 小时：分钟
	 * 创建时间：2010-12-18 YYF
	 * @return String
	 */
	public static String getSystemDateAndHourMinute() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String mDateTime1 = formatter.format(date);
		return mDateTime1;
	}
	
	/**
	 * 得到当前系统时间HH:mm:ss；
	 * 
	 * @return String
	 */
	public static String getSystemTime() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String mDateTime1 = formatter.format(date);
		return mDateTime1;
	}
	
	
	/**
	 * 获取两个日期之间的天数
	 * sDate -- 起始日期，eDate -- 结束日期
	 * @return int--天数
	 * @throws Exception
	 * */
	public static int subtractDate(String sDate, String eDate){
		   DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd") ;
		   DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd") ;
		   Date date1 =null;
		   Date date2 =null;
		try {
			date1 = df1.parse(sDate);
			date2 = df2.parse(eDate);
		} catch (Exception e) {
			// TODO: handle exception
		}
	       if (null == date1 || null == date2) {
	           return -1;
	       }
	       long intervalMilli = date2.getTime() - date1.getTime();
	       return (int)(intervalMilli / (24 * 60 * 60 * 1000));
	 }
	
	 /**   
	  * 方法说明：取得本月的第一天
	  * 创建时间：2010-09-27 YYF
	  * @return   
	  */    
	 public static String getMonthFirstDay() {     
		 
		 Calendar calendar = new GregorianCalendar();
		 calendar.set(Calendar.DATE, 1 );
		 SimpleDateFormat simpleFormate = new SimpleDateFormat( "yyyy-MM-dd" );
	     
	     return simpleFormate.format(calendar.getTime());     
	 }     
	     
	 /**   
	  * 方法说明：取得本月的最后一天
	  * 创建时间：2010-09-27 YYF
	  * @return   
	  */  
	 public static String getMonthLastDay() {     

		 Calendar calendar = new GregorianCalendar();
		 calendar.set(Calendar.DATE, 1 );
		 calendar.roll(Calendar.DATE, - 1 );
		 SimpleDateFormat simpleFormate = new SimpleDateFormat( "yyyy-MM-dd" );
		 
	     return simpleFormate.format(calendar.getTime());     
	 }  
	 
	 /**
	  * 验证是否是日期格式
	  * @param dateStr
	  * @return
	  */
	 public static boolean verifyDateFormat(String dateStr){
		 boolean flag = false;
		 if(dateStr==null){
			 dateStr = "";
		 }
		 String regexStr= "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]? ((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12356789])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";   
		 Pattern p = Pattern.compile(regexStr);   
		 Matcher m = p.matcher(dateStr);   
		 flag = m.matches();   
		 return flag;
	 }
	 
	 /**
	  * 方法说明：判断某一天是否是周未
	  * 创建时间：2010-11-24 14：55 YYF
	  *         返回true是周未，否则不是周未
	  * @param dateString
	  */
     public static boolean getIfIsWeekEndByDate(String dateString) {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date;
		
		try {
			date = df.parse(dateString);
			// df.parse()返回的是一个Date类
			calendar.setTime(date);
			if (calendar.get(Calendar.DAY_OF_WEEK) == 1
					|| calendar.get(Calendar.DAY_OF_WEEK) == 7) {
				return true;
			} else {
				return false;
			}
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return false;
     }  
     
	 /**
	  * 方法说明：通过日期判断是一周的哪一天
	  * 创建时间：2010-11-24 14：55 YYF
	  */
     public static int getWeekDayByDate(String dateString) {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date;
		
		try {
			date = df.parse(dateString);
			// df.parse()返回的是一个Date类
			calendar.setTime(date);
			return calendar.get(Calendar.DAY_OF_WEEK);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return 0;
     }  
     
	 /**
	  * 方法说明：判断某一天是是星期几
	  * 创建时间：2010-11-24 14：55 YYF
	  *         返回true是周未，否则不是周未
	  * @param dateString
	  */
     public static int getWeeyTypeByDate(String dateString) {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date;
		
		try {
			date = df.parse(dateString);
			// df.parse()返回的是一个Date类
			calendar.setTime(date);
				return calendar.get(Calendar.DAY_OF_WEEK);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return 0;
		}
     } 
     
  	/**
  	 * 用途说明：取得系统时间昨天-=-天
  	 * 创建时间：2010-12-06 YYF
  	 * @return String
  	 */
  	public static String getYesDay(String nowDate) {
  		Date date;
  		String mDateTime1="";
  		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  		try {
			date=df.parse(nowDate);
	  		SimpleDateFormat formatter = new SimpleDateFormat("dd");
	  		mDateTime1 = formatter.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

  		return mDateTime1;
  	}
     
 	/**
 	 * 用途说明：取得系统时间-=-天
 	 * 创建时间：2010-12-06 YYF
 	 * @return String
 	 */
 	public static String getNowDay() {
 		java.util.Date date = new java.util.Date();
 		SimpleDateFormat formatter = new SimpleDateFormat("dd");
 		String mDateTime1 = formatter.format(date);
 		return mDateTime1;
 	}
 	
 	/**
 	 * 用途说明：取得系统时间-=-月
 	 * 创建时间：2010-12-06 YYF
 	 * @return String
 	 */
 	public static String getNowMonth() {
 		java.util.Date date = new java.util.Date();
 		SimpleDateFormat formatter = new SimpleDateFormat("MM");
 		String mDateTime1 = formatter.format(date);
 		return mDateTime1;
 	}
 	
	 /**
	  * 方法说明：比较两个时间的相差分钟数
	  * 创建时间：2010-12-18 YYF 
	  */
	public static double getTimeDiff(String startDate,String endDate){
		
		Date d1 = null;
		Date d2 = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		try {
			d1 = sdf.parse(startDate);
			d2 = sdf.parse(endDate);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		long dd1 = d1.getTime();
		long dd2 = d2.getTime();
		double minute = (double) (dd2 - dd1) / 3600 / 1000 * 60;

		return minute;
	}
     
	public static String getPreProductEndDateByStartDate(String startDate){
		
		String endDate=null;
		
		if(startDate!=null){
			int i=0;
			endDate=startDate;
			while(true){
				String tempDate=DateUtils.getOtherDay(endDate, 1);
				boolean bRet=DateUtils.getIfIsWeekEndByDate(tempDate);
				
				if(!bRet){
					i++;
				}
				
				endDate=tempDate;
				
				if(i>2){
					break;
				}
				
			}
		}
		
		return endDate;
	}
	
	/**
	 * 得到当前系统小时 h
	 * 
	 * @return String
	 */
	public static String getSystemHour() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("H");
		String mDateTime1 = formatter.format(date);
		return mDateTime1;
	}
	
	/**
	 * 得到当前系统分钟 s
	 * 
	 * @return String
	 */
	public static String getSystemMinute() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("mm");
		String mDateTime1 = formatter.format(date);
		return mDateTime1;
	}
	
	/**
	 * 方法说明：把时间timestamp（1328712865157）按规格转换
	 * 创建时间：2012-02-08YYF
	 * @param timestamp
	 * @return
	 */
	public static String getTimeByTimestamp(String timestamp){
		
		if(timestamp!=null){
			
			Timestamp now = new Timestamp(Long.parseLong(timestamp));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String str = df.format(now);   
			
		    return str;
		}
		
		return null;
	}
	
	/**
	 * 方法说明：把时间timestamp（1328712865157）按规格转换
	 * 创建时间：2012-02-08YYF
	 * @param timestamp
	 * @return
	 */
	public static String getTimeByTimestamp(String timestamp,int iType){
		
		if(timestamp!=null){
			
			Timestamp now = new Timestamp(Long.parseLong(timestamp));
			SimpleDateFormat df = new SimpleDateFormat("MM月dd HH点mm分"); 
			String str = df.format(now);   
			
		    return str;
		}
		
		return null;
	}
	
	/**
	 * 取得两个日期的时间间隔 秒数
	 * sDate -- 起始日期，eDate -- 结束日期
	 * @return int--天数
	 * @throws Exception
	 * */
	public static long getTwoDateMilli(String sDate, String eDate){
		   DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd") ;
		   DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd") ;
		   Date date1 =null;
		   Date date2 =null;
		try {
			date1 = df1.parse(sDate);
			date2 = df2.parse(eDate);
		} catch (Exception e) {
			// TODO: handle exception
		}
	       if (null == date1 || null == date2) {
	           return -1;
	       }
	       long intervalMilli = date2.getTime() - date1.getTime();
	       return intervalMilli;
	 }
	
	public static void main(String[] args){
		
// for(int i=0; i<10000; i++){
// System.out.println(DateUtils.getOtherDay("2010-11-07", 31));
// }
		
		//testMenth("2010-11-11");
		// System.out.println(DateUtils.subtractDate("2010-09-13",
		// "2010-09-11"));Calendar.DAY_OF_WEEK
//		String str = "2011-03-08";
//		str = DateUtils.addSmallDay(str, 0);
		
//		System.out.println(getTimeDiff("2011-11-06 00:00:00","2011-12-05 00:00:00"));
	}
	
}
