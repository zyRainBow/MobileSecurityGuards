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
	 * ���������
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
	 * ɾ���ض��ĺ���
	 * @param number
	 */
	public void delete(String number){
		BaseDao baseDao=new BaseDao(context);
		SQLiteDatabase database=baseDao.createDatabase();
		String[] args={number};
		database.delete(DataBaseImpl.TABLE_BLACKLIST, "blacklist_number=?", args);
	}
	
	
	
	
	/**
	 * �������к�����
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
	 * �жϺ����Ƿ��ں�������
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
