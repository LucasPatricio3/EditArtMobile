package com.editart.mobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.editart.mobile.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> books = new ArrayList<>();

    public void setBooks(List<Book> books) {
        this.books = books;
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
        Book book = books.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookPrice.setText(String.format("€%.2f", book.getPrice()));
        holder.bookRating.setRating(book.getRating());

        // Load image (if using Glide or Picasso)
        // Glide.with(holder.itemView.getContext()).load(book.getCoverPicture()).into(holder.bookCover);
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
