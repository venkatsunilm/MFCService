package com.gm.hmi.mfc.nodes;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.gm.hmi.mfc.constants.GlobalConstants;
import com.gm.hmi.mfc.helper.ConverterHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Builds the nodes of app tray and application package in map list.
 * *
 */
public abstract class NodesBuilder {

    final AccessibilityService service;
    private static final String APPTRAYNAVWINDOWFRAMEVIEWIENDTEXT = "navigation_bar_frame";

    // TODO: getters and setters, remove static
    public static Map<String, AccessibilityNodeInfoCompat> currentScreenNodes;
    public static Map<String, AccessibilityNodeInfoCompat> appTrayNavNodes;
    public static String[] appTrayIdList;
    private static String firstNodeViewId = "";
    public static int windowsIndex = -1;

    NodesBuilder(AccessibilityService service) {
        this.service = service;
    }

    /**
     * Obtains a list of nodes from the screen
     * There will be three windows in each screen and we will ready app tray and
     * application nodes and ignore the System status bar layout window
     */
    public static void getNodes(NodeInfo root) {
        windowsIndex++;
        int childCount = root.getChildCount();
        AccessibilityNodeInfoCompat firstChild = null;
        AccessibilityNodeInfoCompat secondchild = null;
        for (int i = 0; i < childCount; i++) {
            try {
                firstChild = root.getChild(i);
                if (firstChild.getViewIdResourceName() != null && windowsIndex == 1) {
                    Log.i(GlobalConstants.LOGTAG, "first child: " + firstChild.getViewIdResourceName());
                    currentScreenNodes.put(
                            ConverterHelper.getViewIdFromResourceViewId(
                                    firstChild.getViewIdResourceName()),
                            firstChild);

                    if (i == 1) {
                        firstNodeViewId = ConverterHelper.getViewIdFromResourceViewId(
                                firstChild.getViewIdResourceName());
                    }
                }
                int subChildCount = firstChild.getChildCount();

//                Log.i(GlobalConstants.LOGTAG, "root.getViewIdResourceName():  " + root.getViewIdResourceName());
                for (int j = 0; j < subChildCount; j++) {

                    boolean isWindowsID = false;
                    if (!GlobalConstants.IS_HARDWARE && root.getViewIdResourceName() != null) {
                        isWindowsID = root.getViewIdResourceName().endsWith(APPTRAYNAVWINDOWFRAMEVIEWIENDTEXT);
                    } else if (GlobalConstants.IS_HARDWARE){
                        isWindowsID = true;
                    }

                    if (isWindowsID) {
                        secondchild = firstChild.getChild(j);
                        appTrayIdList[j] = ConverterHelper.getViewIdFromResourceViewId(
                                secondchild.getViewIdResourceName());

                        appTrayNavNodes.put(
                                ConverterHelper.getViewIdFromResourceViewId(
                                        secondchild.getViewIdResourceName()),
                                secondchild);

                        Log.i(GlobalConstants.LOGTAG, "second child or app tray: " + secondchild.getViewIdResourceName());

                    }
                }
//                Rect bounds = getBoundsInternal(child);
            } catch (Exception e) {
                Log.i(GlobalConstants.LOGTAG, e.toString());
            }

        }

        if (windowsIndex == 2) {
            windowsIndex = -1;
        }

    }

    /**
     * sets the focus to the first node
     */
    public static void setFocusToFirstnode() {
        currentScreenNodes.entrySet().iterator().next().getValue().
                performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
    }

    /**
     * Reset all the values
     */
    public static void resetAll() {
        currentScreenNodes = new HashMap();
        appTrayNavNodes = new HashMap();
        appTrayIdList = new String[20];
        windowsIndex = -1;
    }
}
