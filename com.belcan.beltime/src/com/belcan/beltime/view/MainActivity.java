/*
 * MainActivity.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 5, 2013 at 10:29:09 PM.
 */

package com.belcan.beltime.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.belcan.beltime.R;
import com.belcan.beltime.model.Activity;
import com.belcan.beltime.model.ChargeNumber;
import com.belcan.beltime.model.ITimeCardListener;
import com.belcan.beltime.model.TimeCard;
import com.belcan.beltime.util.Dates;
import com.belcan.beltime.util.NullAnalysis;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The main activity.
 */
public final class MainActivity
    extends AbstractBeltimeActivity
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The active activity charge number text view. */
    private TextView activeActivityChargeNumberTextView_;

    /** The active activity start time text view. */
    private TextView activeActivityStartTimeTextView_;

    /** The stop activity button. */
    private Button stopActivityButton_;

    /** The time card status text view. */
    private TextView timeCardStatusTextView_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainActivity} class.
     */
    public MainActivity()
    {
        activeActivityChargeNumberTextView_ = null;
        activeActivityStartTimeTextView_ = null;
        stopActivityButton_ = null;
        timeCardStatusTextView_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Called when the start activity button has been clicked.
     * 
     * @param view
     *        The start activity button.
     */
    public void onClickStartActivity(
        final View view )
    {
        final EditText chargeNumberEditText = new EditText( this );
        final AlertDialog alertDialog = new AlertDialog.Builder( this ) //
            .setNegativeButton( R.string.chargeNumberDialog_negativeButton_text, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(
                    @Nullable
                    final DialogInterface dialog,
                    final int which )
                {
                    // do nothing
                }
            } ) //
            .setPositiveButton( R.string.chargeNumberDialog_positiveButton_text, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(
                    @Nullable
                    final DialogInterface dialog,
                    final int which )
                {
                    final ChargeNumber chargeNumber = ChargeNumber.fromString( NullAnalysis.nonNull( chargeNumberEditText.getText().toString() ) );
                    getTimeCard().startActivity( chargeNumber, Dates.now() );
                }
            } ) //
            .setTitle( R.string.chargeNumberDialog_title ) //
            .setView( chargeNumberEditText ) //
            .create();
        alertDialog.show();
    }

    /**
     * Called when the stop activity button has been clicked.
     * 
     * @param view
     *        The stop activity button.
     */
    public void onClickStopActivity(
        final View view )
    {
        getTimeCard().stopActiveActivity( Dates.now() );
    }

    /*
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(
        @Nullable
        final Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_main );
        activeActivityChargeNumberTextView_ = (TextView)findViewById( R.id.activeActivityChargeNumberTextView );
        activeActivityStartTimeTextView_ = (TextView)findViewById( R.id.activeActivityStartTimeTextView );
        stopActivityButton_ = (Button)findViewById( R.id.stopActivityButton );
        timeCardStatusTextView_ = (TextView)findViewById( R.id.timeCardStatusTextView );
    }

    /*
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(
        @Nullable
        final Menu menu )
    {
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    /*
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(
        @Nullable
        final MenuItem item )
    {
        assert item != null;

        switch( item.getItemId() )
        {
            case R.id.menuItem_billingReports:
                startActivity( new Intent( this, BillingReportsActivity.class ) );
                return true;

            case R.id.menuItem_timeCard:
                startActivity( new Intent( this, TimeCardActivity.class ) );
                return true;

            default:
                return super.onOptionsItemSelected( item );
        }
    }

    /*
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause()
    {
        super.onPause();

        getTimeCard().setTimeCardListener( null );
    }

    /*
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        getTimeCard().setTimeCardListener( new TimeCardListener() );
        update();
    }

    /**
     * Updates the state of the activity based on the model.
     */
    private void update()
    {
        final boolean isTimeCardActive = getTimeCard().isActive();
        stopActivityButton_.setEnabled( isTimeCardActive );
        timeCardStatusTextView_.setText( isTimeCardActive ? R.string.timeCardStatusTextView_text_active : R.string.timeCardStatusTextView_text_inactive );

        if( isTimeCardActive )
        {
            final Activity activeActivity = getTimeCard().getActiveActivity();
            activeActivityChargeNumberTextView_.setText( activeActivity.getChargeNumber().toString() );
            activeActivityStartTimeTextView_.setText( activeActivity.getStartTime().toString() );
        }
        else
        {
            activeActivityChargeNumberTextView_.setText( "" ); //$NON-NLS-1$
            activeActivityStartTimeTextView_.setText( "" ); //$NON-NLS-1$
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The time card listener for the activity.
     */
    @SuppressWarnings( "synthetic-access" )
    private final class TimeCardListener
        implements ITimeCardListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TimeCardListener} class.
         */
        TimeCardListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see com.belcan.beltime.model.ITimeCardListener#onActivityStarted(com.belcan.beltime.model.TimeCard, com.belcan.beltime.model.Activity)
         */
        @Override
        public void onActivityStarted(
            final TimeCard timeCard,
            final Activity activity )
        {
            update();
        }

        /*
         * @see com.belcan.beltime.model.ITimeCardListener#onActivityStopped(com.belcan.beltime.model.TimeCard, com.belcan.beltime.model.Activity)
         */
        @Override
        public void onActivityStopped(
            final TimeCard timeCard,
            final Activity activity )
        {
            update();
        }

        /*
         * @see com.belcan.beltime.model.ITimeCardListener#onReset(com.belcan.beltime.model.TimeCard)
         */
        @Override
        public void onReset(
            final TimeCard timeCard )
        {
            update();
        }
    }
}
