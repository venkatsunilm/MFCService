package com.gm.hmi.mfc;

import android.view.accessibility.AccessibilityEvent;

/**
 * Listener for a11 events.
 */
public interface AccessibilityEventListener {

    int getEventTypes();

    void onAccessibilityEvent(AccessibilityEvent event);
}

