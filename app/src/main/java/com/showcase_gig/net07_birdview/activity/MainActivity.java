package com.showcase_gig.net07_birdview.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.showcase_gig.net07_birdview.R;
import com.showcase_gig.net07_birdview.constant.Const;
import com.showcase_gig.net07_birdview.fragment.StartFragment;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ログインチェック
        if(ParseUser.getCurrentUser() == null) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            return;
        }

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(Const.INSTALLATION_USER, ParseUser.getCurrentUser());
        installation.saveInBackground();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, StartFragment.newInstance());
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_log_out) {
            // ログアウト処理
            ParseUser.logOut();
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.remove(Const.INSTALLATION_USER);
            installation.saveInBackground();
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
