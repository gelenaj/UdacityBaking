package com.example.gloria.udacitybaking;

import android.app.Application;
import android.arch.core.BuildConfig;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.example.gloria.udacitybaking.IdlingResource.RecipesIdlingResource;

public class MyApplication extends Application {

    @Nullable
    private RecipesIdlingResource mIdlingResource;


    @VisibleForTesting
    private void initIdling() {
        if (mIdlingResource == null) {
            mIdlingResource = new RecipesIdlingResource();
        }
    }

    public MyApplication() {
        if (BuildConfig.DEBUG) {
            initIdling();
        }


    }

    public void setIdleState(boolean state) {
        if (mIdlingResource != null)
            mIdlingResource.setIdleState(state);


    }

    @NonNull
    public RecipesIdlingResource getIdlingResource() {
        return mIdlingResource;
    }
}
