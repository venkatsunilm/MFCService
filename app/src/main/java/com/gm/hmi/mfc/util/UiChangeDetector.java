package com.gm.hmi.mfc.util;

import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.Nullable;

import com.gm.hmi.mfc.AccessibilityEventListener;


/**
 * Class to detect possible changes to the UI based on AccessibilityEvents
 */
public class UiChangeDetector implements AccessibilityEventListener {

    /**
     * Event types that are handled by UiChangeDetector.
     */
    private static final int MASK_EVENTS_HANDLED_BY_UI_CHANGE_DETECTOR =
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                    | AccessibilityEvent.TYPE_WINDOWS_CHANGED
                    | AccessibilityEvent.TYPE_VIEW_SCROLLED
                    | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;

    private final PossibleUiChangeListener listener;

    public UiChangeDetector() {
        this.listener = new UiChangeStabilizer();
    }

    @Override
    public int getEventTypes() {
        return MASK_EVENTS_HANDLED_BY_UI_CHANGE_DETECTOR;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) {
            return;
        }
        int eventType = event.getEventType();
        boolean willClearFocus =
                (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)
                        || (eventType == AccessibilityEvent.TYPE_WINDOWS_CHANGED)
                        || (eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED);

        if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            /* Ignore changes that don't affect the view hierarchy */
            int changeTypes = event.getContentChangeTypes();
            changeTypes &= ~AccessibilityEvent.CONTENT_CHANGE_TYPE_TEXT;
            changeTypes &= ~AccessibilityEvent.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION;
            willClearFocus = (changeTypes != 0);
        }

        if (willClearFocus) {
            listener.onPossibleChangeToUi(event);
        }
    }

    /**
     * Listener that is notified when an Accessibility event might have caused the UI to
     * change.
     */
    public interface PossibleUiChangeListener {
        void onPossibleChangeToUi(@Nullable AccessibilityEvent event);
    }
}


