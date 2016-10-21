package com.zy.mobilesafe.Impl;

public interface DataBaseImpl {


	/****创建黑名单表常量****/
	public static final String TABLE_BLACKLIST="blacklist";				//黑名单表
	public static final String CREATE_TABLE_BLACKLIST="create table "+TABLE_BLACKLIST
			+"(blacklist_id integer pramary key,blacklist_number string);";
	
	/****创建白名单表常量****/
	public static final String TABLE_WHITELIST="whitelist";				//白名单表
	public static final String CREATE_TABLE_WHITELIST="create table "+TABLE_WHITELIST
			+"(whitelist_id integer pramary key,whitelist_number string);";
	
	/****创建短信拦截记录表****/
	public static final String TABLE_SMSBLOCKRECORD="smsblockrecord";	//短信拦截记录表
	public static final String CREATE_TABLE_SMSBLOCKRECORD="create table "+TABLE_SMSBLOCKRECORD
			+" (smsblockrecord_id integer pramary key,smsblockrecord_number string"
			+ ",smsblockrecord_content string,smsblockrecord_time String);";
	
	/****创建电话拦截记录表****/
	public static final String TABLE_TELBLOCKRECORD="telblockrecord";	//电话拦截记录表
	public static final String CREATE_TABLE_TELBLOCKRECORD="create table  "+TABLE_TELBLOCKRECORD
			+"(telblockrecord_id integer pramary key,"
			+ "telblockrecord_number string,telblockrecord_time string);";
	
}
