package cn.dragon2.common.apps;

import java.util.Calendar;

/**/
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.ViewGroup;
import cn.dragon2.common.util.PreferencesUtils;

public class BaseApplication extends DragonApplication {

	private static AdConfig adConfig;

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

	public void AppendAd(Context ctx,ViewGroup container,AdViewListener listener){		
		AdView.setAppSid(ctx,getAdConfig().APPSID);
		AdView.setAppSec(ctx,getAdConfig().AppFeeID);
		
		AdView av=new AdView(ctx);		
		if(null!=listener) av.setListener(listener);
		container.addView(av);		
		container.invalidate();
	}	

	public static class AdConfig {
		public String APPSID;
		public String AppFeeID;

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
		
		//增加积分(可视为金币)
		public long addScore(int score){
			int sScore=PreferencesUtils.getInt(mContext,this.ScoreKey,0)+score;
			PreferencesUtils.putLong(mContext,this.ScoreKey,sScore);
			return sScore;
		}
	}
}
