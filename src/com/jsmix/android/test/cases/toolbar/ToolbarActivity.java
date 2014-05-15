package com.jsmix.android.test.cases.toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.jsmix.android.test.BaseCasesActivity;
import com.jsmix.android.test.R;

public class ToolbarActivity extends BaseCasesActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		findViewById(R.id.test).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showToolbarMenu(v);
			}
		});
	}
	
	protected void showToolbarMenu(View anchor) {
		
		ToolbarPopupWindow pw = new ToolbarPopupWindow(this);
		pw.show(anchor);

	}

	@Override
	protected int getLayout() {
		return R.layout.activity_toolbar;
	}

}
