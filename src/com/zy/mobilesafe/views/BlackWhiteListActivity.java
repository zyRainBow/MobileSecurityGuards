package com.zy.mobilesafe.views;


import com.zy.mobilesafe.R;
import com.zy.mobilesafe.fragment.BlackListFragment;
import com.zy.mobilesafe.fragment.WhiteListFragment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class BlackWhiteListActivity extends FragmentActivity {

	private ViewPager vp_black_white_list;
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blackwhitelist_layout);
		
		vp_black_white_list=(ViewPager) findViewById(R.id.vp_black_white_list);
		actionBar=getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		MyTabListener listener=new MyTabListener();
		actionBar.addTab(actionBar.newTab().setText(R.string.black_list).setTabListener(listener));
		actionBar.addTab(actionBar.newTab().setText(R.string.white_list).setTabListener(listener));
		vp_black_white_list.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		vp_black_white_list.setOnPageChangeListener(new MyViewPagerChangedListener());
	}
	
	
	/**
	 * tab¸Ä±äÊÊÅäÆ÷
	 * @author Administrator
	 *
	 */
	public class MyTabListener implements TabListener {

		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			vp_black_white_list.setCurrentItem(arg0.getPosition());
		}

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub

		}

	}
	
	
	/**
	 * viewpagerÊÊÅäÆ÷
	 * @author Administrator
	 *
	 */
	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Fragment fragment=new Fragment();
			if (arg0==0){
				fragment=new BlackListFragment();
			}else{
				fragment=new WhiteListFragment();
			}
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}

	}
	
	
	
	/**
	 * viewpager¸Ä±ä¼àÌýÆ÷
	 * @author Administrator
	 *
	 */
	public class MyViewPagerChangedListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			actionBar.setSelectedNavigationItem(arg0);
		}

	}
	
}
