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
import android.view.KeyEvent;
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

    /** The first charge number for use in the fixture. */
    private static final ChargeNumber CHARGE_NUMBER_1 = ChargeNumber.fromString( "12345678.1234" ); //$NON-NLS-1$

    /** The second charge number for use in the fixture. */
    private static final ChargeNumber CHARGE_NUMBER_2 = ChargeNumber.fromString( "87654321.4321" ); //$NON-NLS-1$

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

    /**
     * Clicks the start job button.
     */
    private void clickStartJob()
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
    }

    /**
     * Clicks the start job button and cancels input of the charge number.
     */
    private void clickStartJobAndCancelChargeNumberInput()
    {
        clickStartJob();
        // TODO: clicking Cancel button; replace with Robotium
        sendKeys( KeyEvent.KEYCODE_TAB, KeyEvent.KEYCODE_ENTER );
    }

    /**
     * Clicks the start job button and inputs a default charge number.
     */
    @SuppressWarnings( "null" )
    private void clickStartJobAndInputChargeNumber()
    {
        clickStartJobAndInputChargeNumber( CHARGE_NUMBER_1 );
    }

    /**
     * Clicks the start job button and inputs the specified charge number.
     * 
     * @param chargeNumber
     *        The charge number; must not be {@code null}.
     */
    private void clickStartJobAndInputChargeNumber(
        final ChargeNumber chargeNumber )
    {
        clickStartJob();
        sendKeys( getChargeNumberKeys( chargeNumber ) );
        // TODO: clicking OK button; replace with Robotium
        sendKeys( KeyEvent.KEYCODE_TAB, KeyEvent.KEYCODE_TAB, KeyEvent.KEYCODE_ENTER );
    }

    /**
     * Clicks the stop job button.
     */
    private void clickStopJob()
    {
        mainActivity_.runOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                stopJobButton_.performClick();
            }
        } );

        instrumentation_.waitForIdleSync();
    }

    /**
     * Gets the sequence of key presses required to enter the specified charge
     * number.
     * 
     * @param chargeNumber
     *        The charge number; must not be {@code null}.
     * 
     * @return The sequence of key presses required to enter the specified
     *         charge number; never {@code null}.
     */
    private int[] getChargeNumberKeys(
        final ChargeNumber chargeNumber )
    {
        final String chargeNumberAsString = chargeNumber.toString();
        final int[] keys = new int[ chargeNumberAsString.length() ];
        for( int index = 0; index < keys.length; ++index )
        {
            final char ch = chargeNumberAsString.charAt( index );
            if( (ch >= '0') && (ch <= '9') )
            {
                keys[ index ] = KeyEvent.KEYCODE_0 + (ch - '0');
            }
            else if( ch == '.' )
            {
                keys[ index ] = KeyEvent.KEYCODE_PERIOD;
            }
            else
            {
                throw new AssertionError( "unsupported key code" ); //$NON-NLS-1$
            }
        }

        return keys;
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
        clickStartJobAndInputChargeNumber();

        assertTrue( "start job button is disabled", startJobButton_.isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicks the start job button does not start a new job if the
     * charge number input is cancelled.
     */
    public void testClickStartJobButton_DoesNotStartNewJobIfChargeNumberInputCancelled()
    {
        clickStartJobAndCancelChargeNumberInput();

        assertFalse( "time card is active", timeCard_.isActive() ); //$NON-NLS-1$
        assertEquals( "expected 0 jobs in time card", 0, timeCard_.getJobs().size() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button enables the stop job button.
     */
    public void testClickStartJobButton_EnablesStopJobButton()
    {
        clickStartJobAndInputChargeNumber();

        assertTrue( "stop job button is disabled", stopJobButton_.isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button starts a new job when the time card
     * is active.
     */
    @SuppressWarnings( "null" )
    public void testClickStartJobButton_StartsNewJobWhenTimeCardActive()
    {
        clickStartJobAndInputChargeNumber( CHARGE_NUMBER_1 );
        clickStartJobAndInputChargeNumber( CHARGE_NUMBER_2 );

        assertTrue( "time card is inactive", timeCard_.isActive() ); //$NON-NLS-1$
        assertEquals( "expected 2 jobs in time card", 2, timeCard_.getJobs().size() ); //$NON-NLS-1$
        assertEquals( "unexpected charge number", CHARGE_NUMBER_2, timeCard_.getJobs().get( 1 ).getChargeNumber() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button starts a new job when the time card
     * is inactive.
     */
    public void testClickStartJobButton_StartsNewJobWhenTimeCardInactive()
    {
        clickStartJobAndInputChargeNumber();

        assertTrue( "time card is inactive", timeCard_.isActive() ); //$NON-NLS-1$
        assertEquals( "expected 1 job in time card", 1, timeCard_.getJobs().size() ); //$NON-NLS-1$
        assertEquals( "unexpected charge number", CHARGE_NUMBER_1, timeCard_.getJobs().get( 0 ).getChargeNumber() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button does not disable the start job
     * button.
     */
    public void testClickStopJobButton_DoesNotDisableStartJobButton()
    {
        clickStartJobAndInputChargeNumber();
        clickStopJob();

        assertTrue( "start job button is disabled", startJobButton_.isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button disables the stop job button.
     */
    public void testClickStopJobButton_DisablesStopJobButton()
    {
        clickStartJobAndInputChargeNumber();
        clickStopJob();

        assertFalse( "stop job button is enabled", stopJobButton_.isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button stops the active job.
     */
    public void testClickStopJobButton_StopsActiveJob()
    {
        clickStartJobAndInputChargeNumber();
        clickStopJob();

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
