package com.zy.mobilesafe.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.bean.AppInfo;
import com.zy.mobilesafe.utils.MyApplicationInfos;
import com.zy.mobilesafe.utils.MySharedPreference;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AppUninstallActivity extends Activity {

	private ListView lv_app_uninstall_list;
	private ArrayList<String> uninstallPackageList=new ArrayList<>();
	private ArrayList<AppInfo> appInfoList=new ArrayList<>();
	private Myadapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_app_uninstall);
		lv_app_uninstall_list = (ListView) findViewById(R.id.lv_app_uninstall_list);
		
		adapter=new Myadapter();
		
		initAppInfoList();
		lv_app_uninstall_list.setAdapter(adapter);

	}
	
	/**
	 * 初始化应用程序列表
	 */
	private void initAppInfoList() {
		// TODO Auto-generated method stub
		MyApplicationInfos applicationInfos=new MyApplicationInfos(AppUninstallActivity.this);
		applicationInfos.getApplicationInfo(appInfoList);
	}
	
	/**
	 * listview适配器
	 * @author Administrator
	 *
	 */
	public class Myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return appInfoList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView( final int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View view=arg1;
			Holder holder=null;
			if (view==null){
				view=LayoutInflater.from(AppUninstallActivity.this)
						.inflate(R.layout.activity_app_uninstall_listitem, arg2, false);
				holder=new Holder(view);
				view.setTag(holder);
			}else{
				holder=(Holder) view.getTag();
			}
			ImageView iv_app_icon=holder.getIv_app_icon();
			TextView tv_app_name=holder.getTv_app_name();
			TextView tv_app_version=holder.getTv_app_version();
			CheckBox cb_lock_check=holder.getCb_lock();
			for (int i = 0; i < uninstallPackageList.size(); i++) {
				if(uninstallPackageList.get(i).equals(appInfoList.get(arg0).getPackageName())){
					cb_lock_check.setChecked(true);
				}else{
					cb_lock_check.setChecked(false);
				}
			}
			cb_lock_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton box, boolean ischecked) {
					// TODO Auto-generated method stub
					if(ischecked){
						uninstallPackageList.add(appInfoList.get(arg0).getPackageName());
						Toast.makeText(getApplicationContext(), appInfoList.get(arg0).getAppName()+"将被卸载", Toast.LENGTH_SHORT).show();
					}else{
						uninstallPackageList.remove(appInfoList.get(arg0).getPackageName());
						Toast.makeText(getApplicationContext(), appInfoList.get(arg0).getAppName()+"将取消卸载", Toast.LENGTH_SHORT).show();
					}
				}
			});
			iv_app_icon.setImageDrawable(appInfoList.get(arg0).getAppIcon());
			tv_app_name.setText(appInfoList.get(arg0).getAppName());
			String version=appInfoList.get(arg0).getAppVersionName();
			String ver=getResources().getString(R.string.version);
			if (version.contains("-")){
				String v=version.split("-")[0];
				
				if (v.length()>=8){
					tv_app_version.setText(ver+v.substring(0, 7));
				}else{
					tv_app_version.setText(ver+v);
				}
				
			}else{
				if (version.length()>=8){
					tv_app_version.setText(ver+version.substring(0, 7));
				}else{
					tv_app_version.setText(ver+version);
				}
			}
			return view;
		}

	}
	
	/**
	 * 持有者类
	 * @author Administrator
	 *
	 */
	private class Holder {
		private View view;
		private ImageView iv_app_icon;
		private TextView tv_app_name;
		private TextView tv_app_version;
		private CheckBox cb_lock_check;
		
		public Holder(View view) {
			super();
			this.view = view;
		}
		public ImageView getIv_app_icon() {
			if (iv_app_icon==null){
				iv_app_icon=(ImageView) view.findViewById(R.id.iv_app_icon_uninstall);
			}
			return iv_app_icon;
		}
		public TextView getTv_app_name() {
			if(tv_app_name==null){
				tv_app_name=(TextView) view.findViewById(R.id.tv_app_name_uninstall);
			}
			return tv_app_name;
		}
		public TextView getTv_app_version() {
			if(tv_app_version==null){
				tv_app_version=(TextView) view.findViewById(R.id.tv_app_version_uninstall);
			}
			return tv_app_version;
		}
		public CheckBox getCb_lock() {
			if(cb_lock_check==null){
				cb_lock_check=(CheckBox) view.findViewById(R.id.cb_uninstall_check);
			}
			return cb_lock_check;
		}
	}

	/**
	 * 取消按钮监听
	 * @param v
	 */
	public void cancel_uninstall(View v){
		finish();
	}
	
	/**
	 * 确认按钮监听
	 * @param v
	 */
	public void comfrm_uninstall(View v){
		
		for (int i = 0; i < uninstallPackageList.size(); i++) {
			if(uninstallPackageList.get(i).equals("com.zy.mobilesafe")){
				Toast.makeText(getApplicationContext(), "本应用不能卸载", Toast.LENGTH_SHORT).show();
				return;
			}else{
		        
				//通过程序的包名创建URL  
		        Uri packageURI=Uri.parse("package:"+uninstallPackageList.get(i));  
		        //创建Intent意图  
		        Intent intent=new Intent(Intent.ACTION_DELETE);  
		        //设置Uri  
		        intent.setData(packageURI);  
		        //卸载程序  
		        startActivity(intent); 
		        
		        appInfoList.clear();
		        initAppInfoList();
		        adapter.notifyDataSetChanged();
			}
		}
		
		
	}
}
