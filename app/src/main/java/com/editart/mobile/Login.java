package com.editart.mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.editart.mobile.api.APIInterface;
import com.editart.mobile.models.LoginRequest;
import com.editart.mobile.models.LoginResponse;
import com.editart.mobile.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    EditText userEmail;
    EditText userPassword;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userEmail = findViewById(R.id.user_email_input);
        userPassword = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var email = userEmail.getText().toString();
                var password = userPassword.getText().toString();
                Log.i("Credentials", String.format("Email: %s and Password : %s", email, password));
                performLogin(email, password);
            }
        });
    }

    private void performLogin(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Log.e("Error", "Email or password cannot be empty");
            return;
        }

        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8000/");
        APIInterface apiInterface = retrofit.create(APIInterface.class);

        LoginRequest loginRequest = new LoginRequest(email, password);
        apiInterface.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    // Access the main fields
                    boolean success = loginResponse.isSuccess();
                    String message = loginResponse.getMessage();

                    // Access nested data
                    if (loginResponse.getData() != null) {
                        String token = loginResponse.getData().getToken();
                        String name = loginResponse.getData().getName();
                        String role = loginResponse.getData().getRole();

                        Log.d("Login Success", "Token: " + token);
                        Log.d("User Info", "Name: " + name + ", Role: " + role);
                    } else {
                        Log.e("Login Error", "Data is null");
                    }
                } else {
                    Log.e("Login Error", "Invalid response. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Login Error", t.getMessage());
            }
        });
    }
}