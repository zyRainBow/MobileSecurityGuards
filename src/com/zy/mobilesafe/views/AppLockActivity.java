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
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AppLockActivity extends Activity {

	private ListView lv_app_lock_list;
	private ArrayList<String> lockPackageList=new ArrayList<>();
	private ArrayList<AppInfo> appInfoList=new ArrayList<>();
	private MySharedPreference share;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_app_lock_layout);
        lv_app_lock_list=(ListView) findViewById(R.id.lv_app_lock_list);
        initAppInfoList();
        initlockList();
        lv_app_lock_list.setAdapter(new Myadapter());
       
    }
    

	private void initlockList() {
		// TODO Auto-generated method stub
		Set<String> set=new HashSet<>();
		share=new MySharedPreference(AppLockActivity.this);
		set=share.getappLockPackageName();
		if(set!=null){			//set判空
			Iterator<String> it=set.iterator();
			while(it.hasNext()){
				lockPackageList.add(it.next());
			}
		}
	}


	/**
	 * 初始化应用程序列表
	 */
	private void initAppInfoList() {
		// TODO Auto-generated method stub
		MyApplicationInfos applicationInfos=new MyApplicationInfos(AppLockActivity.this);
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
				view=LayoutInflater.from(AppLockActivity.this)
						.inflate(R.layout.activity_app_lock_listitem, arg2, false);
				holder=new Holder(view);
				view.setTag(holder);
			}else{
				holder=(Holder) view.getTag();
			}
			ImageView iv_app_icon=holder.getIv_app_icon();
			TextView tv_app_name=holder.getTv_app_name();
			TextView tv_app_version=holder.getTv_app_version();
			CheckBox cb_lock_check=holder.getCb_lock();
			for (int i = 0; i < lockPackageList.size(); i++) {
				if(lockPackageList.get(i).equals(appInfoList.get(arg0).getPackageName())){
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
						lockPackageList.add(appInfoList.get(arg0).getPackageName());
						Toast.makeText(getApplicationContext(), appInfoList.get(arg0).getAppName()+"将被加锁", Toast.LENGTH_SHORT).show();
					}else{
						lockPackageList.remove(appInfoList.get(arg0).getPackageName());
						Toast.makeText(getApplicationContext(), appInfoList.get(arg0).getAppName()+"将取消加锁", Toast.LENGTH_SHORT).show();
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
				iv_app_icon=(ImageView) view.findViewById(R.id.iv_app_icon);
			}
			return iv_app_icon;
		}
		public TextView getTv_app_name() {
			if(tv_app_name==null){
				tv_app_name=(TextView) view.findViewById(R.id.tv_app_name);
			}
			return tv_app_name;
		}
		public TextView getTv_app_version() {
			if(tv_app_version==null){
				tv_app_version=(TextView) view.findViewById(R.id.tv_app_version);
			}
			return tv_app_version;
		}
		public CheckBox getCb_lock() {
			if(cb_lock_check==null){
				cb_lock_check=(CheckBox) view.findViewById(R.id.cb_lock_check);
			}
			return cb_lock_check;
		}
	}

	
	/**
	 * 设置加锁密码按钮单击事件
	 * @param view
	 */
	public void setting_lock_pwd(View view){
		setting_lock_pwd();
		
	}
	
	/**
	 * 设置密码
	 */
	private void setting_lock_pwd() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new Builder(AppLockActivity.this);
		
		if (share.getAppLockPwd()!=null){
			builder.setTitle(R.string.hint);
			builder.setMessage("您已经设置了加锁密码,可以去修改加锁密码");
			builder.setPositiveButton("确定", null);
			builder.show();
		}else{
			builder.setTitle("应用加锁密码设置");
			builder.setIcon(R.drawable.app_small_icon);
			final EditText et=new EditText(AppLockActivity.this);
			et.setSingleLine(true);
			et.setHint("输入加锁密码");
			builder.setView(et);
			
			builder.setPositiveButton(R.string.ok, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					if (TextUtils.isEmpty(et.getText().toString().trim())){
						AlertDialog.Builder b_empty=new Builder(AppLockActivity.this);
						b_empty.setTitle(R.string.hint);
						b_empty.setMessage("输入加锁密码不能为空");
						b_empty.setPositiveButton(R.string.ok, null);
						b_empty.show();
					}else{
						share.setAppLockPwd(et.getText().toString().trim());
						AlertDialog.Builder b_success=new Builder(AppLockActivity.this);
						b_success.setTitle(R.string.hint);
						b_success.setMessage("应用加锁密码设置成功");
						b_success.setPositiveButton(R.string.ok, null);
						b_success.show();
					}
				}
			});
			builder.setNegativeButton(R.string.cancel, null);
			builder.show();
		}
	}


	/**
	 * 修改锁密码按钮单击事件
	 * @param view
	 */
	public void modify_lock_pwd(View view){
		modify_lock_pwd();
	}
	
	/**
	 * 修改密码
	 */
	private void modify_lock_pwd() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new Builder(AppLockActivity.this);
		builder.setTitle("应用加锁密码修改");
		builder.setIcon(R.drawable.app_small_icon);
		View arg=LayoutInflater.from(AppLockActivity.this).inflate(R.layout.activity_app_lock_pwd_layout, null, false);
		final EditText et_old_pwd=(EditText) arg.findViewById(R.id.et_old_pwd);
		final EditText et_new_pwd=(EditText) arg.findViewById(R.id.et_new_pwd);
		builder.setView(arg);
		builder.setPositiveButton(R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(et_old_pwd.getText().toString().trim())||TextUtils.isEmpty(et_new_pwd.getText().toString().trim())){
					AlertDialog.Builder b_empty=new Builder(AppLockActivity.this);
					b_empty.setTitle(R.string.hint);
					b_empty.setMessage("输入加锁密码不能为空");
					b_empty.setPositiveButton(R.string.ok, null);
					b_empty.show();
				}else{
					if (share.getAppLockPwd().equals(et_old_pwd.getText().toString().trim())){
						AlertDialog.Builder b_mistake=new Builder(AppLockActivity.this);
						b_mistake.setTitle(R.string.hint);
						b_mistake.setMessage("原密码输入错误，修改失败！");
						b_mistake.setPositiveButton(R.string.ok, null);
						b_mistake.show();
					}else{
						share.setAppLockPwd(et_new_pwd.getText().toString().trim());		//保存修改的密码
						AlertDialog.Builder b_success=new Builder(AppLockActivity.this);	
						b_success.setTitle(R.string.hint);
						b_success.setMessage("应用加锁密码修改成功");
						b_success.setPositiveButton(R.string.ok, null);
						b_success.show();
					}
				}
			}
		});
		
		builder.setNegativeButton(R.string.cancel, null);
		builder.show();
	}


	/**
	 * 确定加锁
	 * @param view
	 */
	public void comfrm_lock(View view){
		
		AlertDialog.Builder builder=new Builder(AppLockActivity.this);
		if (share.getAppLockPwd()==null){
			builder.setTitle(R.string.hint);
			builder.setCancelable(false);
			builder.setMessage("您第一次使用应用加锁,请设置先设置加锁密码！");
			builder.setPositiveButton(R.string.ok, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					setting_lock_pwd();
				}
			});
			builder.setNegativeButton(R.string.cancel, null);
			builder.show();
		}else{
			builder.setTitle("应用加锁密码验证");
			builder.setIcon(R.drawable.app_small_icon);
			final EditText et=new EditText(AppLockActivity.this);
			et.setSingleLine(true);
			et.setHint("输入加锁密码");
			builder.setView(et);
			builder.setPositiveButton(R.string.ok, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					if (TextUtils.isEmpty(et.getText().toString().trim())){
						AlertDialog.Builder b_empty=new Builder(AppLockActivity.this);
						b_empty.setTitle(R.string.hint);
						b_empty.setMessage("输入加锁密码不能为空");
						b_empty.setPositiveButton(R.string.ok, null);
						b_empty.show();
					}else{
						if (!et.getText().toString().trim().equals(share.getAppLockPwd())){
							AlertDialog.Builder b_mistake=new Builder(AppLockActivity.this);
							b_mistake.setTitle(R.string.hint);
							b_mistake.setMessage("密码输入不正确,加锁失败！");
							b_mistake.setPositiveButton(R.string.ok, null);
							b_mistake.show();
						}else{
							Set<String> set=new HashSet<>();
							for (int i = 0; i < lockPackageList.size(); i++) {
								if(lockPackageList.get(i).equals("com.zy.mobilesafe")){
									Toast.makeText(getApplicationContext(), "本应用不能上锁", Toast.LENGTH_SHORT).show();
								}else{
									set.add(lockPackageList.get(i));
								}
							}
							share.setappLockPackageName(set);
							AlertDialog.Builder b_success=new Builder(AppLockActivity.this);
							b_success.setTitle(R.string.hint);
							b_success.setMessage("应用加锁成功");
							b_success.setPositiveButton(R.string.ok, null);
							b_success.show();
						}
					}
				}
			});
			builder.setNegativeButton(R.string.cancel, null);
			builder.show();
		}
	}
}
