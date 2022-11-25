package com.zlatamigas.readme.customview.recyclerview.cart;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zlatamigas.readme.R;
import com.zlatamigas.readme.customview.ItemBookCartView;
import com.zlatamigas.readme.customview.ItemBookSearchView;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.util.StringMerger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class BookCartRVAdapter extends RecyclerView.Adapter<BookCartRVAdapter.BookCartViewHolder> {

    private Context context;
    private ArrayList<BookCommonInfoRVModel> booksList;
    private ArrayList<Boolean> selectedList;

    private BookCartRVOptionsListener listener;

    // For checkbox control
    private ArrayList<ItemBookCartView> viewList;

    public BookCartRVAdapter(
            Context context,
            ArrayList<BookCommonInfoRVModel> booksList,
            BookCartRVOptionsListener listener) {

        this.context = context;
        this.booksList = booksList;
        this.listener = listener;
        viewList = new ArrayList<>();
        selectedList = new ArrayList<>(Collections.nCopies(booksList.size(), false));
    }

    @NonNull
    @Override
    public BookCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemBookCartView view = new ItemBookCartView(context);

        return new BookCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookCartViewHolder holder, int position) {

        BookCommonInfoRVModel model = booksList.get(position);

        ItemBookCartView view = holder.itemView;

        viewList.add(position, view);

        boolean selected = selectedList.get(position);
        view.setChecked(selected);

        if(model.getImgUrl() != null && !model.getImgUrl().isEmpty()){
            Picasso.get()
                    .load(model.getImgUrl())
                    .error(R.mipmap.ic_launcher)
                    .into(view.getIvCover());
        } else {
            view.getIvCover().setImageResource(R.mipmap.ic_launcher);
        }

        view.getBtnDelete().setOnClickListener(v -> {
            listener.onDeleteBookClicked(model, position);
        });
        view.getCheckBox().setOnClickListener(v -> {
            listener.onSelectCheckBoxClicked(model, view);
            selectedList.set(position, view.isChecked());
        });
        view.getIvCover().setOnClickListener(v -> {
            listener.onBookClicked(model, view);
        });


        view.getTvTitle().setText(model.getTitle());
        view.getTvAuthors().setText(StringMerger.mergeStrings(model.getAuthors()));


        view.getTvCost().setText(model.getCost().toString());

    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public static class BookCartViewHolder extends RecyclerView.ViewHolder{

        ItemBookCartView itemView;

        public BookCartViewHolder(@NonNull ItemBookCartView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    public void selectAll(){
        selectedList.clear();
        selectedList.addAll(Collections.nCopies(booksList.size(), true));
        for(ItemBookCartView v : viewList){
            v.getCheckBox().setChecked(true);
        }
    }

    public void unselectAll(){
        selectedList.clear();
        selectedList.addAll(Collections.nCopies(booksList.size(), false));
        for(ItemBookCartView v : viewList){
            v.getCheckBox().setChecked(false);
        }
    }

    public void deleteViewWithModelAt(int position){
        booksList.remove(position);
        viewList.remove(position);
        selectedList.remove(position);
        //notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
