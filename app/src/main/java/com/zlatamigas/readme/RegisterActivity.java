package com.zlatamigas.readme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zlatamigas.readme.controller.APIProvider;
import com.zlatamigas.readme.controller.apimodel.request.LoginRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.request.RegisterRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.LoginResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.RegisterResponseAPIModel;
import com.zlatamigas.readme.databinding.ActivityMainBinding;
import com.zlatamigas.readme.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.idActRegisterBtnToLogin.setOnClickListener(v -> {
            Intent toLogInIntent = new Intent(this, LogInActivity.class);
            startActivity(toLogInIntent);
        });

        etEmail = binding.idActRegisterETEmail;
        etPassword = binding.idActRegisterETPassword;

        binding.idActRegisterBtnRegister.setOnClickListener(v -> {

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            RegisterRequestAPIModel registerRequestAPIModel = new RegisterRequestAPIModel();
            registerRequestAPIModel.setEmail(email);
            registerRequestAPIModel.setPassword(password);

            Context context = this;

            Call<RegisterResponseAPIModel> call = APIProvider.getInstance().getService().register(registerRequestAPIModel);
            call.enqueue(new Callback<RegisterResponseAPIModel>() {
                @Override
                public void onResponse(Call<RegisterResponseAPIModel> call, Response<RegisterResponseAPIModel> response) {

                    if(response.body() != null) {
                        Intent toLogInIntent = new Intent(context, LogInActivity.class);
                        startActivity(toLogInIntent);
                    } else {
                        etEmail.setText("");
                        etPassword.setText("");
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponseAPIModel> call, Throwable t) {
                    etEmail.setText("");
                    etPassword.setText("");
                }
            });

        });
    }

    private boolean register(){


        return false;
    }
}