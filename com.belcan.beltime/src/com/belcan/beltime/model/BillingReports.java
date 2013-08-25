/*
 * BillingReports.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 17, 2013 at 9:29:28 PM.
 */

package com.belcan.beltime.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import com.belcan.beltime.util.DateRange;
import com.belcan.beltime.util.Duration;
import com.belcan.beltime.util.NullAnalysis;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A collection of commonly-used billing reports.
 */
public final class BillingReports
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The number of milliseconds per day. */
    private static final long MILLISECONDS_PER_DAY = 24L * 60L * 60L * 1000L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BillingReports} class.
     */
    private BillingReports()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new billing report for the specified day and collection of
     * charge number durations.
     * 
     * @param beginDate
     *        The beginning date of the report, inclusive.
     * @param endDate
     *        The ending date of the report, inclusive.
     * @param durations
     *        The collection of durations billed to each charge number. The key
     *        is the charge number. The value is the cumulative duration of all
     *        activities billed to the charge number during the period of time
     *        specified by {@code [beginDate, endDate]}. This collection will be
     *        cleared before this method returns.
     * 
     * @return A new billing report.
     */
    private static BillingReport createBillingReport(
        final Date beginDate,
        final Date endDate,
        final Map<ChargeNumber, Duration> durations )
    {
        final List<Bill> bills = new ArrayList<Bill>();
        for( final Map.Entry<ChargeNumber, Duration> entry : durations.entrySet() )
        {
            bills.add( new Bill( NullAnalysis.nonNull( entry.getKey() ), NullAnalysis.nonNull( entry.getValue() ) ) );
        }

        // ensure reports are always returned in a deterministic order (sorted by charge number)
        Collections.sort( bills, new Comparator<Bill>()
        {
            @Override
            public int compare(
                @Nullable
                final Bill lhs,
                @Nullable
                final Bill rhs )
            {
                assert lhs != null;
                assert rhs != null;

                return lhs.getChargeNumber().compareTo( rhs.getChargeNumber() );
            }
        } );

        durations.clear();

        return new BillingReport( new DateRange( beginDate, endDate ), bills );
    }

    /**
     * Creates a new calendar to be used for billing date calculations.
     * 
     * @return A new calendar.
     */
    private static Calendar createCalendar()
    {
        return NullAnalysis.nonNull( Calendar.getInstance( TimeZone.getTimeZone( "GMT" ) ) ); //$NON-NLS-1$
    }

    /**
     * Generates a collection of daily billing reports for the specified time
     * card.
     * 
     * <p>
     * A billing report will be generated for each day that has at least one
     * time card activity.
     * </p>
     * 
     * @param timeCard
     *        The time card for which the billing reports will be generated.
     * 
     * @return A collection of daily billing reports for the specified time
     *         card.
     */
    public static Collection<BillingReport> daily(
        final TimeCard timeCard )
    {
        // TODO: need to define how to handle active jobs

        final Collection<BillingReport> billingReports = new ArrayList<BillingReport>();
        final Map<ChargeNumber, Duration> durations = new HashMap<ChargeNumber, Duration>();

        Date currentStartOfDay = null;
        Date currentEndOfDay = null;
        for( final Job job : timeCard.getJobs() )
        {
            for( final DateRange day : getJobDays( NullAnalysis.nonNull( job ) ) )
            {
                final Date startOfDay = day.getBeginDate();
                final Date endOfDay = day.getEndDate();

                if( (currentStartOfDay != null) && (currentEndOfDay != null) && !currentStartOfDay.equals( startOfDay ) )
                {
                    billingReports.add( createBillingReport( currentStartOfDay, currentEndOfDay, durations ) );
                }

                final long startTime = Math.max( job.getStartTime().getTime(), startOfDay.getTime() );
                final long stopTime = Math.min( job.getStopTime().getTime(), endOfDay.getTime() + 1L ); // add +1 to endOfDay because stopTime is exclusive
                updateDuration( durations, job.getChargeNumber(), startTime, stopTime );

                currentStartOfDay = startOfDay;
                currentEndOfDay = endOfDay;
            }
        }

        if( (currentStartOfDay != null) && (currentEndOfDay != null) && !durations.isEmpty() )
        {
            billingReports.add( createBillingReport( currentStartOfDay, currentEndOfDay, durations ) );
        }

        return billingReports;
    }

    /**
     * Gets the end of day associated with the specified date.
     * 
     * @param date
     *        The date.
     * 
     * @return The end of day associated with the specified date.
     */
    private static Date getEndOfDay(
        final Date date )
    {
        final Calendar calendar = createCalendar();
        calendar.setTime( date );
        calendar.set( Calendar.HOUR_OF_DAY, 23 );
        calendar.set( Calendar.MINUTE, 59 );
        calendar.set( Calendar.SECOND, 59 );
        calendar.set( Calendar.MILLISECOND, 999 );
        return NullAnalysis.nonNull( calendar.getTime() );
    }

    /**
     * Gets the collection of days spanned by the specified job.
     * 
     * @param job
     *        The job.
     * 
     * @return The collection of days spanned by the specified job. Each day is
     *         represented as a {@link DateRange} whose beginning date
     *         corresponds to time 00:00:00 on the specified day, and whose
     *         ending date corresponds to time 23:59:59 on the specified day.
     */
    static Collection<DateRange> getJobDays(
        final Job job )
    {
        final Collection<DateRange> days = new ArrayList<DateRange>();
        final Date startOfFirstDay = getStartOfDay( job.getStartTime() );
        final Date endOfLastDay = getEndOfDay( job.getStopTime() );

        final Date startOfDay = new Date( startOfFirstDay.getTime() );
        while( startOfDay.compareTo( endOfLastDay ) < 0 )
        {
            days.add( new DateRange( startOfDay, getEndOfDay( startOfDay ) ) );
            startOfDay.setTime( startOfDay.getTime() + MILLISECONDS_PER_DAY );
        }

        return days;
    }

    /**
     * Gets the start of day associated with the specified date.
     * 
     * @param date
     *        The date.
     * 
     * @return The start of day associated with the specified date.
     */
    private static Date getStartOfDay(
        final Date date )
    {
        final Calendar calendar = createCalendar();
        calendar.setTime( date );
        calendar.set( Calendar.HOUR_OF_DAY, 0 );
        calendar.set( Calendar.MINUTE, 0 );
        calendar.set( Calendar.SECOND, 0 );
        calendar.set( Calendar.MILLISECOND, 0 );
        return NullAnalysis.nonNull( calendar.getTime() );
    }

    /**
     * Updates the specified collection of durations with the specified activity
     * duration.
     * 
     * @param durations
     *        The collection of durations billed to each charge number. The key
     *        is the charge number. The value is the cumulative duration of all
     *        activities billed to the charge number during the period of time
     *        specified by
     *        {@code [startTimeInMilliseconds, endTimeInMilliseconds)}.
     * @param chargeNumber
     *        The charge number to be billed.
     * @param startTimeInMilliseconds
     *        The activity start time in milliseconds, inclusive.
     * @param stopTimeInMilliseconds
     *        The activity stop time in milliseconds, exclusive.
     */
    private static void updateDuration(
        final Map<ChargeNumber, Duration> durations,
        final ChargeNumber chargeNumber,
        final long startTimeInMilliseconds,
        final long stopTimeInMilliseconds )
    {
        Duration oldDuration = durations.get( chargeNumber );
        if( oldDuration == null )
        {
            oldDuration = Duration.fromMilliseconds( 0L );
        }

        final long additionalDurationInMilliseconds = stopTimeInMilliseconds - startTimeInMilliseconds;
        final Duration newDuration = Duration.fromMilliseconds( oldDuration.toMilliseconds() + additionalDurationInMilliseconds );
        durations.put( chargeNumber, newDuration );
    }
}
