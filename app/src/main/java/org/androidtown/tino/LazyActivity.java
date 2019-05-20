package org.androidtown.tino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LazyActivity extends AppCompatActivity {
    Button btn3,btn5,btn8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy);

        btn3=(Button)findViewById(R.id.slow);
        btn5=(Button)findViewById(R.id.mid);
        btn8=(Button)findViewById(R.id.fast);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //3분 선택 DB에 넣기.

            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //5분 선택 DB에 넣기.

            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //8분 선택 DB에 넣기.

            }
        });
    }
}
