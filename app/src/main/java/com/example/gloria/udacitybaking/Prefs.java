package com.example.gloria.udacitybaking;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gloria.udacitybaking.data.Recipe;

public class Prefs {
    private static final String PREF_NAME = "prefs";

    public static void saveRecipe(Context context, Recipe recipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        prefs.putString(context.getString(R.string.widget_rk), Recipe.toBase64String(recipe));

        prefs.apply();
    }

    public static Recipe getRecipe(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String recipeBase64 = prefs.getString(context.getString(R.string.widget_rk), "");

        return "".equals(recipeBase64) ? null :
                Recipe.fromBase64(prefs.getString(context.getString(R.string.widget_rk), ""));

    }
}
