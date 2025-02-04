package com.editart.mobile.models;

import android.util.Log;

import com.editart.mobile.api.APIInterface;
import com.editart.mobile.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRepository {

    private APIInterface apiInterface;

    private static BookRepository Instance;

    public BookRepository() {
        apiInterface = RetrofitClient.getClient(APIInterface.API_URL).create(APIInterface.class);
    }

    public static BookRepository GetInstance()
    {
        if (Instance == null)
        {
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
                    BookResponse bookResponse = response.body();
                    if (bookResponse.isSuccess()) {
                        Log.i("API", "Books fetched successfully: " + bookResponse.getData().size() + " books found.");
                        callback.onBooksFetched(bookResponse.getData());
                    } else {
                        Log.e("API", "API returned failure: " + response.message());
                        callback.onFailure("API returned failure");
                    }
                } else {
                    Log.e("API", "Error: " + response.message());
                    callback.onFailure("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Log.e("API", "Error fetching books: " + t.getMessage());
                callback.onFailure(t.getMessage());
            }
        });
    }
}
