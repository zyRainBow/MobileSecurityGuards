package com.zy.mobilesafe.dao;

import com.zy.mobilesafe.utils.MyDataBaseOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BaseDao {
	
	private Context context;
	
	public BaseDao(Context context) {
		super();
		this.context = context;
	}

	public SQLiteDatabase createDatabase(){
		MyDataBaseOpenHelper myDataBaseOpenHelper=new MyDataBaseOpenHelper(context, "security.db", null,1);
		SQLiteDatabase dataBase=myDataBaseOpenHelper.getWritableDatabase();
		return dataBase;
	}
	
}
