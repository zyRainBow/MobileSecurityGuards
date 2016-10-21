package com.zy.mobilesafe.service;

import java.util.ArrayList;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.bean.RunningAppInfo;
import com.zy.mobilesafe.utils.MySharedPreference;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class FlowMonitorService extends Service {
	private long mobileRxBytes;			//手机当前接收到的流量
	private long old_mobileRxBytes;		//手机之前接收到的流量
	private long mRxBytes;				//手机瞬时接收到的流量
	private long mobileRxTotalBytes;
	
	private long mobileTxBytes;			//手机当前发送的流量
	private long old_mobileTxBytes;		//手机之前发送的流量
	private long mTxBytes;				//手机瞬时发送的流量
	private long mobileTxTotalBytes;	
	
	
	private long totalRxBytes;			//手机接收到的总流量
	private long totalTxBytes;			//手机发送的总流量
	
	private long wifiRxBytes;			//wifi接收到的流量
	private long old_wifiRxBytes;		//wifi之前接收到的流量
	private long wRxBytes;				//wifi接收到的瞬时流量
	private long wifiRxTotalBytes;
	
	private long wifiTxBytes;			//wifi发送的流量
	private long old_wifiTxBytes;		//wifi之前发送的流量
	private long wTxBytes;				//wifi发送的瞬时流量
	private long wifiTxTotalBytes;
	
	private  ArrayList<RunningAppInfo> list=new ArrayList<>();		//运行的app信息
	
	
	private boolean flag;
	private MySharedPreference share;
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				 if(share.getIsFlowsMonitorNotification()){
			    	   if(mobileRxTotalBytes>((share.getMaxFlows())*1024*1024)){
			    		   if(share.getNum()==0){
			    		   		Toast.makeText(getApplicationContext(), "设置最大流量已用完，请合理使用流量，以免造成不必要的费用！", Toast.LENGTH_SHORT).show();
			    		   		share.setNum(1);;
			    		   }		
			    	   }
			     }
				break;

			default:
				break;
			}
			
		};
	};
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Toast.makeText(getApplicationContext(), "flowmonitorservice", Toast.LENGTH_SHORT).show();
		share=new MySharedPreference(getApplicationContext());
		
		initTotalFlows();
		new Thread(){

			@SuppressLint("NewApi")
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				
				while(!flag){
					
					try {
						/** 获取手机通过 2G/3G 接收的字节流量总数 */
					       mobileRxBytes=TrafficStats.getMobileRxBytes();
					       if(mobileRxBytes!=-1){
					    	   mRxBytes=mobileRxBytes-old_mobileRxBytes;	//mobile下载字节数
					    	   old_mobileRxBytes=mobileRxBytes;	
					    	   mobileRxTotalBytes+=mobileRxBytes;
					       }
//					       Log.i("test", "mobileRxBytes:"+mobileRxBytes);
//					       Log.i("test", "old_mobileRxBytes:"+old_mobileRxBytes);
//					       Log.i("test", "mRxBytes:"+mRxBytes);
//					       Log.i("test", "mobileRxTotalBytes:"+mobileRxTotalBytes);
					       /** 获取手机通过 2G/3G 发出的字节流量总数 */
					       mobileTxBytes=TrafficStats.getMobileTxBytes();
					       if(mobileTxBytes!=-1){
						       mTxBytes=mobileTxBytes-old_mobileTxBytes;	//mobile上传字节数
						       old_mobileTxBytes=mobileTxBytes;
						       mobileTxTotalBytes+=mobileRxBytes;
					       }
//					       Log.i("test", "mobileTxBytes:"+mobileTxBytes);
//					       Log.i("test", "old_mobileTxBytes:"+old_mobileTxBytes);
//					       Log.i("test", "mTxBytes:"+mTxBytes);
//					       Log.i("test", "mobileTxTotalBytes:"+mobileTxTotalBytes);
					       
					       /** 获取手机通过所有网络方式接收的字节流量总数(包括 wifi) */
					       totalRxBytes=TrafficStats.getTotalRxBytes();	//上传总量

					       /** 获取手机通过所有网络方式发送的字节流量总数(包括 wifi) */
					       totalTxBytes=TrafficStats.getTotalTxBytes();	//下载总量
					       
					       /***接收到的我wifi流量****/
					       wifiRxBytes=totalRxBytes-mobileRxBytes;
					       if(wifiRxBytes!=-1){
						       wRxBytes=wifiRxBytes-old_wifiRxBytes;		//wifi下载字节数
						       old_wifiRxBytes=wifiRxBytes;
						       wifiRxTotalBytes+=wifiRxBytes;
					       }
					       
					       Log.i("test", "wifiRxBytes:"+wifiRxBytes);
					       Log.i("test", "old_wifiRxBytes:"+old_wifiRxBytes);
					       Log.i("test", "wRxBytes:"+wRxBytes);
					       Log.i("test", "wifiRxTotalBytes:"+wifiRxTotalBytes);
					       
					       /****发送的wifi流量****/
					       wifiTxBytes=totalTxBytes-mobileTxBytes;
					       if(wifiTxBytes!=-1){
						       wTxBytes=wifiTxBytes-old_wifiTxBytes;		//wifi上传的字节数
						       old_wifiTxBytes=wifiTxBytes;
						       wifiTxTotalBytes+=wifiTxBytes;
					       }
					       Log.i("test", "wifiTxBytes:"+wifiTxBytes);
					       Log.i("test", "old_wifiTxBytes:"+old_wifiTxBytes);
					       Log.i("test", "wTxBytes:"+wTxBytes);
					       Log.i("test", "wifiTxTotalBytes:"+wifiTxTotalBytes);
					       
					       /****保存总流量***/
					       share.setmobileRxTotalFlows(mobileRxTotalBytes);
					       share.setmobileTxTotalFlows(mobileTxTotalBytes);
					       share.setwifiRxTotalFlows(wifiRxTotalBytes);
					       share.setwifiTxTotalFlows(wifiTxTotalBytes);
					       
					       handler.sendEmptyMessage(1);
					       
					       Thread.sleep(1000);	
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	
		}.start();
		
	}
	

	/**
	 * 初始化总流量
	 */
	private void initTotalFlows() {
		// TODO Auto-generated method stub
		mobileRxTotalBytes=share.getmobileRxTotalFlows();
		mobileTxTotalBytes=share.getmobileTxTotalFlows();
		wifiRxTotalBytes=share.getwifiRxTotalFlows();
		wifiTxTotalBytes=share.getwifiTxTotalFlows();
	}


	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return new MyBinder();
	}
	
	/**
	 * binder类
	 * @author Administrator
	 *
	 */
	public class MyBinder extends Binder{
		public long getmobileRxTotalBytes(){
			return mobileRxTotalBytes;
		}
		
		public long getmobileTxTotalBytes(){
			return mobileTxBytes;
		}
		
		public long getmRxBytes(){
			return mRxBytes;
		}
		
		public long getmTxBytes(){
			return mTxBytes;
		}
		public long getwifiRxTotalBytes(){
			return wifiRxTotalBytes;
		}
		
		public long getwifiTxTotalBytes(){
			return wifiTxBytes;
		}
		
		public long getwRxBytes(){
			return wRxBytes;
		}
		
		public long getwTxBytes(){
			return wTxBytes;
		}
		
		public ArrayList<RunningAppInfo> getRunningAppInfo(){
			return list;
		}
	
	}


}
