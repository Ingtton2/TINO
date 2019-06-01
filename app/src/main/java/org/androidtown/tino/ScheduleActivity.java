package org.androidtown.tino;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class ScheduleActivity extends Activity {

    EditText editShower, editMakeup, editPack, editDry;
    EditText editEat, editClothes, editStyling, editPoo;
    EditText inputHour, inputMinute;
    Switch switchShower,switchMakeup,switchPack,switchDry,switchEat,switchClothes,switchStyling,switchPoo;
    static int count = 0;

    // db information
    final static String TAG = "SQLite";
    //final static String dbName = "tino.db";
    final static int dbVersion = 2;
    //static DBHelper dbHelper, dbHelper2;
    TinoDB helper;
    BmDB helper2;

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
        helper2=new BmDB(this);

        switchShower = (Switch)findViewById(R.id.switchShower);
        switchMakeup = (Switch)findViewById(R.id.switchMakeup);
        switchPack = (Switch)findViewById(R.id.switchPack);
        switchDry = (Switch)findViewById(R.id.switchDry);
        switchEat = (Switch)findViewById(R.id.switchEat);
        switchClothes = (Switch)findViewById(R.id.switchClothes);
        switchStyling = (Switch)findViewById(R.id.switchStyling);
        switchPoo = (Switch)findViewById(R.id.switchPoo);


    }

    public void mOnClick(View v) {
        SQLiteDatabase db, db2;
        Intent intent=getIntent();
        int id =intent.getIntExtra("id",0);

        // 북마크 디비 따로 해야함

        switch (v.getId()) {
            case R.id.ok:

                //tino row가 하나도 없는 초기 상황이면 모두 0으로 입력값 insert
                if (helper.check() == 0) {
                    Log.d("test", "input");
                    helper.insert(0, "shower", "0", 0);
                    helper.insert(1, "makeup", "0", 0);
                    helper.insert(2, "pack", "0", 0);
                    helper.insert(3, "dry", "0", 0);
                    helper.insert(4, "eat", "0", 0);
                    helper.insert(5, "clothes", "0", 0);
                    helper.insert(6, "styling", "0", 0);
                    helper.insert(7, "poo", "0", 0);
                }


                showertime = editShower.getText().toString();
                makeuptime = editMakeup.getText().toString();
                packtime = editPack.getText().toString();
                drytime = editDry.getText().toString();
                eattime = editEat.getText().toString();
                clothestime = editClothes.getText().toString();
                stylingtime = editStyling.getText().toString();
                pootime = editPoo.getText().toString();

                hour = inputHour.getText().toString();
                minute = inputMinute.getText().toString();

                //다시 입력한 값만 업데이트
                if (!showertime.equals(""))
                    helper.update(shower, showertime);
                if (!makeuptime.equals(""))
                    helper.update(makeup, makeuptime);
                if (!packtime.equals(""))
                    helper.update(pack, packtime);
                if (!drytime.equals(""))
                    helper.update(dry, drytime);
                if (!eattime.equals(""))
                    helper.update(eat, eattime);
                if (!clothestime.equals(""))
                    helper.update(clothes, clothestime);
                if (!stylingtime.equals(""))
                    helper.update(styling, stylingtime);
                if (!pootime.equals(""))
                    helper.update(poo, pootime);

                if (switchShower.isChecked())
                    helper.update_do(shower, 1);
                else
                    helper.update_do(shower, 0);

                if (switchMakeup.isChecked())
                    helper.update_do(makeup, 1);
                else
                    helper.update_do(makeup, 0);

                if (switchPack.isChecked())
                    helper.update_do(pack, 1);
                else
                    helper.update_do(pack, 0);

                if (switchDry.isChecked())
                    helper.update_do(dry, 1);
                else
                    helper.update_do(dry, 0);

                if (switchEat.isChecked())
                    helper.update_do(eat, 1);
                else
                    helper.update_do(eat, 0);

                if (switchClothes.isChecked())
                    helper.update_do(clothes, 1);
                else
                    helper.update_do(clothes, 0);

                if (switchStyling.isChecked())
                    helper.update_do(styling, 1);
                else
                    helper.update_do(styling, 0);

                if (switchPoo.isChecked())
                    helper.update_do(poo, 1);
                else
                    helper.update_do(poo, 0);

                int h = 0, m = 0;


                if (!hour.equals("") && !minute.equals("")) {
                    h = Integer.parseInt(hour);
                    m = Integer.parseInt(minute);
                    helper2.addData(h, m, id);
                } else if (!hour.equals("")) {
                    h = Integer.parseInt(hour);
                    m = 0;
                    helper2.addData(h, m, id);
                } else if (!minute.equals("")) {
                    h = 0;
                    m = Integer.parseInt(minute);
                    helper2.addData(h, m, id);
                }
                Log.d("hour2", "hour" + hour + "min" + minute);


                db = helper.getReadableDatabase();
                Cursor cursor = db.rawQuery("Select name,time, do from tino ;", null);
                //Cursor cursor2 = db2.rawQuery(sql2, null);
                int count = cursor.getCount();
                Log.d(TAG, "count" + count);

                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext())
                        Log.d("tesst", String.format("\n**name = %s, time = %s, do = %s", cursor.getString(0), cursor.getString(1), cursor.getString(2)));
                } else {
                    Log.d(TAG, "not exists");
                }
                cursor.close();

                db2 = helper2.getReadableDatabase();
                Cursor cursor2 = db2.rawQuery("Select * from bookmark Where id=" + id + " ;", null);
                int count2 = cursor2.getCount();
                Log.d("count2", "count" + count2);

                cursor2.moveToNext();
                int sh = cursor2.getInt(cursor2.getColumnIndex("hour"));
                int sm = cursor2.getInt(cursor2.getColumnIndex("min"));
                cursor2.close();
                Log.d("hour2", "hour" + sh + "min" + sm);

                if (h > sh && m <= sm) {
                    sh = sh + 24 - h;
                    sm = sm - m;
                } else if (h >= sh && m > sm) {
                    sh = sh + 23 - h;
                    sm = sm + 60 - m;
                } else if (h <= sh && m <= sm) {
                    sh = sh - h;
                    sm = sm - m;
                } else if (h < sh && m > sm) {
                    sh = sh - h - 1;
                    sm = sm + 60 - m;
                }
                Log.d("time2", "hour:" + sh + "min:" + sm);
                SharedPreferences sharedPreferences = getSharedPreferences("sTime",MODE_PRIVATE);
                //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("hour",sh);
                editor.putInt("min",sm);
                editor.commit();

                Intent intent2 = new Intent(ScheduleActivity.this, CheckScheduleActivity.class);
                startActivity(intent2);
                Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_LONG).show();

                break;

        }
    }

}