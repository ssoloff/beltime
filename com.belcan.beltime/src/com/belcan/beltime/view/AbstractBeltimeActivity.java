/*
 * AbstractBeltimeActivity.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 27, 2013 at 7:59:52 PM.
 */

package com.belcan.beltime.view;

import android.app.Activity;
import com.belcan.beltime.model.TimeCard;
import com.belcan.beltime.util.NullAnalysis;

/**
 * Superclass for all activities in the Beltime application.
 */
public abstract class AbstractBeltimeActivity
    extends Activity
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractBeltimeActivity} class.
     */
    AbstractBeltimeActivity()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the Beltime application.
     * 
     * @return The Beltime application; never {@code null}.
     */
    final BeltimeApplication getBeltimeApplication()
    {
        return NullAnalysis.nonNull( (BeltimeApplication)getApplication() );
    }

    /**
     * Gets the time card.
     * 
     * @return The time card; never {@code null}.
     */
    final TimeCard getTimeCard()
    {
        return getBeltimeApplication().getTimeCard();
    }
}
