package com.zlatamigas.readme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.zlatamigas.readme.controller.APIProvider;
import com.zlatamigas.readme.controller.UserController;
import com.zlatamigas.readme.controller.apimodel.request.LoginRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.LoginResponseAPIModel;
import com.zlatamigas.readme.databinding.ActivityLogInBinding;
import com.zlatamigas.readme.util.validator.Validator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    private ActivityLogInBinding binding;

    private EditText etEmail;
    private EditText etPassword;

    private Button btnLogIn;

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

        btnLogIn = binding.idActLogInBtnLogin;
        btnLogIn.setEnabled(false);
        btnLogIn.setOnClickListener(v -> {

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            LoginRequestAPIModel loginRequestAPIModel = new LoginRequestAPIModel();
            loginRequestAPIModel.setEmail(email);
            loginRequestAPIModel.setPassword(password);

            LogInActivity activity = this;

            AlertDialog.Builder invalidCredentialsDialogBuilder = new AlertDialog.Builder(activity);
            invalidCredentialsDialogBuilder
                    .setTitle("Неправильный адрес эл.почты и/или пароль!")
                    .setPositiveButton("ОК", (dialog, id) -> {
                        dialog.cancel();
                    });
            AlertDialog invalidCredentialsDialog = invalidCredentialsDialogBuilder.create();

            AlertDialog.Builder logInFailureDialogBuilder = new AlertDialog.Builder(activity);
            logInFailureDialogBuilder
                    .setTitle("Ошибка связи с сервером!")
                    .setMessage("Проверьте соединение с интернетом!")
                    .setPositiveButton("ОК", (dialog, id) -> {
                        dialog.cancel();
                    });
            AlertDialog logInFailureDialog = logInFailureDialogBuilder.create();

            Call<LoginResponseAPIModel> call = APIProvider.getInstance().getService().login(loginRequestAPIModel);
            call.enqueue(new Callback<LoginResponseAPIModel>() {
                @Override
                public void onResponse(Call<LoginResponseAPIModel> call, Response<LoginResponseAPIModel> response) {

                    if (response.body() != null) {

                        UserController userController = UserController.getInstance();
                        userController.setToken(response.body().getToken());

                        Intent toMainIntent = new Intent(context, MainActivity.class);
                        startActivity(toMainIntent);
                    } else {

                        invalidCredentialsDialog.show();

                        etEmail.setText("");
                        etPassword.setText("");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseAPIModel> call, Throwable t) {

                    logInFailureDialog.show();

                    etEmail.setText("");
                    etPassword.setText("");
                }
            });

        });

        TextWatcher loginInputChange = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Validator.validateEmail(etEmail.getText().toString())
                        && Validator.validatePassword(etPassword.getText().toString())) {
                    btnLogIn.setEnabled(true);
                } else {
                    btnLogIn.setEnabled(false);
                }
            }
        };
        etEmail.addTextChangedListener(loginInputChange);
        etPassword.addTextChangedListener(loginInputChange);

        binding.idActLogInBtnToRegister.setOnClickListener(v -> {
            Intent toRegisterIntent = new Intent(this, RegisterActivity.class);
            startActivity(toRegisterIntent);
        });
    }

}