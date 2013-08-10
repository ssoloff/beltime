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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.belcan.beltime.R;
import com.belcan.beltime.model.ChargeNumber;
import com.belcan.beltime.model.ITimeCardListener;
import com.belcan.beltime.model.Job;
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

    /** The active job charge number text view. */
    private TextView activeJobChargeNumberTextView_;

    /** The active job start time text view. */
    private TextView activeJobStartTimeTextView_;

    /** The stop job button. */
    private Button stopJobButton_;

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
        timeCardStatusTextView_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
                public void onClick(
                    @Nullable
                    final DialogInterface dialog,
                    final int which )
                {
                    final ChargeNumber chargeNumber = ChargeNumber.fromString( NullAnalysis.nonNull( chargeNumberEditText.getText().toString() ) );
                    getTimeCard().startJob( chargeNumber, Dates.now() );
                }
            } ) //
            .setTitle( R.string.chargeNumberDialog_title ) //
            .setView( chargeNumberEditText ) //
            .create();
        alertDialog.show();
    }

    /**
     * Called when the start time card activity button has been clicked.
     * 
     * @param view
     *        The start time card activity button; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code view} is {@code null}.
     */
    public void onClickStartTimeCardActivity(
        final View view )
    {
        startActivity( new Intent( this, TimeCardActivity.class ) );
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
        getTimeCard().stopActiveJob( Dates.now() );
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
        stopJobButton_.setEnabled( isTimeCardActive );
        timeCardStatusTextView_.setText( isTimeCardActive ? R.string.timeCardStatusTextView_text_active : R.string.timeCardStatusTextView_text_inactive );

        if( isTimeCardActive )
        {
            final Job activeJob = getTimeCard().getActiveJob();
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
