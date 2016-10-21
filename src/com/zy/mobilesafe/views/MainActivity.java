package com.zy.mobilesafe.views;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.Impl.NotificationImpl;
import com.zy.mobilesafe.fragment.Main_AppManageFragment;
import com.zy.mobilesafe.fragment.Main_ExamScanFragment;
import com.zy.mobilesafe.fragment.Main_FlowMonitorFragment;
import com.zy.mobilesafe.fragment.Main_HarassBlockFragment;
import com.zy.mobilesafe.service.MonitorAppService;
import com.zy.mobilesafe.utils.MySharedPreference;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private ViewPager viewpager;		//viewpager
	private Button btn_exam_scan;		//体检扫描按钮
	private Button btn_harass_block;	//骚扰拦截按钮
	private Button btn_flow_monitor;	//流量监控按钮
	private Button btn_app_manage;		//应用管理按钮
	
	private MySharedPreference mySharedPreference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		//无标题显示
		setContentView(R.layout.activity_main);
		findView();
		
		Intent intent_app=new Intent(getApplicationContext(), MonitorAppService.class);
		startService(intent_app);	//启动应用锁服务
		
		viewpager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager()));
		viewpager.setOnPageChangeListener(new MainViewPagerChangeListener());
		
		examScan();//首界面第一个按钮被选中(默认情况)
		
		TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		/****保存本机号码****/
		String personalNumber=tm.getLine1Number();
		if (TextUtils.isEmpty(personalNumber)){
			Toast.makeText(getApplicationContext(), "没有插SIM卡", Toast.LENGTH_SHORT).show();
		}
		mySharedPreference=new MySharedPreference(getApplicationContext());
		mySharedPreference.setMyNumber(personalNumber);		
		
		//显示悬浮通知框
		notification();
		
	}
	


	/**
	 * 关联控件
	 */
	private void findView() {
		// TODO Auto-generated method stub
		viewpager=(ViewPager) findViewById(R.id.viewPager);
		btn_exam_scan=(Button) findViewById(R.id.btn_exam_scan);
		btn_harass_block=(Button) findViewById(R.id.btn_harass_block);
		btn_flow_monitor=(Button) findViewById(R.id.btn_flow_monitor);
		btn_app_manage=(Button) findViewById(R.id.btn_app_manage);
	}
	
	/**
	 * viewpage的fragment适配器
	 * @author Administrator
	 *
	 */
	public class MainViewPagerAdapter extends FragmentPagerAdapter{

		public MainViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 0:
				Main_ExamScanFragment main_esf=new Main_ExamScanFragment();
				return main_esf;
			case 1:
				Main_HarassBlockFragment main_hbf=new Main_HarassBlockFragment();
				return main_hbf;
			case 2:
				Main_FlowMonitorFragment main_fmf=new Main_FlowMonitorFragment();
				return main_fmf;
			case 3:
				Main_AppManageFragment main_amf=new Main_AppManageFragment();
				return main_amf;
			}
			
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
		}
	}
	
	/**
	 * viewpage的页面改变监听器类
	 * @author Administrator
	 *
	 */
	public class MainViewPagerChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			viewpager.setCurrentItem(arg0, true);
			switch (arg0) {
			case 0:
				btn_exam_scan.setTextColor(Color.RED);
				btn_harass_block.setTextColor(Color.BLACK);
				btn_flow_monitor.setTextColor(Color.BLACK);
				btn_app_manage.setTextColor(Color.BLACK);
				break;
			case 1:
				btn_exam_scan.setTextColor(Color.BLACK);
				btn_harass_block.setTextColor(Color.RED);
				btn_flow_monitor.setTextColor(Color.BLACK);
				btn_app_manage.setTextColor(Color.BLACK);
				break;
			case 2:
				btn_exam_scan.setTextColor(Color.BLACK);
				btn_harass_block.setTextColor(Color.BLACK);
				btn_flow_monitor.setTextColor(Color.RED);
				btn_app_manage.setTextColor(Color.BLACK);
				break;
			case 3:
				btn_exam_scan.setTextColor(Color.BLACK);
				btn_harass_block.setTextColor(Color.BLACK);
				btn_flow_monitor.setTextColor(Color.BLACK);
				btn_app_manage.setTextColor(Color.RED);
				break;

			default:
				break;
			}
			
		}

	}
	
	/**
	 * 所有按钮单击事件
	 * @param view
	 */
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.btn_exam_scan:
			examScan();
			break;
		case R.id.btn_harass_block:
			harassBlock();
			break;
		case R.id.btn_flow_monitor:
			flowMonitor();
			break;
		case R.id.btn_app_manage:
			appManage();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 体检扫描按钮单击事件
	 */
	private void examScan() {
		// TODO Auto-generated method stub
		viewpager.setCurrentItem(0,true);
		btn_exam_scan.setTextColor(Color.RED);
		btn_harass_block.setTextColor(Color.BLACK);
		btn_flow_monitor.setTextColor(Color.BLACK);
		btn_app_manage.setTextColor(Color.BLACK);
	}
	
	/**
	 * 骚扰拦截按钮单击事件
	 */
	private void harassBlock() {
		// TODO Auto-generated method stub
		viewpager.setCurrentItem(1, true);
		btn_exam_scan.setTextColor(Color.BLACK);
		btn_harass_block.setTextColor(Color.RED);
		btn_flow_monitor.setTextColor(Color.BLACK);
		btn_app_manage.setTextColor(Color.BLACK);
	}
	
	/**
	 * 流量监控按妞妞单击事件
	 */
	private void flowMonitor() {
		// TODO Auto-generated method stub
		viewpager.setCurrentItem(2, true);
		btn_exam_scan.setTextColor(Color.BLACK);
		btn_harass_block.setTextColor(Color.BLACK);
		btn_flow_monitor.setTextColor(Color.RED);
		btn_app_manage.setTextColor(Color.BLACK);
	}
	
	/**
	 * 应用管理按钮单击事件
	 */
	private void appManage() {
		// TODO Auto-generated method stub
		viewpager.setCurrentItem(3, true);
		btn_exam_scan.setTextColor(Color.BLACK);
		btn_harass_block.setTextColor(Color.BLACK);
		btn_flow_monitor.setTextColor(Color.BLACK);
		btn_app_manage.setTextColor(Color.RED);
	}
	
	
	/**
	 * 发通知(悬浮框)，触摸后进入主界面
	 */
	private void notification() {
		// TODO Auto-generated method stub
		NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder=new Builder(getApplicationContext());
		builder.setContentTitle("zy安全卫士时刻保护您手机");
		builder.setNumber(0);
		builder.setDefaults(Notification.DEFAULT_ALL);
		builder.setSmallIcon(R.drawable.app_small_icon);
		builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon));
		builder.setContentText("You are My Destiny~~~");
		PendingIntent intent=PendingIntent.getActivity(MainActivity.this
													, NotificationImpl.requestCode
													, new Intent(getApplicationContext(), MainActivity.class)
													, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(intent);
		manager.notify(NotificationImpl.NotificationMain, builder.build());
	}
	
}
