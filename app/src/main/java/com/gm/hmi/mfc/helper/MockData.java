package com.gm.hmi.mfc.helper;

import android.util.ArrayMap;
import java.util.Map;

public class MockData {

    public static Map<String, NavigationHelperData> getNavigationMap() {
        return navigationMap;
    }

    private static Map<String, NavigationHelperData> navigationMap = new ArrayMap<String, NavigationHelperData>();

    public MockData() {

        // For settings Activity
        navigationMap.put("btn_home",
                new NavigationHelperData(
                        "btn_home", "btn_previous",
                        "btn_now_playing", "btn_TED",
                        "btn_home", "btn_music"));
        navigationMap.put("btn_music",
                new NavigationHelperData(
                        "btn_music", "btn_previous",
                        "btn_now_playing", "btn_TED",
                        "btn_home", "btn_phone"));
        navigationMap.put("btn_phone",
                new NavigationHelperData(
                        "btn_phone", "btn_previous",
                        "btn_now_playing", "btn_TED",
                        "btn_music", "btn_back"));
        navigationMap.put("btn_back",
                new NavigationHelperData(
                        "btn_back", "btn_previous",
                        "btn_now_playing", "btn_TED",
                        "btn_phone", "btn_now_playing"));
        navigationMap.put("btn_overview",
                new NavigationHelperData(
                        "btn_overview", "btn_previous",
                        "btn_now_playing", "btn_TED",
                        "btn_back", "btn_now_playing"));

    }
}

