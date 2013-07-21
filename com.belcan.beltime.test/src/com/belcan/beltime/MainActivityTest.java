/*
 * MainActivityTest.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 18, 2013 at 8:52:20 PM.
 */

package com.belcan.beltime;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

/**
 * A fixture for testing the {@link MainActivity} class.
 */
public final class MainActivityTest
    extends ActivityInstrumentationTestCase2<MainActivity>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The instrumentation for use in the fixture. */
    private Instrumentation instrumentation_;

    /** The activity under test in the fixture. */
    private MainActivity mainActivity_;

    /** The start job button. */
    private Button startJobButton_;

    /** The stop job button. */
    private Button stopJobButton_;

    /** The time card. */
    private TimeCard timeCard_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainActivityTest} class.
     */
    public MainActivityTest()
    {
        super( MainActivity.class );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see android.test.ActivityInstrumentationTestCase2#setUp()
     */
    @Override
    protected void setUp()
        throws Exception
    {
        super.setUp();

        setActivityInitialTouchMode( false );

        instrumentation_ = getInstrumentation();
        mainActivity_ = getActivity();
        timeCard_ = mainActivity_.getTimeCard();
        startJobButton_ = (Button)mainActivity_.findViewById( R.id.startJobButton );
        stopJobButton_ = (Button)mainActivity_.findViewById( R.id.stopJobButton );
    }

    /**
     * Ensures clicking the start job button does not disable the start job
     * button.
     */
    public void testClickStartJobButton_DoesNotDisableStartJobButton()
    {
        mainActivity_.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton_.performClick();
            }
        } );
        instrumentation_.waitForIdleSync();

        assertTrue( "start job button is disabled", startJobButton_.isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button enables the stop job button.
     */
    public void testClickStartJobButton_EnablesStopJobButton()
    {
        mainActivity_.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton_.performClick();
            }
        } );
        instrumentation_.waitForIdleSync();

        assertTrue( "stop job button is disabled", stopJobButton_.isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button starts a new job when the time card
     * is active.
     */
    public void testClickStartJobButton_StartsNewJobWhenTimeCardActive()
    {
        mainActivity_.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton_.performClick();
                startJobButton_.performClick();
            }
        } );
        instrumentation_.waitForIdleSync();

        assertTrue( "time card is inactive", timeCard_.isActive() ); //$NON-NLS-1$
        assertEquals( "expected 2 jobs in time card", 2, timeCard_.getJobs().size() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button starts a new job when the time card
     * is inactive.
     */
    public void testClickStartJobButton_StartsNewJobWhenTimeCardInactive()
    {
        mainActivity_.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton_.performClick();
            }
        } );
        instrumentation_.waitForIdleSync();

        assertTrue( "time card is inactive", timeCard_.isActive() ); //$NON-NLS-1$
        assertEquals( "expected 1 job in time card", 1, timeCard_.getJobs().size() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button does not disable the start job
     * button.
     */
    public void testClickStopJobButton_DoesNotDisableStartJobButton()
    {
        mainActivity_.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton_.performClick();
                stopJobButton_.performClick();
            }
        } );
        instrumentation_.waitForIdleSync();

        assertTrue( "start job button is disabled", startJobButton_.isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button disables the stop job button.
     */
    public void testClickStopJobButton_DisablesStopJobButton()
    {
        mainActivity_.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton_.performClick();
                stopJobButton_.performClick();
            }
        } );
        instrumentation_.waitForIdleSync();

        assertFalse( "stop job button is enabled", stopJobButton_.isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button stops the active job.
     */
    public void testClickStopJobButton_StopsActiveJob()
    {
        mainActivity_.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton_.performClick();
                stopJobButton_.performClick();
            }
        } );
        instrumentation_.waitForIdleSync();

        assertFalse( "time card is active", timeCard_.isActive() ); //$NON-NLS-1$
        assertEquals( "expected 1 job in time card", 1, timeCard_.getJobs().size() ); //$NON-NLS-1$
    }

    /**
     * Ensures the activity pre-conditions are satisfied.
     */
    public void testPreConditions()
    {
        assertFalse( "time card is active", timeCard_.isActive() ); //$NON-NLS-1$
        assertEquals( "expected 0 jobs in time card", 0, timeCard_.getJobs().size() ); //$NON-NLS-1$
        assertTrue( "start job button is disabled", startJobButton_.isEnabled() ); //$NON-NLS-1$
        assertFalse( "stop job button is enabled", stopJobButton_.isEnabled() ); //$NON-NLS-1$
    }
}
