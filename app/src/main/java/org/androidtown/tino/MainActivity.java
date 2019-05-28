package org.androidtown.tino;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy 년 MM 월 dd 일");
    SimpleDateFormat mFormat2 = new SimpleDateFormat("HH 시간 mm분 ss초");
    TextView textDate;
    TextView textnowtime;
    TextView textView0;
    ProgressBar bar;
    ProgressHandler handler;
    boolean isRunning = false;
    Button btnNew;
    Button btnExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDate = (TextView)findViewById(R.id.textDate);
        textnowtime = (TextView)findViewById(R.id.textRemaintime);

        textView0 = (TextView)findViewById(R.id.textView0);
        bar = (ProgressBar)findViewById(R.id.bar);
        bar.setScaleY(8f);

        handler = new ProgressHandler();

        btnNew = (Button)findViewById(R.id.btnNew);
        btnExist = (Button)findViewById(R.id.btnExist);

        mNow =System.currentTimeMillis();
        mDate = new Date(mNow);
        textDate.setText(mFormat.format(mDate));
        int hour=0;
        int minute=0;
        Intent intent = new Intent(MainActivity.this, AddScheduleActivity.class);
       // intent.getExtras(hour,"scheduleHour");
        //intent.getExtras(minute,"scheduleHour");

        btnNew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                startActivity(intent);

            }
        });

        btnExist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
                startActivity(intent);

            }
        });


        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch(menuItem.getItemId()) {
                            case R.id.action_home:
                                setContentView(R.layout.activity_main);
                                break;
                            case R.id.action_schedule:
                                //replaceFragment(ScheduleFragment.newInstance());
                                break;
                            case R.id.action_check:
                                //testText.setText("Check");
                                break;
                            case R.id.action_more:
                                replaceFragment(WeatherFragment.newInstance());
                                //testText.setText("More");
                                break;
                        }
                        return true;
                    }
                }
        );
    }
    @Override
    protected void onStart(){
        super.onStart();
        bar.setProgress(0);
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                try{for(int i=0; i<20 && isRunning; i++){
                    Thread.sleep(1000);
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                }}
                catch(Exception ex){
                    Log.e("MainActivity","Exception in processing message.",ex);
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
    public class ProgressHandler extends Handler {
        public void handleMessage(Message msg){
            bar.incrementProgressBy(5);

            if(bar.getProgress()==bar.getMax()){
                textView0.setText("약속 시간이 되었어요 !");
            }else{
                textView0.setText(String.format("%s", bar.getProgress()));
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }
}
