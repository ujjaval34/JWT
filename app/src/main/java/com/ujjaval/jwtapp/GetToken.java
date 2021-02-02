package com.ujjaval.jwtapp;


import com.google.gson.annotations.SerializedName;

public class GetToken implements NetworkRequest.ApiResponse {
    @SerializedName("id_token")
    private String idToken;

    public String getIdToken() {
        return idToken;
    }

    @Override
    public String string() {
        return idToken;
    }
}
