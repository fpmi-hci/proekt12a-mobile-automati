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
import com.zlatamigas.readme.controller.APIProvider;
import com.zlatamigas.readme.controller.apimodel.request.SearchParamsRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.AuthorResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.BookResponseAPIModel;
import com.zlatamigas.readme.databinding.FragmentHomeBinding;
import com.zlatamigas.readme.customview.recyclerview.search.BookSearchRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.customview.recyclerview.search.BookSearchRVOptionsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements BookSearchRVOptionsListener {



    private RecyclerView rvRandomBooks;
    private BookSearchRVAdapter rvAdapter;

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



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

        Call<List<BookResponseAPIModel>> call = APIProvider.getInstance().getService().getAllBooks();
        call.enqueue(new Callback<List<BookResponseAPIModel>>() {
            @Override
            public void onResponse(Call<List<BookResponseAPIModel>> call, Response<List<BookResponseAPIModel>> response) {

                List<BookResponseAPIModel> body = response.body();
                if(body != null) {

                    rvModelBookCommonInfoRVModelList.clear();

                    ListIterator<BookResponseAPIModel> iterator
                            = body.listIterator(body.size());
                    BookResponseAPIModel book;
                    int i = 10;
                    while (iterator.hasPrevious() && i > 0){
                        book = iterator.previous();
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
            public void onFailure(Call<List<BookResponseAPIModel>> call, Throwable t) {
                System.out.println("failure");
            }
        });

        binding.idFrHomeBtnNewBooks.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);

            Bundle args = new Bundle();
            SearchParamsRequestAPIModel searchParams = new SearchParamsRequestAPIModel();
            searchParams.setSearchString("Главные новинки");
            searchParams.setSortDirection(SearchParamsRequestAPIModel.SortDirection.DESC);
            args.putSerializable("search_params", searchParams);
            args.putBoolean("search_all", true);
            navController.navigate(R.id.navigation_searchresult, args);
        });

        binding.idFrHomeBtnAllBooks.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);

            Bundle args = new Bundle();
            SearchParamsRequestAPIModel searchParams = new SearchParamsRequestAPIModel();
            searchParams.setSearchString("Все товары");
            searchParams.setSortDirection(SearchParamsRequestAPIModel.SortDirection.ASC);
            args.putSerializable("search_params", searchParams);
            args.putBoolean("search_all", true);
            navController.navigate(R.id.navigation_searchresult, args);
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