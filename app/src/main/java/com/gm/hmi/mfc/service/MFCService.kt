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

    private var prevEventTime: Long = 0
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
        if (!GlobalConstants.IS_HARDWARE) {
            MockData()
            data_mock = MockData.getNavigationMap()
        } else {
            MockData_New()
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

//            Handler().postDelayed({
////                NodesBuilder.appTrayNavNodes["home"]
////                    ?.performAction(AccessibilityNodeInfoCompat.ACTION_CLICK)
////                Log.d(GlobalConstants.LOGTAG, "invisibleButton Focused ........ ")
//
////                performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)
//            }, 5000)
        }

//        Handler().postDelayed({
//            Log.i(GlobalConstants.LOGTAG, "disableSelf disableSelf")
////            disableSelf()
//        }, 20000)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val source: AccessibilityNodeInfo = event?.source ?: return
//        TODO: try to stop unnecessary events and reading data as the frame work calls multiple events at an instance.
//        if (Math.abs(event.eventTime - prevEventTime) <= 50) {
//            Log.i(GlobalConstants.LOGTAG, "Previous event time and this time is same, ignoring duplicate events ..........")
//            return
//        }

//        Log.i(GlobalConstants.LOGTAG, "onAccessibilityEvent: " + source.viewIdResourceName);

        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_FOCUSED
            && (source.packageName.toString() == GlobalConstants.SYSTEM_PACKAGE_NAME /*||
                    source.packageName.toString() == GlobalConstants.LAUNCHER_PACKAGE_NAME*/)
            && source.viewIdResourceName != null
        ) {
            GlobalConstants.CurrentItemIndex = event.getCurrentItemIndex()
            GlobalConstants.isFocusOnSystemAppTray = true
        }

        if (eventProcessor != null) {
            eventProcessor.onAccessibilityEvent(event)
        }

        prevEventTime = event.eventTime

    }

    fun buildTree() {

        if (false) {
            return;
        }
        MainNodesBuilder(this).addWindowList(
            WindowInfo.convertWindowArrayOrder(
                ServiceCompatUtils.getWindows(this)
            )
        )
        NodesBuilder.setFocusToFirstnode();

        // get the keys of the setHashMap of the nodes to collect the in
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {

        if (event!!.action == KeyEvent.ACTION_UP) {
            return false
        }

        Log.i(GlobalConstants.LOGTAG, "key code value: " + event?.keyCode.toString())

        currentFocusedNode =
            ServiceCompatUtils.getInputFocusedNode(this)

        Log.i(
            GlobalConstants.LOGTAG,
            "Keycode currentFocusedNode: " + currentFocusedNode?.viewIdResourceName
        )


        if (currentFocusedNode == null) {
            return false
        }

        when (event.getKeyCode()) {
            KeyEvent.KEYCODE_BUTTON_L1, KeyEvent.KEYCODE_COMMA,
            KeyEvent.KEYCODE_NUMPAD_0 -> {
//                setNextRotate(data_mock, currentFocusedNode.viewIdResourceName, false);
                setNextRotate(currentFocusedNode.viewIdResourceName, true)
            }
            KeyEvent.KEYCODE_BUTTON_R1, KeyEvent.KEYCODE_PERIOD,
            KeyEvent.KEYCODE_NUMPAD_DOT -> {
                setNextRotate(data_mock, currentFocusedNode.viewIdResourceName, true);
            }
            KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_A, 2602,
            KeyEvent.KEYCODE_NUMPAD_1 -> {
                setNext(data_mock, currentFocusedNode.viewIdResourceName, 0)
            }
            KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_S, 2603,
            KeyEvent.KEYCODE_NUMPAD_3 -> {
                setNext(data_mock, currentFocusedNode.viewIdResourceName, 1)
            }
            KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_W, 2604,
            KeyEvent.KEYCODE_NUMPAD_9 -> {
                if (GlobalConstants.isFocusOnSystemAppTray) {
                    GlobalConstants.isFocusOnSystemAppTray = false
                    GlobalConstants.isFocusOnDummyView = false

                    NodesBuilder.appTrayNavNodes[((NodesBuilder.appTrayIdList[localIndexAppTray]))]
                        ?.performAction(AccessibilityNodeInfoCompat.ACTION_CLEAR_SELECTION)

                    Log.i(
                        GlobalConstants.LOGTAG,
                        "(NodesBuilder.appTrayIdList[localIndexAppTray]): "
                                + (NodesBuilder.appTrayIdList[localIndexAppTray])
                    )

                    NodesBuilder.currentScreenNodes[(savePreviousAppViewId)]
                        ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
                    return super.onKeyEvent(event)
                }
                setNext(data_mock, currentFocusedNode.viewIdResourceName, 2)
            }
            KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_Z, 2605,
            KeyEvent.KEYCODE_NUMPAD_7 -> {
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

            for (item in NodesBuilder.currentScreenNodes) {
                Log.i(GlobalConstants.LOGTAG, "Current node: " + item)
            }

            Log.i(
                GlobalConstants.LOGTAG,
                "currentScreenNodes[(currentViewId)]: " + NodesBuilder.currentScreenNodes[(currentViewId)]
            )
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

    var rotateIndex = -1
    private fun setNextRotate(
        viewIdResourceName: String?,
        value: Boolean
    ) {
        rotateIndex = GlobalConstants.applicationScreenStartingIndex
        // get the current index and set the next index

        if (value) {
            rotateIndex++
        } else {
            rotateIndex--
        }

        if (rotateIndex <= GlobalConstants.applicationScreenStartingIndex) {
            rotateIndex = GlobalConstants.applicationScreenStartingIndex
        }
//        if (rotateIndex ){
//
//        }

        var keyText = ConverterHelper.getElementByIndexFromHashMap(
            NodesBuilder.currentScrNodesHashMap,
            rotateIndex
        )

        while (!keyText.isEnabled) {
            Log.i(
                GlobalConstants.LOGTAG,
                "Key text is Disabled, so get the next element until it finds which is enabled"
            )
            rotateIndex++
            keyText = ConverterHelper.getElementByIndexFromHashMap(
                NodesBuilder.currentScrNodesHashMap,
                rotateIndex
            )
        }

        Log.i(
            GlobalConstants.LOGTAG, "rotateIndex: " + rotateIndex
                    + " keyText: " + keyText
        )


        keyText.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
//        NodesBuilder.currentScrNodesHashMap["Window#1#1#app_item"]?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
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