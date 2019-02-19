package com.example.gloria.udacitybaking.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.gloria.udacitybaking.data.Recipe;
import com.example.gloria.udacitybaking.R;
import com.example.gloria.udacitybaking.ui.fragments.RecipesFragment;


public class RecipeListActivity extends AppCompatActivity implements RecipesFragment.OnRecipeClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
    }


    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.RECIPE_KEY, recipe);
        startActivity(intent);
    }
}




