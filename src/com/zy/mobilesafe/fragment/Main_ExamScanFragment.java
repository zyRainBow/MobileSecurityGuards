package com.zy.mobilesafe.fragment;


import java.io.File;
import java.util.ArrayList;

import com.zy.mobilesafe.R;
import com.zy.mobilesafe.bean.MyFile;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main_ExamScanFragment extends Fragment {
	
	private View view;
	private LinearLayout layout;
	private Button btn_exam_scan;
	private ProgressBar pb_exam;
	private long sd_used_size;					//sd卡已用空间大小
	private long sd_file_size;					//sd卡文件大小
	private long availableSize;					//sd卡可用空间大小
	private TextView tv_exam;
	private ListView lv_file;
	private Myadapter adapter;
	private ArrayList<MyFile> list=new ArrayList<>();
	private static final int HANDLER_MSG=0X00;
	
	private boolean flag=false;
	
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			switch (msg.what) {
			case HANDLER_MSG:
				Bundle bundle=msg.getData();
				
				long filesize=bundle.getLong("sd_file_size");
				float progress=filesize/(float)sd_used_size;
				tv_exam.setText(getResources().getString(R.string.scanning)+bundle.getString("filename"));
				pb_exam.setProgress((int)(progress*100));
				
				if(flag){
					if(availableSize<1048576L){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						tv_exam.setTextColor(Color.RED);
						tv_exam.setText(getResources().getString(R.string.scan_complete)+"磁盘空间不足·······");
						pb_exam.setProgress(100);
					}else{
						tv_exam.setTextColor(Color.GREEN);
						tv_exam.setText(getResources().getString(R.string.scan_complete)+"磁盘空间充足·······");
					}
				}
				
				adapter.notifyDataSetChanged();
				break;
		
			default:
				break;
			}
		}
	};
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_main_exam_scan, container, false);
		Animation anim=AnimationUtils.loadAnimation(getActivity(), R.anim.exam);
		view.startAnimation(anim);
		layout=(LinearLayout) view.findViewById(R.id.layout_exam);
		btn_exam_scan=(Button) view.findViewById(R.id.btn_exam_scan);
		btn_exam_scan.setOnClickListener(new MyButtonClickListener());
		
		return view;
	}
	
	
	public class MyButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			final MyThread thread=new MyThread();
			btn_exam_scan.setVisibility(View.GONE);
			final View newView=LayoutInflater.from(getActivity()).inflate(R.layout.activity_exam_layout, null, false);
			initSDInfo(newView);
			pb_exam=(ProgressBar) newView.findViewById(R.id.pb_exam);
			tv_exam=(TextView) newView.findViewById(R.id.tv_exam);
			lv_file=(ListView) newView.findViewById(R.id.lv_file);
			Button btn_back_return=(Button) newView.findViewById(R.id.btn_back_return);
			btn_back_return.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					layout.removeView(newView);
					btn_exam_scan.setVisibility(View.VISIBLE);
				}
			});
			sd_file_size=0;
			list.clear();
			layout.addView(newView);
			adapter=new Myadapter();
			lv_file.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			if (Environment.getExternalStorageState().equals(  
                    Environment.MEDIA_MOUNTED)) { 
				thread.start();
            }else{
            	Toast.makeText(getActivity(), "没有找到sd卡", Toast.LENGTH_SHORT).show();
            }

		}


	}

	
	class MyThread extends Thread{
		String ext = ".";  
        File file = Environment.getExternalStorageDirectory();
        public void run() {  
            try {
				scanFile(file, ext);
				
				flag=true;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        } 
	}
	
	
	/**
	 * 初始化sd卡信息
	 * @param newView
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void initSDInfo(View newView) {
		// TODO Auto-generated method stub
		TextView tv_sd_availableBlocks= (TextView) newView.findViewById(R.id.tv_sd_availableBlocks);
		TextView tv_sd_availableSize=(TextView) newView.findViewById(R.id.tv_sd_availableSize);
		TextView tv_sd_block_size=(TextView) newView.findViewById(R.id.tv_sd_block_size);
		TextView tv_sd_blockCount=(TextView) newView.findViewById(R.id.tv_sd_blockCount);
		TextView tv_sd_totalSize=(TextView) newView.findViewById(R.id.tv_sd_totalSize);
		TextView tv_sd_usedSize=(TextView) newView.findViewById(R.id.tv_sd_usedSize);
		
		File data = Environment.getExternalStorageDirectory();		//sd卡存储控件文件对象
		StatFs sf = new StatFs(data.getPath());						//sd卡文件系统状态
		
		long availableBlocks=sf.getAvailableBlocks();	//可用储存块数量
		tv_sd_availableBlocks.setText(availableBlocks+"");
		
		long blockCount=sf.getBlockCount();	//总储存块数
		tv_sd_blockCount.setText(blockCount+"");
		
		long block_size=sf.getBlockSize();	//每个block 占字节数
		tv_sd_block_size.setText(fileSize(block_size));
		
		availableSize=availableBlocks*block_size;	//sd卡可用大小
		String Byte=getResources().getString(R.string.Byte);
		tv_sd_availableSize.setText(fileSize(availableSize)+"("+availableSize+Byte+")");
		
		long totalSize=blockCount*block_size;		//sd卡总的大小
		tv_sd_totalSize.setText(fileSize(totalSize)+"("+totalSize+Byte+")");
		
		sd_used_size=totalSize-availableSize;	//sd卡已用大小
		tv_sd_usedSize.setText(fileSize(sd_used_size)+"("+sd_used_size+Byte+")");
	}

	
	
	/**
	 * 文件扫描
	 * @param file
	 * @param ext(分隔符)
	 * @throws InterruptedException 
	 */
	private void scanFile(File file, String ext) throws InterruptedException {  
        if (file != null) {  
            if (file.isDirectory()) {  
                File[] listFile = file.listFiles();  
                if (listFile != null) {  
                    for (int i = 0; i < listFile.length; i++) {  
                        scanFile(listFile[i], ext);  
                    } 
                    
                }  
            } else {  
                String filename = file.getAbsolutePath(); 
                Thread.sleep(600);
                if(filename.contains(ext)) {
					MyFile myFile = new MyFile(filename, file.length());
					sd_file_size+=file.length();
					list.add(myFile);

					Message msg = Message.obtain(); // 消息实例
					Bundle data = new Bundle();
					data.putString("filename", filename);
					data.putLong("sd_file_size", sd_file_size);
					msg.setData(data);
					msg.what = HANDLER_MSG;
					handler.sendMessage(msg);
					
                }     
            }  
        }  
    } 
	
	
	/**
	 * 适配器
	 * @author Administrator
	 *
	 */
	public class Myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size()+1;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
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
				view=LayoutInflater.from(getActivity()).inflate(R.layout.activity_file_listitem, arg2, false);
				holder=new Myholder(view);
				view.setTag(holder);
			}else{
				holder=(Myholder) view.getTag();
			}
			TextView tv_file_name=holder.getTv_file_name();
			TextView tv_file_size=holder.getTv_file_size();
			if(arg0<list.size()){
				tv_file_name.setText(list.get(arg0).getFileName());
				tv_file_size.setText(fileSize(list.get(arg0).getFileSize()));
			}else{
				if(list.size()==0){
					tv_file_name.setText("sd卡中没有文件");
					tv_file_size.setText("");
				}else{
					tv_file_name.setText("sd卡中共扫描到"+list.size()+"个文件");
					tv_file_size.setText(fileSize(sd_file_size));
					
				}
			}
			return view;
		}

	}
	
	/**
	 * 计算文件大小
	 * @param size
	 * @return
	 */
	public String fileSize(long size){
		String str="";
		if(size<1024L){
			str= size+"b";
		}else if(size<1048576L){
			str= String.format("%.3f",size/1024.0)+"kb";
		}else if(size<1073741824L){
			str= String.format("%.3f",size/1048576.0)+"M";
		}else if(size<1099511627776L){
			str=String.format("%.3f",size/1024/1024/1024.0)+"G";
		}
		return str;
	}

	/**
	 * listview持有者类
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
