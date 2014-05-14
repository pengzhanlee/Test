package com.jsmix.android.test.cases;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.jsmix.android.test.BaseCasesActivity;
import com.jsmix.android.test.R;

public class IphoneDialogActivity extends BaseCasesActivity {

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

	public void showDialog() {
		final Dialog dialog = new Dialog(this, R.style.IphoneDialog);
		final LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.dialog_share, null);

		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
			}
		});

		dialog.setContentView(view);
		dialog.show();
		
		
		
		dialog.findViewById(R.id.cancel).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
			
		});


		dialog.findViewById(R.id.share_to_weixin).setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
			}
			
		});
		
		dialog.findViewById(R.id.share_to_friend).setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
			}
			
		});
		
		dialog.findViewById(R.id.share_to_weibo).setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
			}
			
		});
		
		dialog.findViewById(R.id.share_copy_link).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			}
			
		});
	}
	
}
