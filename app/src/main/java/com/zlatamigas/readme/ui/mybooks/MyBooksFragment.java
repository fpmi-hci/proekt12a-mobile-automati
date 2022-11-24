package com.zlatamigas.readme.ui.mybooks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.R;
import com.zlatamigas.readme.controller.APIController;
import com.zlatamigas.readme.controller.APIProvider;
import com.zlatamigas.readme.controller.UserController;
import com.zlatamigas.readme.controller.apimodel.response.AuthorResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.BookFullInfoResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.CartResponseAPIModel;
import com.zlatamigas.readme.customview.recyclerview.cart.BookCartRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.customview.recyclerview.mybooks.BookMyBooksRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.mybooks.BookMyBooksRVOptionsListener;
import com.zlatamigas.readme.databinding.FragmentMybooksBinding;
import com.zlatamigas.readme.ui.cart.CartFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBooksFragment extends Fragment implements BookMyBooksRVOptionsListener {

    private APIController apiController;

    private RecyclerView rvBooks;
    private BookMyBooksRVAdapter rvAdapter;

    private MyBooksViewModel myBooksViewModel;
    private FragmentMybooksBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myBooksViewModel = new ViewModelProvider(this).get(MyBooksViewModel.class);

        binding = FragmentMybooksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        apiController = new APIController();

        rvBooks = binding.idFrMyBooksRVBooks;


        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvBooks.setLayoutManager(layoutManager);
        rvBooks.setNestedScrollingEnabled(false);

        DividerItemDecoration itemDecVert = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        itemDecVert.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_blue_100_2h));
        rvBooks.addItemDecoration(itemDecVert);

        MyBooksFragment currFragment = this;
        ArrayList<BookCommonInfoRVModel> rvModelBookCommonInfoRVModelList = new ArrayList<>();


        Call<List<BookFullInfoResponseAPIModel>> call = APIProvider.getInstance().getService().getUserPurchased(UserController.getInstance().getToken());
        call.enqueue(new Callback<List<BookFullInfoResponseAPIModel>>() {
            @Override
            public void onResponse(Call<List<BookFullInfoResponseAPIModel>> call, Response<List<BookFullInfoResponseAPIModel>> response) {

                List<BookFullInfoResponseAPIModel> body = response.body();
                if(body != null) {

                    rvModelBookCommonInfoRVModelList.clear();

                    for(BookFullInfoResponseAPIModel book: body){
                        ArrayList<String> authors = new ArrayList<>(book.getAuthors().size());
                        for(AuthorResponseAPIModel author: book.getAuthors()){
                            authors.add(author.getFullName());
                        }
                        rvModelBookCommonInfoRVModelList.add(new BookCommonInfoRVModel(
                                book.getId(),
                                book.getTitle(),
                                authors,
                                book.getCost(),
                                book.getImageUrl()
                        ));
                    }
                    rvAdapter = new BookMyBooksRVAdapter(requireActivity(), rvModelBookCommonInfoRVModelList, currFragment);
                    rvBooks.setAdapter(rvAdapter);

                    System.out.println("success");
                } else {
                    System.out.println("empty? error?");
                }
            }

            @Override
            public void onFailure(Call<List<BookFullInfoResponseAPIModel>> call, Throwable t) {
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

    @Override
    public void onBookClicked(BookCommonInfoRVModel book, View v) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle args = new Bundle();
        args.putLong("book_id", book.getId());
        navController.navigate(R.id.navigation_book, args);
    }

    @Override
    public void onDownloadClicked(BookCommonInfoRVModel book, View v) {

    }
}