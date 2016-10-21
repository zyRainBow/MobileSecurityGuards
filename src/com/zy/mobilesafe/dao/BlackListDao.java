package com.zy.mobilesafe.dao;

import java.util.ArrayList;

import com.zy.mobilesafe.Impl.DataBaseImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BlackListDao {

	private Context context;
	public BlackListDao(Context context) {
		super();
		this.context = context;
	}
	
	/**
	 * 插入黑名单
	 * @param number
	 */
	public void insert(String number){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		ContentValues values=new ContentValues();
		values.put("blacklist_number", number);
		database.insert(DataBaseImpl.TABLE_BLACKLIST, null, values);
	}
	
	/**
	 * 删除特定的号码
	 * @param number
	 */
	public void delete(String number){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		String[] args={number};
		database.delete(DataBaseImpl.TABLE_BLACKLIST, "blacklist_number=?", args);
	}
	
	
	
	
	/**
	 * 搜索所有黑名单
	 * @param blackList
	 */
	public void searchAll(ArrayList<String> blackList){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		String [] columns={"blacklist_id","blacklist_number"};
		Cursor cursor=database.query(DataBaseImpl.TABLE_BLACKLIST, columns, null, null, null, null, null);
		
		for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			blackList.add(cursor.getString(cursor.getColumnIndex("blacklist_number")));
		}
		
	}
	
	/**
	 * 判断号码是否在黑名单中
	 * @param number
	 * @return
	 */
	public boolean isNumberInBlackList(String number){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		String [] columns={"blacklist_id","blacklist_number"};
		Cursor cursor=database.query(DataBaseImpl.TABLE_BLACKLIST, columns, null, null, null, null, null);
		
		for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			if(number.equals(cursor.getString(cursor.getColumnIndex("blacklist_number")))){
				return true;
			}
		}
		return false;
	}
	
}
