package com.jsmix.android.test.cases.toolbar;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.jsmix.android.test.BaseCasesActivity;
import com.jsmix.android.test.R;

public class ToolbarActivity extends BaseCasesActivity implements OnClickListener {

	public static final String KEY_NAME = "name";

	public static final String KEY_ID = "id";
	
	private int mShowingMenuId = -1;
	
	private JSONArray menus;
	
	private ViewGroup menuContainer;
	
	private ToolbarPopupWindow subMenu;
	
	private void setTestData(){
		InputStream is = null;
		try {
			is = getAssets().open("a.json");
			String testJson = IOUtils.toString(is, "UTF-8");
			menus = new JSONArray(testJson);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		menuContainer = (ViewGroup) findViewById(R.id.menu_container);
		setTestData();
		
		inflateMenu();
	}
	
	private void inflateMenu() {
		try {
			for(int i = 0; i < menus.length(); i++){
				JSONObject menu = menus.getJSONObject(i);
				View menuView = LayoutInflater.from(mContext).inflate(R.layout.toolbar_item, menuContainer, false);
				menuView.setTag(i);
				menuContainer.addView(menuView);
				
				// add divider
				if(i != menus.length() - 1){
					View divider = LayoutInflater.from(mContext).inflate(R.layout.toolbar_divider, menuContainer, false);
					menuContainer.addView(divider);
				}
				
				TextView title = (TextView) menuView.findViewById(R.id.title);
				title.setText(menu.optString(KEY_NAME));
				
				menuView.setOnClickListener(this);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	protected void showToolbarMenu(View anchor, int id) {
		if(id < 0){
			return;
		}
		
		if(subMenu == null){
			subMenu = new ToolbarPopupWindow(this);
			subMenu.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss() {
					System.out.println("dismiss");
				}
			});
			
			mContainer.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mShowingMenuId = -1;
				}
			});

		}
		
		// 当前正在显示
		if(id == mShowingMenuId){
			mShowingMenuId = -1;
			return;
		}
		
		subMenu.show(anchor);
		mShowingMenuId = id;
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_toolbar;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu:
			int id = (Integer) v.getTag();
			System.out.println("click ... id: " +id);
			
			showToolbarMenu(v, id);
			break;

		default:
			break;
		}
	}

}
