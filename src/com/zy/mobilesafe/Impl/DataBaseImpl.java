package com.zy.mobilesafe.Impl;

public interface DataBaseImpl {


	/****��������������****/
	public static final String TABLE_BLACKLIST="blacklist";				//��������
	public static final String CREATE_TABLE_BLACKLIST="create table "+TABLE_BLACKLIST
			+"(blacklist_id integer pramary key,blacklist_number string);";
	
	/****��������������****/
	public static final String TABLE_WHITELIST="whitelist";				//��������
	public static final String CREATE_TABLE_WHITELIST="create table "+TABLE_WHITELIST
			+"(whitelist_id integer pramary key,whitelist_number string);";
	
	/****�����������ؼ�¼��****/
	public static final String TABLE_SMSBLOCKRECORD="smsblockrecord";	//�������ؼ�¼��
	public static final String CREATE_TABLE_SMSBLOCKRECORD="create table "+TABLE_SMSBLOCKRECORD
			+" (smsblockrecord_id integer pramary key,smsblockrecord_number string"
			+ ",smsblockrecord_content string,smsblockrecord_time String);";
	
	/****�����绰���ؼ�¼��****/
	public static final String TABLE_TELBLOCKRECORD="telblockrecord";	//�绰���ؼ�¼��
	public static final String CREATE_TABLE_TELBLOCKRECORD="create table  "+TABLE_TELBLOCKRECORD
			+"(telblockrecord_id integer pramary key,"
			+ "telblockrecord_number string,telblockrecord_time string);";
	
}
