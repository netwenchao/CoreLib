package cn.dragon2.common.apps;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import com.kyview.InitConfiguration;
import com.kyview.InitConfiguration.UpdateMode;
import com.kyview.interfaces.AdViewBannerListener;
import com.kyview.interfaces.AdViewInstlListener;
import com.kyview.manager.AdViewBannerManager;
import com.kyview.manager.AdViewInstlManager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import cn.dragon2.common.util.PreferencesUtils;



public class BaseApplicationAdView extends DragonApplication {

	private static AdConfig adConfig;
	private static AdViewInstlManager adInstlMgr;
	private static Boolean isFirstAd=true;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public AdConfig getAdConfig() {
		if (adConfig == null)
			adConfig = AdConfig.Load(this);
		return adConfig;
	}

	public void AppendAd(Activity ctx,ViewGroup container,AdViewBannerListener listener){        
		InitConfiguration initConfig = new InitConfiguration.Builder(ctx).setUpdateMode(UpdateMode.EVERYTIME).build()
				;
		AdViewBannerManager mgr=AdViewBannerManager.getInstance(ctx);
		mgr.init(initConfig,new String[]{getAdViewSDKKey()});
		mgr.requestAd(this,getAdViewSDKKey(), listener);
		View v=mgr.getAdViewLayout(ctx,getAdViewSDKKey());
		/*ViewGroup vp= (ViewGroup)v.getParent();
		if(vp!=null){
			vp.removeView(v);
		}*/
		container.addView(v);		
		container.invalidate();
	}
	//AdInstlInterface
	public void prepareAdInst(Activity ctx,AdViewInstlListener adInterface){
		
		if(adInstlMgr!=null) adInstlMgr.destory();
		adInstlMgr=null;
		adInstlMgr=AdViewInstlManager.getInstance(ctx);
		adInstlMgr.requestAd(ctx,getAdViewSDKKey(),adInterface);
		//AdViewTargeting.setInstlSwitcherMode(InstlSwitcher.CANCLOSED);
	}
	
	public void showAdInst(Activity ctx){
		adInstlMgr.requestAd(ctx,getAdViewSDKKey());//.requestAndshow();
	}
	
	/*
	 * 获取用户配置元数据
	 * */
	public String getMetaData(Context ctx,String key){
		try {
			ApplicationInfo aInfo = ctx
					.getApplicationContext()
					.getPackageManager()
					.getApplicationInfo(ctx.getPackageName(),
							PackageManager.GET_META_DATA);
			
			Log.v("dragon2","GetMetaDataFor:"+key);
			String metaData=aInfo.metaData.get(key).toString();
			Log.v("dragon2",metaData);
			return metaData;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	/*
	 * 获取用户配置元数据
	 * */
	public Hashtable<String,String> getMetaData(Context ctx,List<String> keys){
		ApplicationInfo aInfo;
		try {
			aInfo = ctx
					.getApplicationContext()
					.getPackageManager()
					.getApplicationInfo(ctx.getPackageName(),
							PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
		Hashtable<String,String> rslt=new Hashtable<String, String>();
		String metaData="";
		
		for (String key : keys) {
			metaData=aInfo.metaData.get(key).toString();
			rslt.put(key,metaData);
		}
		return rslt;		
	}

	public static class AdConfig {
		public String APPSID;
		public String AppFeeID;
		public String advkey;

		public static AdConfig Load(Context ctx) {
			AdConfig config = new AdConfig();
			try {
				ApplicationInfo aInfo = ctx
						.getApplicationContext()
						.getPackageManager()
						.getApplicationInfo(ctx.getPackageName(),
								PackageManager.GET_META_DATA);
				String[] idKeys = aInfo.metaData.get("idkey").toString()
						.split("\\|\\|");
				config.advkey=aInfo.metaData.get("advkey").toString();
				
				Log.v("advkey",aInfo.metaData.get("advkey").toString());
				Log.v("dwc",aInfo.metaData.get("idkey").toString());
				
				if (idKeys.length > 1) {
					config.APPSID = idKeys[0];
					config.AppFeeID = idKeys[1];
					Log.v("dwc",idKeys[0]);
					Log.v("dwc",idKeys[1]);
				}
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return config;
		}
	}
	
	public static class UserAdScore{
		private static final String ScoreKey="ad_score";
		private static final String DaysKey="ad_day";
		private static final String LastDay="ad_lstday";
		private static final String LastClickedKey="ad_lstClicked";
		
		private Context mContext;
		
		public UserAdScore(Context ctx){
			mContext=ctx;
		}	
		
		//返回连续登录天数
		public Integer getDays() {
			int days=PreferencesUtils.getInt(mContext,this.DaysKey);
			long now=Calendar.getInstance().getTimeInMillis();
			long datePart=now-PreferencesUtils.getLong(mContext,this.LastDay)/100000;
			if(datePart>864 && datePart<1728){
				days++;
			}else{
				days=1;
				PreferencesUtils.putInt(mContext,this.DaysKey,days);
			}			
			return days;			
		}		

		//获得积分
		public long getScore() {
			return PreferencesUtils.getLong(mContext,this.ScoreKey,0);
		}
		
		public Boolean isTodayClicked(){
			Long lastDate=PreferencesUtils.getLong(mContext,this.LastClickedKey,0);
			return ((Calendar.getInstance().getTimeInMillis()-lastDate)/100000)<864;
		}
		
		public void clicked(){
			PreferencesUtils.getLong(mContext,this.LastClickedKey,Calendar.getInstance().getTimeInMillis());
		}
		
		//增加积分(可视为金币)
		public long addScore(int score){
			int sScore=PreferencesUtils.getInt(mContext,this.ScoreKey,0)+score;
			PreferencesUtils.putLong(mContext,this.ScoreKey,sScore);
			return sScore;
		}
	}
}
