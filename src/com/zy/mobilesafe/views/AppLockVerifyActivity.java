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
		tv.setText(appName+"应用已被加锁，无法打开该应用,请解锁");
	}


	
	 /**
	  * 获取应用名称
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
	 * 确认解锁
	 * @param view
	 */
	public void comfirm_verify(View view){
		Intent intent=new Intent(ActionImpl.MYMONITORAPPSERVICERECEIVER_ACTION);
		String pwd=et.getText().toString().trim();
		if (num==3){
			Toast.makeText(AppLockVerifyActivity.this, "3次输入机会用完，密码解锁失败", Toast.LENGTH_SHORT).show();
			AppLockVerifyActivity.this.finish();
		}
		num++;
		if (TextUtils.isEmpty(pwd)){
			Toast.makeText(AppLockVerifyActivity.this, "密码输入不能为空，还有"+(3-num)+"次输入机会", Toast.LENGTH_SHORT).show();
			return;
		}
		MySharedPreference share=new MySharedPreference(AppLockVerifyActivity.this);
		
		if(!share.getAppLockPwd().equals(pwd)){
			Toast.makeText(AppLockVerifyActivity.this, "解锁密码输入不正确，还有"+(3-num)+"次输入机会", Toast.LENGTH_SHORT).show();
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
	 * 取消解锁
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
