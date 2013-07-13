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

package com.belcan.beltime;

import java.util.Date;

/**
 * A continuous unit of work that is billed to a single charge number.
 */
public final class Job
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The charge number to be billed. */
    private final ChargeNumber chargeNumber;

    /** The time at which work on the job started. */
    private final Date startTime;

    /** The time at which work on the job stopped. */
    private Date stopTime;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Job} class.
     * 
     * @param chargeNumber
     *        The charge number to be billed; must not be {@code null}.
     */
    private Job(
        final ChargeNumber chargeNumber )
    {
        this.chargeNumber = chargeNumber;
        this.startTime = new Date();
        this.stopTime = null;
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
        return chargeNumber;
    }

    /**
     * Gets the duration of the job in milliseconds.
     * 
     * @return The duration of the job in milliseconds.
     * 
     * @throws java.lang.IllegalStateException
     *         If the job is active.
     */
    public long getDurationInMilliseconds()
    {
        if( stopTime == null )
        {
            throw new IllegalStateException( "cannot get duration of an active job" ); //$NON-NLS-1$
        }

        return stopTime.getTime() - startTime.getTime();
    }

    /**
     * Gets the time at which work on the job started.
     * 
     * @return The time at which work on the job started; never {@code null}.
     */
    public Date getStartTime()
    {
        return new Date( startTime.getTime() );
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
        if( stopTime == null )
        {
            throw new IllegalStateException( "cannot get stop time of an active job" ); //$NON-NLS-1$
        }

        return new Date( stopTime.getTime() );
    }

    /**
     * Indicates the job is active.
     * 
     * @return {@code true} if the job is active; otherwise {@code false}.
     */
    public boolean isActive()
    {
        return stopTime == null;
    }

    /**
     * Starts a new job.
     * 
     * @param chargeNumber
     *        The charge number to be billed; must not be {@code null}.
     * 
     * @return A new job; never {@code null}.
     */
    public static Job start(
        final ChargeNumber chargeNumber )
    {
        return new Job( chargeNumber );
    }

    /**
     * Stops the job.
     * 
     * @throws java.lang.IllegalStateException
     *         If the job is inactive.
     */
    public void stop()
    {
        if( stopTime != null )
        {
            throw new IllegalStateException( "cannot stop an inactive job" ); //$NON-NLS-1$
        }

        stopTime = new Date();
    }
}
