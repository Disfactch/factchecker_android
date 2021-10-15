package org.androidtown.disfactch;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends AppCompatActivity {
    //private SessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            // 로그인 화면으로 이동
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);


    }
}

