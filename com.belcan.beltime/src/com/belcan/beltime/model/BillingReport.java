/*
 * BillingReport.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 17, 2013 at 8:06:15 PM.
 */

package com.belcan.beltime.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * A billing report.
 * 
 * <p>
 * A billing report consists of a collection of bills charged during a specified
 * period of time.
 * </p>
 */
public final class BillingReport
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The beginning date of the report, inclusive. */
    private final Date beginDate_;

    /** The collection of bills charged during the time period of the report. */
    private final Collection<Bill> bills_;

    /** The ending date of the report, inclusive. */
    private final Date endDate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BillingReport} class.
     * 
     * @param beginDate
     *        The beginning date of the report, inclusive. No copy is made of
     *        this value and it must not be modified after calling this method.
     * @param endDate
     *        The ending date of the report, inclusive. No copy is made of this
     *        value and it must not be modified after calling this method.
     * @param bills
     *        The collection of bills charged during the time period of the
     *        report. No copy is made of this collection and it must not be
     *        modified after calling this method.
     */
    BillingReport(
        final Date beginDate,
        final Date endDate,
        final Collection<Bill> bills )
    {
        beginDate_ = beginDate;
        bills_ = bills;
        endDate_ = endDate;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the beginning date of the report, inclusive.
     * 
     * @return The beginning date of the report, inclusive.
     */
    public Date getBeginDate()
    {
        return new Date( beginDate_.getTime() );
    }

    /**
     * Gets the collection of bills charged during the time period of the
     * report.
     * 
     * @return The collection of bills charged during the time period of the
     *         report.
     */
    public Collection<Bill> getBills()
    {
        return new ArrayList<Bill>( bills_ );
    }

    /**
     * Gets the ending date of the report, inclusive.
     * 
     * @return The ending date of the report, inclusive.
     */
    public Date getEndDate()
    {
        return new Date( endDate_.getTime() );
    }
}
