package com.zlatamigas.readme.ui.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zlatamigas.readme.MainActivity;
import com.zlatamigas.readme.R;
import com.zlatamigas.readme.controller.APIController;
import com.zlatamigas.readme.controller.APIProvider;
import com.zlatamigas.readme.controller.OrderController;
import com.zlatamigas.readme.controller.UserController;
import com.zlatamigas.readme.controller.apimodel.response.AuthorResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.BookFullInfoResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.LoginResponseAPIModel;
import com.zlatamigas.readme.customview.ItemBookCartView;
import com.zlatamigas.readme.customview.recyclerview.cart.BookCartRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.cart.BookCartRVOptionsListener;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.databinding.FragmentBookBinding;
import com.zlatamigas.readme.databinding.FragmentCartBinding;
import com.zlatamigas.readme.util.StringMerger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookFragment extends Fragment {

    private BookViewModel bookViewModel;
    private FragmentBookBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        binding = FragmentBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Call<BookFullInfoResponseAPIModel> call = APIProvider.getInstance().getService().getBookFullInfo(
                getArguments().getLong("book_id"),
                UserController.getInstance().getToken());
        call.enqueue(new Callback<BookFullInfoResponseAPIModel>() {
            @Override
            public void onResponse(Call<BookFullInfoResponseAPIModel> call, Response<BookFullInfoResponseAPIModel> response) {

                BookFullInfoResponseAPIModel body = response.body();
                if(body != null) {


                    ArrayList<String> authorStrs = new ArrayList<>(body.getAuthors().size());
                    for (AuthorResponseAPIModel author: body.getAuthors()) {
                        authorStrs.add(author.getFullName());
                    };
                    binding.idFrBookTVAuthor.setText(StringMerger.mergeStrings(authorStrs));
                    if(body.isPurchasedByUser()){
                        binding.idFrBookTVBought.setVisibility(View.VISIBLE);
                        binding.idFrBookTVPrice.setVisibility(View.GONE);
                    } else {
                        binding.idFrBookTVBought.setVisibility(View.GONE);
                        binding.idFrBookTVPrice.setText(body.getCost().toString());
                        binding.idFrBookTVPrice.setVisibility(View.VISIBLE);
                    }
                    binding.idFrBookTVTitle.setText(body.getTitle());
                    binding.idFrBookTVDescription.setText(body.getDescription());

                    Picasso.get()
                            .load(body.getImageUrl())
                            .error(R.drawable.ic_bnm_mybooks)
                            .into(binding.idFrBookIVCover);

                    binding.idFrBookCLMain.setVisibility(View.VISIBLE);

                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(body.getTitle());

                    System.out.println("success");
                } else {
                    System.out.println("empty? error?");
                }
            }

            @Override
            public void onFailure(Call<BookFullInfoResponseAPIModel> call, Throwable t) {
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