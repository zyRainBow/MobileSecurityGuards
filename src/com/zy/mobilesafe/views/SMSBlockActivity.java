package com.zy.mobilesafe.views;

import java.util.ArrayList;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.Impl.ActionImpl;
import com.zy.mobilesafe.bean.Message;
import com.zy.mobilesafe.dao.SMSBlockDao;
import com.zy.mobilesafe.utils.MyContacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class SMSBlockActivity extends Activity {

	private ListView lv_sms_block_record;
	private ArrayList<Message> smsList=new ArrayList<>();
	private MySMSAdapter adapter;
	private SMSBlockDao smsBlockDao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.activity_sms_block_layout);
		
		findView();
		initSMSList();
		adapter=new MySMSAdapter();
		lv_sms_block_record.setAdapter(adapter);
		lv_sms_block_record.setOnItemClickListener(new MySMSBlockItemClickListener());
	}
	
	/**
	 * 关联控件
	 */
	private void findView() {
		// TODO Auto-generated method stub
		lv_sms_block_record=(ListView) findViewById(R.id.lv_sms_block_record);
	}
	
	/**
	 * 初始化列表
	 */
	private void initSMSList() {
		// TODO Auto-generated method stub
		smsBlockDao=new SMSBlockDao(SMSBlockActivity.this);
		smsBlockDao.searchAll(smsList);
	}

	
	/**
	 * listview 适配器
	 * @author Administrator
	 *
	 */
	public class MySMSAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return smsList.size()+1;
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
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			MyContacts myContacts=new MyContacts(SMSBlockActivity.this);
			Holder holder=null;
			View view=arg1;
			if (view==null){
				view=LayoutInflater.from(SMSBlockActivity.this)
						.inflate(R.layout.activity_sms_block_listitem, arg2, false);
				holder=new Holder(view);
				view.setTag(holder);
			}else{
				holder=(Holder) view.getTag();
			}
			
			TextView tv_sms_name=holder.getTv_sms_name();
			TextView tv_sms_time=holder.getTv_sms_time();
			TextView tv_sms_content=holder.getTv_sms_content();
			if(arg0<smsList.size()){
				if (myContacts.isSMSNumberInContact(smsList.get(arg0))){
					tv_sms_name.setText(smsList.get(arg0).getName());
				}else{
					tv_sms_name.setText(smsList.get(arg0).getNumber());
				}
				
				tv_sms_time.setText(smsList.get(arg0).getTime());
				if (smsList.get(arg0).getContent().length()>10){
					tv_sms_content.setText(smsList.get(arg0).getContent().substring(0, 10)+"···");		//字符串太长截取前7个字符
				}else{
					tv_sms_content.setText(smsList.get(arg0).getContent());
				}
			}else{
				if (smsList.size()<=0){
					tv_sms_name.setText("没有任何短信拦截记录");
					tv_sms_time.setText("");
					tv_sms_content.setText("");
				}else{
					tv_sms_name.setText("共有"+smsList.size()+"条短信拦截记录");
					tv_sms_time.setText("");
					tv_sms_content.setText("");
				}
			}
			return view;
		}

	}
	
	
	
	/**
	 * listitem的项的单击事件
	 * @author Administrator
	 *
	 */
	public class MySMSBlockItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
			AlertDialog.Builder builder=new Builder(SMSBlockActivity.this);
			builder.setTitle(R.string.hint);
			builder.setPositiveButton(R.string.delete, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					if (smsList.size()>0){
						
						smsBlockDao.delete(smsList.get(arg2));
						smsList.remove(arg2);
						
						Toast.makeText(SMSBlockActivity.this, "数据已清除", Toast.LENGTH_LONG).show();
						
						adapter.notifyDataSetChanged();
					}
				}
			});
			
			builder.setNegativeButton(R.string.details,new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder_detail=new Builder(SMSBlockActivity.this);
					builder_detail.setTitle(R.string.hint);
					if(smsList.size()==0){
						builder_detail.setMessage("没有任何拦截记录");
					}else{
						builder_detail.setMessage("电话号码:"+smsList.get(arg2).getNumber()
									+"\n时间:"+smsList.get(arg2).getTime()+"\n短信内容:"+smsList.get(arg2).getContent());
					}
					builder_detail.setPositiveButton(R.string.ok, null);
					builder_detail.show();
				}
			});
			
			builder.show();
		}

	}	
	
	/**
	 * 持有者类
	 * @author Administrator
	 *
	 */
	public class Holder {
		private View view;
		private TextView tv_sms_name;
		private TextView tv_sms_time;
		private TextView tv_sms_content;
		public Holder(View view) {
			super();
			this.view = view;
		}
		public TextView getTv_sms_name() {
			if(tv_sms_name==null){
				tv_sms_name=(TextView) view.findViewById(R.id.tv_sms_name_or_number);
			}
			return tv_sms_name;
		}

		public TextView getTv_sms_time() {
			if (tv_sms_time==null){
				tv_sms_time=(TextView) view.findViewById(R.id.tv_sms_time);
			}
			return tv_sms_time;
		}

		public TextView getTv_sms_content() {
			if (tv_sms_content==null){
				tv_sms_content=(TextView) view.findViewById(R.id.tv_sms_content);
			}
			return tv_sms_content;
		}

	}
	

	/***
	 * 清空列表按钮单击事件
	 * @param view
	 */
	
	@SuppressWarnings("deprecation")
	public void empty_sms_list(View view){
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setTitle(getResources().getString(R.string.empty_hint));
		dialog.setCanceledOnTouchOutside(false);									//设置对话框以外的地方不起作用
		dialog.setMessage(getResources().getString(R.string.comfirm_empty));
		dialog.setButton2(getResources().getString(R.string.ok), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if (smsList.size()>0){
					smsList.clear();
					adapter.notifyDataSetChanged();
					dialog.cancel();
				}
			}
		});
		dialog.setButton3(getResources().getString(R.string.cancel), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		
		dialog.show();
	}
	
	/**
	 * 短信拦截设置按钮单击事件
	 * @param view
	 */
	public void sms_block_setting(View view){
		startActivity(new Intent(ActionImpl.SMSBLOCKSETTING_ACTION));
		overridePendingTransition(R.anim.set_totate_in, 0);
	}
	
	/**
	 * 刷新列表
	 */
	public void sms_refresh(View view){
		smsList.clear();
		initSMSList();
		adapter.notifyDataSetChanged();
		Toast.makeText(SMSBlockActivity.this, "刷新成功", Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 返回按钮
	 * @param view
	 */
	public void sms_block_return(View view){
		this.finish();
		Toast.makeText(SMSBlockActivity.this, "返回", Toast.LENGTH_LONG).show();
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(smsList.size()>0){
			smsList.clear();
		}
		initSMSList();
		adapter.notifyDataSetChanged();
	}
}
