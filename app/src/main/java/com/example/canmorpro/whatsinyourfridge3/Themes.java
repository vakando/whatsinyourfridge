package com.example.canmorpro.whatsinyourfridge3;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Empress on 2016-04-14.
 */
public class Themes {
    public static int cTheme = 0;
    public final static int BLACK = 1;
    public final static int RED = 2;
    public final static int DEFAULT = 3;

    public static void changeToTheme(Activity activity, int theme){
        cTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity){
        switch (cTheme){
            case BLACK:
                activity.setTheme(R.style.Black);
                break;
            case RED:
                activity.setTheme(R.style.Red);
                break;
            case DEFAULT:
                activity.setTheme(R.style.Default);
                break;
        }
    }
}
