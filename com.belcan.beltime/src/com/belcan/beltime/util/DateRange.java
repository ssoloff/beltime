/*
 * DateRange.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 24, 2013 at 8:16:09 PM.
 */

package com.belcan.beltime.util;

import java.util.Date;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A range of dates.
 */
public final class DateRange
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The beginning date of the range, inclusive. */
    private final Date beginDate_;

    /** The ending date of the range, inclusive. */
    private final Date endDate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DateRange} class.
     * 
     * @param beginDate
     *        The beginning date of the range, inclusive.
     * @param endDate
     *        The ending date of the range, inclusive.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code endDate} is less than {@code beginDate}.
     */
    public DateRange(
        final Date beginDate,
        final Date endDate )
    {
        if( endDate.compareTo( beginDate ) < 0 )
        {
            throw new IllegalArgumentException( "ending date must be greater than or equal to beginning date" ); //$NON-NLS-1$
        }

        beginDate_ = new Date( beginDate.getTime() );
        endDate_ = new Date( endDate.getTime() );
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
        if( this == o )
        {
            return true;
        }

        if( !(o instanceof DateRange) )
        {
            return false;
        }

        final DateRange other = (DateRange)o;
        return beginDate_.equals( other.beginDate_ ) //
            && endDate_.equals( other.endDate_ );
    }

    /**
     * Gets the beginning date of the range, inclusive.
     * 
     * @return The beginning date of the range, inclusive.
     */
    public Date getBeginDate()
    {
        return new Date( beginDate_.getTime() );
    }

    /**
     * Gets the ending date of the range, inclusive.
     * 
     * @return The ending date of the range, inclusive.
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
        sb.append( "DateRange[" ); //$NON-NLS-1$
        sb.append( "beginDate=" ); //$NON-NLS-1$
        sb.append( beginDate_ );
        sb.append( ", endDate=" ); //$NON-NLS-1$
        sb.append( endDate_ );
        sb.append( "]" ); //$NON-NLS-1$
        return NullAnalysis.nonNull( sb.toString() );
    }
}
