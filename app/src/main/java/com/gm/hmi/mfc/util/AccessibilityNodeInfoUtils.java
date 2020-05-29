package com.gm.hmi.mfc.util;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import androidx.annotation.Nullable;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

public class AccessibilityNodeInfoUtils {

    private static final String TAG = "Venk";


    // CHanged from AccessibilityNodeInfoCompat to AccessibilityNodeInfo in the passing parameters
    /** Wrapper for AccessibilityNodeInfoCompat.getWindow() that handles SecurityException. */
    public static @Nullable
    AccessibilityWindowInfo getWindow(
            AccessibilityNodeInfo node) {
        // This implementation is redundant with getWindow(AccessibilityNodeInfo) because there are no
        // un/wrap() functions for AccessibilityWindowInfoCompat.

        if (node == null) {
            return null;
        }

        try {
            return node.getWindow();
        } catch (SecurityException e) {
            Log.e(TAG, "SecurityException in AccessibilityWindowInfoCompat.getWindow()");
            return null;
        }
    }

    /**
     * A wrapper over AccessibilityNodeInfoCompat constructor, so that we can add any desired error
     * checking and memory management.
     *
     * @param nodeInfo The AccessibilityNodeInfo which will be wrapped. The caller retains the
     *     responsibility to recycle nodeInfo.
     * @return Encapsulating AccessibilityNodeInfoCompat, or null if input is null.
     */
    public static AccessibilityNodeInfoCompat toCompat(
            AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return null;
        }
        return AccessibilityNodeInfoCompat.wrap(nodeInfo);
    }


}
