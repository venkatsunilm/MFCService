package com.gm.hmi.mfc.helper;

public class ConverterHelper {

    public static String getViewIdFromResourceViewId(String input) {
        return input.substring(input.lastIndexOf("/") + 1);
    }
}
