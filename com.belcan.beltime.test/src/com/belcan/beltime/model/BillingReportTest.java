/*
 * BillingReportTest.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 17, 2013 at 8:16:06 PM.
 */

package com.belcan.beltime.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import junit.framework.TestCase;
import com.belcan.beltime.util.Duration;

/**
 * A fixture for testing the {@link BillingReport} class.
 */
public final class BillingReportTest
    extends TestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The billing report under test in the fixture. */
    private BillingReport billingReport_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BillingReportTest} class.
     */
    public BillingReportTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
        throws Exception
    {
        super.setUp();

        billingReport_ = new BillingReport( new Date( 0L ), new Date( 3600000L ), new ArrayList<Bill>() );
    }

    /**
     * Ensures the {@link BillingReport#getBeginDate} method returns a copy of
     * the beginning date.
     */
    public void testGetBeginDate_ReturnsCopy()
    {
        final Date beginDate = billingReport_.getBeginDate();
        final Date expectedBeginDate = new Date( beginDate.getTime() );
        beginDate.setTime( beginDate.getTime() + 1L );

        assertEquals( expectedBeginDate, billingReport_.getBeginDate() );
    }

    /**
     * Ensures the {@link BillingReport#getBills} method returns a copy of the
     * bills collection.
     */
    @SuppressWarnings( "null" )
    public void testGetBills_ReturnsCopy()
    {
        final Collection<Bill> bills = billingReport_.getBills();
        final Collection<Bill> expectedBills = new ArrayList<Bill>( bills );
        bills.add( new Bill( TestChargeNumbers.CHARGE_NUMBER_1, Duration.fromMilliseconds( 0L ) ) );

        assertEquals( expectedBills, billingReport_.getBills() );
    }

    /**
     * Ensures the {@link BillingReport#getEndDate} method returns a copy of the
     * ending date.
     */
    public void testGetEndDate_ReturnsCopy()
    {
        final Date endDate = billingReport_.getEndDate();
        final Date expectedEndDate = new Date( endDate.getTime() );
        endDate.setTime( endDate.getTime() + 1L );

        assertEquals( expectedEndDate, billingReport_.getEndDate() );
    }
}
