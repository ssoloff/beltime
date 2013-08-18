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
     * The collection of jobs in chronological order. The active job, if any,
     * will be the the last entry.
     */
    private final List<Job> jobs_;

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
        jobs_ = new ArrayList<Job>();
        listener_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the active job.
     * 
     * @return The active job.
     * 
     * @throws java.lang.IllegalStateException
     *         If the time card is inactive.
     */
    public Job getActiveJob()
    {
        final Job activeJob = getActiveJobOrNull();
        if( activeJob == null )
        {
            throw new IllegalStateException( "no active job" ); //$NON-NLS-1$
        }

        return activeJob;
    }

    /**
     * Gets the active job or {@code null} if no job is active.
     * 
     * @return The active job or {@code null} if no job is active.
     */
    @Nullable
    private Job getActiveJobOrNull()
    {
        if( !jobs_.isEmpty() )
        {
            final Job job = jobs_.get( jobs_.size() - 1 );
            if( job.isActive() )
            {
                return job;
            }
        }

        return null;
    }

    /**
     * Gets the collection of jobs in chronological order. The active job, if
     * any, will be the the last entry.
     * 
     * @return The collection of jobs.
     */
    public List<Job> getJobs()
    {
        return new ArrayList<Job>( jobs_ );
    }

    /**
     * Indicates the time card is active.
     * 
     * @return {@code true} if the time card is active; otherwise {@code false}.
     */
    public boolean isActive()
    {
        return getActiveJobOrNull() != null;
    }

    /**
     * Resets the time card.
     */
    public void reset()
    {
        jobs_.clear();

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
     * Starts a new job.
     * 
     * <p>
     * If a job is currently active, it will be stopped, and a new job will be
     * started.
     * </p>
     * 
     * @param chargeNumber
     *        The charge number to be billed.
     * @param startTime
     *        The time at which work on the new job started.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If a job is currently active and {@code startTime} is less than
     *         the time at which work on the active job started.
     */
    public void startJob(
        final ChargeNumber chargeNumber,
        final Date startTime )
    {
        if( isActive() )
        {
            stopActiveJob( startTime );
        }

        final Job job = Job.start( chargeNumber, startTime );
        jobs_.add( job );

        if( listener_ != null )
        {
            listener_.onJobStarted( this, job );
        }
    }

    /**
     * Stops the active job.
     * 
     * @param stopTime
     *        The time at which work on the active job stopped.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code stopTime} is less than the time at which work on the
     *         active job started.
     * @throws java.lang.IllegalStateException
     *         If the time card is inactive.
     */
    public void stopActiveJob(
        final Date stopTime )
    {
        final Job job = getActiveJob();
        job.stop( stopTime );

        if( listener_ != null )
        {
            listener_.onJobStopped( this, job );
        }
    }
}
