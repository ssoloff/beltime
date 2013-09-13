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
import com.belcan.beltime.model.TestChargeNumbers;
import com.belcan.beltime.util.NullAnalysis;
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
     * Clicks the start activity button.
     */
    private void clickStartActivity()
    {
        solo_.clickOnView( getStartActivityButton() );
        assertTrue( "expected charge number dialog to be opened but was not opened", solo_.waitForDialogToOpen( 5000L ) ); //$NON-NLS-1$
    }

    /**
     * Clicks the start activity button and cancels input of the charge number.
     */
    private void clickStartActivityAndCancelChargeNumberInput()
    {
        clickStartActivity();
        solo_.clickOnView( solo_.getView( android.R.id.button2 ) );
        assertTrue( "the charge number dialog was not closed", solo_.waitForDialogToClose( 5000L ) ); //$NON-NLS-1$
    }

    /**
     * Clicks the start activity button and inputs a default charge number.
     */
    @SuppressWarnings( "null" )
    private void clickStartActivityAndInputChargeNumber()
    {
        clickStartActivityAndInputChargeNumber( TestChargeNumbers.CHARGE_NUMBER_1 );
    }

    /**
     * Clicks the start activity button and inputs the specified charge number.
     * 
     * @param chargeNumber
     *        The charge number.
     */
    private void clickStartActivityAndInputChargeNumber(
        final ChargeNumber chargeNumber )
    {
        clickStartActivity();
        solo_.enterText( 0, chargeNumber.toString() );
        solo_.clickOnView( solo_.getView( android.R.id.button1 ) );
        assertTrue( "the charge number dialog was not closed", solo_.waitForDialogToClose( 5000L ) ); //$NON-NLS-1$
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
     * Clicks the stop activity button.
     */
    private void clickStopActivity()
    {
        solo_.clickOnView( getStopActivityButton() );
        getInstrumentation().waitForIdleSync();
    }

    /**
     * Gets the active activity charge number text view.
     * 
     * @return The active activity charge number text view.
     */
    private TextView getActiveActivityChargeNumberTextView()
    {
        return NullAnalysis.nonNull( (TextView)solo_.getView( R.id.activeActivityChargeNumberTextView ) );
    }

    /**
     * Gets the active activity start time text view.
     * 
     * @return The active activity start time text view.
     */
    private TextView getActiveActivityStartTimeTextView()
    {
        return NullAnalysis.nonNull( (TextView)solo_.getView( R.id.activeActivityStartTimeTextView ) );
    }

    /**
     * Gets the start activity button.
     * 
     * @return The start activity button.
     */
    private View getStartActivityButton()
    {
        return NullAnalysis.nonNull( solo_.getView( R.id.startActivityButton ) );
    }

    /**
     * Gets the start time card activity button.
     * 
     * @return The start time card activity button.
     */
    private View getStartTimeCardActivityButton()
    {
        return NullAnalysis.nonNull( solo_.getView( R.id.startTimeCardActivityButton ) );
    }

    /**
     * Gets the stop activity button.
     * 
     * @return The stop activity button.
     */
    private View getStopActivityButton()
    {
        return NullAnalysis.nonNull( solo_.getView( R.id.stopActivityButton ) );
    }

    /**
     * Gets the time card status text view.
     * 
     * @return The time card status text view.
     */
    private TextView getTimeCardStatusTextView()
    {
        return NullAnalysis.nonNull( (TextView)solo_.getView( R.id.timeCardStatusTextView ) );
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
     * Ensures clicking the start activity button changes the active activity
     * status text views to reflect the new activity when the time card is
     * active.
     */
    @SuppressWarnings( "null" )
    public void testClickStartActivityButton_ChangesActiveActivityStatusTextViewsToReflectNewActivityWhenTimeCardActive()
    {
        clickStartActivityAndInputChargeNumber( TestChargeNumbers.CHARGE_NUMBER_1 );
        clickStartActivityAndInputChargeNumber( TestChargeNumbers.CHARGE_NUMBER_2 );

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertEquals( "active activity charge number text view text", getTimeCard().getActiveActivity().getChargeNumber().toString(), getActiveActivityChargeNumberTextView().getText() ); //$NON-NLS-1$
                assertEquals( "active activity start time text view text", getTimeCard().getActiveActivity().getStartTime().toString(), getActiveActivityStartTimeTextView().getText() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the start activity button changes the active activity
     * status text views to reflect the new activity when the time card is
     * inactive.
     */
    public void testClickStartActivityButton_ChangesActiveActivityStatusTextViewsToReflectNewActivityWhenTimeCardInactive()
    {
        clickStartActivityAndInputChargeNumber();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertEquals( "active activity charge number text view text", getTimeCard().getActiveActivity().getChargeNumber().toString(), getActiveActivityChargeNumberTextView().getText() ); //$NON-NLS-1$
                assertEquals( "active activity start time text view text", getTimeCard().getActiveActivity().getStartTime().toString(), getActiveActivityStartTimeTextView().getText() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the start activity button changes the time card status
     * text view to indicate the time card is active when the time card is
     * inactive.
     */
    public void testClickStartActivityButton_ChangesTimeCardStatusTextViewToActiveWhenTimeCardInactive()
    {
        clickStartActivityAndInputChargeNumber();

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
     * Ensures clicking the start activity button does not change the time card
     * status text view when the time card is active.
     */
    @SuppressWarnings( "null" )
    public void testClickStartActivityButton_DoesNotChangeTimeCardStatusTextViewWhenTimeCardActive()
    {
        clickStartActivityAndInputChargeNumber( TestChargeNumbers.CHARGE_NUMBER_1 );
        clickStartActivityAndInputChargeNumber( TestChargeNumbers.CHARGE_NUMBER_2 );

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
     * Ensures clicking the start activity button does not disable the start
     * activity button.
     */
    public void testClickStartActivityButton_DoesNotDisableStartActivityButton()
    {
        clickStartActivityAndInputChargeNumber();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertTrue( "expected start activity button to be enabled but was disabled", getStartActivityButton().isEnabled() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicks the start activity button does not start a new activity if
     * the charge number input is cancelled.
     */
    public void testClickStartActivityButton_DoesNotStartNewActivityIfChargeNumberInputCancelled()
    {
        clickStartActivityAndCancelChargeNumberInput();

        runTestOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                assertFalse( "expected time card to be inactive but was active", getTimeCard().isActive() ); //$NON-NLS-1$
                assertEquals( "time card activity count", 0, getTimeCard().getActivities().size() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the start activity button enables the stop activity
     * button.
     */
    public void testClickStartActivityButton_EnablesStopActivityButton()
    {
        clickStartActivityAndInputChargeNumber();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertTrue( "expected stop activity button to be enabled but was disabled", getStopActivityButton().isEnabled() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the start activity button starts a new activity when the
     * time card is active.
     */
    @SuppressWarnings( "null" )
    public void testClickStartActivityButton_StartsNewActivityWhenTimeCardActive()
    {
        clickStartActivityAndInputChargeNumber( TestChargeNumbers.CHARGE_NUMBER_1 );
        clickStartActivityAndInputChargeNumber( TestChargeNumbers.CHARGE_NUMBER_2 );

        runTestOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                assertTrue( "expected time card to be active but was inactive", getTimeCard().isActive() ); //$NON-NLS-1$
                assertEquals( "time card activity count", 2, getTimeCard().getActivities().size() ); //$NON-NLS-1$
                assertEquals( "latest time card activity charge number", TestChargeNumbers.CHARGE_NUMBER_2, getTimeCard().getActivities().get( 1 ).getChargeNumber() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the start activity button starts a new activity when the
     * time card is inactive.
     */
    public void testClickStartActivityButton_StartsNewActivityWhenTimeCardInactive()
    {
        clickStartActivityAndInputChargeNumber();

        runTestOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                assertTrue( "expected time card to be active but was inactive", getTimeCard().isActive() ); //$NON-NLS-1$
                assertEquals( "time card activity count", 1, getTimeCard().getActivities().size() ); //$NON-NLS-1$
                assertEquals( "latest time card activity charge number", TestChargeNumbers.CHARGE_NUMBER_1, getTimeCard().getActivities().get( 0 ).getChargeNumber() ); //$NON-NLS-1$
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
     * Ensures clicking the stop activity button changes the time card status
     * text view to indicate the time card is inactive.
     */
    public void testClickStopActivityButton_ChangesTimeCardStatusTextViewToInactive()
    {
        clickStartActivityAndInputChargeNumber();
        clickStopActivity();

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
     * Ensures clicking the stop activity button clears the active activity
     * status text views.
     */
    public void testClickStopActivityButton_ClearsActiveActivityStatusTextViews()
    {
        clickStartActivityAndInputChargeNumber();
        clickStopActivity();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertEquals( "active activity charge number text view text", "", getActiveActivityChargeNumberTextView().getText() ); //$NON-NLS-1$ //$NON-NLS-2$
                assertEquals( "active activity start time text view text", "", getActiveActivityStartTimeTextView().getText() ); //$NON-NLS-1$ //$NON-NLS-2$
            }
        } );
    }

    /**
     * Ensures clicking the stop activity button does not disable the start
     * activity button.
     */
    public void testClickStopActivityButton_DoesNotDisableStartActivityButton()
    {
        clickStartActivityAndInputChargeNumber();
        clickStopActivity();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertTrue( "expected start activity button to be enabled but was disabled", getStartActivityButton().isEnabled() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the stop activity button disables the stop activity
     * button.
     */
    public void testClickStopActivityButton_DisablesStopActivityButton()
    {
        clickStartActivityAndInputChargeNumber();
        clickStopActivity();

        runTestOnUiThread( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertFalse( "expected stop activity button to be disabled but was enabled", getStopActivityButton().isEnabled() ); //$NON-NLS-1$
            }
        } );
    }

    /**
     * Ensures clicking the stop activity button stops the active activity.
     */
    public void testClickStopActivityButton_StopsActiveActivity()
    {
        clickStartActivityAndInputChargeNumber();
        clickStopActivity();

        runTestOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                assertFalse( "expected time card to be inactive but was active", getTimeCard().isActive() ); //$NON-NLS-1$
                assertEquals( "time card activity count", 1, getTimeCard().getActivities().size() ); //$NON-NLS-1$
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
        assertEquals( "time card activity count", 0, getTimeCard().getActivities().size() ); //$NON-NLS-1$

        assertEquals( "time card status text view text", solo_.getString( R.string.timeCardStatusTextView_text_inactive ), getTimeCardStatusTextView().getText() ); //$NON-NLS-1$
        assertEquals( "active activity charge number text view text", "", getActiveActivityChargeNumberTextView().getText() ); //$NON-NLS-1$ //$NON-NLS-2$
        assertEquals( "active activity start time text view text", "", getActiveActivityStartTimeTextView().getText() ); //$NON-NLS-1$ //$NON-NLS-2$
        assertTrue( "expected start activity button to be enabled but was disabled", getStartActivityButton().isEnabled() ); //$NON-NLS-1$
        assertFalse( "expected stop activity button to be disabled but was enabled", getStopActivityButton().isEnabled() ); //$NON-NLS-1$
        assertTrue( "expected start time card activity button to be enabled but was disabled", getStartTimeCardActivityButton().isEnabled() ); //$NON-NLS-1$
    }
}
