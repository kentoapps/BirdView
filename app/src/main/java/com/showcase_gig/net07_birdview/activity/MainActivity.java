package com.showcase_gig.net07_birdview.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.showcase_gig.net07_birdview.R;
import com.showcase_gig.net07_birdview.fragment.StartFragment;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, StartFragment.newInstance());
        fragmentTransaction.commit();
    }
}
