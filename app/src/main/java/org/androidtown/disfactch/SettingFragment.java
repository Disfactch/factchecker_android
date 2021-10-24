/**
 * SPDX-FileCopyrightText: © 2021 Subin Kim <subinga18@naver.com>
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.androidtown.disfactch;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Switch;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;



public class SettingFragment extends Fragment {

    Button btnGithub;
    Button btnOpenSource;
    BrowseFragment web;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_setting, container, false);

        btnGithub = (Button) layout.findViewById(R.id.btn_github);
        btnOpenSource = (Button) layout.findViewById(R.id.btn_opensource);

        //btn_github: disfactch github를 webview 방식으로 앱 내에서 보여줌
        btnGithub.setOnClickListener(v-> {
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
        });

        btnOpenSource.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), OssLicensesMenuActivity.class));

        });

        Switch swDark = (Switch) layout.findViewById(R.id.sw);
        swDark.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);

        swDark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
        return layout;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fragment_setting, null);
    }
}


