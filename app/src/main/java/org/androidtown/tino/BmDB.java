package org.androidtown.tino;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BmDB extends SQLiteOpenHelper {
    // DATAbase constants
    private static final int DATABASE_VERSION = 2; // data version
    private static final String DATABASE_NAME = "db_Bm"; // database name
    private static final String TABLE_NAME = "bookmark";  // table name
    private static final String COL_ID = "id";          // this column store task id
    private static final String COL_NAME = "name";    // this column store task
    private static final String COL_Hour = "hour";        // this column store time
    private static final String COL_Min = "min";        // this column store do

    private String CREATE_TABLE_BM= "CREATE TABLE IF NOT EXISTS tino ("
            + COL_ID + " INTEGER, "  // this column contain alarm's id
            + COL_NAME + " TEXT, "      // task's name
            + COL_Hour + " INTEGER," // this column contain time
            + COL_Min + " INTEGER)"; // this column contain do


    // TODO:   this is data base constructor
    public BmDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BM);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bookmark");
        onCreate(db);
    }
    public void insert(int id, String name,int hour,int min){
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // ContentValues like a box to contain value in there
        ContentValues values = new ContentValues();
        // put value to each column
        values.put(COL_ID,id);
        values.put(COL_NAME,name);
        values.put(COL_Hour,hour);
        values.put(COL_Min,min);
        // DB에 입력한 값으로 행 추가
        // insert to table
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void update(int id, int new_id){
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE bookmark SET id=" +new_id + " WHERE id = "+id+";");
        db.close();
    }
    public void delete(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM bookmark;");
        db.close();
    }
    public int check(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_NAME, null);
        int num = cursor.getCount();
        cursor.close();
        db.close();
        return num;

    }
    //북마크 정리 최근 n개(임의)만 남게 앞의 초과한 수만큼 col 삭제.
    public void manage(int n){
        int count=check();
        if(count>n){

            SQLiteDatabase db = getWritableDatabase();
            // 입력한 항목과 일치하는 행의 가격 정보 수정
            Log.d("asdfj","sf");

            String sql = "Select * from bookmark;";
            Cursor cursor = db.rawQuery(sql, null);
            int num = cursor.getCount();
            Log.d("test", "count: " +count);

            try {
                if (cursor != null) {
                    for (int i = 0; i < num; i++) {
                        cursor.moveToNext();
                        if(i<count-n){
                            del_row(i);
                        }
                        else {
                            update(i, i - (count - num));
                            Log.d("sls", "adf");
                        }
                    }
                }
            } finally {
                db.close();
                cursor.close();
            }

        }
    }
    public void del_row(int id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("DELETE FROM bookmark WHERE id=" + id + ";");
        db.close();
    }
}
