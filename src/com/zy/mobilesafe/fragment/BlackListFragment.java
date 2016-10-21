package com.zy.mobilesafe.fragment;

import java.util.ArrayList;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.bean.Telephony;
import com.zy.mobilesafe.dao.BlackListDao;
import com.zy.mobilesafe.utils.MyContacts;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class BlackListFragment extends Fragment {
	
	private ListView lv_black_list;
	private Button btn_add_black_list,btn_delete_black;
	private ArrayList<String> blackList=new ArrayList<>();
	private MyListViewAdapter adapter;
	private ArrayList<String> deleteList=new ArrayList<>();
	
	public BlackListFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		initBlackList();
		adapter=new MyListViewAdapter();
		
		View view=inflater.inflate(R.layout.fragment_black_list_layout, container, false);
		lv_black_list=(ListView) view.findViewById(R.id.lv_black_list);
		btn_add_black_list=(Button) view.findViewById(R.id.btn_add_black_list);
		btn_delete_black=(Button) view.findViewById(R.id.btn_delete_black);
		
		MyButtonClickListener listener=new MyButtonClickListener();
		btn_add_black_list.setOnClickListener(listener);
		btn_delete_black.setOnClickListener(listener);
		lv_black_list.setAdapter(adapter);
		return view;
	}
	
	public class MyButtonClickListener implements
			android.view.View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.btn_add_black_list:
				add_black_list();
				break;
			case R.id.btn_delete_black:
				delete();
				break;
			default:
				break;
			}
		}

	}

	/**
	 * 获取数据库中所有黑名单
	 */
	private void initBlackList() {
		// TODO Auto-generated method stub
		BlackListDao blackListDao = new BlackListDao(getActivity());
		blackListDao.searchAll(blackList);
	}

	/**
	 * listview适配器
	 * 
	 * @author Administrator
	 * 
	 */
	public class MyListViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return blackList.size() + 1;
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
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.fragment_black_white_listitem, arg2, false);
			MyContacts contacts = new MyContacts(getActivity());
			TextView tv_black_list_number = (TextView) view
					.findViewById(R.id.tv_black_white_list_number);

			CheckBox cb = (CheckBox) view.findViewById(R.id.cb_check);
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton box, boolean arg1) {
					// TODO Auto-generated method stub
					if (arg1) {
						deleteList.add(blackList.get(arg0));
					} else {
						deleteList.remove(blackList.get(arg0));
					}
				}
			});

			if (arg0 >= blackList.size()) {
				if (blackList.size() > 0) {
					tv_black_list_number.setText("共有" + blackList.size()
							+ "条黑名单");
				} else {
					tv_black_list_number.setText("没有黑名单");
				}
				cb.setVisibility(View.INVISIBLE);
			} else {
				Telephony telephony = new Telephony();
				telephony.setNumber(blackList.get(arg0));
				if (contacts.isTelNumberInContact(telephony)) {
					tv_black_list_number.setText(blackList.get(arg0) + "("
							+ telephony.getName() + ")");
				} else {
					tv_black_list_number.setText(blackList.get(arg0) + "("
							+ getResources().getString(R.string.unknow) + ")");
				}
			}
			return view;
		}

	}

	/**
	 * 添加黑名单按钮单击事件
	 * 
	 * @param view
	 */
	private void add_black_list() {
		AlertDialog.Builder builder = new Builder(getActivity());

		builder.setTitle("添加黑名单");
		final EditText et = new EditText(getActivity());
		et.setHint("输入电话号码");
		et.setInputType(InputType.TYPE_CLASS_NUMBER);
		et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)}); //即限定最大输入字符数为11
		et.setTextSize(15f);
		builder.setView(et);
		builder.setPositiveButton(R.string.cancel, null);
		builder.setNegativeButton(R.string.ok, new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				BlackListDao dao = new BlackListDao(getActivity());
				if (TextUtils.isEmpty(et.getText().toString().trim())) {
					Toast.makeText(getActivity(), "黑名单号码不能为空",
							Toast.LENGTH_SHORT).show();
				} else {
					String number = et.getText().toString().trim();
					if (dao.isNumberInBlackList(number)) {
						Toast.makeText(getActivity(), "该号码已在黑名单中，不能重复添加",
								Toast.LENGTH_SHORT).show();
					} else {
						dao.insert(number);
						Toast.makeText(getActivity(), "添加黑名单成功！",
								Toast.LENGTH_LONG).show();
						blackList.add(number);
						adapter.notifyDataSetChanged();
					}

				}
			}
		});

		builder.show();
	}

	/**
	 * 删除
	 */
	private void delete() {
		// TODO Auto-generated method stub
		for (int i = 0; i < deleteList.size(); i++) {
			for (int j = 0; j < blackList.size(); j++) {
				if (deleteList.get(i).equals(blackList.get(j))) {
					blackList.remove(deleteList.get(i));
				}
			}
		}
		adapter.notifyDataSetChanged();
		BlackListDao blackListDao = new BlackListDao(getActivity());
		for (int i = 0; i < deleteList.size(); i++) {
			blackListDao.delete(deleteList.get(i));
		}
		Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_LONG).show();
	}

}
