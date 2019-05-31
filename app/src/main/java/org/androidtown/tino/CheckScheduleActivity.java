package org.androidtown.tino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kwabenaberko.openweathermaplib.models.common.Main;

public class CheckScheduleActivity extends AppCompatActivity {

    Button checkOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_schedule);

        checkOk = findViewById(R.id.checkOk);

        checkOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CheckScheduleActivity.this, AlarmsetActivity.class);
                startActivity(intent);
            }
        });

    }
}
