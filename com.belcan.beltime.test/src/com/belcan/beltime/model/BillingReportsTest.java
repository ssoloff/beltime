/*
 * BillingReportsTest.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 17, 2013 at 9:45:01 PM.
 */

package com.belcan.beltime.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import junit.framework.TestCase;
import com.belcan.beltime.util.DateRange;
import com.belcan.beltime.util.Duration;

/**
 * A fixture for testing the {@link BillingReports} class.
 */
public final class BillingReportsTest
    extends TestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The end time (23:59:59) of day 1. */
    private static final long DAY_1_END_TIME = 86399999L;

    /** The start time (00:00:00) of day 1. */
    private static final long DAY_1_START_TIME = 0L;

    /** The end time (23:59:59) of day 2. */
    private static final long DAY_2_END_TIME = 172799999L;

    /** The start time (00:00:00) of day 2. */
    private static final long DAY_2_START_TIME = 86400000L;

    /** The end time (23:59:59) of day 3. */
    private static final long DAY_3_END_TIME = 259199999L;

    /** The start time (00:00:00) of day 3. */
    private static final long DAY_3_START_TIME = 172800000L;

    /** One hour expressed in milliseconds. */
    private static final long ONE_HOUR = 3600000L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BillingReportsTest} class.
     */
    public BillingReportsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link BillingReports#daily} method returns the correct
     * billing reports when the time card contains an active activity.
     */
    @SuppressWarnings( "null" )
    public void testDaily_IgnoresActiveActivities()
    {
        final Collection<BillingReport> expectedBillingReports = Arrays.asList( //
            new BillingReport( //
                new DateRange( new Date( DAY_1_START_TIME ), new Date( DAY_1_END_TIME ) ), //
                Arrays.asList( //
                    new Bill( //
                        TestChargeNumbers.CHARGE_NUMBER_1, //
                        Duration.fromMilliseconds( ONE_HOUR ) //
                    ) //
                    ) //
            ) //
            );
        final TimeCard timeCard = new TimeCard();
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_1_START_TIME ) );
        timeCard.stopActiveJob( new Date( DAY_1_START_TIME + ONE_HOUR ) );
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_2, new Date( DAY_1_START_TIME + 2 * ONE_HOUR ) );

        assertEquals( expectedBillingReports, BillingReports.daily( timeCard ) );
    }

    /**
     * Ensures the {@link BillingReports#daily} method returns the correct
     * billing reports when the time card contains one activity that occurs over
     * one day.
     */
    @SuppressWarnings( "null" )
    public void testDaily_OneActivityOverOneDay()
    {
        final Collection<BillingReport> expectedBillingReports = Arrays.asList( //
            new BillingReport( //
                new DateRange( new Date( DAY_1_START_TIME ), new Date( DAY_1_END_TIME ) ), //
                Arrays.asList( //
                    new Bill( //
                        TestChargeNumbers.CHARGE_NUMBER_1, //
                        Duration.fromMilliseconds( ONE_HOUR ) //
                    ) //
                    ) //
            ) //
            );
        final TimeCard timeCard = new TimeCard();
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_1_START_TIME ) );
        timeCard.stopActiveJob( new Date( DAY_1_START_TIME + ONE_HOUR ) );

        assertEquals( expectedBillingReports, BillingReports.daily( timeCard ) );
    }

    /**
     * Ensures the {@link BillingReports#daily} method returns the correct
     * billing reports when the time card contains one activity that occurs over
     * two days.
     */
    @SuppressWarnings( "null" )
    public void testDaily_OneActivityOverTwoDays()
    {
        final Collection<BillingReport> expectedBillingReports = Arrays.asList( //
            new BillingReport( //
                new DateRange( new Date( DAY_1_START_TIME ), new Date( DAY_1_END_TIME ) ), //
                Arrays.asList( //
                    new Bill( //
                        TestChargeNumbers.CHARGE_NUMBER_1, //
                        Duration.fromMilliseconds( ONE_HOUR ) //
                    ) //
                    ) //
            ), //
            new BillingReport( //
                new DateRange( new Date( DAY_2_START_TIME ), new Date( DAY_2_END_TIME ) ), //
                Arrays.asList( //
                    new Bill( //
                        TestChargeNumbers.CHARGE_NUMBER_1, //
                        Duration.fromMilliseconds( 2 * ONE_HOUR ) //
                    ) //
                    ) //
            ) //
            );
        final TimeCard timeCard = new TimeCard();
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_1_START_TIME + 23 * ONE_HOUR ) );
        timeCard.stopActiveJob( new Date( DAY_2_START_TIME + 2 * ONE_HOUR ) );

        assertEquals( expectedBillingReports, BillingReports.daily( timeCard ) );
    }

    /**
     * Ensures the {@link BillingReports#daily} method returns the correct
     * billing reports when the time card contains three activities that occur
     * over two days. The first activity occurs entirely on one day, the second
     * activity spans the first day and the subsequent day, and the third
     * activity occurs entirely on the subsequent day. Each activity has the
     * same charge number.
     */
    @SuppressWarnings( "null" )
    public void testDaily_ThreeActivitiesOverTwoDays_SameChargeNumber_SecondActivitySpansTwoDays()
    {
        final Collection<BillingReport> expectedBillingReports = Arrays.asList( //
            new BillingReport( //
                new DateRange( new Date( DAY_1_START_TIME ), new Date( DAY_1_END_TIME ) ), //
                Arrays.asList( //
                    new Bill( //
                        TestChargeNumbers.CHARGE_NUMBER_1, //
                        Duration.fromMilliseconds( 2 * ONE_HOUR ) //
                    ) //
                    ) //
            ), //
            new BillingReport( //
                new DateRange( new Date( DAY_2_START_TIME ), new Date( DAY_2_END_TIME ) ), //
                Arrays.asList( //
                    new Bill( //
                        TestChargeNumbers.CHARGE_NUMBER_1, //
                        Duration.fromMilliseconds( 5 * ONE_HOUR ) //
                    ) //
                    ) //
            ) //
            );
        final TimeCard timeCard = new TimeCard();
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_1_START_TIME ) );
        timeCard.stopActiveJob( new Date( DAY_1_START_TIME + ONE_HOUR ) );
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_1_START_TIME + 23 * ONE_HOUR ) );
        timeCard.stopActiveJob( new Date( DAY_2_START_TIME + 2 * ONE_HOUR ) );
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_2_START_TIME + 6 * ONE_HOUR ) );
        timeCard.stopActiveJob( new Date( DAY_2_START_TIME + 9 * ONE_HOUR ) );

        assertEquals( expectedBillingReports, BillingReports.daily( timeCard ) );
    }

    /**
     * Ensures the {@link BillingReports#daily} method returns the correct
     * billing reports when the time card contains two activities that occur
     * over one day. Each activity has a different charge number.
     */
    @SuppressWarnings( "null" )
    public void testDaily_TwoActivitiesOverOneDay_DifferentChargeNumbers()
    {
        final Collection<BillingReport> expectedBillingReports = Arrays.asList( //
            new BillingReport( //
                new DateRange( new Date( DAY_1_START_TIME ), new Date( DAY_1_END_TIME ) ), //
                Arrays.asList( //
                    new Bill( //
                        TestChargeNumbers.CHARGE_NUMBER_1, //
                        Duration.fromMilliseconds( ONE_HOUR ) //
                    ), //
                    new Bill( //
                        TestChargeNumbers.CHARGE_NUMBER_2, //
                        Duration.fromMilliseconds( 2 * ONE_HOUR ) //
                    ) //
                    ) //
            ) //
            );
        final TimeCard timeCard = new TimeCard();
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_1_START_TIME ) );
        timeCard.stopActiveJob( new Date( DAY_1_START_TIME + ONE_HOUR ) );
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_2, new Date( DAY_1_START_TIME + 2 * ONE_HOUR ) );
        timeCard.stopActiveJob( new Date( DAY_1_START_TIME + 4 * ONE_HOUR ) );

        assertEquals( expectedBillingReports, BillingReports.daily( timeCard ) );
    }

    /**
     * Ensures the {@link BillingReports#daily} method returns the correct
     * billing reports when the time card contains two activities that occur
     * over one day. Each activity has the same charge number.
     */
    @SuppressWarnings( "null" )
    public void testDaily_TwoActivitiesOverOneDay_SameChargeNumber()
    {
        final Collection<BillingReport> expectedBillingReports = Arrays.asList( //
            new BillingReport( //
                new DateRange( new Date( DAY_1_START_TIME ), new Date( DAY_1_END_TIME ) ), //
                Arrays.asList( //
                    new Bill( //
                        TestChargeNumbers.CHARGE_NUMBER_1, //
                        Duration.fromMilliseconds( 2 * ONE_HOUR ) //
                    ) //
                    ) //
            ) //
            );
        final TimeCard timeCard = new TimeCard();
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_1_START_TIME ) );
        timeCard.stopActiveJob( new Date( DAY_1_START_TIME + ONE_HOUR ) );
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_1_START_TIME + 2 * ONE_HOUR ) );
        timeCard.stopActiveJob( new Date( DAY_1_START_TIME + 3 * ONE_HOUR ) );

        assertEquals( expectedBillingReports, BillingReports.daily( timeCard ) );
    }

    /**
     * Ensures the {@link BillingReports#daily} method returns the correct
     * billing reports when the time card contains two activities that occur
     * over two days. The first activity occurs entirely on one day, and the
     * second activity occurs entirely on the subsequent day. Each activity has
     * a different charge number.
     */
    @SuppressWarnings( "null" )
    public void testDaily_TwoActivitiesOverTwoDays_DifferentChargeNumbers()
    {
        final Collection<BillingReport> expectedBillingReports = Arrays.asList( //
            new BillingReport( //
                new DateRange( new Date( DAY_1_START_TIME ), new Date( DAY_1_END_TIME ) ), //
                Arrays.asList( //
                    new Bill( //
                        TestChargeNumbers.CHARGE_NUMBER_1, //
                        Duration.fromMilliseconds( ONE_HOUR ) //
                    ) //
                    ) //
            ), //
            new BillingReport( //
                new DateRange( new Date( DAY_2_START_TIME ), new Date( DAY_2_END_TIME ) ), //
                Arrays.asList( //
                    new Bill( //
                        TestChargeNumbers.CHARGE_NUMBER_2, //
                        Duration.fromMilliseconds( 2 * ONE_HOUR ) //
                    ) //
                    ) //
            ) //
            );
        final TimeCard timeCard = new TimeCard();
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_1_START_TIME ) );
        timeCard.stopActiveJob( new Date( DAY_1_START_TIME + ONE_HOUR ) );
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_2, new Date( DAY_2_START_TIME ) );
        timeCard.stopActiveJob( new Date( DAY_2_START_TIME + 2 * ONE_HOUR ) );

        assertEquals( expectedBillingReports, BillingReports.daily( timeCard ) );
    }

    /**
     * Ensures the {@link BillingReports#daily} method returns the correct
     * billing reports when the time card contains two activities that occur
     * over two days. The first activity occurs entirely on one day, and the
     * second activity occurs entirely on the subsequent day. Each activity has
     * the same charge number.
     */
    @SuppressWarnings( "null" )
    public void testDaily_TwoActivitiesOverTwoDays_SameChargeNumber()
    {
        final Collection<BillingReport> expectedBillingReports = Arrays.asList( //
            new BillingReport( //
                new DateRange( new Date( DAY_1_START_TIME ), new Date( DAY_1_END_TIME ) ), //
                Arrays.asList( //
                    new Bill( //
                        TestChargeNumbers.CHARGE_NUMBER_1, //
                        Duration.fromMilliseconds( ONE_HOUR ) //
                    ) //
                    ) //
            ), //
            new BillingReport( //
                new DateRange( new Date( DAY_2_START_TIME ), new Date( DAY_2_END_TIME ) ), //
                Arrays.asList( //
                    new Bill( //
                        TestChargeNumbers.CHARGE_NUMBER_1, //
                        Duration.fromMilliseconds( 2 * ONE_HOUR ) //
                    ) //
                    ) //
            ) //
            );
        final TimeCard timeCard = new TimeCard();
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_1_START_TIME ) );
        timeCard.stopActiveJob( new Date( DAY_1_START_TIME + ONE_HOUR ) );
        timeCard.startJob( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_2_START_TIME ) );
        timeCard.stopActiveJob( new Date( DAY_2_START_TIME + 2 * ONE_HOUR ) );

        assertEquals( expectedBillingReports, BillingReports.daily( timeCard ) );
    }

    /**
     * Ensures the {@link BillingReports#daily} method returns the correct
     * billing reports when the time card contains zero activities.
     */
    public void testDaily_ZeroActivities()
    {
        final Collection<BillingReport> expectedBillingReports = Arrays.asList();
        final TimeCard timeCard = new TimeCard();

        assertEquals( expectedBillingReports, BillingReports.daily( timeCard ) );
    }

    /**
     * Ensures the {@link BillingReports#getJobDays} method returns the correct
     * days when the job spans one day.
     */
    @SuppressWarnings( "null" )
    public void testGetJobDays_JobSpansOneDay()
    {
        final Collection<DateRange> expectedDays = Arrays.asList( //
            new DateRange( new Date( DAY_1_START_TIME ), new Date( DAY_1_END_TIME ) ) //
            );
        final Job job = Job.start( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_1_START_TIME + ONE_HOUR ) );
        job.stop( new Date( DAY_1_START_TIME + 2 * ONE_HOUR ) );

        assertEquals( expectedDays, BillingReports.getJobDays( job ) );
    }

    /**
     * Ensures the {@link BillingReports#getJobDays} method returns the correct
     * days when the job spans three days.
     */
    @SuppressWarnings( "null" )
    public void testGetJobDays_JobSpansThreeDays()
    {
        final Collection<DateRange> expectedDays = Arrays.asList( //
            new DateRange( new Date( DAY_1_START_TIME ), new Date( DAY_1_END_TIME ) ), //
            new DateRange( new Date( DAY_2_START_TIME ), new Date( DAY_2_END_TIME ) ), //
            new DateRange( new Date( DAY_3_START_TIME ), new Date( DAY_3_END_TIME ) ) //
            );
        final Job job = Job.start( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_1_START_TIME + ONE_HOUR ) );
        job.stop( new Date( DAY_3_START_TIME + ONE_HOUR ) );

        assertEquals( expectedDays, BillingReports.getJobDays( job ) );
    }

    /**
     * Ensures the {@link BillingReports#getJobDays} method returns the correct
     * days when the job spans two days.
     */
    @SuppressWarnings( "null" )
    public void testGetJobDays_JobSpansTwoDays()
    {
        final Collection<DateRange> expectedDays = Arrays.asList( //
            new DateRange( new Date( DAY_1_START_TIME ), new Date( DAY_1_END_TIME ) ), //
            new DateRange( new Date( DAY_2_START_TIME ), new Date( DAY_2_END_TIME ) ) //
            );
        final Job job = Job.start( TestChargeNumbers.CHARGE_NUMBER_1, new Date( DAY_1_START_TIME + ONE_HOUR ) );
        job.stop( new Date( DAY_2_START_TIME + ONE_HOUR ) );

        assertEquals( expectedDays, BillingReports.getJobDays( job ) );
    }
}
