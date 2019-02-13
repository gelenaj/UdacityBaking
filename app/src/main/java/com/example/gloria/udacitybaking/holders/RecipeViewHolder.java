package com.example.gloria.udacitybaking.holders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gloria.udacitybaking.Data.Recipe;
import com.example.gloria.udacitybaking.R;
import com.example.gloria.udacitybaking.utils.Listeners;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.recipe_name_text)
    public TextView name;


   public RecipeViewHolder(View itemView){
       super(itemView);
       ButterKnife.bind(this, itemView);

    }
}
