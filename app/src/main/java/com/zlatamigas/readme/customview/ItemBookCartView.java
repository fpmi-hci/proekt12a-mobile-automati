package com.zlatamigas.readme.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewDebug;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zlatamigas.readme.R;

public class ItemBookCartView extends RelativeLayout {

    private ImageView ivCover;
    private TextView tvTitle, tvAuthors, tvCost;

    private CheckBox checkBox;
    private ImageButton btnDelete;

    public ItemBookCartView(@NonNull Context context) {
        super(context);

        View view = inflate(getContext(), R.layout.item_book_cart, this);

        tvTitle = view.findViewById(R.id.idItemBookCartTitle);
        tvAuthors = view.findViewById(R.id.idItemBookCartAuthor);
        tvCost = view.findViewById(R.id.idItemBookCartFLPrice);
        ivCover = view.findViewById(R.id.idItemBookCartCover);

        btnDelete = view.findViewById(R.id.idItemBookCartBtnDelete);
        checkBox = view.findViewById(R.id.idItemBookCartCheckbox);
    }

    public ItemBookCartView(@NonNull Context context, @Nullable AttributeSet attrs) {
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

            View view = inflate(getContext(), R.layout.item_book_cart, this);

            tvTitle = view.findViewById(R.id.idItemBookCartTitle);
            tvTitle.setText(title);
            tvAuthors = view.findViewById(R.id.idItemBookCartAuthor);
            tvAuthors.setText(author);
            tvCost = view.findViewById(R.id.idItemBookCartFLPrice);
            tvCost.setText(price);
            ivCover = view.findViewById(R.id.idItemBookCartCover);
            ivCover.setImageResource(cover);

            btnDelete = view.findViewById(R.id.idItemBookCartBtnDelete);
            checkBox = view.findViewById(R.id.idItemBookCartCheckbox);
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

    public TextView getTvCost() {
        return tvCost;
    }

    public ImageButton getBtnDelete() {
        return btnDelete;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    @ViewDebug.ExportedProperty
    public boolean isChecked() {
        return checkBox.isChecked();
    }

    public void setChecked(boolean checked) {
        checkBox.setChecked(checked);
    }
}
