package com.gm.hmi.mfc.events;

import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.Nullable;

import com.gm.hmi.mfc.service.MFCService;

public class EventActions implements EventUpdator.onScreenUpdateListener {

    private final String LOG_TAG = "Venk";

    @Override
    public void onScreenUpdate(@Nullable AccessibilityEvent event) {
        if (event != null) {
            int eventType = event.getEventType();
            if (eventType == (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)) {
                MFCService.instance.buildTree();
            }
        }
    }
}
