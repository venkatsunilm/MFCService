package com.gm.hmi.mfc.nodes;

import android.view.accessibility.AccessibilityWindowInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is an extension class for AccessibilityNodeInfoCompat
 */
public class NodeInfo extends AccessibilityNodeInfoCompat {

    private final List<AccessibilityWindowInfo> windowsAbove;

    public NodeInfo(Object info, List<AccessibilityWindowInfo> windowsAbove) {
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
}
