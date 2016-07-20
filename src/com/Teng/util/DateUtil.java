package com.Teng.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.Teng.util.DateUtil;

public class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);
	
	public static final String FORMAT_SS="yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date getCurDate() {
		return new Date();
	}
	
	
	/**
	 * 
	 * Description：日期转为字符串
	 * 
	 * @return yyyyMMddHHmmss
	 */
	public static String transDateToStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getCurDateStr(String pattern) {
		String strDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			strDate = format.format(getCurDate());
		} catch (Exception e) {
			log.error("Method getCurDateStr arises the error,parameters ---> pattern="+pattern);
			log.error(e.toString(),e);
		}
		return strDate;
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		String strDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			strDate = format.format(date);
		} catch (Exception e) {
			log.error("Method formatDate arises the error,parameters --->date="+date+"pattern="+pattern);
		}
		return strDate;
	}
	
	/**
	 * 比较开始时间和结束时间
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int compareDate(String beginDate, String endDate) {
		DateFormat df = new SimpleDateFormat(FORMAT_SS);//  "yyyy-MM-dd"
		try {
			Date dateBegin = df.parse(beginDate);
			Date dateEnd = df.parse(endDate);

			if (dateBegin.getTime() > dateEnd.getTime()) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			log.error("Method compareDate arises the error,parameters --->begindate" +beginDate+",enddate="+endDate);
			return -1;
		}
	}
	
	/**
	 * 当前时间加上n天
	 * @param day
	 * @return
	 */
	public static String dateAdd(Date date,Integer day,String pattern){
		Date tomod = new Date(); 
		Calendar calendar = new GregorianCalendar(); 
		calendar.setTime(date);
		calendar.add(calendar.DATE, day);
		tomod = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(tomod);
	}
}
