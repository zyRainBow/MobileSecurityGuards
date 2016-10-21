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
			String currentNumber=telephonyManager.getLine1Number();			//��ȡ��ǰ����
			if (!msp.getMyNumber().equals(currentNumber)){
				
				SmsManager smsManager=SmsManager.getDefault();
				
				smsManager.sendTextMessage(msp.getPreventThelfNumber()
											, null
											, "SIM���Ѿ��ı䣬�ֻ��п��ܱ���"
											, null
											, null);
				Intent flowsService=new Intent(arg0.getApplicationContext(), FlowMonitorService.class);
				arg0.startService(flowsService);
				Toast.makeText(arg0, "����������ط���", Toast.LENGTH_SHORT).show();
				
			}
		}
	}


}
