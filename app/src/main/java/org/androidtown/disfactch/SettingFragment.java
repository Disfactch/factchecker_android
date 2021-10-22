package org.androidtown.disfactch;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SettingFragment extends Fragment {

    Button btnGithub;
    Button btnSecond;
    BrowseFragment web;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_setting, container, false);

        //TODO: 버튼 이름 기능에 따라 변경 필요
        btnGithub = (Button) layout.findViewById(R.id.btn_github);
        btnSecond = (Button) layout.findViewById(R.id.button_2);

        //btn_github: disfactch github를 webview 방식으로 앱 내에서 보여줌
        btnGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putString("link", "https://github.com/Disfactch");//번들에 넘길 값 저장
                if (getActivity() != null) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    web = new BrowseFragment();//프래그먼트2 선언
                    web.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
                    transaction.replace(R.id.main_frame, web);
                    // 뒤로 가기 누르면 전 화면으로 돌려줌.
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });


        //button_2 기능: 임시로 전화기능 설정
        btnSecond.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("tel:010"));
            startActivity(intent);
        });

        Switch swDark = (Switch) layout.findViewById(R.id.sw);
        swDark.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);

        //TODO: 여기서 onConfigurationChanged 메서드 사용해야함
        swDark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        return layout;
    }

    //TODO: onConfiguratioonChanged 방법 찾기.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fragment_setting, null);
    }

}


