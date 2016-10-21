package com.zy.mobilesafe.fragment;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.Impl.ActionImpl;
import com.zy.mobilesafe.utils.MySharedPreference;
import com.zy.mobilesafe.views.MainActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Main_HarassBlockFragment extends Fragment {
	
	private LinearLayout layout_pwd_setting;
	private LinearLayout layout_sms_block;
	private LinearLayout layout_tel_block;
	private LinearLayout layout_list;
	
	private Button btn_sms_block;
	private Button btn_tel_block;
	private Button btn_black_white_list;
	private Button btn_black_pwd_setting;
	private MySharedPreference share;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		share=new MySharedPreference(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view=inflater.inflate(R.layout.fragment_main_harass_block, container, false);
		
		btn_sms_block=(Button) view.findViewById(R.id.btn_sms_block);
		btn_tel_block=(Button) view.findViewById(R.id.btn_tel_block);
		btn_black_white_list=(Button) view.findViewById(R.id.btn_black_white_list);
		btn_black_pwd_setting=(Button) view.findViewById(R.id.btn_black_pwd_setting);
		
		layout_pwd_setting=(LinearLayout)view.findViewById(R.id.layout_pwd_setting);
		layout_sms_block=(LinearLayout)view.findViewById(R.id.layout_sms_block);
		layout_tel_block=(LinearLayout)view.findViewById(R.id.layout_tel_block);
		layout_list=(LinearLayout)view.findViewById(R.id.layout_list);
		
		blockAnimation();
		
		MyButtonClickListener listener=new MyButtonClickListener();
		btn_sms_block.setOnClickListener(listener);
		btn_tel_block.setOnClickListener(listener);
		btn_black_white_list.setOnClickListener(listener);
		btn_black_pwd_setting.setOnClickListener(listener);
		
		return view;
	}
	
	/**
	 * 界面动画
	 */
	public void blockAnimation(){
		ScaleAnimation sc_sms_block = new ScaleAnimation(0.2f, 1.0f,
					0.2f,1.0f, 540f,960f);
		sc_sms_block.setDuration(2000);
		layout_sms_block.startAnimation(sc_sms_block);
		
		ScaleAnimation sc_tel_block = new ScaleAnimation(0.2f, 1.0f,
					0.2f, 1.0f, 540f, 960f);
		sc_tel_block.setDuration(2000);
		layout_tel_block.startAnimation(sc_tel_block);

		ScaleAnimation sc_pwd_setting = new ScaleAnimation(0.2f,
					1.0f, 0.2f, 1.0f, 540f, 960f);
		sc_pwd_setting.setDuration(2000);
		layout_pwd_setting.startAnimation(sc_pwd_setting);

		ScaleAnimation sc_list = new ScaleAnimation(0.2f, 1.0f, 0.2f,
					1.0f, 540f, 960f);
		sc_list.setDuration(2000);
		layout_list.startAnimation(sc_list);
		
	}

	
	public class MyButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.btn_sms_block:
				sms_block();
				break;
			case R.id.btn_tel_block:
				tel_block();
				break;
			case R.id.btn_black_white_list:
				startActivity(new Intent(ActionImpl.BLACKWHITELIST_ACTION));
				getActivity().overridePendingTransition(R.anim.set_right_in, R.anim.set_left_out);
				break;
			case R.id.btn_black_pwd_setting:
				block_pwd_setting();
				break;
			default:
				break;
			}
		}


	}
	
	/**
	 * 短信拦截
	 */
	private void sms_block() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new Builder(getActivity());
		if(share.getBlockPwd()==null){
			builder.setTitle(R.string.hint);
			builder.setMessage("您第一次使用骚扰拦截功能,请设置先设置拦截保护密码！");
			builder.setCancelable(false);
			builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					block_pwd_setting();
				}
			});
			builder.show();
		}else{
			builder.setTitle("骚扰拦截密码保护验证");
			builder.setIcon(R.drawable.app_small_icon);
			final EditText et=new EditText(getActivity());
			et.setInputType(InputType.TYPE_CLASS_NUMBER);
			et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); //即限定最大输入字符数为6
			et.setSingleLine(true);
			et.setHint("输入拦截保护密码");
			builder.setView(et);
			builder.setCancelable(false);
			builder.setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					if (TextUtils.isEmpty(et.getText().toString().trim())){
						AlertDialog.Builder b_empty=new Builder(getActivity());
						b_empty.setTitle(R.string.hint);
						b_empty.setCancelable(false);
						b_empty.setMessage("输入密码不能为空,验证失败");
						b_empty.setPositiveButton(R.string.ok, null);
						b_empty.show();
					}else{
						if (!(et.getText().toString().trim().equals(share.getBlockPwd()))){
							AlertDialog.Builder b_mistake=new Builder(getActivity());
							b_mistake.setTitle(R.string.hint);
							b_mistake.setCancelable(false);
							b_mistake.setMessage("密码输入不正确,验证失败！");
							b_mistake.setPositiveButton(R.string.ok, null);
							b_mistake.show();
						}else{
							startActivity(new Intent(ActionImpl.SMSBLOCK_ACTION));
							getActivity().overridePendingTransition(R.anim.set_right_in, R.anim.set_left_out);
						}
					}
				}
			});
			builder.setNegativeButton(R.string.cancel, null);
			builder.show();
		}
	}

	
	/**
	 * 电话拦截
	 */
	private void tel_block() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new Builder(getActivity());
		if(share.getBlockPwd()==null){
			builder.setTitle(R.string.hint);
			builder.setCancelable(false);
			builder.setMessage("您第一次使用骚扰拦截功能,请设置先设置拦截保护密码！");
			builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					block_pwd_setting();
				}
			});
			builder.show();
		}else{
			builder.setTitle("骚扰拦截密码保护验证");
			builder.setIcon(R.drawable.app_small_icon);
			final EditText et=new EditText(getActivity());
			et.setInputType(InputType.TYPE_CLASS_NUMBER);
			et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); //即限定最大输入字符数为6
			et.setSingleLine(true);
			et.setHint("输入保护密码");
			builder.setView(et);
			builder.setCancelable(false);
			builder.setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					if (TextUtils.isEmpty(et.getText().toString().trim())){
						AlertDialog.Builder b_empty=new Builder(getActivity());
						b_empty.setTitle(R.string.hint);
						b_empty.setMessage("输入保护密码不能为空,验证失败");
						b_empty.setCancelable(false);
						b_empty.setPositiveButton(R.string.ok, null);
						b_empty.show();
					}else{
						if (!(et.getText().toString().trim().equals(share.getBlockPwd()))){
							AlertDialog.Builder b_mistake=new Builder(getActivity());
							b_mistake.setTitle(R.string.hint);
							b_mistake.setMessage("密码输入不正确,验证失败！");
							b_mistake.setCancelable(false);
							b_mistake.setPositiveButton(R.string.ok, null);
							b_mistake.show();
						}else{
							startActivity(new Intent(ActionImpl.TELBLOCK_ACTION));
							getActivity().overridePendingTransition(R.anim.set_bottom_in, R.anim.set_top_out);
						}
					}
				}
			});
			builder.setNegativeButton(R.string.cancel, null);
			builder.show();
		}
	}
	
	
	
	/**
	 * 拦截密码设置
	 */
	private void block_pwd_setting() {
		// TODO Auto-generated method stub
		String pwd=share.getBlockPwd();
		if(pwd==null){
			Toast.makeText(getActivity(), "第一次使用拦截，设置拦截密码", Toast.LENGTH_SHORT).show();
			AlertDialog.Builder builder=new Builder(getActivity());
			builder.setTitle("拦截保护密码设置");
			final EditText et=new EditText(getActivity());
			et.setSingleLine(true);	//输入框中数据不换行
			et.setInputType(InputType.TYPE_CLASS_NUMBER);
			et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); //即限定最大输入字符数为6
			builder.setCancelable(false);
			builder.setView(et);
			builder.setNegativeButton(R.string.ok, new AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					if(TextUtils.isEmpty(et.getText().toString().trim())){
						Toast.makeText(getActivity(), "密码输入不能为空", Toast.LENGTH_SHORT).show();
					}else{
						share.setBlockPwd(et.getText().toString().trim());
					}
				}
			});
			builder.setPositiveButton(R.string.cancel, null);
			builder.show();
		}else{
			Toast.makeText(getActivity(), "提示", Toast.LENGTH_SHORT).show();
			AlertDialog.Builder builder=new Builder(getActivity());
			builder.setTitle(R.string.hint);
			builder.setMessage("您已设置拦截密码，是否修改拦截密码");
			builder.setIcon(R.drawable.app_small_icon);
			builder.setCancelable(false);
			builder.setNegativeButton(R.string.ok, new AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder_modify=new Builder(getActivity());
					builder_modify.setTitle("拦截锁密码修改");
					builder_modify.setIcon(R.drawable.app_small_icon);
					View arg=LayoutInflater.from(getActivity()).inflate(R.layout.activity_app_lock_pwd_layout, null, false);
					final EditText et_old_pwd=(EditText) arg.findViewById(R.id.et_old_pwd);
					final EditText et_new_pwd=(EditText) arg.findViewById(R.id.et_new_pwd);
					et_old_pwd.setInputType(InputType.TYPE_CLASS_NUMBER);
					et_new_pwd.setInputType(InputType.TYPE_CLASS_NUMBER);
					et_old_pwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); //即限定最大输入字符数为6
					et_new_pwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); //即限定最大输入字符数为6
					builder_modify.setView(arg);
					builder_modify.setCancelable(false);
					builder_modify.setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							if (TextUtils.isEmpty(et_old_pwd.getText().toString().trim())||TextUtils.isEmpty(et_new_pwd.getText().toString().trim())){
								AlertDialog.Builder b_empty=new Builder(getActivity());
								b_empty.setTitle(R.string.hint);
								b_empty.setMessage("输入拦截锁密码不能为空");
								b_empty.setPositiveButton(R.string.ok, null);
								b_empty.show();
							}else{
								
								if ((et_old_pwd.getText().toString().trim().equals(share.getBlockPwd().trim()))){
									
									share.setBlockPwd(et_new_pwd.getText().toString().trim());		//保存修改的密码
									AlertDialog.Builder b_success=new Builder(getActivity());	
									b_success.setTitle(R.string.hint);
									b_success.setMessage("拦截锁密码修改成功");
									b_success.setCancelable(false);
									b_success.setPositiveButton(R.string.ok, null);
									b_success.show();
									
								}else{
									AlertDialog.Builder b_mistake=new Builder(getActivity());
									b_mistake.setTitle(R.string.hint);
									b_mistake.setMessage("原密码验证错误，修改失败！");
									b_mistake.setPositiveButton(R.string.ok, null);
									b_mistake.show();
								}
							}
						}
					});
					
					builder_modify.setNegativeButton(R.string.cancel, null);
					builder_modify.show();
				}
			});
			builder.setPositiveButton(R.string.cancel, null);
			builder.show();
		}
	}
	
}
