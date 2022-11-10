package com.zlatamigas.readme.customview.recyclerview.cart;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.customview.ItemBookCartView;
import com.zlatamigas.readme.customview.ItemBookSearchView;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;

import java.util.ArrayList;
import java.util.Iterator;

public class BookCartRVAdapter extends RecyclerView.Adapter<BookCartRVAdapter.BookCartViewHolder> {

    private Context context;
    private ArrayList<BookCommonInfoRVModel> booksList;

    private BookCartRVOptionsListener listener;

    public BookCartRVAdapter(
            Context context,
            ArrayList<BookCommonInfoRVModel> booksList,
            BookCartRVOptionsListener listener) {

        this.context = context;
        this.booksList = booksList;
        this.listener = listener;
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

        // cover

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

    public static class BookCartViewHolder extends RecyclerView.ViewHolder{

        ItemBookCartView itemView;

        public BookCartViewHolder(@NonNull ItemBookCartView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
