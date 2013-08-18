/*
 * Duration.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 17, 2013 at 8:47:33 PM.
 */

package com.belcan.beltime.util;

import org.eclipse.jdt.annotation.Nullable;

/**
 * A duration of time with millisecond precision.
 */
public final class Duration
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The duration value in milliseconds. */
    private final long milliseconds_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Duration} class.
     * 
     * @param milliseconds
     *        The duration value in milliseconds.
     */
    private Duration(
        final long milliseconds )
    {
        milliseconds_ = milliseconds;
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

        if( !(o instanceof Duration) )
        {
            return false;
        }

        final Duration other = (Duration)o;
        return milliseconds_ == other.milliseconds_;
    }

    /**
     * Creates a new instance of the {@code Duration} class from a duration
     * value in milliseconds.
     * 
     * @param milliseconds
     *        The duration value in milliseconds.
     * 
     * @return A new instance of the {@code Duration} class.
     */
    public static Duration fromMilliseconds(
        final long milliseconds )
    {
        return new Duration( milliseconds );
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return (int)(milliseconds_ ^ (milliseconds_ >>> 32));
    }

    /**
     * Gets the duration value in milliseconds.
     * 
     * @return The duration value in milliseconds.
     */
    public long toMilliseconds()
    {
        return milliseconds_;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Duration[milliseconds_ = " + milliseconds_ + "]"; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
