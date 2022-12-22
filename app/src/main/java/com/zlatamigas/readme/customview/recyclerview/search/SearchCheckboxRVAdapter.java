package com.zlatamigas.readme.customview.recyclerview.search;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zlatamigas.readme.R;
import com.zlatamigas.readme.customview.recyclerview.entity.SearchCheckboxRVModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SearchCheckboxRVAdapter extends RecyclerView.Adapter<SearchCheckboxRVAdapter.SearchCheckboxViewHolder> {

    private Context context;
    private ArrayList<SearchCheckboxRVModel> checkboxList;


    public SearchCheckboxRVAdapter(
            Context context,
            ArrayList<SearchCheckboxRVModel> checkboxList) {

        this.context = context;
        this.checkboxList = checkboxList;
    }

    @NonNull
    @Override
    public SearchCheckboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CheckBox view = new CheckBox(new ContextThemeWrapper(context, R.style.CheckBoxTheme));

        return new SearchCheckboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCheckboxViewHolder holder, int position) {

        SearchCheckboxRVModel model = checkboxList.get(position);

        CheckBox view = holder.checkBox;
        view.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.setSelected(isChecked);
        });
        view.setTextColor(context.getColor(R.color.black));
        view.setText(model.getValue());
        view.setChecked(model.isSelected());
    }

    @Override
    public int getItemCount() {
        return checkboxList.size();
    }

    public ArrayList<SearchCheckboxRVModel> getCheckboxList() {
        return checkboxList;
    }

    public List<Long> getSelectedGenreIds(){

        List<Long> selectedGenres = new LinkedList<>();

        for(SearchCheckboxRVModel cb :checkboxList){
            if(cb.isSelected()){
                selectedGenres.add(cb.getId());
            }
        }

        return selectedGenres;
    }

    public static class SearchCheckboxViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;

        public SearchCheckboxViewHolder(@NonNull CheckBox itemView) {
            super(itemView);

            checkBox = itemView;
        }
    }
}
