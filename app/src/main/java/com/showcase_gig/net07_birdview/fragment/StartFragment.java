package com.showcase_gig.net07_birdview.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.showcase_gig.net07_birdview.R;

public class StartFragment extends Fragment {
    private static final String TAG = StartFragment.class.getSimpleName();

    private View mView;

    public static StartFragment newInstance() {
        StartFragment fragment = new StartFragment();
        return fragment;
    }

    public StartFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_start, container, false);
        return mView;
    }
}
