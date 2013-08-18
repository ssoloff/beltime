/*
 * BillAsEquatableTest.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 17, 2013 at 9:58:35 PM.
 */

package com.belcan.beltime.model;

import java.util.ArrayList;
import java.util.Collection;
import com.belcan.beltime.test.AbstractEquatableTestCase;
import com.belcan.beltime.util.Duration;

/**
 * A fixture for testing the {@link Bill} class to ensure it does not violate
 * the contract of the equatable interface.
 */
public final class BillAsEquatableTest
    extends AbstractEquatableTestCase<Bill>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BillAsEquatableTest} class.
     */
    public BillAsEquatableTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see com.belcan.beltime.test.AbstractEquatableTestCase#createReferenceInstance()
     */
    @Override
    @SuppressWarnings( "null" )
    protected Bill createReferenceInstance()
    {
        return new Bill( TestChargeNumbers.CHARGE_NUMBER_1, Duration.fromMilliseconds( 1000L ) );
    }

    /*
     * @see com.belcan.beltime.test.AbstractEquatableTestCase#createUnequalInstances()
     */
    @Override
    @SuppressWarnings( "null" )
    protected Collection<Bill> createUnequalInstances()
    {
        final Collection<Bill> others = new ArrayList<Bill>();
        others.add( new Bill( TestChargeNumbers.CHARGE_NUMBER_2, Duration.fromMilliseconds( 1000L ) ) );
        others.add( new Bill( TestChargeNumbers.CHARGE_NUMBER_1, Duration.fromMilliseconds( 2000L ) ) );
        return others;
    }
}
