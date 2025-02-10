package com.editart.mobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.editart.mobile.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    public enum BookDisplayType{
        Normal,
        Wishlist,
        Cart
    }

    private BookDisplayType displayType;

    public BookAdapter(BookDisplayType displayType){
        this.displayType = displayType;
    }

    private List<Book> books = new ArrayList<>();

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var itemToInflate = displayType == BookDisplayType.Wishlist ? R.layout.wishlist_item : R.layout.book_item;
        View view = LayoutInflater.from(parent.getContext()).inflate(itemToInflate, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthors());
        holder.bookPrice.setText(String.format("€%.2f", book.getPrice()));
        holder.bookRating.setRating(book.getRating());

        // Load image (if using Glide or Picasso)
        if(book.getCoverPicture() == null)
            holder.bookCover.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.img_nao_disponivel));
        else {}// Glide.with(holder.itemView.getContext()).load(book.getCoverPicture()).into(holder.bookCover);

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ProductPage.class);
            intent.putExtra("BOOK_ID", book.getId()); // Pass book ID
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, bookAuthor, bookPrice;
        ImageView bookCover;
        RatingBar bookRating;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookPrice = itemView.findViewById(R.id.book_price);
            bookCover = itemView.findViewById(R.id.book_cover);
            bookRating = itemView.findViewById(R.id.book_rating);
        }
    }
}
