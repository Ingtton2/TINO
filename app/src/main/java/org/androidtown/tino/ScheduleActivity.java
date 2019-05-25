package org.androidtown.tino;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleActivity extends Activity {

    EditText editShower, editMakeup, editPack, editDry;
    EditText editEat, editClothes, editStyling, editPoo;
    //EditText inputHour, inputMinute;
    static int count = 0;

    // db information
    final static String TAG = "SQLite";
    //final static String dbName = "tino.db";
    final static int dbVersion = 2;
    static DBHelper dbHelper, dbHelper2;

    // name column에 들어갈 string
    final static String shower = "shower", makeup = "makeup", pack = "pack", dry = "dry";
    final static String eat = "eat", clothes = "clothes", styling = "styling", poo = "poo";

    // time column에 들어갈 time
    static String showertime = "0", makeuptime = "0", packtime = "0", drytime = "0";
    static String eattime = "0", clothestime = "0", stylingtime = "0", pootime = "0";
    //static String hour = "0", minute = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        editShower = findViewById(R.id.editShower);
        editMakeup = findViewById(R.id.editMakeup);
        editPack = findViewById(R.id.editPack);
        editDry = findViewById(R.id.editDry);
        editEat = findViewById(R.id.editEat);
        editClothes = findViewById(R.id.editClothes);
        editStyling = findViewById(R.id.editStyling);
        editPoo = findViewById(R.id.editPoo);

        dbHelper = new DBHelper(this, "tino.db", null, dbVersion);
        //dbHelper2 = new DBHelper(this, "timetogo.db", null, dbVersion);
    }

    public void mOnClick(View v) {
        SQLiteDatabase db, db2;
        String sql, sql2;

        // 북마크 디비 따로 해야함

        switch (v.getId()) {
            case R.id.ok:
                //Intent intent = new Intent(getApplicationContext(), AlarmsetActivity.class);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_LONG).show();

                showertime = editShower.getText().toString();
                makeuptime = editMakeup.getText().toString();
                packtime = editPack.getText().toString();
                drytime = editDry.getText().toString();
                eattime = editEat.getText().toString();
                clothestime = editClothes.getText().toString();
                stylingtime = editStyling.getText().toString();
                pootime = editPoo.getText().toString();

                //hour = inputHour.getText().toString();
                //minute = inputMinute.getText().toString();

                db = dbHelper.getWritableDatabase();
                //db2 = dbHelper2.getWritableDatabase();

                insert_tino(db);
                //insert_timetogo(db2);

                if (count >= 1) {
                    //Log.d(TAG, String.format("'%s'", count));
                    if (showertime.equals(""))
                        showertime = "0";
                    if (makeuptime.equals(""))
                        makeuptime = "0";
                    if (packtime.equals(""))
                        packtime = "0";
                    if (drytime.equals(""))
                        drytime = "0";
                    if (eattime.equals(""))
                        eattime = "0";
                    if (clothestime.equals(""))
                        clothestime = "0";
                    if (stylingtime.equals(""))
                        stylingtime = "0";
                    if (pootime.equals(""))
                        pootime = "0";
                    /*if (hour.equals(""))
                        hour = "0";
                    if (minute.equals(""))
                        minute = "0";*/

                    db.execSQL(String.format("UPDATE tino SET time = '%s' WHERE name = '%s';", showertime, shower));
                    db.execSQL(String.format("UPDATE tino SET time = '%s' WHERE name = '%s';", makeuptime, makeup));
                    db.execSQL(String.format("UPDATE tino SET time = '%s' WHERE name = '%s';", packtime, pack));
                    db.execSQL(String.format("UPDATE tino SET time = '%s' WHERE name = '%s';", drytime, dry));
                    db.execSQL(String.format("UPDATE tino SET time = '%s' WHERE name = '%s';", eattime, eat));
                    db.execSQL(String.format("UPDATE tino SET time = '%s' WHERE name = '%s';", clothestime, clothes));
                    db.execSQL(String.format("UPDATE tino SET time = '%s' WHERE name = '%s';", stylingtime, styling));
                    db.execSQL(String.format("UPDATE tino SET time = '%s' WHERE name = '%s';", pootime, poo));

                    //db.execSQL(String.format("UPDATE timetogo SET hour = '%s';", hour));
                    //db.execSQL(String.format("UPDATE timetogo SET minute = '%s';", minute));
                }

                db = dbHelper.getReadableDatabase();
                //db2 = dbHelper2.getReadableDatabase();

                sql = "SELECT * FROM tino;";
                //sql2 = "SELECT * FROM timetogo;";

                Cursor cursor = db.rawQuery(sql, null);
                //Cursor cursor2 = db2.rawQuery(sql2, null);

                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext())
                        Log.d(TAG, String.format("\nname = %s, time = %s", cursor.getString(0), cursor.getString(1)));
                } else {
                    Log.d(TAG, "not exists");
                }
                cursor.close();

                /*if (cursor2.getCount() > 0) {
                    while (cursor2.moveToNext())
                        Log.d(TAG, String.format("\nhour = %s, minute = %s", cursor2.getString(0), cursor2.getString(1)));
                } else {
                    Log.d(TAG, "not exists");
                }
                cursor2.close();*/
                break;

            case R.id.delete: //전체삭제 버튼(delete)
                db = dbHelper.getWritableDatabase();
                sql = "DELETE FROM tino;";
                db.execSQL(sql);
                //Log.d(TAG, "delete");
                break;
        }
        dbHelper.close();
        //dbHelper2.close();
    }

    public static void insert_tino(SQLiteDatabase db){
        //writable 없어도되겠지?
        db.execSQL(String.format("INSERT or REPLACE INTO tino VALUES('%s', '%s');", shower, showertime));
        db.execSQL(String.format("INSERT or REPLACE INTO tino VALUES('%s', '%s');", makeup, makeuptime));
        db.execSQL(String.format("INSERT or REPLACE INTO tino VALUES('%s', '%s');", pack, packtime));
        db.execSQL(String.format("INSERT or REPLACE INTO tino VALUES('%s', '%s');", dry, drytime));
        db.execSQL(String.format("INSERT or REPLACE INTO tino VALUES('%s', '%s');", eat, eattime));
        db.execSQL(String.format("INSERT or REPLACE INTO tino VALUES('%s', '%s');", clothes, clothestime));
        db.execSQL(String.format("INSERT or REPLACE INTO tino VALUES('%s', '%s');", styling, stylingtime));
        db.execSQL(String.format("INSERT or REPLACE INTO tino VALUES('%s', '%s');", poo, pootime));
        count++;
    }

    /*public static void insert_timetogo(SQLiteDatabase db){
        db.execSQL(String.format("INSERT or REPLACE INTO timetogo VALUES('%s', '%s');", hour, minute));
    }*/

    static class DBHelper extends SQLiteOpenHelper {

        //생성자 - database 파일을 생성한다.
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //DB 처음 만들때 호출. - 테이블 생성 등의 초기 처리.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS tino (name TEXT, time TEXT);");
            //db.execSQL("CREATE TABLE IF NOT EXISTS timetogo (hour TEXT, minute TEXT);");
            //result.append("\nt3 테이블 생성 완료.");
        }

        //DB 업그레이드 필요 시 호출. (version값에 따라 반응)
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS tino");
            onCreate(db);
        }

    }
}