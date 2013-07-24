/*
 * ChargeNumber.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 12, 2013 at 8:00:16 PM.
 */

package com.belcan.beltime.model;

import org.eclipse.jdt.annotation.Nullable;

/**
 * A charge number.
 * 
 * <p>
 * A charge number can be a project number or work order number.
 * </p>
 */
public final class ChargeNumber
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The charge number encoded as a string. */
    private final String value_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ChargeNumber} class.
     * 
     * @param value
     *        The charge number encoded as a string; must not be {@code null}.
     */
    private ChargeNumber(
        final String value )
    {
        value_ = value;
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

        if( !(o instanceof ChargeNumber) )
        {
            return false;
        }

        final ChargeNumber other = (ChargeNumber)o;
        return value_.equals( other.value_ );
    }

    /**
     * Creates a new instance of the {@code ChargeNumber} class from the
     * specified string representation.
     * 
     * @param value
     *        The charge number encoded as a string; must not be {@code null}.
     * 
     * @return A new instance of the {@code ChargeNumber} class; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code value} is {@code null}.
     */
    public static ChargeNumber fromString(
        final String value )
    {
        return new ChargeNumber( value );
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return value_.hashCode();
    }

    /**
     * This method returns the charge number encoded as a string exactly as it
     * was passed to {@link #fromString}.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    @SuppressWarnings( "null" )
    public String toString()
    {
        return value_;
    }
}
