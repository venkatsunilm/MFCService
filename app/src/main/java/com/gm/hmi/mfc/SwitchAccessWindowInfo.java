package com.gm.hmi.mfc;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Extension of AccessibilityWindowInfo that returns {@code ExtendedNodeCompat} for its root
 */
public class SwitchAccessWindowInfo {
    private final AccessibilityWindowInfo accessibilityWindowInfo;
    private final List<AccessibilityWindowInfo> listOfWindowsAbove;

    public static List<SwitchAccessWindowInfo> convertZOrderWindowList(
            List<AccessibilityWindowInfo> originalList) {
        List<SwitchAccessWindowInfo> newList = new ArrayList<>(originalList.size());
        for (int i = 0; i < originalList.size(); i++) {
            newList.add(new SwitchAccessWindowInfo(originalList.get(i), originalList.subList(0, i)));
        }
        return newList;
    }

    public SwitchAccessWindowInfo(
            AccessibilityWindowInfo accessibilityWindowInfo,
            List<AccessibilityWindowInfo> listOfWindowsAbove) {
        if (accessibilityWindowInfo == null) {
            throw new NullPointerException();
        }
        this.accessibilityWindowInfo = accessibilityWindowInfo;
        this.listOfWindowsAbove = listOfWindowsAbove;
    }

    public SwitchAccessNodeCompat getRoot() {
        AccessibilityNodeInfo root = null;
        try {
            root = accessibilityWindowInfo.getRoot();
        } catch (NullPointerException | SecurityException | StackOverflowError e) {
            // If the framework throws an exception, ignore.
        }
        return (root == null) ? null : new SwitchAccessNodeCompat(root, listOfWindowsAbove);
    }

    public int getType() {
        return accessibilityWindowInfo.getType();
    }

    public void getBoundsInScreen(Rect outBounds) {
        accessibilityWindowInfo.getBoundsInScreen(outBounds);
    }
}
