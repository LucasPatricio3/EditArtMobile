package com.editart.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.editart.mobile.models.LoginResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfilePage extends AppCompatActivity {
    private TextView bookTitle, bookAuthor, bookGenre, bookPrice, bookDescription, bookRatingValue;
    private ImageView bookCover;
    private RatingBar bookRating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        // Initialize UI components

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_products) {
                startActivity(new Intent(ProfilePage.this, MainPage.class));
            } else if (itemId == R.id.nav_wishlist) {
                startActivity(new Intent(ProfilePage.this, MainPage.class));
            } else if (itemId == R.id.nav_cart) {
                startActivity(new Intent(ProfilePage.this, MainPage.class));
            }
            return false;
        });

        TextView name_text = findViewById(R.id.client_name_text);
        TextView email_text = findViewById(R.id.email_text);
        TextView nif_text = findViewById(R.id.nif_text);
        TextView phone_text = findViewById(R.id.phone_text);
        TextView address_text = findViewById(R.id.address_text);
        TextView postal_text = findViewById(R.id.postal_text);
        TextView locality_text = findViewById(R.id.locality_text);
        TextView created_text = findViewById(R.id.created_text);

        name_text.setText(LoginResponse.getLastLogin().getName());
        email_text.setText(LoginResponse.getLastLogin().getEmail());
        nif_text.setText(LoginResponse.getLastLogin().getNif());
        phone_text.setText(LoginResponse.getLastLogin().getPhoneNumber());
        address_text.setText(LoginResponse.getLastLogin().getAddress());
        postal_text.setText(LoginResponse.getLastLogin().getPostalCode());
        locality_text.setText(LoginResponse.getLastLogin().getLocality());
        created_text.setText(LoginResponse.getLastLogin().getCreatedAt());
    }
}
