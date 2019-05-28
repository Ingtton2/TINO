package org.androidtown.tino;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

public class WeatherActivity extends AppCompatActivity {

    public static final String TAG = "GPSListener";
    Button btn;
    double latitude, longitude;
    ImageView weatherIcon;
    TextView weatherText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        btn = findViewById(R.id.btn);
        weatherIcon = findViewById(R.id.weatherIcon);
        weatherText = findViewById(R.id.weatherText);

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions( WeatherActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                            0 );
                }
                else {
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    String provider = location.getProvider();
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();

                    /*textView.setText("위치정보 : " + provider + "\n" +
                            "위도 : " + longitude + "\n" +
                            "경도 : " + latitude + "\n" +
                            "고도  : " + altitude);*/

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
                        double temperature = (currentWeather.getMain().getTempMax() - 32.0) / 1.8;
                        Log.d("WeatherActivity", description);

                        /*textView.setText("Coordinates: " + currentWeather.getCoord().getLat() + ", "+currentWeather.getCoord().getLon() +"\n"
                                +"Weather Description: " + currentWeather.getWeather().get(0).getDescription() + "\n"
                                +"Temperature: " + ((currentWeather.getMain().getTempMax() - 32.0) / 1.8) +"\n"
                                +"City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()
                        );*/

                        //weatherText.setText(temperature + "℃");
                        setting(description, temperature);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.v(TAG, throwable.getMessage());
                    }
                });

            }
        });

    }

    public void setting(String description, double temperature) {
        weatherText.setText(temperature + "℃");

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

        else if(description.equalsIgnoreCase("overcase clouds")) {
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
}
