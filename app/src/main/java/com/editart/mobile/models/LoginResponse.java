package com.editart.mobile.models;

public class LoginResponse {
    private boolean success;
    private Data data; // A nested class for the "data" object
    private String message;

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    // Nested class for "data"
    public static class Data {
        private String token;
        private String name;
        private String role;

        public String getToken() {
            return token;
        }

        public String getName() {
            return name;
        }

        public String getRole() {
            return role;
        }
    }
}
