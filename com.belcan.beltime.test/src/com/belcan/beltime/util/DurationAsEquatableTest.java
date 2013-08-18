/*
 * DurationAsEquatableTest.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 17, 2013 at 8:53:09 PM.
 */

package com.belcan.beltime.util;

import java.util.ArrayList;
import java.util.Collection;
import com.belcan.beltime.test.AbstractEquatableTestCase;

/**
 * A fixture for testing the {@link Duration} class to ensure it does not
 * violate the contract of the equatable interface.
 */
public final class DurationAsEquatableTest
    extends AbstractEquatableTestCase<Duration>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DurationAsEquatableTest} class.
     */
    public DurationAsEquatableTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see com.belcan.beltime.test.AbstractEquatableTestCase#createReferenceInstance()
     */
    @Override
    protected Duration createReferenceInstance()
    {
        return Duration.fromMilliseconds( 1000L );
    }

    /*
     * @see com.belcan.beltime.test.AbstractEquatableTestCase#createUnequalInstances()
     */
    @Override
    protected Collection<Duration> createUnequalInstances()
    {
        final Collection<Duration> others = new ArrayList<Duration>();
        others.add( Duration.fromMilliseconds( 0L ) );
        others.add( Duration.fromMilliseconds( Long.MAX_VALUE ) );
        return others;
    }
}
