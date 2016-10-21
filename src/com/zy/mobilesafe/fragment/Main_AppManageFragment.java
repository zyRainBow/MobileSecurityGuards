package com.zy.mobilesafe.fragment;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.Impl.ActionImpl;
import com.zy.mobilesafe.utils.MySharedPreference;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Main_AppManageFragment extends Fragment {
	
	private LinearLayout layout_app_uninstall;
	private LinearLayout layout_apk_manage;
	private LinearLayout layout_app_lock;
	private LinearLayout layout_prevent_thelf;
	
	private Button btn_app_lock;
	private Button btn_app_uninstall;
	private Button btn_apk_manage;
	private Button btn_prevent_thelf;
	
	private MySharedPreference share;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		share=new MySharedPreference(getActivity());
		
		View view=inflater.inflate(R.layout.fragment_main_app_manage, container, false);
		
		btn_app_lock=(Button) view.findViewById(R.id.btn_app_lock);
		btn_app_uninstall=(Button) view.findViewById(R.id.btn_app_uninstall);
		btn_apk_manage=(Button) view.findViewById(R.id.btn_apk_manage);
		btn_prevent_thelf=(Button) view.findViewById(R.id.btn_prevent_thelf);

		layout_apk_manage=(LinearLayout) view.findViewById(R.id.layout_apk_manage);
		layout_app_uninstall=(LinearLayout) view.findViewById(R.id.layout_app_uninstall);
		layout_app_lock=(LinearLayout) view.findViewById(R.id.layout_app_lock);
		layout_prevent_thelf=(LinearLayout) view.findViewById(R.id.layout_prevent_thelf);

		animation();
		
		MyButtonClickLIstener listener=new MyButtonClickLIstener();
		btn_apk_manage.setOnClickListener(listener);
		btn_app_lock.setOnClickListener(listener);
		btn_app_uninstall.setOnClickListener(listener);
		btn_prevent_thelf.setOnClickListener(listener);
		
		return view;
	}
	
	/**
	 * 各个按钮出现时的动画
	 */
	public void animation(){
		RotateAnimation ra_apk_manage = new RotateAnimation(0, // 起始角度
				360, // 终止角度
				Animation.RELATIVE_TO_SELF, // 旋转中心横坐标的参照方式
				0f, // 旋转中心横坐标的值
				Animation.RELATIVE_TO_SELF, // 旋转中心纵坐标的参照方式
				1f); // 旋转中心纵坐标的值
		ra_apk_manage.setDuration(3000);
		layout_apk_manage.startAnimation(ra_apk_manage);
		
		RotateAnimation ra_app_lock = new RotateAnimation(0, // 起始角度
				360, // 终止角度
				Animation.RELATIVE_TO_SELF, // 旋转中心横坐标的参照方式
				1f, // 旋转中心横坐标的值
				Animation.RELATIVE_TO_SELF, // 旋转中心纵坐标的参照方式
				0f); // 旋转中心纵坐标的值
		ra_app_lock.setDuration(3000);
		layout_app_lock.startAnimation(ra_app_lock);
		
		RotateAnimation ra_app_uninstall = new RotateAnimation(0, // 起始角度
				360, // 终止角度
				Animation.RELATIVE_TO_SELF, // 旋转中心横坐标的参照方式
				1f, // 旋转中心横坐标的值
				Animation.RELATIVE_TO_SELF, // 旋转中心纵坐标的参照方式
				1f); // 旋转中心纵坐标的值
		ra_app_uninstall.setDuration(3000);
		layout_app_uninstall.startAnimation(ra_app_uninstall);
		
		RotateAnimation ra_prevent_thelf = new RotateAnimation(0, // 起始角度
				360, // 终止角度
				Animation.RELATIVE_TO_SELF, // 旋转中心横坐标的参照方式
				0f, // 旋转中心横坐标的值
				Animation.RELATIVE_TO_SELF, // 旋转中心纵坐标的参照方式
				0f); // 旋转中心纵坐标的值
		ra_prevent_thelf.setDuration(3000);
		layout_prevent_thelf.startAnimation(ra_prevent_thelf);
	}
	
	/**
	 * 按钮单击事件监听
	 * @author Administrator
	 *
	 */
	public class MyButtonClickLIstener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.btn_apk_manage:
				startActivity(new Intent(ActionImpl.APKMANAGE_ACTION));
				break;
			case R.id.btn_app_lock:
				startActivity(new Intent(ActionImpl.APPLOCK_ACTION));
				break;
			case R.id.btn_app_uninstall:
				startActivity(new Intent(ActionImpl.APPUNINSTALL_ACTION));
				break;
			case R.id.btn_prevent_thelf:
				prevent_thelf();
				break;
			default:
				break;
			}
		}

	}
	
	
	
	
	
	/**
	 * 手机防盗
	 */
	private void prevent_thelf() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new Builder(getActivity());
		if (share.getPreventThelfPwd()==null){
			builder.setTitle(R.string.hint);
			builder.setMessage("您是第一次使用手机防盗功能请先设置防盗保护密码");
			builder.setCancelable(false);
			builder.setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					setting_prevent_pwd();
				}
			});
			builder.setNegativeButton(R.string.cancel, null);
			builder.show();
		}else{
			builder.setTitle(R.string.hint);
			builder.setMessage("本机已设置手机防盗保护密码，进行防盗验证还是修改密码？");
			builder.setCancelable(false);
			builder.setPositiveButton("防盗验证", new AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					prevent_pwd_verify();
				}
			});
			builder.setNeutralButton("修改密码", new AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					modify_pwd();
				}
			});
			builder.setNegativeButton(R.string.cancel, null);
			builder.show();
			
		}
		
	}
	
	/**
	 * 修改防盗密码
	 */
	private void modify_pwd() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new Builder(getActivity());
		builder.setTitle("防盗密码修改");
		builder.setIcon(R.drawable.app_small_icon);
		View arg=LayoutInflater.from(getActivity()).inflate(R.layout.activity_app_lock_pwd_layout, null, false);
		final EditText et_old_pwd=(EditText) arg.findViewById(R.id.et_old_pwd);
		final EditText et_new_pwd=(EditText) arg.findViewById(R.id.et_new_pwd);
		builder.setView(arg);
		builder.setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(et_old_pwd.getText().toString().trim())||TextUtils.isEmpty(et_new_pwd.getText().toString().trim())){
					AlertDialog.Builder b_empty=new Builder(getActivity());
					b_empty.setTitle(R.string.hint);
					b_empty.setMessage("输入防盗密码不能为空");
					b_empty.setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							modify_pwd();
						}
					});
					b_empty.show();
				}else{
					if (share.getPreventThelfPwd().equals(et_old_pwd.getText().toString().trim())){
						share.setPreventThelfPwd(et_new_pwd.getText().toString().trim());		//保存修改的密码
						AlertDialog.Builder b_success=new Builder(getActivity());	
						b_success.setTitle(R.string.hint);
						b_success.setMessage("防盗密码修改成功");
						b_success.setPositiveButton(R.string.ok, null);
						b_success.show();
					}else{
						AlertDialog.Builder b_mistake=new Builder(getActivity());
						b_mistake.setTitle(R.string.hint);
						b_mistake.setMessage("原密码输入错误，修改失败！");
						b_mistake.setPositiveButton(R.string.ok, null);
						b_mistake.show();
					}
				}
			}
		});
		
		builder.setNegativeButton(R.string.cancel, null);
		builder.show();
	}
	
	/**
	 * 防盗密码保护验证
	 * @return
	 */
	public void prevent_pwd_verify(){
		AlertDialog.Builder builder=new Builder(getActivity());
		builder.setTitle("手机防盗功能密码保护验证");
		builder.setIcon(R.drawable.app_small_icon);
		final EditText et = new EditText(getActivity());
		et.setSingleLine(true);
		et.setHint("输入防盗保护密码");
		builder.setView(et);
		builder.setCancelable(false);		//对话框之外的无效
		builder.setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(et.getText().toString().trim())) {
					Toast.makeText(getActivity(), "密码不能为空",Toast.LENGTH_SHORT).show();
					prevent_pwd_verify();
				} else {
					if(share.getPreventThelfPwd().equals(et.getText().toString().trim())){
						isOpenPreventThelf();
					}else{
						Toast.makeText(getActivity(), "防盗保护密码错误，验证失败", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		builder.setNegativeButton(R.string.cancel, null);
		builder.show();
	}
	
	
	/**
	 * 是否开启防盗
	 */
	private void  isOpenPreventThelf(){
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new Builder(getActivity());
		builder.setTitle(R.string.hint);
		builder.setCancelable(false);
		if(share.getIsPreventThelf()){
			builder.setMessage("通过防盗密码保护验证\n您已开启手机防盗功能，是否关闭手机防盗！\n为了安全建议开启防盗功能···");
			builder.setCancelable(false);
			builder.setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					share.setIsPreventThelf(false);
					Toast.makeText(getActivity(), "防盗功能已成功关闭", Toast.LENGTH_SHORT).show();
				}
			});
			builder.setNegativeButton(R.string.cancel, null);
			builder.show();
		}else{
			builder.setMessage("通过防盗密码保护验证\n您还没有开启手机防盗功能，是否开启手机防盗！");
			builder.setPositiveButton(R.string.yes, new AlertDialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					share.setIsPreventThelf(true);
					setting_prevent_number();
				}

				
			});
			builder.setNegativeButton(R.string.no, null);
			builder.show();
		}
	}
	
	
	/**
	 * 设置防盗号码
	 */
	private void setting_prevent_number() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new Builder(getActivity());
		builder.setTitle("手机防盗保护号码设置");
		builder.setIcon(R.drawable.app_small_icon);
		final EditText et = new EditText(getActivity());
		et.setSingleLine(true);
		et.setInputType(EditorInfo.TYPE_CLASS_PHONE);	//类型
		et.setHint("输入防盗号码");
		builder.setView(et);
		builder.setCancelable(false);		//对话框之外的无效
		builder.setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(et.getText().toString().trim())) {
					Toast.makeText(getActivity(), "输入防盗号码不能为空",Toast.LENGTH_SHORT).show();
					setting_prevent_number();
				} else {
					share.setPreventThelfNumber(et.getText().toString().trim());
					AlertDialog.Builder b_success = new Builder(
							getActivity());
					b_success.setTitle(R.string.hint);
					b_success.setCancelable(false);
					b_success.setCancelable(false);
					b_success.setMessage("防盗号码设置成功,成功开启手机防盗");
					b_success.setPositiveButton(R.string.ok, null);
					b_success.show();
				}
			}
		});
		builder.setNegativeButton(R.string.cancel, null);
		builder.show();
		
	}
	
	
	/**
	 * 设置防盗密码
	 * @param view
	 */
	private void setting_prevent_pwd(){
		AlertDialog.Builder builder=new Builder(getActivity());

		builder.setTitle("手机防盗保护密码设置");
		builder.setIcon(R.drawable.app_small_icon);
		final EditText et = new EditText(getActivity());
		et.setSingleLine(true);
		et.setHint("输入防盗保护密码");
		builder.setView(et);
		builder.setCancelable(false);		//对话框之外的无效
		builder.setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(et.getText().toString().trim())) {
					Toast.makeText(getActivity(), "输入防盗密码不能为空",Toast.LENGTH_SHORT).show();
					setting_prevent_pwd();
				} else {
					share.setPreventThelfPwd(et.getText().toString().trim());
					AlertDialog.Builder b_success = new Builder(
							getActivity());
					b_success.setTitle(R.string.hint);
					b_success.setCancelable(false);
					b_success.setCancelable(false);
					b_success.setMessage("防盗密码设置成功,开启手机防盗");
					b_success.setPositiveButton(R.string.ok, null);
					b_success.show();
				}
			}
		});
		builder.setNegativeButton(R.string.cancel, null);
		builder.show();
	}
}
