/*
 * TimeCard.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 12, 2013 at 10:54:56 PM.
 */

package com.belcan.beltime.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A time card.
 */
public final class TimeCard
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of activities in chronological order. The active activity,
     * if any, will be the the last entry.
     */
    private final List<Activity> activities_;

    /** The time card listener or {@code null} if none. */
    private ITimeCardListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TimeCard} class.
     */
    public TimeCard()
    {
        activities_ = new ArrayList<Activity>();
        listener_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the active activity.
     * 
     * @return The active activity.
     * 
     * @throws java.lang.IllegalStateException
     *         If the time card is inactive.
     */
    public Activity getActiveActivity()
    {
        final Activity activeActivity = getActiveActivityOrNull();
        if( activeActivity == null )
        {
            throw new IllegalStateException( "no active activity" ); //$NON-NLS-1$
        }

        return activeActivity;
    }

    /**
     * Gets the active activity or {@code null} if no activity is active.
     * 
     * @return The active activity or {@code null} if no activirt is active.
     */
    @Nullable
    private Activity getActiveActivityOrNull()
    {
        if( !activities_.isEmpty() )
        {
            final Activity activity = activities_.get( activities_.size() - 1 );
            if( activity.isActive() )
            {
                return activity;
            }
        }

        return null;
    }

    /**
     * Gets the collection of activities in chronological order. The active
     * activity, if any, will be the the last entry.
     * 
     * @return The collection of activities.
     */
    public List<Activity> getActivities()
    {
        return new ArrayList<Activity>( activities_ );
    }

    /**
     * Indicates the time card is active.
     * 
     * @return {@code true} if the time card is active; otherwise {@code false}.
     */
    public boolean isActive()
    {
        return getActiveActivityOrNull() != null;
    }

    /**
     * Resets the time card.
     */
    public void reset()
    {
        activities_.clear();

        if( listener_ != null )
        {
            listener_.onReset( this );
        }
    }

    /**
     * Sets the time card listener.
     * 
     * @param listener
     *        The time card listener or {@code null} to clear the time card
     *        listener.
     */
    public void setTimeCardListener(
        @Nullable
        final ITimeCardListener listener )
    {
        listener_ = listener;
    }

    /**
     * Starts a new activity.
     * 
     * <p>
     * If an activity is currently active, it will be stopped, and a new
     * activity will be started.
     * </p>
     * 
     * @param chargeNumber
     *        The charge number of the job to be billed.
     * @param startTime
     *        The time at which the new activity started.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If an activity is currently active and {@code startTime} is less
     *         than the time at which the active activity started.
     */
    public void startActivity(
        final ChargeNumber chargeNumber,
        final Date startTime )
    {
        if( isActive() )
        {
            stopActiveActivity( startTime );
        }

        final Activity activity = Activity.start( chargeNumber, startTime );
        activities_.add( activity );

        if( listener_ != null )
        {
            listener_.onActivityStarted( this, activity );
        }
    }

    /**
     * Stops the active activity.
     * 
     * @param stopTime
     *        The time at which the active activity stopped.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code stopTime} is less than the time at which the active
     *         activity started.
     * @throws java.lang.IllegalStateException
     *         If the time card is inactive.
     */
    public void stopActiveActivity(
        final Date stopTime )
    {
        final Activity activity = getActiveActivity();
        activity.stop( stopTime );

        if( listener_ != null )
        {
            listener_.onActivityStopped( this, activity );
        }
    }
}
