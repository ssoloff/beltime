/*
 * AbstractBeltimeActivityInstrumentationTestCase.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 27, 2013 at 10:18:31 PM.
 */

package com.belcan.beltime.view;

import junit.framework.AssertionFailedError;
import android.test.ActivityInstrumentationTestCase2;
import com.belcan.beltime.model.TimeCard;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Superclass for all fixtures that test instances of
 * {@link AbstractBeltimeActivity} using instrumentation.
 * 
 * @param <T>
 *        The type of activity to test.
 */
public class AbstractBeltimeActivityInstrumentationTestCase<T extends AbstractBeltimeActivity>
    extends ActivityInstrumentationTestCase2<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractBeltimeActivityInstrumentationTestCase} class.
     * 
     * @param activityClass
     *        The class of the activity to test; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code activityClass} is {@code null}.
     */
    AbstractBeltimeActivityInstrumentationTestCase(
        final Class<T> activityClass )
    {
        super( activityClass );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the application time card.
     * 
     * @return The application time card; never {@code null}.
     */
    final TimeCard getTimeCard()
    {
        return getActivity().getTimeCard();
    }

    /**
     * Resets the application time card.
     */
    final void resetTimeCard()
    {
        runTestOnUiThread( new Runnable()
        {
            @Override
            public void run()
            {
                getTimeCard().reset();
            }
        } );
    }

    /*
     * @see android.test.InstrumentationTestCase#runTestOnUiThread(java.lang.Runnable)
     */
    @Override
    public final void runTestOnUiThread(
        @Nullable
        final Runnable r )
    {
        try
        {
            super.runTestOnUiThread( r );
        }
        catch( final RuntimeException e )
        {
            throw e;
        }
        catch( final Error e )
        {
            throw e;
        }
        catch( final Throwable e )
        {
            throw (Error)(new AssertionFailedError( "unexpected checked exception" ).initCause( e )); //$NON-NLS-1$
        }
    }
}
