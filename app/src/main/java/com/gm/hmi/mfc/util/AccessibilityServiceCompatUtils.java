package com.gm.hmi.mfc.util;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import androidx.annotation.Nullable;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.Collections;
import java.util.List;

public class AccessibilityServiceCompatUtils {

    private static final String TAG = "Venk";

    public static AccessibilityNodeInfoCompat getRootInActiveWindow(AccessibilityService service) {
        if (service == null) {
            return null;
        }

        AccessibilityNodeInfo root = service.getRootInActiveWindow();
        if (root == null) {
            return null;
        }
        return AccessibilityNodeInfoUtils.toCompat(root);
    }

    public static List<AccessibilityWindowInfo> getWindows(AccessibilityService service) {
        if (true) {
            // Use try/catch to fix
            try {
                return service.getWindows();
            } catch (SecurityException e) {
                Log.e(TAG, "SecurityException occurred at AccessibilityService#getWindows(): %s", e);
                return Collections.emptyList();
            }
        }
        try {
            return service.getWindows();
        } catch (Exception e) {
            Log.e(TAG, "Exception occurred at AccessibilityService#getWindows(): %s", e);
            return Collections.emptyList();
        }
    }

    public static @Nullable
    AccessibilityNodeInfoCompat getRootInAccessibilityFocusedWindow(
            AccessibilityService service) {
        if (service == null) {
            return null;
        }

        AccessibilityNodeInfo focusedRoot = null;
        List<AccessibilityWindowInfo> windows = getWindows(service);
        WindowManager manager = new WindowManager(false);
        manager.setWindows(windows);
        AccessibilityWindowInfo accessibilityFocusedWindow =
                manager.getCurrentWindow(false);

        if (accessibilityFocusedWindow != null) {
            focusedRoot = AccessibilityWindowInfoUtils.getRoot(accessibilityFocusedWindow);
        }

        if (focusedRoot == null) {
            focusedRoot = service.getRootInActiveWindow();
        }

        if (focusedRoot == null) {
            return null;
        }

        return AccessibilityNodeInfoUtils.toCompat(focusedRoot);
    }

    public static AccessibilityWindowInfo getActiveWidow(AccessibilityService service) {
        if (service == null) {
            return null;
        }

        AccessibilityNodeInfo rootInActiveWindow = service.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return null;
        }
        AccessibilityWindowInfo window = AccessibilityNodeInfoUtils.getWindow(rootInActiveWindow);
        rootInActiveWindow.recycle();
        return window;
    }

    public static AccessibilityNodeInfoCompat getInputFocusedNode(AccessibilityService service) {
        // TODO: Shall we use active window or accessibility focused window?
        AccessibilityNodeInfoCompat activeRoot = getRootInActiveWindow(service);
        if (activeRoot != null) {
            try {
                return activeRoot.findFocus(AccessibilityNodeInfoCompat.FOCUS_INPUT);
            } finally {
                activeRoot.recycle();
            }
        }
        return null;
    }

    public static AccessibilityNodeInfoCompat getInputAcceeFocusedNode(AccessibilityService service) {
        // TODO: Shall we use active window or accessibility focused window?
        AccessibilityNodeInfoCompat activeRoot = getRootInActiveWindow(service);
        if (activeRoot != null) {
            try {
                return activeRoot.findFocus(AccessibilityNodeInfoCompat.FOCUS_ACCESSIBILITY);
            } finally {
                activeRoot.recycle();
            }
        }
        return null;
    }

}
