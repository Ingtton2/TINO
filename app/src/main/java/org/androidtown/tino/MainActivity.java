package org.androidtown.tino;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        testText = findViewById(R.id.testText);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch(menuItem.getItemId()) {
                            case R.id.action_home:
                                testText.setText("Home");
                                break;
                            case R.id.action_schedule:
                                testText.setText("Schedule");
                                break;
                            case R.id.action_check:
                                testText.setText("Check");
                                break;
                            case R.id.action_more:
                                testText.setText("More");
                                break;
                        }
                        return true;
                    }
                }
        );
    }
}
