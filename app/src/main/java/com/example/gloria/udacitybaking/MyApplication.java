package com.example.gloria.udacitybaking;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;


import com.example.gloria.udacitybaking.IdlingResource.RecipesIdlingResource;

public class MyApplication extends Application {

    @Nullable
    private RecipesIdlingResource mIdlingResource;


    @VisibleForTesting
    @NonNull
    private IdlingResource initIdling(){
        if(mIdlingResource == null ) {
            mIdlingResource = new RecipesIdlingResource();
        }
        return mIdlingResource;
    }

    public MyApplication(){
        if(BuildConfig.DEBUG){
            initIdling();
        }


    }

    public void setIdleState(boolean state){
        if(mIdlingResource != null)
            mIdlingResource.setIdleState(state);



    }

    @NonNull
    public RecipesIdlingResource getIdlingResource(){return mIdlingResource;}
}
