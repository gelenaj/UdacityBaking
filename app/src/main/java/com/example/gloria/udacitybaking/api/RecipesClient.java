package com.example.gloria.udacitybaking.api;

import com.example.gloria.udacitybaking.data.Recipe;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class RecipesClient implements Serializable {

    private static volatile RecipesClient recipesClient = new RecipesClient();
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";


    private RecipeApi recipesApi;


    private RecipesClient(){

        if(recipesClient !=null){
            throw new RuntimeException("getInstance() required");
        }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

           recipesApi = retrofit.create(RecipeApi.class);

    }

    public static RecipesClient getInstance(){
        if(recipesClient == null){
            synchronized (RecipesClient.class){
                if(recipesClient == null)
                    recipesClient = new RecipesClient();
            }
        }
        return recipesClient;
    }

    public void getRecipes(final RecipesCallback<List<Recipe>> recipesCallback){
        recipesApi.fetchMovies().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipesCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                if(call.isCanceled()){
                    recipesCallback.onCancel();
                }else{
                    recipesCallback.onResponse(null);

                }
            }
        });
    }

}
