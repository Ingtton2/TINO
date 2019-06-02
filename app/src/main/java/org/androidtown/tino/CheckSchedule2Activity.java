package org.androidtown.tino;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckSchedule2Activity extends AppCompatActivity {

    TextView checkInputHour, checkInputMinute;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_schedule2);
        ArrayList<String> arraylist = new ArrayList<String>();
        ArrayList<String> alist = new ArrayList<String>();

        checkInputHour = findViewById(R.id.checkInputHour);
        checkInputMinute = findViewById(R.id.checkInputMinute);
        listView = findViewById(R.id.listView);

        SQLiteDatabase db, db2;
        String sql, sql2;

        final TinoDB helper =new TinoDB(this);
        db = helper.getReadableDatabase();
        sql = "Select * from tino where time>=1 and do =1 ORDER BY id DESC;";

        Cursor cursor = db.rawQuery(sql, null);
        final int count=cursor.getCount();
        try {
            if (cursor != null) {
                for (int i = 0; i < count; i++) {
                    cursor.moveToNext();
                    String task = cursor.getString(cursor.getColumnIndex("name"));
                    arraylist.add(task);
                    Log.d("sls", "name: " + task );
                }
            }
        } finally {
            db.close();
            cursor.close();
        }

        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);
        listView.setAdapter(Adapter);

        final BmDB bookmark = new BmDB(this);
        db2 = bookmark.getReadableDatabase();
        sql2 = "select d_hour, d_min from bookmark;";

        Cursor cursor2 = db2.rawQuery(sql2, null);
        final int count2 = cursor2.getCount();
        try {
            if (cursor2!=null){
               for (int i = 0; i < count2; i++) {
                   cursor2.moveToNext();
                   String d_hour = cursor2.getString(cursor2.getColumnIndex("d_hour"));
                   String d_min = cursor2.getString(cursor2.getColumnIndex("d_min"));
                   checkInputHour.setText(d_hour);
                   checkInputMinute.setText(d_min);
               }
            }
        } finally {
            db2.close();
            cursor2.close();
        }
        /*
        SQLiteDatabase db3;
        ArrayAdapter<String> Adapter2;
        Adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, alist);

        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(Adapter2);

        ArrayList<HashMap<String,String>> Lists =new ArrayList<HashMap<String,String>> ();
        HashMap<String, String> item;
        item = new HashMap<String, String>;,
        item.put("item1", name);
        item.put("item2", time);
        Lists.se


        TinoDB tinoDB = new TinoDB(this);
        db3 = tinoDB.getReadableDatabase();
        String sql3 = "select name, time from tino ORDER BY id DESC;";

        Cursor cursor3 = db3.rawQuery(sql3, null);
        final int count3 = cursor3.getCount();
        try {
            if (cursor3!=null){
                for (int i = 0; i < count3; i++) {
                    cursor3.moveToNext();
                    String name = cursor3.getString(cursor3.getColumnIndex("name"));
                    String time = cursor3.getString(cursor3.getColumnIndex("time"));
                    item.put("item1",name);
                    item.put("item2",time);
                    alist.add(item);
                }
            }
        } finally {
            db3.close();
            cursor3.close();
        }                    */
    }
}
