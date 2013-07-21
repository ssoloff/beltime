/*
 * TimeCardTest.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 13, 2013 at 8:09:43 PM.
 */

package com.belcan.beltime;

import java.util.ArrayList;
import java.util.List;
import android.test.AndroidTestCase;
import com.belcan.beltime.test.EasyMockJUnit3Utils;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

/**
 * A fixture for testing the {@link TimeCard} class.
 */
public final class TimeCardTest
    extends AndroidTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The first charge number for use in the fixture. */
    private static final ChargeNumber CHARGE_NUMBER_1 = ChargeNumber.fromString( "1111111.1111" ); //$NON-NLS-1$

    /** The second charge number for use in the fixture. */
    private static final ChargeNumber CHARGE_NUMBER_2 = ChargeNumber.fromString( "2222222.2222" ); //$NON-NLS-1$

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl;

    /** The time card under test in the fixture. */
    private TimeCard timeCard;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TimeCardTest} class.
     */
    public TimeCardTest()
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

        mocksControl = EasyMock.createControl();
        timeCard = new TimeCard();
    }

    /**
     * Ensures the {@link TimeCard#TimeCard} constructor initializes the state
     * of the time card to be inactive.
     */
    public void testConstructor_InitialStateIsInactive()
    {
        assertFalse( "time card is active", timeCard.isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#getActiveJob} method throws an exception if
     * the time card is inactive.
     */
    public void testGetActiveJob_ThrowsExceptionIfTimeCardInactive()
    {
        try
        {
            timeCard.getActiveJob();
            fail( "getActiveJob() did not throw IllegalStateException" ); //$NON-NLS-1$
        }
        catch( final IllegalStateException e )
        {
            // expected
        }
    }

    /**
     * Ensures the {@link TimeCard#getJobs} method returns a copy of the jobs
     * collection.
     */
    @SuppressWarnings( "null" )
    public void testGetJobs_ReturnsCopy()
    {
        final List<Job> jobs = timeCard.getJobs();
        final List<Job> expectedJobs = new ArrayList<Job>( jobs );
        jobs.add( Job.start( CHARGE_NUMBER_1 ) );

        assertEquals( expectedJobs, timeCard.getJobs() );
    }

    /**
     * Ensures the {@link TimeCard#startJob} method activates the time card.
     */
    @SuppressWarnings( "null" )
    public void testStartJob_ActivatesTimeCard()
    {
        timeCard.startJob( CHARGE_NUMBER_1 );

        assertTrue( "time card is not active", timeCard.isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#startJob} method activates a new job if the
     * time card is active.
     */
    @SuppressWarnings( "null" )
    public void testStartJob_ActivatesNewJobIfActive()
    {
        timeCard.startJob( CHARGE_NUMBER_1 );

        timeCard.startJob( CHARGE_NUMBER_2 );

        assertEquals( CHARGE_NUMBER_2, timeCard.getActiveJob().getChargeNumber() );
    }

    /**
     * Ensures the {@link TimeCard#startJob} method activates a new job if the
     * time card is inactive.
     */
    @SuppressWarnings( "null" )
    public void testStartJob_ActivatesNewJobIfInactive()
    {
        timeCard.startJob( CHARGE_NUMBER_1 );

        assertEquals( CHARGE_NUMBER_1, timeCard.getActiveJob().getChargeNumber() );
    }

    /**
     * Ensures the {@link TimeCard#startJob} method deactivates the active job
     * if the time card is active.
     */
    @SuppressWarnings( "null" )
    public void testStartJob_DeactivatesActiveJobIfActive()
    {
        timeCard.startJob( CHARGE_NUMBER_1 );

        timeCard.startJob( CHARGE_NUMBER_2 );

        assertFalse( "first job not deactivated", timeCard.getJobs().get( 0 ).isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#startJob} method fires the
     * {@link TimeCardListener#onJobStarted} event.
     */
    @SuppressWarnings( "null" )
    public void testStartJob_FiresOnJobStarted()
    {
        final TimeCardListener timeCardListener = mocksControl.createMock( TimeCardListener.class );
        final Capture<TimeCard> timeCardCapture = new Capture<TimeCard>();
        timeCardListener.onJobStarted( EasyMock.capture( timeCardCapture ), EasyMock.notNull( Job.class ) );
        mocksControl.replay();

        timeCard.setTimeCardListener( timeCardListener );
        timeCard.startJob( CHARGE_NUMBER_1 );

        EasyMockJUnit3Utils.verify( mocksControl );
        assertEquals( "expected fixture time card", timeCard, timeCardCapture.getValue() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#startJob} method fires the
     * {@link TimeCardListener#onJobStopped} event if there is an active job.
     */
    @SuppressWarnings( "null" )
    public void testStartJob_FiresOnJobStoppedIfJobActive()
    {
        final TimeCardListener timeCardListener = mocksControl.createMock( TimeCardListener.class );
        final Capture<TimeCard> timeCardCapture = new Capture<TimeCard>();
        final Capture<Job> jobCapture = new Capture<Job>();
        timeCardListener.onJobStopped( EasyMock.capture( timeCardCapture ), EasyMock.capture( jobCapture ) );
        timeCardListener.onJobStarted( EasyMock.notNull( TimeCard.class ), EasyMock.notNull( Job.class ) );
        mocksControl.replay();

        timeCard.startJob( CHARGE_NUMBER_1 );
        final Job job = timeCard.getActiveJob();
        timeCard.setTimeCardListener( timeCardListener );
        timeCard.startJob( CHARGE_NUMBER_2 );

        EasyMockJUnit3Utils.verify( mocksControl );
        assertEquals( "expected fixture time card", timeCard, timeCardCapture.getValue() ); //$NON-NLS-1$
        assertEquals( "expected previously active job", job, jobCapture.getValue() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#stopActiveJob} method deactivates the time
     * card if it is active.
     */
    @SuppressWarnings( "null" )
    public void testStopActiveJob_DeactivatesTimeCardIfActive()
    {
        timeCard.startJob( CHARGE_NUMBER_1 );

        timeCard.stopActiveJob();

        assertFalse( "time card is active", timeCard.isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#stopActiveJob} method fires the
     * {@link TimeCardListener#onJobStopped} event.
     */
    @SuppressWarnings( "null" )
    public void testStopActiveJob_FiresOnJobStopped()
    {
        final TimeCardListener timeCardListener = mocksControl.createMock( TimeCardListener.class );
        final Capture<TimeCard> timeCardCapture = new Capture<TimeCard>();
        final Capture<Job> jobCapture = new Capture<Job>();
        timeCardListener.onJobStopped( EasyMock.capture( timeCardCapture ), EasyMock.capture( jobCapture ) );
        mocksControl.replay();

        timeCard.startJob( CHARGE_NUMBER_1 );
        final Job job = timeCard.getActiveJob();
        timeCard.setTimeCardListener( timeCardListener );
        timeCard.stopActiveJob();

        EasyMockJUnit3Utils.verify( mocksControl );
        assertEquals( "expected fixture time card", timeCard, timeCardCapture.getValue() ); //$NON-NLS-1$
        assertEquals( "expected previously active job", job, jobCapture.getValue() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#stopActiveJob} method throws an exception if
     * the time card is inactive.
     */
    public void testStopActiveJob_ThrowsExceptionIfTimeCardInactive()
    {
        try
        {
            timeCard.stopActiveJob();
            fail( "stopActiveJob() did not throw IllegalStateException" ); //$NON-NLS-1$
        }
        catch( final IllegalStateException e )
        {
            // expected
        }
    }
}
