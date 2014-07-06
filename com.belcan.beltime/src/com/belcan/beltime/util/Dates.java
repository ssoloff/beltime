/*
 * Dates.java
 *
 * Copyright 2014 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 9, 2013 at 11:03:18 PM.
 */

package com.belcan.beltime.util;

import java.util.Date;

/**
 * A collection of methods that provide commonly-used dates.
 */
public final class Dates
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Dates} class.
     */
    private Dates()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a date that represents the current time.
     * 
     * @return The current time.
     */
    public static Date now()
    {
        return new Date();
    }
}
