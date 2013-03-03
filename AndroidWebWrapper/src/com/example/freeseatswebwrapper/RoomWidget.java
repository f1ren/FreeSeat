package com.example.freeseatswebwrapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.example.freeseatswebwrapper.R;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

public class RoomWidget extends AppWidgetProvider {
	@Override
    public void onUpdate( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds )
    {
        RemoteViews remoteViews;
        ComponentName watchWidget;
        DateFormat format = SimpleDateFormat.getTimeInstance( SimpleDateFormat.MEDIUM, Locale.getDefault() );

        remoteViews = new RemoteViews( context.getPackageName(), R.layout.room_widget );
        watchWidget = new ComponentName( context, RoomWidget.class );
        remoteViews.setTextViewText( R.id.widget_textview, "Time = " + format.format( new Date()));
        appWidgetManager.updateAppWidget( watchWidget, remoteViews );
    }
}
