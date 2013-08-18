/*
 * BeltimeApplication.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 26, 2013 at 11:17:13 PM.
 */

package com.belcan.beltime.view;

import android.app.Application;
import com.belcan.beltime.model.TimeCard;

/**
 * The Beltime application.
 */
public final class BeltimeApplication
    extends Application
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The time card. */
    private final TimeCard timeCard_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BeltimeApplication} class.
     */
    public BeltimeApplication()
    {
        timeCard_ = new TimeCard();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the time card.
     * 
     * @return The time card.
     */
    @SuppressWarnings( "null" )
    TimeCard getTimeCard()
    {
        return timeCard_;
    }
}
