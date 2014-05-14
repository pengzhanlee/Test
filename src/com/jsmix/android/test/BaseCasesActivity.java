package com.jsmix.android.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseCasesActivity extends Activity {

	protected ViewGroup mContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_case);
		mContainer = (ViewGroup) findViewById(R.id.container);
		
		View view = LayoutInflater.from(this).inflate(getLayout(), mContainer, true);
	}
	
	protected abstract int getLayout();
	
}
