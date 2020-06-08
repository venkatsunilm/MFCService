package com.gm.hmi.mfc.util;

import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.Nullable;

import com.gm.hmi.mfc.service.MFCService;

public class UiChangeStabilizer implements UiChangeDetector.PossibleUiChangeListener {

    private final String LOG_TAG = "Venk";

    private static final int EVENTS_FOR_NEW_WINDOW =
            AccessibilityEvent.WINDOWS_CHANGE_ADDED
                    | AccessibilityEvent.TYPE_WINDOWS_CHANGED;

    @Override
    public void onPossibleChangeToUi(@Nullable AccessibilityEvent event) {
        if (event != null) {
            int eventType = event.getEventType();
            if (eventType == (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)) {
                MFCService.instance.buildTree();
            }
        }
    }
}
