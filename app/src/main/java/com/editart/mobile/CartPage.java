package com.editart.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.editart.mobile.api.APIInterface;
import com.editart.mobile.models.Cart;
import com.editart.mobile.models.CartItem;
import com.editart.mobile.models.CartRequest;
import com.editart.mobile.models.UserResponse;
import com.editart.mobile.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);

        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_cart);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_products) {
                startActivity(new Intent(this, MainPage.class));
            } else if (itemId == R.id.nav_wishlist) {
                startActivity(new Intent(this, WishlistPage.class));
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(this, ProfilePage.class));
            }
            return false;
        });

        // RecyclerView setup
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartAdapter = new CartAdapter();
        recyclerView.setAdapter(cartAdapter);

        fetchCartData();

        ImageButton logOutButton = findViewById(R.id.log_out_button);

        // Set an OnClickListener to handle the click event
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the LoginPage
                Intent intent = new Intent(CartPage.this, Login.class);

                // Optional: You can clear the activity stack to prevent returning to the previous page
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Start the LoginPage activity
                startActivity(intent);

                // Optional: Finish the current activity to remove it from the stack
                finish();
            }
        });
    }

    private void fetchCartData() {
        Call<Cart> call = apiInterface.getCart(UserResponse.getLastLogin().getId());

        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CartItem> cartItems = response.body().getData();
                    if (cartItems != null && !cartItems.isEmpty()) {
                        Log.d("API", "Cart contains " + cartItems.size() + " items");
                        for (CartItem item : cartItems) {
                            Log.d("API", "Cart item: " + item.getBook().getTitle() + ", quantity: " + item.getQuantity());
                        }
                        cartAdapter.setCartItems(cartItems);
                    } else {
                        Log.d("API", "Cart is empty.");
                    }
                } else {
                    Log.e("API", "Failed to fetch cart data, response code: " + response.code());
                }
            }


            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Log.e("API", "Error fetching cart: " + t.getMessage());
            }
        });
    }
    private void removeFromCart(int userId, int bookId) {
        CartRequest cartRequest = new CartRequest(bookId, 0); // 0 quantity means remove the book
        apiInterface.removeFromCart(userId, cartRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CartPage.this, "Book removed from cart", Toast.LENGTH_SHORT).show();
                    fetchCartData();  // Optionally, refresh the cart data
                } else {
                    Toast.makeText(CartPage.this, "Failed to remove book", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API", "Error: " + t.getMessage());
                Toast.makeText(CartPage.this, "Error removing book", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
