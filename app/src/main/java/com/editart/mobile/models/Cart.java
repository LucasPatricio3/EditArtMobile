package com.editart.mobile.models;

import com.editart.mobile.models.CartItem;

import java.util.List;

public class Cart {
    private boolean success;
    private List<CartItem> data;  // Ensure this is the correct type
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public List<CartItem> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
