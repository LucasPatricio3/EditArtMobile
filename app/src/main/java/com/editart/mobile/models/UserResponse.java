package com.editart.mobile.models;

public class UserResponse {
    private boolean success;
    private Data data; // A nested class for the "data" object
    private String message;

    private static Data lastLogin;

    public static void setLastLogin(Data loginInfo)
    {
        lastLogin = loginInfo;
    }

    public static Data getLastLogin()
    {
        return lastLogin;
    }

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
        private int id;
        private String token;
        private String name;
        private String email;
        private String nif;
        private String phone_number;
        private String address;
        private String postal_code;
        private String locality;
        private String created_at;
        private String role;

        public int getId() {
            return id;
        }
        public String getToken() {
            return token;
        }

        public String getName() {
            return name;
        }
        public String getEmail() {
            return email;
        }
        public String getNif() {
            return (nif != null && !nif.isEmpty()) ? nif : "Não definido.";
        }

        public String getPhoneNumber() {
            return (phone_number != null && !phone_number.isEmpty()) ? phone_number : "Não definido.";
        }

        public String getAddress() {
            return (address != null && !address.isEmpty()) ? address : "Não definido.";
        }
        public String getPostalCode() {
            return (postal_code != null && !postal_code.isEmpty()) ? postal_code : "Não definido.";
        }
        public String getLocality() {
            return (locality != null && !locality.isEmpty()) ? locality : "Não definido.";
        }

        public String getCreatedAt() {
            return created_at;
        }

        public String getRole() {
            return role;
        }
    }
}
