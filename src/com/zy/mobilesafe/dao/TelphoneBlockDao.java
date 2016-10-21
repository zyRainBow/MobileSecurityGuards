package com.zy.mobilesafe.dao;

import java.util.ArrayList;

import com.zy.mobilesafe.Impl.DataBaseImpl;
import com.zy.mobilesafe.bean.Telephony;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TelphoneBlockDao {

private Context context;
	
	public TelphoneBlockDao(Context context) {
		super();
		this.context = context;
	}
	
	/**
	 * 搜索所有的电话拦截记录
	 * @param listTelBlock
	 */
	public void searchAll(ArrayList<Telephony> listTelBlock){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		String [] columns={"telblockrecord_id","telblockrecord_number","telblockrecord_time"};
		Cursor cursor=database.query(DataBaseImpl.TABLE_TELBLOCKRECORD, columns, null, null, null, null, null);
		
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Telephony mt = new Telephony();
			mt.setNumber(cursor.getString(cursor
					.getColumnIndex("telblockrecord_number")));
			mt.setTime(cursor.getString(cursor
					.getColumnIndex("telblockrecord_time")));
			listTelBlock.add(mt);
		}
		cursor.close();
	}
	
	
	/**
	 * 插入电话拦截
	 * @param telephony
	 */
	public void insert(Telephony telephony){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		ContentValues values=new ContentValues();
		values.put("telblockrecord_number", telephony.getNumber());
		values.put("telblockrecord_time", telephony.getTime());
		database.insert(DataBaseImpl.TABLE_TELBLOCKRECORD, null, values);
	}
	
	/**
	 * 删除电话拦截记录
	 * @param telephony
	 */
	public void delete(Telephony telephony){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		String whereClause="telblockrecord_number=? and telblockrecord_time=?";
		String[] whereArgs={telephony.getNumber(),telephony.getTime()};
		database.delete(DataBaseImpl.TABLE_TELBLOCKRECORD, whereClause, whereArgs);
	}
	
}
