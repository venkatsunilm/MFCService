package com.gm.hmi.mfc.helper;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Helps to do all conversions
 */
public class ConverterHelper {

    public static String getViewIdFromResourceViewId(String input) {
        if (input != null) {
            return input.substring(input.lastIndexOf("/") + 1);
        } else {
            return "";
        }
    }

    /**
     * Helps to get the value object from the position given
     *
     * @param map
     * @param index
     * @return
     */
    public static AccessibilityNodeInfoCompat getElementByIndexFromHashMap(
            HashMap<String, AccessibilityNodeInfoCompat> map, int index) {
        Object[] values = map.keySet().toArray();
        return map.get(values[index]);
    }

    public static int getIndexByKeyFromHashMap(HashMap map, String key) {
        return new ArrayList<String>(map.keySet()).indexOf(key);
    }

    //    public AccessibilityNodeInfoCompat getElementByIndexx(int index){
//        return currentScrNodesHashMap.values().toArray(new AccessibilityNodeInfoCompat[currentScrNodesHashMap.values().size()])[index];
//    }


}
