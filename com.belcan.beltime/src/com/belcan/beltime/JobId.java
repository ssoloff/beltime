/*
 * JobId.java
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

package com.belcan.beltime;

/**
 * A job identifier.
 */
public final class JobId
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The job identifier encoded as a string. */
    private final String id;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code JobId} class.
     * 
     * @param id
     *        The job identifier encoded as a string; must not be {@code null}.
     */
    private JobId(
        final String id )
    {
        this.id = id;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code JobId} class from the specified job
     * identifier encoded as a string.
     * 
     * @param id
     *        The job identifier encoded as a string; must not be {@code null}.
     * 
     * @return A new instance of the {@code JobId} class; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    public static JobId fromString(
        final String id )
    {
        return new JobId( id );
    }

    /**
     * This method returns the job identifier encoded as a string exactly as it
     * was passed to {@link #fromString}.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    @SuppressWarnings( "null" )
    public String toString()
    {
        return id;
    }
}
