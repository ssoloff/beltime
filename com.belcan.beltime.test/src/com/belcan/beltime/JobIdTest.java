/*
 * JobIdTest.java
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

package com.belcan.beltime;

import android.test.AndroidTestCase;

/**
 * A fixture for testing the {@link JobId} class.
 */
public final class JobIdTest
    extends AndroidTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code JobIdTest} class.
     */
    public JobIdTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link JobId#fromString} method correctly creates a job
     * identifier.
     */
    public void testFromString_CreatesExpectedJobId()
    {
        final String expectedValueAsString = "1234567.1234"; //$NON-NLS-1$

        final JobId actualValue = JobId.fromString( expectedValueAsString );

        assertEquals( expectedValueAsString, actualValue.toString() );
    }
}
