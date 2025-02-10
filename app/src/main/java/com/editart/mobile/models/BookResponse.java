package com.editart.mobile.models;
import java.util.List;

public class BookResponse {
    private boolean success;
    private List<Book> data;
    private String message;

    public boolean isSuccess() { return success; }
    public List<Book> getData() { return data; }

    public String getMessage() {
        return message;
    }
}
