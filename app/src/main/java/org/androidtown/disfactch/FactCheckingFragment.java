package org.androidtown.disfactch;

import android.content.Context;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.kakao.sdk.template.model.FeedTemplate;

import java.util.HashMap;
import java.util.Map;

public class FactCheckingFragment extends Fragment implements CircleProgressBar.ProgressFormatter {


    private static final String DEFAULT_PATTERN = "%d%%";

    RelativeLayout progress;
    MainActivity mainActivity;
    BrowseFragment web;


    EditText link;
    CircleProgressBar cb_prov;// 자극성 기사 progress bar
    CircleProgressBar cb_pub; // 홍보성 기사
    CircleProgressBar cb_sa; // 동일 기사
    CircleProgressBar cb_fi; // 최종 신뢰도


/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    // 메인 액티비티에서 내려온다.
    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }
*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        //https://lcw126.tistory.com/284
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
        Button rl_read_btn = (Button)v.findViewById(R.id.rl_search_read_btn);

        rl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
            }
        });

        link = v.findViewById(R.id.rl_search_edit);
        rl_read_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putString("link",link.getText().toString());//번들에 넘길 값 저장
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                web = new BrowseFragment();//프래그먼트2 선언
                web.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
                transaction.replace(R.id.main_frame, web);
                transaction.commit();

                //mainActivity.fragmentChange(1);

            }
        });





        return v;
    }

    @Override
    public CharSequence format(int progress, int max) {
        return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
    }



}