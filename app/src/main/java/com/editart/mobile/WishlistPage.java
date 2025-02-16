package com.editart.mobile;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.editart.mobile.models.Book;
import com.editart.mobile.models.UserResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class WishlistPage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_page);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_wishlist);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_products) {
                startActivity(new Intent(WishlistPage.this, MainPage.class));
            } else if (itemId == R.id.nav_cart) {
                startActivity(new Intent(WishlistPage.this, CartPage.class));
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(WishlistPage.this, ProfilePage.class));
            }
            return false;
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        bookAdapter = new BookAdapter(BookAdapter.BookDisplayType.Wishlist);
        recyclerView.setAdapter(bookAdapter);

        // Observe the LiveData for preloaded books
        WishlistRepository.getInstance().getWishlist(UserResponse.getLastLogin().getId(), new WishlistRepository.WishlistCallback() {
            @Override
            public void onSuccess(List<Book> wishlist) {

                displayWishlist(wishlist);
                if(wishlist.isEmpty())
                {
                    TextView empty = findViewById(R.id.empty_text);
                    empty.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onError(String error) {
                // Erro - Exibe uma mensagem de erro
                Toast.makeText(WishlistPage.this, error, Toast.LENGTH_SHORT).show();
                Log.e("API", error);
            }
        });

        ImageButton logOutButton = findViewById(R.id.log_out_button);

        // Set an OnClickListener to handle the click event
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the LoginPage
                Intent intent = new Intent(WishlistPage.this, Login.class);

                // Optional: You can clear the activity stack to prevent returning to the previous page
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Start the LoginPage activity
                startActivity(intent);

                // Optional: Finish the current activity to remove it from the stack
                finish();
            }
        });
    }

    private void displayWishlist(List<Book> wishlist) {
        bookAdapter.setBooks(wishlist);
    }
}
