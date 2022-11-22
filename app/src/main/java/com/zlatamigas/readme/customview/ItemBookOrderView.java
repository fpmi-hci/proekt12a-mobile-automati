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

public class ItemBookOrderView extends RelativeLayout {

    private TextView tvTitle, tvAuthors, tvCost;

    public ItemBookOrderView(@NonNull Context context) {
        super(context);

        View view = inflate(getContext(), R.layout.item_book_order, this);

        tvTitle = view.findViewById(R.id.idItemBookOrderTitle);
        tvAuthors = view.findViewById(R.id.idItemBookOrderAuthor);
        tvCost = view.findViewById(R.id.idItemBookOrderPrice);

    }

    public ItemBookOrderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ItemBookView,
                0, 0);

        try {
            String title = a.getString(R.styleable.ItemBookView_title);
            String author = a.getString(R.styleable.ItemBookView_author);
            String price = a.getString(R.styleable.ItemBookView_price);
            int cover = a.getResourceId(R.styleable.ItemBookView_cover, R.color.ic_launcher_background);

            View view = inflate(getContext(), R.layout.item_book_order, this);

            tvTitle = view.findViewById(R.id.idItemBookOrderTitle);
            tvTitle.setText(title);
            tvAuthors = view.findViewById(R.id.idItemBookOrderAuthor);
            tvAuthors.setText(author);
            tvCost = view.findViewById(R.id.idItemBookOrderFLPrice);
            tvCost.setText(price);
        } finally {
            a.recycle();
        }
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvAuthors() {
        return tvAuthors;
    }

    public TextView getTvCost() {
        return tvCost;
    }
}
