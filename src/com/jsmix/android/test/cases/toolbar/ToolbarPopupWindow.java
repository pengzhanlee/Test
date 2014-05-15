package com.jsmix.android.test.cases.toolbar;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jsmix.android.test.R;

public class ToolbarPopupWindow extends PopupWindow {

	private Context mContext;

	private Rect mRect = new Rect();

	private final int[] mLocation = new int[2];

	private int popupGravity = Gravity.NO_GRAVITY;

	LinearLayout mLinearLayout;

	public ToolbarPopupWindow(Context context) {
		super(context);
		this.mContext = context;
		setFocusable(true);
		setTouchable(true);
		setOutsideTouchable(true);
		setAnimationStyle(R.style.AnimationToolbar);
		
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new BitmapDrawable());
		mLinearLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.toolbar_popup, null);
	}

	public void show(View view) {
		view.getLocationOnScreen(mLocation);
		mRect.set(mLocation[0], mLocation[1], mLocation[0] + view.getWidth(),
				mLocation[1] + view.getHeight());

		setContentView(mLinearLayout);

		getContentView().measure(0, 0);
		int h = getContentView().getMeasuredHeight();
		
		int posX = mRect.left;
		int posY = mRect.top - h;
		
		// 显示弹窗的位置
		showAtLocation(view, popupGravity, posX, posY);
	}
	
}
