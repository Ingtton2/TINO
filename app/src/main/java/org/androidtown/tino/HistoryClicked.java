package org.androidtown.tino;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HistoryClicked extends Activity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.history_item);

        Intent intent =getIntent();

        TextView name=(TextView)findViewById(R.id.s_Name);
        TextView hour=(TextView)findViewById(R.id.sHour);
        TextView min=(TextView)findViewById(R.id.sMinute);
        String schedule=intent.getStringExtra("sName");
        name.setText("schedule");
        if(!schedule.equals("")){
            name.setText(schedule);
        }

        hour.setText(intent.getStringExtra("sHour"));
        min.setText(intent.getStringExtra("sMinute"));
    }
}