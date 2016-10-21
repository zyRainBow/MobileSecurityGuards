package com.zy.mobilesafe.views;

import java.util.ArrayList;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.Impl.ActionImpl;
import com.zy.mobilesafe.bean.Telephony;
import com.zy.mobilesafe.dao.TelphoneBlockDao;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TelBlockActivity extends Activity {
	
	private ListView lv_tel_block_record;
	private ArrayList<Telephony> telList=new ArrayList<>();
	private MyTelAdapter adapter;
	private TelphoneBlockDao telBlockDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.activity_tel_block_layout);
		
		findView();
		initTelList();
		adapter=new MyTelAdapter();
		lv_tel_block_record.setAdapter(adapter);
		lv_tel_block_record.setOnItemClickListener(new MySMSBlockItemClickListener());
		
	}
	
	
	/**
	 * �����ؼ�
	 */
	private void findView() {
		// TODO Auto-generated method stub
		lv_tel_block_record=(ListView) findViewById(R.id.lv_tel_block_record);
	}
	
	/**
	 * ��ʼ�����ص绰�б�
	 */
	private void initTelList() {
		// TODO Auto-generated method stub
		telBlockDao=new TelphoneBlockDao(TelBlockActivity.this);
		telBlockDao.searchAll(telList);
	}

	/**
	 * listview ������
	 * @author Administrator
	 *
	 */
	public class MyTelAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return telList.size()+1;
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
			MyContacts myContacts=new MyContacts(TelBlockActivity.this);
			Holder holder=null;
			View view=arg1;
			if (view==null){
				view=LayoutInflater.from(TelBlockActivity.this)
						.inflate(R.layout.activity_tel_block_listitem, arg2, false);
				holder=new Holder(view);
				view.setTag(holder);
			}else{
				holder=(Holder) view.getTag();
			}
			
			TextView tv_tel_name=holder.getTv_tel_name();
			TextView tv_tel_time=holder.getTv_tel_time();
			TextView tv_tel_number=holder.getTv_tel_number();
			
			if(arg0<telList.size()){
				Telephony telephony=telList.get(arg0);
				if (myContacts.isTelNumberInContact(telephony)){
					tv_tel_name.setText(telephony.getName());
				}else{
					tv_tel_name.setText(getResources().getString(R.string.unknow));
				}
				
				tv_tel_time.setText(telephony.getTime());
				
				tv_tel_number.setText(telephony.getNumber());
				
			}else{
				if (telList.size()<=0){
					tv_tel_name.setText("û���κε绰���ؼ�¼");
					tv_tel_number.setText("");
					tv_tel_time.setText("");
				}else{
					tv_tel_name.setText("����"+telList.size()+"���绰���ؼ�¼");
					tv_tel_number.setText("");
					tv_tel_time.setText("");
				}
			}
			return view;
		}

	}
	
	
	/**
	 * list��ĵ����¼�
	 * @author Administrator
	 *
	 */
	public class MySMSBlockItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
			AlertDialog.Builder builder=new Builder(TelBlockActivity.this);
			builder.setTitle(R.string.hint);
			builder.setPositiveButton(R.string.delete, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					if(telList.size()>0){
						
						telBlockDao.delete(telList.get(arg2));
						telList.remove(arg2);
						Toast.makeText(TelBlockActivity.this, "������ɾ��", Toast.LENGTH_LONG).show();
						adapter.notifyDataSetChanged();
					}
				}
			});
			
			builder.setNegativeButton(R.string.details,new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder_detail=new Builder(TelBlockActivity.this);
					builder_detail.setTitle(R.string.hint);
					if (telList.size()==0){
						builder_detail.setMessage("û�е绰���ؼ�¼");
					}else{
						builder_detail.setMessage("�绰����:"+telList.get(arg2).getNumber()
								+"\nʱ��:"+telList.get(arg2).getTime());
					}
					builder_detail.setPositiveButton(R.string.ok, null);
					builder_detail.show();
				}
			});
			
			builder.show();
		}

	}	
	
	/**
	 * ��������
	 * @author Administrator
	 *
	 */
	public class Holder {
		private View view;
		private TextView tv_tel_name;
		private TextView tv_tel_time;
		private TextView tv_tel_number;
		public Holder(View view) {
			super();
			this.view = view;
		}
		public TextView getTv_tel_name() {
			if(tv_tel_name==null){
				tv_tel_name=(TextView) view.findViewById(R.id.tv_tel_name);
			}
			return tv_tel_name;
		}

		public TextView getTv_tel_time() {
			if (tv_tel_time==null){
				tv_tel_time=(TextView) view.findViewById(R.id.tv_tel_time);
			}
			return tv_tel_time;
		}

		public TextView getTv_tel_number() {
			if (tv_tel_number==null){
				tv_tel_number=(TextView) view.findViewById(R.id.tv_tel_number);
			}
			return tv_tel_number;
		}

	}
	

	/**
	 * ��հ�ť
	 * @param view
	 */
	@SuppressWarnings("deprecation")
	public void empty_tel_list(View view){
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setTitle(getResources().getString(R.string.empty_hint));
		dialog.setCanceledOnTouchOutside(false);									//���öԻ�������ĵط���������
		dialog.setMessage(getResources().getString(R.string.comfirm_empty));
		dialog.setButton2(getResources().getString(R.string.ok), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if(telList.size()>0){
					telList.clear();
					adapter.notifyDataSetChanged();
				}
				dialog.cancel();
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
	 * �绰�������ð�ť�����¼�
	 * @param view
	 */
	public void tel_block_setting(View view){
		startActivity(new Intent(ActionImpl.TELBLOCKSETTING_ACTION));
		overridePendingTransition(R.anim.set_totate_in, 0);
	}
	
	/**
	 * ˢ���б�
	 */
	public void tel_refresh(View view){
		if (telList.size()>0){
			telList.clear();
			initTelList();
			adapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * ���ذ�ť
	 * @param view
	 */
	public void tel_block_return(View view){
		this.finish();
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (telList.size()>0){
			telList.clear();
		}
			initTelList();
			adapter.notifyDataSetChanged();
	}
}
