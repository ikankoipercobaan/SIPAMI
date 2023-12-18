package com.uas.sipami.Model;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("nikNip")
    private String nikNip;

    @SerializedName("password")
    private String password;

    public LoginRequest(String nikNip, String password) {
        this.nikNip = nikNip;
        this.password = password;
    }

    public String getNikNip() {
        return nikNip;
    }

    public void setNikNip(String nikNip) {
        this.nikNip = nikNip ;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


