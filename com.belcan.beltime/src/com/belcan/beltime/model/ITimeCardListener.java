/*
 * ITimeCardListener.java
 *
 * Copyright 2013 Beltime contributors and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0, which
 * accompanies this distribution and is available at
 * <http://www.eclipse.org/legal/epl-v10.html>.
 *
 * Created on Jul 18, 2013 at 11:02:32 PM.
 */

package com.belcan.beltime.model;

/**
 * A listener of time card events.
 */
public interface ITimeCardListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Called when a job has been started.
     * 
     * @param timeCard
     *        The time card that fired the event; must not be {@code null}.
     * @param job
     *        The job that has been started; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code timeCard} or {@code job} is {@code null}.
     */
    public void onJobStarted(
        TimeCard timeCard,
        Job job );

    /**
     * Called when a job has been stopped.
     * 
     * @param timeCard
     *        The time card that fired the event; must not be {@code null}.
     * @param job
     *        The job that has been stopped; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code timeCard} or {@code job} is {@code null}.
     */
    public void onJobStopped(
        TimeCard timeCard,
        Job job );
}
