package org.androidtown.tino;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class AddScheduleActivity extends AppCompatActivity {
    TimePicker schedule_timepicker;
    Context context;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        schedule_timepicker = findViewById(R.id.time_picker);

        btnOk = (Button)findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                int hour = schedule_timepicker.getHour();
                int minute = schedule_timepicker.getMinute();

                Intent intent = new Intent(AddScheduleActivity.this, ScheduleActivity.class);
                startActivity(intent);

                Intent intent2 = new Intent(AddScheduleActivity.this, MainActivity.class);
                intent2.putExtra("scheduleHour",hour);
                intent2.putExtra("scheduleMinute",minute);

            }
        });
    }
}
