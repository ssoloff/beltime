/*
 * AbstractComparableTestCase.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Aug 23, 2013 at 10:46:47 PM.
 */

package com.belcan.beltime.test;

import java.util.Collection;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link Comparable} interface.
 * 
 * @param <T>
 *        The type of the comparable class.
 */
public abstract class AbstractComparableTestCase<T extends Comparable<T>>
    extends AbstractEquatableTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractComparableTestCase}
     * class.
     */
    protected AbstractComparableTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a collection of instances that are greater than the reference
     * instance.
     * 
     * <p>
     * Implementors should return one instance per field that is used to
     * determine comparability. The returned collection must not be empty and
     * must not contain a {@code null} entry.
     * </p>
     * 
     * @return A collection of instances that are greater than the reference
     *         instance; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract Collection<T> createGreaterThanInstances()
        throws Exception;

    /**
     * Creates a collection of instances that are less than the reference
     * instance.
     * 
     * <p>
     * Implementors should return one instance per field that is used to
     * determine comparability. The returned collection must not be empty and
     * must not contain a {@code null} entry.
     * </p>
     * 
     * @return A collection of instances that are less than the reference
     *         instance; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract Collection<T> createLessThanInstances()
        throws Exception;

    /**
     * Ensures the {@link Comparable#compareTo} method correctly indicates
     * instances that are equal to the reference instance are equal to the
     * reference instance.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    public void testCompareTo_Equal()
        throws Exception
    {
        final T reference1 = createReferenceInstance();
        final T reference2 = createReferenceInstance();

        assertEquals( 0, reference1.compareTo( reference2 ) );
        assertEquals( 0, reference2.compareTo( reference1 ) );
    }

    /**
     * Ensures the {@link Comparable#compareTo} method correctly indicates
     * instances that are greater than the reference instance are greater than
     * the reference instance.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    public void testCompareTo_GreaterThan()
        throws Exception
    {
        final T reference = createReferenceInstance();

        for( final T other : createGreaterThanInstances() )
        {
            assertTrue( String.format( "expected <%s> to be less than <%s>", reference, other ), reference.compareTo( other ) < 0 ); //$NON-NLS-1$
            assertTrue( String.format( "expected <%s> to be greater than <%s>", other, reference ), other.compareTo( reference ) > 0 ); //$NON-NLS-1$
        }
    }

    /**
     * Ensures the {@link Comparable#compareTo} method correctly indicates
     * instances that are less than the reference instance are less than the
     * reference instance.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    public void testCompareTo_LessThan()
        throws Exception
    {
        final T reference = createReferenceInstance();

        for( final T other : createLessThanInstances() )
        {
            assertTrue( String.format( "expected <%s> to be greater than <%s>", reference, other ), reference.compareTo( other ) > 0 ); //$NON-NLS-1$
            assertTrue( String.format( "expected <%s> to be less than <%s>", other, reference ), other.compareTo( reference ) < 0 ); //$NON-NLS-1$
        }
    }

    /**
     * Ensures the {@link Comparable#compareTo} method correctly indicates
     * {@code null} is less than the reference instance.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    public void testCompareTo_LessThan_Null()
        throws Exception
    {
        final T reference = createReferenceInstance();

        assertTrue( String.format( "expected <%s> to be greater than null", reference ), reference.compareTo( null ) > 0 ); //$NON-NLS-1$
    }
}
