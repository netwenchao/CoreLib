package cn.dragon2.common.entity;

import cn.dragon2.common.helper.AssertDbHelper;
import cn.dragon2.common.util.StringUtils;
import android.content.Context;
import android.graphics.Bitmap;

public class MyAppsInfo {
	public int Id;
	public String AppName;
	public String AppDesc;
	public String AppUrl;
	public String AppSize;
	public int AppPrice;
	/*
	 * Child 0,
	 * Game: 1,
	 * Info: 2
	*/
	public String AppIcon;
	public int AppType;
	public int AppIndex;
	public String Note;
	
	public Bitmap getAppIconBitMap(Context mContext){
		if(StringUtils.isBlank(AppIcon)) return null;
		return AssertDbHelper.GetImageFromAsset(mContext, AppIcon);
	}
	
}
