package com.gm.hmi.mfc.util;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import androidx.annotation.Nullable;

/**
 * This is a util class to get information from AccessibilityWindowInfo
 *
 * TODO: Add or remove few util methods as per the requirement
 */
public class WindowUtils {

    private static final String TAG = "Venk";

    @Nullable
    public static AccessibilityNodeInfo getRoot(AccessibilityWindowInfo windowInfo) {
        AccessibilityNodeInfo nodeInfo = null;
        if (windowInfo == null) {
            return null;
        }

        try {
            nodeInfo = windowInfo.getRoot();
        } catch (SecurityException e) {
            Log.e(
                    TAG, "SecurityException occurred at WindowUtils#getRoot(): %s", e);
        }
        return nodeInfo;
    }

}
