package com.santiago.smartalert.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Santiago on 3/6/2018.
 */

public class AuthService {
    private static final String PREFS_TOKEN = "@SmartAlert::";
    private static final String TAG_TOKEN = PREFS_TOKEN + "token";

    public static void setToken(Context context, String sToken){
        SharedPreferences settings = context.getSharedPreferences(PREFS_TOKEN, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TAG_TOKEN, sToken);
        editor.commit();
    }

    public static String getToken(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_TOKEN, context.MODE_PRIVATE);
        return settings.getString(TAG_TOKEN, "");
    }
}
