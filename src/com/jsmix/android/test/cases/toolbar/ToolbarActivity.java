package com.jsmix.android.test.cases.toolbar;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.jsmix.android.test.BaseCasesActivity;
import com.jsmix.android.test.R;

public class ToolbarActivity extends BaseCasesActivity implements OnClickListener {

	public static final String KEY_NAME = "name";

	public static final String KEY_ID = "id";

	public static final String KEY_SUB_MENU = "sub";
	
	private int mShowingMenuIndex = -1;
	
	private JSONArray mMenus;
	
	private ViewGroup mMenuContainer;
	
	private ToolbarPopupWindow mSubMenu;
	
	private void setTestData(){
		InputStream is = null;
		try {
			is = getAssets().open("a.json");
			String testJson = IOUtils.toString(is, "UTF-8");
			mMenus = new JSONArray(testJson);
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
		mMenuContainer = (ViewGroup) findViewById(R.id.menu_container);
		setTestData();
		
		inflateMenu();
	}
	
	private void inflateMenu() {
		try {
			for(int index = 0; index < mMenus.length(); index++){
				JSONObject menu = mMenus.getJSONObject(index);
				View menuView = LayoutInflater.from(mContext).inflate(R.layout.toolbar_item, mMenuContainer, false);
				menuView.setTag(index);
				mMenuContainer.addView(menuView);
				
				// add divider
				if(index != mMenus.length() - 1){
					View divider = LayoutInflater.from(mContext).inflate(R.layout.toolbar_divider, mMenuContainer, false);
					mMenuContainer.addView(divider);
				}
				
				// 子菜单
				JSONArray subMenu = menu.optJSONArray(KEY_SUB_MENU);
				if(subMenu == null || subMenu.length() < 1){
					menuView.findViewById(R.id.ic_sub_menu).setVisibility(View.GONE);;
				}
				
				TextView title = (TextView) menuView.findViewById(R.id.title);
				title.setText(menu.optString(KEY_NAME));
				
				menuView.setOnClickListener(this);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	protected void showToolbarMenu(View anchor, int index, JSONArray subMenuArray) {
		if(index < 0){
			return;
		}
		
		if(subMenuArray == null || subMenuArray.length() < 1){
			// TODO: trigger main menu
			return;
		}
		
		if(mSubMenu == null){
			mSubMenu = new ToolbarPopupWindow(this);
			mSubMenu.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss() {
					System.out.println("dismiss");
				}
			});
			
			mContainer.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mShowingMenuIndex = -1;
				}
			});

		}
		
		// 当前正在显示
		if(index == mShowingMenuIndex){
			mShowingMenuIndex = -1;
			return;
		}
		
		mSubMenu.show(anchor);
		mShowingMenuIndex = index;
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_toolbar;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu:
			int index = (Integer) v.getTag();
			JSONArray subMenu = mMenus.optJSONObject(index).optJSONArray(KEY_SUB_MENU);
			showToolbarMenu(v, index, subMenu);
			break;

		default:
			break;
		}
	}

}
