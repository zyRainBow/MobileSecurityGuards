package com.zy.mobilesafe.views;


import com.zy.mobilesafe.R;
import com.zy.mobilesafe.utils.MySharedPreference;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TelBlockSettingActivity extends Activity {

	private ListView lv_tel_block_setting;
	private int[] TitleArray={R.string.block_state,R.string.strange_number,R.string.contact};
	private int[] stateArray={R.string.on,R.string.off};
	private int[] drawableId={R.drawable.switch_on,R.drawable.switch_off};
	private boolean isBlockStrangeNumber;
	private boolean isBlockContacts;
	private boolean isTelBlock;
	private MySharedPreference share;
	private RadioButton rb_mode_hang_up;
	private RadioButton rb_mode_mute;
	private RadioButton rb_mode_shock;
	private RadioButton rb_mode_reply_sms;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tel_block_setting_layout);
        share=new MySharedPreference(TelBlockSettingActivity.this);
		lv_tel_block_setting=(ListView) findViewById(R.id.lv_tel_block_setting);
		initIsBlock();
		lv_tel_block_setting.setAdapter(new MyAdapter());
		
		lv_tel_block_setting.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ImageView iv_switch = (ImageView) arg1
						.findViewById(R.id.iv_switch);
				TextView tv_state = (TextView) arg1.findViewById(R.id.tv_state);
				switch (arg2) {
				case 0:
					if (isTelBlock) {
						isTelBlock = false;
						iv_switch.setImageResource(drawableId[1]);
						tv_state.setText(stateArray[1]);
					} else {
						isTelBlock = true;
						iv_switch.setImageResource(drawableId[0]);
						tv_state.setText(stateArray[0]);
					}
					share.setIsTelBlock(isTelBlock);
					break;
				
				case 1: {
					if (isBlockStrangeNumber) {
						isBlockStrangeNumber = false;
						iv_switch.setImageResource(drawableId[1]);
						tv_state.setText(stateArray[1]);
					} else {
						isBlockStrangeNumber = true;
						iv_switch.setImageResource(drawableId[0]);
						tv_state.setText(stateArray[0]);
					}
					share.setIsBlockStrangeNumberTel(isBlockStrangeNumber);
					break;
				}
				case 2: {
					if (isBlockContacts) {
						isBlockContacts = false;
						iv_switch.setImageResource(drawableId[1]);
						tv_state.setText(stateArray[1]);
					} else {
						isBlockContacts = true;
						iv_switch.setImageResource(drawableId[0]);
						tv_state.setText(stateArray[0]);
					}
					share.setIsBlockContactsTel(isBlockContacts);
					break;
				}
				default:
					break;
				}
			}
		});
	}

	/**
	 * 初始化是否拦截
	 */
	public void initIsBlock(){
		MySharedPreference share=new MySharedPreference(TelBlockSettingActivity.this);
		isBlockContacts=share.getIsBlockContactsTel();
		isBlockStrangeNumber=share.getIsBlockStrangeNumberTel();
		isTelBlock=share.getIsTelBlock();
//		Toast.makeText(SMSBlockSettingActivity.this, "isBlockGarbageSMS:"+isBlockGarbageSMS+"\n"
//				+"isBlockStrangeNumber:"+isBlockStrangeNumber+"\n"
//				+"isBlockContacts"+isBlockContacts, Toast.LENGTH_SHORT).show();
	}
	
	
	/**
	 * listview适配器
	 * @author Administrator
	 */
	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return TitleArray.length;
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
			View view=LayoutInflater.from(TelBlockSettingActivity.this).inflate(R.layout.activity_block_setting_listitem, arg2, false);
			TextView tv_title=(TextView) view.findViewById(R.id.tv_list_name);
			tv_title.setText(TitleArray[arg0]);
			TextView tv_state=(TextView) view.findViewById(R.id.tv_state);
			ImageView iv_switch=(ImageView) view.findViewById(R.id.iv_switch);
			initImageState(arg0,tv_state,iv_switch);
			return view;
		}

	}
	
	/**
	 * 初始化list图标和状态
	 * @param index
	 */
	private void initImageState(int index,TextView tv_state,ImageView iv_switch) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			if (isTelBlock){
				tv_state.setText(stateArray[0]);
				iv_switch.setImageResource(drawableId[0]);
			}else{
				iv_switch.setImageResource(drawableId[1]);
				tv_state.setText(stateArray[1]);
			}
			break;
		case 1:
			if (isBlockStrangeNumber){
				tv_state.setText(stateArray[0]);
				iv_switch.setImageResource(drawableId[0]);
			}else{
				iv_switch.setImageResource(drawableId[1]);
				tv_state.setText(stateArray[1]);
			}
			break;
		case 2:
			if (isBlockContacts){
				tv_state.setText(stateArray[0]);
				iv_switch.setImageResource(drawableId[0]);
			}else{
				iv_switch.setImageResource(drawableId[1]);
				tv_state.setText(stateArray[1]);
			}
			break;

		default:
			break;
		}
	}  
	
	/**
	 * 电话拦截模式
	 * @param view
	 */
	public void tel_block_mode(View view){
		
		AlertDialog.Builder builder=new AlertDialog.Builder(TelBlockSettingActivity.this);
		builder.setTitle(R.string.tel_block_mode);
		builder.setIcon(R.drawable.app_small_icon);
		View arg=LayoutInflater.from(TelBlockSettingActivity.this).inflate(R.layout.activity_alertdialog_layout, null, false);
		final RadioGroup rg=(RadioGroup) arg.findViewById(R.id.rg_mode);
		builder.setView(arg);
		rb_mode_hang_up=(RadioButton) arg.findViewById(R.id.rb_mode_hang_up);
		rb_mode_mute=(RadioButton) arg.findViewById(R.id.rb_mode_mute);
		rb_mode_reply_sms=(RadioButton) arg.findViewById(R.id.rb_mode_reply_sms);
		rb_mode_shock=(RadioButton) arg.findViewById(R.id.rb_mode_shock);
		initBlockMode();
		builder.setPositiveButton(R.string.ok, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (rg.getCheckedRadioButtonId()) {
				case R.id.rb_mode_hang_up:
					share.setTelBlockMode(getResources().getString(R.string.hang_up));
					Toast.makeText(getApplicationContext(), "直接挂断模式", Toast.LENGTH_SHORT).show();
					break;
				case R.id.rb_mode_mute:
					share.setTelBlockMode(getResources().getString(R.string.mute));
					Toast.makeText(getApplicationContext(), "静音模式", Toast.LENGTH_SHORT).show();
					break;
				case R.id.rb_mode_shock:
					share.setTelBlockMode(getResources().getString(R.string.shock));
					Toast.makeText(getApplicationContext(), "震动模式", Toast.LENGTH_SHORT).show();
					break;
				case R.id.rb_mode_reply_sms:
					share.setTelBlockMode(getResources().getString(R.string.reply_sms));
					Toast.makeText(getApplicationContext(), "短信回复模式", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		});
		builder.show();
		
	}
	
	/**
	 * 初始化拦截模式
	 */
	private void initBlockMode() {
		// TODO Auto-generated method stub
		String mode=share.getTelBlockMode();
		if (mode.equals(getResources().getString(R.string.hang_up))){		//直接挂断
			rb_mode_hang_up.setChecked(true);
		}else if(mode.equals(getResources().getString(R.string.mute))){		//静音
			rb_mode_mute.setChecked(true);
		}else if(mode.equals(getResources().getString(R.string.shock))){	//震动
			rb_mode_shock.setChecked(true);
		}else{																//回复短信
			rb_mode_reply_sms.setChecked(true);
		}
	}

	/**
	 * 返回
	 * @param view
	 */
	public void setting_return(View view){
		TelBlockSettingActivity.this.finish();
	}
	
}
