package com.gm.hmi.mfc.nodes;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.util.Log;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.gm.hmi.mfc.constants.GlobalConstants;
import com.gm.hmi.mfc.helper.ConverterHelper;
import com.gm.hmi.mfc.model.NodeInfoData;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Builds the nodes of app tray and application package in map list.
 * *
 */
public abstract class NodesBuilder {

    private static boolean isNodeFocused;
    final AccessibilityService service;
    private static final String APPTRAYNAVWINDOWFRAMEVIEWIENDTEXT = "navigation_bar_frame"; // navigation_bar_frame

    // TODO: getters and setters, remove static
    public static Map<String, AccessibilityNodeInfoCompat> currentScreenNodes;
    public static Map<String, AccessibilityNodeInfoCompat> appTrayNavNodes;

    private static int windowListSize = -1;
    private static int depthToReadChildrenLevel = 1;
    public static HashMap<String, String> windowChildSize;
    public static HashMap<String, AccessibilityNodeInfoCompat> currentScrNodesHashMap;
    public static HashMap<String, AccessibilityNodeInfoCompat> tempCurrentScrNodesHashMap;
    public static String[] appTrayIdList;
    public static int windowsIndex = -1;
    private static String WindowIndexPrefix = "Window#1";
    private static String firstFocusKeyCol = "W1#C0";
    private static String firstFocusKeyRow = "W1#R1";// W1#R1 for pixel AOSP phone
    public static HashMap<String, HashMap<String, AccessibilityNodeInfoCompat>> windowRows;
    public static Set<Integer> boundsTop = new TreeSet<>();
    private static int boundIndex = 0;
    private static char wIndx = '0';
    private static int boundTopLoopIndex = -1;

    NodesBuilder(AccessibilityService service) {
        this.service = service;
    }

    /**
     * Obtains a list of nodes from the screen
     * There will be three windows in each screen and we will ready app tray and
     * application nodes and ignore the System status bar layout window
     */
    public static void getNodes(NodeInfo root, int windowsCount) {
        windowsIndex++;
        windowListSize = windowsCount;
        Log.i(GlobalConstants.LOGTAG,
                " \n windowsIndex " + windowsIndex
                        + " \n getViewIdResourceName : " + root.getViewIdResourceName()
                        + " \n getContentDescription(): " + root.getContentDescription()
                        + " \n getPackageName: " + root.getPackageName()
                        + " \n firstChild Count : " + root.getChildCount() + "\n    ");

//        updateFirstChildInfo(root);
        updateChildsInfo(root, windowsIndex);
//        windowChildSize.put(String.valueOf(windowsIndex), String.valueOf(indexCheck));
        Log.i(GlobalConstants.LOGTAG, " windowChildSize: " + windowChildSize);
        indexCheck = 0;
        if (windowsIndex == windowListSize - 1) {
            windowsIndex = -1;
        }
    }

    //    region: Try to read all children one by one till the depth is availale
//    TODO: This depth migt not be required as i do not see any children more than three steps in any screens.
//    TODO: In the third child level almost the resource id are null or all are with the same resource name or it is not useful for navigation
//    TODO: except for the app grid content, as per the observation.
    private static AccessibilityNodeInfoCompat child;
    private static int indexCheck = 0;
    private static int childLevelIndex = -1;
    private static NodeInfoData nodeInfoData;

