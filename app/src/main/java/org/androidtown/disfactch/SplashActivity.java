package org.androidtown.disfactch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaSession2;
import android.os.Bundle;
import android.os.Handler;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Button;


import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;


public class SplashActivity extends AppCompatActivity {
    //private SessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //btn_start 누르면 MainActivity로 이동
        Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

/*
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            // 로그인 화면으로 이동
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
*/


        // TODO: 카카오 계정 연동하여 로그인
        // https://baessi.tistory.com/148
/*
        //카카오 로그인 init
        if (KakaoSDK.getAdapter()==null){
            KakaoSDK.init(new SplashActivity.KakaoSDKAdapter());
        }

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)){
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback{
        @Override
        public void onSessionOpened(){
            redirectSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception){
            if (exception != null){
                Logger.e(exception);
            }
        }
    }



    protected void redirectSignupActivity(){
        final Intent intent= new Intent(this, FactCheckingFragment.class);

        startActivity(intent);
        finish();
    }
 */

/*
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

 */


    }
}