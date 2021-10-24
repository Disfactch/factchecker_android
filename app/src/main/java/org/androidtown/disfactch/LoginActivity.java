package org.androidtown.disfactch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageButton btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
                Toast.makeText(LoginActivity.this, "로그인이 실패하였습니다.", Toast.LENGTH_SHORT).show();

            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());

                UserApiClient.getInstance().me((user, meError) -> {
                    if (meError != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", meError);
                    } else {
                        Log.i(TAG, "로그인 완료");
                        //로그인 성공시 main으로 넘어감
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        Log.i(TAG, user.toString());
                        Account account = user.getKakaoAccount();
                        if (account != null) {
                            Log.i(TAG, "사용자 정보 요청 성공" +
                                    "\n회원번호: " + user.getId() +
                                    "\n이메일: " + account.getEmail());
                        }
                    }
                    return null;
                });
            }
            return null;
        }));

        //btn_start 누르면 MainActivity로 이동
        Button btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }
}
