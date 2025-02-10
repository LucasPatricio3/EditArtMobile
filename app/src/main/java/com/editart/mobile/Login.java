package com.editart.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.editart.mobile.api.APIInterface;
import com.editart.mobile.models.LoginRequest;
import com.editart.mobile.models.UserResponse;
import com.editart.mobile.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    EditText userEmail;
    EditText userPassword;
    Button loginBtn;
    TextView forgotPassword;
    SpannableString span = new SpannableString("Não tem conta? Registe-se!");
    SpannableString spanRecover = new SpannableString("Esqueceu-se da password?");

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
        TextView doRegisterText = findViewById(R.id.do_register_text);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var email = userEmail.getText().toString();
                var password = userPassword.getText().toString();
                Log.i("Credentials", String.format("Email: %s and Password : %s", email, password));
                performLogin(email, password);
            }
        });

        span.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Log.i("Click", "Clicked On Register Redirect");
                Intent intent = new Intent(widget.getContext(), Register.class);
                widget.getContext().startActivity(intent);
            }
        }, 0, span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        doRegisterText.setText(span);
        doRegisterText.setMovementMethod(LinkMovementMethod.getInstance());

        spanRecover.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Log.i("Click", "Clicked On Recover Redirect");
                Intent intent = new Intent(widget.getContext(), Register.class);
                widget.getContext().startActivity(intent);
            }
        }, 0, spanRecover.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        doRegisterText.setText(spanRecover);
        doRegisterText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void performLogin(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Log.e("Error", "Email or password cannot be empty");
            return;
        }

        Retrofit retrofit = RetrofitClient.getClient();
        APIInterface apiInterface = retrofit.create(APIInterface.class);

        LoginRequest loginRequest = new LoginRequest(email, password);
        apiInterface.login(loginRequest).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse loginResponse = response.body();

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
                        UserResponse.setLastLogin(loginResponse.getData());
                        Intent intent = new Intent(Login.this, MainPage.class);
                        startActivity(intent);
                    } else {
                        Log.e("Login Error", "Data is null");
                    }
                } else {
                    Log.e("Login Error", "Invalid response. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("Login Error", t.getMessage());
            }
        });
    }
}