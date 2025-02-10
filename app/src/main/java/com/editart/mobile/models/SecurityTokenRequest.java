package com.editart.mobile.models;

public class SecurityTokenRequest {
    private String security_token;

    public SecurityTokenRequest(String security_token) {
        this.security_token = security_token;
    }

    public String getSecurity_token() {
        return security_token;
    }

    public void setSecurity_token(String security_token) {
        this.security_token = security_token;
    }
}

