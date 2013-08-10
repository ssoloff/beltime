/*
 * NullAnalysis.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 9, 2013 at 9:13:46 PM.
 */

package com.belcan.beltime.util;

import org.eclipse.jdt.annotation.Nullable;

/**
 * A collection of methods useful for working with the null analysis engine.
 */
public final class NullAnalysis
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullAnalysis} class.
     */
    private NullAnalysis()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Casts the specified nullable value to a non-null value.
     * 
     * @param <T>
     *        The type of the value.
     * 
     * @param value
     *        The value; may be {@code null}.
     * 
     * @return The value passed as an argument to this method; never
     *         {@code null}.
     * 
     * @throws java.lang.AssertionError
     *         If {@code value} is {@code null}.
     */
    public static <T> T nonNull(
        @Nullable
        final T value )
    {
        if( value == null )
        {
            throw new AssertionError( "expected non-null value" ); //$NON-NLS-1$
        }

        return value;
    }
}
