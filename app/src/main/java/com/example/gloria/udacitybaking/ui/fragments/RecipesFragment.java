package com.example.gloria.udacitybaking.ui.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.gloria.udacitybaking.Data.Recipe;
import com.example.gloria.udacitybaking.MyApplication;
import com.example.gloria.udacitybaking.Prefs;
import com.example.gloria.udacitybaking.R;
import com.example.gloria.udacitybaking.adapters.RecipeAdapter;
import com.example.gloria.udacitybaking.api.RecipesCallback;
import com.example.gloria.udacitybaking.api.RecipesClient;
import com.example.gloria.udacitybaking.utils.Listeners;
import com.example.gloria.udacitybaking.utils.NetworkUtil;
import com.example.gloria.udacitybaking.widget.AppWidgetService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipesFragment extends Fragment {
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recipes_recycler)
    RecyclerView mRecylerView;


    @BindView(R.id.noDataLayout)
    ConstraintLayout mEmptyContainer;

    @BindView(R.id.recipes_fragment)
    RelativeLayout recipesFragment;

    public static String RECIPE_KEY = "recipes";

    private OnRecipeClickListener mListener;

    private Unbinder unbinder;
    private List<Recipe> mRecipes;

    private MyApplication myApplication;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mRecipes == null) {
                getRecipes();
            }
        }
    };

    public RecipesFragment() { }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        unbinder = ButterKnife.bind(this,view);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRecipes();
            }
        });



        mEmptyContainer.setVisibility(View.VISIBLE);
        setUpRecyclerView();

        myApplication = (MyApplication) getActivity().getApplicationContext();
        myApplication.setIdleState(false);

        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPE_KEY)) {
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPE_KEY);

            mRecylerView.setAdapter(new RecipeAdapter(getActivity().getApplicationContext(),
                    mRecipes, new Listeners.OnItemClickListener() {

                @Override
                public void onItemClickListener(int pos) {
                    mListener.onRecipeSelected(mRecipes.get(pos));


                }
            }));
            recipesLayout();

        }
        return view;
    }

    private void getRecipes() {
        if(NetworkUtil.isConnected(getActivity().getApplicationContext())){

           refreshLayout.setRefreshing(true);

            RecipesClient.getInstance().getRecipes(new RecipesCallback<List<Recipe>>() {
                @Override
                public void onResponse(final List<Recipe> result) {
                    if(result != null){

                        mRecipes = result;
                        mRecylerView.setAdapter(new RecipeAdapter(getActivity().getApplicationContext(),
                                mRecipes, new Listeners.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int pos) {
                                mListener.onRecipeSelected(mRecipes.get(pos));
                            }
                        }));
                        if (Prefs.getRecipe(getActivity().getApplicationContext()) == null) {
                            AppWidgetService.updateWidget(getActivity(), mRecipes.get(0));
                        }

                    }else{

                    }
                    recipesLayout();
                }

                @Override
                public void onCancel() {
                    recipesLayout();
                }
            });

        }
    }

    private void recipesLayout() {

        boolean isLoaded = mRecipes !=null && mRecipes.size() > 0;
        refreshLayout.setRefreshing(false);

        mRecylerView.setVisibility(isLoaded? View.VISIBLE : View.GONE);
        mEmptyContainer.setVisibility(isLoaded? View.GONE: View.VISIBLE);

      myApplication.setIdleState(true);
    }

    private void setUpRecyclerView() {
        mRecylerView.setVisibility(View.GONE);
        mRecylerView.setHasFixedSize(true);

        boolean twoPane= false;
        if(twoPane){
            mRecylerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        }else{
            mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }

   mRecylerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnRecipeClickListener){
            mListener = (OnRecipeClickListener) context;
        }else{
            //error
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener=null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
         unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onSaveInstanceState(@NonNull  Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mRecipes != null && !mRecipes.isEmpty()){
            outState.putParcelableArrayList(RECIPE_KEY, (ArrayList<? extends Parcelable>)mRecipes);
        }

    }

    public interface OnRecipeClickListener{
        void onRecipeSelected(Recipe recipe);
    }


}

