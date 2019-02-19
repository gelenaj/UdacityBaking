package com.example.gloria.udacitybaking.api;

import com.example.gloria.udacitybaking.data.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

interface RecipeApi {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> fetchMovies();
}

