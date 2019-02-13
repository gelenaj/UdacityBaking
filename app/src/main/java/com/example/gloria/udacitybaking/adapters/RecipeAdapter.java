package com.example.gloria.udacitybaking.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gloria.udacitybaking.Data.Recipe;
import com.example.gloria.udacitybaking.R;
import com.example.gloria.udacitybaking.holders.RecipeViewHolder;
import com.example.gloria.udacitybaking.ui.activities.RecipeListActivity;
import com.example.gloria.udacitybaking.utils.Listeners;


import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    public static final String LOG_TAG = RecipeAdapter.class.getSimpleName();


    private RecipeListActivity mParentActivity;
    private boolean mTwoPane;
    Context mContext;
   private List<Recipe> recipes;
    private Listeners.OnItemClickListener onItemClickListener;

    public RecipeAdapter(Context context,List<Recipe> recipes, Listeners.OnItemClickListener itemClickListener) {
      this.mContext = context;
       this.recipes = recipes;
        this.onItemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           View view = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.recipe_list_content, parent, false);
           return new RecipeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {

        holder.name.setText(recipes.get(position).getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }


}

