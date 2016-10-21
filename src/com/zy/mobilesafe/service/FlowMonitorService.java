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
	private long mobileRxBytes;			//�ֻ���ǰ���յ�������
	private long old_mobileRxBytes;		//�ֻ�֮ǰ���յ�������
	private long mRxBytes;				//�ֻ�˲ʱ���յ�������
	private long mobileRxTotalBytes;
	
	private long mobileTxBytes;			//�ֻ���ǰ���͵�����
	private long old_mobileTxBytes;		//�ֻ�֮ǰ���͵�����
	private long mTxBytes;				//�ֻ�˲ʱ���͵�����
	private long mobileTxTotalBytes;	
	
	
	private long totalRxBytes;			//�ֻ����յ���������
	private long totalTxBytes;			//�ֻ����͵�������
	
	private long wifiRxBytes;			//wifi���յ�������
	private long old_wifiRxBytes;		//wifi֮ǰ���յ�������
	private long wRxBytes;				//wifi���յ���˲ʱ����
	private long wifiRxTotalBytes;
	
	private long wifiTxBytes;			//wifi���͵�����
	private long old_wifiTxBytes;		//wifi֮ǰ���͵�����
	private long wTxBytes;				//wifi���͵�˲ʱ����
	private long wifiTxTotalBytes;
	
	private  ArrayList<RunningAppInfo> list=new ArrayList<>();		//���е�app��Ϣ
	
	
	private boolean flag;
	private MySharedPreference share;
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				 if(share.getIsFlowsMonitorNotification()){
			    	   if(mobileRxTotalBytes>((share.getMaxFlows())*1024*1024)){
			    		   if(share.getNum()==0){
			    		   		Toast.makeText(getApplicationContext(), "����������������꣬�����ʹ��������������ɲ���Ҫ�ķ��ã�", Toast.LENGTH_SHORT).show();
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
						/** ��ȡ�ֻ�ͨ�� 2G/3G ���յ��ֽ��������� */
					       mobileRxBytes=TrafficStats.getMobileRxBytes();
					       if(mobileRxBytes!=-1){
					    	   mRxBytes=mobileRxBytes-old_mobileRxBytes;	//mobile�����ֽ���
					    	   old_mobileRxBytes=mobileRxBytes;	
					    	   mobileRxTotalBytes+=mobileRxBytes;
					       }
//					       Log.i("test", "mobileRxBytes:"+mobileRxBytes);
//					       Log.i("test", "old_mobileRxBytes:"+old_mobileRxBytes);
//					       Log.i("test", "mRxBytes:"+mRxBytes);
//					       Log.i("test", "mobileRxTotalBytes:"+mobileRxTotalBytes);
					       /** ��ȡ�ֻ�ͨ�� 2G/3G �������ֽ��������� */
					       mobileTxBytes=TrafficStats.getMobileTxBytes();
					       if(mobileTxBytes!=-1){
						       mTxBytes=mobileTxBytes-old_mobileTxBytes;	//mobile�ϴ��ֽ���
						       old_mobileTxBytes=mobileTxBytes;
						       mobileTxTotalBytes+=mobileRxBytes;
					       }
//					       Log.i("test", "mobileTxBytes:"+mobileTxBytes);
//					       Log.i("test", "old_mobileTxBytes:"+old_mobileTxBytes);
//					       Log.i("test", "mTxBytes:"+mTxBytes);
//					       Log.i("test", "mobileTxTotalBytes:"+mobileTxTotalBytes);
					       
					       /** ��ȡ�ֻ�ͨ���������緽ʽ���յ��ֽ���������(���� wifi) */
					       totalRxBytes=TrafficStats.getTotalRxBytes();	//�ϴ�����

					       /** ��ȡ�ֻ�ͨ���������緽ʽ���͵��ֽ���������(���� wifi) */
					       totalTxBytes=TrafficStats.getTotalTxBytes();	//��������
					       
					       /***���յ�����wifi����****/
					       wifiRxBytes=totalRxBytes-mobileRxBytes;
					       if(wifiRxBytes!=-1){
						       wRxBytes=wifiRxBytes-old_wifiRxBytes;		//wifi�����ֽ���
						       old_wifiRxBytes=wifiRxBytes;
						       wifiRxTotalBytes+=wifiRxBytes;
					       }
					       
					       Log.i("test", "wifiRxBytes:"+wifiRxBytes);
					       Log.i("test", "old_wifiRxBytes:"+old_wifiRxBytes);
					       Log.i("test", "wRxBytes:"+wRxBytes);
					       Log.i("test", "wifiRxTotalBytes:"+wifiRxTotalBytes);
					       
					       /****���͵�wifi����****/
					       wifiTxBytes=totalTxBytes-mobileTxBytes;
					       if(wifiTxBytes!=-1){
						       wTxBytes=wifiTxBytes-old_wifiTxBytes;		//wifi�ϴ����ֽ���
						       old_wifiTxBytes=wifiTxBytes;
						       wifiTxTotalBytes+=wifiTxBytes;
					       }
					       Log.i("test", "wifiTxBytes:"+wifiTxBytes);
					       Log.i("test", "old_wifiTxBytes:"+old_wifiTxBytes);
					       Log.i("test", "wTxBytes:"+wTxBytes);
					       Log.i("test", "wifiTxTotalBytes:"+wifiTxTotalBytes);
					       
					       /****����������***/
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
	 * ��ʼ��������
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
	 * binder��
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
