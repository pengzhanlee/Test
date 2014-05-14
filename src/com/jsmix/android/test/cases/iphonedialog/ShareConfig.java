package com.jsmix.android.test.cases.iphonedialog;

import java.util.HashMap;

import com.jsmix.android.test.R;

public class ShareConfig {
	
	private static class Config{
		int bgRes;
		int iconRes;
		String name;
		public Config(int bgRes, int iconRes, String name) {
			this.bgRes = bgRes;
			this.iconRes = iconRes;
			this.name = name;
		}
		
	}
	
	private static HashMap<String, Config> configs;
	
	static {
		configs = new HashMap<String, ShareConfig.Config>();
		configs.put("wechat", new Config(R.drawable.bg_round_green, R.drawable.ic_popup_share_weixin, "微信"));
		configs.put("moment", new Config(R.drawable.bg_round_orange, R.drawable.ic_popup_share_moment, "朋友圈"));
		configs.put("weibo", new Config(R.drawable.bg_round_red, R.drawable.ic_popup_share_weibo, "微博"));
		configs.put("link", new Config(R.drawable.bg_round_blue, R.drawable.ic_popup_share_link, "复制链接"));
	}
	
	public static int getBgRes(String id){
		return configs.get(id).bgRes;
	}

	public static int getIconRes(String id){
		return configs.get(id).iconRes;
	}
	
	
	public static String getName(String id){
		return configs.get(id).name;
	}
	
}
