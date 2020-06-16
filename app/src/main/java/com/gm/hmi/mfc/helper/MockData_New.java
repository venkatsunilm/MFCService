package com.gm.hmi.mfc.helper;

import android.util.ArrayMap;

import java.util.Map;

public class MockData_New {

        public static Map<String, NavigationHelperData> getNavigationMap() {
            return navigationMap;
        }

        private static Map<String, NavigationHelperData> navigationMap = new ArrayMap<String, NavigationHelperData>();

        public MockData_New(){

            // For settings Activity
            navigationMap.put("home",
                    new NavigationHelperData(
                            "home", "btn_previous",
                            "btn_now_playing", "btn_TED",
                            "home", "music_nav"));
            navigationMap.put("music_nav",
                    new NavigationHelperData(
                            "music_nav", "btn_previous",
                            "btn_now_playing", "btn_TED",
                            "home", "phone_nav"));
            navigationMap.put("phone_nav",
                    new NavigationHelperData(
                            "phone_nav", "btn_previous",
                            "btn_now_playing", "btn_TED",
                            "music_nav", "grid_nav"));
            navigationMap.put("grid_nav",
                    new NavigationHelperData(
                            "grid_nav", "btn_previous",
                            "btn_now_playing", "btn_TED",
                            "phone_nav", "btn_overview"));
            navigationMap.put("btn_overview",
                    new NavigationHelperData(
                            "btn_overview", "btn_previous",
                            "btn_now_playing", "btn_TED",
                            "grid_nav", "btn_overview"));



            navigationMap.put("btn_now_playing",
                    new NavigationHelperData(
                            "home", "button_next_screen",
                            "btn_now_playing", "btn_TED",
                            "btn_now_playing", "btn_music_menu"));

            navigationMap.put("btn_music_menu",
                    new NavigationHelperData(
                            "home", "button_next_screen",
                            "btn_music_menu", "btn_TED",
                            "btn_now_playing", "btn_previous"));


            navigationMap.put("btn_previous",
                    new NavigationHelperData(
                            "phone_nav", "btn_previous",
                            "button_next_screen", "btn_TED",
                            "btn_music_menu", "btn_play_pause"));

            navigationMap.put("btn_play_pause",
                    new NavigationHelperData(
                            "phone_nav", "btn_play_pause",
                            "button_next_screen", "btn_TED",
                            "btn_previous", "btn_next"));

            navigationMap.put("btn_next",
                    new NavigationHelperData(
                            "phone_nav", "btn_next",
                            "button_next_screen", "btn_TED",
                            "btn_play_pause", "btn_more"));

            navigationMap.put("btn_more",
                    new NavigationHelperData(
                            "phone_nav", "btn_more",
                            "button_next_screen", "btn_TED",
                            "btn_next", "btn_Settings"));


            navigationMap.put("btn_Settings",
                    new NavigationHelperData(
                            "button_next_screen", "btn_Settings",
                            "btn_Settings", "btn_Settings",
                            "btn_more", "btn_Settings"));




            navigationMap.put("btn_TED",
                    new NavigationHelperData(
                            "phone_nav", "btn_TED",
                            "btn_previous", "home",
                            "btn_TED", "btn_NAV"));

            navigationMap.put("btn_NAV",
                    new NavigationHelperData(
                            "phone_nav", "btn_NAV",
                            "btn_previous", "home",
                            "btn_TED", "btn_NAV"));

            navigationMap.put("button_next_screen",
                    new NavigationHelperData(
                            "btn_music_menu", "btn_Settings",
                            "button_next_screen", "btn_play_pause",
                            "button_next_screen", "button_next_screen"));


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

