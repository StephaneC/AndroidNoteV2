package com.castrec.stephane.androidnotev2;

/**
 * Created by sca on 25/09/17.
 */

import android.app.Application;

import com.castrec.stephane.androidnotev2.di.AppComponent;
import com.castrec.stephane.androidnotev2.di.DaggerAppComponent;

public class TchatApp extends Application {

    AppComponent mAppComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().application(this).build();

    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }


}