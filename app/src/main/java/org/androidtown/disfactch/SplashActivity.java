/**
 * SPDX-FileCopyrightText: © 2021 Subin Kim <subinga18@naver.com>
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.androidtown.disfactch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 로그인 화면으로 이동
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 3000);


    }
}

