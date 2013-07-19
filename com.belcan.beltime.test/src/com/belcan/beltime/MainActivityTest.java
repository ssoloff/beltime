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
        startJobButton = (Button)mainActivity.findViewById( R.id.startJobButton );
        stopJobButton = (Button)mainActivity.findViewById( R.id.stopJobButton );
    }

    /**
     * Ensures clicking the start job button does not disable the start job
     * button.
     */
    public void testClickStartJobButtonDoesNotDisableStartJobButton()
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
    public void testClickStartJobButtonEnablesStopJobButton()
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
     * Ensures clicking the stop job button does not disable the start job
     * button.
     */
    public void testClickStopJobButtonDoesNotDisableStartJobButton()
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
    public void testClickStopJobButtonDisablesStopJobButton()
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
     * Ensures the activity pre-conditions are satisfied.
     */
    public void testPreConditions()
    {
        assertTrue( "start job button is disabled", startJobButton.isEnabled() ); //$NON-NLS-1$
        assertFalse( "stop job button is enabled", stopJobButton.isEnabled() ); //$NON-NLS-1$
    }
}
