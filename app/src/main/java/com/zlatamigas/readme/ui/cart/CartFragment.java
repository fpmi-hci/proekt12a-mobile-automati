package com.zlatamigas.readme.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.R;
import com.zlatamigas.readme.controller.APIController;
import com.zlatamigas.readme.customview.recyclerview.cart.BookCartRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.cart.BookCartRVOptionsListener;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.databinding.FragmentCartBinding;

import java.util.ArrayList;

public class CartFragment extends Fragment implements BookCartRVOptionsListener {

    private APIController apiController;

    private RecyclerView rvCartBooks;
    private ArrayList<BookCommonInfoRVModel> rvModelBookCommonInfoRVModelList;
    private BookCartRVAdapter rvAdapter;

    private CartViewModel cartViewModel;
    private FragmentCartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        apiController = new APIController();

        rvCartBooks = binding.idFrCartRVBooks;

        rvModelBookCommonInfoRVModelList = new ArrayList<>();
        rvAdapter = new BookCartRVAdapter(requireActivity(), rvModelBookCommonInfoRVModelList, this);
        rvCartBooks.setAdapter(rvAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        rvCartBooks.setLayoutManager(layoutManager);
        rvCartBooks.setNestedScrollingEnabled(false);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_blue_100_2h));
        rvCartBooks.addItemDecoration(itemDecorator);

        fillCart();

        return root;
    }

    public void fillCart(){

        rvModelBookCommonInfoRVModelList.clear();
        rvModelBookCommonInfoRVModelList.addAll(apiController.getUserCartBooks(1));
        rvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onBookClicked(BookCommonInfoRVModel book, View v) {

    }

    @Override
    public void onSelectCheckBoxClicked(BookCommonInfoRVModel book, View v) {

    }

    @Override
    public void onDeleteBookClicked(BookCommonInfoRVModel book, View v) {

    }
}