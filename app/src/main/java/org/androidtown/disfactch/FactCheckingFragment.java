/**
 * SPDX-FileCopyrightText: © 2021 Subin Kim <subinga18@naver.com>
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.androidtown.disfactch;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.kakao.sdk.link.LinkClient;
import com.kakao.sdk.template.model.Link;
import com.kakao.sdk.template.model.TextTemplate;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FactCheckingFragment extends Fragment implements CircleProgressBar.ProgressFormatter {


    private static final String DEFAULT_PATTERN = "%d%%";

    RelativeLayout progress;
    BrowseFragment web;


    EditText link;
    CircleProgressBar cbProv;// 자극성 기사 progress bar
    CircleProgressBar cbPub; // 홍보성 기사
    CircleProgressBar cbSa; // 동일 기사
    CircleProgressBar cbFi; // 최종 신뢰도


    Button btnKakaoMsg;
    String url;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.fragment_fact_checking, container, false);
        cbProv = v.findViewById(R.id.cb_provocative);
        cbProv.setProgress(70);  // 해당 퍼센트를 적용_ 나중엔 결과값으로 넣을 것

        cbPub = v.findViewById(R.id.cb_publicity);
        cbPub.setProgress(30);  // 해당 퍼센트를 적용_ 나중엔 결과값으로 넣을 것

        cbSa = v.findViewById(R.id.cb_same);
        cbSa.setProgress(88);  // 해당 퍼센트를 적용_ 나중엔 결과값으로 넣을 것

        cbFi = v.findViewById(R.id.cb_final);
        cbFi.setProgress(100);  // 해당 퍼센트를 적용_ 나중엔 결과값으로 넣을 것

        // progress bar를 invisible 하게 두다가 링크 넣고 버튼 누르면 보여주기.

        progress = v.findViewById(R.id.rl_progress);
        progress.setVisibility(View.INVISIBLE);

        link = v.findViewById(R.id.rl_search_edit);

        Button rlBtn = (Button)v.findViewById(R.id.rl_search_btn);
        Button rlReadBtn = (Button)v.findViewById(R.id.rl_search_read_btn);

        rlBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                // creating a client
                OkHttpClient okHttpClient = new OkHttpClient();

                // link를 base64 인코딩하여 전달
                String linkForParameter = Base64.getEncoder().encodeToString(link.toString().getBytes(StandardCharsets.UTF_8));
                // building a request
                Request request = new Request.Builder().url("http://110.76.73.55:8080/factchecker/factchecked/rest/"+linkForParameter).build();

                // making call asynchronously
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    // called if server is unreachable
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    // called if we get a
                    // response from the server
                    public void onResponse(
                            @NotNull Call call,
                            @NotNull Response response)
                            throws IOException {

                        String bodyString = response.body().string();
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(bodyString);
                        } catch (Exception e) {
                            e.printStackTrace();
                            jsonObject = new JSONObject();
                        }

                        int re1 = jsonObject.optInt("reliability_1", 0);
                        int re2 = jsonObject.optInt("reliability_2", 0);
                        int re3 = jsonObject.optInt("reliability_3", 0);
                        int re4 = jsonObject.optInt("reliability_final", 0);

                        new Handler(Looper.getMainLooper()).post(() -> {
                            cbProv.setProgress(re1);  // 해당 퍼센트를 적용_ 나중엔 결과값으로 넣을 것
                            cbPub.setProgress(re2);  // 해당 퍼센트를 적용_ 나중엔 결과값으로 넣을 것
                            cbSa.setProgress(re3);  // 해당 퍼센트를 적용_ 나중엔 결과값으로 넣을 것
                            cbFi.setProgress(re4);
                            progress.setVisibility(View.VISIBLE);
                        });
                    }
                });
            }
        });



        rlReadBtn.setOnClickListener(view -> {
            Bundle bundle = new Bundle(); // 번들을 통해 값 전달
            bundle.putString("link",link.getText().toString()); //번들에 넘길 값 저장

            if (getActivity() != null) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                web = new BrowseFragment();//프래그먼트2 선언
                web.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
                transaction.replace(R.id.main_frame, web);
                // 뒤로 가기 누르면 전 화면으로 돌려줌.
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnKakaoMsg = (Button)v.findViewById(R.id.btn_link);
        btnKakaoMsg.setOnClickListener(view -> {

            /**
             * 연합 , 네이버, 다음, 중앙, 동아, 경향, 국민, 뉴스1, 뉴시스, 문화 이렇게 10개 회사만 가능.
             */

            TextTemplate textTemplate = new TextTemplate("기사를 확인해보세요 !~", new Link(url, url));
            LinkClient.getInstance().defaultTemplate(view.getContext(), textTemplate,null, (linkResult, throwable) -> {
                if (throwable != null) {
                    Log.e("TAG", "카카오링크 보내기 실패", throwable);
                } else if (linkResult != null) {
                    view.getContext().startActivity(linkResult.getIntent());

                    // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.w("TAG", "Warning Msg: "+ linkResult.getWarningMsg());
                    Log.w("TAG", "Argument Msg: "+ linkResult.getArgumentMsg());
                }
                return null;
            });
        });

        return v;
    }

    @Override
    public CharSequence format(int progress, int max) {
        return String.format(Locale.getDefault(), DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
    }
}