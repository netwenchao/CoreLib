package cn.dragon2.common.util;
import cn.dragon2.common.R;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SettingUtil {
	
	public static void Share2Friend(Context ctx,String msg,String title){
		Intent iShare=new Intent(Intent.ACTION_SEND);
		iShare.setType("text/plain");
		iShare.putExtra(Intent.EXTRA_TEXT,msg);
		ctx.startActivity(Intent.createChooser(iShare,title));
	}
	
	public static void ShareContent(String title,String msg,Context context) {
		Intent iShare=new Intent(Intent.ACTION_SEND);
		iShare.setType("text/plain");
		iShare.putExtra(Intent.EXTRA_TEXT,msg);
		context.startActivity(Intent.createChooser(iShare,title));
	}
	
	public static void RattingApp(Context ctx){
		ctx.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(GetAppUrl(ctx))));
	}
	
	public static String GetAppUrl(Context ctx){
		return "market://details?id="+ctx.getPackageName();
	}
	
	public static void SendMail(Context ctx,String mailAddr){
		try {
			Intent email = new Intent(android.content.Intent.ACTION_SEND);
			email.setType("text/plain");
			String[] emailReciver = new String[]{mailAddr};
			ctx.startActivity(Intent.createChooser(email,ctx.getString(R.string.mailChooser)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
