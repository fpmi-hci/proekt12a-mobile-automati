package com.zlatamigas.readme.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

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
import com.zlatamigas.readme.customview.ItemBookCartView;
import com.zlatamigas.readme.customview.recyclerview.cart.BookCartRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.cart.BookCartRVOptionsListener;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.databinding.FragmentCartBinding;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CartFragment extends Fragment implements BookCartRVOptionsListener {

    private APIController apiController;

    private RecyclerView rvCartBooks;
    private ArrayList<BookCommonInfoRVModel> rvModelBookCommonInfoRVModelList;
    private BookCartRVAdapter rvAdapter;

    private CartViewModel cartViewModel;
    private FragmentCartBinding binding;

    private CheckBox cbSelectAll;
    private ArrayList<BookCommonInfoRVModel> selectedBooks;

    private TextView tvSelectedCount;
    private TextView tvSelectedCost;

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

        Button btnSearch = binding.idFrCartBtnToOrder;
        btnSearch.setOnClickListener(v -> {

            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.navigation_order);
        });

        selectedBooks = new ArrayList<>();

        cbSelectAll = binding.idFrCartCBSelectAll;
        cbSelectAll.setOnClickListener(v -> {
            if(cbSelectAll.isChecked()) {
                rvAdapter.selectAll();
                selectedBooks.clear();
                selectedBooks.addAll(rvModelBookCommonInfoRVModelList);
            } else {
                rvAdapter.unselectAll();
                selectedBooks.clear();
            }
            setSelectedBooksSumInfo();
        });

        tvSelectedCount = binding.idFrCartTVSelectedCount;
        tvSelectedCost = binding.idFrCartTVSelectedCost;

        setSelectedBooksSumInfo();

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
    public void onBookClicked(BookCommonInfoRVModel book, ItemBookCartView v) {
//        NavController navController = NavHostFragment.findNavController(this);
//        navController.navigate(R.id.navigation_order);
    }

    @Override
    public void onSelectCheckBoxClicked(BookCommonInfoRVModel book, ItemBookCartView v) {
        if(v.getCheckBox().isChecked()){
            selectedBooks.add(book);
        } else {
            selectedBooks.remove(book);
            cbSelectAll.setChecked(false);
        }
        setSelectedBooksSumInfo();
    }

    @Override
    public void onDeleteBookClicked(BookCommonInfoRVModel book, int position) {
        rvAdapter.deleteViewWithModelAt(position);
        selectedBooks.remove(book);
        apiController.removeBookFromCart(book.getId(), 1);
        setSelectedBooksSumInfo();
    }

    private void setSelectedBooksSumInfo(){
        tvSelectedCount.setText(Integer.toString(selectedBooks.size()));
        BigDecimal cost = new BigDecimal(0);
        for(BookCommonInfoRVModel book : selectedBooks){
            cost = cost.add(book.getCost());
        }
        tvSelectedCost.setText(cost.toString() + " rub.");
    }
}