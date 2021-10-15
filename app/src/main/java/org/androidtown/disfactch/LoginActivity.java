package org.androidtown.disfactch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.kakao.sdk.user.UserApiClient;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "사용자";
    private ImageButton btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, (oAuthToken, error) -> {
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error);

                        //로그인 실패 하더라도 넘어가게는 해놨음 > just 확인용
                        Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    } else if (oAuthToken != null) {
                        Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());

                        UserApiClient.getInstance().me((user, meError) -> {
                            if (meError != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", meError);
                            } else {
                                System.out.println("로그인 완료");
                                //로그인 성공시 main으로 넘어감
                                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                                Log.i(TAG, user.toString());
                                {
                                    Log.i(TAG, "사용자 정보 요청 성공" +
                                            "\n회원번호: " + user.getId() +
                                            "\n이메일: " + user.getKakaoAccount().getEmail());
                                }
                            }
                            return null;
                        });

                    }
                    return null;
                });


                //btn_start 누르면 MainActivity로 이동
                Button btn_start = (Button) findViewById(R.id.btn_start);
                btn_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}