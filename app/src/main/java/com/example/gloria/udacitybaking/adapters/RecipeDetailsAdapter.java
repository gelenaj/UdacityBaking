package com.example.gloria.udacitybaking.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gloria.udacitybaking.Data.Ingredients;
import com.example.gloria.udacitybaking.Data.Recipe;
import com.example.gloria.udacitybaking.R;
import com.example.gloria.udacitybaking.holders.IngredientsViewHolder;
import com.example.gloria.udacitybaking.holders.StepsViewHolder;
import com.example.gloria.udacitybaking.ui.activities.RecipeDetailActivity;
import com.example.gloria.udacitybaking.utils.Listeners;

import java.util.Locale;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Recipe recipe;
    private Listeners.OnItemClickListener mOnItemClickListener;

    public RecipeDetailsAdapter(Recipe recipe, Listeners.OnItemClickListener onItemClickListener) {
        this.recipe = recipe;
        this.mOnItemClickListener = onItemClickListener;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new IngredientsViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredient_list_content, parent, false));
        } else {
            return new StepsViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_list_content, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof IngredientsViewHolder) {
            IngredientsViewHolder viewHolder = (IngredientsViewHolder) holder;

            StringBuilder ingValue = new StringBuilder();
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                Ingredients ingredients = recipe.getIngredients().get(i);
                ingValue.append(String.format(Locale.getDefault(), "%s (%d %s)", ingredients.getIngredient(), ingredients.getQuantity(), ingredients.getMeasure()));
                if (i != recipe.getIngredients().size() - 1)
                    ingValue.append("\n");
            }

            viewHolder.ingredientName.setText(ingValue.toString());
        } else if (holder instanceof StepsViewHolder) {
            StepsViewHolder viewHolder = (StepsViewHolder) holder;
            viewHolder.stepOrder.setText(String.valueOf(position - 1) + ".");
            viewHolder.step.setText(recipe.getSteps().get(position - 1).getShortDescription());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClickListener(position- 1);

                }
            });
        }




    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return recipe.getSteps().size();
    }




}
