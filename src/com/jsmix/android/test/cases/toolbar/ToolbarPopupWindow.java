package com.jsmix.android.test.cases.toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jsmix.android.test.R;

public class ToolbarPopupWindow extends PopupWindow implements AnimationListener {

	private Context mContext;

	private Rect mRect = new Rect();

	private final int[] mLocation = new int[2];

	private int popupGravity = Gravity.NO_GRAVITY;

	private JSONArray mSubMenus = new JSONArray();
	
	private ListView mListView;
	
	private BaseAdapter mAdapter;

	private Animation exitAnimation;

	private Animation enterAnimation;
	
	private OnSubMenuSelectedListener mListener;
	
	@SuppressWarnings("deprecation")
	public ToolbarPopupWindow(Context context) {
		super(context);
		this.mContext = context;
		setFocusable(false);
		setTouchable(true);
		setOutsideTouchable(true);
		setWidth(200);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new BitmapDrawable());
		
		mListView = (ListView) LayoutInflater.from(mContext).inflate(R.layout.toolbar_popup, null);
		mAdapter = new SubMenuAdapter();
		mListView.setAdapter(mAdapter);
		
		exitAnimation = AnimationUtils.loadAnimation(mContext, R.anim.toolbar_exit);
		exitAnimation.setAnimationListener(this);
		enterAnimation = AnimationUtils.loadAnimation(mContext, R.anim.toolbar_enter);
		
		setContentView(mListView);
	}

	public void show(JSONArray submenus, final View view) {
		if(isShowing()){
			super.dismiss();
		}
		mSubMenus = submenus;
		mAdapter.notifyDataSetChanged();
		
		view.getLocationOnScreen(mLocation);
		mRect.set(mLocation[0], mLocation[1], mLocation[0] + view.getWidth(),
				mLocation[1] + view.getHeight());
	
		getContentView().measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		int h = getContentView().getMeasuredHeight() * mAdapter.getCount() +  mAdapter.getCount() - (getContentView().getPaddingBottom() + getContentView().getPaddingTop()) * (mAdapter.getCount() - 1);
		int w = getContentView().getMeasuredWidth();
		w = 200;

		int posX = mRect.left - (w - view.getWidth()) / 2;
		int posY = mRect.top - h;

		
		// 显示弹窗的位置
		showAtLocation(view, popupGravity, posX, posY);
		mListView.startAnimation(enterAnimation);
	}
	
	@Override
	public void dismiss() {
		mListView.startAnimation(exitAnimation);
	}

	@Override
	public void onAnimationStart(Animation animation) {
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		super.dismiss();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		
	}
	
	static class ViewHolder {
		TextView title;
	}
	
	class SubMenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mSubMenus.length();
		}

		@Override
		public Object getItem(int position) {
			return mSubMenus.optJSONObject(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final JSONObject menu = (JSONObject) getItem(position);
			ViewHolder viewHolder = null;
			if(convertView == null){
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(R.layout.toolbar_popup_item, parent, false);
				viewHolder.title = (TextView) convertView.findViewById(R.id.title);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.title.setText(menu.optString(ToolbarActivity.KEY_NAME));
			
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(mListener != null){
						mListener.onSelected(menu.optString(ToolbarActivity.KEY_ID), menu.optString(ToolbarActivity.KEY_NAME));
						dismiss();
					}
				}
			});
			return convertView;
		}
		
	}
	
	public void setOnSubMenuSelectedListener(OnSubMenuSelectedListener listener){
		mListener = listener;
	}

	interface OnSubMenuSelectedListener {
		void onSelected(String id, String name);
	}
	
}
