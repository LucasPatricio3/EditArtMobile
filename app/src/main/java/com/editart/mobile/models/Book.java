package com.editart.mobile.models;

import java.util.Objects;

public class  Book {
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
    private String authors;
    private String genres;
    private float rating; // New field for rating

    public int getId() {
        return id;
    }
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

    public String getAuthors() {
        return !Objects.equals(authors, "") ? authors : "Autor desconhecido";
    }

    public String getGenres() {
        return !Objects.equals(genres, "") ? genres : "Gênero desconhecido"; // Getter for author
    }

    public float getRating() {
        return rating; // Getter for rating
    }
}
