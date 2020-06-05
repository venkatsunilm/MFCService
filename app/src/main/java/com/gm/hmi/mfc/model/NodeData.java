package com.gm.hmi.mfc.model;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.ArrayList;

public class NodeData {

    private String windowsViewId;
    private ArrayList<AccessibilityNodeInfoCompat> accessibilityNodeInfoNodes = new ArrayList<>();

    public String getWindowsViewId() {
        return windowsViewId;
    }

    public void setWindowsViewId(String windowsViewId) {
        this.windowsViewId = windowsViewId;
    }

    public void setAccessibilityNodeInfoNodes(ArrayList<AccessibilityNodeInfoCompat> accessibilityNodeInfoNodes) {
        this.accessibilityNodeInfoNodes = accessibilityNodeInfoNodes;
    }

    public ArrayList<AccessibilityNodeInfoCompat> getAccessibilityNodeInfoNodes() {
        return accessibilityNodeInfoNodes;
    }


}
