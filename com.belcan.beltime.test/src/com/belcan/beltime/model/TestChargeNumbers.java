/*
 * TestChargeNumbers.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 9, 2013 at 7:55:29 PM.
 */

package com.belcan.beltime.model;

import com.belcan.beltime.model.ChargeNumber;

/**
 * A collection of charge numbers used for testing.
 */
public final class TestChargeNumbers
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The first charge number. */
    public static final ChargeNumber CHARGE_NUMBER_1 = ChargeNumber.fromString( "1111111.1111" ); //$NON-NLS-1$

    /** The second charge number. */
    public static final ChargeNumber CHARGE_NUMBER_2 = ChargeNumber.fromString( "2222222.2222" ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TestChargeNumbers} class.
     */
    private TestChargeNumbers()
    {
    }
}
