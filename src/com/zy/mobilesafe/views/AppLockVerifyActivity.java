package com.zy.mobilesafe.views;

import java.util.ArrayList;
import java.util.Set;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.Impl.ActionImpl;
import com.zy.mobilesafe.bean.AppInfo;
import com.zy.mobilesafe.utils.MyApplicationInfos;
import com.zy.mobilesafe.utils.MySharedPreference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AppLockVerifyActivity extends Activity {

	private String packageName;
	private EditText et;
	private int num;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_app_lock_verify_layout);
		
		TextView tv=(TextView) findViewById(R.id.tv_lock_app_name);
		et=(EditText) findViewById(R.id.et_unlock);
		packageName=getIntent().getStringExtra("packageName");
		String appName=getAppNameByPackageName(packageName);
		tv.setText(appName+"Ӧ���ѱ��������޷��򿪸�Ӧ��,�����");
	}


	
	 /**
	  * ��ȡӦ������
	  * @param packageName
	  * @return
	  */
	private String getAppNameByPackageName(String packageName) {
		// TODO Auto-generated method stub
		MyApplicationInfos appInfos=new MyApplicationInfos(AppLockVerifyActivity.this);
		ArrayList<AppInfo> appInfoList=new ArrayList<>();
		appInfos.getApplicationInfo(appInfoList);
		for (int i = 0; i < appInfoList.size(); i++) {
			if(packageName.equals(appInfoList.get(i).getPackageName())){
				return appInfoList.get(i).getAppName();
			}
		}
		return null;
	}
	
	/**
	 * ȷ�Ͻ���
	 * @param view
	 */
	public void comfirm_verify(View view){
		Intent intent=new Intent(ActionImpl.MYMONITORAPPSERVICERECEIVER_ACTION);
		String pwd=et.getText().toString().trim();
		if (num==3){
			Toast.makeText(AppLockVerifyActivity.this, "3������������꣬�������ʧ��", Toast.LENGTH_SHORT).show();
			AppLockVerifyActivity.this.finish();
		}
		num++;
		if (TextUtils.isEmpty(pwd)){
			Toast.makeText(AppLockVerifyActivity.this, "�������벻��Ϊ�գ�����"+(3-num)+"���������", Toast.LENGTH_SHORT).show();
			return;
		}
		MySharedPreference share=new MySharedPreference(AppLockVerifyActivity.this);
		
		if(!share.getAppLockPwd().equals(pwd)){
			Toast.makeText(AppLockVerifyActivity.this, "�����������벻��ȷ������"+(3-num)+"���������", Toast.LENGTH_SHORT).show();
		}else{
			intent.putExtra("isVerify", true);
			Set<String> set=share.getappLockPackageName();
			Log.i("lockapp", "before"+set);
			if(set!=null){
        		set.remove(packageName);
        		Log.i("lockapp", "after"+set+"");
    		}
			share.setappLockPackageName(set);
			AppLockVerifyActivity.this.finish();
		}
	}
	
	/**
	 * ȡ������
	 * @param view
	 */
	public void cancel_verify(View view){
		AppLockVerifyActivity.this.finish();
		
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();	
		
	}
}
