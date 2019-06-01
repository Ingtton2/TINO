package org.androidtown.tino;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

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

    public static final String TAG = "GPSListener";
    TextView weatherText;
    Button btn;
    double latitude, longitude;
    ImageView weatherIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        //Intent intent = new Intent(MainActivity.this, AddScheduleActivity.class);
        //intent.getExtras(hour,"scheduleHour");
        //intent.getExtras(minute,"scheduleHour");

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

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        final LinearLayout linear1 = findViewById(R.id.linear1);
        final LinearLayout linear2 = findViewById(R.id.linear2);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        switch(menuItem.getItemId()) {
                            case R.id.action_home:
                                linear1.setVisibility(View.INVISIBLE);
                                linear2.setVisibility(View.VISIBLE);
                                break;
                            case R.id.action_schedule:
                                linear2.setVisibility(View.INVISIBLE);
                                linear1.setVisibility(View.VISIBLE);
                                replaceFragment(ScheduleFragment.newInstance());
                                break;
                            case R.id.action_check:
                                linear2.setVisibility(View.INVISIBLE);
                                linear1.setVisibility(View.VISIBLE);
                                //replaceFragment(CheckFragment.newInstance());
                                break;
                            case R.id.action_more:
                                linear2.setVisibility(View.INVISIBLE);
                                linear1.setVisibility(View.VISIBLE);
                                replaceFragment(MoreFragment.newInstance());
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
