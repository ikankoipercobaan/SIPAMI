package com.uas.sipami.Model;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("nikNip")
    private String nikNip;

    @SerializedName("accessToken")
    private String accessToken;

    public RegisterResponse(String nikNip, String accessToken) {
        this.nikNip = nikNip;
        this.accessToken = accessToken;
    }
    public String getNikNip() {
        return nikNip;
    }
    public String getAccessToken() {
        return accessToken;
    }
}

