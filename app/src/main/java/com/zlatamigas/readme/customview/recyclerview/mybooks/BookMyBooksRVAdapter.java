package com.zlatamigas.readme.customview.recyclerview.mybooks;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.customview.ItemBookMyBooksView;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;

import java.util.ArrayList;
import java.util.Iterator;

public class BookMyBooksRVAdapter extends RecyclerView.Adapter<BookMyBooksRVAdapter.BookMyBooksViewHolder> {

    private Context context;
    private ArrayList<BookCommonInfoRVModel> booksList;

    private BookMyBooksRVOptionsListener listener;

    public BookMyBooksRVAdapter(
            Context context,
            ArrayList<BookCommonInfoRVModel> booksList,
            BookMyBooksRVOptionsListener listener) {

        this.context = context;
        this.booksList = booksList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookMyBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemBookMyBooksView view = new ItemBookMyBooksView(context);

        return new BookMyBooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookMyBooksViewHolder holder, int position) {

        BookCommonInfoRVModel model = booksList.get(position);

        ItemBookMyBooksView view = holder.itemView;

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
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public static class BookMyBooksViewHolder extends RecyclerView.ViewHolder{

        ItemBookMyBooksView itemView;

        public BookMyBooksViewHolder(@NonNull ItemBookMyBooksView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
