package com.jsmix.android.test.cases.iphonedialog;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsmix.android.test.BaseCasesActivity;
import com.jsmix.android.test.R;

public class IphoneDialogActivity extends BaseCasesActivity {
	
	class ShareItem {
		String id;
		ShareItem(String id) {
			this.id = id;
		}
	}

	private ArrayList<ShareItem> shareItems = new ArrayList<IphoneDialogActivity.ShareItem>();
	
	{
		// test code
		shareItems.add(new ShareItem("wechat"));
		shareItems.add(new ShareItem("link"));
		shareItems.add(new ShareItem("moment"));
		shareItems.add(new ShareItem("weibo"));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		findViewById(R.id.share).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog();
			}
		});
	}
	
	@Override
	protected int getLayout() {
		return R.layout.activity_share;
	}
	
	class GridViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return shareItems.size();
		}

		@Override
		public Object getItem(int position) {
			return shareItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ShareItem shareItem = (ShareItem) getItem(position);
			final String shareItemId = shareItem.id;
			
			convertView = LayoutInflater.from(IphoneDialogActivity.this).inflate(R.layout.share_item, parent, false);
			
			View bg = convertView.findViewById(R.id.bg);
			bg.setBackgroundResource(ShareConfig.getBgRes(shareItemId));
			
			ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
			icon.setImageResource(ShareConfig.getIconRes(shareItemId));
			
			TextView textView = (TextView) convertView.findViewById(R.id.text);
			textView.setText(ShareConfig.getName(shareItemId));
			
			
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO
					// trigger id
				}
			});
			return convertView;
		}
		
	}

	public void showDialog() {
		final Dialog dialog = new Dialog(this, R.style.IphoneDialog);
		ViewGroup dialogView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
		GridView gridView = (GridView) dialogView.findViewById(R.id.share_item_container);
		gridView.setAdapter(new GridViewAdapter());
		
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
			}
		});
	
		
		
		dialog.setContentView(dialogView);
		dialog.show();
		
		OnClickListener clickToDissmissListener = new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
			
		};
		
		dialog.findViewById(R.id.mask).setOnClickListener(clickToDissmissListener);
		dialog.findViewById(R.id.cancel).setOnClickListener(clickToDissmissListener);

	}
	
}
