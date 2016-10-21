package com.zy.mobilesafe.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.zy.mobilesafe.utils.MySharedPreference;
import com.zy.mobilesafe.views.AppLockVerifyActivity;

import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MonitorAppService extends Service {
	public String runningApp;  			//�������еĳ���
	public String LockedApp;			//������еĳ���
	private boolean flag=true;			//�߳�ѭ����־ 
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Toast.makeText(getApplicationContext(), "monitorappservice", Toast.LENGTH_SHORT).show();
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				ActivityManager am=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
				while(flag){
					List<RunningTaskInfo> runningTaskInfos=am.getRunningTasks(1);		//�õ���ǰ�������е�����ջ
					RunningTaskInfo runningTaskInfo=runningTaskInfos.get(0);			//�õ���ǰ���е�����
					//�õ�Ҫ���е�Activity�İ���
			        String runningpackageName=runningTaskInfo.topActivity.getPackageName();
			        runningApp=runningpackageName;
//			        try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					if (isActivityLock(runningpackageName)) {
						LockedApp = runningpackageName;
						Intent intent = new Intent(getApplicationContext(),
								AppLockVerifyActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra("packageName", runningpackageName);
						startActivity(intent);
					}
				}
			}
			
		}.start();
		
		
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	
	/**
	 * �ж�Ӧ�ð����Ƿ�Ϊ����״̬
	 * @param packageName
	 * @return
	 */
	public boolean isActivityLock(String packageName){
		MySharedPreference share=new MySharedPreference(getApplicationContext());
		Set<String> set=share.getappLockPackageName();
		if (set!=null){
			Iterator<String> it=set.iterator();
			while(it.hasNext()){
				if(it.next().equals(packageName)){
					return true;
				}
			}
		}
		return false;
	}
	


	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
