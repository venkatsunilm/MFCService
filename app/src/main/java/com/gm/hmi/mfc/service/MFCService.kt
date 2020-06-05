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
import com.gm.hmi.mfc.helper.MockData
import com.gm.hmi.mfc.helper.NavigationHelperData
import com.gm.hmi.mfc.nodes.MainTreeBuilder
import com.gm.hmi.mfc.nodes.TreeBuilder
import com.gm.hmi.mfc.util.AccessibilityServiceCompatUtils
import com.gm.hmi.mfc.util.UiChangeDetector

public class MFCService : AccessibilityService() {

    private lateinit var data_mock: MutableMap<String, NavigationHelperData>
    private lateinit var eventProcessor: UiChangeDetector

    override fun onServiceConnected() {
        super.onServiceConnected()

        // test mock data initiate
        MockData()
        data_mock = MockData.getNavigationMap()

        // get the active window information
        Log.i(GlobalConstants.LOGTAG, "onServiceConnected.... ")
        eventProcessor = UiChangeDetector()

        Handler().postDelayed({
            buildTree()
        }, 1000)

        // setFocus to First node of the a
        Handler().postDelayed({ //                Log.i(GlobalConstants.LOGTAG, "currentNodeCompat_HMI_THREE Focused...");
            TreeBuilder.currentScreenNodes.get("btn_home")
                ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
        }, 2000)

        Handler().postDelayed({
            val currentFocusedNode =
                AccessibilityServiceCompatUtils.getInputFocusedNode(this)
            Log.i(
                GlobalConstants.LOGTAG,
                "Service currentFocusedNode text " + currentFocusedNode.viewIdResourceName
            )

        }, 3000)
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
            Log.i(
                "Venk",
                "GlobalConstants.CurrentItemIndex : " + GlobalConstants.CurrentItemIndex
            )
        }

        if (eventProcessor != null) {
            eventProcessor.onAccessibilityEvent(event)
        }
    }

    private fun buildTree() {
        MainTreeBuilder(this).addWindowListToTree(
            SwitchAccessWindowInfo.convertZOrderWindowList(
                AccessibilityServiceCompatUtils.getWindows(this)
            )
        )
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {

        if (event!!.action == KeyEvent.ACTION_UP) {
            return false
        }

        Log.i(GlobalConstants.LOGTAG, "Switch access service: " + event.keyCode)
        val currentFocusedNode =
            AccessibilityServiceCompatUtils.getInputFocusedNode(this)
        if (currentFocusedNode == null) {
            return false
        }

        when (event.getKeyCode()) {
            KeyEvent.KEYCODE_BUTTON_L1 -> {
                setNextRotate(data_mock, currentFocusedNode.viewIdResourceName, false);
            }
            KeyEvent.KEYCODE_BUTTON_R1 -> {
                setNextRotate(data_mock, currentFocusedNode.viewIdResourceName, true);
            }
            KeyEvent.KEYCODE_DPAD_LEFT, 2602 -> {
                setNext(data_mock, currentFocusedNode.viewIdResourceName, 0)
            }
            KeyEvent.KEYCODE_DPAD_RIGHT, 2603 -> {
                setNext(data_mock, currentFocusedNode.viewIdResourceName, 1)
            }
            KeyEvent.KEYCODE_DPAD_UP, 2604 -> {
                setNext(data_mock, currentFocusedNode.viewIdResourceName, 2)
            }
            KeyEvent.KEYCODE_DPAD_DOWN, 2605 -> {
                setNext(data_mock, currentFocusedNode.viewIdResourceName, 3)
            }
        }
        return super.onKeyEvent(event)
    }

    private fun setNext(
        data: MutableMap<String, NavigationHelperData>,
        viewIdResourceName: String?,
        directionIndex: Int
    ) {
        val navigationHelperData = data.get(viewIdResourceName)
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

        TreeBuilder.currentScreenNodes.get((currentViewId))
            ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
    }

    private fun setNextRotate(
        data: MutableMap<String, NavigationHelperData>,
        viewIdResourceName: String?,
        value: Boolean
    ) {
        val navigationHelperData = data.get(viewIdResourceName)
        var currentViewId = navigationHelperData?.rotateLeft
        if (value) {
            currentViewId = navigationHelperData?.rotateRight
        }

        TreeBuilder.currentScreenNodes.get((currentViewId))
            ?.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(GlobalConstants.LOGTAG, "onCreate.... ")
    }

    override fun onInterrupt() {
    }
}