package com.gm.hmi.mfc.helper;

/**
 * Helps to do all conversions
 */
public class ConverterHelper {

    public static String getViewIdFromResourceViewId(String input) {
        return input.substring(input.lastIndexOf("/") + 1);
    }
}
