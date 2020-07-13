package com.gm.hmi.mfc.model;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.ArrayList;

/**
 * This class holds the Node data based on window id
 * <p>
 * TODO: As of now this is not in use, will use in future as per requirement
 */
public class NodeInfoData {

    private String nodeViewId;
    private AccessibilityNodeInfoCompat nodeInfoCompat;

    public NodeInfoData(String nodeViewId, AccessibilityNodeInfoCompat nodeInfoCompat) {
        this.nodeViewId = nodeViewId;
        this.nodeInfoCompat = nodeInfoCompat;
    }

    public String getNodeViewId() {
        return nodeViewId;
    }

    public AccessibilityNodeInfoCompat getNodeInfoCompat() {
        return nodeInfoCompat;
    }
}
