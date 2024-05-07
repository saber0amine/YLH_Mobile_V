package com.example.yallah_project.database;
import android.content.Context;
import android.content.SharedPreferences;


    public class SharedPrefer {
    private static final String SHARED_PREF_NAME = "user_data";

    public static void storeUserData(Context context, String[] paramNames, String[] paramValues) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (paramNames.length != paramValues.length) {
            throw new IllegalArgumentException("paramNames and paramValues arrays must have the same length");
        }
        for (int i = 0; i < paramNames.length; i++) {
            editor.putString(paramNames[i], paramValues[i]);
        }

        editor.apply();
    }

    public static String getUserData(Context context, String paramName) {
          SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(paramName, null);
    }
}
