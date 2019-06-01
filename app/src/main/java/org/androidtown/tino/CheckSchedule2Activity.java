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

public class CheckSchedule2Activity extends AppCompatActivity {

    TextView checkInputHour, checkInputMinute;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_schedule2);
        ArrayList<String> arraylist = new ArrayList<String>();

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

    }
}
