package com.zlatamigas.readme.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;
import com.zlatamigas.readme.MainActivity;
import com.zlatamigas.readme.R;
import com.zlatamigas.readme.controller.APIProvider;
import com.zlatamigas.readme.controller.UserController;
import com.zlatamigas.readme.controller.apimodel.response.CartResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.UserResponseAPIModel;
import com.zlatamigas.readme.databinding.FragmentProfileBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Call<UserResponseAPIModel> callAdd = APIProvider.getInstance().getService().getUserInfo(UserController.getInstance().getToken());
        callAdd.enqueue(new Callback<UserResponseAPIModel>() {
            @Override
            public void onResponse(Call<UserResponseAPIModel> call, Response<UserResponseAPIModel> response) {

                UserResponseAPIModel body = response.body();
                if(body != null) {

                    binding.idFrProfileEmail.setText(body.getEmail());
                    binding.idFrProfileName.setText(body.getName());

                    if (body.getIconPath() != null && !body.getIconPath().isEmpty()) {
                        Picasso.get()
                                .load(body.getIconPath())
                                .error(R.mipmap.ic_launcher)
                                .into(binding.idFrProfileIcon);
                    } else {
                        binding.idFrProfileIcon.setImageResource(R.mipmap.ic_launcher);
                    }



                    System.out.println("success");
                } else {
                    System.out.println("empty? error?");
                }
            }

            @Override
            public void onFailure(Call<UserResponseAPIModel> call, Throwable t) {
                System.out.println("failure");
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}