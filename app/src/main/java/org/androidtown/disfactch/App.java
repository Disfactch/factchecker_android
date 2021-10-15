package org.androidtown.disfactch;


import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 네이티브 앱 키로 초기화
        KakaoSdk.init(this, "785eafb7631beeb2174384ab75d1e3b1");
    }
}
