package cn.dragon2.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

public class NetWorkUtil {
	
	public static boolean NetWorkAvailable(Context context) {
		ConnectivityManager netWorkMgr=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(netWorkMgr!=null){
			NetworkInfo info= netWorkMgr.getActiveNetworkInfo();
			return info!=null && info.isAvailable(); 
		}
		return false;
	}
	
	public static boolean WifiAvailable(Context context) {
		ConnectivityManager netWorkMgr=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(netWorkMgr!=null){
			NetworkInfo info= netWorkMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			return info!=null && info.isAvailable(); 
		}
		return false;
	}
	
	public static boolean Is2GOr3GAvailable(Context context) {
		ConnectivityManager netWorkMgr=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(netWorkMgr!=null){
			NetworkInfo info= netWorkMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			return info!=null && info.isAvailable(); 
		}
		return false;
	}
}
