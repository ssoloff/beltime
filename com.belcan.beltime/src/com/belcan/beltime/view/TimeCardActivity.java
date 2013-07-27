/*
 * TimeCardActivity.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 26, 2013 at 10:02:28 PM.
 */

package com.belcan.beltime.view;

import android.app.Activity;
import android.os.Bundle;
import com.belcan.beltime.R;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The time card activity.
 */
public final class TimeCardActivity
    extends Activity
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TimeCardActivity} class.
     */
    public TimeCardActivity()
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

        setContentView( R.layout.activity_time_card );
    }
}
