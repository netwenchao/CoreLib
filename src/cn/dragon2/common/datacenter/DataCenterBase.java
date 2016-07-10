package cn.dragon2.common.datacenter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.dragon2.common.entity.MyAppsInfo;

public abstract  class DataCenterBase {
	protected Context mContext;
	protected SQLiteDatabase dbW = null;
	protected SQLiteDatabase dbR = null;
	protected static String dbName = "dragon2.db3";
	protected static String DbAssertName = "x.png";
	protected String packageNameString;
	
	/*
	 * 返回App列表，如果为NULL说明不存在appinfo表
	 * */
	public List<MyAppsInfo> getMoreApps(){		
		Cursor curTable=dbW.rawQuery("select name from sqlite_master where type='table' and name='appInfo'",null);
		if(curTable.getCount()>0){
			List<MyAppsInfo> cards=new ArrayList<MyAppsInfo>();
			Cursor cur=dbW.rawQuery("select _id,ifnull(AppName,'') as AppName,ifnull(AppDesc,'') as AppDesc,ifnull(AppUrl,'') as AppUrl,ifnull(AppIcon,'') as AppIcon,ifnull(AppSize,'') as AppSize,AppPrice,ifnull(Note,'') as Note,AppType,AppIndex from appinfo",null);
			while (cur.moveToNext()) {
				cards.add(cur2MyAppsInfo(cur));
			}
			return cards;
		}
		return null;
	}
	
	private MyAppsInfo cur2MyAppsInfo(Cursor cur){
		MyAppsInfo info=new MyAppsInfo();
		info.Id=cur.getInt(cur.getColumnIndex("_id"));
		info.AppName=cur.getString(cur.getColumnIndex("AppName"));
		info.AppDesc=cur.getString(cur.getColumnIndex("AppDesc"));
		info.AppUrl=cur.getString(cur.getColumnIndex("AppUrl"));
		info.AppIcon=cur.getString(cur.getColumnIndex("AppIcon"));
		info.AppSize=cur.getString(cur.getColumnIndex("AppSize"));
		info.AppPrice=cur.getInt(cur.getColumnIndex("AppPrice"));
		info.Note=cur.getString(cur.getColumnIndex("Note"));
		info.AppType=cur.getInt(cur.getColumnIndex("AppType"));
		info.AppIndex=cur.getInt(cur.getColumnIndex("AppIndex"));
		return info;
	}
}
