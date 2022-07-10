package br.com.fenixdev.documentProviderFileExpose;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {

    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        MainApplication.appContext = appContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        setAppContext(base);
        super.attachBaseContext(base);
    }
}
