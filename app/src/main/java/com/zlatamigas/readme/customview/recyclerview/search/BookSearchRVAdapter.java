package com.zlatamigas.readme.customview.recyclerview.search;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zlatamigas.readme.R;
import com.zlatamigas.readme.customview.ItemBookSearchView;
import com.zlatamigas.readme.customview.recyclerview.cart.BookCartRVOptionsListener;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.util.StringMerger;

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

        if(model.getImgUrl() != null && !model.getImgUrl().isEmpty()){
            Picasso.get()
                    .load(model.getImgUrl())
                    .error(R.mipmap.ic_launcher)
                    .into(view.getIvCover());
        } else {
            view.getIvCover().setImageResource(R.mipmap.ic_launcher);
        }

        view.getTvTitle().setText(model.getTitle());

        view.getTvAuthors().setText(StringMerger.mergeStrings(model.getAuthors()));

        view.getTvCost().setText(model.getCost().toString());

        view.getIvCover().setOnClickListener(v -> {
            listener.onBookClicked(model, view);
        });
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
