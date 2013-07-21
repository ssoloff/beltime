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

package com.belcan.beltime;

import java.util.Date;
import android.test.AndroidTestCase;

/**
 * A fixture for testing the {@link Job} class.
 */
public final class JobTest
    extends AndroidTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

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
    protected void setUp()
        throws Exception
    {
        super.setUp();

        job_ = Job.start( ChargeNumber.fromString( "1234567.1234" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link Job#getDurationInMilliseconds} method returns the
     * expected duration.
     */
    public void testGetDurationInMilliseconds_ReturnsExpectedDuration()
    {
        job_.stop();
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
    public void testGetStopTime_ReturnsCopy()
    {
        job_.stop();
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
     * Ensures the {@link Job#start} method creates a job that is active.
     */
    public void testStart_CreatesActiveJob()
    {
        assertTrue( "job is not active", job_.isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link Job#stop} method deactivates the job.
     */
    public void testStop_DeactivatesJob()
    {
        job_.stop();

        assertFalse( "job is active", job_.isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link Job#stop} method throws an exception if the job is
     * inactive.
     */
    public void testStop_ThrowsExceptionIfJobInactive()
    {
        job_.stop();

        try
        {
            job_.stop();
            fail( "stop() did not throw IllegalStateException" ); //$NON-NLS-1$
        }
        catch( final IllegalStateException e )
        {
            // expected
        }
    }
}
