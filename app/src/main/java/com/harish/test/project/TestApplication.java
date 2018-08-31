package com.harish.test.project;

import android.app.Application;

import com.harish.test.project.utils.APIHelper;

public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        APIHelper.init();
    }

}
