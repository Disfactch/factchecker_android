/**
 * SPDX-FileCopyrightText: © 2021 Subin Kim <subinga18@naver.com>
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.androidtown.disfactch;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Context context = this;

    BrowseFragment web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기

        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);
        web = new BrowseFragment();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new FactCheckingFragment()).commitAllowingStateLoss();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.fact_checking:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new FactCheckingFragment()).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new SettingFragment()).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}

