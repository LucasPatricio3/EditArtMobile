package com.editart.mobile.models;

public class Book {
    private int id;
    private String title;
    private String type;
    private String publicationDate;
    private int editionNumber;
    private String isbn;
    private int numberOfPages;
    private int stock;
    private String language;
    private String coverPicture; // This might be null
    private double price;
    private String description;
    private String author; // New field for author
    private float rating; // New field for rating

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public int getStock() {
        return stock;
    }

    public String getLanguage() {
        return language;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author; // Getter for author
    }

    public float getRating() {
        return rating; // Getter for rating
    }
}
