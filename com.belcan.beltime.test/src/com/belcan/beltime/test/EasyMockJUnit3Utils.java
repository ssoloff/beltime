/*
 * EasyMockJUnit3Utils.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 18, 2013 at 11:55:23 PM.
 */

package com.belcan.beltime.test;

import junit.framework.AssertionFailedError;
import org.easymock.IMocksControl;

/**
 * A collection of useful methods for writing JUnit 3 tests using EasyMock.
 */
public final class EasyMockJUnit3Utils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EasyMockJUnit3Utils} class.
     */
    private EasyMockJUnit3Utils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Verifies that all expectations were met in the specified mocks control.
     * 
     * @param mocksControl
     *        The mocks control.
     * 
     * @throws junit.framework.AssertionFailedError
     *         If an expectation is not met.
     */
    public static void verify(
        final IMocksControl mocksControl )
    {
        try
        {
            mocksControl.verify();
        }
        catch( final AssertionError e )
        {
            final AssertionFailedError failure = new AssertionFailedError( e.getMessage() );
            failure.initCause( e );
            throw failure;
        }
    }
}
