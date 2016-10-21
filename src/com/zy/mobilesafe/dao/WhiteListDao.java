package com.zy.mobilesafe.dao;

import java.util.ArrayList;

import com.zy.mobilesafe.Impl.DataBaseImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WhiteListDao {

	private Context context;
	public WhiteListDao(Context context) {
		super();
		this.context = context;
	}
	
	
	/**
	 * 插入白名单
	 * @param number
	 */
	public void insert(String number){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		ContentValues values=new ContentValues();
		values.put("whitelist_number", number);
		database.insert(DataBaseImpl.TABLE_WHITELIST, null, values);
	}
	
	
	/**
	 * 删除特定的号码
	 * @param number
	 */
	public void delete(String number){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		String[] args={number};
		database.delete(DataBaseImpl.TABLE_WHITELIST, "whitelist_number=?", args);
	}
	
	
	/**
	 * 搜索所有白名单
	 * @param whiteList
	 */
	public void searchAll(ArrayList<String> whiteList){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		String [] columns={"whitelist_id","whitelist_number"};
		Cursor cursor=database.query(DataBaseImpl.TABLE_WHITELIST, columns, null, null, null, null, null);
		
		for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			whiteList.add(cursor.getString(cursor.getColumnIndex("whitelist_number")));
		}
		
	}
	
	
	/**
	 * 判断号码是否在白名单中
	 * @param number
	 * @return
	 */
	public boolean isNumberInWhiteList(String number){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		String [] columns={"whitelist_id","whitelist_number"};
		Cursor cursor=database.query(DataBaseImpl.TABLE_WHITELIST, columns, null, null, null, null, null);
		
		for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			if(number.equals(cursor.getString(cursor.getColumnIndex("whitelist_number")))){
				return true;
			};
		}
		return false;
	}
	
}
