/*
 * TimeCardActivity.java
 *
 * Copyright 2014 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 26, 2013 at 10:02:28 PM.
 */

package com.belcan.beltime.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.belcan.beltime.R;
import com.belcan.beltime.model.Activity;
import com.belcan.beltime.model.ITimeCardListener;
import com.belcan.beltime.model.TimeCard;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The time card activity.
 */
public final class TimeCardActivity
    extends AbstractBeltimeActivity
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The activities list view. */
    private ListView activitiesListView_;

    /** The display utilities. */
    private final DisplayUtils displayUtils_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TimeCardActivity} class.
     */
    public TimeCardActivity()
    {
        displayUtils_ = new DisplayUtils( this );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the adapter for the activities list view.
     * 
     * @return The adapter for the activities list view.
     */
    private ListAdapter createActivitiesAdapter()
    {
        final String chargeNumberColumnName = "chargeNumber"; //$NON-NLS-1$
        final String startTimeColumnName = "startTime"; //$NON-NLS-1$
        final String stopTimeColumnName = "stopTime"; //$NON-NLS-1$
        final String durationColumnName = "duration"; //$NON-NLS-1$
        final String[] from = {
            chargeNumberColumnName, //
            startTimeColumnName, //
            stopTimeColumnName, //
            durationColumnName
        };
        final int[] to = {
            R.id.chargeNumberTextView, //
            R.id.startTimeTextView, //
            R.id.stopTimeTextView, //
            R.id.durationTextView
        };

        final List<Map<String, Object>> activitiesData = new ArrayList<Map<String, Object>>();
        for( final Activity activity : getTimeCard().getActivities() )
        {
            assert activity != null;
            final Map<String, Object> activityData = new HashMap<String, Object>();
            activityData.put( chargeNumberColumnName, displayUtils_.formatChargeNumber( activity ) );
            activityData.put( startTimeColumnName, displayUtils_.formatStartTime( activity ) );
            activityData.put( stopTimeColumnName, displayUtils_.formatStopTime( activity ) );
            activityData.put( durationColumnName, displayUtils_.formatDuration( activity ) );
            activitiesData.add( activityData );
        }

        return new SimpleAdapter( this, activitiesData, R.layout.view_activity, from, to );
    }

    /*
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(
        @Nullable
        final Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_time_card );
        activitiesListView_ = (ListView)findViewById( R.id.activitiesListView );
    }

    /*
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause()
    {
        super.onPause();

        getTimeCard().setTimeCardListener( null );
    }

    /*
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        getTimeCard().setTimeCardListener( new TimeCardListener() );
        update();
    }

    /**
     * Updates the state of the activity based on the model.
     */
    private void update()
    {
        activitiesListView_.setAdapter( createActivitiesAdapter() );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The time card listener for the activity.
     */
    @SuppressWarnings( "synthetic-access" )
    private final class TimeCardListener
        implements ITimeCardListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TimeCardListener} class.
         */
        TimeCardListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see com.belcan.beltime.model.ITimeCardListener#onActivityStarted(com.belcan.beltime.model.TimeCard, com.belcan.beltime.model.Activity)
         */
        @Override
        public void onActivityStarted(
            final TimeCard timeCard,
            final Activity activity )
        {
            update();
        }

        /*
         * @see com.belcan.beltime.model.ITimeCardListener#onActivityStopped(com.belcan.beltime.model.TimeCard, com.belcan.beltime.model.Activity)
         */
        @Override
        public void onActivityStopped(
            final TimeCard timeCard,
            final Activity activity )
        {
            update();
        }

        /*
         * @see com.belcan.beltime.model.ITimeCardListener#onReset(com.belcan.beltime.model.TimeCard)
         */
        @Override
        public void onReset(
            final TimeCard timeCard )
        {
            update();
        }
    }
}
