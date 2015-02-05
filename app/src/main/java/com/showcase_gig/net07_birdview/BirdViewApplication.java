package com.showcase_gig.net07_birdview;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by kento on 15/02/05.
 */
public class BirdViewApplication extends Application{
    private static final String APPLICATION_ID = "X7RMpWjzY565t2MID7D9MmAs70fAcGgcW7ItbCWJ";
    private static final String CLIENT_KEY = "M529F0JTKa2imMD3ZqNK3vUzk0IMfXJXwMSqhIIU";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
    }
}
