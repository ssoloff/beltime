/*
 * Job.java
 *
 * Copyright 2013 Beltime contributors and others.
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
 * A continuous unit of work that is billed to a single charge number.
 */
public final class Job
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The charge number to be billed. */
    private final ChargeNumber chargeNumber_;

    /** The time at which work on the job started. */
    private final Date startTime_;

    /** The time at which work on the job stopped. */
    private Date stopTime_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Job} class.
     * 
     * @param chargeNumber
     *        The charge number to be billed; must not be {@code null}.
     * @param startTime
     *        The time at which work on the job started; must not be
     *        {@code null}.
     */
    private Job(
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
     * Gets the charge number to be billed.
     * 
     * @return The charge number to be billed; never {@code null}.
     */
    @SuppressWarnings( "null" )
    public ChargeNumber getChargeNumber()
    {
        return chargeNumber_;
    }

    /**
     * Gets the duration of the job.
     * 
     * @return The duration of the job; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the job is active.
     */
    public Duration getDuration()
    {
        if( stopTime_ == null )
        {
            throw new IllegalStateException( "cannot get duration of an active job" ); //$NON-NLS-1$
        }

        return Duration.fromMilliseconds( stopTime_.getTime() - startTime_.getTime() );
    }

    /**
     * Gets the time at which work on the job started.
     * 
     * @return The time at which work on the job started; never {@code null}.
     */
    public Date getStartTime()
    {
        return new Date( startTime_.getTime() );
    }

    /**
     * Gets the time at which work on the job stopped.
     * 
     * @return The time at which work on the job stopped; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the job is active.
     */
    public Date getStopTime()
    {
        if( stopTime_ == null )
        {
            throw new IllegalStateException( "cannot get stop time of an active job" ); //$NON-NLS-1$
        }

        return new Date( stopTime_.getTime() );
    }

    /**
     * Indicates the job is active.
     * 
     * @return {@code true} if the job is active; otherwise {@code false}.
     */
    public boolean isActive()
    {
        return stopTime_ == null;
    }

    /**
     * Starts a new job.
     * 
     * @param chargeNumber
     *        The charge number to be billed; must not be {@code null}.
     * @param startTime
     *        The time at which work on the job started; must not be
     *        {@code null}.
     * 
     * @return A new job; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code chargeNumber} or {@code startTime} is {@code null}.
     */
    public static Job start(
        final ChargeNumber chargeNumber,
        final Date startTime )
    {
        return new Job( chargeNumber, startTime );
    }

    /**
     * Stops the job.
     * 
     * @param stopTime
     *        The time at which work on the job stopped; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code stopTime} is less than the time at which work on the
     *         job started.
     * @throws java.lang.IllegalStateException
     *         If the job is inactive.
     * @throws java.lang.NullPointerException
     *         If {@code stopTime} is {@code null}.
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
            throw new IllegalStateException( "cannot stop an inactive job" ); //$NON-NLS-1$
        }

        stopTime_ = new Date( stopTime.getTime() );
    }
}
