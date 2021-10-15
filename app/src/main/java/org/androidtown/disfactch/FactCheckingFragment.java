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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.kakao.sdk.link.LinkClient;
import com.kakao.sdk.link.model.LinkResult;
import com.kakao.sdk.template.model.Content;
import com.kakao.sdk.template.model.FeedTemplate;
import com.kakao.sdk.template.model.Link;
import com.kakao.sdk.template.model.TextTemplate;

import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

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


    Button kakao_msg;
    String url;
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

        link = v.findViewById(R.id.rl_search_edit);


        Button rl_btn = (Button)v.findViewById(R.id.rl_search_btn);
        Button rl_read_btn = (Button)v.findViewById(R.id.rl_search_read_btn);

        rl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = link.getText().toString();

                progress.setVisibility(View.VISIBLE);
            }
        });


        rl_read_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putString("link",link.getText().toString());//번들에 넘길 값 저장
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                web = new BrowseFragment();//프래그먼트2 선언
                web.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
                transaction.replace(R.id.main_frame, web);
                // 뒤로 가기 누르면 전 화면으로 돌려줌.
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //TODO: 위에서 작성한 link가 string 형식으로 넘어오질 않음

        kakao_msg = (Button)v.findViewById(R.id.btn_link);
        kakao_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * new Link() : 파라미터 순서대로 webLink, mobileLink, androidLink, iosLink
                 * https://www.beemo.co.kr/entry/카카오-sdk-v2-굳이-자바로-사용하기-2-카카오-링크-메시지?category=743783
                 */
               // FeedTemplate feedTemplate = new FeedTemplate(new Content("title","imageUrl",    //메시지 제목, 이미지 url
                        //new Link("https://www.naver.com"),"description",                    //메시지 링크, 메시지 설명
                       // 300,300));                                                     //이미지 사이즈
                //url = "\""+url+"\"";

                /**
                 * 연합 , 네이버, 다음, 중앙, 동아, 경향, 국민, 뉴스1, 뉴시스, 문화 이렇게 10개 회사만 가능.
                 */
                //url 넘겨 받는거 : 검색 버튼 눌러야지만 공유 가능.
                TextTemplate textTemplate = new TextTemplate("기사를 확인해보세요 !~" ,new Link(url,url));
                LinkClient.getInstance().defaultTemplate(view.getContext(), textTemplate,null,new Function2<LinkResult, Throwable, Unit>() {
                    @Override
                    public Unit invoke(LinkResult linkResult, Throwable throwable) {
                        if (throwable != null) {
                            Log.e("TAG", "카카오링크 보내기 실패", throwable);
                        }
                        else if (linkResult != null) {
                            view.getContext().startActivity(linkResult.getIntent());

                            // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                            Log.w("TAG", "Warning Msg: "+ linkResult.getWarningMsg());
                            Log.w("TAG", "Argument Msg: "+ linkResult.getArgumentMsg());
                        }
                        return null;
                    }
                });
            }
        });

        return v;
    }

    @Override
    public CharSequence format(int progress, int max) {
        return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
    }

}