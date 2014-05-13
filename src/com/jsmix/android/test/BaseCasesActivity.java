package com.jsmix.android.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseCasesActivity extends Activity {

	private ViewGroup container;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_case);
		container = (ViewGroup) findViewById(R.id.container);
		
		View view = LayoutInflater.from(this).inflate(getLayout(), container, true);
	}
	
	protected abstract int getLayout();
	
}
