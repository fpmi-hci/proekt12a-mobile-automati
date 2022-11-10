package com.zlatamigas.readme.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zlatamigas.readme.R;

public class ItemBookMyBooksView extends RelativeLayout {

    private ImageView ivCover;
    private TextView tvTitle, tvAuthors;

    public ItemBookMyBooksView(@NonNull Context context) {
        super(context);

        View view = inflate(getContext(), R.layout.item_book_mybooks, this);

        tvTitle = view.findViewById(R.id.idItemBookMyBooksTitle);
        tvAuthors = view.findViewById(R.id.idItemBookMyBooksAuthor);
        ivCover = view.findViewById(R.id.idItemBookMyBooksCover);

    }

    public ItemBookMyBooksView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ItemBookView,
                0, 0);

        try {
            String title = a.getString(R.styleable.ItemBookView_title);
            String author = a.getString(R.styleable.ItemBookView_author);
            int cover = a.getResourceId(R.styleable.ItemBookView_cover, R.color.ic_launcher_background);

            View view = inflate(getContext(), R.layout.item_book_mybooks, this);

            tvTitle = view.findViewById(R.id.idItemBookMyBooksTitle);
            tvTitle.setText(title);
            tvAuthors = view.findViewById(R.id.idItemBookMyBooksAuthor);
            tvAuthors.setText(author);
            ivCover = view.findViewById(R.id.idItemBookMyBooksCover);
            ivCover.setImageResource(cover);
        } finally {
            a.recycle();
        }
    }

    public ImageView getIvCover() {
        return ivCover;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvAuthors() {
        return tvAuthors;
    }
}
