package org.androidtown.tino;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TinoDB extends SQLiteOpenHelper {
    // DATAbase constants
    private static final int DATABASE_VERSION = 2; // data version
    private static final String DATABASE_NAME = "db_TINO"; // database name
    private static final String TABLE_NAME = "tino";  // table name
    private static final String COL_ID = "id";          // this column store task id
    private static final String COL_NAME = "name";    // this column store task
    private static final String COL_Time = "time";        // this column store time
    private static final String COL_Do = "do";        // this column store do

    private String CREATE_TABLE_TINO= "CREATE TABLE IF NOT EXISTS tino ("
            + COL_ID + " INTEGER, "  // this column contain alarm's id
            + COL_NAME + " TEXT, "      // task's name
            + COL_Time + " TEXT," // this column contain time
            + COL_Do + " INTEGER)"; // this column contain do

    // TODO:   this is data base constructor
    public TinoDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TINO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tino");
        onCreate(db);
    }
    public void insert(int id, String task,String time,int d) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // ContentValues like a box to contain value in there
        ContentValues values = new ContentValues();
        // put value to each column
        values.put(COL_ID,id);
        values.put(COL_NAME,task);
        values.put(COL_Time,time);
        values.put(COL_Do,d);
        // DB에 입력한 값으로 행 추가
        // insert to table
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void update(String task,String time) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE tino SET time=" + time + " WHERE name='" + task + "';");
        db.close();
    }
    public void delete(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM tino;");
        db.close();
    }
    public int check(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select * from tino;";
        Cursor cursor = db.rawQuery(sql, null);
        int num = cursor.getCount();
        cursor.close();
        db.close();
        return num;
    }
    public String hint(String task){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select time from tino where name = '"+task+"';", null);
        int cu=cursor.getCount();
        Log.d("time","check:"+cu);
        String hint = cursor.getString(cursor.getColumnIndex("time"));
        Log.d("time","check:"+hint);
        cursor.close();
        db.close();

        return hint;
    }
    public void update_do(String task, int d){
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        String sql="UPDATE tino SET do = "+ d + " WHERE name ='" + task + "';";
        db.execSQL(sql);

        db.close();
    }
}
