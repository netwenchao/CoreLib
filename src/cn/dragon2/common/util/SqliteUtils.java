package cn.dragon2.common.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * SqliteUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-10-21
 */
public class SqliteUtils {

    private static volatile SqliteUtils instance;

    private DbHelper                    dbHelper;
    private SQLiteDatabase              wDb;
    private SQLiteDatabase              rDb;

    private SqliteUtils(Context context){
        dbHelper = new DbHelper(context);
        wDb = dbHelper.getWritableDatabase();
        rDb = dbHelper.getReadableDatabase();
    }

    public static SqliteUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SqliteUtils.class) {
                if (instance == null) {
                    instance = new SqliteUtils(context);
                }
            }
        }
        return instance;
    }

    public SQLiteDatabase getWDb() {
        return wDb;
    }

    public SQLiteDatabase getRDb() {
        return rDb;
    }
}
