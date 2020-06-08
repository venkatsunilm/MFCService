package com.gm.hmi.mfc.nodes;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.gm.hmi.mfc.GlobalConstants;
import com.gm.hmi.mfc.SwitchAccessNodeCompat;
import com.gm.hmi.mfc.helper.ConverterHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for tree building. Includes some common utility methods.
 */
public abstract class TreeBuilder {

    final AccessibilityService service;
    private static final String APPTRAYNAVWINDOWFRAMEVIEWIENDTEXT = "navigation_bar_frame";

    public static Map<String, AccessibilityNodeInfoCompat> currentScreenNodes;
    public static Map<String, AccessibilityNodeInfoCompat> appTrayNavNodes;
    public static String[] appTrayIdList;
    private static String firstNodeViewId = "";
    public static int windowsIndex = -1;

    TreeBuilder(AccessibilityService service) {
        this.service = service;
    }

    /**
     * Obtains a list of nodes
     */
    public static void getNodesInTalkBackOrder(SwitchAccessNodeCompat root) {
        windowsIndex++;
        int childCount = root.getChildCount();
        Log.i(GlobalConstants.LOGTAG, "AccessibilityNodeInfoCompat childCount: " + childCount);
        AccessibilityNodeInfoCompat firstChild = null;
        AccessibilityNodeInfoCompat secondchild = null;
        for (int i = 0; i < childCount; i++) {
            try {
                firstChild = root.getChild(i);
                if (firstChild.getViewIdResourceName() != null && windowsIndex == 1) {
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

                for (int j = 0; j < subChildCount; j++) {
                    if (root.getViewIdResourceName() != null
                            && root.getViewIdResourceName().endsWith(APPTRAYNAVWINDOWFRAMEVIEWIENDTEXT)) {
                        secondchild = firstChild.getChild(j);
                        appTrayIdList[j] = ConverterHelper.getViewIdFromResourceViewId(
                                secondchild.getViewIdResourceName());

                        appTrayNavNodes.put(
                                ConverterHelper.getViewIdFromResourceViewId(
                                        secondchild.getViewIdResourceName()),
                                secondchild);
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
        appTrayIdList = new String[7];
        windowsIndex = -1;
    }
}
