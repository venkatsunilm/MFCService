package com.gm.hmi.mfc.nodes;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.util.Log;
import android.view.accessibility.AccessibilityWindowInfo;

import com.gm.hmi.mfc.GlobalConstants;
import com.gm.hmi.mfc.SwitchAccessNodeCompat;
import com.gm.hmi.mfc.SwitchAccessWindowInfo;
import com.gm.hmi.mfc.util.DisplayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MainTreeBuilder {

    private AccessibilityService service;

    public MainTreeBuilder(AccessibilityService service) {
        this.service = service;
    }

    public void addWindowListToTree(List<SwitchAccessWindowInfo> windowList) {
        if (windowList != null) {
            List<SwitchAccessWindowInfo> wList = new ArrayList<>(windowList);
            sortWindowListForTraversalOrder(wList);

            for (SwitchAccessWindowInfo window : wList) {
                Log.i(GlobalConstants.LOGTAG, "window: " + window);
                SwitchAccessNodeCompat windowRoot = window.getRoot();
                if (windowRoot != null) {
                    addViewHierarchyToTree(windowRoot);
                }

            }
        }
    }

    public void addViewHierarchyToTree(
            SwitchAccessNodeCompat root) {
//        TreeScanNode tree = (treeToBuildOn != null) ? treeToBuildOn : new ClearFocusNode();
        /*LinkedList<SwitchAccessNodeCompat> talkBackOrderList =*/
        TreeBuilder.getNodesInTalkBackOrder(root);
//        Iterator<SwitchAccessNodeCompat> reverseListIterator = talkBackOrderList.descendingIterator();
//        while (reverseListIterator.hasNext()) {
//            SwitchAccessNodeCompat next = reverseListIterator.next();
//            tree = addCompatToTree(next, tree, includeNonActionableItems);
//            next.recycle();
//        }
//        return tree;
    }


    /**
     * Sorts windows so that the IME is traversed first, followed by other windows top-to-bottom. Note
     * that the list comes out backwards, which makes it easy to iterate through it when building the
     * tree from the bottom up.
     *
     * @param windowList The list to be sorted.
     */
    private static void sortWindowListForTraversalOrder(List<SwitchAccessWindowInfo> windowList) {
        Collections.sort(
                windowList,
                new Comparator<SwitchAccessWindowInfo>() {
                    @Override
                    public int compare(SwitchAccessWindowInfo arg0, SwitchAccessWindowInfo arg1) {

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

    /*
     * Removes items on the status bar from the window list.
     */
    private void removeStatusBarButtonsFromWindowList(List<SwitchAccessWindowInfo> windowList) {
        int statusBarHeight = DisplayUtils.getStatusBarHeightInPixel(service);

        final Iterator<SwitchAccessWindowInfo> windowIterator = windowList.iterator();
        while (windowIterator.hasNext()) {
            SwitchAccessWindowInfo window = windowIterator.next();
            /* Keep all non-system buttons */
            if (window.getType() != AccessibilityWindowInfo.TYPE_SYSTEM) {
                continue;
            }

            final Rect windowBounds = new Rect();
            window.getBoundsInScreen(windowBounds);

            /* Filter out items in the status bar */
            if ((windowBounds.bottom <= statusBarHeight)) {
                windowIterator.remove();
            }
        }
    }


}
