package com.zlatamigas.readme.customview.recyclerview.search;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.customview.ItemBookSearchView;
import com.zlatamigas.readme.customview.recyclerview.cart.BookCartRVOptionsListener;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;

import java.util.ArrayList;
import java.util.Iterator;

public class BookSearchRVAdapter extends RecyclerView.Adapter<BookSearchRVAdapter.BookSearchViewHolder> {

    private Context context;
    private ArrayList<BookCommonInfoRVModel> booksList;

    private BookSearchRVOptionsListener listener;

    public BookSearchRVAdapter(
            Context context,
            ArrayList<BookCommonInfoRVModel> booksList,
            BookSearchRVOptionsListener listener) {

        this.context = context;
        this.booksList = booksList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemBookSearchView view = new ItemBookSearchView(context);

        return new BookSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookSearchViewHolder holder, int position) {

        BookCommonInfoRVModel model = booksList.get(position);

        ItemBookSearchView view = holder.itemView;

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

    public static class BookSearchViewHolder extends RecyclerView.ViewHolder{

        ItemBookSearchView itemView;

        public BookSearchViewHolder(@NonNull ItemBookSearchView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
