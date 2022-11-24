package com.zlatamigas.readme.ui.home;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.R;
import com.zlatamigas.readme.controller.APIController;
import com.zlatamigas.readme.controller.APIProvider;
import com.zlatamigas.readme.controller.UserController;
import com.zlatamigas.readme.controller.apimodel.response.AuthorResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.BookFullInfoResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.CartResponseAPIModel;
import com.zlatamigas.readme.customview.recyclerview.cart.BookCartRVAdapter;
import com.zlatamigas.readme.databinding.FragmentHomeBinding;
import com.zlatamigas.readme.customview.recyclerview.search.BookSearchRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.customview.recyclerview.search.BookSearchRVOptionsListener;
import com.zlatamigas.readme.ui.cart.CartFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements BookSearchRVOptionsListener {

    private APIController apiController;

    private RecyclerView rvRandomBooks;
    private BookSearchRVAdapter rvAdapter;

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        apiController = new APIController();

        rvRandomBooks = binding.idFrHomeRVRandomBooks;

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        rvRandomBooks.setLayoutManager(layoutManager);
        rvRandomBooks.setNestedScrollingEnabled(false);

        DividerItemDecoration itemDecVert = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        itemDecVert.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_blue_100_2h));
        rvRandomBooks.addItemDecoration(itemDecVert);
        DividerItemDecoration itemDecHor = new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL);
        itemDecHor.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_blue_100_2w));
        rvRandomBooks.addItemDecoration(itemDecHor);


        HomeFragment currFragment = this;
        ArrayList<BookCommonInfoRVModel> rvModelBookCommonInfoRVModelList = new ArrayList<>();

        Call<List<BookFullInfoResponseAPIModel>> call = APIProvider.getInstance().getService().getAllBooks();
        call.enqueue(new Callback<List<BookFullInfoResponseAPIModel>>() {
            @Override
            public void onResponse(Call<List<BookFullInfoResponseAPIModel>> call, Response<List<BookFullInfoResponseAPIModel>> response) {

                List<BookFullInfoResponseAPIModel> body = response.body();
                if(body != null) {

                    rvModelBookCommonInfoRVModelList.clear();

                    Iterator<BookFullInfoResponseAPIModel> iterator = body.iterator();
                    BookFullInfoResponseAPIModel book;
                    int i = 10;
                    while (iterator.hasNext() && i > 0){
                        book = iterator.next();
                        i--;

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
                    rvAdapter = new BookSearchRVAdapter(requireActivity(), rvModelBookCommonInfoRVModelList, currFragment);
                    rvRandomBooks.setAdapter(rvAdapter);

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
}