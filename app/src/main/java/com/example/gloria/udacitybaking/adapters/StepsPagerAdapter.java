package com.example.gloria.udacitybaking.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.gloria.udacitybaking.Data.Step;
import com.example.gloria.udacitybaking.R;
import com.example.gloria.udacitybaking.ui.fragments.RecipeStepsFragment;

import java.util.List;

public class StepsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private List<Step> mSteps;

    public StepsPagerAdapter(Context context, List<Step> steps, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        this.mSteps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putParcelable(RecipeStepsFragment.STEP_KEY, mSteps.get(position));
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        recipeStepsFragment.setArguments(args);

        return recipeStepsFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.format(mContext.getString(R.string.step), position);
    }

    @Override
    public int getCount() {
        return mSteps.size();
    }
}
