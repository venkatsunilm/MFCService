package com.gm.hmi.mfc.nodes;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.util.Log;
import android.view.accessibility.AccessibilityWindowInfo;

import com.gm.hmi.mfc.constants.GlobalConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Main class to suppor Node builder to do its operations
 */
public class MainNodesBuilder {

    private AccessibilityService service;

    public MainNodesBuilder(AccessibilityService service) {
        this.service = service;
    }

    /**
     * add window list to the array list and iterate to add child nodes to a map
     *
     * @param windowList
     */
    public void addWindowList(List<WindowInfo> windowList) {
        if (windowList != null) {
            NodesBuilder.resetAll();
            List<WindowInfo> wList = new ArrayList<>(windowList);
            sortWindowList(wList);
            Log.i(GlobalConstants.LOGTAG, "Window list Size: " + wList.size());

            for (WindowInfo window : wList) {
                NodeInfo windowRoot = window.getRoot();
                if (windowRoot != null) {
                    addViews(windowRoot, wList.size());
                }
            }


            NodesBuilder.organizeTheNodesForAutoNavigation();

        }
    }

    /**
     * add node information to the map for future use
     *
     * @param root
     */
    public void addViews(
            NodeInfo root, int windowListSize) {
        NodesBuilder.getNodes(root, windowListSize);
    }

    private static void sortWindowList(List<WindowInfo> windowList) {
        Collections.sort(
                windowList,
                new Comparator<WindowInfo>() {
                    @Override
                    public int compare(WindowInfo arg0, WindowInfo arg1) {

                        /* Present IME windows first */
                        final int type0 = arg0.getType();
                        final int type1 = arg1.getType();
                        if (type0 == AccessibilityWindowInfo.TYPE_INPUT_METHOD) {
                            if (type1 == AccessibilityWindowInfo.TYPE_INPUT_METHOD) {
                                return 0;
                            }
                            return 1;
                        }
                        if (type1 == AccessibilityWindowInfo.TYPE_INPUT_METHOD) {
                            return -1;
                        }

                        /* Then go top to bottom, comparing the vertical center of each view */
                        final Rect bounds0 = new Rect();
                        arg0.getBoundsInScreen(bounds0);
                        final int verticalCenter0 = bounds0.top + bounds0.bottom;
                        final Rect bounds1 = new Rect();
                        arg1.getBoundsInScreen(bounds1);
                        final int verticalCenter1 = bounds1.top + bounds1.bottom;

                        if (verticalCenter0 < verticalCenter1) {
                            return 1;
                        }
                        if (verticalCenter0 > verticalCenter1) {
                            return -1;
                        }

                        /* Others are don't care */
                        return 0;
                    }
                });
    }
}