    private static void updateChildsInfo(AccessibilityNodeInfoCompat root, int windowsIndex) {

        int childCount = root.getChildCount();

        if (windowsIndex >= windowListSize) {
            Log.i(GlobalConstants.LOGTAG, "Reached end of reading all windows.........");
            return;
        }

        Log.i(GlobalConstants.LOGTAG, "childCount &&&&&&&&&&&&&&&&&: " + childCount);
        for (int i = 0; i < childCount; i++) {
            child = root.getChild(i);
            if (child == null) {
                return;
            }
            String resourceIdOrContentDescription = child.getViewIdResourceName();

//              The resource id is null in few screens when tested in different hardwares
            if (resourceIdOrContentDescription == null
                    && child.getContentDescription() != null) {
                resourceIdOrContentDescription = child.getContentDescription().toString();
            }

            if (child.getViewIdResourceName() == null && child.getContentDescription() == null) {
                resourceIdOrContentDescription = "NOT_AVAILABLE#";
            }

//            String keyName = "Windows#" + windowsIndex + "#"
//                    + "indexCheck#" + indexCheck + "#"
//                    + "childLevelIndex#" + childLevelIndex + "#"
//                    + ConverterHelper.getViewIdFromResourceViewId(resourceIdOrContentDescription)
//                    + "#" + child.getPackageName();

            String keyName = "Window#" + windowsIndex + "#"
                    + i + "#"
                    + ConverterHelper.getViewIdFromResourceViewId(resourceIdOrContentDescription);
//            Log.i(GlobalConstants.LOGTAG, " outside keyName: " + keyName
//                    + "  \nchild: " + child);

//            windowChildSize.put(String.valueOf(windowsIndex), String.valueOf(i));
//            Log.i(GlobalConstants.LOGTAG, " childLevelIndex: " + childLevelIndex
//                    + " depthToReadChildrenLevel: " + depthToReadChildrenLevel
//                    + " childLevelIndex < depthToReadChildrenLevel: " + (childLevelIndex < depthToReadChildrenLevel));

            if (childLevelIndex < depthToReadChildrenLevel
                    && child.isFocusable() && child.isImportantForAccessibility()
                    && !resourceIdOrContentDescription.contains("NOT_AVAILABLE#")
                    && !child.getClassName().toString().contains("RecyclerView")
                    && !child.getViewIdResourceName().contains("id/car_nav_button_icon")) {
                currentScreenNodes.put(keyName, child);
                currentScrNodesHashMap.put(keyName, child);
                Log.i(GlobalConstants.LOGTAG, " inside keyName: " + keyName);
            }

            childLevelIndex++;

            Log.i(GlobalConstants.LOGTAG, "\n .... next ");

            updateChildsInfo(child, windowsIndex);
            childLevelIndex = -1;

            Log.i(GlobalConstants.LOGTAG, "\n ******************** ");
        }
        indexCheck++;
    }
//  endregion:

    private static void updateFirstChildInfo(NodeInfo root) {
        AccessibilityNodeInfoCompat firstChild = null;
        AccessibilityNodeInfoCompat secondChild = null;
        AccessibilityNodeInfoCompat thirdChild = null;

        int childCount = root.getChildCount();

        for (int i = 0; i < childCount; i++) {

            try {
                firstChild = root.getChild(i);
                String firstResourceIdOrContentDescription = firstChild.getViewIdResourceName();

//              The resource id is null in few screens when tested in different hardwares
                if (firstResourceIdOrContentDescription == null
                        && firstChild.getContentDescription() != null) {
                    firstResourceIdOrContentDescription = firstChild.getContentDescription().toString();
                }

                if (windowsIndex == 1) {
                    Log.i(GlobalConstants.LOGTAG,
                            "firstChild resourceOrDescriptionName: " +
                                    firstResourceIdOrContentDescription);

                    currentScreenNodes.put(
                            ConverterHelper.getViewIdFromResourceViewId(
                                    firstResourceIdOrContentDescription),
                            firstChild);
                }
                updateSecondChildInfo(root, firstChild, secondChild, thirdChild);

            } catch (Exception e) {
                Log.i(GlobalConstants.LOGTAG, e.toString());
            }
        }
    }

