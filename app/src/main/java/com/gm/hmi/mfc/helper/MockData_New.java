package com.gm.hmi.mfc.helper;

import android.util.ArrayMap;

import java.util.Map;

public class MockData_New {

    public static Map<String, NavigationHelperData> getNavigationMap() {
        return navigationMap;
    }

    private static Map<String, NavigationHelperData> navigationMap = new ArrayMap<String, NavigationHelperData>();

    public MockData_New() {

        // Instead of app tray system icons
        navigationMap.put("btn_home",
                new NavigationHelperData(
                        "app_two", "btn_previous",
                        "btn_now_playing", "btn_TED",
                        "btn_home", "btn_music"));
        navigationMap.put("btn_music",
                new NavigationHelperData(
                        "app_two", "btn_previous",
                        "btn_now_playing", "btn_TED",
                        "btn_home", "btn_phone"));
        navigationMap.put("btn_phone",
                new NavigationHelperData(
                        "app_two", "btn_previous",
                        "btn_now_playing", "btn_TED",
                        "btn_music", "btn_back"));
        navigationMap.put("btn_back",
                new NavigationHelperData(
                        "app_two", "btn_previous",
                        "btn_now_playing", "btn_TED",
                        "btn_phone", "btn_overview"));
        navigationMap.put("btn_overview",
                new NavigationHelperData(
                        "app_two", "btn_previous",
                        "btn_now_playing", "btn_TED",
                        "btn_back", "btn_overview"));


        // middle buttons application
        navigationMap.put("btn_now_playing",
                new NavigationHelperData(
                        "btn_home", "button_next_screen",
                        "btn_now_playing", "btn_TED",
                        "btn_now_playing", "btn_music_menu"));

        navigationMap.put("btn_music_menu",
                new NavigationHelperData(
                        "btn_home", "button_next_screen",
                        "btn_music_menu", "btn_TED",
                        "btn_now_playing", "btn_previous"));


        navigationMap.put("btn_previous",
                new NavigationHelperData(
                        "btn_phone", "btn_previous",
                        "button_next_screen", "btn_TED",
                        "btn_music_menu", "btn_play_pause"));

        navigationMap.put("btn_play_pause",
                new NavigationHelperData(
                        "btn_phone", "btn_play_pause",
                        "button_next_screen", "btn_TED",
                        "btn_previous", "btn_next"));

        navigationMap.put("btn_next",
                new NavigationHelperData(
                        "btn_phone", "btn_next",
                        "button_next_screen", "btn_TED",
                        "btn_play_pause", "btn_more"));

        navigationMap.put("btn_more",
                new NavigationHelperData(
                        "btn_phone", "btn_more",
                        "button_next_screen", "btn_TED",
                        "btn_next", "btn_Settings"));


        navigationMap.put("btn_Settings",
                new NavigationHelperData(
                        "button_next_screen", "btn_Settings",
                        "btn_Settings", "btn_Settings",
                        "btn_more", "btn_Settings"));


        // bottom buttons
        navigationMap.put("btn_TED",
                new NavigationHelperData(
                        "btn_phone", "btn_TED",
                        "btn_previous", "home",
                        "btn_TED", "btn_NAV"));

        navigationMap.put("btn_NAV",
                new NavigationHelperData(
                        "btn_phone", "btn_NAV",
                        "btn_previous", "home",
                        "btn_TED", "btn_NAV"));

        // next screen element
        navigationMap.put("button_next_screen",
                new NavigationHelperData(
                        "btn_music_menu", "btn_Settings",
                        "button_next_screen", "btn_play_pause",
                        "button_next_screen", "button_next_screen"));


        // For hardware app tray
        navigationMap.put("app_tray",
                new NavigationHelperData(
                        "app_tray", "btn_phone",
                        "app_tray", "app_tray",
                        "app_tray", "app_one"));

        navigationMap.put("app_one",
                new NavigationHelperData(
                        "app_one", "btn_phone",
                        "app_one", "app_one",
                        "app_tray", "app_two"));

        navigationMap.put("app_two",
                new NavigationHelperData(
                        "app_two", "btn_phone",
                        "app_two", "app_two",
                        "app_one", "app_three"));

        navigationMap.put("app_three",
                new NavigationHelperData(
                        "app_three", "btn_phone",
                        "app_three", "app_three",
                        "app_two", "app_four"));

        navigationMap.put("app_four",
                new NavigationHelperData(
                        "app_four", "btn_phone",
                        "app_four", "app_four",
                        "app_three", "app_five"));

        navigationMap.put("app_five",
                new NavigationHelperData(
                        "app_five", "btn_phone",
                        "app_five", "app_five",
                        "app_four", "app_five"));

    }
}

