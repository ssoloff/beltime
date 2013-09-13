/*
 * DisplayUtilsTest.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 8, 2013 at 10:29:54 PM.
 */

package com.belcan.beltime.view;

import java.util.Date;
import android.test.AndroidTestCase;
import com.belcan.beltime.R;
import com.belcan.beltime.model.Activity;
import com.belcan.beltime.model.TestChargeNumbers;
import com.belcan.beltime.util.NullAnalysis;

/**
 * A fixture for testing the {@link DisplayUtils} class.
 */
public final class DisplayUtilsTest
    extends AndroidTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The number of milliseconds per hour. */
    private static final long MILLISECONDS_PER_HOUR = 60L * 60L * 1000L;

    /** The number of milliseconds per decihour. */
    private static final long MILLISECONDS_PER_DECIHOUR = MILLISECONDS_PER_HOUR / 10L;

    /** The activity start time for use in the fixture. */
    private static final Date START_TIME = new Date( 0L );

    /** The activity stop time for use in the fixture. */
    private static final Date STOP_TIME = new Date( 86400000L );

    /** The display utilities under test in the fixture. */
    private DisplayUtils displayUtils_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DisplayUtilsTest} class.
     */
    public DisplayUtilsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see android.test.AndroidTestCase#setUp()
     */
    @Override
    protected void setUp()
        throws Exception
    {
        super.setUp();

        displayUtils_ = new DisplayUtils( NullAnalysis.nonNull( getContext() ) );
    }

    /**
     * Ensures the {@link DisplayUtils#formatChargeNumber} method returns the
     * expected value.
     */
    @SuppressWarnings( "null" )
    public void testFormatChargeNumber()
    {
        final Activity activity = Activity.start( TestChargeNumbers.CHARGE_NUMBER_1, START_TIME );

        assertEquals( TestChargeNumbers.CHARGE_NUMBER_1.toString(), displayUtils_.formatChargeNumber( activity ) );
    }

    /**
     * Ensures the {@link DisplayUtils#formatDuration} method returns the
     * expected value when the activity is active.
     */
    @SuppressWarnings( "null" )
    public void testFormatDuration_WhenActivityActive()
    {
        final Activity activity = Activity.start( TestChargeNumbers.CHARGE_NUMBER_1, START_TIME );

        assertEquals( getContext().getString( R.string.displayUtils_duration_active ), displayUtils_.formatDuration( activity ) );
    }

    /**
     * Ensures the {@link DisplayUtils#formatDuration} method returns the
     * expected value when the activity is inactive and the activity duration
     * should not be rounded.
     */
    @SuppressWarnings( "null" )
    public void testFormatDuration_WhenActivityInactiveAndDurationShouldNotRound()
    {
        final Activity activity = Activity.start( TestChargeNumbers.CHARGE_NUMBER_1, START_TIME );
        activity.stop( new Date( START_TIME.getTime() + MILLISECONDS_PER_HOUR ) );

        assertEquals( "1.0", displayUtils_.formatDuration( activity ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link DisplayUtils#formatDuration} method returns the
     * expected value when the activity is inactive and the activity duration
     * should be rounded down.
     */
    @SuppressWarnings( "null" )
    public void testFormatDuration_WhenActivityInactiveAndDurationShouldRoundDown()
    {
        final Activity activity = Activity.start( TestChargeNumbers.CHARGE_NUMBER_1, START_TIME );
        activity.stop( new Date( START_TIME.getTime() + MILLISECONDS_PER_HOUR - MILLISECONDS_PER_DECIHOUR ) );

        assertEquals( "0.9", displayUtils_.formatDuration( activity ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link DisplayUtils#formatDuration} method returns the
     * expected value when the activity is inactive and the activity duration
     * should be rounded up.
     */
    @SuppressWarnings( "null" )
    public void testFormatDuration_WhenActivityInactiveAndDurationShouldRoundUp()
    {
        final Activity activity = Activity.start( TestChargeNumbers.CHARGE_NUMBER_1, START_TIME );
        activity.stop( new Date( START_TIME.getTime() + MILLISECONDS_PER_HOUR + MILLISECONDS_PER_DECIHOUR ) );

        assertEquals( "1.1", displayUtils_.formatDuration( activity ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link DisplayUtils#formatDuration} method returns the
     * expected value when the activity is inactive and the activity duration is
     * zero.
     */
    @SuppressWarnings( "null" )
    public void testFormatDuration_WhenActivityInactiveAndDurationZero()
    {
        final Activity activity = Activity.start( TestChargeNumbers.CHARGE_NUMBER_1, START_TIME );
        activity.stop( START_TIME );

        assertEquals( "0.0", displayUtils_.formatDuration( activity ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link DisplayUtils#formatStartTime} method returns the
     * expected value when the activity is active.
     */
    @SuppressWarnings( "null" )
    public void testFormatStartTime_WhenActivityActive()
    {
        final Activity activity = Activity.start( TestChargeNumbers.CHARGE_NUMBER_1, START_TIME );

        assertEquals( START_TIME.toString(), displayUtils_.formatStartTime( activity ) );
    }

    /**
     * Ensures the {@link DisplayUtils#formatStartTime} method returns the
     * expected value when the activity is inactive.
     */
    @SuppressWarnings( "null" )
    public void testFormatStartTime_WhenActivityInactive()
    {
        final Activity activity = Activity.start( TestChargeNumbers.CHARGE_NUMBER_1, START_TIME );
        activity.stop( STOP_TIME );

        assertEquals( START_TIME.toString(), displayUtils_.formatStartTime( activity ) );
    }

    /**
     * Ensures the {@link DisplayUtils#formatStopTime} method returns the
     * expected value when the activity is active.
     */
    @SuppressWarnings( "null" )
    public void testFormatStopTime_WhenActivityActive()
    {
        final Activity activity = Activity.start( TestChargeNumbers.CHARGE_NUMBER_1, START_TIME );

        assertEquals( getContext().getString( R.string.displayUtils_stopTime_active ), displayUtils_.formatStopTime( activity ) );
    }

    /**
     * Ensures the {@link DisplayUtils#formatStopTime} method returns the
     * expected value when the activity is inactive.
     */
    @SuppressWarnings( "null" )
    public void testFormatStopTime_WhenActivityInactive()
    {
        final Activity activity = Activity.start( TestChargeNumbers.CHARGE_NUMBER_1, START_TIME );
        activity.stop( STOP_TIME );

        assertEquals( STOP_TIME.toString(), displayUtils_.formatStopTime( activity ) );
    }
}
