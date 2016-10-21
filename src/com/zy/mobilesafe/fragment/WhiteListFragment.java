package com.zy.mobilesafe.fragment;


import java.util.ArrayList;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.bean.Telephony;
import com.zy.mobilesafe.dao.WhiteListDao;
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

public class WhiteListFragment extends Fragment {

	private ListView lv_white_list;
	private Button btn_add_white_list,btn_delete_white;
	
	private ArrayList<String> whiteList=new ArrayList<>();
	private MyListViewAdapter adapter;
	private ArrayList<String> deleteList=new ArrayList<>();
	
	public WhiteListFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initBlackList();
		adapter=new MyListViewAdapter();
		View view=inflater.inflate(R.layout.fragment_white_list_layout, container, false);
		lv_white_list=(ListView) view.findViewById(R.id.lv_white_list);
		btn_add_white_list=(Button) view.findViewById(R.id.btn_add_white_list);
		btn_delete_white=(Button) view.findViewById(R.id.btn_delete_white);
		
		MyButtonClickListener listener=new MyButtonClickListener();
		btn_add_white_list.setOnClickListener(listener);
		btn_delete_white.setOnClickListener(listener);
		lv_white_list=(ListView) view.findViewById(R.id.lv_white_list);
		lv_white_list.setAdapter(adapter);
		return view;
	}
	
	
	public class MyButtonClickListener implements
	android.view.View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.btn_add_white_list:
				add_white_list();
				break;
			case R.id.btn_delete_white:
				delete();
				break;
			default:
				break;
			}
		}

	}

	/**
	 * ��ȡ���ݿ������а�����
	 */
	private void initBlackList() {
		// TODO Auto-generated method stub
		WhiteListDao whiteListDao = new WhiteListDao(getActivity());
		whiteListDao.searchAll(whiteList);
	}

	/**
	 * listview������
	 * 
	 * @author Administrator
	 * 
	 */
	public class MyListViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return whiteList.size() + 1;
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
			TextView tv_white_list_number = (TextView) view
					.findViewById(R.id.tv_black_white_list_number);

			CheckBox cb = (CheckBox) view.findViewById(R.id.cb_check);
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton box, boolean arg1) {
					// TODO Auto-generated method stub
					if (arg1) {
						deleteList.add(whiteList.get(arg0));
					} else {
						deleteList.remove(whiteList.get(arg0));
					}
				}
			});
			if (arg0 >= whiteList.size()) {
				if (whiteList.size() > 0) {
					tv_white_list_number.setText("����" + whiteList.size()
							+ "��������");
				} else {
					tv_white_list_number.setText("û�а�����");
				}
				cb.setVisibility(View.INVISIBLE);
			} else {
				Telephony telephony = new Telephony();
				telephony.setNumber(whiteList.get(arg0));
				if (contacts.isTelNumberInContact(telephony)) {
					tv_white_list_number.setText(whiteList.get(arg0) + "("
							+ telephony.getName() + ")");
				} else {
					tv_white_list_number.setText(whiteList.get(arg0) + "("
							+ getResources().getString(R.string.unknow) + ")");
				}
			}
			return view;
		}

	}

	/**
	 * ���Ӱ�������ť�����¼�
	 * 
	 * @param view
	 */
	private void add_white_list() {
		AlertDialog.Builder builder = new Builder(getActivity());

		builder.setTitle("���Ӱ�����");
		final EditText et = new EditText(getActivity());
		et.setHint("����绰����");
		et.setInputType(InputType.TYPE_CLASS_NUMBER);
		et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)}); //���޶���������ַ���Ϊ11
		et.setTextSize(15f);
		builder.setView(et);
		builder.setPositiveButton(R.string.cancel, null);
		builder.setNegativeButton(R.string.ok, new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				WhiteListDao dao = new WhiteListDao(getActivity());
				if (TextUtils.isEmpty(et.getText().toString().trim())) {
					Toast.makeText(getActivity(), "���������벻��Ϊ��",
							Toast.LENGTH_SHORT).show();
				} else {
					String number = et.getText().toString().trim();
					if (dao.isNumberInWhiteList(number)) {
						Toast.makeText(getActivity(), "�ú������ڰ������У������ظ�����",
								Toast.LENGTH_SHORT).show();
					} else {
						dao.insert(number);
						Toast.makeText(getActivity(), "���Ӱ������ɹ���",
								Toast.LENGTH_SHORT).show();
						whiteList.add(number);
						adapter.notifyDataSetChanged();
					}

				}
			}
		});

		builder.show();
	}

	/**
	 * ɾ��������
	 * 
	 * @param view
	 */
	private void delete() {
		for (int i = 0; i < deleteList.size(); i++) {
			for (int j = 0; j < whiteList.size(); j++) {
				if (deleteList.get(i).equals(whiteList.get(j))) {
					whiteList.remove(deleteList.get(i));
				}
			}
		}
		adapter.notifyDataSetChanged();
		WhiteListDao whiteListDao = new WhiteListDao(getActivity());
		for (int i = 0; i < deleteList.size(); i++) {
			whiteListDao.delete(deleteList.get(i));
		}
	}
}