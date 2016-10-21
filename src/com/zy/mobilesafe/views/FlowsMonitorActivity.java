package com.zy.mobilesafe.views;


import com.zy.mobilesafe.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class FlowsMonitorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flows_monitor_layout);
		
		
		
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_flows, menu);
		return true;
	}
	
}
