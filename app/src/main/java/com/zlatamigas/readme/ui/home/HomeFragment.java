package com.zlatamigas.readme.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.R;
import com.zlatamigas.readme.controller.APIController;
import com.zlatamigas.readme.databinding.FragmentHomeBinding;
import com.zlatamigas.readme.customview.recyclerview.search.BookSearchRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.customview.recyclerview.search.BookSearchRVOptionsListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements BookSearchRVOptionsListener {

    private APIController apiController;

    private RecyclerView rvRandomBooks;
    private ArrayList<BookCommonInfoRVModel> rvModelBookCommonInfoRVModelList;
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

        rvModelBookCommonInfoRVModelList = new ArrayList<>();
        rvAdapter = new BookSearchRVAdapter(requireActivity(), rvModelBookCommonInfoRVModelList, this);
        rvRandomBooks.setAdapter(rvAdapter);

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        rvRandomBooks.setLayoutManager(layoutManager);
        rvRandomBooks.setNestedScrollingEnabled(false);

        DividerItemDecoration itemDecVert = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        itemDecVert.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_blue_100_2h));
        rvRandomBooks.addItemDecoration(itemDecVert);
        DividerItemDecoration itemDecHor = new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL);
        itemDecHor.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_blue_100_2w));
        rvRandomBooks.addItemDecoration(itemDecHor);

        fillRandomBookCommonInfo();

        return root;
    }

    public void fillRandomBookCommonInfo(){

        rvModelBookCommonInfoRVModelList.clear();
        rvModelBookCommonInfoRVModelList.addAll(apiController.getRandomBooksCommonInfo());
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