package xyz.sainumtown.padc_week6;

import android.app.Application;
import android.content.Context;

/**
 * Created by User on 7/8/2016.
 */
public class PADC_WEEK6_APP extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
