/*
 * JobTest.java
 *
 * Copyright 2013 Beltime contributors and others.
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

/**
 * A fixture for testing the {@link Job} class.
 */
public final class JobTest
    extends TestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The charge number for use in the fixture. */
    private static final ChargeNumber CHARGE_NUMBER = ChargeNumber.fromString( "12345678.1234" ); //$NON-NLS-1$

    /** The start time for use in the fixture. */
    private static final Date START_TIME = new Date( 0L );

    /** The stop time for use in the fixture. */
    private static final Date STOP_TIME = new Date( 86400L );

    /** The job under test in the fixture. */
    private Job job_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code JobTest} class.
     */
    public JobTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see android.test.AndroidTestCase#setUp()
     */
    @Override
    @SuppressWarnings( "null" )
    protected void setUp()
        throws Exception
    {
        super.setUp();

        job_ = Job.start( CHARGE_NUMBER, START_TIME );
    }

    /**
     * Ensures the {@link Job#getDurationInMilliseconds} method returns the
     * expected duration.
     */
    @SuppressWarnings( "null" )
    public void testGetDurationInMilliseconds_ReturnsExpectedDuration()
    {
        job_.stop( STOP_TIME );
        final long expectedDurationInMilliseconds = job_.getStopTime().getTime() - job_.getStartTime().getTime();

        assertEquals( expectedDurationInMilliseconds, job_.getDurationInMilliseconds() );
    }

    /**
     * Ensures the {@link Job#getDurationInMilliseconds} method throws an
     * exception if the job is active.
     */
    public void testGetDurationInMilliseconds_ThrowsExceptionIfJobActive()
    {
        try
        {
            job_.getDurationInMilliseconds();
            fail( "getDurationInMilliseconds() did not throw IllegalStateException" ); //$NON-NLS-1$
        }
        catch( final IllegalStateException e )
        {
            // expected
        }
    }

    /**
     * Ensures the {@link Job#getStartTime} method returns a copy of the start
     * time.
     */
    public void testGetStartTime_ReturnsCopy()
    {
        final Date startTime = job_.getStartTime();
        final Date expectedStartTime = new Date( startTime.getTime() );
        startTime.setTime( 0L );

        assertEquals( expectedStartTime, job_.getStartTime() );
    }

    /**
     * Ensures the {@link Job#getStopTime} method returns a copy of the stop
     * time.
     */
    @SuppressWarnings( "null" )
    public void testGetStopTime_ReturnsCopy()
    {
        job_.stop( STOP_TIME );
        final Date stopTime = job_.getStopTime();
        final Date expectedStopTime = new Date( stopTime.getTime() );
        stopTime.setTime( 0L );

        assertEquals( expectedStopTime, job_.getStopTime() );
    }

    /**
     * Ensures the {@link Job#getStopTime} method throws an exception if the job
     * is active.
     */
    public void testGetStopTime_ThrowsExceptionIfJobActive()
    {
        try
        {
            job_.getStopTime();
            fail( "getStopTime() did not throw IllegalStateException" ); //$NON-NLS-1$
        }
        catch( final IllegalStateException e )
        {
            // expected
        }
    }

    /**
     * Ensures the {@link Job#start} method copies the start time.
     */
    @SuppressWarnings( "null" )
    public void testStart_CopiesStartTime()
    {
        final Date startTime = new Date( START_TIME.getTime() );

        final Job job = Job.start( CHARGE_NUMBER, startTime );
        startTime.setTime( Long.MAX_VALUE );

        assertEquals( START_TIME, job.getStartTime() );
    }

    /**
     * Ensures the {@link Job#start} method creates a job that is active.
     */
    public void testStart_CreatesActiveJob()
    {
        assertTrue( "job is not active", job_.isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link Job#stop} method copies the stop time.
     */
    public void testStop_CopiesStopTime()
    {
        final Date stopTime = new Date( STOP_TIME.getTime() );

        job_.stop( stopTime );
        stopTime.setTime( Long.MAX_VALUE );

        assertEquals( STOP_TIME, job_.getStopTime() );
    }

    /**
     * Ensures the {@link Job#stop} method deactivates the job.
     */
    @SuppressWarnings( "null" )
    public void testStop_DeactivatesJob()
    {
        job_.stop( STOP_TIME );

        assertFalse( "job is active", job_.isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link Job#stop} method throws an exception if the job is
     * inactive.
     */
    @SuppressWarnings( "null" )
    public void testStop_ThrowsExceptionIfJobInactive()
    {
        job_.stop( STOP_TIME );

        try
        {
            job_.stop( STOP_TIME );
            fail( "stop() did not throw IllegalStateException" ); //$NON-NLS-1$
        }
        catch( final IllegalStateException e )
        {
            // expected
        }
    }

    /**
     * Ensures the {@link Job#stop} method throws an exception if the stop time
     * is less than the start time.
     */
    public void testStop_ThrowsExceptionIfStopTimeLessThanStartTime()
    {
        try
        {
            job_.stop( new Date( job_.getStartTime().getTime() - 1L ) );
            fail( "stop() did not throw IllegalArgumentException" ); //$NON-NLS-1$
        }
        catch( final IllegalArgumentException e )
        {
            // expected
        }
    }
}
