package com.jsmix.android.test;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.jsmix.android.test.R;

public class MainActivity extends Activity implements OnItemClickListener {

	public static final String CASES_PACKAGE_NAME = ".cases";
	
	private Context mContext;
	
	private List<ActivityInfo> mActivityInfos = new ArrayList<ActivityInfo>();
	
	private ListView mListView;
	
	private BaseAdapter mAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.listview);
		fillActivityInfos();
		mAdapter = new ListViewAdapter();
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	public void fillActivityInfos() {
		try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
			ActivityInfo[] uncheckedActivityInfos = packageInfo.activities;
			for(ActivityInfo activityInfo : uncheckedActivityInfos){
				if(activityInfo.name.contains(CASES_PACKAGE_NAME)){
					mActivityInfos.add(activityInfo);
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	static class ViewHolder {
		TextView title;
	}
	
	class ListViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mActivityInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return mActivityInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			ActivityInfo activityInfo = (ActivityInfo) getItem(position);
			if(convertView == null){
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, parent, false);
				viewHolder.title = (TextView) convertView.findViewById(R.id.title);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			try {
				viewHolder.title.setText(activityInfo.labelRes);
			} catch (Exception e) {
				viewHolder.title.setText(activityInfo.name);
			}
			
			return convertView;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try {
			ActivityInfo activityInfo = (ActivityInfo) parent.getAdapter().getItem(position);
			Class<?> clazz = Class.forName(activityInfo.name);
			Intent intent = new Intent();
			intent.setClass(mContext, clazz);
			startActivity(intent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
