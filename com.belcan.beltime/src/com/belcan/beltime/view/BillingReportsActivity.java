/*
 * BillingReportsActivity.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Sep 20, 2013 at 10:38:07 PM.
 */

package com.belcan.beltime.view;

import android.os.Bundle;
import com.belcan.beltime.R;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The billing reports activity.
 */
public final class BillingReportsActivity
    extends AbstractBeltimeActivity
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BillingReportsActivity} class.
     */
    public BillingReportsActivity()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(
        @Nullable
        final Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_billing_reports );
    }
}
