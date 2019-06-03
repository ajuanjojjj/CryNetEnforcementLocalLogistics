package com.dam2.crynetenforcementlocallogistics;

import android.app.Application;

import com.onesignal.OneSignal;

public class App extends Application {
    public static String token = null;

    @Override
    public void onCreate() {
        super.onCreate();

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
