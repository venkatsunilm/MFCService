package com.gm.hmi.mfc.helper;

import android.util.ArrayMap;

import java.util.Map;

/**
 * This code has to be replaced by reading the excel sheet dynamically.
 */
public class MockData {

    public static Map<String, NavigationHelperData> getNavigationMap() {
        return navigationMap;
    }

    private static Map<String, NavigationHelperData> navigationMap = new ArrayMap<String, NavigationHelperData>();

    public MockData() {

        // Instead of app tray system icons we created few
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
                        "btn_phone", "btn_overview"));
        navigationMap.put("btn_overview",
                new NavigationHelperData(
                        "btn_overview", "btn_previous",
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



        // For AOSP pixel app tray
        navigationMap.put("home",
                new NavigationHelperData(
                        "home", "home",
                        "btn_TED", "home",
                        "home", "maps_nav"));

        navigationMap.put("maps_nav",
                new NavigationHelperData(
                        "maps_nav", "maps_nav",
                        "btn_TED", "maps_nav",
                        "home", "music_nav"));

        navigationMap.put("music_nav",
                new NavigationHelperData(
                        "music_nav", "music_nav",
                        "btn_TED", "music_nav",
                        "maps_nav", "phone_nav"));

        navigationMap.put("phone_nav",
                new NavigationHelperData(
                        "phone_nav", "phone_nav",
                        "btn_TED", "phone_nav",
                        "music_nav", "grid_nav"));

        navigationMap.put("grid_nav",
                new NavigationHelperData(
                        "grid_nav", "grid_nav",
                        "btn_TED", "grid_nav",
                        "phone_nav", "notifications"));

        navigationMap.put("notifications",
                new NavigationHelperData(
                        "notifications", "notifications",
                        "btn_TED", "notifications",
                        "grid_nav", "assist"));

        navigationMap.put("assist",
                new NavigationHelperData(
                        "assist", "assist",
                        "btn_TED", "assist",
                        "notifications", "assist"));

    }
}

