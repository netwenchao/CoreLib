package cn.dragon2.common.apps;

import android.app.Application;

public class DragonApplication extends Application{
	
	private static String CurrentSDKKey="";
	public static String[] AdViewSDKS=new String[]{
			"SDK20161510030718w9b0x5xv3egalfj",//����Key
			"SDK20161510030703h63seez9mrsz8z4"//����ʶʳ��
	};
	
	public static String getAdViewSDKKey(){
		if(CurrentSDKKey.equals("")){
			CurrentSDKKey=Math.random()>0.5?AdViewSDKS[0]:AdViewSDKS[1];
		}
		return CurrentSDKKey;
	};
	
}
