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
	 * ���������
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
	 * ɾ���ض��ĺ���
	 * @param number
	 */
	public void delete(String number){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		String[] args={number};
		database.delete(DataBaseImpl.TABLE_WHITELIST, "whitelist_number=?", args);
	}
	
	
	/**
	 * �������а�����
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
	 * �жϺ����Ƿ��ڰ�������
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
