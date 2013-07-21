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

package com.belcan.beltime;

import java.util.ArrayList;
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
    private TimeCardListener listener_;


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
     * @return The active job; never {@code null}.
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
     * @return The collection of jobs; never {@code null}.
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
     * Sets the time card listener.
     * 
     * @param listener
     *        The time card listener or {@code null} to clear the time card
     *        listener.
     */
    public void setTimeCardListener(
        @Nullable
        final TimeCardListener listener )
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
     *        The charge number to be billed; must not be {@code null}.
     */
    public void startJob(
        final ChargeNumber chargeNumber )
    {
        final Job oldJob = getActiveJobOrNull();
        if( oldJob != null )
        {
            stopJob( oldJob );
        }

        final Job newJob = Job.start( chargeNumber );
        jobs_.add( newJob );

        if( listener_ != null )
        {
            listener_.onJobStarted( this, newJob );
        }
    }

    /**
     * Stops the active job.
     * 
     * @throws java.lang.IllegalStateException
     *         If the time card is inactive.
     */
    public void stopActiveJob()
    {
        stopJob( getActiveJob() );
    }

    /**
     * Stops the specified job.
     * 
     * @param job
     *        The job; must not be {@code null}.
     */
    private void stopJob(
        final Job job )
    {
        job.stop();

        if( listener_ != null )
        {
            listener_.onJobStopped( this, job );
        }
    }
}
