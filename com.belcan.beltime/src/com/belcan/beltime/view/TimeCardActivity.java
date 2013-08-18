/*
 * TimeCardActivity.java
 *
 * Copyright 2013 Beltime contributors and others.
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
import com.belcan.beltime.model.ITimeCardListener;
import com.belcan.beltime.model.Job;
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

    /** The display utilities. */
    private final DisplayUtils displayUtils_;

    /** The jobs list view. */
    private ListView jobsListView_;


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
     * Creates the adapter for the jobs list view.
     * 
     * @return The adapter for the jobs list view.
     */
    private ListAdapter createJobsAdapter()
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

        final List<Map<String, Object>> jobsData = new ArrayList<Map<String, Object>>();
        for( final Job job : getTimeCard().getJobs() )
        {
            assert job != null;
            final Map<String, Object> jobData = new HashMap<String, Object>();
            jobData.put( chargeNumberColumnName, displayUtils_.formatChargeNumber( job ) );
            jobData.put( startTimeColumnName, displayUtils_.formatStartTime( job ) );
            jobData.put( stopTimeColumnName, displayUtils_.formatStopTime( job ) );
            jobData.put( durationColumnName, displayUtils_.formatDuration( job ) );
            jobsData.add( jobData );
        }

        return new SimpleAdapter( this, jobsData, R.layout.view_job, from, to );
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
        jobsListView_ = (ListView)findViewById( R.id.jobsListView );
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
        jobsListView_.setAdapter( createJobsAdapter() );
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
         * @see com.belcan.beltime.model.ITimeCardListener#onJobStarted(com.belcan.beltime.model.TimeCard, com.belcan.beltime.model.Job)
         */
        @Override
        public void onJobStarted(
            final TimeCard timeCard,
            final Job job )
        {
            update();
        }

        /*
         * @see com.belcan.beltime.model.ITimeCardListener#onJobStopped(com.belcan.beltime.model.TimeCard, com.belcan.beltime.model.Job)
         */
        @Override
        public void onJobStopped(
            final TimeCard timeCard,
            final Job job )
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
