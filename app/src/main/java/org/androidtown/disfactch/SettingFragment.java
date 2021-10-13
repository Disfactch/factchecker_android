/*
package org.androidtown.disfactch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import org.androidtown.disfactch.R;

import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingFragment extends Fragment {

    MainActivity mainActivity;

    // 메인 액티비티에서 내려온다.
    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }



/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(this.getContext(), "This is SettingFragment", Toast.LENGTH_SHORT).show();
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        //findViewById 오류 생길때마다 여기에 담기
        TextView button_1 = (TextView) v.findViewById(R.id.button_1);
        TextView button_2 = (TextView) v.findViewById(R.id.button_2);
        TextView button_3 = (TextView) v.findViewById(R.id.button3);
        TextView button_darkmode = (TextView) v.findViewById(R.id.button_darkmode);

        return v;

    }

 */

/*
    Button button_1;
    Button button_2;
    Button button_3;
    Button button_darkmode;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_setting);
        // TODO: setContentView 오류 고치기



        button_1 = (Button) getView().findViewById(R.id.button_1);
        button_2 = (Button) getView().findViewById(R.id.button_2);
        button_3 = (Button) getView().findViewById(R.id.button3);
        button_darkmode = (Button) getView().findViewById(R.id.button_darkmode);


        //button_1 기능 : disfactch Organization Github 연결
        button_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/Disfactch"));
                startActivity(intent);
            }
        });


        //button_2 기능: 임시로 전화기능 설정
        button_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:010"));
                startActivity(intent);
            }
        });


        //button_3 기능: 임시로 구글 검색기능 설정
        button_3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY,"searchString");
                startActivity(intent);
            }
        });


        //button_darkmode 기능: 다크모드 설정
        button_darkmode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.naver.com"));
                startActivity(intent);
            }
        });

        //https://comxp.tistory.com/243 여기 참고해서 Intent.ACTION 기능 살펴보고 추가

/*
        이거는 누르면 어플 종료
        나중에 보고 추가할지 말지 결정하기
        button_f.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        );

 */

//    }

//}


package org.androidtown.disfactch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import org.androidtown.disfactch.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceFragmentCompat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingFragment extends Fragment {

    MainActivity mainActivity;

    // 메인 액티비티에서 내려온다.
    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    Button btn_github;
    Button button_2;
    Button button_3;
    Button button_darkmode;

    BrowseFragment web;

    String themeColor = ThemeUtil.LIGHT_MODE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        //TODO: 버튼 이름 기능에 따라 변경 필요 _ button1 이런건 나중에 뭔지 헷갈릴 것 같아요
        btn_github = (Button) v.findViewById(R.id.btn_github);
        button_2 = (Button) v.findViewById(R.id.button_2);
        button_3 = (Button) v.findViewById(R.id.button3);
        button_darkmode = (Button) v.findViewById(R.id.button_darkmode);


        //btn_github: disfactch github를 webview 방식으로 앱 내에서 보여줌
        btn_github.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putString("link","https://github.com/Disfactch");//번들에 넘길 값 저장
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                web = new BrowseFragment();//프래그먼트2 선언
                web.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
                transaction.replace(R.id.main_frame, web);
                transaction.commit();
            }
        });


        //button_2 기능: 임시로 전화기능 설정
        button_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:010"));
                startActivity(intent);
            }
        });


        //button_3 기능: 임시로 구글 검색기능 설정
        button_3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY,"searchString");
                startActivity(intent);
            }
        });




        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction mt = fm.beginTransaction();

        mt.replace(R.id.main_frame,new dark_mode_Fragment());
        mt.commit();


        return v;



    }

    private void DarkMode(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction mt = fm.beginTransaction();

        mt.replace(R.id.main_frame,new dark_mode_Fragment());
        mt.commit();

    }
}


