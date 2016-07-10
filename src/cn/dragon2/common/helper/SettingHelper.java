package cn.dragon2.common.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;


public class SettingHelper{
	
	public static void ShareContent(String title,String msg,Context context) {
		Intent iShare=new Intent(Intent.ACTION_SEND);
		iShare.setType("text/plain");
		iShare.putExtra(Intent.EXTRA_TEXT,msg);
		context.startActivity(Intent.createChooser(iShare,title));
	}
	
	public static void RattingApp(Context ctx){
		ctx.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(GetAppUrl(ctx))));
	}
	
	public static void MoreApps(Context ctx){
		ctx.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(GetMoreApp())));
	}
	
	public static String GetAppWebUrl(Context ctx){
		return "http://play.google.com/store/apps/details?id="+ctx.getPackageName();
	}
	
	public static String GetAppUrl(Context ctx){
		return "market://details?id="+ctx.getPackageName();
	}
	
	public static String GetMoreApp(){
		return "market://search?q=pub:Denny.Dong";
	}
	
	public static String GetTextFromStream(InputStream stream){
		StringBuilder builder=new StringBuilder();
		BufferedReader bReader=new BufferedReader(new InputStreamReader(stream));
		String line="";
		try {
			while ((line=bReader.readLine())!=null) {
				builder.append(line);	
				builder.append("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}
}
