/*
 * DisplayUtils.java
 *
 * Copyright 2014 Beltime contributors and others.
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
import com.belcan.beltime.model.Activity;
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
     *        The application context.
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
     * Formats the charge number of the specified activity.
     * 
     * @param activity
     *        The activity.
     * 
     * @return The formatted charge number.
     */
    @SuppressWarnings( "static-method" )
    String formatChargeNumber(
        final Activity activity )
    {
        return activity.getChargeNumber().toString();
    }

    /**
     * Formats the duration of the specified activity.
     * 
     * @param activity
     *        The activity.
     * 
     * @return The formatted duration.
     */
    String formatDuration(
        final Activity activity )
    {
        if( activity.isActive() )
        {
            return NullAnalysis.nonNull( context_.getString( R.string.displayUtils_duration_active ) );
        }

        final double durationInHours = activity.getDuration().toMilliseconds() / MILLISECONDS_PER_HOUR;
        return NullAnalysis.nonNull( context_.getString( R.string.displayUtils_duration, Double.valueOf( durationInHours ) ) );
    }

    /**
     * Formats the start time of the specified activity.
     * 
     * @param activity
     *        The activity.
     * 
     * @return The formatted start time.
     */
    @SuppressWarnings( "static-method" )
    String formatStartTime(
        final Activity activity )
    {
        return NullAnalysis.nonNull( activity.getStartTime().toString() );
    }

    /**
     * Formats the stop time of the specified activity.
     * 
     * @param activity
     *        The activity.
     * 
     * @return The formatted stop time.
     */
    String formatStopTime(
        final Activity activity )
    {
        if( activity.isActive() )
        {
            return NullAnalysis.nonNull( context_.getString( R.string.displayUtils_stopTime_active ) );
        }

        return NullAnalysis.nonNull( activity.getStopTime().toString() );
    }
}
