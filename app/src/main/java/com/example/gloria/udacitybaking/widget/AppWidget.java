package com.example.gloria.udacitybaking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.gloria.udacitybaking.data.Recipe;
import com.example.gloria.udacitybaking.Prefs;
import com.example.gloria.udacitybaking.R;
import com.example.gloria.udacitybaking.ui.activities.RecipeListActivity;

public class AppWidget extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        Recipe recipe = Prefs.getRecipe(context);
        if (null != recipe) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, RecipeListActivity.class), 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.udacity_baking_app_widget);

            views.setTextViewText(R.id.recipe_widget_name_text, recipe.getName());

            views.setOnClickPendingIntent(R.id.recipe_widget_name_text, pendingIntent);


            Intent intent = new Intent(context, AppWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            views.setRemoteAdapter(R.id.recipe_widget_listview, intent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipe_widget_listview);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}