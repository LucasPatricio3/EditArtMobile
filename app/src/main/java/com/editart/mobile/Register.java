package com.editart.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView doLoginText = findViewById(R.id.do_login_text);
        SpannableString span = new SpannableString("Já tem conta? Faça Login!");
        span.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Log.i("Click", "Clicked On Login Redirect");
                Intent intent = new Intent(widget.getContext(), Login.class);
                widget.getContext().startActivity(intent);
            }
        }, 0, span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        doLoginText.setText(span);
        doLoginText.setMovementMethod(LinkMovementMethod.getInstance());
    }
}