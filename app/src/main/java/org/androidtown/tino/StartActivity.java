package org.androidtown.tino;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    Button btnCreate;
    Button btnBookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnCreate = (Button)findViewById(R.id.btnCreate);
        btnBookmark = (Button)findViewById(R.id.btnBookmark);

        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });



    }
}
