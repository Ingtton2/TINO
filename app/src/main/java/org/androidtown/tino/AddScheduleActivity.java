package org.androidtown.tino;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddScheduleActivity extends AppCompatActivity {
    TimePicker schedule_timepicker;
    Context context;
    Button btnOk;
    BmDB helper;
    EditText schedule_name;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        schedule_timepicker = findViewById(R.id.time_picker);
        helper=new BmDB(this);
        schedule_name = findViewById(R.id.schedule_name);

        btnOk = (Button)findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if (schedule_name.equals(""))
                    name = "Schedule";
                else
                    name = schedule_name.getText().toString();

                int hour = schedule_timepicker.getHour();
                int minute = schedule_timepicker.getMinute();
                int id=helper.check();
                helper.insert(id,name,hour,minute,0,0);

                SQLiteDatabase db = helper.getReadableDatabase();
                Cursor cursor = db.rawQuery("Select * from bookmark", null);
                //Cursor cursor2 = db2.rawQuery(sql2, null);
                int count = cursor.getCount();
                Log.d("schedule", "count" + count);

                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext())
                        Log.d("schedule", String.format("\n**id = %s,name = %s, hour = %s,minute = %s", cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                } else {
                    Log.d("schedule", "not exists");
                }
                cursor.close();

                Intent intent = new Intent(AddScheduleActivity.this, ScheduleActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);

            }
        });
    }
}
