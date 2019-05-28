package org.androidtown.tino;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class ScheduleActivity extends Activity {

    Switch switchShower,switchMakeup,switchPack,switchDry,switchEat,switchClothes,switchStyling,switchPoo;
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

        switchShower = (Switch)findViewById(R.id.switchShower);
        switchMakeup = (Switch)findViewById(R.id.switchMakeup);
        switchPack = (Switch)findViewById(R.id.switchPack);
        switchDry = (Switch)findViewById(R.id.switchDry);
        switchEat = (Switch)findViewById(R.id.switchEat);
        switchClothes = (Switch)findViewById(R.id.switchClothes);
        switchStyling = (Switch)findViewById(R.id.switchStyling);
        switchPoo = (Switch)findViewById(R.id.switchPoo);


        editShower = findViewById(R.id.editShower);
        editMakeup = findViewById(R.id.editMakeup);
        editPack = findViewById(R.id.editPack);
        editDry = findViewById(R.id.editDry);
        editEat = findViewById(R.id.editEat);
        editClothes = findViewById(R.id.editClothes);
        editStyling = findViewById(R.id.editStyling);
        editPoo = findViewById(R.id.editPoo);
//        //hint 저장된거 보이게 하고 싶은뎁 잘 안되서 ㅠㅠ
//        int num=helper.check();
//        if(num==8){
//            String[] hint=hint();
//            Log.d("hint","2ok?");
//            editShower.setHint(hint[0]);
//            editMakeup.setHint(hint[1]);
//            editPack.setHint(hint[1]);
//            editDry.setHint(hint[2]);
//            editEat.setHint(hint[4]);
//            editClothes.setHint(hint[5]);
//            editStyling.setHint(hint[6]);
//            editPoo.setHint(hint[7]);
//
//            switchShower.setChecked(chkSwitch(shower));
//            switchMakeup.setChecked(chkSwitch(makeup));
//            switchPack.setChecked(chkSwitch(pack));
//            switchDry.setChecked(chkSwitch(dry));
//            switchEat.setChecked(chkSwitch(eat));
//            switchClothes.setChecked(chkSwitch(clothes));
//            switchStyling.setChecked(chkSwitch(styling));
//            switchPoo.setChecked(chkSwitch(poo));
//
//
//        }


       // dbHelper = new DBHelper(this, "tino.db", null, dbVersion);
        //dbHelper2 = new DBHelper(this, "timetogo.db", null, dbVersion);
    }

    public void mOnClick(View v) {
        SQLiteDatabase db, db2;
        String sql, sql2;

        // 북마크 디비 따로 해야함

        switch (v.getId()) {
            case R.id.ok:

                //tino row가 하나도 없는 초기 상황이면 모두 0으로 입력값 insert
                if(helper.check()==0){
                    Log.d("test","input");
                    helper.insert(0, "shower", "0",0);
                    helper.insert(1, "makeup", "0",0);
                    helper.insert(2, "pack", "0",0);
                    helper.insert(3, "dry", "0",0);
                    helper.insert(4, "eat", "0",0);
                    helper.insert(5, "clothes", "0",0);
                    helper.insert(6, "styling", "0",0);
                    helper.insert(7, "poo", "0",0);
                }
                int num=helper.check();
                Log.d("test330","count "+num);


                Intent intent = new Intent(getApplicationContext(), AlarmsetActivity.class);
                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

                //다시 입력한 값만 업데이트
                if (!showertime.equals(""))
                    helper.update(shower, showertime);
                if (!makeuptime.equals(""))
                    helper.update( makeup, makeuptime);
                if (!packtime.equals(""))
                    helper.update( pack, packtime);
                if (!drytime.equals(""))
                    helper.update( dry, drytime);
                if (!eattime.equals(""))
                    helper.update( eat, eattime);
                if (!clothestime.equals(""))
                    helper.update( clothes, clothestime);
                if (!stylingtime.equals(""))
                    helper.update( styling, stylingtime);
                if (!pootime.equals(""))
                    helper.update( poo, pootime);

                if(switchShower.isChecked())
                    helper.update_do(shower,1);
                else
                    helper.update_do(shower,0);

                if(switchMakeup.isChecked())
                    helper.update_do(makeup,1);
                else
                    helper.update_do(makeup,0);

                if(switchPack.isChecked())
                    helper.update_do(pack,1);
                else
                    helper.update_do(pack,0);

                if(switchDry.isChecked())
                    helper.update_do(dry,1);
                else
                    helper.update_do(dry,0);

                if(switchEat.isChecked())
                    helper.update_do(eat,1);
                else
                    helper.update_do(eat,0);

                if(switchClothes.isChecked())
                    helper.update_do(clothes,1);
                else
                    helper.update_do(clothes,0);

                if(switchStyling.isChecked())
                    helper.update_do(styling,1);
                else
                    helper.update_do(styling,0);

                if(switchPoo.isChecked())
                    helper.update_do(poo,1);
                else
                    helper.update_do(poo,0);



                // db = dbHelper.getWritableDatabase();
                //db2 = dbHelper2.getWritableDatabase();

                db=helper.getReadableDatabase();
                Cursor cursor = db.rawQuery("Select * from tino;", null);
                //Cursor cursor2 = db2.rawQuery(sql2, null);
                int count = cursor.getCount();
                Log.d(TAG, "count" + count);

                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext())
                        Log.d("test", String.format("\n**name = %s, do = %s", cursor.getString(1), cursor.getString(3)));
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
    public String[] hint(){
        String[] time = new String[0];
        int i=0;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from tino ;", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()){
                time[i] = cursor.getString(cursor.getColumnIndex("name"));
                i++;
            }
        } else {
            Log.d(TAG, "not exists");
        }
        cursor.close();
        db.close();

        return time;

    }
    public boolean chkSwitch(String task){
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql="Select do from tino where name ='"+task+"';";
        Cursor cursor = db.rawQuery(sql,null);
        int n=0;
        while(cursor.moveToNext()) {
            n = cursor.getInt(cursor.getColumnIndex("do"));
        }
        if(n==0)
            return false;
        else
            return true;

    }

}