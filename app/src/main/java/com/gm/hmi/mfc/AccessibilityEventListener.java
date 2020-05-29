package com.gm.hmi.mfc;

import android.view.accessibility.AccessibilityEvent;

/**
 * Listener for a11y events. Each class that extends this should specify a mask of the events it
 * would handle and return that mask by implementing {@link #getEventTypes()} method.
 */
// TODO: Implement this interface instead of the android/accessibility/utils one in
// services that don't need to use the EventId.
public interface AccessibilityEventListener {

    /** Returns the mask of the events to be handled. */
    int getEventTypes();

    /**
     * Receives the events that are specified in the mask returned by {@link #getEventTypes()} method.
     */
    void onAccessibilityEvent(AccessibilityEvent event);
}

