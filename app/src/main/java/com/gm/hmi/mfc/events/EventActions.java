package com.gm.hmi.mfc.events;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.Nullable;

import com.gm.hmi.mfc.constants.GlobalConstants;
import com.gm.hmi.mfc.service.MFCService;

public class EventActions implements EventUpdator.onScreenUpdateListener {

    private final String LOG_TAG = "Venk";

    @Override
    public void onScreenUpdate(@Nullable AccessibilityEvent event) {
        Log.i(GlobalConstants.LOGTAG, "onScreenUpdate: " + event.toString());
        if (event != null) {
            int eventType = event.getEventType();
            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                    /*|| eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED*/
                    || eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
                MFCService.instance.buildTree();
            }
        }
    }
}
