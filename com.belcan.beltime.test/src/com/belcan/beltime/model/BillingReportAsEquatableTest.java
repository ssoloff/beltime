/*
 * BillingReportAsEquatableTest.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 17, 2013 at 10:06:02 PM.
 */

package com.belcan.beltime.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import com.belcan.beltime.test.AbstractEquatableTestCase;
import com.belcan.beltime.util.DateRange;
import com.belcan.beltime.util.Duration;

/**
 * A fixture for testing the {@link BillingReport} class to ensure it does not
 * violate the contract of the equatable interface.
 */
public final class BillingReportAsEquatableTest
    extends AbstractEquatableTestCase<BillingReport>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The reference billing report bills collection. */
    @SuppressWarnings( "null" )
    private static final Collection<Bill> BILLS = Arrays.asList( new Bill( TestChargeNumbers.CHARGE_NUMBER_1, Duration.fromMilliseconds( 1000L ) ) );

    /** The reference billing report date range. */
    private static final DateRange DATE_RANGE = new DateRange( new Date( 0L ), new Date( 1000L ) );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BillingReportAsEquatableTest}
     * class.
     */
    public BillingReportAsEquatableTest()
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
    protected BillingReport createReferenceInstance()
    {
        return new BillingReport( DATE_RANGE, BILLS );
    }

    /*
     * @see com.belcan.beltime.test.AbstractEquatableTestCase#createUnequalInstances()
     */
    @Override
    @SuppressWarnings( "null" )
    protected Collection<BillingReport> createUnequalInstances()
    {
        final Collection<BillingReport> others = new ArrayList<BillingReport>();
        others.add( new BillingReport( DATE_RANGE, Arrays.asList( new Bill( TestChargeNumbers.CHARGE_NUMBER_2, Duration.fromMilliseconds( 2000L ) ) ) ) );
        others.add( new BillingReport( new DateRange( new Date( DATE_RANGE.getBeginDate().getTime() + 1L ), DATE_RANGE.getEndDate() ), BILLS ) );
        return others;
    }
}
