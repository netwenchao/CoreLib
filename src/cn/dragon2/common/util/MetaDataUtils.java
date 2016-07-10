package cn.dragon2.common.util;

import java.util.Hashtable;
import java.util.Set;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;

public class MetaDataUtils {	
	/*
	 * 返回系统的MetaData
	 * */
	public static Hashtable<String,String> GetAllMetaData(Context ctx){
		Hashtable<String,String> metaData=new Hashtable<String,String>();
		try {
			ApplicationInfo appInfo=ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(),PackageManager.GET_META_DATA);
			Set<String> keys=appInfo.metaData.keySet();
			for(String s:keys){
				metaData.put(s,metaData.get(s));
			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return metaData;
	}

	/*
	 * 返回系统所有Activitys
	 * */
	public static Hashtable<String,String> GetAllActivity(Context ctx){
		Hashtable<String,String> metaData=new Hashtable<String,String>();
		PackageManager pm= ctx.getPackageManager();
		try {
			PackageInfo pInfo=pm.getPackageInfo(ctx.getPackageName(),PackageManager.GET_ACTIVITIES|PackageManager.GET_SERVICES);
			for(ActivityInfo ainfo:pInfo.activities){
				metaData.put(ainfo.name,"");
			}
			
			for(ServiceInfo ainfo:pInfo.services){
				metaData.put(ainfo.name,"");
			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return metaData;
	}
}
