package com.editart.mobile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.editart.mobile.models.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.BookViewHolder> {
    private List<CartItem> cartItems = new ArrayList<>();

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        // Logging the quantity to ensure it's correct
        Log.d("CartAdapter", "Quantity: " + cartItem.getQuantity());

        holder.bookTitle.setText(cartItem.getBook().getTitle());
        holder.bookAuthor.setText(cartItem.getBook().getAuthors());
        holder.bookPrice.setText(String.format("€%.2f", cartItem.getBook().getPrice()));
        holder.bookRating.setRating(cartItem.getBook().getRating());

        // Set visibility based on quantity
        if (cartItem.getQuantity() >= 1) {
            holder.quantity.setVisibility(View.VISIBLE);
            holder.quantity.setText(String.format("%d", cartItem.getQuantity()));
        } else {
            holder.quantity.setVisibility(View.GONE); // Hide if quantity is 1 or less
        }

        // Load image using Glide or set a placeholder
        if (cartItem.getBook().getCoverPicture() == null) {
            holder.bookCover.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.img_nao_disponivel));
        } else {
            // Glide.with(holder.itemView.getContext()).load(cartItem.getBook().getCoverPicture()).into(holder.bookCover);
        }

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ProductPage.class);
            intent.putExtra("BOOK_ID", cartItem.getBook().getId());
            context.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, bookAuthor, bookPrice, quantity;
        ImageView bookCover;
        RatingBar bookRating;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookPrice = itemView.findViewById(R.id.book_price);
            bookCover = itemView.findViewById(R.id.book_cover);
            bookRating = itemView.findViewById(R.id.book_rating);
            quantity = itemView.findViewById(R.id.cart_quantity);
            quantity.setVisibility(View.GONE); // Default to invisible
        }
    }
}
