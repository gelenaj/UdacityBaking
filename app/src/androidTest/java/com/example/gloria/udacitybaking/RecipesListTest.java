 package com.example.gloria.udacitybaking;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.gloria.udacitybaking.ui.activities.RecipeDetailActivity;
import com.example.gloria.udacitybaking.ui.activities.RecipeListActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;


 @RunWith(AndroidJUnit4.class)
public class RecipesListTest{
   Context context;

    @Rule public ActivityTestRule<RecipeListActivity> mActivityTest =
            new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void clickOnRecipe_openDetails() {
        onView(ViewMatchers.withId(R.id.recipes_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(ViewMatchers.withId(R.id.detail_toolbar))
                .check(matches(isDisplayed()));
    }


    @Test
    public void checkText_RecipeActivity() {
        onView(ViewMatchers.withId(R.id.recipes_recycler)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Brownies")).check(matches(isDisplayed()));
    }




}

