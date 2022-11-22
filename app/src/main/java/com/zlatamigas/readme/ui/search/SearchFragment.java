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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.R;
import com.zlatamigas.readme.controller.APIController;
import com.zlatamigas.readme.customview.recyclerview.entity.SearchCheckboxRVModel;
import com.zlatamigas.readme.customview.recyclerview.search.SearchCheckboxRVAdapter;
import com.zlatamigas.readme.databinding.FragmentSearchBinding;
import com.zlatamigas.readme.ui.searchresult.SearchResultFragment;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private APIController apiController;

    private RecyclerView rvGenres;
    private SearchCheckboxRVAdapter rvGenresAdapter;
    private RecyclerView rvSearchTypes;
    private SearchCheckboxRVAdapter rvSearchTypesAdapter;

    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        apiController = new APIController();

        rvGenres = binding.idFrSearchRVGenres;
        rvGenresAdapter = new SearchCheckboxRVAdapter(requireActivity(), apiController.getGenres());
        rvGenres.setAdapter(rvGenresAdapter);
        GridLayoutManager glmGenres = new GridLayoutManager(requireContext(), 2);
        rvGenres.setLayoutManager(glmGenres);
        rvGenres.setNestedScrollingEnabled(false);

        rvSearchTypes = binding.idFrSearchRVSearchTypes;
        rvSearchTypesAdapter = new SearchCheckboxRVAdapter(requireActivity(), apiController.getSearchTypes());
        rvSearchTypes.setAdapter(rvSearchTypesAdapter);
        GridLayoutManager glmSearchTypes = new GridLayoutManager(requireContext(), 2);
        rvSearchTypes.setLayoutManager(glmSearchTypes);


        Button btnSearch = binding.idFrSearchBtnSearch;
        btnSearch.setOnClickListener(v -> {
//            SearchResultFragment nextFrag= new SearchResultFragment();
//            requireActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.nav_host_fragment_activity_main, nextFrag, "Search result")
//                    .addToBackStack(null)
//                    .commit();

            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.navigation_searchresult);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}