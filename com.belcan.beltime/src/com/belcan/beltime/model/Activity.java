/*
 * Activity.java
 *
 * Copyright 2014 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 6, 2013 at 11:02:35 PM.
 */

package com.belcan.beltime.model;

import java.util.Date;
import com.belcan.beltime.util.Duration;

/**
 * A continuous period of billable work performed against a job.
 */
public final class Activity
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The charge number of the job to be billed. */
    private final ChargeNumber chargeNumber_;

    /** The time at which the activity started, inclusive. */
    private final Date startTime_;

    /** The time at which the activity stopped, exclusive. */
    private Date stopTime_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Activity} class.
     * 
     * @param chargeNumber
     *        The charge number of the job to be billed.
     * @param startTime
     *        The time at which the activity started, inclusive.
     */
    private Activity(
        final ChargeNumber chargeNumber,
        final Date startTime )
    {
        chargeNumber_ = chargeNumber;
        startTime_ = new Date( startTime.getTime() );
        stopTime_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the charge number of the job to be billed.
     * 
     * @return The charge number of the job to be billed.
     */
    @SuppressWarnings( "null" )
    public ChargeNumber getChargeNumber()
    {
        return chargeNumber_;
    }

    /**
     * Gets the duration of the activity.
     * 
     * @return The duration of the activity.
     * 
     * @throws java.lang.IllegalStateException
     *         If the activity is active.
     */
    public Duration getDuration()
    {
        if( stopTime_ == null )
        {
            throw new IllegalStateException( "cannot get duration of an active activity" ); //$NON-NLS-1$
        }

        return Duration.fromMilliseconds( stopTime_.getTime() - startTime_.getTime() );
    }

    /**
     * Gets the time at which the activity started, inclusive.
     * 
     * @return The time at which the activity started, inclusive.
     */
    public Date getStartTime()
    {
        return new Date( startTime_.getTime() );
    }

    /**
     * Gets the time at which the activity stopped, exclusive.
     * 
     * @return The time at which the activity stopped, exclusive.
     * 
     * @throws java.lang.IllegalStateException
     *         If the activity is active.
     */
    public Date getStopTime()
    {
        if( stopTime_ == null )
        {
            throw new IllegalStateException( "cannot get stop time of an active activity" ); //$NON-NLS-1$
        }

        return new Date( stopTime_.getTime() );
    }

    /**
     * Indicates the activity is active.
     * 
     * @return {@code true} if the activity is active; otherwise {@code false}.
     */
    public boolean isActive()
    {
        return stopTime_ == null;
    }

    /**
     * Starts a new activity.
     * 
     * @param chargeNumber
     *        The charge number of the job to be billed.
     * @param startTime
     *        The time at which the activity started, inclusive.
     * 
     * @return A new activity.
     */
    public static Activity start(
        final ChargeNumber chargeNumber,
        final Date startTime )
    {
        return new Activity( chargeNumber, startTime );
    }

    /**
     * Stops the activity.
     * 
     * @param stopTime
     *        The time at which the activity stopped, exclusive.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code stopTime} is less than the time at which the activity
     *         started.
     * @throws java.lang.IllegalStateException
     *         If the activity is inactive.
     */
    public void stop(
        final Date stopTime )
    {
        if( stopTime.compareTo( startTime_ ) < 0 )
        {
            throw new IllegalArgumentException( "stop time must be greater than or equal to start time" ); //$NON-NLS-1$
        }
        else if( stopTime_ != null )
        {
            throw new IllegalStateException( "cannot stop an inactive activity" ); //$NON-NLS-1$
        }

        stopTime_ = new Date( stopTime.getTime() );
    }
}
