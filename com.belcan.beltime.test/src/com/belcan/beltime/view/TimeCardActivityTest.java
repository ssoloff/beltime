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

import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import android.test.UiThreadTest;
import android.widget.ListView;
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
     * Asserts that the specified job is found in the jobs list view.
     * 
     * @param job
     *        The job to find; must not be {@code null}.
     * 
     * @throws junit.framework.AssertionFailedError
     *         If the specified job is not found.
     */
    private void assertJobFound(
        final Job job )
    {
        final AtomicReference<ChargeNumber> chargeNumberRef = new AtomicReference<ChargeNumber>();
        runTestOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                chargeNumberRef.set( job.getChargeNumber() );
            }
        } );

        assertTrue( String.format( "expected to find charge number '%s' in jobs list view", chargeNumberRef.get() ), solo_.searchText( Pattern.quote( chargeNumberRef.get().toString() ) ) ); //$NON-NLS-1$
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
                assertEquals( "jobs list view item count", 2, getJobsListView().getCount() ); //$NON-NLS-1$
            }
        } );
        assertJobFound( job1 );
        assertJobFound( job2 );
    }
}
