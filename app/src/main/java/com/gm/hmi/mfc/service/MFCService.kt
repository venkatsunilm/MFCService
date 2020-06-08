package com.gm.hmi.mfc.service

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.gm.hmi.mfc.GlobalConstants
import com.gm.hmi.mfc.SwitchAccessWindowInfo
import com.gm.hmi.mfc.helper.ConverterHelper
import com.gm.hmi.mfc.helper.MockData
import com.gm.hmi.mfc.helper.NavigationHelperData
import com.gm.hmi.mfc.nodes.MainTreeBuilder
import com.gm.hmi.mfc.nodes.TreeBuilder
import com.gm.hmi.mfc.util.AccessibilityServiceCompatUtils
import com.gm.hmi.mfc.util.UiChangeDetector

public class MFCService : AccessibilityService() {

    private var previousViewId: String? = ""
    private var savePreviousAppViewId: String? = ""
    private var isFocusOnAppTray: Boolean = false
    private lateinit var data_mock: MutableMap<String, NavigationHelperData>
    private lateinit var eventProcessor: UiChangeDetector
    private var localIndexAppTray = 0;

    companion object {
        lateinit var instance: MFCService
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        instance = this

        // test mock data initiate
        MockData()
        data_mock = MockData.getNavigationMap()
        eventProcessor = UiChangeDetector()

        Handler().postDelayed({
            buildTree()
        }, 1000)

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.i(GlobalConstants.LOGTAG, "onAccessibilityEvent: $event")
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
        MainTreeBuilder(this).addWindowListToTree(
            SwitchAccessWindowInfo.convertZOrderWindowList(
                AccessibilityServiceCompatUtils.getWindows(this)
            )
        )

        TreeBuilder.setFocusToFirstnode();
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {

        if (event!!.action == KeyEvent.ACTION_UP) {
            return false
        }
        val currentFocusedNode =
            AccessibilityServiceCompatUtils.getInputFocusedNode(this)

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

                    TreeBuilder.appTrayNavNodes[(savePreviousAppViewId)]
                        ?.performAction(AccessibilityNodeInfoCompat.ACTION_CLEAR_FOCUS)

                    TreeBuilder.appTrayNavNodes[(savePreviousAppViewId)]
                        ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
                    return super.onKeyEvent(event)
                }
                setNext(data_mock, currentFocusedNode.viewIdResourceName, 2)
            }
            KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_Z, 2605 -> {
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
        if (!GlobalConstants.isFocusOnSystemAppTray
            && TreeBuilder.appTrayIdList.contains(
                ConverterHelper.getViewIdFromResourceViewId(currentViewId)
            )
            && directionIndex == 3
        ) {
            GlobalConstants.isFocusOnSystemAppTray = true
        }

        if (!GlobalConstants.isFocusOnSystemAppTray
            && !TreeBuilder.appTrayNavNodes.containsKey(currentViewId)
        ) {
            TreeBuilder.currentScreenNodes[(currentViewId)]
                ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
            savePreviousAppViewId = currentViewId

        } else {
            if (!savePreviousAppViewId.isNullOrEmpty()) {
                val id = TreeBuilder.appTrayIdList.get(localIndexAppTray)
                TreeBuilder.appTrayNavNodes[(id)]
                    ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
            } else {
                TreeBuilder.appTrayNavNodes[(currentViewId)]
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
                if (localIndexAppTray >= TreeBuilder.appTrayIdList.size) {
                    localIndexAppTray = TreeBuilder.appTrayIdList.size - 1
                }
            } else {
                localIndexAppTray--
                if (localIndexAppTray <= 0) {
                    localIndexAppTray = 0
                }
            }
        }

        if (!GlobalConstants.isFocusOnSystemAppTray) {
            TreeBuilder.currentScreenNodes[(currentViewId)]
                ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)

        } else {
            TreeBuilder.appTrayNavNodes[(TreeBuilder.appTrayIdList[localIndexAppTray])]
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