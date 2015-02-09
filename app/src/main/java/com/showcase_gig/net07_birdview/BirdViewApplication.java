package com.showcase_gig.net07_birdview;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by kento on 15/02/05.
 */
public class BirdViewApplication extends Application{
    private static final String APPLICATION_ID = "AWnR6mo52pqwVBlGkkbMCfatva6rWKNzbyEtzhOU";
    private static final String CLIENT_KEY = "wHCIlG55AfyMNOSSYNvDrMeyN3X6QYvCi9c9psVQ";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }
}
