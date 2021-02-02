package com.ujjaval.jwtapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.auth0.android.jwt.JWT;

public class Auth {

    private static final String JWT_KEY_USERNAME = "username";

    private static final String PREFS = "prefs";
    private static final String PREF_TOKEN = "pref_token";
    private SharedPreferences mPrefs;

    private static Auth sInstance;

    private Auth(@NonNull Context context) {
        mPrefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        sInstance = this;
    }

    public static Auth getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new Auth(context);
        }
        return sInstance;
    }

    public void setIdToken(@NonNull GetToken token) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_TOKEN, token.getIdToken());
        editor.apply();
    }

    @Nullable
    public String getIdToken() {
        return mPrefs.getString(PREF_TOKEN, null);
    }

    public boolean isLoggedIn() {
        String token = getIdToken();
        return token != null;
    }

    /**
     * Gets the username of the signed in user
     * @return - username of the signed in user
     */
    public String getUsername() {
        if (isLoggedIn()) {
            return decodeUsername(getIdToken());
        }
        return null;
    }

    @Nullable
    private String decodeUsername(String token) {
        JWT jwt = new JWT(token);
        try {
            if (jwt.getClaim(JWT_KEY_USERNAME) != null) {
                return jwt.getClaim(JWT_KEY_USERNAME).asString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clear() {
        mPrefs.edit().clear().commit();
    }

}
