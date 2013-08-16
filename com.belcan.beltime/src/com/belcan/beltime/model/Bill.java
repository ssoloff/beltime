/*
 * Bill.java
 *
 * Copyright 2013 Beltime contributors and others.
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
     * The cumulative duration in milliseconds of all activities performed
     * against the job over the period of time covered by this bill.
     */
    private final long durationInMilliseconds_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Bill} class.
     * 
     * @param chargeNumber
     *        The charge number of the job to be billed.
     * @param durationInMilliseconds
     *        The cumulative duration in milliseconds of all activities
     *        performed against the job over the period of time covered by this
     *        bill.
     */
    public Bill(
        final ChargeNumber chargeNumber,
        final long durationInMilliseconds )
    {
        chargeNumber_ = chargeNumber;
        durationInMilliseconds_ = durationInMilliseconds;
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
     * Gets the cumulative duration in milliseconds of all activities performed
     * against the job over the period of time covered by this bill.
     * 
     * @return The cumulative duration in milliseconds of all activities
     *         performed against the job over the period of time covered by this
     *         bill.
     */
    public long getDurationInMilliseconds()
    {
        return durationInMilliseconds_;
    }
}
