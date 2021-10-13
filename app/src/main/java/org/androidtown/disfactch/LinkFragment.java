package org.androidtown.disfactch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kakao.sdk.template.model.FeedTemplate;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

import static android.content.ContentValues.TAG;

public class LinkFragment extends Fragment {

    MainActivity mainActivity;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragments
        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.fragment_link, container, false);

        return v;
    }
}