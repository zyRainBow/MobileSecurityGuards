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
	public String runningApp;  			//正在运行的程序
	public String LockedApp;			//最后运行的程序
	private boolean flag=true;			//线程循环标志 
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
					List<RunningTaskInfo> runningTaskInfos=am.getRunningTasks(1);		//拿到当前正在运行的任务栈
					RunningTaskInfo runningTaskInfo=runningTaskInfos.get(0);			//拿到当前运行的任务
					//拿到要运行的Activity的包名
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
	 * 判断应用包名是否为加锁状态
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
