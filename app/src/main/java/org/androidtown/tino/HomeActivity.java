package org.androidtown.tino;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    TextView textView0;
    ProgressBar bar;
    Handler handler;
    ProgressRunnable runnable;
    boolean isRunning = false;
    Button btnNew;
    Button btnExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView0 = (TextView)findViewById(R.id.textView0);
        bar = (ProgressBar)findViewById(R.id.bar);

        handler = new Handler();
        runnable = new ProgressRunnable();

        btnNew = (Button)findViewById(R.id.btnNew);
        btnExist = (Button)findViewById(R.id.btnExist);

        btnNew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomeActivity.this, ScheduleActivity.class);
                startActivity(intent);

            }
        });

        btnExist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomeActivity.this, BookmarkActivity.class);
                startActivity(intent);

            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        bar.setProgress(0);
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                try{for(int i=0; i<20 && isRunning; i++){
                    Thread.sleep(1000);
                    handler.post(runnable);
                }}
                catch(Exception ex){
                    Log.e("HomeActivity","Exception in processing message.",ex);
                }}
        });
        isRunning = true;
        thread1.start();
    }

    @Override
    protected void onStop(){
        super.onStop();
        isRunning = false;
    }
    public class ProgressRunnable implements Runnable {
       public void run(){
           bar.incrementProgressBy(5);

           if(bar.getProgress()==bar.getMax()){
               textView0.setText("Done");
           }else{
               textView0.setText("Working..."+bar.getProgress());
           }
        }
    }
}
