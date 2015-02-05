package com.showcase_gig.net07_birdview.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.showcase_gig.net07_birdview.R;

public class GameFragment extends Fragment {
    private static final String TAG = GameFragment.class.getSimpleName();

    private View mView;

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        return fragment;
    }

    public GameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_game, container, false);
        return mView;
    }


}
