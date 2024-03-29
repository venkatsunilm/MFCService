package com.gm.hmi.mfc.util;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import androidx.annotation.Nullable;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

/**
 * This is a util class to get node related objects from AccessibilityNodeInfo
 * and to convert from AccessibilityNodeInfo to new AccessibilityNodeInfoCompat
 *
 * TODO: Add or remove few util methods as per the requirement
 */
public class NodeUtils {

    private static final String TAG = "Venk";


    public static @Nullable
    AccessibilityWindowInfo getWindow(
            AccessibilityNodeInfo node) {
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

    public static AccessibilityNodeInfoCompat toCompat(
            AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return null;
        }
        return AccessibilityNodeInfoCompat.wrap(nodeInfo);
    }

}
