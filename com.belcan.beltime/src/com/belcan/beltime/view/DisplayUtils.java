/*
 * DisplayUtils.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 8, 2013 at 10:12:19 PM.
 */

package com.belcan.beltime.view;

import android.content.Context;
import com.belcan.beltime.R;
import com.belcan.beltime.model.Job;

/**
 * A collection of useful methods for formatting objects for display.
 */
final class DisplayUtils
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The application context. */
    private final Context context_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DisplayUtils} class.
     * 
     * @param context
     *        The application context; must not be {@code null}.
     */
    DisplayUtils(
        final Context context )
    {
        context_ = context;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Formats the duration of the specified job.
     * 
     * @param job
     *        The job; must not be {@code null}.
     * 
     * @return The formatted duration; never {@code null}.
     */
    @SuppressWarnings( "null" )
    String formatJobDuration(
        final Job job )
    {
        if( job.isActive() )
        {
            return context_.getString( R.string.displayUtils_job_duration_active );
        }

        final double jobDurationInHours = job.getDurationInMilliseconds() / 86400000.0;
        return context_.getString( R.string.displayUtils_job_duration_inactive, Double.valueOf( jobDurationInHours ) );
    }
}
