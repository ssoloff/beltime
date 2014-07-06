/*
 * BillingReportsActivityTest.java
 *
 * Copyright 2014 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Sep 20, 2013 at 10:58:48 PM.
 */

package com.belcan.beltime.view;

import android.test.UiThreadTest;
import com.jayway.android.robotium.solo.Solo;

/**
 * A fixture for testing the {@link BillingReportsActivity} class.
 */
public final class BillingReportsActivityTest
    extends AbstractBeltimeActivityInstrumentationTestCase<BillingReportsActivity>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The Robotium manager. */
    private Solo solo_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BillingReportsActivityTest}
     * class.
     */
    public BillingReportsActivityTest()
    {
        super( BillingReportsActivity.class );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see android.test.ActivityInstrumentationTestCase2#setUp()
     */
    @Override
    protected void setUp()
        throws Exception
    {
        super.setUp();

        setActivityInitialTouchMode( false );

        solo_ = new Solo( getInstrumentation(), getActivity() );
    }

    /*
     * @see android.test.ActivityInstrumentationTestCase2#tearDown()
     */
    @Override
    protected void tearDown()
        throws Exception
    {
        solo_.finishOpenedActivities();

        super.tearDown();
    }

    /**
     * Ensures the activity pre-conditions are satisfied.
     */
    @UiThreadTest
    public void testPreConditions()
    {
        // TODO
    }
}
