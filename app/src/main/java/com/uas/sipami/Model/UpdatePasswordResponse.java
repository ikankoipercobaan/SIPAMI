package com.uas.sipami.Model;

public class UpdatePasswordResponse {

    private String message;

    public UpdatePasswordResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

