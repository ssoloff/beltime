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
import com.belcan.beltime.model.Job;
import com.belcan.beltime.model.TestChargeNumbers;

/**
 * A fixture for testing the {@link DisplayUtils} class.
 */
public final class DisplayUtilsTest
    extends AndroidTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The number of milliseconds per decihour. */
    private static final long MILLISECONDS_PER_DECIHOUR = 8640000L;

    /** The number of milliseconds per hour. */
    private static final long MILLISECONDS_PER_HOUR = 86400000L;

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
    @SuppressWarnings( "null" )
    protected void setUp()
        throws Exception
    {
        super.setUp();

        displayUtils_ = new DisplayUtils( getContext() );
    }

    /**
     * Ensures the {@link DisplayUtils#formatJobDuration} method returns the
     * expected value when the job is active.
     */
    @SuppressWarnings( "null" )
    public void testFormatJobDuration_WhenJobActive()
    {
        final Job job = Job.start( TestChargeNumbers.CHARGE_NUMBER_1, new Date() );

        assertEquals( getContext().getString( R.string.displayUtils_job_duration_active ), displayUtils_.formatJobDuration( job ) );
    }

    /**
     * Ensures the {@link DisplayUtils#formatJobDuration} method returns the
     * expected value when the job is inactive and the job duration is zero.
     */
    @SuppressWarnings( "null" )
    public void testFormatJobDuration_WhenJobInactiveAndDurationZero()
    {
        final Job job = Job.start( TestChargeNumbers.CHARGE_NUMBER_1, new Date( 0L ) );
        job.stop( new Date( 0L ) );

        assertEquals( "0.0", displayUtils_.formatJobDuration( job ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link DisplayUtils#formatJobDuration} method returns the
     * expected value when the job is inactive and the job duration should not
     * be rounded.
     */
    @SuppressWarnings( "null" )
    public void testFormatJobDuration_WhenJobInactiveAndDurationShouldNotRound()
    {
        final Job job = Job.start( TestChargeNumbers.CHARGE_NUMBER_1, new Date( 0L ) );
        job.stop( new Date( MILLISECONDS_PER_HOUR ) );

        assertEquals( "1.0", displayUtils_.formatJobDuration( job ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link DisplayUtils#formatJobDuration} method returns the
     * expected value when the job is inactive and the job duration should be
     * rounded down.
     */
    @SuppressWarnings( "null" )
    public void testFormatJobDuration_WhenJobInactiveAndDurationShouldRoundDown()
    {
        final Job job = Job.start( TestChargeNumbers.CHARGE_NUMBER_1, new Date( 0L ) );
        job.stop( new Date( MILLISECONDS_PER_HOUR - MILLISECONDS_PER_DECIHOUR ) );

        assertEquals( "0.9", displayUtils_.formatJobDuration( job ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link DisplayUtils#formatJobDuration} method returns the
     * expected value when the job is inactive and the job duration should be
     * rounded up.
     */
    @SuppressWarnings( "null" )
    public void testFormatJobDuration_WhenJobInactiveAndDurationShouldRoundUp()
    {
        final Job job = Job.start( TestChargeNumbers.CHARGE_NUMBER_1, new Date( 0L ) );
        job.stop( new Date( MILLISECONDS_PER_HOUR + MILLISECONDS_PER_DECIHOUR ) );

        assertEquals( "1.1", displayUtils_.formatJobDuration( job ) ); //$NON-NLS-1$
    }
}
