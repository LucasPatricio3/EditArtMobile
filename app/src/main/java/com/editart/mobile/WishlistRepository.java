package com.editart.mobile;

import com.editart.mobile.api.APIInterface;
import com.editart.mobile.models.Book;
import com.editart.mobile.models.BookResponse;
import com.editart.mobile.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class WishlistRepository {

    // A instância singleton do repositório
    private static WishlistRepository instance;
    private APIInterface apiInterface;

    // Construtor privado para evitar múltiplas instâncias
    private WishlistRepository() {
        apiInterface = RetrofitClient.getClient().create(APIInterface.class);
    }

    // Método para obter a instância singleton do repositório
    public static WishlistRepository getInstance() {
        if (instance == null) {
            instance = new WishlistRepository();
        }
        return instance;
    }

    // Método para obter a wishlist de um usuário
    public void getWishlist(int userId, final WishlistCallback callback) {
        Call<BookResponse> call = apiInterface.getWishlist(userId); // Call<BookResponse>, not Call<List<Book>>
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Extract the list of books from the BookResponse and pass it to the callback
                    List<Book> books = response.body().getData(); // Get the list from BookResponse
                    callback.onSuccess(books);
                } else {
                    callback.onError("Failed to load wishlist.");
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    // Interface para callbacks
    public interface WishlistCallback {
        void onSuccess(List<Book> wishlist);
        void onError(String error);
    }
}
