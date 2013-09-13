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
     * Called when an activity has started.
     * 
     * @param timeCard
     *        The time card that fired the event.
     * @param activity
     *        The activity that has started.
     */
    public void onActivityStarted(
        TimeCard timeCard,
        Activity activity );

    /**
     * Called when an activity has stopped.
     * 
     * @param timeCard
     *        The time card that fired the event.
     * @param activity
     *        The activity that has stopped.
     */
    public void onActivityStopped(
        TimeCard timeCard,
        Activity activity );

    /**
     * Called when the time card has been reset.
     * 
     * @param timeCard
     *        The time card that fired the event.
     */
    public void onReset(
        TimeCard timeCard );
}
