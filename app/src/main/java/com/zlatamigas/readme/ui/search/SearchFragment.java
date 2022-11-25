package com.zlatamigas.readme.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.R;
import com.zlatamigas.readme.controller.APIProvider;
import com.zlatamigas.readme.controller.apimodel.request.SearchParamsRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.GenreResponseAPIModel;
import com.zlatamigas.readme.customview.recyclerview.entity.SearchCheckboxRVModel;
import com.zlatamigas.readme.customview.recyclerview.search.SearchCheckboxRVAdapter;
import com.zlatamigas.readme.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {



    private RecyclerView rvGenres;
    private SearchCheckboxRVAdapter rvGenresAdapter;

    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        rvGenres = binding.idFrSearchRVGenres;

        GridLayoutManager glmGenres = new GridLayoutManager(requireContext(), 2);
        rvGenres.setLayoutManager(glmGenres);
        rvGenres.setNestedScrollingEnabled(false);

        Call<List<GenreResponseAPIModel>> call = APIProvider.getInstance().getService().getGenres();
        call.enqueue(new Callback<List<GenreResponseAPIModel>>() {
            @Override
            public void onResponse(Call<List<GenreResponseAPIModel>> call, Response<List<GenreResponseAPIModel>> response) {

                List<GenreResponseAPIModel> body = response.body();
                if(body != null) {

                    ArrayList<SearchCheckboxRVModel> genres = new ArrayList<>(body.size());
                    for(GenreResponseAPIModel genre : body){
                        genres.add(new SearchCheckboxRVModel(genre.getId(), genre.getName()));
                    }

                    rvGenresAdapter = new SearchCheckboxRVAdapter(requireActivity(), genres);
                    rvGenres.setAdapter(rvGenresAdapter);

                    System.out.println("success");
                } else {
                    System.out.println("empty? error?");
                }
            }

            @Override
            public void onFailure(Call<List<GenreResponseAPIModel>> call, Throwable t) {
                System.out.println("failure");
            }
        });


        binding.idFrSearchSPSortTypes.setSelection(0);

        Button btnSearch = binding.idFrSearchBtnSearch;
        btnSearch.setOnClickListener(v -> {

            NavController navController = NavHostFragment.findNavController(this);

            Bundle args = new Bundle();
            SearchParamsRequestAPIModel searchParams = new SearchParamsRequestAPIModel();

            searchParams.setSearchString(binding.idFrSearchTVSearchStr.getText().toString().trim());

            boolean titleSearch = binding.idFrSearchCBTitle.isChecked();
            boolean authorSearch = binding.idFrSearchCBAuthor.isChecked();
            SearchParamsRequestAPIModel.SearchType searchType = SearchParamsRequestAPIModel.SearchType.TITLE_OR_AUTHOR;
            if(titleSearch && !authorSearch){
                searchType =  SearchParamsRequestAPIModel.SearchType.TITLE;
            }else if(!titleSearch && authorSearch){
                searchType =  SearchParamsRequestAPIModel.SearchType.AUTHOR;
            }
            searchParams.setSearchType(searchType);

            searchParams.setSortDirection(SearchParamsRequestAPIModel.SortDirection.getById(
                    binding.idFrSearchSPSortTypes.getSelectedItemPosition())
            );

            args.putSerializable("search_params", searchParams);
            args.putBoolean("search_all", false);
            navController.navigate(R.id.navigation_searchresult, args);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}