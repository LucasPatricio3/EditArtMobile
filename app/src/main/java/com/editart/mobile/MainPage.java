package com.editart.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.editart.mobile.models.Book;
import com.editart.mobile.models.BookResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;

public class MainPage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_products);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_wishlist) {
                startActivity(new Intent(MainPage.this, MainPage.class));
            } else if (itemId == R.id.nav_cart) {
                startActivity(new Intent(MainPage.this, MainPage.class));
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(MainPage.this, ProfilePage.class));
            }
            return false;
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter = new BookAdapter();
        recyclerView.setAdapter(bookAdapter);

        // Observe the LiveData for preloaded books
        BookRepository bookRepository = BookRepository.GetInstance();
        if (bookRepository.getBooks().isEmpty()) {
            fetchBooks();
        } else {
            bookAdapter.setBooks(bookRepository.getBooks()); // Set existing books if available
        }
    }

    private void fetchBooks() {
        BookRepository bookRepository = BookRepository.GetInstance();
        bookRepository.fetchBooks(new BookRepository.BookFetchCallback() {
            @Override
            public void onBooksFetched(List<Book> books) {
                Log.i("API", "Books fetched: " + books.size() + " books to display");
                bookAdapter.setBooks(books);
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Log.e("API", "Error fetching books: " + t.getMessage());
            }

            @Override
            public void onFailure(String message) {
                Log.e("API", "API Failure: " + message);
            }
        });
    }
}
