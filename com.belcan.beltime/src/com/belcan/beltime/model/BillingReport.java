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
import com.belcan.beltime.util.DateRange;
import com.belcan.beltime.util.NullAnalysis;
import org.eclipse.jdt.annotation.Nullable;

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

    /** The collection of bills charged during the time period of the report. */
    private final Collection<Bill> bills_;

    /** The date range of the report. */
    private final DateRange dateRange_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BillingReport} class.
     * 
     * @param dateRange
     *        The date range of the report.
     * @param bills
     *        The collection of bills charged during the time period of the
     *        report. No copy is made of this collection and it must not be
     *        modified after calling this method.
     */
    BillingReport(
        final DateRange dateRange,
        final Collection<Bill> bills )
    {
        bills_ = bills;
        dateRange_ = dateRange;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        @Nullable
        final Object o )
    {
        if( o == this )
        {
            return true;
        }

        if( !(o instanceof BillingReport) )
        {
            return false;
        }

        final BillingReport other = (BillingReport)o;
        return dateRange_.equals( other.dateRange_ ) //
            && bills_.equals( other.bills_ );
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
     * Gets the date range of the report.
     * 
     * @return The date range of the report.
     */
    @SuppressWarnings( "null" )
    public DateRange getDateRange()
    {
        return dateRange_;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int hashCode = 17;
        hashCode = 31 * hashCode + bills_.hashCode();
        hashCode = 31 * hashCode + dateRange_.hashCode();
        return hashCode;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "BillingReport[" ); //$NON-NLS-1$
        sb.append( "dateRange=" ); //$NON-NLS-1$
        sb.append( dateRange_ );
        sb.append( ", bills=" ); //$NON-NLS-1$
        sb.append( bills_ );
        sb.append( "]" ); //$NON-NLS-1$
        return NullAnalysis.nonNull( sb.toString() );
    }
}
