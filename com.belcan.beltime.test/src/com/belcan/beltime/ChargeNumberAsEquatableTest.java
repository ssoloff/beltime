/*
 * ChargeNumberAsEquatableTest.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 12, 2013 at 9:01:59 PM.
 */

package com.belcan.beltime;

import java.util.ArrayList;
import java.util.Collection;
import com.belcan.beltime.test.AbstractEquatableTestCase;

/**
 * A fixture for testing the {@link ChargeNumber} class to ensure it does not
 * violate the contract of the equatable interface.
 */
public final class ChargeNumberAsEquatableTest
    extends AbstractEquatableTestCase<ChargeNumber>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ChargeNumberAsEquatableTest}
     * class.
     */
    public ChargeNumberAsEquatableTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see com.belcan.beltime.test.AbstractEquatableTestCase#createReferenceInstance()
     */
    @Override
    protected ChargeNumber createReferenceInstance()
    {
        return ChargeNumber.fromString( "1111111.1111" ); //$NON-NLS-1$
    }

    /*
     * @see com.belcan.beltime.test.AbstractEquatableTestCase#createUnequalInstances()
     */
    @Override
    protected Collection<ChargeNumber> createUnequalInstances()
    {
        final Collection<ChargeNumber> others = new ArrayList<ChargeNumber>();
        others.add( ChargeNumber.fromString( "2222222.2222" ) ); //$NON-NLS-1$
        return others;
    }
}
