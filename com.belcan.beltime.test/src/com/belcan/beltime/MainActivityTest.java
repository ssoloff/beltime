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
    private Instrumentation instrumentation;

    /** The activity under test in the fixture. */
    private MainActivity mainActivity;

    /** The start job button. */
    private Button startJobButton;

    /** The stop job button. */
    private Button stopJobButton;

    /** The time card. */
    private TimeCard timeCard;


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

        instrumentation = getInstrumentation();
        mainActivity = getActivity();
        timeCard = mainActivity.getTimeCard();
        startJobButton = (Button)mainActivity.findViewById( R.id.startJobButton );
        stopJobButton = (Button)mainActivity.findViewById( R.id.stopJobButton );
    }

    /**
     * Ensures clicking the start job button does not disable the start job
     * button.
     */
    public void testClickStartJobButton_DoesNotDisableStartJobButton()
    {
        mainActivity.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton.performClick();
            }
        } );
        instrumentation.waitForIdleSync();

        assertTrue( "start job button is disabled", startJobButton.isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button enables the stop job button.
     */
    public void testClickStartJobButton_EnablesStopJobButton()
    {
        mainActivity.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton.performClick();
            }
        } );
        instrumentation.waitForIdleSync();

        assertTrue( "stop job button is disabled", stopJobButton.isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button starts a new job when the time card
     * is active.
     */
    public void testClickStartJobButton_StartsNewJobWhenTimeCardActive()
    {
        mainActivity.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton.performClick();
                startJobButton.performClick();
            }
        } );
        instrumentation.waitForIdleSync();

        assertTrue( "time card is inactive", timeCard.isActive() ); //$NON-NLS-1$
        assertEquals( "expected 2 jobs in time card", 2, timeCard.getJobs().size() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button starts a new job when the time card
     * is inactive.
     */
    public void testClickStartJobButton_StartsNewJobWhenTimeCardInactive()
    {
        mainActivity.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton.performClick();
            }
        } );
        instrumentation.waitForIdleSync();

        assertTrue( "time card is inactive", timeCard.isActive() ); //$NON-NLS-1$
        assertEquals( "expected 1 job in time card", 1, timeCard.getJobs().size() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button does not disable the start job
     * button.
     */
    public void testClickStopJobButton_DoesNotDisableStartJobButton()
    {
        mainActivity.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton.performClick();
                stopJobButton.performClick();
            }
        } );
        instrumentation.waitForIdleSync();

        assertTrue( "start job button is disabled", startJobButton.isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button disables the stop job button.
     */
    public void testClickStopJobButton_DisablesStopJobButton()
    {
        mainActivity.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton.performClick();
                stopJobButton.performClick();
            }
        } );
        instrumentation.waitForIdleSync();

        assertFalse( "stop job button is enabled", stopJobButton.isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button stops the active job.
     */
    public void testClickStopJobButton_StopsActiveJob()
    {
        mainActivity.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                startJobButton.performClick();
                stopJobButton.performClick();
            }
        } );
        instrumentation.waitForIdleSync();

        assertFalse( "time card is active", timeCard.isActive() ); //$NON-NLS-1$
        assertEquals( "expected 1 job in time card", 1, timeCard.getJobs().size() ); //$NON-NLS-1$
    }

    /**
     * Ensures the activity pre-conditions are satisfied.
     */
    public void testPreConditions()
    {
        assertFalse( "time card is active", timeCard.isActive() ); //$NON-NLS-1$
        assertEquals( "expected 0 jobs in time card", 0, timeCard.getJobs().size() ); //$NON-NLS-1$
        assertTrue( "start job button is disabled", startJobButton.isEnabled() ); //$NON-NLS-1$
        assertFalse( "stop job button is enabled", stopJobButton.isEnabled() ); //$NON-NLS-1$
    }
}
