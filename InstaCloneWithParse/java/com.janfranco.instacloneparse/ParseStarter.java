package com.janfranco.instacloneparse;

import com.parse.Parse;
import android.app.Application;

public class ParseStarter extends Application {

    public void onCreate(){
        super.onCreate();
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Fb36WlXXMGMadsa1tFpI4KSuJ0bxSvJWZRGbgaPH")
                .clientKey("ITuX5Dvx6zBKRE3dLyvvXNntw5pHHEWiIb9i3wgJ")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }

}
