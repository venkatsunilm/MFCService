package com.gm.hmi.mfc.util;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import androidx.annotation.Nullable;

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
