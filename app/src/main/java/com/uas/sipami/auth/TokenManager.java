package com.uas.sipami.auth;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String TOKEN_PREFS = "TokenPrefs";
    private static final String ACCESS_TOKEN = "AccessToken";
    private SharedPreferences sharedPreferences;

    public TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(TOKEN_PREFS, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(ACCESS_TOKEN, null);
    }

    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ACCESS_TOKEN);
        editor.apply();
    }
}
