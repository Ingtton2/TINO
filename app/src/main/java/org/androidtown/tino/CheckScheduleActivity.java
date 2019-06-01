package org.androidtown.tino;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class CheckScheduleActivity extends AppCompatActivity {

    Button checkOk;
    Button checkOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_schedule);
        ArrayList<String> arraylist = new ArrayList<String>();

        checkOk = findViewById(R.id.checkOk);
        checkOrder = findViewById(R.id.checkOrder);
        SQLiteDatabase db;
        String sql;
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

        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(Adapter);




        checkOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CheckScheduleActivity.this, AlarmsetActivity.class);
                startActivity(intent);
            }
        });

        checkOrder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CheckScheduleActivity.this, SetOrderActivity.class);
                startActivity(intent);
            }
        });

    }
}