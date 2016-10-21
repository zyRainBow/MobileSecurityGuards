package com.zy.mobilesafe.dao;

import java.util.ArrayList;

import com.zy.mobilesafe.Impl.DataBaseImpl;
import com.zy.mobilesafe.bean.Message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SMSBlockDao {

	private Context context;
	
	public SMSBlockDao(Context context) {
		super();
		this.context = context;
	}
	
	/**
	 * ËÑË÷ËùÓÐÀ¹½Ø¶ÌÐÅ
	 * @param ListSMSBlock
	 */
	public void searchAll(ArrayList<Message> ListSMSBlock){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		String [] columns={"smsblockrecord_number","smsblockrecord_content","smsblockrecord_time"};
		Cursor cursor=database.query(DataBaseImpl.TABLE_SMSBLOCKRECORD, columns, null, null, null, null, null);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Message mm = new Message();
			mm.setNumber(cursor.getString(0));
			mm.setContent(cursor.getString(1));
			mm.setTime(cursor.getString(2));
			ListSMSBlock.add(mm);
		}

		cursor.close();
	}
	
	/**
	 * ²åÈëÀ¹½Ø¶ÌÐÅ
	 * @param message
	 */
	public void insert(Message message){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		ContentValues values=new ContentValues();
		values.put("smsblockrecord_number", message.getNumber());
		values.put("smsblockrecord_content", message.getContent());
		values.put("smsblockrecord_time", message.getTime());
		database.insert(DataBaseImpl.TABLE_SMSBLOCKRECORD, null, values);
	}
	
	/**
	 * É¾³ýÀ¹½Ø¶ÌÐÅ¼ÇÂ¼
	 * @param message
	 */
	public void delete(Message message){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		String whereClause="smsblockrecord_number=? and smsblockrecord_content=? and smsblockrecord_time=?";
		String[] whereArgs={message.getNumber(),message.getContent(),message.getTime()};
		database.delete(DataBaseImpl.TABLE_SMSBLOCKRECORD, whereClause, whereArgs);
	}
	
}
