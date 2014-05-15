package com.jsmix.android.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseCasesActivity extends ActionBarActivity {

	protected ViewGroup mContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_main_case);
		mContainer = (ViewGroup) findViewById(R.id.container);
		View view = LayoutInflater.from(this).inflate(getLayout(), mContainer, true);
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        case android.R.id.home:
	        	finish();
	            return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected abstract int getLayout();
	
}
