package org.androidtown.tino;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    ImageView weatherIcon;
    TextView weatherText;
    Button btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherIcon = findViewById(R.id.weatherIcon);
        weatherText = findViewById(R.id.weatherText);
        btn = findViewById(R.id.btn);
    }

}