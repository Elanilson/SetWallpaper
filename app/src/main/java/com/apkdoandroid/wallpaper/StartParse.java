package com.apkdoandroid.wallpaper;

import android.app.Application;

import com.parse.Parse;

public class StartParse  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("UsAQZ0QGKzlgjQrkA6CcYf1XNktavFVSCiLNRgsT")
                // if defined
                .clientKey("pUvOjtd05kzkF3ml73HP4UjuITWzcgEGfXlBWYIv")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
