/**
 * SPDX-FileCopyrightText: © 2021 Subin Kim <subinga18@naver.com>
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.androidtown.disfactch;


import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class App extends Application {
    private static final String KAKAO_SDK_APP_KEY = "785eafb7631beeb2174384ab75d1e3b1";

    @Override
    public void onCreate() {
        super.onCreate();

        // 네이티브 앱 키로 초기화
        KakaoSdk.init(this, KAKAO_SDK_APP_KEY);
    }
}
