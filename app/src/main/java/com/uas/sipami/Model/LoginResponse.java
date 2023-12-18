package com.uas.sipami.Model;

import java.io.Serializable;
import java.util.List;

public class LoginResponse implements Serializable {
    private String nikNip;
    private String accessToken;
    private String name;
    private List<String> roles;
    public LoginResponse(String nikNip, String accessToken,List<String> roles, String name) {
        this.nikNip = nikNip;
        this.accessToken = accessToken;
        this.roles = roles;
        this.name=name;
    }

    public String getNikNip() {
        return nikNip;
    }

    public void setNikNip(String nikNip) {
        this.nikNip = nikNip;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name =  name;
    }

}
