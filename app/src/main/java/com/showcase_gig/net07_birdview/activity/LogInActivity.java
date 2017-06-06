package com.showcase_gig.net07_birdview.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.showcase_gig.net07_birdview.R;
import com.showcase_gig.net07_birdview.fragment.LogInFragment;

public class LogInActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.login_container, LogInFragment.newInstance());
        fragmentTransaction.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
