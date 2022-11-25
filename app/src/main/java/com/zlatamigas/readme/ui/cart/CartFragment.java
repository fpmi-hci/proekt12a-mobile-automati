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
import com.zlatamigas.readme.controller.APIProvider;
import com.zlatamigas.readme.controller.OrderController;
import com.zlatamigas.readme.controller.UserController;
import com.zlatamigas.readme.controller.apimodel.response.AuthorResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.BookResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.CartResponseAPIModel;
import com.zlatamigas.readme.customview.ItemBookCartView;
import com.zlatamigas.readme.customview.recyclerview.cart.BookCartRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.cart.BookCartRVOptionsListener;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.databinding.FragmentCartBinding;

import java.math.BigDecimal;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements BookCartRVOptionsListener {



    private RecyclerView rvCartBooks;
    private BookCartRVAdapter rvAdapter;

    private CartViewModel cartViewModel;
    private FragmentCartBinding binding;

    private CheckBox cbSelectAll;
    private OrderController orderController;

    private TextView tvSelectedCount;
    private TextView tvSelectedCost;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        orderController = OrderController.getInstance();


        rvCartBooks = binding.idFrCartRVBooks;

        CartFragment currFragment = this;
        ArrayList<BookCommonInfoRVModel> rvModelBookCommonInfoRVModelList = new ArrayList<>();

        Call<CartResponseAPIModel> call = APIProvider.getInstance().getService().getUserCart(UserController.getInstance().getToken());
        call.enqueue(new Callback<CartResponseAPIModel>() {
            @Override
            public void onResponse(Call<CartResponseAPIModel> call, Response<CartResponseAPIModel> response) {

                CartResponseAPIModel body = response.body();
                if(body != null) {

                    rvModelBookCommonInfoRVModelList.clear();

                    for(BookResponseAPIModel book: body.getBooks()){
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
                    rvAdapter = new BookCartRVAdapter(requireActivity(), rvModelBookCommonInfoRVModelList, currFragment);
                    rvCartBooks.setAdapter(rvAdapter);

                    System.out.println("success");
                } else {
                    System.out.println("empty? error?");
                }
            }

            @Override
            public void onFailure(Call<CartResponseAPIModel> call, Throwable t) {
                System.out.println("failure");
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        rvCartBooks.setLayoutManager(layoutManager);
        rvCartBooks.setNestedScrollingEnabled(false);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider_blue_100_2h));
        rvCartBooks.addItemDecoration(itemDecorator);

        Button btnSearch = binding.idFrCartBtnToOrder;
        btnSearch.setOnClickListener(v -> {

            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.navigation_order);
        });


        cbSelectAll = binding.idFrCartCBSelectAll;
        cbSelectAll.setOnClickListener(v -> {
            if(cbSelectAll.isChecked()) {
                rvAdapter.selectAll();
                orderController.clear();
                orderController.replaceAll(rvModelBookCommonInfoRVModelList);
            } else {
                rvAdapter.unselectAll();
                orderController.clear();
            }
            setSelectedBooksSumInfo();
        });

        tvSelectedCount = binding.idFrCartTVSelectedCount;
        tvSelectedCost = binding.idFrCartTVSelectedCost;

        setSelectedBooksSumInfo();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onBookClicked(BookCommonInfoRVModel book, ItemBookCartView v) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle args = new Bundle();
        args.putLong("book_id", book.getId());
        navController.navigate(R.id.navigation_book, args);
    }

    @Override
    public void onSelectCheckBoxClicked(BookCommonInfoRVModel book, ItemBookCartView v) {
        if(v.getCheckBox().isChecked()){
            orderController.add(book);
        } else {
            orderController.remove(book);
            cbSelectAll.setChecked(false);
        }
        setSelectedBooksSumInfo();
    }

    @Override
    public void onDeleteBookClicked(BookCommonInfoRVModel book, int position) {

        Call<CartResponseAPIModel> call = APIProvider.getInstance().getService().removeFromCart(book.getId(), UserController.getInstance().getToken());
        call.enqueue(new Callback<CartResponseAPIModel>() {
            @Override
            public void onResponse(Call<CartResponseAPIModel> call, Response<CartResponseAPIModel> response) {

                CartResponseAPIModel body = response.body();
                if(body != null) {

                    rvAdapter.deleteViewWithModelAt(position);
                    orderController.remove(book);
                    setSelectedBooksSumInfo();

                    System.out.println("success");
                } else {
                    System.out.println("empty? error?");
                }
            }

            @Override
            public void onFailure(Call<CartResponseAPIModel> call, Throwable t) {
                System.out.println("failure");
            }
        });
    }

    private void setSelectedBooksSumInfo(){
        tvSelectedCount.setText(Integer.toString(orderController.getSelectedBooks().size()));
        BigDecimal cost = new BigDecimal(0);
        for(BookCommonInfoRVModel book : orderController.getSelectedBooks()){
            cost = cost.add(book.getCost());
        }
        tvSelectedCost.setText(cost.toString() + " rub.");
    }

    @Override
    public void onResume() {
        super.onResume();
        orderController.clear();
        cbSelectAll.setChecked(false);
        setSelectedBooksSumInfo();
    }
}