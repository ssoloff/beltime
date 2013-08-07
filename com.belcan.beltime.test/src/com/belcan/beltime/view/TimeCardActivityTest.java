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
import java.util.concurrent.atomic.AtomicReference;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.belcan.beltime.R;
import com.belcan.beltime.model.ChargeNumber;
import com.belcan.beltime.model.Job;
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

    /** The first charge number for use in the fixture. */
    private static final ChargeNumber CHARGE_NUMBER_1 = ChargeNumber.fromString( "12345678.1234" ); //$NON-NLS-1$

    /** The second charge number for use in the fixture. */
    private static final ChargeNumber CHARGE_NUMBER_2 = ChargeNumber.fromString( "87654321.4321" ); //$NON-NLS-1$

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
     * Asserts that the jobs list view contains each of the specified jobs.
     * 
     * @param jobs
     *        The collection of jobs; must not be {@code null}.
     * 
     * @throws junit.framework.AssertionFailedError
     *         If the jobs list view does not contain the specified jobs.
     */
    private void assertJobsListViewContains(
        final List<Job> jobs )
    {
        final ListView jobsListView = getJobsListView();
        final int itemCount = getJobsListView().getCount();
        assertEquals( "jobs list view item count", jobs.size(), itemCount ); //$NON-NLS-1$

        for( int index = 0; index < itemCount; ++index )
        {
            final Job job = jobs.get( index );
            final View row = jobsListView.getChildAt( index );
            assertNotNull( String.format( "expected item in jobs list view at index %d", Integer.valueOf( index ) ), row ); //$NON-NLS-1$

            final TextView chargeNumberTextView = (TextView)row.findViewById( R.id.chargeNumberTextView );
            assertNotNull( "expected charge number text view", chargeNumberTextView ); //$NON-NLS-1$
            assertEquals( "charge number text view text", job.getChargeNumber().toString(), chargeNumberTextView.getText() ); //$NON-NLS-1$

            final TextView startTimeTextView = (TextView)row.findViewById( R.id.startTimeTextView );
            assertNotNull( "expected start time text view", startTimeTextView ); //$NON-NLS-1$
            final String expectedStartTimeTextViewText = job.getStartTime().toString();
            assertEquals( "start time text view text", expectedStartTimeTextViewText, startTimeTextView.getText() ); //$NON-NLS-1$

            final TextView stopTimeTextView = (TextView)row.findViewById( R.id.stopTimeTextView );
            assertNotNull( "expected stop time text view", stopTimeTextView ); //$NON-NLS-1$
            final String expectedStopTimeTextViewText = job.isActive() ? solo_.getString( R.string.timeCardActivity_jobStopTime_active ) : job.getStopTime().toString();
            assertEquals( "stop time text view text", expectedStopTimeTextViewText, stopTimeTextView.getText() ); //$NON-NLS-1$
        }
    }

    /**
     * Gets the jobs list view.
     * 
     * @return The jobs list view; never {@code null}.
     */
    @SuppressWarnings( "null" )
    private ListView getJobsListView()
    {
        return (ListView)solo_.getView( R.id.jobsListView );
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

        solo_ = new Solo( getInstrumentation(), getActivity() );

        resetTimeCard();
    }

    /**
     * Starts a new job with the specified charge number.
     * 
     * @param chargeNumber
     *        The charge number; must not be {@code null}.
     * 
     * @return The job that was started; never {@code null}.
     */
    @SuppressWarnings( "null" )
    private Job startJob(
        final ChargeNumber chargeNumber )
    {
        final AtomicReference<Job> jobRef = new AtomicReference<Job>();
        runTestOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                getTimeCard().startJob( chargeNumber );
                jobRef.set( getTimeCard().getActiveJob() );
            }
        } );
        return jobRef.get();
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
        assertEquals( "jobs list view item count", 0, getJobsListView().getCount() ); //$NON-NLS-1$
    }

    /**
     * Ensures starting a new job updates the jobs list view.
     */
    @SuppressWarnings( "null" )
    public void testStartJob_UpdatesJobsListView()
    {
        final Job job1 = startJob( CHARGE_NUMBER_1 );
        final Job job2 = startJob( CHARGE_NUMBER_2 );

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertJobsListViewContains( Arrays.asList( job1, job2 ) );
            }
        } );
    }
}
