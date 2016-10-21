package com.zy.mobilesafe.utils;

import com.zy.mobilesafe.Impl.DataBaseImpl;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseOpenHelper extends SQLiteOpenHelper {

	public MyDataBaseOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		arg0.execSQL(DataBaseImpl.CREATE_TABLE_BLACKLIST);			//创建黑名单表
		arg0.execSQL(DataBaseImpl.CREATE_TABLE_WHITELIST);			//创建白名单表
		arg0.execSQL(DataBaseImpl.CREATE_TABLE_SMSBLOCKRECORD);		//创建短信拦截记录表
		arg0.execSQL(DataBaseImpl.CREATE_TABLE_TELBLOCKRECORD);		//创建电话拦截记录表
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
