package com.example.gloria.udacitybaking.holders;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.gloria.udacitybaking.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ingredient_name_text)
    public TextView ingredientName;

    public IngredientsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}