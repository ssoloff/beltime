/*
 * ChargeNumberTest.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 12, 2013 at 8:01:16 PM.
 */

package com.belcan.beltime.model;

import junit.framework.TestCase;

/**
 * A fixture for testing the {@link ChargeNumber} class.
 */
public final class ChargeNumberTest
    extends TestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ChargeNumberTest} class.
     */
    public ChargeNumberTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ChargeNumber#fromString} method correctly creates a
     * charge number.
     */
    public void testFromString_CreatesExpectedChargeNumber()
    {
        final String expectedValueAsString = "1234567.1234"; //$NON-NLS-1$

        final ChargeNumber actualValue = ChargeNumber.fromString( expectedValueAsString );

        assertEquals( expectedValueAsString, actualValue.toString() );
    }
}
