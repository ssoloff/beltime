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
    private final List<Job> jobs;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TimeCard} class.
     */
    public TimeCard()
    {
        this.jobs = new ArrayList<Job>();
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
        if( !jobs.isEmpty() )
        {
            final Job job = jobs.get( jobs.size() - 1 );
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
        return new ArrayList<Job>( jobs );
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
        final Job activeJob = getActiveJobOrNull();
        if( activeJob != null )
        {
            activeJob.stop();
        }

        jobs.add( Job.start( chargeNumber ) );
    }

    /**
     * Stops the active job.
     * 
     * @throws java.lang.IllegalStateException
     *         If the time card is inactive.
     */
    public void stopActiveJob()
    {
        getActiveJob().stop();
    }
}
