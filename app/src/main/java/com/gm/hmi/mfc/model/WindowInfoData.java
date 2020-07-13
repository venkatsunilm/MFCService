package com.gm.hmi.mfc.model;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.ArrayList;

public class WindowInfoData {
    private String WindowsID;
    private ArrayList<NodeInfoData> nodeInfoDataList = new ArrayList<>();

    public String getWindowsID() {
        return WindowsID;
    }

    public void setWindowsID(String windowsID) {
        WindowsID = windowsID;
    }

    public ArrayList<NodeInfoData> getNodeInfoDataList() {
        return nodeInfoDataList;
    }

    public void setNodeInfoDataList(ArrayList<NodeInfoData> nodeInfoDataList) {
        this.nodeInfoDataList = nodeInfoDataList;
    }
}
