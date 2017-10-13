package com.kentoapps.birdview.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kentoapps.birdview.R;
import com.kentoapps.birdview.fragment.LogInFragment;

public class LogInActivity extends AppCompatActivity {

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
