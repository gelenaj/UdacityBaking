package com.example.gloria.udacitybaking.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.design.widget.TabLayout;


import com.example.gloria.udacitybaking.Data.Recipe;
import com.example.gloria.udacitybaking.R;
import com.example.gloria.udacitybaking.adapters.StepsPagerAdapter;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsDetailActivity extends AppCompatActivity {
    public static final String RECIPE_KEY = "recipe";
    public static final String STEP_CLICKED_KEY = "step";

    @BindView(R.id.step_tablayout)
    TabLayout mTab_layout;

    @BindView(R.id.steps_viewpager)
    ViewPager mViewPager;

    @BindView(android.R.id.content)
    View parentLayout;

    @BindBool(R.bool.twoPane)
    boolean isTwoPane;


    private Recipe recipe;
    private int mStepPosition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_details_activity);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey(RECIPE_KEY) && bundle.containsKey(STEP_CLICKED_KEY)){
            recipe = bundle.getParcelable(RECIPE_KEY);
            mStepPosition=bundle.getInt(STEP_CLICKED_KEY);

        }else{
            Snackbar.make(parentLayout, "Error loading recipes", Snackbar.LENGTH_LONG).show();
            finish();
        }

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(recipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        StepsPagerAdapter adapter = new StepsPagerAdapter(getApplicationContext(), recipe.getSteps(), getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTab_layout.setupWithViewPager(mViewPager);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
            mTab_layout.setVisibility(View.GONE);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                if (actionBar != null) {
                    actionBar.setTitle(recipe.getSteps().get(position).getShortDescription());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        mViewPager.setCurrentItem(mStepPosition);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
