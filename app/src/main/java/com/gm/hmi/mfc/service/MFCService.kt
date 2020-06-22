package com.gm.hmi.mfc.service

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.gm.hmi.mfc.constants.GlobalConstants
import com.gm.hmi.mfc.events.EventUpdator
import com.gm.hmi.mfc.helper.ConverterHelper
import com.gm.hmi.mfc.helper.MockData
import com.gm.hmi.mfc.helper.MockData_New
import com.gm.hmi.mfc.helper.NavigationHelperData
import com.gm.hmi.mfc.nodes.MainNodesBuilder
import com.gm.hmi.mfc.nodes.NodesBuilder
import com.gm.hmi.mfc.nodes.WindowInfo
import com.gm.hmi.mfc.util.ServiceCompatUtils

/**
 * Main service class to use Accessibility feature
 */
public class MFCService : AccessibilityService() {

    private lateinit var currentFocusedNode: AccessibilityNodeInfoCompat
    private var previousViewId: String? = ""
    private var savePreviousAppViewId: String? = ""
    private var isFocusOnAppTray: Boolean = false
    private lateinit var data_mock: MutableMap<String, NavigationHelperData>
    private lateinit var eventProcessor: EventUpdator
    private var localIndexAppTray = 0;

    companion object {
        lateinit var instance: MFCService
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(GlobalConstants.LOGTAG, "onDestroy onDestroy")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        instance = this

        // test mock data initiate
        MockData()
        if (!GlobalConstants.IS_HARDWARE) {
            data_mock = MockData.getNavigationMap()

//            data_mock.clear()
//
//            // start the for loop here and read data one by one
//            for (i in 0..10){
//
//                val value = NavigationHelperData(
//                    "app_two", "btn_previous",
//                    "btn_now_playing", "btn_TED",
//                    "btn_home", "btn_music"
//                )
//
//                data_mock.put("btn_home_sample", value)
//            }


        } else {
            data_mock = MockData_New.getNavigationMap()
        }
        eventProcessor = EventUpdator()

        Handler().postDelayed({
            buildTree()
        }, 1000)


        if (GlobalConstants.IS_HARDWARE) {
            Handler().postDelayed({
                Log.i(GlobalConstants.LOGTAG, "btn_TED focused..................................")
                NodesBuilder.currentScreenNodes["btn_TED"]
                    ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
            }, 10000)

            Handler().postDelayed({
                NodesBuilder.appTrayNavNodes["app_two"]
                    ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
            }, 15000)

            Handler().postDelayed({
                NodesBuilder.appTrayNavNodes["app_four"]
                    ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
            }, 20000)

            Handler().postDelayed({
                NodesBuilder.currentScreenNodes["btn_music"]
                    ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
            }, 25000)

            Log.i(GlobalConstants.LOGTAG, "IS_HARDWARE True")
        } else {
//
//            Handler().postDelayed({
//                NodesBuilder.currentScreenNodes["btn_phone"]
//                    ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
//                Log.d(GlobalConstants.LOGTAG, "invisibleButton Focused ........ ")
//            }, 5000)
        }

//        Handler().postDelayed({
//            Log.i(GlobalConstants.LOGTAG, "disableSelf disableSelf")
////            disableSelf()
//        }, 20000)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
//        Log.i(GlobalConstants.LOGTAG, "onAccessibilityEvent: $event")
        val source: AccessibilityNodeInfo = event?.source ?: return

        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_FOCUSED
            && source.packageName.toString() == GlobalConstants.SYSTEM_PACKAGE_NAME
            && source.viewIdResourceName != null
        ) {
            GlobalConstants.CurrentItemIndex = event.getCurrentItemIndex()
            GlobalConstants.isFocusOnSystemAppTray = true
        }

