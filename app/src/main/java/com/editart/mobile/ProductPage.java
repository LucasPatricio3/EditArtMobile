package com.editart.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.editart.mobile.api.APIInterface;
import com.editart.mobile.models.Book;
import com.editart.mobile.models.CartRequest;
import com.editart.mobile.models.UserResponse;
import com.editart.mobile.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPage extends AppCompatActivity {

    private APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
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

        // Set up bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_products);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_products) {
                startActivity(new Intent(ProductPage.this, MainPage.class));
            } else if (itemId == R.id.nav_wishlist) {
                startActivity(new Intent(ProductPage.this, WishlistPage.class));
            } else if (itemId == R.id.nav_cart) {
                startActivity(new Intent(ProductPage.this, CartPage.class));
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(ProductPage.this, ProfilePage.class));
            }
            return false;
        });

        // Find cart icon and set OnClickListener
        ImageView cartIcon = findViewById(R.id.cart_icon);
        cartIcon.setOnClickListener(v -> {
            int quantity = 1;  // Example quantity, you could get this dynamically if needed
            if (bookId != -1) {
                addToCart(UserResponse.getLastLogin().getId(), bookId, quantity);  // Call addToCart method
            }
        });

        ImageButton logOutButton = findViewById(R.id.log_out_button);

        // Set an OnClickListener to handle the click event
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the LoginPage
                Intent intent = new Intent(ProductPage.this, Login.class);

                // Optional: You can clear the activity stack to prevent returning to the previous page
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Start the LoginPage activity
                startActivity(intent);

                // Optional: Finish the current activity to remove it from the stack
                finish();
            }
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

    private void addToCart(int userId, int bookId, int quantity) {
        CartRequest cartRequest = new CartRequest(bookId, quantity);
        apiInterface.addToCart(userId, cartRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Check if the response is successful
                if (response.isSuccessful()) {
                    Log.d("API", "Book added to cart: " + response.code());
                    Toast.makeText(ProductPage.this, "Book added to cart", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("API", "Failed to add book to cart, response code: " + response.code());
                    Toast.makeText(ProductPage.this, "Failed to add book", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Log the error
                Log.e("API", "Error adding book to cart: " + t.getMessage());
                Toast.makeText(ProductPage.this, "Error adding book", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