    private static void updateSecondChildInfo(NodeInfo root,
                                              AccessibilityNodeInfoCompat firstChild,
                                              AccessibilityNodeInfoCompat secondChild,
                                              AccessibilityNodeInfoCompat thirdChild) {
        int secondChildCount = firstChild.getChildCount();

        for (int j = 0; j < secondChildCount; j++) {
            boolean isApptrayContent = true;
//            if (GlobalConstants.IS_HARDWARE) {
//                isApptrayContent = true;
//            }
//
//            if (!GlobalConstants.IS_HARDWARE && root.getViewIdResourceName() != null) {
//                isApptrayContent = root.getViewIdResourceName().endsWith(APPTRAYNAVWINDOWFRAMEVIEWIENDTEXT);
//            }

            if (isApptrayContent) {
                secondChild = firstChild.getChild(j);

                String secondResourceIdOrContentDescription = secondChild.getViewIdResourceName();

//              The resource id is null in few screens when tested in different hardwares
                if (secondResourceIdOrContentDescription == null
                        && secondChild.getContentDescription() != null) {
                    secondResourceIdOrContentDescription = secondChild.getContentDescription().toString();
                }


                appTrayIdList[j] = ConverterHelper.getViewIdFromResourceViewId(
                        secondResourceIdOrContentDescription);

                appTrayNavNodes.put(
                        ConverterHelper.getViewIdFromResourceViewId(
                                secondResourceIdOrContentDescription),
                        secondChild);


                Log.i(GlobalConstants.LOGTAG, "\n app tray secondResourceIdOrContentDescription: "
                        + secondResourceIdOrContentDescription);

                if (secondChild.getViewIdResourceName() == null
                        && secondChild.getContentDescription() == null) {
                    Log.i(GlobalConstants.LOGTAG, "secondChild info: " + secondChild);
                }

                updateThirdChildInfo(root, secondChild, thirdChild);
            }
        }
    }

    private static void updateThirdChildInfo(NodeInfo root,
                                             AccessibilityNodeInfoCompat secondChild,
                                             AccessibilityNodeInfoCompat thirdChild) {

        int thirdChildCount = secondChild.getChildCount();

        for (int k = 0; k < thirdChildCount; k++) {
            thirdChild = secondChild.getChild(k);

            String thirdResourceIdOrContentDescription = thirdChild.getViewIdResourceName();

//          The resource id is null in few screens when tested in different hardwares
            if (thirdResourceIdOrContentDescription == null
                    && thirdChild.getContentDescription() != null) {
                thirdResourceIdOrContentDescription = thirdChild.getContentDescription().toString();
            }

            Log.i(GlobalConstants.LOGTAG, "thirdChild resourceOrDescriptionName: "
                    + thirdResourceIdOrContentDescription);
        }


    }

    public static String getCurrentPackageName() {
        return null;
    }

    /**
     * sets the focus to the first node
     */
    public static void setFocusToFirstnode() {
        if (currentScrNodesHashMap.size() > 0) {
            for (String key : currentScrNodesHashMap.keySet()) {
                Log.i(GlobalConstants.LOGTAG, " currentScrNodesHashMap key: " + key);
                if (key.startsWith(WindowIndexPrefix)) {
                    Log.i(GlobalConstants.LOGTAG, "setFocusToFirstnode: "
                            + currentScrNodesHashMap.get(key).getViewIdResourceName());
                    currentScrNodesHashMap.get(key)
                            .performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
                    GlobalConstants.applicationScreenStartingIndex = ConverterHelper.getIndexByKeyFromHashMap(
                            currentScrNodesHashMap,
                            key
                    );
                    Log.i(GlobalConstants.LOGTAG, "GlobalConstants.startingIndex: " + GlobalConstants.applicationScreenStartingIndex);
                    break;
                }
            }
        }
    }

