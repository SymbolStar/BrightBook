package com.scottfu.brightbook;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by fujindong on 2017/3/7.
 */

public class MainFragment extends Fragment {

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

//        TODO check saveInstanceState


    }
}
