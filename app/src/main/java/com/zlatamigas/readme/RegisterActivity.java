package com.zlatamigas.readme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zlatamigas.readme.databinding.ActivityMainBinding;
import com.zlatamigas.readme.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

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
        binding.idActRegisterBtnRegister.setOnClickListener(v -> {
            if(register()) {
                Intent toLogInIntent = new Intent(this, LogInActivity.class);
                startActivity(toLogInIntent);
            } else{
                ///
            }
        });
    }

    private boolean register(){

        ////////
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://automati-develop.herokuapp.com/")
//                .build();
//
//        final HerokuService service = retrofit.create(HerokuService.class);
//
//        Call<ResponseBody> call = service.getAllBooks();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> _,
//                                   Response<ResponseBody> response) {
//                try {
//                    System.out.println(response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> _, Throwable t) {
//                t.printStackTrace();
//                System.out.println(t.getMessage());
//            }
//        });

        return false;
    }
}