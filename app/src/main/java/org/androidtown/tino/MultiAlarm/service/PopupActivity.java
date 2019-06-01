package org.androidtown.tino.MultiAlarm.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.androidtown.tino.MultiAlarm.ultil.Constants;
import org.androidtown.tino.R;

public class PopupActivity extends Activity {
    TextView txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        layoutParams.flags=WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_popup);

        //UI 객체생성
        txtText = (TextView) findViewById(R.id.txtText);

        //데이터 가져오기
        Intent intent = getIntent();
        String task = intent.getStringExtra("task");
        txtText.setText(task + " 시간!!");

    }

    //확인 버튼 클릭
    public void mOnClose(View v) {

        // if alarm is triggered and ringing, send this alarm detail to AlarmReceiver
        Intent sendIntent =new Intent(this, AlarmService.class);
        // then AlarmReceiver send detail to service to stop music
        sendIntent.putExtra("ON_OFF", Constants.OFF_INTENT);
        sendIntent.putExtra("AlarmId",-1);
        startService(sendIntent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
