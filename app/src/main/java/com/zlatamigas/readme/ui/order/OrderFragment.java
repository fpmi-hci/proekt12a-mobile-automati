package com.zlatamigas.readme.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.MainActivity;
import com.zlatamigas.readme.R;
import com.zlatamigas.readme.controller.APIProvider;
import com.zlatamigas.readme.controller.OrderController;
import com.zlatamigas.readme.controller.UserController;
import com.zlatamigas.readme.controller.apimodel.request.OrderRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.UserOrderResponseAPIModel;
import com.zlatamigas.readme.customview.recyclerview.order.BookOrderRVAdapter;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.databinding.FragmentOrderBinding;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

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

        rvOrderBooks = binding.idFrOrderRVBooks;

        String paymentType = getArguments().getString("payment_type");
        binding.idFrOrderTVPaymentType.setText(paymentType);

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
        tvSelectedCost.setText(cost.toString() + " руб.");

        OrderFragment currFragment = this;

        binding.idFrOrderBtnFinishOrder.setOnClickListener(v -> {

            OrderRequestAPIModel order = new OrderRequestAPIModel();
            List<Long> bookIds = new ArrayList<>(rvModelBookCommonInfoRVModelList.size());
            for(BookCommonInfoRVModel book : rvModelBookCommonInfoRVModelList){
                bookIds.add(book.getId());
            }
            order.setBooks(bookIds);

            Call<UserOrderResponseAPIModel> call = APIProvider.getInstance().getService().addUserOrder(
                    order,
                    UserController.getInstance().getToken());
            call.enqueue(new Callback<UserOrderResponseAPIModel>() {
                @Override
                public void onResponse(Call<UserOrderResponseAPIModel> call, Response<UserOrderResponseAPIModel> response) {

                    UserOrderResponseAPIModel body = response.body();
                    if(body != null) {

                        rvModelBookCommonInfoRVModelList.clear();

                        NavController navController = NavHostFragment.findNavController(currFragment);
                        navController.navigate(R.id.navigation_cart);


                        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                        builder.setTitle("Оплата подтверждена!")
                                .setPositiveButton("ОК", (dialog, id) -> {
                                    dialog.cancel();
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        System.out.println("success");
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                        builder.setTitle("Ошибка во время оплаты!")
                                .setMessage("Оплата отменена, попытайтесь повторно оформить заказ позже.")
                                .setPositiveButton("ОК", (dialog, id) -> {
                                    dialog.cancel();
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        System.out.println("empty? error?");
                    }


                }

                @Override
                public void onFailure(Call<UserOrderResponseAPIModel> call, Throwable t) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                    builder.setTitle("Ошибка связи с сервером!")
                            .setMessage("Проверьте соединение с интернетом!")
                            .setPositiveButton("ОК", (dialog, id) -> {
                                dialog.cancel();
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    System.out.println("failure");
                }
            });


        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}