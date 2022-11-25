package com.zlatamigas.readme.ui.searchresult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.customview.recyclerview.search.BookSearchRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.search.BookSearchRVOptionsListener;
import com.zlatamigas.readme.databinding.FragmentSearchresultBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultFragment extends Fragment implements BookSearchRVOptionsListener {



    private RecyclerView rvSearchResultBooks;
    private BookSearchRVAdapter rvAdapter;

    private SearchResultViewModel searchResultViewModel;
    private FragmentSearchresultBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchResultViewModel = new ViewModelProvider(this).get(SearchResultViewModel.class);

        binding = FragmentSearchresultBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        rvSearchResultBooks = binding.idFrSearchResultRVBooks;

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        rvSearchResultBooks.setLayoutManager(layoutManager);
        rvSearchResultBooks.setNestedScrollingEnabled(false);

        DividerItemDecoration itemDecVert = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        itemDecVert.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_blue_100_2h));
        rvSearchResultBooks.addItemDecoration(itemDecVert);
        DividerItemDecoration itemDecHor = new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL);
        itemDecHor.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_blue_100_2w));
        rvSearchResultBooks.addItemDecoration(itemDecHor);


        SearchResultFragment currFragment = this;
        ArrayList<BookCommonInfoRVModel> rvModelBookCommonInfoRVModelList = new ArrayList<>();

        boolean searchAll = getArguments().getBoolean("search_all");
        SearchParamsRequestAPIModel searchParams = (SearchParamsRequestAPIModel)getArguments().getSerializable("search_params");

        String title = searchParams.getSearchString();
        if(!searchAll && title.isEmpty()){
            title = getString(R.string.title_searchresult);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);

        if(!searchAll) {
            Call<List<BookResponseAPIModel>> call = APIProvider.getInstance().getService().getSearchBooks(searchParams);
            call.enqueue(new Callback<List<BookResponseAPIModel>>() {
                @Override
                public void onResponse(Call<List<BookResponseAPIModel>> call, Response<List<BookResponseAPIModel>> response) {

                    List<BookResponseAPIModel> body = response.body();
                    if (body != null) {

                        rvModelBookCommonInfoRVModelList.clear();

                        for (BookResponseAPIModel book : body) {


                            ArrayList<String> authors = new ArrayList<>(book.getAuthors().size());
                            for (AuthorResponseAPIModel author : book.getAuthors()) {
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
                        rvSearchResultBooks.setAdapter(rvAdapter);

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
        } else {
            Call<List<BookResponseAPIModel>> call = APIProvider.getInstance().getService().getAllBooks();
            call.enqueue(new Callback<List<BookResponseAPIModel>>() {
                @Override
                public void onResponse(Call<List<BookResponseAPIModel>> call, Response<List<BookResponseAPIModel>> response) {

                    List<BookResponseAPIModel> body = response.body();
                    if(body != null) {

                        rvModelBookCommonInfoRVModelList.clear();

                        if(searchParams.getSortDirection() == SearchParamsRequestAPIModel.SortDirection.DESC){
                            Collections.reverse(body);
                        }
                        Iterator<BookResponseAPIModel> iterator = body.iterator();
                        BookResponseAPIModel book;
                        while (iterator.hasNext()){
                            book = iterator.next();

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
                        rvSearchResultBooks.setAdapter(rvAdapter);

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
        }
//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//
//                NavController navController = NavHostFragment.findNavController(thisFragment);
//                navController.navigate(R.id.navigation_search);
//                // Handle the back button event
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // TODO
    @Override
    public void onBookClicked(BookCommonInfoRVModel book, View v) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle args = new Bundle();
        args.putLong("book_id", book.getId());
        navController.navigate(R.id.navigation_book, args);
    }

    

}