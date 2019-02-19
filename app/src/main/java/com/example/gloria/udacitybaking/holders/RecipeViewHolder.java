package com.example.gloria.udacitybaking.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.gloria.udacitybaking.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_name_text)
    public TextView name;


    public RecipeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}
