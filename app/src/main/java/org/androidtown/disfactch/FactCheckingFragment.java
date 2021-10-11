package org.androidtown.disfactch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.dinuscxj.progressbar.CircleProgressBar;


public class FactCheckingFragment extends Fragment implements CircleProgressBar.ProgressFormatter {

    private static final String DEFAULT_PATTERN = "%d%%";

    RelativeLayout progress;
    CircleProgressBar cb_prov;// 자극성 기사 progress bar
    CircleProgressBar cb_pub; // 홍보성 기사
    CircleProgressBar cb_sa; // 동일 기사
    CircleProgressBar cb_fi; // 최종 신뢰도
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.fragment_fact_checking, container, false);
        cb_prov= v.findViewById(R.id.cb_provocative);
        cb_prov.setProgress(70);  // 해당 퍼센트를 적용_ 나중엔 결과값으로 넣을 것

        cb_pub= v.findViewById(R.id.cb_publicity);
        cb_pub.setProgress(30);  // 해당 퍼센트를 적용_ 나중엔 결과값으로 넣을 것

        cb_sa= v.findViewById(R.id.cb_same);
        cb_sa.setProgress(88);  // 해당 퍼센트를 적용_ 나중엔 결과값으로 넣을 것

        cb_fi= v.findViewById(R.id.cb_final);
        cb_fi.setProgress(100);  // 해당 퍼센트를 적용_ 나중엔 결과값으로 넣을 것

        // progress bar를 invisible 하게 두다가 링크 넣고 버튼 누르면 보여주기.

        progress = v.findViewById(R.id.rl_progress);
        progress.setVisibility(View.INVISIBLE);

        Button rl_btn = (Button)v.findViewById(R.id.rl_search_btn);
        rl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }

    @Override
    public CharSequence format(int progress, int max) {
        return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
    }
}