        if (eventProcessor != null) {
            eventProcessor.onAccessibilityEvent(event)
        }
    }

    fun buildTree() {
        MainNodesBuilder(this).addWindowList(
            WindowInfo.convertWindowArrayOrder(
                ServiceCompatUtils.getWindows(this)
            )
        )

        NodesBuilder.setFocusToFirstnode();
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {

        Log.i(GlobalConstants.LOGTAG, "key code value: " + event?.keyCode.toString())
        if (event!!.action == KeyEvent.ACTION_UP) {
            return false
        }
        currentFocusedNode =
            ServiceCompatUtils.getInputFocusedNode(this)

        if (currentFocusedNode == null) {
            return false
        }

        when (event.getKeyCode()) {
            KeyEvent.KEYCODE_BUTTON_L1, KeyEvent.KEYCODE_COMMA -> {
                setNextRotate(data_mock, currentFocusedNode.viewIdResourceName, false);
            }
            KeyEvent.KEYCODE_BUTTON_R1, KeyEvent.KEYCODE_PERIOD -> {
                setNextRotate(data_mock, currentFocusedNode.viewIdResourceName, true);
            }
            KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_A, 2602 -> {
                setNext(data_mock, currentFocusedNode.viewIdResourceName, 0)
            }
            KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_S, 2603 -> {
                setNext(data_mock, currentFocusedNode.viewIdResourceName, 1)
            }
            KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_W, 2604 -> {
                if (GlobalConstants.isFocusOnSystemAppTray) {
                    GlobalConstants.isFocusOnSystemAppTray = false
                    GlobalConstants.isFocusOnDummyView = false

//                    NodesBuilder.appTrayNavNodes[(savePreviousAppViewId)]
//                        ?.performAction(AccessibilityNodeInfoCompat.ACTION_CLEAR_FOCUS)

                    Log.i(
                        GlobalConstants.LOGTAG,
                        "NodesBuilder.currentScreenNodes[(savePreviousAppViewId)]: "
                                + NodesBuilder.currentScreenNodes[(savePreviousAppViewId)]
                    )

                    NodesBuilder.currentScreenNodes[(savePreviousAppViewId)]
                        ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
                    return super.onKeyEvent(event)
                }
                setNext(data_mock, currentFocusedNode.viewIdResourceName, 2)
            }
            KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_Z, 2605 -> {
//                currentFocusedNode.refresh()
//                currentFocusedNode.recycle()
                setNext(data_mock, currentFocusedNode.viewIdResourceName, 3)
            }
        }
        return super.onKeyEvent(event)
    }

    /**
     * This method sets the navigation directions dynamically
     */
    private fun setNext(
        data: Map<String, NavigationHelperData>,
        viewIdResourceName: String?,
        directionIndex: Int
    ) {

        val navigationHelperData =
            data.get(ConverterHelper.getViewIdFromResourceViewId(viewIdResourceName))
        var currentViewId = navigationHelperData?.left
        when (directionIndex) {
            1 -> {
                currentViewId = navigationHelperData?.right.toString()
            }
            2 -> {
                currentViewId = navigationHelperData?.up.toString()
            }
            3 -> {
                currentViewId = navigationHelperData?.down.toString()
            }
        }

        // This logic works only for the navigation down button for CSM it will be on LEFT
        Log.i(
            GlobalConstants.LOGTAG,
            "Current View ID: " + currentViewId + " directionIndex: " + directionIndex +
                    "GlobalConstants.isFocusOnSystemAppTray: " + GlobalConstants.isFocusOnSystemAppTray +
                    "NodesBuilder.appTrayIdList.contains(currentViewId): " + NodesBuilder.appTrayIdList.contains(
                currentViewId
            )
        )

//        for(x in NodesBuilder.appTrayIdList){
//            Log.i(GlobalConstants.LOGTAG, "NodesBuilder.appTrayIdList : " + x)
//        }

//        if focus is on not on app tray and if next focus is about to point to the app tray
//        also if the direction is down the set focus to the dummy button
        if (!GlobalConstants.isFocusOnSystemAppTray
            && NodesBuilder.appTrayIdList.contains(currentViewId)
            && directionIndex == 3
        ) {
            GlobalConstants.isFocusOnSystemAppTray = true
            GlobalConstants.isFocusOnDummyView = true

            // force save the current id so we can focus when we come back from app tray
            savePreviousAppViewId = ConverterHelper.getViewIdFromResourceViewId(viewIdResourceName)

            Log.i(
                GlobalConstants.LOGTAG,
                "isFocused on app tray savePreviousAppViewId: " + savePreviousAppViewId
            )
            NodesBuilder.currentScreenNodes["dummyView"]
                ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)

            // region action clear focus code research, not in use as of now
//            val activeRoot =
//                ServiceCompatUtils.getRootInActiveWindow(this)
//            if (activeRoot != null) {
//                try {
//                    var currNode = activeRoot.findFocus(AccessibilityNodeInfoCompat.ACTION_FOCUS)
//                    Log.i(
//                        GlobalConstants.LOGTAG,
//                        "currentFocusedNode: " + currNode.viewIdResourceName
//                    )
//                    currNode.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS)
//                    currNode.isFocused = false
//                    NodesBuilder.currentScreenNodes["dummyView"]
//                        ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
//                } finally {
//                    activeRoot.recycle()
//                }
//            }
//            currentFocusedNode.isEnabled = false
//            currentFocusedNode.isFocused = false
//            AccessibilityNodeInfo.ACTION_CLEAR_SELECTION
//            currentFocusedNode.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS)
//            currentFocusedNode.isSelected = false
//            currentFocusedNode.isDismissable = true

//            endregion, not in use as of now

        }

        if (!GlobalConstants.isFocusOnSystemAppTray
            && !NodesBuilder.appTrayNavNodes.containsKey(currentViewId)
            && !GlobalConstants.isFocusOnDummyView
        ) {
            NodesBuilder.currentScreenNodes[(currentViewId)]
                ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
            savePreviousAppViewId = currentViewId

        } else if (GlobalConstants.isFocusOnSystemAppTray) {
            if (!savePreviousAppViewId.isNullOrEmpty()) {
                // a localIndexAppTray index is mandatory to navigate through the app tray
                val id = NodesBuilder.appTrayIdList.get(localIndexAppTray)
                NodesBuilder.appTrayNavNodes[(id)]
                    ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
            } else {
                NodesBuilder.appTrayNavNodes[(currentViewId)]
                    ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
            }
        }

        previousViewId = currentViewId
    }

    /**
     * This method sets the next rotate left and right dynamically
     */
    private fun setNextRotate(
        data: Map<String, NavigationHelperData>,
        viewIdResourceName: String?,
        value: Boolean
    ) {
        val navigationHelperData =
            data[ConverterHelper.getViewIdFromResourceViewId(viewIdResourceName)]
        var currentViewId = navigationHelperData?.rotateLeft
        if (value) {
            currentViewId = navigationHelperData?.rotateRight
        }

        if (GlobalConstants.isFocusOnSystemAppTray) {
            if (value) {
                localIndexAppTray++
                if (localIndexAppTray >= NodesBuilder.appTrayIdList.size) {
                    localIndexAppTray = NodesBuilder.appTrayIdList.size - 1
                }
            } else {
                localIndexAppTray--
                if (localIndexAppTray <= 0) {
                    localIndexAppTray = 0
                }
            }
        }

        if (!GlobalConstants.isFocusOnSystemAppTray) {
            NodesBuilder.currentScreenNodes[(currentViewId)]
                ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)

        } else {
            NodesBuilder.appTrayNavNodes[(NodesBuilder.appTrayIdList[localIndexAppTray])]
                ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
        }

        previousViewId = currentViewId

    }

    override fun onCreate() {
        super.onCreate()
        Log.i(GlobalConstants.LOGTAG, "onCreate.... ")
    }

    override fun onInterrupt() {
    }


}