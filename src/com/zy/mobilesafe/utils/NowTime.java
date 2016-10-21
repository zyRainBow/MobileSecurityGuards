package com.zy.mobilesafe.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class NowTime {

	/**
	 * 获取中文时间
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDateTimeCN() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;// 2012年10月03日 23:41:31
		
	}
	
	/**
	 * 获取英文格式时间
	 * @return
	 */
	public static String getDateTimeEN() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;// 2012-10-03 23:41:31
	}
	
	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getDateEN() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;// 2012-10-03 23:41:31
	}
	
	/**
	 * 长整型时间转换为字符串型
	 * @param time
	 * @return
	 */
	public static String getTime(long time){
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String t = format1.format(new Date(time));
		return t;
	}
}
