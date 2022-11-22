package com.zlatamigas.readme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.zlatamigas.readme.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {

    private ActivityLogInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.idActLogInBtnLogin.setOnClickListener(v -> {
            if(login()) {
                Intent toMainIntent = new Intent(this, MainActivity.class);
                startActivity(toMainIntent);
            } else {
                ///
            }
        });
        binding.idActLogInBtnToRegister.setOnClickListener(v -> {
            Intent toRegisterIntent = new Intent(this, RegisterActivity.class);
            startActivity(toRegisterIntent);
        });
    }

    private boolean login(){


        return true;
    }
}