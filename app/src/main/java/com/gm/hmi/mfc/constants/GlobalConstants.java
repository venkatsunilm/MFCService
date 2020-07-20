package com.gm.hmi.mfc.constants;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

public class GlobalConstants {

    public static final boolean IS_HARDWARE = false;
    public static final boolean IS_ANDROID_PHONE = false;

    public static final String LOGTAG = "Venk";
    public static int CurrentItemIndex = -1;
    public static boolean isFocusOnSystemAppTray = false;
    public static final String SYSTEM_PACKAGE_NAME = "com.android.systemui";
    public static final String LAUNCHER_PACKAGE_NAME = "com.google.android.apps.moddednexuslauncher";
    public static boolean isFocusOnDummyView;

    public static int applicationScreenStartingIndex;
    public static int totalItems;

    public static int appScreenStartingColIndex;
    public static int appScreenStartingRowIndex;
    // forcefully taken this extra, as we have duel focus issue
    public static AccessibilityNodeInfoCompat currentFocusedNode;
}
