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

package com.belcan.beltime;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The main activity.
 */
public final class MainActivity
    extends Activity
    implements TimeCardListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The stop job button. */
    private Button stopJobButton;

    /** The time card. */
    private final TimeCard timeCard;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainActivity} class.
     */
    public MainActivity()
    {
        stopJobButton = null;
        timeCard = new TimeCard();

        timeCard.setTimeCardListener( this );
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
        return timeCard;
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

        stopJobButton = (Button)findViewById( R.id.stopJobButton );

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
     * @see com.belcan.beltime.TimeCardListener#onJobStarted(com.belcan.beltime.TimeCard, com.belcan.beltime.Job)
     */
    @Override
    public void onJobStarted(
        @SuppressWarnings( "hiding" )
        final TimeCard timeCard,
        final Job job )
    {
        update();
    }

    /*
     * @see com.belcan.beltime.TimeCardListener#onJobStopped(com.belcan.beltime.TimeCard, com.belcan.beltime.Job)
     */
    @Override
    public void onJobStopped(
        @SuppressWarnings( "hiding" )
        final TimeCard timeCard,
        final Job job )
    {
        update();
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
    public void startJob(
        final View view )
    {
        timeCard.startJob( ChargeNumber.fromString( "11111111.1111" ) ); //$NON-NLS-1$
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
    public void stopJob(
        final View view )
    {
        timeCard.stopActiveJob();
    }

    /**
     * Updates the state of the activity based on the model.
     */
    private void update()
    {
        stopJobButton.setEnabled( timeCard.isActive() );
    }
}
