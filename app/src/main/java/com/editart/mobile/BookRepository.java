package com.editart.mobile;

import android.util.Log;
import com.editart.mobile.api.APIInterface;
import com.editart.mobile.models.Book;
import com.editart.mobile.models.BookResponse;
import com.editart.mobile.retrofit.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRepository {

    private APIInterface apiInterface;
    private static BookRepository Instance;
    private List<Book> bookList = new ArrayList<>(); // Local cache

    public BookRepository() {
        apiInterface = RetrofitClient.getClient().create(APIInterface.class);
    }

    public static BookRepository GetInstance() {
        if (Instance == null) {
            Instance = new BookRepository();
        }
        return Instance;
    }

    public interface BookFetchCallback {
        void onBooksFetched(List<Book> books);

        void onFailure(Call<BookResponse> call, Throwable t);

        void onFailure(String message);
    }

    public void fetchBooks(BookFetchCallback callback) {
        Call<BookResponse> call = apiInterface.getBooks();
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookList = response.body().getData(); // Save to local cache
                    callback.onBooksFetched(bookList);
                } else {
                    callback.onFailure("API returned failure");
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public Book getBookById(int id) {
        for (Book book : bookList) {
            if (book.getId() == id) {
                return book; // Return from cache
            }
        }
        return null; // Not found
    }

    public List<Book> getBooks(){
        return bookList;
    }
}
