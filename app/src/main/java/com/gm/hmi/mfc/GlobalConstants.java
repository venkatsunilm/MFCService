package com.gm.hmi.mfc;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

public class GlobalConstants {

    public static final String LOGTAG = "Venk";
    public static AccessibilityNodeInfoCompat currentNodeCompat_homeButton;
    public static AccessibilityNodeInfoCompat currentNodeCompat_musicButton;
    public static AccessibilityNodeInfoCompat currentNodeCompat_phoneButton;
    public static AccessibilityNodeInfoCompat currentNodeCompat_playPauseButton;
    public static AccessibilityNodeInfoCompat currentNodeCompat_previousButton;
    public static AccessibilityNodeInfoCompat currentNodeCompat_TEDradioHourButton;
    public static AccessibilityNodeInfoCompat currentNodeCompat_BackMain;
    public static AccessibilityNodeInfoCompat currentNodeCompat_HomeMain;
    public static AccessibilityNodeInfoCompat currentNodeCompat_OverviewMain;
    public static AccessibilityNodeInfoCompat currentNodeCompat_MIN36;
    public static AccessibilityNodeInfoCompat currentNodeCompat_AOSP_HOME;
    public static AccessibilityNodeInfoCompat currentNodeCompat_AOSP_MAPS;
    public static AccessibilityNodeInfoCompat currentNodeCompat_AOSP_MUSIC;
    public static AccessibilityNodeInfoCompat currentNodeCompat_AOSP_PLAY_PAUSE;
    public static AccessibilityNodeInfoCompat currentNodeCompat_HMI_TWO;
    public static AccessibilityNodeInfoCompat currentNodeCompat_HMI_THREE;

    public static String PREVTEXT = "Prev";
    public static String PLAYPAUSETEXT = "Play_Pause";
    public static String NEXTTEXT = "Next";
    public static String MORETEXT = "More";
    public static String TEDTEXT = "TED RADIO HOUR";
    public static String MIN36TEXT = "36 Min To Work";
    public static String HOMETEXT = "Home";
    public static String MUSICTEXT = "Music";
    public static String PHONETEXT = "Phone";
    public static String BACKTEXT = "Back";
    public static String OVERVIEWTEXT = "Overview";
    public static String MUSICMENUTEXT = "Music menu";
    public static String NOWPLAYINGTEXT = "Now playing";
    public static String SETTINGSTEXT = "Settings";
    public static String MYAPPPACKAGE = "com.example.myapplication";
    public static String AOSP_HOME = "com.android.systemui:id/home";
    public static String AOSP_MAPS = "com.android.systemui:id/maps_nav";
    public static String AOSP_MUSIC = "com.android.systemui:id/music_nav";
    public static String AOSP_PLAY_PAUSE = "com.example.myapplication:id/btn_play_pause"; //
    public static String HMI_TWO = "com.android.systemui:id/app_two";
    public static String HMI_THREE = "com.android.systemui:id/app_three";

    public static int CurrentItemIndex = -1;
    public static boolean isFocusOnSystemAppTray = false;
    public static String SYSTEM_PACKAGE_NAME = "com.android.systemui";


}
