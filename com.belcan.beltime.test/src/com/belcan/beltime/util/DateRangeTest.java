/*
 * DateRangeTest.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 24, 2013 at 8:20:45 PM.
 */

package com.belcan.beltime.util;

import java.util.Date;
import junit.framework.TestCase;

/**
 * A fixture for testing the {@link DateRange} class.
 */
public final class DateRangeTest
    extends TestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The inclusive begin date for use in the fixture. */
    private static final Date BEGIN_DATE = new Date( 0L );

    /** The inclusive end date for use in the fixture. */
    private static final Date END_DATE = new Date( 1000L );

    /** The date range under test in the fixture. */
    private DateRange dateRange_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DateRangeTest} class.
     */
    public DateRangeTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    @SuppressWarnings( "null" )
    protected void setUp()
        throws Exception
    {
        super.setUp();

        dateRange_ = new DateRange( BEGIN_DATE, END_DATE );
    }

    /**
     * Ensures the {@link DateRange#DateRange} constructor copies the begin
     * date.
     */
    @SuppressWarnings( "null" )
    public void testConstructor_CopiesBeginDate()
    {
        final Date beginDate = new Date( BEGIN_DATE.getTime() );

        final DateRange dateRange = new DateRange( beginDate, END_DATE );
        beginDate.setTime( beginDate.getTime() + 1L );

        assertEquals( BEGIN_DATE, dateRange.getBeginDateInclusive() );
    }

    /**
     * Ensures the {@link DateRange#DateRange} constructor copies the end date.
     */
    @SuppressWarnings( "null" )
    public void testConstructor_CopiesEndDate()
    {
        final Date endDate = new Date( END_DATE.getTime() );

        final DateRange dateRange = new DateRange( BEGIN_DATE, endDate );
        endDate.setTime( endDate.getTime() + 1L );

        assertEquals( END_DATE, dateRange.getEndDateInclusive() );
    }

    /**
     * Ensures the {@link DateRange#DateRange} constructor throws an exception
     * if the beginning date of the range is less than the ending date of the
     * range.
     */
    @SuppressWarnings( "null" )
    public void testConstructor_ThrowsExceptionIfEndDateLessThanBeginDate()
    {
        try
        {
            @SuppressWarnings( "unused" )
            final DateRange dateRange = new DateRange( BEGIN_DATE, new Date( BEGIN_DATE.getTime() - 1L ) );
            fail( "DateRange() did not throw IllegalArgumentException" ); //$NON-NLS-1$
        }
        catch( final IllegalArgumentException e )
        {
            // expected
        }
    }

    /**
     * Ensures the {@link DateRange#getBeginDateExclusive} method returns a copy
     * of the begin date.
     */
    public void testGetBeginDateExclusive_ReturnsCopy()
    {
        final Date beginDate = dateRange_.getBeginDateExclusive();
        final Date expectedBeginDate = new Date( beginDate.getTime() );
        beginDate.setTime( beginDate.getTime() + 1L );

        assertEquals( expectedBeginDate, dateRange_.getBeginDateExclusive() );
    }

    /**
     * Ensures the {@link DateRange#getBeginDateExclusive} method returns the
     * exclusive begin date.
     */
    public void testGetBeginDateExclusive_ReturnsExclusiveBeginDate()
    {
        assertEquals( new Date( BEGIN_DATE.getTime() + 1L ), dateRange_.getBeginDateExclusive() );
    }

    /**
     * Ensures the {@link DateRange#getBeginDateInclusive} method returns a copy
     * of the begin date.
     */
    public void testGetBeginDateInclusive_ReturnsCopy()
    {
        final Date beginDate = dateRange_.getBeginDateInclusive();
        final Date expectedBeginDate = new Date( beginDate.getTime() );
        beginDate.setTime( beginDate.getTime() + 1L );

        assertEquals( expectedBeginDate, dateRange_.getBeginDateInclusive() );
    }

    /**
     * Ensures the {@link DateRange#getBeginDateInclusive} method returns the
     * inclusive begin date.
     */
    public void testGetBeginDateInclusive_ReturnsInclusiveBeginDate()
    {
        assertEquals( BEGIN_DATE, dateRange_.getBeginDateInclusive() );
    }

    /**
     * Ensures the {@link DateRange#getEndDateExclusive} method returns a copy
     * of the end date.
     */
    public void testGetEndDateExclusive_ReturnsCopy()
    {
        final Date endDate = dateRange_.getEndDateExclusive();
        final Date expectedEndDate = new Date( endDate.getTime() );
        endDate.setTime( endDate.getTime() + 1L );

        assertEquals( expectedEndDate, dateRange_.getEndDateExclusive() );
    }

    /**
     * Ensures the {@link DateRange#getEndDateExclusive} method returns the
     * exclusive end date.
     */
    public void testGetEndDateExclusive_ReturnsExclusiveEndDate()
    {
        assertEquals( new Date( END_DATE.getTime() - 1L ), dateRange_.getEndDateExclusive() );
    }

    /**
     * Ensures the {@link DateRange#getEndDateInclusive} method returns a copy
     * of the end date.
     */
    public void testGetEndDateInclusive_ReturnsCopy()
    {
        final Date endDate = dateRange_.getEndDateInclusive();
        final Date expectedEndDate = new Date( endDate.getTime() );
        endDate.setTime( endDate.getTime() + 1L );

        assertEquals( expectedEndDate, dateRange_.getEndDateInclusive() );
    }

    /**
     * Ensures the {@link DateRange#getEndDateInclusive} method returns the
     * inclusive end date.
     */
    public void testGetEndDateInclusive_ReturnsInclusiveEndDate()
    {
        assertEquals( END_DATE, dateRange_.getEndDateInclusive() );
    }
}
