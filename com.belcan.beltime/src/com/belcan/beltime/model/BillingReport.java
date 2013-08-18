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
        return beginDate_.equals( other.beginDate_ ) //
            && endDate_.equals( other.endDate_ ) //
            && bills_.equals( other.bills_ );
    }

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

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int hashCode = 17;
        hashCode = 31 * hashCode + beginDate_.hashCode();
        hashCode = 31 * hashCode + bills_.hashCode();
        hashCode = 31 * hashCode + endDate_.hashCode();
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
        sb.append( "beginDate=" ); //$NON-NLS-1$
        sb.append( beginDate_ );
        sb.append( ", endDate=" ); //$NON-NLS-1$
        sb.append( endDate_ );
        sb.append( ", bills=" ); //$NON-NLS-1$
        sb.append( bills_ );
        sb.append( "]" ); //$NON-NLS-1$
        return NullAnalysis.nonNull( sb.toString() );
    }
}
