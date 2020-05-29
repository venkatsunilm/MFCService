package com.gm.hmi.mfc.util;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UiChangeStabilizer implements UiChangeDetector.PossibleUiChangeListener {

    private final String LOG_TAG = "Venk";

    /**
     * Event types that are sent to WindowChangedListener.
     */
    private static final int MASK_EVENTS_SENT_TO_WINDOW_CHANGED_LISTENER =
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                    | AccessibilityEvent.TYPE_WINDOWS_CHANGED
                    | AccessibilityEvent.TYPE_VIEW_SCROLLED;

    // The AccessibilityEvents that will be sent to the WindowChangedListener after the UI
    // stabilizes. These events will be further sent to the SwitchAccessScreenFeedbackManager to
    // provide screen hints. When the user opens a new window, multiple AccessibilityEvents will be
    // triggered. SwitchAccessScreenFeedbackManager needs to interpret these AccessibilityEvents to
    // generate screen hints.
    private final List<AccessibilityEvent> windowChangeEventList = new ArrayList<>();

    @Override
    public void onPossibleChangeToUi(@Nullable AccessibilityEvent event) {
        Log.i(LOG_TAG, "onPossibleChangeToUi: " + event);

        if (event != null) {
            int eventType = event.getEventType();
            if ((eventType & MASK_EVENTS_SENT_TO_WINDOW_CHANGED_LISTENER) != 0) {
                if (windowChangeEventList.isEmpty()) {
                    // When the windowChangeEventList queue is empty, the current event is the first of a
                    // sequence of events that are triggered by the UI change. Call windowChangedListener to
                    // stop all previous speech immediately before
                    // providing spoken feedback for the new UI.
//                    windowChangedListener.onWindowChangeStarted();
                }

                // Obtain a new AccessibilityEvent because {@link AccessibilityEvent#getSource} can only be
                // called on a sealed instance.
                AccessibilityEvent accessibilitEventInstance = AccessibilityEvent.obtain(event);
                windowChangeEventList.add(accessibilitEventInstance);

                Log.i(LOG_TAG, "windowChangeEventList accessibilitEventInstance: " + accessibilitEventInstance);

            }
        }
    }
}
