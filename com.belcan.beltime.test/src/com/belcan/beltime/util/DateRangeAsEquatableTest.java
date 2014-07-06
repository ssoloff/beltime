/*
 * DateRangeAsEquatableTest.java
 *
 * Copyright 2014 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 24, 2013 at 8:16:31 PM.
 */

package com.belcan.beltime.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import com.belcan.beltime.test.AbstractEquatableTestCase;

/**
 * A fixture for testing the {@link DateRange} class to ensure it does not
 * violate the contract of the equatable interface.
 */
public final class DateRangeAsEquatableTest
    extends AbstractEquatableTestCase<DateRange>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The reference date range beginning date. */
    private static final Date BEGIN_DATE = new Date( 0L );

    /** The reference date range ending date. */
    private static final Date END_DATE = new Date( 1000L );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DateRangeAsEquatableTest} class.
     */
    public DateRangeAsEquatableTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see com.belcan.beltime.test.AbstractEquatableTestCase#createReferenceInstance()
     */
    @Override
    @SuppressWarnings( "null" )
    protected DateRange createReferenceInstance()
    {
        return new DateRange( BEGIN_DATE, END_DATE );
    }

    /*
     * @see com.belcan.beltime.test.AbstractEquatableTestCase#createUnequalInstances()
     */
    @Override
    @SuppressWarnings( "null" )
    protected Collection<DateRange> createUnequalInstances()
    {
        final Collection<DateRange> others = new ArrayList<DateRange>();
        others.add( new DateRange( new Date( BEGIN_DATE.getTime() + 1L ), END_DATE ) );
        others.add( new DateRange( BEGIN_DATE, new Date( END_DATE.getTime() + 1L ) ) );
        return others;
    }
}
