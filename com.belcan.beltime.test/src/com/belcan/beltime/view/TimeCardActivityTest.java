/*
 * TimeCardActivityTest.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 26, 2013 at 10:02:44 PM.
 */

package com.belcan.beltime.view;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.belcan.beltime.R;
import com.belcan.beltime.model.Activity;
import com.belcan.beltime.model.ChargeNumber;
import com.belcan.beltime.model.TestChargeNumbers;
import com.belcan.beltime.util.Dates;
import com.belcan.beltime.util.NullAnalysis;
import com.jayway.android.robotium.solo.Solo;

/**
 * A fixture for testing the {@link TimeCardActivity} class.
 */
public final class TimeCardActivityTest
    extends AbstractBeltimeActivityInstrumentationTestCase<TimeCardActivity>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The display utilities for use in the fixture. */
    private DisplayUtils displayUtils_;

    /** The Robotium manager. */
    private Solo solo_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TimeCardActivityTest} class.
     */
    public TimeCardActivityTest()
    {
        super( TimeCardActivity.class );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts that the activities list view contains each of the specified
     * activities.
     * 
     * @param activities
     *        The collection of activities.
     * 
     * @throws junit.framework.AssertionFailedError
     *         If the activities list view does not contain the specified
     *         activities.
     */
    private void assertActivitiesListViewContains(
        final List<Activity> activities )
    {
        final ListView activitiesListView = getActivitiesListView();
        final int itemCount = activitiesListView.getCount();
        assertEquals( "activities list view item count", activities.size(), itemCount ); //$NON-NLS-1$

        for( int index = 0; index < itemCount; ++index )
        {
            final Activity activity = activities.get( index );
            assert activity != null;
            final View row = activitiesListView.getChildAt( index );
            assertNotNull( String.format( Locale.US, "expected item in activities list view at index %d", Integer.valueOf( index ) ), row ); //$NON-NLS-1$

            final TextView chargeNumberTextView = (TextView)row.findViewById( R.id.chargeNumberTextView );
            assertNotNull( "expected charge number text view", chargeNumberTextView ); //$NON-NLS-1$
            final String expectedChargeNumberTextViewText = displayUtils_.formatChargeNumber( activity );
            assertEquals( "charge number text view text", expectedChargeNumberTextViewText, chargeNumberTextView.getText() ); //$NON-NLS-1$

            final TextView startTimeTextView = (TextView)row.findViewById( R.id.startTimeTextView );
            assertNotNull( "expected start time text view", startTimeTextView ); //$NON-NLS-1$
            final String expectedStartTimeTextViewText = displayUtils_.formatStartTime( activity );
            assertEquals( "start time text view text", expectedStartTimeTextViewText, startTimeTextView.getText() ); //$NON-NLS-1$

            final TextView stopTimeTextView = (TextView)row.findViewById( R.id.stopTimeTextView );
            assertNotNull( "expected stop time text view", stopTimeTextView ); //$NON-NLS-1$
            final String expectedStopTimeTextViewText = displayUtils_.formatStopTime( activity );
            assertEquals( "stop time text view text", expectedStopTimeTextViewText, stopTimeTextView.getText() ); //$NON-NLS-1$

            final TextView durationTextView = (TextView)row.findViewById( R.id.durationTextView );
            assertNotNull( "expected duration text view", durationTextView ); //$NON-NLS-1$
            final String expectedDurationTextViewText = displayUtils_.formatDuration( activity );
            assertEquals( "duration text view text", expectedDurationTextViewText, durationTextView.getText() ); //$NON-NLS-1$
        }
    }

    /**
     * Gets the activities list view.
     * 
     * @return The activities list view.
     */
    private ListView getActivitiesListView()
    {
        return NullAnalysis.nonNull( (ListView)solo_.getView( R.id.activitiesListView ) );
    }

    /*
     * @see android.test.ActivityInstrumentationTestCase2#setUp()
     */
    @Override
    protected void setUp()
        throws Exception
    {
        super.setUp();

        setActivityInitialTouchMode( false );

        displayUtils_ = new DisplayUtils( NullAnalysis.nonNull( getActivity() ) );
        solo_ = new Solo( getInstrumentation(), getActivity() );

        resetTimeCard();
    }

    /**
     * Starts a new activity with the specified charge number.
     * 
     * @param chargeNumber
     *        The charge number.
     * 
     * @return The activity that was started.
     */
    private Activity startActivity(
        final ChargeNumber chargeNumber )
    {
        final AtomicReference<Activity> activityRef = new AtomicReference<Activity>();
        runTestOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                getTimeCard().startActivity( chargeNumber, Dates.now() );
                activityRef.set( getTimeCard().getActiveActivity() );
            }
        } );
        return NullAnalysis.nonNull( activityRef.get() );
    }

    /*
     * @see android.test.ActivityInstrumentationTestCase2#tearDown()
     */
    @Override
    protected void tearDown()
        throws Exception
    {
        solo_.finishOpenedActivities();

        super.tearDown();
    }

    /**
     * Ensures the activity pre-conditions are satisfied.
     */
    @UiThreadTest
    public void testPreConditions()
    {
        assertEquals( "activities list view item count", 0, getActivitiesListView().getCount() ); //$NON-NLS-1$
    }

    /**
     * Ensures starting a new activity updates the activities list view.
     */
    @SuppressWarnings( "null" )
    public void testStartActivity_UpdatesActivitiesListView()
    {
        final Activity activity1 = startActivity( TestChargeNumbers.CHARGE_NUMBER_1 );
        final Activity activity2 = startActivity( TestChargeNumbers.CHARGE_NUMBER_2 );

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertActivitiesListViewContains( Arrays.asList( activity1, activity2 ) );
            }
        } );
    }
}
