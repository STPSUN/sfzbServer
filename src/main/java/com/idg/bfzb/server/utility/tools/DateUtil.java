package com.idg.bfzb.server.utility.tools;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目名称：账号安全中心(all)   
 * 类名称：DateUtil   
 * 类描述：   时间操作工具
 * 创建人：linwu   
 * 创建时间：2014-12-17 上午10:43:08       
 * @version
 */
public class DateUtil {

	/**
	 * 生成ISO-8601 规范的时间格式
	 * @param date
	 * @return
	 */
	public static String formatISO8601DateString(Date date){
		String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
		return DateFormatUtils.format(date, pattern);
	}
	
	
	/**
	 * 获取反时间戳
	 * @param date
	 * @return
	 */
	public static Long getCurrentReverseTime(){
		long longTime = System.currentTimeMillis()*1000000 + CalculateUtil.getNext(999999);
		return Long.MAX_VALUE - longTime;
	}
	
	/**
	 * 获取原时间戳
	 * @param date
	 * @return
	 */
	public static Long recoverReverseTime(Long reverseTime){
		long longTime = Long.MAX_VALUE - reverseTime;
		return longTime/1000000;
	}

	/**
	 * 生成页面普通展示时间
	 * @param date
	 * @return
	 */
	public static String formatNormalDateString(Date date){
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return DateFormatUtils.format(date, pattern);
	}
	
	/**
	 * 通过时间字符串获取timstamp对象
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Timestamp getTimestampByDateString(String dateStr, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		
		try {
			Date date = sdf.parse(dateStr);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 生成页面指定pattern展示时间
	 * @param timestamp
	 * @param pattern
	 * @return
	 */
	public static String formatTimestampDateString(Timestamp timestamp, String pattern){
		Date date = new Date(timestamp.getTime());
		return DateFormatUtils.format(date, pattern);
	}
	
	/**
     * 取得格式化后的日期
     * @param date 日期
     * @param pattern 格式
     * @return
     */
    public static String getFormatedDate(Date date,String pattern) {
    	SimpleDateFormat format = new SimpleDateFormat(pattern);
    	return format.format(date);
    }	
	
}
