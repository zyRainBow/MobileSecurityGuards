package com.zy.mobilesafe.views;

import java.util.ArrayList;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.utils.MySharedPreference;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SMSBlockSettingActivity extends Activity {

	private ListView lv_sms_block_setting;
	private int[] TitleArray={R.string.block_state,R.string.strange_number,R.string.contact};
	private int[] stateArray={R.string.on,R.string.off};
	private int[] drawableId={R.drawable.switch_on,R.drawable.switch_off};
	private boolean isBlockStrangeNumber;
	private boolean isBlockContacts;
	private boolean isSMSBlock;
	private ArrayList<View> viewList=new ArrayList<View>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sms_block_setting_layout);
		lv_sms_block_setting=(ListView) findViewById(R.id.lv_sms_block_setting);
		initIsBlock();
		
		lv_sms_block_setting.setAdapter(new MyAdapter());
		
		lv_sms_block_setting.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ImageView iv_switch=(ImageView) arg1.findViewById(R.id.iv_switch);
				TextView tv_state=(TextView) arg1.findViewById(R.id.tv_state);
				MySharedPreference share=new MySharedPreference(SMSBlockSettingActivity.this);
				switch (arg2) {
				case 0:
				{
					if (isSMSBlock){
						isSMSBlock=false;
						iv_switch.setImageResource(drawableId[1]);
						tv_state.setText(stateArray[1]);
					}else{
						isSMSBlock=true;
						iv_switch.setImageResource(drawableId[0]);
						tv_state.setText(stateArray[0]);
					}
					share.setIsSMSBlock(isSMSBlock);
					break;
				}
				case 1:
				{
					if (isBlockStrangeNumber){
						isBlockStrangeNumber=false;
						iv_switch.setImageResource(drawableId[1]);
						tv_state.setText(stateArray[1]);
					}else{
						isBlockStrangeNumber=true;
						iv_switch.setImageResource(drawableId[0]);
						tv_state.setText(stateArray[0]);
					}
					share.setIsBlockStrangeNumberSMS(isBlockStrangeNumber);
					break;
				}
				case 2:
				{
					if (isBlockContacts){
						isBlockContacts=false;
						iv_switch.setImageResource(drawableId[1]);
						tv_state.setText(stateArray[1]);
					}else{
						isBlockContacts=true;
						iv_switch.setImageResource(drawableId[0]);
						tv_state.setText(stateArray[0]);
					}
					share.setIsBlockContactsSMS(isBlockContacts);
					break;
				}
				default:
					break;
				}
			}
		});
	}
	
	/**
	 * ≥ı ºªØ «∑Ò¿πΩÿ
	 */
	public void initIsBlock(){
		MySharedPreference share=new MySharedPreference(SMSBlockSettingActivity.this);
		isBlockContacts=share.getIsBlockContactsSMS();
		isBlockStrangeNumber=share.getIsBlockStrangeNumberSMS();
		isSMSBlock=share.getIsSMSBlock();
	}
	
	
	/**
	 * listview  ≈‰∆˜
	 * @author Administrator
	 *
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
			View view=LayoutInflater.from(SMSBlockSettingActivity.this).inflate(R.layout.activity_block_setting_listitem, arg2, false);
			TextView tv_title=(TextView) view.findViewById(R.id.tv_list_name);
			tv_title.setText(TitleArray[arg0]);
			TextView tv_state=(TextView) view.findViewById(R.id.tv_state);
			ImageView iv_switch=(ImageView) view.findViewById(R.id.iv_switch);
			initImageState(arg0,tv_state,iv_switch);
			viewList.add(view);
			return view;
		}


	}
	
	/**
	 * ≥ı ºªØlistÕº±Í∫Õ◊¥Ã¨
	 * @param index
	 */
	private void initImageState(int index,TextView tv_state,ImageView iv_switch) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			if (isSMSBlock){
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
	
	
}
