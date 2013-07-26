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

package com.belcan.beltime.view;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;
import com.belcan.beltime.R;
import com.belcan.beltime.model.ChargeNumber;
import com.belcan.beltime.model.TimeCard;
import com.jayway.android.robotium.solo.Solo;

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

    /** The Robotium manager. */
    private Solo solo_;


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
        solo_.clickOnView( getStartJobButton() );
    }

    /**
     * Clicks the start job button and cancels input of the charge number.
     */
    private void clickStartJobAndCancelChargeNumberInput()
    {
        clickStartJob();
        solo_.clickOnView( solo_.getView( android.R.id.button2 ) );
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
        solo_.enterText( 0, chargeNumber.toString() );
        solo_.clickOnView( solo_.getView( android.R.id.button1 ) );
    }

    /**
     * Clicks the stop job button.
     */
    private void clickStopJob()
    {
        solo_.clickOnView( getStopJobButton() );
    }

    /**
     * Gets the active job charge number text view.
     * 
     * @return The active job charge number text view; never {@code null}.
     */
    @SuppressWarnings( "null" )
    private TextView getActiveJobChargeNumberTextView()
    {
        return (TextView)solo_.getView( R.id.activeJobChargeNumberTextView );
    }

    /**
     * Gets the active job start time text view.
     * 
     * @return The active job start time text view; never {@code null}.
     */
    @SuppressWarnings( "null" )
    private TextView getActiveJobStartTimeTextView()
    {
        return (TextView)solo_.getView( R.id.activeJobStartTimeTextView );
    }

    /**
     * Gets the start job button.
     * 
     * @return The start job button; never {@code null}.
     */
    @SuppressWarnings( "null" )
    private View getStartJobButton()
    {
        return solo_.getView( R.id.startJobButton );
    }

    /**
     * Gets the stop job button.
     * 
     * @return The stop job button; never {@code null}.
     */
    @SuppressWarnings( "null" )
    private View getStopJobButton()
    {
        return solo_.getView( R.id.stopJobButton );
    }

    /**
     * Gets the time card.
     * 
     * @return The time card; never {@code null}.
     */
    private TimeCard getTimeCard()
    {
        return getActivity().getTimeCard();
    }

    /**
     * Gets the time card status text view.
     * 
     * @return The time card status text view; never {@code null}.
     */
    @SuppressWarnings( "null" )
    private TextView getTimeCardStatusTextView()
    {
        return (TextView)solo_.getView( R.id.timeCardStatusTextView );
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

        solo_ = new Solo( getInstrumentation(), getActivity() );
    }

    /*
     * @see android.test.ActivityInstrumentationTestCase2#tearDown()
     */
    @Override
    protected void tearDown()
        throws Exception
    {
        solo_.finishOpenedActivities();

        super.tearDown();
    }

    /**
     * Ensures clicking the start job button changes the active job status text
     * views to reflect the new job when the time card is active.
     */
    @SuppressWarnings( "null" )
    public void testClickStartJobButton_ChangesActiveJobStatusTextViewsToReflectNewJobWhenTimeCardActive()
    {
        clickStartJobAndInputChargeNumber( CHARGE_NUMBER_1 );
        clickStartJobAndInputChargeNumber( CHARGE_NUMBER_2 );

        assertEquals( "active job charge number text view contains wrong charge number", getTimeCard().getActiveJob().getChargeNumber().toString(), getActiveJobChargeNumberTextView().getText() ); //$NON-NLS-1$
        assertEquals( "active job start time text view contains wrong start time", getTimeCard().getActiveJob().getStartTime().toString(), getActiveJobStartTimeTextView().getText() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button changes the active job status text
     * views to reflect the new job when the time card is inactive.
     */
    public void testClickStartJobButton_ChangesActiveJobStatusTextViewsToReflectNewJobWhenTimeCardInactive()
    {
        clickStartJobAndInputChargeNumber();

        assertEquals( "active job charge number text view contains wrong charge number", getTimeCard().getActiveJob().getChargeNumber().toString(), getActiveJobChargeNumberTextView().getText() ); //$NON-NLS-1$
        assertEquals( "active job start time text view contains wrong start time", getTimeCard().getActiveJob().getStartTime().toString(), getActiveJobStartTimeTextView().getText() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button changes the time card status text
     * view to indicate the time card is active when the time card is inactive.
     */
    public void testClickStartJobButton_ChangesTimeCardStatusTextViewToActiveWhenTimeCardInactive()
    {
        clickStartJobAndInputChargeNumber();

        assertEquals( "time card status text view indicates time card is inactive", solo_.getString( R.string.timeCardStatusTextView_text_active ), getTimeCardStatusTextView().getText() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button does not change the time card
     * status text view when the time card is active.
     */
    @SuppressWarnings( "null" )
    public void testClickStartJobButton_DoesNotChangeTimeCardStatusTextViewWhenTimeCardActive()
    {
        clickStartJobAndInputChargeNumber( CHARGE_NUMBER_1 );
        clickStartJobAndInputChargeNumber( CHARGE_NUMBER_2 );

        assertEquals( "time card status text view indicates time card is inactive", solo_.getString( R.string.timeCardStatusTextView_text_active ), getTimeCardStatusTextView().getText() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button does not disable the start job
     * button.
     */
    public void testClickStartJobButton_DoesNotDisableStartJobButton()
    {
        clickStartJobAndInputChargeNumber();

        assertTrue( "start job button is disabled", getStartJobButton().isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicks the start job button does not start a new job if the
     * charge number input is cancelled.
     */
    public void testClickStartJobButton_DoesNotStartNewJobIfChargeNumberInputCancelled()
    {
        clickStartJobAndCancelChargeNumberInput();

        assertFalse( "time card is active", getTimeCard().isActive() ); //$NON-NLS-1$
        assertEquals( "expected 0 jobs in time card", 0, getTimeCard().getJobs().size() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button enables the stop job button.
     */
    public void testClickStartJobButton_EnablesStopJobButton()
    {
        clickStartJobAndInputChargeNumber();

        assertTrue( "stop job button is disabled", getStopJobButton().isEnabled() ); //$NON-NLS-1$
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

        assertTrue( "time card is inactive", getTimeCard().isActive() ); //$NON-NLS-1$
        assertEquals( "expected 2 jobs in time card", 2, getTimeCard().getJobs().size() ); //$NON-NLS-1$
        assertEquals( "unexpected charge number", CHARGE_NUMBER_2, getTimeCard().getJobs().get( 1 ).getChargeNumber() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the start job button starts a new job when the time card
     * is inactive.
     */
    public void testClickStartJobButton_StartsNewJobWhenTimeCardInactive()
    {
        clickStartJobAndInputChargeNumber();

        assertTrue( "time card is inactive", getTimeCard().isActive() ); //$NON-NLS-1$
        assertEquals( "expected 1 job in time card", 1, getTimeCard().getJobs().size() ); //$NON-NLS-1$
        assertEquals( "unexpected charge number", CHARGE_NUMBER_1, getTimeCard().getJobs().get( 0 ).getChargeNumber() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button changes the time card status text
     * view to indicate the time card is inactive.
     */
    public void testClickStopJobButton_ChangesTimeCardStatusTextViewToInactive()
    {
        clickStartJobAndInputChargeNumber();
        clickStopJob();

        assertEquals( "time card status text view indicates time card is active", solo_.getString( R.string.timeCardStatusTextView_text_inactive ), getTimeCardStatusTextView().getText() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button clears the active job status text
     * views.
     */
    public void testClickStopJobButton_ClearsActiveJobStatusTextViews()
    {
        clickStartJobAndInputChargeNumber();
        clickStopJob();

        assertEquals( "active job charge number text view is not empty", "", getActiveJobChargeNumberTextView().getText() ); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals( "active job start time text view is not empty", "", getActiveJobStartTimeTextView().getText() ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures clicking the stop job button does not disable the start job
     * button.
     */
    public void testClickStopJobButton_DoesNotDisableStartJobButton()
    {
        clickStartJobAndInputChargeNumber();
        clickStopJob();

        assertTrue( "start job button is disabled", getStartJobButton().isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button disables the stop job button.
     */
    public void testClickStopJobButton_DisablesStopJobButton()
    {
        clickStartJobAndInputChargeNumber();
        clickStopJob();

        assertFalse( "stop job button is enabled", getStopJobButton().isEnabled() ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button stops the active job.
     */
    public void testClickStopJobButton_StopsActiveJob()
    {
        clickStartJobAndInputChargeNumber();
        clickStopJob();

        assertFalse( "time card is active", getTimeCard().isActive() ); //$NON-NLS-1$
        assertEquals( "expected 1 job in time card", 1, getTimeCard().getJobs().size() ); //$NON-NLS-1$
    }

    /**
     * Ensures the activity pre-conditions are satisfied.
     */
    public void testPreConditions()
    {
        assertFalse( "time card is active", getTimeCard().isActive() ); //$NON-NLS-1$
        assertEquals( "expected 0 jobs in time card", 0, getTimeCard().getJobs().size() ); //$NON-NLS-1$

        assertEquals( "time card status text view indicates time card is active", solo_.getString( R.string.timeCardStatusTextView_text_inactive ), getTimeCardStatusTextView().getText() ); //$NON-NLS-1$
        assertEquals( "active job charge number text view is not empty", "", getActiveJobChargeNumberTextView().getText() ); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals( "active job start time text view is not empty", "", getActiveJobStartTimeTextView().getText() ); //$NON-NLS-1$ //$NON-NLS-2$
        assertTrue( "start job button is disabled", getStartJobButton().isEnabled() ); //$NON-NLS-1$
        assertFalse( "stop job button is enabled", getStopJobButton().isEnabled() ); //$NON-NLS-1$
    }
}
