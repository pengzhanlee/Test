package com.jsmix.android.test.cases.keyboard;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.jsmix.android.test.BaseCasesActivity;
import com.jsmix.android.test.R;

public class KeyboradActivity extends BaseCasesActivity implements OnGlobalLayoutListener {

	private View container;

	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		container = findViewById(R.id.container);
		textView = (TextView) findViewById(R.id.info);
		
		findViewById(R.id.show).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				show();
			}
		});

		findViewById(R.id.hide).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hide();
			}
		});
		
		container.getRootView().getViewTreeObserver().addOnGlobalLayoutListener(this);
	}
	
	@Override
	protected int getLayout() {
		return R.layout.activity_keyborad;
	}

	public void show() {
		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
				.toggleSoftInput(InputMethodManager.SHOW_FORCED,
						InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	public void hide() {
		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(container.getWindowToken(), 0);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		hide();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	public void onGlobalLayout() {
		Rect r = new Rect();
        container.getWindowVisibleDisplayFrame(r);
        int screenHeight = container.getRootView().getHeight() - r.top;
        int heightDifference = screenHeight - (r.bottom - r.top);
        textView.setText("键盘高度： " + heightDifference);
	}
	
}
