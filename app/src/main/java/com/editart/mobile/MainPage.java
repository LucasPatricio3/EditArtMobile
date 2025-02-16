package com.editart.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
                startActivity(new Intent(MainPage.this, WishlistPage.class));
            } else if (itemId == R.id.nav_cart) {
                startActivity(new Intent(MainPage.this, CartPage.class));
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(MainPage.this, ProfilePage.class));
            }
            return false;
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter = new BookAdapter(BookAdapter.BookDisplayType.Normal);
        recyclerView.setAdapter(bookAdapter);

        // Observe the LiveData for preloaded books
        BookRepository bookRepository = BookRepository.GetInstance();
        fetchBooks();

        ImageButton logOutButton = findViewById(R.id.log_out_button);

        // Set an OnClickListener to handle the click event
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the LoginPage
                Intent intent = new Intent(MainPage.this, Login.class);

                // Optional: You can clear the activity stack to prevent returning to the previous page
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Start the LoginPage activity
                startActivity(intent);

                // Optional: Finish the current activity to remove it from the stack
                finish();
            }
        });
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
