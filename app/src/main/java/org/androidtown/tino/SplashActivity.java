package org.androidtown.tino;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SharedPreferences shPref=getSharedPreferences("MyPref",0);
        int countValue=shPref.getInt("Count",-100);

        if(countValue==-100)
        {
            countValue=1;
            Intent intent = new Intent(this, LazyActivity.class);
            startActivity(intent);
            SharedPreferences.Editor prefEditor=shPref.edit();
            prefEditor.putInt("Count",countValue);
            prefEditor.commit();
            finish();
        }
        else
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            SharedPreferences.Editor prefEditor=shPref.edit();
            prefEditor.putInt("Count",countValue);
            prefEditor.commit();
            finish();
        }

    }
}
