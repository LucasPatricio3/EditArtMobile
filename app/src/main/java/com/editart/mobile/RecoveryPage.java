package com.editart.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.editart.mobile.api.APIInterface;
import com.editart.mobile.models.ApiResponse;
import com.editart.mobile.models.SecurityTokenRequest;
import com.editart.mobile.retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecoveryPage extends AppCompatActivity {

    private EditText tokenInput;
    private MaterialButton recoverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recover_pass_page);  // Your recovery layout file name

        tokenInput = findViewById(R.id.token_input);
        recoverButton = findViewById(R.id.recover_button);

        recoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = tokenInput.getText().toString().trim();

                if (token.isEmpty()) {
                    Toast.makeText(RecoveryPage.this, "Please enter the security token", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call the checkToken API method
                checkToken(token);
            }
        });
    }

    private void checkToken(String token) {
        // Set up Retrofit client and API interface
        Retrofit retrofit = RetrofitClient.getClient();
        APIInterface apiInterface = retrofit.create(APIInterface.class);

        // Create the request body with the token
        SecurityTokenRequest request = new SecurityTokenRequest(token);

        // Make the API call
        apiInterface.checkToken(request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        // Token is valid, go back to login page
                        Toast.makeText(RecoveryPage.this, "Password reset link sent", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RecoveryPage.this, Login.class); // Redirect to login page
                        startActivity(intent);
                        finish(); // Finish the current activity
                    } else {
                        // Error occurred, show the error message
                        Toast.makeText(RecoveryPage.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Log error response for better understanding
                    Log.e("API Error", "Error: " + response.code() + " " + response.message());
                    if (response.errorBody() != null) {
                        Log.e("API Error", response.errorBody().toString());
                    }
                    Toast.makeText(RecoveryPage.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Handle the failure of the API request
                Toast.makeText(RecoveryPage.this, "Failed to contact the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
