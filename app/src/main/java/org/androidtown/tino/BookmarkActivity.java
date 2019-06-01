package org.androidtown.tino;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


import java.util.ArrayList;


public class BookmarkActivity extends Activity{

    ArrayList<HistoryItem> data= new ArrayList<HistoryItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        ListView listView = (ListView) findViewById(R.id.listview);
        BmDB helper = new BmDB(this);

        data = new ArrayList<HistoryItem>();

        SQLiteDatabase db;
        String sql;
        db = helper.getReadableDatabase();
        sql = "Select * from bookmark;";
        Cursor cursor = db.rawQuery(sql, null);
        final int count = cursor.getCount();
        try {
            if (cursor != null) {
                for (int i = 0; i < count; i++) {
                    cursor.moveToNext();
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String hour = String.valueOf(cursor.getInt(cursor.getColumnIndex("hour")));
                    String min = String.valueOf(cursor.getInt(cursor.getColumnIndex("min")));
                    int d_hour=cursor.getInt(cursor.getColumnIndex("d_hour"));
                    int d_min=cursor.getInt(cursor.getColumnIndex("d_min"));
                    HistoryItem historyItem = new HistoryItem(name, hour, min,d_hour,d_min);

                    data.add(historyItem);
                }
            }
        } finally {
            db.close();
            cursor.close();
        }
        HistoryAdapter adapter = new HistoryAdapter(this, R.layout.history_item, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(listener);
    }
    OnItemClickListener listener=new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(getApplication(),AlarmsetActivity.class);
            int sh=Integer.parseInt(data.get(position).getHour());
            int sm=Integer.parseInt(data.get(position).getMinute());
            int h=data.get(position).getdHour();
            int m=data.get(position).getdMin();

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

            startActivity(intent);
        }
    };

}
