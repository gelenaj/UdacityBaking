package com.example.gloria.udacitybaking.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.gloria.udacitybaking.data.Recipe;
import com.example.gloria.udacitybaking.R;
import com.example.gloria.udacitybaking.adapters.RecipeDetailsAdapter;
import com.example.gloria.udacitybaking.ui.fragments.RecipeStepsFragment;
import com.example.gloria.udacitybaking.utils.Listeners;
import com.example.gloria.udacitybaking.widget.AppWidgetService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String RECIPE_KEY = "recipe";

    private Recipe mRecipe;

    @BindView(R.id.recipe_steps)
    RecyclerView mRecyler;

    @BindView(android.R.id.content)
    View detailParentlayout;

    private boolean mTwoPane;

    @BindView(R.id.layout)
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(RECIPE_KEY)) {
            mRecipe = bundle.getParcelable(RECIPE_KEY);

        } else {
            finish();
        }

        setContentView(R.layout.activity_recipe_detail);

        ButterKnife.bind(this);


        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mRecipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        mTwoPane = getResources().getBoolean(R.bool.twoPane);
        if (mTwoPane) {
            if (savedInstanceState == null && !mRecipe.getSteps().isEmpty()) {
                displayStep(0);
            }
        }

        setUpRecycler();


    }

    private void setUpRecycler() {
        mRecyler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyler.setAdapter(new RecipeDetailsAdapter(mRecipe, new Listeners.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                displayStep(pos);
            }
        }));
    }


    private void displayStep(int position) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeStepsFragment.STEP_KEY, mRecipe.getSteps().get(position));
            RecipeStepsFragment fragment = new RecipeStepsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepsDetailActivity.class);
            intent.putExtra(StepsDetailActivity.RECIPE_KEY, mRecipe);
            intent.putExtra(StepsDetailActivity.STEP_CLICKED_KEY, position);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            AppWidgetService.updateWidget(this, mRecipe);
            Snackbar.make(coordinatorLayout, String.format(getString(R.string.add), mRecipe.getName()), Snackbar.LENGTH_LONG).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


}
