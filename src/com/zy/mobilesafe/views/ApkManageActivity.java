package com.zy.mobilesafe.views;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.fragment.Main_ExamScanFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ApkManageActivity extends Activity {

	private Context context;
	private List<File> myFiles = new ArrayList<File>();
	private Myadapter adapter;

	private ListView list;
	private Button btn_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_apk_manage_layout);
		
		list=(ListView)findViewById(R.id.lv_apk_list);
		
		adapter=new Myadapter();
		
		btn_back=(Button)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		final File file1 = Environment.getExternalStorageDirectory();
		FindAllAPKFile(file1);
		
		list.setAdapter(adapter);
	
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder=new Builder(ApkManageActivity.this);
				builder.setTitle(R.string.hint);
				builder.setPositiveButton("��װ",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						install(file1,myFiles.get(position).getName());
					}
				});
				builder.setNeutralButton("ɾ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						deleteFile(myFiles.get(position));
						
						myFiles.clear();
						FindAllAPKFile(file1);
						adapter.notifyDataSetChanged();
					}
				});
				builder.show();
				}
			});
	}
	
	
	 /**
     * ��װӦ�ó���
     */
    private void install(File sdCardDir,String Appname) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "�����SD��", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(sdCardDir, Appname);
        if (!file.exists()) {
            Toast.makeText(this, "�ļ�δ�ҵ�", Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
    }
	
    /**
     * ɾ��ָ���ļ�
     * @param file
     */
	public void deleteFile(File file) {
		if (file.exists()) { // �ж��ļ��Ƿ����
			if (file.isFile()) { // �ж��Ƿ����ļ�
				file.delete(); 
			} else if (file.isDirectory()) { // �����������һ��Ŀ¼
				File files[] = file.listFiles(); // ����Ŀ¼�����е��ļ� files[];
				for (int i = 0; i < files.length; i++) { // ����Ŀ¼�����е��ļ�
					this.deleteFile(files[i]); // ��ÿ���ļ� ������������е���
				}
			}
			file.delete();
			Toast.makeText(getApplicationContext(), "�ļ���ɾ��", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "�ļ�������", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	/**
	 * @param args
	 *            ���õݹ��˼�룬�ݹ�ȥ��ÿ��Ŀ¼�����apk�ļ�
	 */
	public void FindAllAPKFile(File file) {

		// �ֻ��ϵ��ļ�,Ŀǰֻ�ж�SD���ϵ�APK�ļ�
		// file = Environment.getDataDirectory();
		// SD���ϵ��ļ�Ŀ¼
		if (file.isFile()) {
			String name_s = file.getName();
			if (name_s.toLowerCase().endsWith(".apk")) {
				myFiles.add(file);
			}
		} else {
			File[] files = file.listFiles();
			if (files != null && files.length > 0) {
				for (File file_str : files) {
					FindAllAPKFile(file_str);
				}
			}
		}
	}


	
	/**
	 * listview������
	 * @author Administrator
	 *
	 */
	public class Myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return myFiles.size();
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
			View view=arg1;
			Myholder holder=null;
			if(view==null){
				view=LayoutInflater.from(ApkManageActivity.this).inflate(R.layout.activity_file_listitem, arg2, false);
				holder=new Myholder(view);
				view.setTag(holder);
			}else{
				holder=(Myholder) view.getTag();
			}
			TextView tv_file_name=holder.getTv_file_name();
			TextView tv_file_size=holder.getTv_file_size();
			if(arg0<myFiles.size()){
				tv_file_name.setText(myFiles.get(arg0).getName());
				tv_file_size.setText(new Main_ExamScanFragment().fileSize(myFiles.get(arg0).length()));
				
			}
			else{
				if(myFiles.size()==0){
					tv_file_name.setText("sd����û���ļ�");
					tv_file_size.setText("");
				}else{
					tv_file_name.setText("sd���й�ɨ�赽"+myFiles.size()+"���ļ�");
				}
			}
			
			return view;
		}
	}
	
	
	/**
	 * listview��������
	 * @author Administrator
	 *
	 */
	class Myholder{
		private View view;
		private TextView tv_file_name;
		private TextView tv_file_size;
		
		public Myholder(View view) {
			super();
			this.view = view;
		}

		public TextView getTv_file_name() {
			if(tv_file_name==null){
				tv_file_name=(TextView) view.findViewById(R.id.tv_file_name);
			}
			return tv_file_name;
		}

		public TextView getTv_file_size() {
			if(tv_file_size==null){
				tv_file_size=(TextView) view.findViewById(R.id.tv_file_size);
			}
			return tv_file_size;
		}	
	
	}
	
}
