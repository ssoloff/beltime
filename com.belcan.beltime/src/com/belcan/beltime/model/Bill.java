/*
 * Bill.java
 *
 * Copyright 2014 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 14, 2013 at 9:01:26 PM.
 */

package com.belcan.beltime.model;

import com.belcan.beltime.util.Duration;
import com.belcan.beltime.util.NullAnalysis;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A bill for all activities performed against a job over a specific period of
 * time.
 */
public final class Bill
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The charge number of the job to be billed. */
    private final ChargeNumber chargeNumber_;

    /**
     * The cumulative duration of all activities performed against the job over
     * the period of time covered by this bill.
     */
    private final Duration duration_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Bill} class.
     * 
     * @param chargeNumber
     *        The charge number of the job to be billed.
     * @param duration
     *        The cumulative duration of all activities performed against the
     *        job over the period of time covered by this bill.
     */
    Bill(
        final ChargeNumber chargeNumber,
        final Duration duration )
    {
        chargeNumber_ = chargeNumber;
        duration_ = duration;
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

        if( !(o instanceof Bill) )
        {
            return false;
        }

        final Bill other = (Bill)o;
        return chargeNumber_.equals( other.chargeNumber_ ) //
            && duration_.equals( other.duration_ );
    }

    /**
     * Gets the charge number of the job to be billed.
     * 
     * @return The charge number of the job to be billed.
     */
    @SuppressWarnings( "null" )
    public ChargeNumber getChargeNumber()
    {
        return chargeNumber_;
    }

    /**
     * Gets the cumulative duration of all activities performed against the job
     * over the period of time covered by this bill.
     * 
     * @return The cumulative duration of all activities performed against the
     *         job over the period of time covered by this bill.
     */
    @SuppressWarnings( "null" )
    public Duration getDuration()
    {
        return duration_;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int hashCode = 17;
        hashCode = 31 * hashCode + chargeNumber_.hashCode();
        hashCode = 31 * hashCode + duration_.hashCode();
        return hashCode;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Bill[" ); //$NON-NLS-1$
        sb.append( "chargeNumber=" ); //$NON-NLS-1$
        sb.append( chargeNumber_ );
        sb.append( ", duration=" ); //$NON-NLS-1$
        sb.append( duration_ );
        sb.append( "]" ); //$NON-NLS-1$
        return NullAnalysis.nonNull( sb.toString() );
    }
}
