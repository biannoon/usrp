package com.scrcu.common.utils;

import com.alibaba.druid.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


/**
 * 描述: 日期工具类
 *  hepengfei
 *  2019-08-29
 */

public class DateUtil {

	/**
	 * 获取计算后的日期
	 * @param date
	 * @param day
	 * @return
	 */
	public static String calculateDate(String date,int day){
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String rtdate=null;
		try {
			Date d=format.parse(date);
			calendar.setTime(d);
			calendar.add(Calendar.DATE, day);
			rtdate=format.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rtdate;
	}
	/**
	 * 计算月份
	 * @param date
	 * @param pattern 日期格式
	 * @return
	 */
	public static String calculateMonth(String date,String pattern,int month){
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		String rtdate=null;
		try {
			Date d=format.parse(date);
			calendar.setTime(d);
			calendar.add(Calendar.MONTH, month);
			rtdate=formatDateToString(calendar.getTime(), pattern);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rtdate;
	}
	/**
	 * 根据格式获取当前日期时间
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDate(String pattern){
		String date=null;
		if(pattern==null){
			pattern="yyyyMMdd";
		}
		Calendar cal=Calendar.getInstance();
		date=formatDateToString(cal.getTime(), pattern);
		return date;
	}
	/**
	 * 将日期转换成指定字符串格式
	 * @param date 
	 * @param pattern
	 * @return
	 */
	public static  String formatDateToString(Date date,String pattern){
		String dateStr=null;
		SimpleDateFormat sd=new SimpleDateFormat(pattern);
		dateStr=sd.format(date);
		return dateStr;
	}
	/**
	 * 从日期参数中获取年份
	 * @param date
	 * @return
	 */
	public static  String getYearFromPara(String date){
		if(StringUtils.isEmpty(date)){
			return "传入的参数不能为空";
		}
		String year = date.substring(0,2);
		return year;
	}
	/**
	 * 从日期参数中获取月份
	 * @param date
	 * @return
	 */
	public static  String getMonthFromPara(String date){
		if(StringUtils.isEmpty(date)){
			return "传入的参数不能为空";
		}
		String month = date.substring(4,6);
		return month;
	}
	/**
	 * 从日期参数中获取日
	 * @param date
	 * @return
	 */
	public static  String getDayFromPara(String date){
		if(StringUtils.isEmpty(date)){
			return "传入的参数不能为空";
		}
		String month = date.substring(6,8);
		return month;
	}
	/**
	 * 获取当前日期
	 * @return yyyyMMdd
	 */
	public static  String getNowDate(){
		String dateStr=null;
		SimpleDateFormat sd=new SimpleDateFormat("yyyyMMdd");
		dateStr=sd.format(new Date());
		return dateStr;
	}

	/**
	 * <p>
	 * 以字符串形式获取当前时间
	 * </p>
	 * @param format 时间格式
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/12 23:58
	 **/
	public static String getNowDate(String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.now().format(formatter);
	}
	/**
	 * 功能：判断日期1是否大于日期2
	 * @param  date1 date2
	 * @return date1>date2时返回true
	 * 传入日期格式为yyyyMMdd
	 */
	public boolean isPositive2(String date1,String date2) {
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			Date dateBegin = fmt.parse(date1);
			Date dateEnd = fmt.parse(date2);
			if(dateBegin.after(dateEnd)){
				return true;
			}else{
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
}
