package com.zy.mobilesafe.broadcastreceiver;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.zy.mobilesafe.R;
import com.zy.mobilesafe.Impl.ActionImpl;
import com.zy.mobilesafe.Impl.NotificationImpl;
import com.zy.mobilesafe.bean.Telephony;
import com.zy.mobilesafe.dao.BlackListDao;
import com.zy.mobilesafe.dao.TelphoneBlockDao;
import com.zy.mobilesafe.dao.WhiteListDao;
import com.zy.mobilesafe.utils.MySharedPreference;
import com.zy.mobilesafe.utils.NowTime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class TelephonyBroadcastReceiver extends BroadcastReceiver {
	private MySharedPreference share;
	private Context context;
	int num=0;  //计数
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		
		context=arg0;
		share=new MySharedPreference(arg0);	
		String phone = arg1.getStringExtra("incoming_number");
		Toast.makeText(arg0, "telephontReceiver  来电号码："+phone , Toast.LENGTH_SHORT).show();
		
		/****监听来电***/
		TelephonyManager tm=(TelephonyManager)arg0.getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	
	/**
	 * 电话监听
	 * @author Administrator
	 *
	 */
	public class MyPhoneListener extends PhoneStateListener{
		

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
			Toast.makeText(context, "电话拦截", Toast.LENGTH_SHORT).show();
			
			BlackListDao blackDao=new BlackListDao(context);
			WhiteListDao whiteDao=new WhiteListDao(context);
			TelphoneBlockDao telDao=new TelphoneBlockDao(context);
			
			String time=NowTime.getDateTimeEN();
			if (state==TelephonyManager.CALL_STATE_RINGING){
				Toast.makeText(context, "state:"+state+"tel:"+incomingNumber,Toast.LENGTH_LONG).show();
				if(share.getIsTelBlock()){									//是否开启电话拦截
					Toast.makeText(context, "电话拦截已开启", Toast.LENGTH_SHORT).show();
					if(!whiteDao.isNumberInWhiteList(incomingNumber)){		//是否在白名单中
						Toast.makeText(context, "来电号码不在白名单中", Toast.LENGTH_SHORT).show();
						Telephony telephony=new Telephony();
						telephony.setNumber(incomingNumber);
						telephony.setTime(time);
						
						if(blackDao.isNumberInBlackList(incomingNumber)){		//是否在黑名单中
							Toast.makeText(context, "来电号码"+incomingNumber+"在黑名单中，将拦截！", Toast.LENGTH_SHORT).show();
							num++;
							telDao.insert(telephony);
							blockMode(incomingNumber);
						}
						
						if(share.getIsBlockStrangeNumberTel()){					//是否拦截陌生人人电话
							Toast.makeText(context, "拦截陌生电话已开启", Toast.LENGTH_SHORT).show();
							num++;
							telDao.insert(telephony);
							blockMode(incomingNumber);
						}
						
						if(share.getIsBlockContactsTel()){						//是否拦截联系人电话
							Toast.makeText(context, "联系人电话拦截已开启", Toast.LENGTH_SHORT).show();
							num++;
							telDao.insert(telephony);
							blockMode(incomingNumber);
						}
						
					}
				}
	        }	
		
		}
		/**
		 * 拦截模式处理
		 */
		public void blockMode(String number){
			AudioManager am=(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
			ITelephony iTelephony = getITelephony(context); //获取电话接口
			String mode=share.getTelBlockMode();
			if (mode.equals(context.getResources().getString(R.string.hang_up))){		//直接挂断
				Toast.makeText(context, "直接挂断模式", Toast.LENGTH_SHORT).show();
				try {
					iTelephony.endCall();		// 结束电话
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				abortBroadcast();
				sendNotification();
			}else if(mode.equals(context.getResources().getString(R.string.mute))){		//静音
				Toast.makeText(context, "静音模式", Toast.LENGTH_SHORT).show();
				am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				sendNotification();
			}else if(mode.equals(context.getResources().getString(R.string.shock))){	//震动
				Toast.makeText(context, "震动模式", Toast.LENGTH_SHORT).show();
				am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
				sendNotification();
			}else{																		//回复短信
				Toast.makeText(context, "挂断回复短信模式", Toast.LENGTH_SHORT).show();
				try {
					iTelephony.endCall();		// 结束电话
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				SmsManager sms=SmsManager.getDefault();
				sms.sendTextMessage(number, null, "I'm so sorry,I am busy now！", null, null);
				sendNotification();
				abortBroadcast();
			}
		} 

		/**
		 * 发送通知
		 */
		public void sendNotification(){
			NotificationManager notification=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			NotificationCompat.Builder builder=new Builder(context);
			builder.setContentTitle("zy安全卫士时刻保护您手机");
			builder.setNumber(num);
			builder.setDefaults(Notification.DEFAULT_ALL);
			builder.setSmallIcon(R.drawable.app_small_icon);
			builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon));
			builder.setContentText("你有电话被拦截oo");
			PendingIntent intent=PendingIntent.getActivity(context
														, NotificationImpl.requestCode
														, new Intent(ActionImpl.TELBLOCK_ACTION)
														, PendingIntent.CONTENTS_FILE_DESCRIPTOR);
			builder.setContentIntent(intent);
			notification.notify(NotificationImpl.NotificationMain, builder.build());
			
		}
		
	}
	
	/**  
     * 根据反射获取电话接口
     * @param context  
     * @return  
     */  
     private static ITelephony getITelephony(Context context) {  
         	ITelephony iTelephony=null;  
            TelephonyManager mTelephonyManager = (TelephonyManager) context  
                    .getSystemService(Context.TELEPHONY_SERVICE);  
            Class<TelephonyManager> c = TelephonyManager.class;  
            Method getITelephonyMethod = null;  
            try {  
                getITelephonyMethod = c.getDeclaredMethod("getITelephony",  
                        (Class[]) null); // 获取声明的方法  
             
                getITelephonyMethod.setAccessible(true);  
            } catch (SecurityException e) {  
                e.printStackTrace();  
            } catch (NoSuchMethodException e) {  
                e.printStackTrace();  
            }  
  
            try {  
                iTelephony = (ITelephony) getITelephonyMethod.invoke(  
                        mTelephonyManager, (Object[]) null); // 获取实例  
//                return iTelephony;  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return iTelephony;  
        }  

}
