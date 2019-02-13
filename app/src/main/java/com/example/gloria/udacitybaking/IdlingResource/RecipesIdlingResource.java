package com.example.gloria.udacitybaking.IdlingResource;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;

import com.example.gloria.udacitybaking.BuildConfig;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class RecipesIdlingResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback callback;

    private AtomicBoolean isIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIdleState(boolean idleState){
        isIdleNow.set(idleState);

        if(idleState && callback !=null ){
            callback.onTransitionToIdle();
        }
    }


}
