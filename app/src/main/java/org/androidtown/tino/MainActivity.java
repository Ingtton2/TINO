package org.androidtown.tino;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

import org.androidtown.tino.MultiAlarm.database.DataBaseManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat2 = new SimpleDateFormat("yyyy 년 MM 월 dd 일");
    String end;
    TextView textDate;
    TextView textnowtime;
    TextView textTime;
    TextView Remaintime;
    TextView textView0;
    ProgressBar bar;
    ProgressHandler handler;
    boolean isRunning = false;
    Button btnNew;
    Button btnExist;
    String wake;

    private RecyclerView recyclerView;
    private Button btn_add;

    private ArrayList<Model> item_list = new ArrayList<>();
    private ModelAdapter mAdapter;
    String res;

    Handler handler1 = new Handler() { //interaction with left dog
        @Override
        public void handleMessage(Message msg) {
            updateThread(); //display the state of the left side
        }
    };

    public static final String TAG = "GPSListener";
    TextView weatherText;
    Button btn;
    double latitude, longitude;
    ImageView weatherIcon;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherText = findViewById(R.id.weatherText);
        weatherIcon = findViewById(R.id.weatherIcon);
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        } else {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
        }

        //Instantiate Class With Your ApiKey As The Parameter
        OpenWeatherMapHelper helper = new OpenWeatherMapHelper("9d0ecaac6ad18c1a3b072f7c87ee845c");

        //Set Units
        helper.setUnits(Units.IMPERIAL);

        //Set lang
        helper.setLang(Lang.ENGLISH);

        helper.getCurrentWeatherByGeoCoordinates(latitude, longitude, new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                String description = currentWeather.getWeather().get(0).getDescription();
                int temperature = (int)((currentWeather.getMain().getTempMax() - 32.0) / 1.8);
                Log.d(TAG, "Coordinates: " + currentWeather.getCoord().getLat() + ", "+currentWeather.getCoord().getLon() +"\n"
                        +"Weather Description: " + currentWeather.getWeather().get(0).getDescription() + "\n"
                        +"Temperature: " + ((currentWeather.getMain().getTempMax() - 32.0) / 1.8) +"\n"
                        +"City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()
                );
                setting(description, temperature);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v(TAG, throwable.getMessage());
            }
        });
        textDate = (TextView)findViewById(R.id.textDate);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        String getNow = mFormat2.format(date);
        textDate.setText(getNow);

        textTime = (TextView)findViewById(R.id.textTime);
        Remaintime = (TextView)findViewById(R.id.textRemaintime);
        String destHour=null;
        String destMin=null;

        SQLiteDatabase db, db2;
        String sql, sql2;
        final BmDB helper2 = new BmDB(this);
        db = helper2.getReadableDatabase();
        sql = "Select hour, min from bookmark;";

        Cursor cursor = db.rawQuery(sql,null);
        final int last = cursor.getCount();
        Log.d("last", String.format("%s",last));
        try{
            if(cursor != null || last>0) {
                for (int i = 0; i < last; i++) {
                    cursor.moveToNext();
                    destHour = cursor.getString(cursor.getColumnIndex("hour"));
                    Log.d("hour", destHour);
                    destMin = cursor.getString(cursor.getColumnIndex("min"));
                }
            }
            if(last == 0){
                Remaintime.setText("");
                textTime.setText("아직 스케쥴이 없네!");
            }
        }finally{
            db.close();
            cursor.close();
        }
        end = destHour + ":" + destMin + ":" + "00";
        Log.d("cheeek",end);

        String wakeHour = null;
        String wakeMinute = null;
        textView0 = (TextView)findViewById(R.id.textView0);
        final DataBaseManager helper3 = new DataBaseManager(this);
        db2 = helper3.getReadableDatabase();
        sql2="Select hour, minute from alarm where alarm_name ='기상';";

        Cursor cursor2 = db2.rawQuery(sql2,null);
        final int last2 = cursor2.getCount();
        Log.d("last2", String.format("%s",last));
        try{
            if(cursor2 != null || last2>0) {
                for (int i = 0; i < last2; i++) {
                    cursor2.moveToNext();
                    wakeHour = cursor2.getString(cursor2.getColumnIndex("hour"));
                    wakeMinute = cursor2.getString(cursor2.getColumnIndex("minute"));
                }
            }
            else{
                bar.setProgress(0);
                textView0.setText("...");
            }
        }finally{
            db2.close();
            cursor2.close();
        }
        wake = wakeHour + ":" + wakeMinute + ":" + "00";
        Log.d("WakeupTime",wake);

        textView0 = (TextView)findViewById(R.id.textView0);
        bar = (ProgressBar)findViewById(R.id.bar);
        bar.setScaleY(8f);

        handler = new ProgressHandler();

        btnNew = (Button)findViewById(R.id.btnNew);
        btnExist = (Button)findViewById(R.id.btnExist);

        btnNew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, AddScheduleActivity.class);
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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btn_add= (Button) findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ModelAdapter(item_list);
        recyclerView.setAdapter(mAdapter);


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        final LinearLayout linear1 = findViewById(R.id.linear1);
        final LinearLayout linear2 = findViewById(R.id.linear2);
        final LinearLayout linear3 = findViewById(R.id.linear3);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        switch(menuItem.getItemId()) {
                            case R.id.action_home:
                                linear1.setVisibility(View.INVISIBLE);
                                linear2.setVisibility(View.VISIBLE);
                                linear3.setVisibility(View.INVISIBLE);
                                break;
                            case R.id.action_schedule:
                                linear2.setVisibility(View.INVISIBLE);
                                linear1.setVisibility(View.VISIBLE);
                                linear3.setVisibility(View.INVISIBLE);
                                replaceFragment(ScheduleFragment.newInstance());
                                break;
                            case R.id.action_check:
                                linear2.setVisibility(View.INVISIBLE);
                                linear1.setVisibility(View.INVISIBLE);
                                linear3.setVisibility(View.VISIBLE);
                                //replaceFragment(CheckFragment.newInstance());
                                break;
                            case R.id.action_more:
                                linear2.setVisibility(View.INVISIBLE);
                                linear1.setVisibility(View.VISIBLE);
                                linear3.setVisibility(View.INVISIBLE);
                                replaceFragment(MoreFragment.newInstance());
                                break;
                        }
                        return true;
                    }
                }
        );

    }

    public void show (){
        final EditText edittext = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("할일을 입력해줘!");
        builder.setView(edittext);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        //Toast.makeText(getApplicationContext(),edittext.getText().toString(), Toast.LENGTH_LONG).show();
                        res = edittext.getText().toString();
                        item_list.add(new Model(res, false));
                        mAdapter.notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    @Override
    protected void onStart(){
        super.onStart();
        //bar.setProgress(0);
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                try{ while(true){
                    Thread.sleep(100);
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                }}
                catch(Exception ex){
                    Log.e("MainActivity","Exception in processing message.",ex);
                }}
        });
        isRunning=true;
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                try{
                    while(true){
                        Thread.sleep(1000);
                        Message msg = handler1.obtainMessage();
                        handler1.sendMessage(msg);
                    }}
                catch(Exception ex){
                    Log.e("MainActivity","Exception in processing message.",ex);
                }}
        });
        thread2.start();

    }

    public void updateThread(){
        SimpleDateFormat mFormat  = new SimpleDateFormat("HH:mm:ss");
        String start = mFormat.format(new Date()) ;
        Date startDate = null;
        Date endDate =null;

        try {
            startDate = mFormat.parse(start);
            endDate = mFormat.parse(end);
            long diff = endDate.getTime() - startDate.getTime();
            Log.d("diff",String.format("%s",diff));
            if(diff >0){
                int seconds = (int) (diff / 1000) % 60 ;
                int minutes = (int) ((diff / (1000*60)) % 60);
                int hours   = (int) ((diff / (1000*60*60)) % 24);
                textTime.setText(String.format("%d 시간 %d 분 %d초야",hours,minutes,seconds));
            }
            if(diff < 0) {
                diff += 24 * 60 * 60 * 1000;
                int seconds = (int) (diff / 1000) % 60;
                int minutes = (int) ((diff / (1000 * 60)) % 60);
                int hours = (int) ((diff / (1000 * 60 * 60)) % 24);
                textTime.setText(String.format("%d 시간 %d 분 %d초야", hours, minutes, seconds));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        isRunning = false;
    }

    public class ProgressHandler extends Handler {
        public void handleMessage(Message msg){
            SimpleDateFormat mFormat  = new SimpleDateFormat("HH:mm:ss");
            String start = mFormat.format(new Date()) ;
            Date startDate = null;
            Date endDate =null;
            Date wakeDate = null;
            try {
                startDate = mFormat.parse(start);
                endDate = mFormat.parse(end);
                wakeDate = mFormat.parse(wake);

                long up = startDate.getTime() - wakeDate.getTime();
                long down = endDate.getTime() - wakeDate.getTime();
                Log.d("up",String.format("%s",up));
                Log.d("down",String.format("%s",down));
                bar.setMax((int)down);

                bar.incrementProgressBy(1);

                if(bar.getProgress()==bar.getMax()){
                    textView0.setText(String.format("약속시간이야! 좋은 하루 보내!"));
                }
                if(up <0) {
                    bar.setProgress(0);
                    textView0.setText("아직 쉬어도 돼!");
                }
                else{
                    textView0.setText(String.format("%s", (float)bar.getProgress()));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.linear1, fragment).commit();
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };


    public void setting(String description, int temperature) {
        weatherText.setText(String.format("%s℃", temperature));

        if(description.equalsIgnoreCase("clear sky")) {
            weatherIcon.setImageResource(R.drawable.clearsky);
        }

        else if(description.equalsIgnoreCase("few clouds")) {
            weatherIcon.setImageResource(R.drawable.fewclouds);
        }

        else if(description.equalsIgnoreCase("scattered clouds")) {
            weatherIcon.setImageResource(R.drawable.scatteredclouds);
        }

        else if(description.equalsIgnoreCase("broken clouds")) {
            weatherIcon.setImageResource(R.drawable.brokenclouds);
        }

        else if(description.equalsIgnoreCase("shower rain")) {
            weatherIcon.setImageResource(R.drawable.rain);
        }

        else if(description.equalsIgnoreCase("rain")) {
            weatherIcon.setImageResource(R.drawable.rain);
        }

        else if(description.equalsIgnoreCase("thunderstorm")) {
            weatherIcon.setImageResource(R.drawable.thunderstorm);
        }

        else if(description.equalsIgnoreCase("Snow")) {
            weatherIcon.setImageResource(R.drawable.snow);
        }

        else if(description.equalsIgnoreCase("mist")) {
            weatherIcon.setImageResource(R.drawable.mist);
        }

        else if(description.equalsIgnoreCase("overcast clouds")) {
            weatherIcon.setImageResource(R.drawable.brokenclouds);
        }

        else if(description.equalsIgnoreCase("light rain")) {
            weatherIcon.setImageResource(R.drawable.lightrain);
        }

        else if(description.equalsIgnoreCase("light intensity drizzle rain")) {
            weatherIcon.setImageResource(R.drawable.rain);
        }

        else if(description.equalsIgnoreCase("proximity thunderstorm")) {
            weatherIcon.setImageResource(R.drawable.prothunderstorm);
        }

        else if(description.equalsIgnoreCase("haze")) {
            weatherIcon.setImageResource(R.drawable.mist);
        }
    }
}