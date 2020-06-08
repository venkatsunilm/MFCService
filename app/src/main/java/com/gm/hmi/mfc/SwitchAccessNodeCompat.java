package com.gm.hmi.mfc;

import android.view.accessibility.AccessibilityWindowInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is an extension class for AccessibilityNodeInfoCompat
 */
public class SwitchAccessNodeCompat extends AccessibilityNodeInfoCompat {

    private final List<AccessibilityWindowInfo> windowsAbove;

    /**
     * @param info         The info to wrap
     * @param windowsAbove The windows sitting on top of the current one. This list is used to compute
     *                     visibility.
     */
    public SwitchAccessNodeCompat(Object info, List<AccessibilityWindowInfo> windowsAbove) {
        super(info);
        if (info == null) {
            throw new NullPointerException();
        }
        if (windowsAbove == null) {
            this.windowsAbove = Collections.emptyList();
        } else {
            this.windowsAbove = new ArrayList<>(windowsAbove);
        }
    }

    /** @return An immutable copy of the current window list */
    public List<AccessibilityWindowInfo> getWindowsAbove() {
        return Collections.unmodifiableList(windowsAbove);
    }
}
