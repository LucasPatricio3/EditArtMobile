package com.editart.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.editart.mobile.models.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProductPage extends AppCompatActivity {
    private TextView bookTitle, bookAuthor, bookGenre, bookPrice, bookDescription, bookRatingValue;
    private ImageView bookCover;
    private RatingBar bookRating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page);

        // Initialize UI components
        bookTitle = findViewById(R.id.book_title);
        bookAuthor = findViewById(R.id.book_author);
        bookGenre = findViewById(R.id.book_genre);
        bookPrice = findViewById(R.id.book_price);
        bookDescription = findViewById(R.id.book_description);
        bookCover = findViewById(R.id.book_cover);
        bookRating = findViewById(R.id.book_rating);
        bookRatingValue = findViewById(R.id.book_rating_value);

        // Get book ID from intent
        int bookId = getIntent().getIntExtra("BOOK_ID", -1);

        if (bookId != -1) {
            loadBookDetails(bookId);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_products);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_products) {
                startActivity(new Intent(ProductPage.this, MainPage.class));
            } else if (itemId == R.id.nav_wishlist) {
                startActivity(new Intent(ProductPage.this, MainPage.class));
            } else if (itemId == R.id.nav_cart) {
                startActivity(new Intent(ProductPage.this, MainPage.class));
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(ProductPage.this, ProfilePage.class));
            }
            return false;
        });
    }

    private void loadBookDetails(int bookId) {
        // Example: Get book from repository (this could be an API or database)
        Book book = BookRepository.GetInstance().getBookById(bookId);
        if (book != null) {
            bookTitle.setText(book.getTitle());
            bookAuthor.setText(book.getAuthors());
            bookGenre.setText(book.getGenres());
            bookPrice.setText(String.format("€%.2f", book.getPrice()));
            bookDescription.setText(book.getDescription());
            bookRating.setRating(book.getRating());
            bookRatingValue.setText(String.valueOf(book.getRating()));

            // Load book cover image (use Glide or Picasso)
            //Glide.with(this).load(book.getCoverPicture()).into(bookCover);
        }
    }
}
