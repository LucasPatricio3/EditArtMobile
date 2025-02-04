package com.editart.mobile;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.editart.mobile.models.Book;
import com.editart.mobile.models.BookRepository;
import com.editart.mobile.models.BookResponse;
import java.util.List;
import retrofit2.Call;

public class EditArt extends Application {
    private static EditArt instance;
    private final MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        preloadBooks();
    }

    public static EditArt getInstance() {
        return instance;
    }

    private void preloadBooks() {
        BookRepository bookRepository = BookRepository.GetInstance();
        bookRepository.fetchBooks(new BookRepository.BookFetchCallback() {
            @Override
            public void onBooksFetched(List<Book> books) {
                booksLiveData.postValue(books);
                Log.i("API", "Preloaded " + books.size() + " books");
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Log.e("API", "Preload error: " + t.getMessage());
            }

            @Override
            public void onFailure(String message) {
                Log.e("API", "Preload API Failure: " + message);
            }
        });
    }

    public LiveData<List<Book>> getBooksLiveData() {
        return booksLiveData;
    }
}
