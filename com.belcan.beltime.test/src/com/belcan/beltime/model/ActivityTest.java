/*
 * ActivityTest.java
 *
 * Copyright 2014 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 12, 2013 at 7:49:24 PM.
 */

package com.belcan.beltime.model;

import java.util.Date;
import junit.framework.TestCase;
import com.belcan.beltime.util.Duration;

/**
 * A fixture for testing the {@link Activity} class.
 */
public final class ActivityTest
    extends TestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The start time for use in the fixture. */
    private static final Date START_TIME = new Date( 0L );

    /** The stop time for use in the fixture. */
    private static final Date STOP_TIME = new Date( 86400000L );

    /** The activity under test in the fixture. */
    private Activity activity_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ActivityTest} class.
     */
    public ActivityTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    @SuppressWarnings( "null" )
    protected void setUp()
        throws Exception
    {
        super.setUp();

        activity_ = Activity.start( TestChargeNumbers.CHARGE_NUMBER_1, START_TIME );
    }

    /**
     * Ensures the {@link Activity#getDuration} method returns the expected
     * duration.
     */
    @SuppressWarnings( "null" )
    public void testGetDuration_ReturnsExpectedDuration()
    {
        activity_.stop( STOP_TIME );
        final Duration expectedDuration = Duration.fromMilliseconds( STOP_TIME.getTime() - START_TIME.getTime() );

        assertEquals( expectedDuration, activity_.getDuration() );
    }

    /**
     * Ensures the {@link Activity#getDuration} method throws an exception if
     * the activity is active.
     */
    public void testGetDuration_ThrowsExceptionIfActivityActive()
    {
        try
        {
            activity_.getDuration();
            fail( "getDuration() did not throw IllegalStateException" ); //$NON-NLS-1$
        }
        catch( final IllegalStateException e )
        {
            // expected
        }
    }

    /**
     * Ensures the {@link Activity#getStartTime} method returns a copy of the
     * start time.
     */
    public void testGetStartTime_ReturnsCopy()
    {
        final Date startTime = activity_.getStartTime();
        final Date expectedStartTime = new Date( startTime.getTime() );
        startTime.setTime( startTime.getTime() + 1L );

        assertEquals( expectedStartTime, activity_.getStartTime() );
    }

    /**
     * Ensures the {@link Activity#getStopTime} method returns a copy of the
     * stop time.
     */
    @SuppressWarnings( "null" )
    public void testGetStopTime_ReturnsCopy()
    {
        activity_.stop( STOP_TIME );
        final Date stopTime = activity_.getStopTime();
        final Date expectedStopTime = new Date( stopTime.getTime() );
        stopTime.setTime( stopTime.getTime() + 1L );

        assertEquals( expectedStopTime, activity_.getStopTime() );
    }

    /**
     * Ensures the {@link Activity#getStopTime} method throws an exception if
     * the activity is active.
     */
    public void testGetStopTime_ThrowsExceptionIfActivityActive()
    {
        try
        {
            activity_.getStopTime();
            fail( "getStopTime() did not throw IllegalStateException" ); //$NON-NLS-1$
        }
        catch( final IllegalStateException e )
        {
            // expected
        }
    }

    /**
     * Ensures the {@link Activity#start} method copies the start time.
     */
    @SuppressWarnings( "null" )
    public void testStart_CopiesStartTime()
    {
        final Date startTime = new Date( START_TIME.getTime() );

        final Activity activity = Activity.start( TestChargeNumbers.CHARGE_NUMBER_1, startTime );
        startTime.setTime( Long.MAX_VALUE );

        assertEquals( START_TIME, activity.getStartTime() );
    }

    /**
     * Ensures the {@link Activity#start} method creates an activity that is
     * active.
     */
    public void testStart_CreatesActiveActivity()
    {
        assertTrue( "activity is not active", activity_.isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link Activity#stop} method copies the stop time.
     */
    public void testStop_CopiesStopTime()
    {
        final Date stopTime = new Date( STOP_TIME.getTime() );

        activity_.stop( stopTime );
        stopTime.setTime( Long.MAX_VALUE );

        assertEquals( STOP_TIME, activity_.getStopTime() );
    }

    /**
     * Ensures the {@link Activity#stop} method deactivates the activity.
     */
    @SuppressWarnings( "null" )
    public void testStop_DeactivatesActivity()
    {
        activity_.stop( STOP_TIME );

        assertFalse( "activity is active", activity_.isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link Activity#stop} method throws an exception if the
     * activity is inactive.
     */
    @SuppressWarnings( "null" )
    public void testStop_ThrowsExceptionIfActivityInactive()
    {
        activity_.stop( STOP_TIME );

        try
        {
            activity_.stop( STOP_TIME );
            fail( "stop() did not throw IllegalStateException" ); //$NON-NLS-1$
        }
        catch( final IllegalStateException e )
        {
            // expected
        }
    }

    /**
     * Ensures the {@link Activity#stop} method throws an exception if the stop
     * time is less than the start time.
     */
    public void testStop_ThrowsExceptionIfStopTimeLessThanStartTime()
    {
        try
        {
            activity_.stop( new Date( activity_.getStartTime().getTime() - 1L ) );
            fail( "stop() did not throw IllegalArgumentException" ); //$NON-NLS-1$
        }
        catch( final IllegalArgumentException e )
        {
            // expected
        }
    }
}