    public static void setFocusToFirstAppScrrenNode() {
        isNodeFocused = false;
        for (String windowRowKey : windowRows.keySet()) {
            Log.i(GlobalConstants.LOGTAG, "windowRowKey: " + windowRowKey);
            HashMap<String, AccessibilityNodeInfoCompat> rowWiseNodes = windowRows.get(windowRowKey);
            for (Map.Entry<String, AccessibilityNodeInfoCompat> entry : rowWiseNodes.entrySet()) {
                Log.i(GlobalConstants.LOGTAG, entry.getKey() + " = " + entry.getValue());
                String key = entry.getKey();
                if (windowRowKey.contains(firstFocusKeyRow) && key.contains(firstFocusKeyCol)) {
                    Log.i(GlobalConstants.LOGTAG, "setFocusToFirstnode: "
                            + rowWiseNodes.get(key).getViewIdResourceName());
                    rowWiseNodes.get(key).performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);

                    GlobalConstants.currentFocusedNode = rowWiseNodes.get(key);

                    GlobalConstants.appScreenStartingRowIndex =
                            ConverterHelper.getIndexByKeyFromHashMap(windowRows, windowRowKey);
                    Log.i(GlobalConstants.LOGTAG, "GlobalConstants.appScreenStartingRowIndex: "
                            + GlobalConstants.appScreenStartingRowIndex);

                    GlobalConstants.appScreenStartingColIndex =
                            ConverterHelper.getIndexByKeyFromHashMap(rowWiseNodes, key);
                    Log.i(GlobalConstants.LOGTAG, "GlobalConstants.appScreenStartingColIndex: "
                            + GlobalConstants.appScreenStartingColIndex);
                    isNodeFocused = true;
                    break;
                }
            }
            if (isNodeFocused) {
                break;
            }
            Log.i(GlobalConstants.LOGTAG, "\n ");
        }
    }

    /**
     * Reset all the values
     */
    public static void resetAll() {
        windowChildSize = new LinkedHashMap();
        windowRows = new LinkedHashMap<>();
        boundsTop = new TreeSet<>();
        currentScreenNodes = new HashMap();
        currentScrNodesHashMap = new LinkedHashMap();
        appTrayNavNodes = new HashMap();
        appTrayIdList = new String[100];
        windowsIndex = -1;
    }


    public static void organizeTheNodesForAutoNavigation() {
        boundIndex = 0;
        boundTopLoopIndex = -1;
        for (String key : currentScrNodesHashMap.keySet()) {
            final Rect bounds = new Rect();
            currentScrNodesHashMap.get(key).getBoundsInScreen(bounds);
//            Log.i(GlobalConstants.LOGTAG, " Key " + key
//                    + "\nboundaries:  " + bounds);

            boundsTop.add(bounds.top);
        }

        int boundsTopSize = boundsTop.size();
        tempCurrentScrNodesHashMap = new LinkedHashMap<>();
        tempCurrentScrNodesHashMap = currentScrNodesHashMap;
        currentScrNodesHashMap = new LinkedHashMap<>();
        for (int top : boundsTop) {
            HashMap<String, AccessibilityNodeInfoCompat> rowWiseNodes = new LinkedHashMap<>();
            boundIndex = 0;
            boundTopLoopIndex++;
            for (String key : tempCurrentScrNodesHashMap.keySet()) {
                final Rect bounds = new Rect();
                tempCurrentScrNodesHashMap.get(key).getBoundsInScreen(bounds);
                if (top == bounds.top) {
                    wIndx = key.charAt(WindowIndexPrefix.length() - 1);
                    String keyTop = "W" + wIndx + "#" + "C" + boundIndex++;
                    rowWiseNodes.put(keyTop, tempCurrentScrNodesHashMap.get(key));
                    currentScrNodesHashMap.put(key, tempCurrentScrNodesHashMap.get(key));
                    Log.i(GlobalConstants.LOGTAG, "KeyTop: " + keyTop);
                    Log.i(GlobalConstants.LOGTAG, "key: " + key
                            + " == " + tempCurrentScrNodesHashMap.get(key).getViewIdResourceName());

                }
            }
            windowRows.put("W" + String.valueOf(wIndx) + "#" + "R" + boundTopLoopIndex, rowWiseNodes);
        }
    }
}
