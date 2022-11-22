package com.zlatamigas.readme.ui.searchresult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.R;
import com.zlatamigas.readme.controller.APIController;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.customview.recyclerview.search.BookSearchRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.search.BookSearchRVOptionsListener;
import com.zlatamigas.readme.databinding.FragmentSearchresultBinding;
import com.zlatamigas.readme.ui.search.SearchFragment;

import java.util.ArrayList;

public class SearchResultFragment extends Fragment implements BookSearchRVOptionsListener {

    private APIController apiController;

    private RecyclerView rvSearchResultBooks;
    private ArrayList<BookCommonInfoRVModel> rvModelBookCommonInfoRVModelList;
    private BookSearchRVAdapter rvAdapter;

    private SearchResultViewModel searchResultViewModel;
    private FragmentSearchresultBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchResultViewModel = new ViewModelProvider(this).get(SearchResultViewModel.class);

        binding = FragmentSearchresultBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        apiController = new APIController();

        rvSearchResultBooks = binding.idFrSearchResultRVBooks;

        rvModelBookCommonInfoRVModelList = new ArrayList<>();
        rvAdapter = new BookSearchRVAdapter(requireActivity(), rvModelBookCommonInfoRVModelList, this);
        rvSearchResultBooks.setAdapter(rvAdapter);

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        rvSearchResultBooks.setLayoutManager(layoutManager);
        rvSearchResultBooks.setNestedScrollingEnabled(false);

        DividerItemDecoration itemDecVert = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        itemDecVert.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_blue_100_2h));
        rvSearchResultBooks.addItemDecoration(itemDecVert);
        DividerItemDecoration itemDecHor = new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL);
        itemDecHor.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_blue_100_2w));
        rvSearchResultBooks.addItemDecoration(itemDecHor);

        fillRandomBookCommonInfo();

        SearchResultFragment thisFragment = this;

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

    public void fillRandomBookCommonInfo() {

        rvModelBookCommonInfoRVModelList.clear();
        rvModelBookCommonInfoRVModelList.addAll(apiController.getSearchBooksCommonInfo());
        rvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // TODO
    @Override
    public void onBookClicked(BookCommonInfoRVModel book, View v) {

    }

    

}