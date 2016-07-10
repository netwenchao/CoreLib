package cn.dragon2.common.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.R.integer;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.provider.Settings.System;
import android.util.Log;

public class AssertDbHelper {
	
	public static SQLiteDatabase OpenDataBase(Context mContext,String dbName,String assertDBName){
		File dataFolder=mContext.getFilesDir(); 
		File dbFile=new File(dataFolder.getAbsolutePath()+"/"+dbName);
		Log.v("DataCenter", dbFile.getAbsolutePath());
		if(!dbFile.exists()){
			try {				
				InputStream inputStream=mContext.getAssets().open(assertDBName);				
				FileOutputStream fso=new FileOutputStream(dbFile);
				byte[] buffer=new byte[1024];
				int readCount=0;
				while((readCount=inputStream.read(buffer))>0){
					fso.write(buffer);
				}
				inputStream.close();
				fso.close();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		SQLiteDatabase db=SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(),null,SQLiteDatabase.CREATE_IF_NECESSARY);
		return db;
	} 
	
	public static SQLiteDatabase OpenDataBaseFromZip(Context mContext,String dbName,String assertDBName){
		File dataFolder=mContext.getFilesDir();
		File dbFile=new File(dataFolder.getAbsolutePath()+"/"+dbName);
		Log.v("DataCenter", dbFile.getAbsolutePath());
		
		if(!dbFile.exists()){
			try {
				Log.v("DataCenter","Get from zip file.");
				ZipEntry entity=null;
				ZipInputStream zipInput=new ZipInputStream(mContext.getAssets().open(assertDBName));
				entity=zipInput.getNextEntry();
				FileOutputStream fso=new FileOutputStream(dbFile);
				
				if(entity!=null && entity.getSize()>0 && !entity.isDirectory()){
					Log.v("",entity.getName());				
					byte[] buffer=new byte[1024];
					int readCount=-1;				
					while((readCount=zipInput.read(buffer))>-1){
						fso.write(buffer,0,readCount);
					}
					zipInput.close();
					fso.flush();
					fso.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		SQLiteDatabase db=SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(),null,SQLiteDatabase.CREATE_IF_NECESSARY);
		return db;
	}

	public static Bitmap GetImageFromAsset(Context mContext,String fileName){
		Bitmap image=null;
		AssetManager am=mContext.getResources().getAssets();
		try {
			BitmapFactory.Options opt = new BitmapFactory.Options();  
			opt.inPreferredConfig = Bitmap.Config.RGB_565;   
			opt.inPurgeable = true;  
			opt.inInputShareable = true;
			
			InputStream stream=am.open(fileName);
			image=BitmapFactory.decodeStream(stream,null,opt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static Bitmap ZoomImage(Bitmap image,int newHeight,int newWidth){
		// 获得图片的宽高
		int width = image.getWidth();
		int height = image.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(image, 0, 0, width, height, matrix, true);
		return newbm;
	}

	public static Boolean DropDataBase(Context mContext,String dbName){
		try {
			File dataFolder=mContext.getFilesDir();
			File dbFile=new File(dataFolder.getAbsolutePath()+"/"+dbName);
			return dbFile.delete();
		} catch (Exception e) {
			Log.e("dwc",e.getMessage());
			return false;
		}		
	}
}
