package com.gm.hmi.mfc.events;

import android.view.accessibility.AccessibilityEvent;

/**
 * Listener for a11 events.
 */
public interface EventListener {

    int getEventTypes();

    void onAccessibilityEvent(AccessibilityEvent event);
}

