package com.zlatamigas.readme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zlatamigas.readme.controller.APIProvider;
import com.zlatamigas.readme.controller.apimodel.request.LoginRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.request.RegisterRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.LoginResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.RegisterResponseAPIModel;
import com.zlatamigas.readme.databinding.ActivityMainBinding;
import com.zlatamigas.readme.databinding.ActivityRegisterBinding;
import com.zlatamigas.readme.util.validator.Validator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordRepeat;

    private Button btnRegister;

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
        etPasswordRepeat = binding.idActRegisterETPasswordRepeat;

        RegisterActivity activity = this;

        AlertDialog.Builder invalidCredentialsDialogBuilder = new AlertDialog.Builder(activity);
        invalidCredentialsDialogBuilder.setTitle("Некорректные данные для регистрации!")
                .setPositiveButton("ОК", (dialog, id) -> {
                    dialog.cancel();
                });
        AlertDialog invalidCredentialsDialog = invalidCredentialsDialogBuilder.create();

        AlertDialog.Builder registerFailureDialogBuilder = new AlertDialog.Builder(activity);
        registerFailureDialogBuilder
                .setTitle("Ошибка связи с сервером!")
                .setMessage("Проверьте интернет соединение!")
                .setPositiveButton("ОК", (dialog, id) -> {
                    dialog.cancel();
                });
        AlertDialog registerFailureDialog = registerFailureDialogBuilder.create();

        btnRegister = binding.idActRegisterBtnRegister;
        btnRegister.setEnabled(false);
        btnRegister.setOnClickListener(v -> {

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
                        Toast.makeText(context, "Регистрация успешно завершена!", Toast.LENGTH_SHORT).show();
                    } else {

                        invalidCredentialsDialog.show();

                        etEmail.setText("");
                        etPassword.setText("");
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponseAPIModel> call, Throwable t) {

                    registerFailureDialog.show();

                    etEmail.setText("");
                    etPassword.setText("");
                }
            });

        });

        TextWatcher registerInputChange = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String passwordRepeat = etPasswordRepeat.getText().toString();

                if (Validator.validateEmail(email)
                        && password.equals(passwordRepeat)
                        && Validator.validatePassword(password)) {
                    btnRegister.setEnabled(true);
                } else {
                    btnRegister.setEnabled(false);
                }
            }
        };
        etEmail.addTextChangedListener(registerInputChange);
        etPassword.addTextChangedListener(registerInputChange);
        etPasswordRepeat.addTextChangedListener(registerInputChange);
    }

    private boolean register(){


        return false;
    }
}