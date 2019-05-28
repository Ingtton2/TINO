package org.androidtown.tino;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.androidtown.tino.MultiAlarm.model.Alarm;

public class AddAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        int hour=intent.getIntExtra("Hour",0);
        int min=intent.getIntExtra("Min",0);
        String task=intent.getStringExtra("Task");
        initView(hour, min, task);

    }

    // TODO: this initialize view for this  activity
    private void initView(int hour, int min, String task) {
        Intent intent = new Intent(this, AlarmsetActivity.class);
        // TODO: create alarm
        Alarm alarm = initAlarm(hour, min, task);

        alarm.setId((int) System.currentTimeMillis());
        intent.putExtra("Alarm", alarm);
        // set result to AlarmMainActivity with intent
        setResult(RESULT_OK, intent);
        // finish method is requires if this Activity was started by startActivityForResult
        finish();

    }

    // TODO:  this return alarm set toggle on by default
    private Alarm initAlarm(int hour, int min, String task) {
        // set toggle on by default, 1 is on and 0 is off
        int toggleOn = 1;
        Alarm alarm;
        String name1 = null;
        // get current time from timePicker
        int hour_x = 0;
        int minute_x = 0;

        try {
            hour_x = hour;
            minute_x = min;
            // get name for alarm from EditText
            String name = task;

            if (name.length() == 0) {
                // if alarm'name is not inputted set the EditText'hint for alarm's name by default
                name1 = null;
            } else {
                name1 = name;
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        // initialize and assign alarm values
        alarm = new Alarm(hour_x, minute_x, name1, toggleOn);
        Log.d("sll", "name: " + name1 + ", time: " + hour_x+minute_x);

        return alarm;
    }
}