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
import com.belcan.beltime.util.NullAnalysis;

/**
 * A collection of useful methods for formatting objects for display.
 */
final class DisplayUtils
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The number of milliseconds per hour. */
    private static final double MILLISECONDS_PER_HOUR = 60.0 * 60.0 * 1000.0;

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
     * Formats the charge number of the specified job.
     * 
     * @param job
     *        The job; must not be {@code null}.
     * 
     * @return The formatted charge number; never {@code null}.
     */
    @SuppressWarnings( "static-method" )
    String formatChargeNumber(
        final Job job )
    {
        return job.getChargeNumber().toString();
    }

    /**
     * Formats the duration of the specified job.
     * 
     * @param job
     *        The job; must not be {@code null}.
     * 
     * @return The formatted duration; never {@code null}.
     */
    String formatDuration(
        final Job job )
    {
        if( job.isActive() )
        {
            return NullAnalysis.nonNull( context_.getString( R.string.displayUtils_duration_active ) );
        }

        final double durationInHours = job.getDuration().toMilliseconds() / MILLISECONDS_PER_HOUR;
        return NullAnalysis.nonNull( context_.getString( R.string.displayUtils_duration, Double.valueOf( durationInHours ) ) );
    }

    /**
     * Formats the start time of the specified job.
     * 
     * @param job
     *        The job; must not be {@code null}.
     * 
     * @return The formatted start time; never {@code null}.
     */
    @SuppressWarnings( "static-method" )
    String formatStartTime(
        final Job job )
    {
        return NullAnalysis.nonNull( job.getStartTime().toString() );
    }

    /**
     * Formats the stop time of the specified job.
     * 
     * @param job
     *        The job; must not be {@code null}.
     * 
     * @return The formatted stop time; never {@code null}.
     */
    String formatStopTime(
        final Job job )
    {
        if( job.isActive() )
        {
            return NullAnalysis.nonNull( context_.getString( R.string.displayUtils_stopTime_active ) );
        }

        return NullAnalysis.nonNull( job.getStopTime().toString() );
    }
}
