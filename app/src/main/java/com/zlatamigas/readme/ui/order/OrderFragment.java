package com.zlatamigas.readme.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.R;
import com.zlatamigas.readme.controller.APIController;
import com.zlatamigas.readme.controller.OrderController;
import com.zlatamigas.readme.customview.recyclerview.order.BookOrderRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.databinding.FragmentOrderBinding;

import java.math.BigDecimal;
import java.util.ArrayList;

public class OrderFragment extends Fragment {

    private APIController apiController;

    private RecyclerView rvOrderBooks;
    private ArrayList<BookCommonInfoRVModel> rvModelBookCommonInfoRVModelList;
    private BookOrderRVAdapter rvAdapter;

    private OrderViewModel orderViewModel;
    private FragmentOrderBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        binding = FragmentOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        apiController = new APIController();

        rvOrderBooks = binding.idFrOrderRVBooks;


        rvModelBookCommonInfoRVModelList = OrderController.getInstance().getSelectedBooks();
        rvAdapter = new BookOrderRVAdapter(requireActivity(), rvModelBookCommonInfoRVModelList);
        rvOrderBooks.setAdapter(rvAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        rvOrderBooks.setLayoutManager(layoutManager);
        rvOrderBooks.setNestedScrollingEnabled(false);


        TextView tvSelectedCount = binding.idFrOrderTVSelectedCount;
        TextView tvSelectedCost = binding.idFrOrderTVSelectedCost;
        tvSelectedCount.setText(Integer.toString(rvModelBookCommonInfoRVModelList.size()));
        BigDecimal cost = new BigDecimal(0);
        for(BookCommonInfoRVModel book : rvModelBookCommonInfoRVModelList){
            cost = cost.add(book.getCost());
        }
        tvSelectedCost.setText(cost.toString() + " rub.");


//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        DividerItemDecoration itemDecorator = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
//        itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_blue_100_2h));
//        rvOrderBooks.addItemDecoration(itemDecorator);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}