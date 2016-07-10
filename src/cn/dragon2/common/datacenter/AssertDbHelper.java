package cn.dragon2.common.datacenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
	
	public static Boolean DropDataBase(Context mContext,String dbName){
		File dataFolder=mContext.getFilesDir(); 
		File dbFile=new File(dataFolder.getAbsolutePath()+"/"+dbName);
		return dbFile.delete();
	}
}
