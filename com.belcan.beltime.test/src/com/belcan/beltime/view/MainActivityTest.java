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

import android.test.UiThreadTest;
import android.view.View;
import android.widget.TextView;
import com.belcan.beltime.R;
import com.belcan.beltime.model.ChargeNumber;
import com.jayway.android.robotium.solo.Solo;

/**
 * A fixture for testing the {@link MainActivity} class.
 */
public final class MainActivityTest
    extends AbstractBeltimeActivityInstrumentationTestCase<MainActivity>
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
        assertTrue( "expected charge number dialog to be opened but was not opened", solo_.waitForDialogToOpen( 5000L ) ); //$NON-NLS-1$
    }

    /**
     * Clicks the start job button and cancels input of the charge number.
     */
    private void clickStartJobAndCancelChargeNumberInput()
    {
        clickStartJob();
        solo_.clickOnView( solo_.getView( android.R.id.button2 ) );
        getInstrumentation().waitForIdleSync(); // wait for charge number dialog to close
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
        getInstrumentation().waitForIdleSync(); // wait for charge number dialog to close
    }

    /**
     * Clicks the start time card activity button.
     */
    private void clickStartTimeCardActivity()
    {
        solo_.clickOnView( getStartTimeCardActivityButton() );
        assertTrue( "expected TimeCardActivity to be activated but was not activated", solo_.waitForActivity( TimeCardActivity.class, 5000 ) ); //$NON-NLS-1$
    }

    /**
     * Clicks the stop job button.
     */
    private void clickStopJob()
    {
        solo_.clickOnView( getStopJobButton() );
        getInstrumentation().waitForIdleSync();
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
     * Gets the start time card activity button.
     * 
     * @return The start time card activity button; never {@code null}.
     */
    @SuppressWarnings( "null" )
    private View getStartTimeCardActivityButton()
    {
        return solo_.getView( R.id.startTimeCardActivityButton );
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

        resetTimeCard();
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

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertEquals( "active job charge number text view text", getTimeCard().getActiveJob().getChargeNumber().toString(), getActiveJobChargeNumberTextView().getText() ); //$NON-NLS-1$
                assertEquals( "active job start time text view text", getTimeCard().getActiveJob().getStartTime().toString(), getActiveJobStartTimeTextView().getText() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the start job button changes the active job status text
     * views to reflect the new job when the time card is inactive.
     */
    public void testClickStartJobButton_ChangesActiveJobStatusTextViewsToReflectNewJobWhenTimeCardInactive()
    {
        clickStartJobAndInputChargeNumber();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertEquals( "active job charge number text view text", getTimeCard().getActiveJob().getChargeNumber().toString(), getActiveJobChargeNumberTextView().getText() ); //$NON-NLS-1$
                assertEquals( "active job start time text view text", getTimeCard().getActiveJob().getStartTime().toString(), getActiveJobStartTimeTextView().getText() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the start job button changes the time card status text
     * view to indicate the time card is active when the time card is inactive.
     */
    public void testClickStartJobButton_ChangesTimeCardStatusTextViewToActiveWhenTimeCardInactive()
    {
        clickStartJobAndInputChargeNumber();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertEquals( "time card status text view text", solo_.getString( R.string.timeCardStatusTextView_text_active ), getTimeCardStatusTextView().getText() ); //$NON-NLS-1$
            }
        } );
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

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertEquals( "time card status text view text", solo_.getString( R.string.timeCardStatusTextView_text_active ), getTimeCardStatusTextView().getText() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the start job button does not disable the start job
     * button.
     */
    public void testClickStartJobButton_DoesNotDisableStartJobButton()
    {
        clickStartJobAndInputChargeNumber();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertTrue( "expected start job button to be enabled but was disabled", getStartJobButton().isEnabled() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicks the start job button does not start a new job if the
     * charge number input is cancelled.
     */
    public void testClickStartJobButton_DoesNotStartNewJobIfChargeNumberInputCancelled()
    {
        clickStartJobAndCancelChargeNumberInput();

        runTestOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                assertFalse( "expected time card to be inactive but was active", getTimeCard().isActive() ); //$NON-NLS-1$
                assertEquals( "time card job count", 0, getTimeCard().getJobs().size() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the start job button enables the stop job button.
     */
    public void testClickStartJobButton_EnablesStopJobButton()
    {
        clickStartJobAndInputChargeNumber();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertTrue( "expected stop job button to be enabled but was disabled", getStopJobButton().isEnabled() ); //$NON-NLS-1$
            }
        } );
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

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertTrue( "expected time card to be active but was inactive", getTimeCard().isActive() ); //$NON-NLS-1$
                assertEquals( "time card job count", 2, getTimeCard().getJobs().size() ); //$NON-NLS-1$
                assertEquals( "latest time card job charge number", CHARGE_NUMBER_2, getTimeCard().getJobs().get( 1 ).getChargeNumber() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the start job button starts a new job when the time card
     * is inactive.
     */
    public void testClickStartJobButton_StartsNewJobWhenTimeCardInactive()
    {
        clickStartJobAndInputChargeNumber();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertTrue( "expected time card to be active but was inactive", getTimeCard().isActive() ); //$NON-NLS-1$
                assertEquals( "time card job count", 1, getTimeCard().getJobs().size() ); //$NON-NLS-1$
                assertEquals( "latest time card job charge number", CHARGE_NUMBER_1, getTimeCard().getJobs().get( 0 ).getChargeNumber() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the start time card activity button starts the time card
     * activity.
     */
    public void testClickStartTimeCardActivityButton_StartsTimeCardActivity()
    {
        clickStartTimeCardActivity();

        solo_.assertCurrentActivity( "active activity", TimeCardActivity.class ); //$NON-NLS-1$
    }

    /**
     * Ensures clicking the stop job button changes the time card status text
     * view to indicate the time card is inactive.
     */
    public void testClickStopJobButton_ChangesTimeCardStatusTextViewToInactive()
    {
        clickStartJobAndInputChargeNumber();
        clickStopJob();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertEquals( "time card status text view text", solo_.getString( R.string.timeCardStatusTextView_text_inactive ), getTimeCardStatusTextView().getText() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the stop job button clears the active job status text
     * views.
     */
    public void testClickStopJobButton_ClearsActiveJobStatusTextViews()
    {
        clickStartJobAndInputChargeNumber();
        clickStopJob();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertEquals( "active job charge number text view text", "", getActiveJobChargeNumberTextView().getText() ); //$NON-NLS-1$ //$NON-NLS-2$
                assertEquals( "active job start time text view text", "", getActiveJobStartTimeTextView().getText() ); //$NON-NLS-1$ //$NON-NLS-2$
            }
        } );
    }

    /**
     * Ensures clicking the stop job button does not disable the start job
     * button.
     */
    public void testClickStopJobButton_DoesNotDisableStartJobButton()
    {
        clickStartJobAndInputChargeNumber();
        clickStopJob();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertTrue( "expected start job button to be enabled but was disabled", getStartJobButton().isEnabled() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the stop job button disables the stop job button.
     */
    public void testClickStopJobButton_DisablesStopJobButton()
    {
        clickStartJobAndInputChargeNumber();
        clickStopJob();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertFalse( "expected stop job button to be disabled but was enabled", getStopJobButton().isEnabled() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the stop job button stops the active job.
     */
    public void testClickStopJobButton_StopsActiveJob()
    {
        clickStartJobAndInputChargeNumber();
        clickStopJob();

        runTestOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                assertFalse( "expected time card to be inactive but was active", getTimeCard().isActive() ); //$NON-NLS-1$
                assertEquals( "time card job count", 1, getTimeCard().getJobs().size() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures the activity pre-conditions are satisfied.
     */
    @UiThreadTest
    public void testPreConditions()
    {
        assertFalse( "expected time card to be inactive but was active", getTimeCard().isActive() ); //$NON-NLS-1$
        assertEquals( "time card job count", 0, getTimeCard().getJobs().size() ); //$NON-NLS-1$

        assertEquals( "time card status text view text", solo_.getString( R.string.timeCardStatusTextView_text_inactive ), getTimeCardStatusTextView().getText() ); //$NON-NLS-1$
        assertEquals( "active job charge number text view text", "", getActiveJobChargeNumberTextView().getText() ); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals( "active job start time text view text", "", getActiveJobStartTimeTextView().getText() ); //$NON-NLS-1$ //$NON-NLS-2$
        assertTrue( "expected start job button to be enabled but was disabled", getStartJobButton().isEnabled() ); //$NON-NLS-1$
        assertFalse( "expected stop job button to be disabled but was enabled", getStopJobButton().isEnabled() ); //$NON-NLS-1$
        assertTrue( "expected start time card activity button to be enabled but was disabled", getStartTimeCardActivityButton().isEnabled() ); //$NON-NLS-1$
    }
}
