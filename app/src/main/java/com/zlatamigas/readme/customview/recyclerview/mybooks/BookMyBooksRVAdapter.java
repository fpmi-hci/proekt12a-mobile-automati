package com.zlatamigas.readme.customview.recyclerview.mybooks;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zlatamigas.readme.R;
import com.zlatamigas.readme.customview.ItemBookMyBooksView;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.util.StringMerger;

import java.util.ArrayList;

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

        if (model.getImgUrl() != null && !model.getImgUrl().isEmpty()) {
            Picasso.get()
                    .load(model.getImgUrl())
                    .error(R.mipmap.ic_launcher)
                    .into(view.getIvCover());
        } else {
            view.getIvCover().setImageResource(R.mipmap.ic_launcher);
        }

        view.getTvTitle().setText(model.getTitle());
        view.getTvAuthors().setText(StringMerger.mergeStrings(model.getAuthors()));

        view.getIvCover().setOnClickListener(v -> {
            listener.onBookClicked(model, view);
        });
        view.getIvDownload().setOnClickListener(v ->{
            listener.onDownloadClicked(model, view);
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public static class BookMyBooksViewHolder extends RecyclerView.ViewHolder {

        ItemBookMyBooksView itemView;

        public BookMyBooksViewHolder(@NonNull ItemBookMyBooksView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
