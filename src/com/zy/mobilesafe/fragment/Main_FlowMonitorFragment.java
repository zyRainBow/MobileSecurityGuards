package com.zy.mobilesafe.fragment;


import java.util.ArrayList;
import java.util.List;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.bean.AppInfo;
import com.zy.mobilesafe.bean.RunningAppInfo;
import com.zy.mobilesafe.service.FlowMonitorService;
import com.zy.mobilesafe.service.FlowMonitorService.MyBinder;
import com.zy.mobilesafe.utils.MyApplicationInfos;
import com.zy.mobilesafe.utils.MySharedPreference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.DialogInterface.OnClickListener;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class Main_FlowMonitorFragment extends Fragment {
	
	private ImageButton iv_flows_setting;
	private Context context;
	private MyBinder mMyBinder;			//binder
	private TextView tv_max;
	private TextView tv_mobile_upload_speed;
	private TextView tv_mobile_download_speed;
	private TextView tv_mobile_upload_total_flows;
	private TextView tv_mobile_download_total_flows;
	private TextView tv_wifi_upload_speed;
	private TextView tv_wifi_download_speed;
	private TextView tv_wifi_upload_total_flows;
	private TextView tv_wifi_download_total_flows;
	private ListView lv_app_flows;
	private MyAppInfoAdapter adapter;
	public static final int BINDER_MSG=0x00;
	private  ArrayList<RunningAppInfo> list=new ArrayList<>();		//运行的app信息
	private boolean flag;
	
	
	
	public Main_FlowMonitorFragment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case BINDER_MSG:
				setText();
				break;
			default:
				break;
			}
		};

		private void setText() {
			// TODO Auto-generated method stub
			tv_mobile_download_speed.setText(setByteFlowsSpeed(mMyBinder.getmRxBytes()));
			tv_mobile_download_total_flows.setText(setByteFlows(mMyBinder.getmobileRxTotalBytes()));
			tv_mobile_upload_speed.setText(setByteFlowsSpeed(mMyBinder.getmTxBytes()));
			tv_mobile_upload_total_flows.setText(setByteFlows(mMyBinder.getmobileTxTotalBytes()));
			tv_wifi_download_speed.setText(setByteFlowsSpeed(mMyBinder.getwRxBytes()));
			tv_wifi_download_total_flows.setText(setByteFlows(mMyBinder.getwifiRxTotalBytes()));
			tv_wifi_upload_speed.setText(setByteFlowsSpeed(mMyBinder.getwTxBytes()));
			tv_wifi_upload_total_flows.setText(setByteFlows(mMyBinder.getwifiTxTotalBytes()));
			
		}
	};
	
	
	/**
	 * 计算流量
	 * @param bytes
	 * @return
	 */
	private String setByteFlows(long bytes){
		String str="0.0";
		if(bytes<1024L){
			str=String.format("%.3f", (float)bytes)+"b";
		}else if(bytes<1048576L){
			str=String.format("%.3f", bytes/1024.0)+"kb";
		}else if(bytes<1073741824L){
			str=String.format("%.3f", bytes/1024/1024.0)+"M";
		}else if(bytes<1099511627776L){
			str=String.format("%.3f", bytes/1024/1024/1024.0)+"G";
		}else if (bytes<1125899906842624L){
			str=String.format("%.3f", bytes/1024/1024/1024/1024.0)+"T";
		}
		return str;
	}
	
	/**
	 * 计算流量速度
	 * @param bytes
	 * @return
	 */
	private String setByteFlowsSpeed(long bytes){
		String str="";
		if(bytes<1024L){
			str=String.format("%.3f", (float)bytes)+"b/s";
		}else if(bytes<(long)1024*1024){
			str=String.format("%.3f", bytes/1024.0)+"kb/s";
		}else if(bytes<(long)1024*1024*1024){
			str=String.format("%.3f", bytes/1024/1024.0)+"M/s";
		}else if(bytes<(long)1024*1024*1024*1024){
			str=String.format("%.3f", bytes/1024/1024/1024.0)+"G/s";
		}
		return str;
	}
	
	
	
	
	/**
	 * 服务链接
	 */
	private ServiceConnection conn=new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName arg0, final IBinder arg1) {
			// TODO Auto-generated method stub
			new Thread(){
				public void run(){
					
						while (!flag) {
							mMyBinder = (MyBinder) arg1;
							mHandler.sendEmptyMessage(Main_FlowMonitorFragment.BINDER_MSG);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
				}
			}.start();
		}
	};
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		context=getActivity();
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		flag=false;
		Intent intent=new Intent(context, FlowMonitorService.class);
		context.startService(intent);
		context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
		
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_main_flow_monitor, container, false);
		findView(view);
		initList();
		tv_max.setText("(最大流量通知0M)");
		
		iv_flows_setting=(ImageButton) view.findViewById(R.id.iv_flows_setting);
		iv_flows_setting.setOnClickListener(new MyImageButtonClickListener());
		adapter=new MyAppInfoAdapter();
		lv_app_flows.setAdapter(adapter);
		lv_app_flows.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg1) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					if(arg0.getFirstVisiblePosition()==0){
						Toast.makeText(getActivity(), "已经滑到顶部，刷新列表", Toast.LENGTH_SHORT).show();
						initList();
						adapter.notifyDataSetChanged();
					}else if(arg0.getLastVisiblePosition()==(arg0.getCount()-1)){
						Toast.makeText(getActivity(), "已经滑到底部", Toast.LENGTH_SHORT).show();
					}
					break;

				default:
					break;
				}
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		return view;
	}

	private void initList() {
		// TODO Auto-generated method stub
		MyApplicationInfos infos=new MyApplicationInfos(getActivity());
		ArrayList<AppInfo> appInfoList=new ArrayList<>();
		infos.getApplicationInfo(appInfoList);
		if(list.size()>0){
			list.clear();
		}
		
		/***** 监控某个应用的流量 *****/
		ActivityManager am = (ActivityManager) getActivity().getSystemService(
				Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningAppProcessInfos = am
				.getRunningAppProcesses();
		for (int i = 0; i < runningAppProcessInfos.size(); i++) {
			RunningAppProcessInfo appInfo = runningAppProcessInfos.get(i);
			int uid = appInfo.uid; // 应用uid
			String name = appInfo.processName;
			long Rx = TrafficStats.getUidRxBytes(uid);
			long Tx = TrafficStats.getUidTxBytes(uid);
			if (Rx != -1 || Tx != -1) {
//				Toast.makeText(getActivity(), "name" + name, Toast.LENGTH_SHORT)
//						.show();
				Log.i("app", "uid:" + uid + "    name:" + name
						+ "   rx:" + Rx + "   tx:" + Tx);
				RunningAppInfo app = new RunningAppInfo(uid, name, Rx, Tx);
				for (int j = 0; j < appInfoList.size(); j++) {
					if (appInfoList.get(j).getPackageName().equals(name)) {
						
						app.setIcon(appInfoList.get(i).getAppIcon());
						app.setVersion(appInfoList.get(i).getAppVersionName());
						app.setName(name);
						app.setAppName(appInfoList.get(i).getAppName());
						Log.i("applist",   "name:" + app.getAppName()
								+ "   rx:" + app.getRxBytes() + "   tx:" + app.getTxBytes());
						list.add(app);
					}
				}
			}
		}

	    	   
	}

	/**
	 * 关联控件
	 * @param view
	 */
	private void findView(View view) {
		// TODO Auto-generated method stub
		tv_max=(TextView) view.findViewById(R.id.tv_max_flows_notification);
		tv_mobile_download_speed=(TextView) view.findViewById(R.id.tv_mobile_download_speed);
		tv_mobile_download_total_flows=(TextView) view.findViewById(R.id.tv_mobile_download_total_flows);
		tv_mobile_upload_speed=(TextView) view.findViewById(R.id.tv_mobile_upload_speed);
		tv_mobile_upload_total_flows=(TextView) view.findViewById(R.id.tv_mobile_upload_total_flows);
		tv_wifi_download_speed=(TextView) view.findViewById(R.id.tv_wifi_download_speed);
		tv_wifi_download_total_flows=(TextView) view.findViewById(R.id.tv_wifi_download_total_flows);
		tv_wifi_upload_speed=(TextView) view.findViewById(R.id.tv_wifi_upload_speed);
		tv_wifi_upload_total_flows=(TextView) view.findViewById(R.id.tv_wifi_upload_total_flows);
		lv_app_flows=(ListView) view.findViewById(R.id.lv_app_flows);
	}
	
	
	public class MyAppInfoAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View view=LayoutInflater.from(getActivity())
					.inflate(R.layout.activity_app_flows_listitem, arg2,false);
			
			TextView tv_name=(TextView) view.findViewById(R.id.tv_name);
			TextView tv_version=(TextView) view.findViewById(R.id.tv_version);
			ImageView iv_iocn=(ImageView) view.findViewById(R.id.iv_icon);
			TextView tv_upload_flows=(TextView) view.findViewById(R.id.tv_upload_flows);
			TextView tv_download_flows=(TextView) view.findViewById(R.id.tv_download_flows);
			if(list.size()>0){
				tv_download_flows.setText(setByteFlows(list.get(arg0).getRxBytes()));
				tv_name.setText(list.get(arg0).getAppName());
				tv_upload_flows.setText(setByteFlows(list.get(arg0).getTxBytes()));
				String version=getResources().getString(R.string.version);
				if (list.get(arg0).getVersion().contains("-")){
					String v=list.get(arg0).getVersion().split("-")[0];
					if (v.length()>=8){
						tv_version.setText(version+v.substring(0, 7));
					}else{
						tv_version.setText(version+v);
					}
					
				}else{
					if (list.get(arg0).getVersion().length()>=8){
						tv_version.setText(version+list.get(arg0).getVersion().substring(0, 7));
					}else{
						tv_version.setText(version+list.get(arg0).getVersion());
					}
				}
				iv_iocn.setImageDrawable(list.get(arg0).getIcon());
			}
			return view;
		}

	}
	


	/**
	 * 按钮单击事件监听器
	 * @author Administrator
	 *
	 */
	public class MyImageButtonClickListener implements
			android.view.View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder=new Builder(context);
			builder.setTitle("流量设置");
			final MySharedPreference share=new MySharedPreference(context);
			View  view  =LayoutInflater.from(context).inflate(R.layout.activity_flows_setting_dialog_layout, null);
			final CheckBox cb_flows_monitor=(CheckBox) view.findViewById(R.id.cb_flows_monitor);
			final EditText et_max_flows= (EditText) view.findViewById(R.id.et_max_flows);
			builder.setView(view);
			builder.setNegativeButton(R.string.ok, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					if(TextUtils.isEmpty(et_max_flows.getText().toString().trim())){
						Toast.makeText(context, "最大流量不能为空", Toast.LENGTH_SHORT).show();
					}else{
						share.setIsFlowsMonitorNotification(cb_flows_monitor.isChecked());
						share.setMaxFlows(Integer.parseInt(et_max_flows.getText().toString().trim()));
						share.setNum(0);
						tv_max.setText("(最大流量通知"+et_max_flows.getText().toString().trim()+"M)");
					}
				}
			});
			
			builder.setPositiveButton(R.string.cancel, null);
			builder.show();
			
		}

	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MySharedPreference share=new MySharedPreference(context);
		tv_max.setText("(最大流量通知"+share.getMaxFlows()+"M)");
		
		Intent intent1=new Intent(context, FlowMonitorService.class);
		context.startService(intent1);
		context.bindService(intent1, conn, Context.BIND_AUTO_CREATE);
		
		initList();
		adapter.notifyDataSetChanged();
		
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		flag=true;
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
