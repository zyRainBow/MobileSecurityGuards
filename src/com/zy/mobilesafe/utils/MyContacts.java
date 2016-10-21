package com.zy.mobilesafe.utils;

import java.util.ArrayList;
import java.util.Iterator;

import com.zy.mobilesafe.bean.Contact;
import com.zy.mobilesafe.bean.Message;
import com.zy.mobilesafe.bean.Telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;


/**
 * 我的联系人类 ，这要用于获取本地联系人
 * @author Administrator
 *
 */
public class MyContacts {

	private Context context;
	
	public MyContacts(Context context) {
		super();
		this.context = context;
	}


	/**
	 * 获取本地联系人
	 * @param contactList
	 */
	public void  getContacts(ArrayList<Contact> contactList){
		ContentResolver contentResolver = context.getContentResolver();
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		Cursor cursor = contentResolver.query(uri, null, null, null, null);
		while(cursor.moveToNext()){
			String id = cursor.getString(cursor.getColumnIndex("_id"));
			String name = cursor.getString(cursor.getColumnIndex("display_name"));
			/****处理同名同id****/
			Cursor dataCursor = contentResolver.query(dataUri, null, "raw_contact_id = ? ", new String[] {id}, null);
			while(dataCursor.moveToNext())
			{
				String type = dataCursor.getString(dataCursor.getColumnIndex("mimetype"));
				if(type.equals("vnd.android.cursor.item/phone_v2"))
				{
					String number = dataCursor.getString(dataCursor.getColumnIndex("data1"));
					Contact contact=new Contact(Integer.parseInt(id), name, number);
					contactList.add(contact);
				}
			}
			dataCursor.close();
		}
		cursor.close();
	}
	
	/**
	 * 判断某短信号码是否存在通讯录中
	 * @param message
	 * @return
	 */
	public boolean isSMSNumberInContact(Message message){
		ArrayList<Contact> contactList=new ArrayList<>();
		getContacts(contactList);
		Iterator<Contact> iterator=contactList.iterator();
		boolean flag=false;
		while(iterator.hasNext()){
			Contact contact=iterator.next();
			if (contact.getContact_number().equals(message.getNumber())){
				message.setName(contact.getContact_name());
				flag=true;
			}
		}
		return flag;
	}
	
	/**
	 * 判断某电话号码是否存在通讯录中
	 * @param telephony
	 * @return
	 */
	public boolean isTelNumberInContact(Telephony telephony){
		ArrayList<Contact> contactList=new ArrayList<>();
		getContacts(contactList);
		Iterator<Contact> iterator=contactList.iterator();
		while(iterator.hasNext()){
			Contact contact=iterator.next();
			if (contact.getContact_number().equals(telephony.getNumber())){
				
				telephony.setName(contact.getContact_name());
				return true;
			}
		}
		
		return false;
	}
	
}
