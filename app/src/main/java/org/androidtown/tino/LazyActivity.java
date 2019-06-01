package org.androidtown.tino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LazyActivity extends AppCompatActivity {
    Button btnFast;
    Button btnMid;
    Button btnSlow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy);

        btnFast = (Button)findViewById(R.id.fast);
        btnMid = (Button)findViewById(R.id.mid);
        btnSlow = (Button)findViewById(R.id.slow);

        btnFast.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                Intent intent = new Intent(LazyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnMid.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                Intent intent = new Intent(LazyActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        btnSlow.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                Intent intent = new Intent(LazyActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}
