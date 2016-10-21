package com.zy.mobilesafe.broadcastreceiver;


import com.zy.mobilesafe.service.FlowMonitorService;
import com.zy.mobilesafe.utils.MySharedPreference;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class BootCompleteBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		MySharedPreference msp=new MySharedPreference(arg0);
		if(msp.getIsPreventThelf()){
			TelephonyManager telephonyManager = (TelephonyManager) arg0.getSystemService(Context.TELEPHONY_SERVICE);
			String currentNumber=telephonyManager.getLine1Number();			//获取当前号码
			if (!msp.getMyNumber().equals(currentNumber)){
				
				SmsManager smsManager=SmsManager.getDefault();
				
				smsManager.sendTextMessage(msp.getPreventThelfNumber()
											, null
											, "SIM卡已经改变，手机有可能被盗"
											, null
											, null);
				Intent flowsService=new Intent(arg0.getApplicationContext(), FlowMonitorService.class);
				arg0.startService(flowsService);
				Toast.makeText(arg0, "启动流量监控服务", Toast.LENGTH_SHORT).show();
				
			}
		}
	}


}
