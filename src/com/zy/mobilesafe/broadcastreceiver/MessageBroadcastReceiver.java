package com.zy.mobilesafe.broadcastreceiver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.Impl.NotificationImpl;
import com.zy.mobilesafe.bean.Message;
import com.zy.mobilesafe.dao.BlackListDao;
import com.zy.mobilesafe.dao.SMSBlockDao;
import com.zy.mobilesafe.dao.WhiteListDao;
import com.zy.mobilesafe.utils.MyContacts;
import com.zy.mobilesafe.utils.MySharedPreference;
import com.zy.mobilesafe.utils.NowTime;
import com.zy.mobilesafe.views.SMSBlockActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MessageBroadcastReceiver extends BroadcastReceiver {

	private MySharedPreference share;
	private SMSBlockDao smsBlockDao;
	private BlackListDao blackListDao;
	private WhiteListDao whiteListDao;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		int num=0;  //����
		
		smsBlockDao=new SMSBlockDao(arg0);
		blackListDao=new BlackListDao(arg0);
		whiteListDao=new WhiteListDao(arg0);
		
		Bundle bundle=arg1.getExtras();	//��ȡ������Ϣ
		Toast.makeText(arg0, "��������", Toast.LENGTH_LONG).show();
		TelephonyManager tm=(TelephonyManager) arg0.getSystemService(Context.TELEPHONY_SERVICE);
		String to=tm.getLine1Number();//��������
		share=new MySharedPreference(arg0);
		MyContacts contacts=new MyContacts(arg0);
		if (bundle!=null){
			Object [] pdus=(Object[]) bundle.get("pdus");
			SmsMessage []  messages=new SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++) {
				messages[i]=SmsMessage.createFromPdu((byte[]) pdus[i]);
			}
			StringBuffer sb=new StringBuffer();
			for (SmsMessage smsMessage : messages) {
				String msg=smsMessage.getMessageBody();
//				deleteSMS(arg0, msg);//ɾ���ռ����б����ض���
				long time=smsMessage.getTimestampMillis();
				String from=smsMessage.getOriginatingAddress();
				String t=NowTime.getTime(time);
				sb.append("���� "+from+"��"+to+" �Ķ���:"+msg+" ʱ��:"+t+" \n");
				Message message=new Message("", from, msg, t);
				if (share.getIsSMSBlock()){								//�Ƿ�����������
					if(!whiteListDao.isNumberInWhiteList(from)){		//�ж��Ƿ��ڰ�������
						
						/*****���غ�����***/
						if(blackListDao.isNumberInBlackList(from)){
							Toast.makeText(arg0, from+"�ں�������", Toast.LENGTH_LONG).show();
							abortBroadcast(); // ȡ���㲥
							num++;
							smsBlockDao.insert(message);
							deleteSMS(arg0, msg);//ɾ���ռ����б����ض���
						}
						
						/***����İ������***/
						if(share.getIsBlockStrangeNumberSMS()){				//�Ƿ�����İ������
							Toast.makeText(arg0, "��������İ������", Toast.LENGTH_LONG).show();
							if(!contacts.isSMSNumberInContact(message)){		//�Ƿ�������ϵ��
								abortBroadcast();				//ȡ���㲥
								num++;
								smsBlockDao.insert(message);
								deleteSMS(arg0, msg);//ɾ���ռ����б����ض���
							}
						}
							
						/***������ϵ��***/
						if (share.getIsBlockContactsSMS()) {
							Toast.makeText(arg0, "����������ϵ��", Toast.LENGTH_LONG).show();
							if(contacts.isSMSNumberInContact(message)){		//�Ƿ�������ϵ��
								abortBroadcast();	
								num++;
								smsBlockDao.insert(message);
								deleteSMS(arg0, msg);//ɾ���ռ����б����ض���//ȡ���㲥
							}
						}
						
					}
				}
			
			}
			notification(arg0,num);	
			Toast.makeText(arg0, sb, Toast.LENGTH_LONG).show();
		}
	}


	/**
	 * ��֪ͨ
	 */
	private void notification(Context arg0,int num) {
		// TODO Auto-generated method stub
		NotificationManager manager=(NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder=new Builder(arg0);
		builder.setContentTitle("zy��ȫ��ʿʱ�̱������ֻ�");
		builder.setNumber(num);
		builder.setLargeIcon(BitmapFactory.decodeResource(arg0.getResources(), R.drawable.app_icon));
		builder.setContentText("������"+num+"������");
		PendingIntent intent=PendingIntent.getActivity(arg0
													, NotificationImpl.requestCode
													, new Intent(arg0, SMSBlockActivity.class)
													, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(intent);
		manager.notify(NotificationImpl.NotificationMain, builder.build());
	}
	
	
    public void deleteSMS(Context context, String smscontent) {
        try {
            // ׼��ϵͳ�����������uri��ַ
            Uri uri = Uri.parse("content://sms/inbox");// ������
            // ��ѯ�����������еĶ���
            Cursor isRead = context.getContentResolver().query(uri, null,
                    "read=" + 0, null, null);
            while (isRead.moveToNext()) {
            	
                String body = isRead.getString(isRead.getColumnIndex("body"))
                        .trim();// ��ȡ��Ϣ����
                if (body.equals(smscontent)) {
                    int id = isRead.getInt(isRead.getColumnIndex("_id"));
 
                    int delid=context.getContentResolver().delete(
                            Uri.parse("content://sms"), "_id=" + id, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
