package com.showcase_gig.net07_birdview;

import android.app.Application;

import com.parse.Parse;

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
    }
}
