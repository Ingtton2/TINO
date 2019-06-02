package org.androidtown.tino;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.kwabenaberko.openweathermaplib.models.common.Main;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;

public class CheckSchedule2Activity extends AppCompatActivity {

    TextView checkInputHour, checkInputMinute;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_schedule2);
        /*ArrayList<String> arraylist = new ArrayList<String>();

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

        CustomAdapter customAdapter = new CustomAdapter(this);
        final TinoDB helper =new TinoDB(this);
        db = helper.getReadableDatabase();
        sql = "Select name, time from tino where time>=1 and do =1 ORDER BY id DESC;";

        Cursor cursor = db.rawQuery(sql, null);
        final int count=cursor.getCount();
        try {
            if (cursor != null) {
                for (int i = 0; i < count; i++) {
                    cursor.moveToNext();
                    String task = cursor.getString(cursor.getColumnIndex("name"));
                    String time = cursor.getString(cursor.getColumnIndex("time"));

                    customAdapter.addItem(task, time);
                    listView.setAdapter(customAdapter);
                    //arraylist.add(task);
                    Log.d("sls", "name: " + task );
                }
            }
        } finally {
            db.close();
            cursor.close();
        }

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
        }*/

    }



}
