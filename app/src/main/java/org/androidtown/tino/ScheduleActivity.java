package org.androidtown.tino;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ScheduleActivity extends Activity {

    EditText editShower, editMakeup, editPack, editDry;
    EditText editEat, editClothes, editStyling, editPoo;
    EditText inputHour, inputMinute;
    static int count = 0;

    // db information
    final static String TAG = "SQLite";
    //final static String dbName = "tino.db";
    final static int dbVersion = 2;
    //static DBHelper dbHelper, dbHelper2;
    TinoDB helper;

    // name column에 들어갈 string
    final static String shower = "shower", makeup = "makeup", pack = "pack", dry = "dry";
    final static String eat = "eat", clothes = "clothes", styling = "styling", poo = "poo";

    // time column에 들어갈 time
    static String showertime = "0", makeuptime = "0", packtime = "0", drytime = "0";
    static String eattime = "0", clothestime = "0", stylingtime = "0", pootime = "0";
    static String hour = "0", minute = "0";

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

        inputHour = findViewById(R.id.inputHour);
        inputMinute = findViewById(R.id.inputMinute);
        helper = new TinoDB(this);

       // dbHelper = new DBHelper(this, "tino.db", null, dbVersion);
        //dbHelper2 = new DBHelper(this, "timetogo.db", null, dbVersion);
    }

    public void mOnClick(View v) {
        SQLiteDatabase db, db2;
        String sql, sql2;

        // 북마크 디비 따로 해야함

        switch (v.getId()) {
            case R.id.ok:
                helper.delete();
                helper.insert(0, "shower", "0");
                helper.insert(1, "makeup", "0");
                helper.insert(2, "pack", "0");
                helper.insert(3, "dry", "0");
                helper.insert(4, "eat", "0");
                helper.insert(5, "clothes", "0");
                helper.insert(6, "styling", "0");
                helper.insert(7, "poo", "0");

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

                helper.update(shower, showertime);
                helper.update( makeup, makeuptime);
                helper.update( pack, packtime);
                helper.update( dry, drytime);
                helper.update( eat, eattime);
                helper.update( clothes, clothestime);
                helper.update( styling, stylingtime);
                helper.update( poo, pootime);



                // db = dbHelper.getWritableDatabase();
                //db2 = dbHelper2.getWritableDatabase();

                db=helper.getReadableDatabase();
                Cursor cursor = db.rawQuery("Select name, time from tino ;", null);
                //Cursor cursor2 = db2.rawQuery(sql2, null);
                int count = cursor.getCount();
                Log.d(TAG, "count" + count);

                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext())
                        Log.d("tesssssst", String.format("\n**name = %s, time = %s", cursor.getString(0), cursor.getString(1)));
                } else {
                    Log.d(TAG, "not exists");
                }
                cursor.close();

                break;

            case R.id.delete: //전체삭제 버튼(delete)
                helper.delete();
                break;
        }
    }

}