package com.zlatamigas.readme.customview.recyclerview.order;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.customview.ItemBookOrderView;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;

import java.util.ArrayList;
import java.util.Iterator;

public class BookOrderRVAdapter extends RecyclerView.Adapter<BookOrderRVAdapter.BookOrderViewHolder> {

    private Context context;
    private ArrayList<BookCommonInfoRVModel> booksList;


    public BookOrderRVAdapter(
            Context context,
            ArrayList<BookCommonInfoRVModel> booksList) {

        this.context = context;
        this.booksList = booksList;
    }

    @NonNull
    @Override
    public BookOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemBookOrderView view = new ItemBookOrderView(context);

        return new BookOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookOrderViewHolder holder, int position) {

        BookCommonInfoRVModel model = booksList.get(position);

        ItemBookOrderView view = holder.itemView;

        view.getTvTitle().setText(model.getTitle());

        StringBuilder sbAuthors = new StringBuilder("");
        Iterator<String> authorsIterator = model.getAuthors().iterator();
        if(authorsIterator.hasNext()){
            sbAuthors.append(authorsIterator.next());

            while (authorsIterator.hasNext()){
                sbAuthors.append(", ").append(authorsIterator.next());
            }
        }
        view.getTvAuthors().setText(sbAuthors.toString());


        view.getTvCost().setText(model.getCost().toString());

    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public static class BookOrderViewHolder extends RecyclerView.ViewHolder{

        ItemBookOrderView itemView;

        public BookOrderViewHolder(@NonNull ItemBookOrderView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
