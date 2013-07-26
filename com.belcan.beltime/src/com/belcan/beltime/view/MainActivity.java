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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.belcan.beltime.R;
import com.belcan.beltime.model.ChargeNumber;
import com.belcan.beltime.model.ITimeCardListener;
import com.belcan.beltime.model.Job;
import com.belcan.beltime.model.TimeCard;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The main activity.
 */
public final class MainActivity
    extends Activity
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The active job charge number text view. */
    private TextView activeJobChargeNumberTextView_;

    /** The active job start time text view. */
    private TextView activeJobStartTimeTextView_;

    /** The stop job button. */
    private Button stopJobButton_;

    /** The time card. */
    private final TimeCard timeCard_;

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
        activeJobChargeNumberTextView_ = null;
        activeJobStartTimeTextView_ = null;
        stopJobButton_ = null;
        timeCard_ = new TimeCard();
        timeCardStatusTextView_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the time card.
     * 
     * @return The time card; never {@code null}.
     */
    @SuppressWarnings( "null" )
    TimeCard getTimeCard()
    {
        return timeCard_;
    }

    /**
     * Called when the start job button has been clicked.
     * 
     * @param view
     *        The start job button; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code view} is {@code null}.
     */
    public void onClickStartJob(
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
                @SuppressWarnings( "synthetic-access" )
                public void onClick(
                    @Nullable
                    final DialogInterface dialog,
                    final int which )
                {
                    @SuppressWarnings( "null" )
                    final ChargeNumber chargeNumber = ChargeNumber.fromString( chargeNumberEditText.getText().toString() );
                    timeCard_.startJob( chargeNumber );
                }
            } ) //
            .setTitle( R.string.chargeNumberDialog_title ) //
            .setView( chargeNumberEditText ) //
            .create();
        alertDialog.show();
    }

    /**
     * Called when the stop job button has been clicked.
     * 
     * @param view
     *        The stop job button; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code view} is {@code null}.
     */
    public void onClickStopJob(
        final View view )
    {
        timeCard_.stopActiveJob();
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
        activeJobChargeNumberTextView_ = (TextView)findViewById( R.id.activeJobChargeNumberTextView );
        activeJobStartTimeTextView_ = (TextView)findViewById( R.id.activeJobStartTimeTextView );
        stopJobButton_ = (Button)findViewById( R.id.stopJobButton );
        timeCardStatusTextView_ = (TextView)findViewById( R.id.timeCardStatusTextView );

        timeCard_.setTimeCardListener( new TimeCardListener() );

        update();
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
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy()
    {
        timeCard_.setTimeCardListener( null );

        super.onDestroy();
    }

    /**
     * Updates the state of the activity based on the model.
     */
    private void update()
    {
        final boolean isTimeCardActive = timeCard_.isActive();
        stopJobButton_.setEnabled( isTimeCardActive );
        timeCardStatusTextView_.setText( isTimeCardActive ? R.string.timeCardStatusTextView_text_active : R.string.timeCardStatusTextView_text_inactive );

        if( isTimeCardActive )
        {
            final Job activeJob = timeCard_.getActiveJob();
            activeJobChargeNumberTextView_.setText( activeJob.getChargeNumber().toString() );
            activeJobStartTimeTextView_.setText( activeJob.getStartTime().toString() );
        }
        else
        {
            activeJobChargeNumberTextView_.setText( "" ); //$NON-NLS-1$
            activeJobStartTimeTextView_.setText( "" ); //$NON-NLS-1$
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The time card listener for the main activity.
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
         * @see com.belcan.beltime.ITimeCardListener#onJobStarted(com.belcan.beltime.TimeCard, com.belcan.beltime.Job)
         */
        @Override
        public void onJobStarted(
            final TimeCard timeCard,
            final Job job )
        {
            update();
        }

        /*
         * @see com.belcan.beltime.ITimeCardListener#onJobStopped(com.belcan.beltime.TimeCard, com.belcan.beltime.Job)
         */
        @Override
        public void onJobStopped(
            final TimeCard timeCard,
            final Job job )
        {
            update();
        }
    }
}
