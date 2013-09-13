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

package com.belcan.beltime.model;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import com.belcan.beltime.test.EasyMockJUnit3Utils;
import com.belcan.beltime.util.Dates;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

/**
 * A fixture for testing the {@link TimeCard} class.
 */
public final class TimeCardTest
    extends TestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The time card under test in the fixture. */
    private TimeCard timeCard_;


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
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
        throws Exception
    {
        super.setUp();

        mocksControl_ = EasyMock.createControl();
        timeCard_ = new TimeCard();
    }

    /**
     * Ensures the {@link TimeCard#TimeCard} constructor initializes the state
     * of the time card to be inactive.
     */
    public void testConstructor_InitialStateIsInactive()
    {
        assertFalse( "time card is active", timeCard_.isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#getActiveActivity} method throws an exception
     * if the time card is inactive.
     */
    public void testGetActiveActivity_ThrowsExceptionIfTimeCardInactive()
    {
        try
        {
            timeCard_.getActiveActivity();
            fail( "getActiveActivity() did not throw IllegalStateException" ); //$NON-NLS-1$
        }
        catch( final IllegalStateException e )
        {
            // expected
        }
    }

    /**
     * Ensures the {@link TimeCard#getActivities} method returns a copy of the
     * activities collection.
     */
    @SuppressWarnings( "null" )
    public void testGetActivities_ReturnsCopy()
    {
        final List<Activity> activities = timeCard_.getActivities();
        final List<Activity> expectedActivities = new ArrayList<Activity>( activities );
        activities.add( Activity.start( TestChargeNumbers.CHARGE_NUMBER_1, Dates.now() ) );

        assertEquals( expectedActivities, timeCard_.getActivities() );
    }

    /**
     * Ensures the {@link TimeCard#reset} method fires the
     * {@link ITimeCardListener#onReset} event.
     */
    @SuppressWarnings( "null" )
    public void testReset_FiresOnReset()
    {
        final ITimeCardListener timeCardListener = mocksControl_.createMock( ITimeCardListener.class );
        final Capture<TimeCard> timeCardCapture = new Capture<TimeCard>();
        timeCardListener.onReset( EasyMock.capture( timeCardCapture ) );
        mocksControl_.replay();

        timeCard_.setTimeCardListener( timeCardListener );
        timeCard_.reset();

        EasyMockJUnit3Utils.verify( mocksControl_ );
        assertEquals( "expected fixture time card", timeCard_, timeCardCapture.getValue() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#reset} method removes all activities if the
     * time card is active.
     */
    @SuppressWarnings( "null" )
    public void testReset_RemovesAllActivitiesIfTimeCardActive()
    {
        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_1, Dates.now() );
        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_2, Dates.now() );

        timeCard_.reset();

        assertFalse( "time card is active", timeCard_.isActive() ); //$NON-NLS-1$
        assertEquals( 0, timeCard_.getActivities().size() );
    }

    /**
     * Ensures the {@link TimeCard#reset} method removes all activities if the
     * time card is inactive.
     */
    @SuppressWarnings( "null" )
    public void testReset_RemovesAllActivitiesIfTimeCardInactive()
    {
        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_1, Dates.now() );
        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_2, Dates.now() );
        timeCard_.stopActiveActivity( Dates.now() );

        timeCard_.reset();

        assertFalse( "time card is active", timeCard_.isActive() ); //$NON-NLS-1$
        assertEquals( 0, timeCard_.getActivities().size() );
    }

    /**
     * Ensures the {@link TimeCard#startActivity} method activates the time
     * card.
     */
    @SuppressWarnings( "null" )
    public void testStartActivity_ActivatesTimeCard()
    {
        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_1, Dates.now() );

        assertTrue( "time card is not active", timeCard_.isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#startActivity} method activates a new
     * activity if the time card is active.
     */
    @SuppressWarnings( "null" )
    public void testStartActivity_ActivatesNewActivityIfActive()
    {
        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_1, Dates.now() );

        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_2, Dates.now() );

        assertEquals( TestChargeNumbers.CHARGE_NUMBER_2, timeCard_.getActiveActivity().getChargeNumber() );
    }

    /**
     * Ensures the {@link TimeCard#startActivity} method activates a new
     * activity if the time card is inactive.
     */
    @SuppressWarnings( "null" )
    public void testStartActicity_ActivatesNewActivityIfInactive()
    {
        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_1, Dates.now() );

        assertEquals( TestChargeNumbers.CHARGE_NUMBER_1, timeCard_.getActiveActivity().getChargeNumber() );
    }

    /**
     * Ensures the {@link TimeCard#startActivity} method deactivates the active
     * activity if the time card is active.
     */
    @SuppressWarnings( "null" )
    public void testStartActivity_DeactivatesActiveActivityIfActive()
    {
        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_1, Dates.now() );

        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_2, Dates.now() );

        assertFalse( "first activity not deactivated", timeCard_.getActivities().get( 0 ).isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#startActivity} method fires the
     * {@link ITimeCardListener#onActivityStarted} event.
     */
    @SuppressWarnings( "null" )
    public void testStartActivity_FiresOnActivityStarted()
    {
        final ITimeCardListener timeCardListener = mocksControl_.createMock( ITimeCardListener.class );
        final Capture<TimeCard> timeCardCapture = new Capture<TimeCard>();
        timeCardListener.onActivityStarted( EasyMock.capture( timeCardCapture ), EasyMock.notNull( Activity.class ) );
        mocksControl_.replay();

        timeCard_.setTimeCardListener( timeCardListener );
        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_1, Dates.now() );

        EasyMockJUnit3Utils.verify( mocksControl_ );
        assertEquals( "expected fixture time card", timeCard_, timeCardCapture.getValue() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#startActivity} method fires the
     * {@link ITimeCardListener#onActivityStopped} event if there is an active
     * activity.
     */
    @SuppressWarnings( "null" )
    public void testStartActivity_FiresOnActivityStoppedIfActivityActive()
    {
        final ITimeCardListener timeCardListener = mocksControl_.createMock( ITimeCardListener.class );
        final Capture<TimeCard> timeCardCapture = new Capture<TimeCard>();
        final Capture<Activity> activityCapture = new Capture<Activity>();
        timeCardListener.onActivityStopped( EasyMock.capture( timeCardCapture ), EasyMock.capture( activityCapture ) );
        timeCardListener.onActivityStarted( EasyMock.notNull( TimeCard.class ), EasyMock.notNull( Activity.class ) );
        mocksControl_.replay();

        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_1, Dates.now() );
        final Activity activity = timeCard_.getActiveActivity();
        timeCard_.setTimeCardListener( timeCardListener );
        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_2, Dates.now() );

        EasyMockJUnit3Utils.verify( mocksControl_ );
        assertEquals( "expected fixture time card", timeCard_, timeCardCapture.getValue() ); //$NON-NLS-1$
        assertEquals( "expected previously active activity", activity, activityCapture.getValue() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#startActivity} method sets the stop time of
     * the active activity equal to the start time of the new activity if the
     * time card is active.
     */
    @SuppressWarnings( "null" )
    public void testStartActivity_SetsStopTimeOfActiveActivityEqualToStartTimeOfNewActivityIfActive()
    {
        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_1, Dates.now() );

        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_2, Dates.now() );

        final List<Activity> activities = timeCard_.getActivities();
        final Activity previousActivity = activities.get( 0 );
        final Activity activeActivity = activities.get( 1 );
        assertEquals( "stop time of previous activity should be equal to start time of active activity", previousActivity.getStopTime(), activeActivity.getStartTime() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#stopActiveActivity} method deactivates the
     * time card if it is active.
     */
    @SuppressWarnings( "null" )
    public void testStopActiveActivity_DeactivatesTimeCardIfActive()
    {
        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_1, Dates.now() );

        timeCard_.stopActiveActivity( Dates.now() );

        assertFalse( "time card is active", timeCard_.isActive() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#stopActiveActivity} method fires the
     * {@link ITimeCardListener#onActivityStopped} event.
     */
    @SuppressWarnings( "null" )
    public void testStopActiveActivity_FiresOnActivityStopped()
    {
        final ITimeCardListener timeCardListener = mocksControl_.createMock( ITimeCardListener.class );
        final Capture<TimeCard> timeCardCapture = new Capture<TimeCard>();
        final Capture<Activity> activityCapture = new Capture<Activity>();
        timeCardListener.onActivityStopped( EasyMock.capture( timeCardCapture ), EasyMock.capture( activityCapture ) );
        mocksControl_.replay();

        timeCard_.startActivity( TestChargeNumbers.CHARGE_NUMBER_1, Dates.now() );
        final Activity activity = timeCard_.getActiveActivity();
        timeCard_.setTimeCardListener( timeCardListener );
        timeCard_.stopActiveActivity( Dates.now() );

        EasyMockJUnit3Utils.verify( mocksControl_ );
        assertEquals( "expected fixture time card", timeCard_, timeCardCapture.getValue() ); //$NON-NLS-1$
        assertEquals( "expected previously active activity", activity, activityCapture.getValue() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link TimeCard#stopActiveActivity} method throws an
     * exception if the time card is inactive.
     */
    public void testStopActiveActivity_ThrowsExceptionIfTimeCardInactive()
    {
        try
        {
            timeCard_.stopActiveActivity( Dates.now() );
            fail( "stopActiveActivity() did not throw IllegalStateException" ); //$NON-NLS-1$
        }
        catch( final IllegalStateException e )
        {
            // expected
        }
    }
}
