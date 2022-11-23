package com.zlatamigas.readme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.zlatamigas.readme.controller.APIProvider;
import com.zlatamigas.readme.controller.apimodel.request.LoginRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.LoginResponseAPIModel;
import com.zlatamigas.readme.databinding.ActivityLogInBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    private ActivityLogInBinding binding;

    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Context context = this;

        etEmail = binding.idActLogInETEmail;
        etPassword = binding.idActLogInETPassword;

        binding.idActLogInBtnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            LoginRequestAPIModel loginRequestAPIModel = new LoginRequestAPIModel();
            loginRequestAPIModel.setEmail(email);
            loginRequestAPIModel.setPassword(password);

            Call<LoginResponseAPIModel> call = APIProvider.getInstance().getService().login(loginRequestAPIModel);
            call.enqueue(new Callback<LoginResponseAPIModel>() {
                @Override
                public void onResponse(Call<LoginResponseAPIModel> call, Response<LoginResponseAPIModel> response) {

                    if(response.body() != null) {
                        Intent toMainIntent = new Intent(context, MainActivity.class);
                        startActivity(toMainIntent);
                    } else {
                        etEmail.setText("");
                        etPassword.setText("");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseAPIModel> call, Throwable t) {
                    etEmail.setText("");
                    etPassword.setText("");
                }
            });

        });
        binding.idActLogInBtnToRegister.setOnClickListener(v -> {
            Intent toRegisterIntent = new Intent(this, RegisterActivity.class);
            startActivity(toRegisterIntent);
        });
    }

}