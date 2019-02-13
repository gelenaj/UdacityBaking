package com.example.gloria.udacitybaking.api;



public interface RecipesCallback<T> {
    void onResponse(T result);
    void onCancel();
}
